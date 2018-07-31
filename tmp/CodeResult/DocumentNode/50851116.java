package de.unirostock.extractor.CellML;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import cellml_api.CellMLComponent;
import cellml_api.CellMLComponentIterator;
import cellml_api.CellMLComponentSet;
import cellml_api.CellMLVariable;
import cellml_api.CellMLVariableIterator;
import cellml_api.CellMLVariableSet;
import cellml_api.Connection;
import cellml_api.ConnectionIterator;
import cellml_api.ConnectionSet;
import cellml_api.MapComponents;
import cellml_api.MapVariables;
import cellml_api.MapVariablesIterator;
import cellml_api.MapVariablesSet;
import cellml_api.Model;
import cellml_api.Reaction;
import cellml_api.ReactionIterator;
import cellml_api.ReactionSet;
import cellml_api.Role;
import cellml_api.RoleIterator;
import cellml_api.VariableRef;
import cellml_api.VariableRefIterator;
import cellml_api.VariableRefSet;

import com.google.gson.Gson;

import de.unirostock.configuration.Property;
import de.unirostock.configuration.Relation.AnnotationRelTypes;
import de.unirostock.configuration.Relation.CellmlRelTypes;
import de.unirostock.configuration.Relation.DatabaseRelTypes;
import de.unirostock.configuration.Relation.DocumentRelTypes;
import de.unirostock.data.PersonWrapper;
import de.unirostock.data.PublicationWrapper;
import de.unirostock.database.traverse.DBTraverser;
import de.unirostock.extractor.Extractor;
import de.unirostock.util.CmetaContainer;



public class CellMLExtractor extends Extractor{

	private static CellMLLoader cLoader = new CellMLLoader();
	private static Transaction tx; 
	
	public static Node extractStoreIndex(String filePath, PublicationWrapper publication, Long databaseID) throws XMLStreamException, IOException{
		Node documentNode = null;
		tx = graphDB.beginTx();
		try {
			documentNode = extractFromCellML(filePath, publication, databaseID);
			tx.success();
		} finally {
			tx.finish();
		}				
		//in case no node was generated, generate an empty one
		if (documentNode==null) {
			tx = graphDB.beginTx();
			try {
				documentNode = graphDB.createNode();
				graphDB.getReferenceNode().createRelationshipTo(documentNode, DocumentRelTypes.HAS_DOCUMENT);				
				documentNode.setProperty(Property.General.NODETYPE, Property.NodeType.DOCUMENT);
				tx.success();
			} finally {
				tx.finish();
			}
		}
		return documentNode;
	}

	private static Node extractFromCellML(String filePath,
			PublicationWrapper publication, Long databaseID){
		
		Node documentNode = graphDB.createNode();
		graphDB.getReferenceNode().createRelationshipTo(documentNode, DocumentRelTypes.HAS_DOCUMENT);
		
		documentNode.setProperty(Property.General.NODETYPE, Property.NodeType.DOCUMENT);
				
		//try to parse CellML model

		Model model = null;
		try {
			//TODO remove that!
			filePath = StringUtils.replace(filePath, "models.cellml.org", "184.169.251.126");			
			model = CellMLReader.loadFromURL(cLoader.getCellMLBootstrap(), filePath);
		} catch (RuntimeException rte) { 
			return documentNode;
		} 		
		documentNode.setProperty(Property.CellML.VERSION, model.getCellmlVersion());		
		if (databaseID!=null) documentNode.setProperty(Property.General.DATABASEID, databaseID.longValue());
		
		//resolve all import dependencies
		model.fullyInstantiateImports();

			
		Node modelNode = graphDB.createNode();
		documentNode.createRelationshipTo(modelNode, DocumentRelTypes.HAS_MODEL);
		modelNode.createRelationshipTo(documentNode, DatabaseRelTypes.BELONGS_TO);
		modelNode.setProperty(Property.General.NAME, model.getName());
		modelNode.setProperty(Property.General.ID, model.getCmetaId());
		modelNode.setProperty(Property.General.NODETYPE, Property.NodeType.MODEL);
		//index model name and id
		modelIndex.add(modelNode, Property.General.NAME, model.getName());
		modelIndex.add(modelNode, Property.General.ID, model.getCmetaId()); 		
		commitAndReopenTransaction();
		
		extractModelMetadata(modelNode, filePath);
		commitAndReopenTransaction();
		
		Map<String, Map<Node, Map<String, Node>>> componentNodes = extractCellmlComponents(model.getModelComponents(), modelNode);
		
		extractCellmlConnections(model.getConnections(), componentNodes);
		componentNodes.clear();
		
		//model.getGroups();
		
		if (publication!=null) {
			Node annotationNode = DBTraverser.fromNodeToAnnotation(modelNode);
			if (annotationNode==null){
				annotationNode = graphDB.createNode();
				annotationNode.setProperty(Property.General.NODETYPE, Property.NodeType.ANNOTATION);
				annotationNode.createRelationshipTo(modelNode, DatabaseRelTypes.BELONGS_TO);
				modelNode.createRelationshipTo(annotationNode, AnnotationRelTypes.HAS_ANNOTATION);
			} 
			Extractor.processPublication(publication, annotationNode, modelNode);
				
		}

		return documentNode;
	}



	private static void extractModelMetadata(Node modelNode, String filePath) {
	
		CmetaContainer cmeta = readCmeta(filePath);
		if (cmeta==null) return;

		//create new annotation if there is something...
		Node annotationNode = graphDB.createNode();
		annotationNode.setProperty(Property.General.NODETYPE, Property.NodeType.ANNOTATION);
		annotationNode.createRelationshipTo(modelNode, DatabaseRelTypes.BELONGS_TO);
		modelNode.createRelationshipTo(annotationNode, AnnotationRelTypes.HAS_ANNOTATION);
		
		String[] modelAuthor = StringUtils.split(cmeta.getModel_author());
		if ((modelAuthor!=null) && (modelAuthor.length > 0)){		
			String firstName = modelAuthor[0];
			String lastName = null;
			if (modelAuthor.length>1) {
				lastName = modelAuthor[1];
			} else {
				//use the only name provided as last name
				lastName = firstName;
				firstName = null;
			}
			PersonWrapper person = new PersonWrapper(firstName, lastName, null, cmeta.getModel_author_org());
			processPerson(person, modelNode, annotationNode, DocumentRelTypes.IS_CREATOR);
		}
		if (cmeta.getCitation_authors()!=null){
			List<PersonWrapper> authorList = new LinkedList<PersonWrapper>();
			for (Iterator<List<String>> iterator = cmeta.getCitation_authors().iterator(); iterator.hasNext();) {
				List<String> pubAuthor = (List<String>) iterator.next();
				String firstName = null;
				String lastName = null;
				try {
					lastName = pubAuthor.get(0);
					firstName = pubAuthor.get(1);
				} catch (IndexOutOfBoundsException e) {
				
				}
				if (StringUtils.isEmpty(lastName)) continue;
				authorList.add(new PersonWrapper(firstName, lastName, null, null));
			}
			if (!StringUtils.isEmpty(cmeta.getCitation_title())){
				PublicationWrapper publication = new PublicationWrapper(cmeta.getCitation_title(), cmeta.getCitation_journal(), null, null, null, cmeta.getCitation_id(), authorList);
				processPublication(publication, annotationNode, modelNode);
			}
		}
		
		if (!StringUtils.isEmpty(cmeta.getCitation_id())){
			String res = cmeta.getCitation_id();
			Node resource = annotationIndex.get(Property.General.URI, res).getSingle();
			if (resource==null){
				resource = graphDB.createNode();
				resource.setProperty(Property.General.URI, res);
				resource.setProperty(Property.General.NODETYPE, Property.NodeType.RESOURCE);
				annotationIndex.add(resource, Property.General.URI, res);
			}
			//create a dynamic relationship based on the qualifier
			annotationNode.createRelationshipTo(resource, DynamicRelationshipType.withName("isDescribedBy"));
			resource.createRelationshipTo(annotationNode, DatabaseRelTypes.BELONGS_TO);
		}
		
	}

	private static void extractCellmlConnections(ConnectionSet connections,
			Map<String, Map<Node, Map<String, Node>>> componentNodes) {
		
		ConnectionIterator conIter = connections.iterateConnections();
		for (int i = 0; i < connections.getLength(); i++) {
			Connection connection = conIter.nextConnection();
			MapComponents mapComponent = connection.getComponentMapping();
			Node firstComponentNode = componentNodes.get(mapComponent.getFirstComponentName()).keySet().iterator().next();
			Node secondComponentNode = componentNodes.get(mapComponent.getSecondComponentName()).keySet().iterator().next();
			firstComponentNode.createRelationshipTo(secondComponentNode, CellmlRelTypes.IS_CONNECTED_TO);		  
			secondComponentNode.createRelationshipTo(firstComponentNode, CellmlRelTypes.IS_CONNECTED_TO);
						
			
			MapVariablesSet variableSet = connection.getVariableMappings();
			MapVariablesIterator varIter = variableSet.iterateMapVariables();
			for (int j = 0; j < variableSet.getLength(); j++) {
				MapVariables mapVar = varIter.nextMapVariable();
				Node firstVariableNode = componentNodes.get(mapComponent.getFirstComponentName()).get(firstComponentNode).get(mapVar.getFirstVariableName());
				Node secondVariableNode = componentNodes.get(mapComponent.getSecondComponentName()).get(secondComponentNode).get(mapVar.getSecondVariableName());
				firstVariableNode.createRelationshipTo(secondVariableNode, CellmlRelTypes.IS_MAPPED_TO);		  
				secondVariableNode.createRelationshipTo(firstVariableNode, CellmlRelTypes.IS_MAPPED_TO);
			}

		}
		commitAndReopenTransaction();
	}

	private static Map<String, Map<Node, Map<String, Node>>> extractCellmlComponents(
			CellMLComponentSet components, Node modelNode) {
		
		Map<String, Map<Node, Map<String, Node>>> componentNodes = new HashMap<String, Map<Node, Map<String, Node>>>();		
		CellMLComponentIterator iter = components.iterateComponents();
		for (int i = 0; i < components.getLength(); i++) {
			Node componentNode = graphDB.createNode();			
			componentNode.createRelationshipTo(modelNode, DatabaseRelTypes.BELONGS_TO);
			modelNode.createRelationshipTo(componentNode, CellmlRelTypes.HAS_COMPONENT);
			componentNode.setProperty(Property.General.NODETYPE, Property.NodeType.CELLML_COMPONENT);
			
			CellMLComponent comp = iter.nextComponent(); 
			componentNode.setProperty(Property.General.NAME, comp.getName());
			componentNode.setProperty(Property.General.ID, comp.getCmetaId());
			modelIndex.add(modelNode, Property.CellML.COMPONENT, comp.getName());
			modelIndex.add(modelNode, Property.CellML.COMPONENT, comp.getCmetaId());
			
			CellMLVariableSet variableSet = comp.getVariables();
			Map<String, Node> variableNodes = extractCellmlVariables(variableSet, componentNode, modelNode);			
			Map<Node, Map<String, Node>> compNodeToVariables = new HashMap<Node, Map<String,Node>>();
			compNodeToVariables.put(componentNode, variableNodes);
			componentNodes.put(comp.getName(), compNodeToVariables);
			extractCellmlReactions(comp.getReactions(), componentNode, variableNodes);
		}
		commitAndReopenTransaction();
		return componentNodes;
	}

	private static void extractCellmlReactions(ReactionSet reactions,
			Node componentNode, Map<String, Node> variableNodes) {
		ReactionIterator iter = reactions.iterateReactions();
		for (int i = 0; i < reactions.getLength(); i++) {
			Node reactionNode = graphDB.createNode();
			reactionNode.setProperty(Property.General.NODETYPE, Property.NodeType.CELLML_REACTION);
			reactionNode.createRelationshipTo(componentNode, CellmlRelTypes.HAS_REACTION);
			componentNode.createRelationshipTo(reactionNode, DatabaseRelTypes.BELONGS_TO);
			Reaction react = iter.nextReaction();
			reactionNode.setProperty( Property.CellML.REVERSIBLE, react.getReversible());
			VariableRefSet vrs = react.getVariableReferences();
			VariableRefIterator vrsit = vrs.iterateVariableRefs();
			for (int j = 0; j < vrs.getLength(); j++) {
				VariableRef vref = vrsit.nextVariableRef();
				RoleIterator roleIt = vref.getRoles().iterateRoles();
				for (int k = 0; k < vref.getRoles().getLength(); k++) {
					Role role = roleIt.nextRole();
					Node varNode = variableNodes.get(vref.getVariable().getName());					
					if (role.getDeltaVariable()!=null){
						Node deltaVarNode = variableNodes.get(role.getDeltaVariable().getName());
						deltaVarNode.createRelationshipTo(varNode, CellmlRelTypes.IS_DELTA_VAR);
						varNode.createRelationshipTo(deltaVarNode, CellmlRelTypes.HAS_DELTA_VAR);
					}					
				}
			}
		}
		
	}

	private static Map<String, Node> extractCellmlVariables(CellMLVariableSet variableSet,
			Node componentNode, Node modelNode) {
		Map<String, Node> variableNodes = new HashMap<String, Node>();
		
		CellMLVariableIterator varIter = variableSet.iterateVariables();
		for (int j = 0; j < variableSet.getLength(); j++) {
			Node variableNode = graphDB.createNode();
			variableNode.createRelationshipTo(componentNode, DatabaseRelTypes.BELONGS_TO);
			componentNode.createRelationshipTo(variableNode, CellmlRelTypes.HAS_VARIABLE);
			variableNode.setProperty(Property.General.NODETYPE, Property.NodeType.CELLML_VARIABLE);
						
			CellMLVariable variable = varIter.nextVariable();
			variableNode.setProperty(Property.General.NAME, variable.getName());
			variableNode.setProperty(Property.General.ID, variable.getCmetaId());
			modelIndex.add(modelNode, Property.CellML.VARIABLE, variable.getName());
			modelIndex.add(modelNode, Property.CellML.VARIABLE, variable.getCmetaId());
			variableNodes.put(variable.getName(), variableNode);			
		}
		commitAndReopenTransaction();
		return variableNodes;
		
	}
	
	private static CmetaContainer readCmeta(String linkToModel){
		
		int timeout = 10000;
		
		URL url;
		URLConnection connection;
		InputStream stream;
		String text = "";
		try {
			url = new URL(linkToModel + "/@@cmeta");
			connection = url.openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestProperty("Content-Type", "application/vnd.physiome.pmr2.json.0");
			connection.setRequestProperty("Accept", "application/vnd.physiome.pmr2.json.0");

			stream = connection.getInputStream(); 
			text = IOUtils.toString(stream);
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		Gson gson = new Gson();
		CmetaContainer cmeta = null;
		
		try {
			cmeta = gson.fromJson(text, CmetaContainer.class);
		} catch (Exception e) {
			cmeta = null;
		}		
		return cmeta;	
	}
	
	private static void commitAndReopenTransaction() {
		
		tx.success();
		tx.finish();
		tx = graphDB.beginTx();
		
	}
	
	
}
