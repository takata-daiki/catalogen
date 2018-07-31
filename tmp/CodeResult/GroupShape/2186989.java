/**
 * 
 */
package uk.ac.lkl.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import uk.ac.lkl.common.util.JREXMLUtilities;
import uk.ac.lkl.common.util.expression.Expression;
import uk.ac.lkl.common.util.expression.LocatedExpression;
import uk.ac.lkl.common.util.restlet.EntityMap;
import uk.ac.lkl.common.util.restlet.RestletException;
import uk.ac.lkl.common.util.restlet.XMLConversionContext;
import uk.ac.lkl.common.util.restlet.XMLConverterManager;
import uk.ac.lkl.common.util.value.Number;
import uk.ac.lkl.client.CommonUtils;
import uk.ac.lkl.client.rpc.ExpresserService;
import uk.ac.lkl.server.objectify.ActivityDocumentCompleted;
import uk.ac.lkl.server.objectify.ActivityDocumentXMLUpdateEvent;
import uk.ac.lkl.server.objectify.AnalysisIndicator;
import uk.ac.lkl.server.objectify.ColorSpecificRuleExpression;
import uk.ac.lkl.server.objectify.DAO;
import uk.ac.lkl.server.objectify.MovedEvent;
import uk.ac.lkl.server.objectify.NextTimeStamp;
import uk.ac.lkl.server.objectify.OpenSessionChannels;
import uk.ac.lkl.server.objectify.PartOfEvent;
import uk.ac.lkl.server.objectify.PreviousTimeStamp;
import uk.ac.lkl.server.objectify.ServerAnimationSettings;
import uk.ac.lkl.server.objectify.ServerLocatedExpression;
import uk.ac.lkl.server.objectify.ServerPattern;
import uk.ac.lkl.server.objectify.ServerProjectNames;
import uk.ac.lkl.server.objectify.ServerShape;
import uk.ac.lkl.server.objectify.ServerTiedNumber;
import uk.ac.lkl.server.objectify.ServerTile;
import uk.ac.lkl.server.objectify.ServerGroupShape;
import uk.ac.lkl.server.objectify.ServerTotalTilesExpression;
import uk.ac.lkl.server.objectify.TiedNumberDisplayModeEvent;
import uk.ac.lkl.server.objectify.TiedNumberLockedEvent;
import uk.ac.lkl.server.objectify.TiedNumberNameEvent;
import uk.ac.lkl.server.objectify.TiedNumberValueEvent;
import uk.ac.lkl.server.objectify.TimeStampLink;
import uk.ac.lkl.server.objectify.XMLUpdateEvent;
import uk.ac.lkl.migen.system.expresser.CommonFormatEventType;
import uk.ac.lkl.migen.system.expresser.KaleidoscopeCommonFormatLogger;
import uk.ac.lkl.migen.system.expresser.model.ColorResourceAttributeHandle;
import uk.ac.lkl.migen.system.expresser.model.ExpresserModel;
import uk.ac.lkl.migen.system.expresser.model.ExpresserModelImpl;
import uk.ac.lkl.migen.system.expresser.model.ExpressionValueSource;
import uk.ac.lkl.migen.system.expresser.model.ModelColor;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BlockShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.GroupShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.ModelGroupShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.PatternShape;
import uk.ac.lkl.migen.system.expresser.model.tiednumber.TiedNumberExpression;
import uk.ac.lkl.migen.system.server.converter.ConversionUtilities;
import uk.ac.lkl.migen.system.util.MiGenUtilities;
import uk.ac.lkl.migen.system.util.URLLocalFileCache;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.SendResponse.Status;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;
import com.google.apphosting.api.ApiProxy.ApiProxyException;
import com.google.apphosting.api.ApiProxy.OverQuotaException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Query;

/**
 * Implements the remote service for the web-based version of Expresser
 * 
 * @author Ken Kahn
 * 
 */

public class ExpresserServiceImpl extends RemoteServiceServlet implements ExpresserService {
    
    private static final XMPPService xmppService = XMPPServiceFactory.getXMPPService();

    private static final JID fromJID = new JID("Metafora@web-expresser.appspotchat.com");

    private boolean sentQuotaExceededMessage;
    
    public ExpresserServiceImpl() {
	super();
	if (!MiGenUtilities.isFactoryRepositoryInitialised()) {
	    MiGenUtilities.initialiseFactoryRepositoryForNonGwtClient();
	}
    }

    public String[] fetchModel(String contextKey, String modelName, String asOfTimeString) {
	return fetchModel(contextKey, null, null, 1, modelName, asOfTimeString, null, null, null, null);
    }

    @Override
    public String[] fetchModel(String contextKey, String contextId, String activityId, int activityNumber,
                               String modelName, String asOfTimeString,
	                       String userName, String contextName, String activityName, String userId) {
	if (CommonUtils.UNNAMED_MODEL.equals(modelName) || CommonUtils.UNNAMED_MODEL_ENCODED.equals(modelName)) {
	    // for backwards compatibility unnamed model has no name
	    modelName = null;
	}
	String[] result = new String[10];
	// 0: contextKey
	// 1: XML of the model at asOfTime
	// 2: XML of activity document events
	// 3: application version number
	// 4: model name 
	// 5: previous time stamp
	// 6: time stamp of most recent event <= asOfTime
	// 7: next time stamp
	// 8: completed? - true or false
	// 9: errors or warnings
	long asOfTime;
	// the following is only needed if asOfTimeString == null
	long currentTime = 0;
	DAO dao = ServerUtils.getDao();
	if (asOfTimeString == null) {
	    // no time stamp means the most recent state
	    asOfTime = DAO.DISTANT_FUTURE_TIME;
	} else {
	    try {
		asOfTime = Long.parseLong(asOfTimeString);
	    } catch (NumberFormatException e) {
		result[9] = ServerUtils.warn("Unable to parse time stamp as a Long: " + asOfTimeString);
		asOfTime = DAO.DISTANT_FUTURE_TIME;
	    }    
	    currentTime = asOfTime;
	}
	result[3] = SystemProperty.Environment.applicationVersion.get();
	String modelKey = ServerUtils.createModelKey(contextKey, modelName);
	if (contextKey == null) {
	    contextKey = ServerUtils.generateGUIDString(); 
	} else {
	    ExpresserModel model = new ExpresserModelImpl();   
	    try {    
		currentTime = constructModel(model, modelKey, asOfTime, currentTime);
	    } catch (ApiProxyException e) { 
		result[9] = handleApiProxyException(e);
	    }
	    returnModelXML(model, result);
	    ServerAnimationSettings animationSettings = dao.getAnimationSettings(modelKey, asOfTime);
	    if (animationSettings != null && result[1] != null) {
		String additionalXML = animationSettings.getXml();
		String combinedXML = 
			result[1].substring(0, result[1].length()-"</ExpresserModel>".length()) + additionalXML + "</ExpresserModel>";
		result[1] = combinedXML;
	    }
	}
	result[0] = contextKey;
	result[2] = dao.getActivityDocumentEvents(modelKey, asOfTime);
	String context = (contextId == null || contextId.isEmpty()) ? contextKey : contextId;
	ServerProjectNames modelDescriptionsOfContextObject = dao.getModelDescriptionsOfContext(context);
	ServerProjectNames modelDescriptionsOfUserIdObject = userId == null ? null : dao.getModelDescriptionsOfUserId(userId);
	String newModelDescriptionsOfContextAsString = "";
	String newModelDescriptionsOfUserIdAsString = "";
	boolean projectNameIsNew = true; // until proven otherwise below
	String[] modelDescriptionsOfContext = null;
	String[] modelDescriptionsOfUserId = null;
	if (userName == null && userId != null) {
	    int indexOfContext = userId.indexOf("." + context);
	    if (indexOfContext < 0) {
		userName = userId;
	    } else {
		userName = userId.substring(0, indexOfContext);
	    }
	}
	String modelDescription = 
		modelName == null ? CommonUtils.UNNAMED_MODEL : modelName
		+ ";" + userName + ";" + contextName + ";" + activityName + ";" + activityNumber;
	if (modelDescriptionsOfContextObject != null) {
	    modelDescriptionsOfContext =  modelDescriptionsOfContextObject.getProjectNames();
//	    String projectDescriptionStart = projectName + ";";
	    for (String oldModelDescription : modelDescriptionsOfContext) {
		if (oldModelDescription.startsWith(modelDescription)) {
		    // already know this project 
		    projectNameIsNew = false;
		} else {
		    newModelDescriptionsOfContextAsString += oldModelDescription + ",";
		}
	    }
	}
	if (modelDescriptionsOfUserIdObject != null) {
	    modelDescriptionsOfUserId =  modelDescriptionsOfUserIdObject.getProjectNames();
	    for (String oldModelDescription : modelDescriptionsOfUserId) {
		if (oldModelDescription.startsWith(modelDescription)) {
		    // already know this project 
		} else {
		    newModelDescriptionsOfUserIdAsString += oldModelDescription + ",";
		}
	    }
	}
	// add time stamp now since check for old project uses rest of projectDescription
	modelDescription += ";" + new Date().getTime();
	newModelDescriptionsOfContextAsString += modelDescription;
	newModelDescriptionsOfUserIdAsString += modelDescription;
	if (userId == null) {
	    result[4] = newModelDescriptionsOfContextAsString;
	} else {
	    result[4] = newModelDescriptionsOfUserIdAsString;
	}
	if (modelName != null) {
	    if (modelDescriptionsOfContextObject == null) {
		modelDescriptionsOfContext = new String[1];
		modelDescriptionsOfContext[0] = modelDescription;
		modelDescriptionsOfContextObject = new ServerProjectNames(context, modelDescriptionsOfContext);
//		result[3] = projectDescription;
	    } else {
		if (projectNameIsNew) {
		    String[] newProjectNames = new String[modelDescriptionsOfContext.length+1];
		    int index = 0;
		    for (String oldModelDescription : modelDescriptionsOfContext) {
			newProjectNames[index] = oldModelDescription;
			index++;
		    }
		    newProjectNames[index] = modelDescription;
		    modelDescriptionsOfContextObject.setProjectNames(newProjectNames);
		}	
	    }
	    dao.ofy().put(modelDescriptionsOfContextObject);
	    if (modelDescriptionsOfUserIdObject == null) {
		modelDescriptionsOfUserId = new String[1];
		modelDescriptionsOfUserId[0] = modelDescription;
		modelDescriptionsOfUserIdObject = new ServerProjectNames(ServerUtils.tagUserId(userId), modelDescriptionsOfUserId);
//		result[3] = projectDescription;
	    } else {
		if (projectNameIsNew) {
		    String[] newProjectNames = new String[modelDescriptionsOfUserId.length+1];
		    int index = 0;
		    for (String oldModelDescription : modelDescriptionsOfUserId) {
			newProjectNames[index] = oldModelDescription;
			index++;
		    }
		    newProjectNames[index] = modelDescription;
		    modelDescriptionsOfUserIdObject.setProjectNames(newProjectNames);
		}	
	    }
	    dao.ofy().put(modelDescriptionsOfUserIdObject);
	}
	if (asOfTimeString == null) {
	    asOfTimeString = Long.toString(currentTime);
	}
	result[5] = getPreviousTimeStamp(asOfTimeString, contextKey, modelName);
	result[6] = Long.toString(currentTime);
	result[7] = getNextTimeStamp(asOfTimeString, contextKey, modelName);
	result[8] = Boolean.toString(ServerUtils.completedActivity(contextKey, modelName));
	return result;
    }
    
    public ExpresserModel constructModel(String contextKey, String projectName) {
	ExpresserModel model = new ExpresserModelImpl();
	String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	constructModel(model, modelKey, DAO.DISTANT_FUTURE_TIME, 0);
	return model;
    }

    public long constructModel(ExpresserModel model, String modelKey, long asOfTime, long currentTime) {
	DAO dao = ServerUtils.getDao();
	HashMap<String, TiedNumberExpression<Number>> idToTiedNumberMap = 
		new HashMap<String, TiedNumberExpression<Number>>();
	Query<ServerTile> tiles = dao.getTiles(modelKey, asOfTime);
	for (ServerTile tile : tiles) {
	    if (addShapeToModel(tile, asOfTime, model, idToTiedNumberMap, dao) && tile.getTimeStamp() > currentTime) {
		currentTime = tile.getTimeStamp();
	    }
	}
	Query<ServerGroupShape> groupShapes = dao.getGroupShapes(modelKey, asOfTime);
	for (ServerGroupShape groupShape : groupShapes) {
	    if (addShapeToModel(groupShape, asOfTime, model, idToTiedNumberMap, dao) && 
		groupShape.getTimeStamp() > currentTime) {
		currentTime = groupShape.getTimeStamp();
	    }
	}
	Query<ServerPattern> patterns = dao.getPatterns(modelKey, asOfTime);
	for (ServerPattern pattern : patterns) {
	    XMLUpdateEvent xmlUpdateEvent = dao.getXMLUpdateEvent(pattern.getId(), asOfTime);
	    if (xmlUpdateEvent != null) {
		pattern.setXml(xmlUpdateEvent.getXml());
		if (xmlUpdateEvent.getTimeStamp() > currentTime) {
		    currentTime = pattern.getTimeStamp();
		}
	    }
	    if (addShapeToModel(pattern, asOfTime, model, idToTiedNumberMap, dao) && 
		pattern.getTimeStamp() > currentTime) {
		currentTime = pattern.getTimeStamp();
	    }
	}
	Query<ServerLocatedExpression> locatedExpressions = dao.getLocatedExpressions(modelKey, asOfTime);
	for (ServerLocatedExpression locatedExpression : locatedExpressions) {
	    // if asOfTime == null then getTopLevelLocatedExpressions has already ensured it has no parentId
	    String id = locatedExpression.getId();
	    if (dao.topLevelPartOfModel(locatedExpression, asOfTime)) {
		XMLUpdateEvent xmlUpdateEvent = dao.getXMLUpdateEvent(id, asOfTime);
		if (xmlUpdateEvent != null) {
		    locatedExpression.setXml(xmlUpdateEvent.getXml());
		}
		LocatedExpression<Number> locatedModelExpression = 
			locatedExpression.getLocatedExpression(idToTiedNumberMap);
		if (locatedModelExpression != null) {
		    MovedEvent movedEvent = dao.getMovedEvent(id, asOfTime);
		    if (movedEvent != null) {
			locatedModelExpression.setX(movedEvent.getX());
			locatedModelExpression.setY(movedEvent.getY());
		    }
		    model.addLocatedExpression(locatedModelExpression);
		}
		if (locatedExpression.getTimeStamp() > currentTime) {
		    currentTime = locatedExpression.getTimeStamp();
		}
	    }
	}
	ServerTotalTilesExpression serverTotalTilesExpression = dao.getTotalTilesExpression(modelKey, asOfTime);
	if (serverTotalTilesExpression != null) {
	    Expression<Number> totalTilesExpression = serverTotalTilesExpression.getExpression(idToTiedNumberMap);
	    if (totalTilesExpression != null) {
		model.setTotalAllocationExpression(totalTilesExpression);
	    }
	    if (serverTotalTilesExpression.getTimeStamp() > currentTime) {
		currentTime = serverTotalTilesExpression.getTimeStamp();
	    }
	}
	//	    Query<ColorSpecificRuleExpression> allColorSpecificRuleExpressions = 
	//		dao.getAllColorSpecificRuleExpressions(modelKey, asOfTime);
	List<ModelColor> colors = model.getPalette().getColors();
	ModelGroupShape modelAsAGroup = model.getModelAsAGroup();
	for (ModelColor color : colors) {
	    String colorName = color.toHTMLString();
	    ColorSpecificRuleExpression rule = dao.getColorSpecificRuleExpression(modelKey, colorName, asOfTime);
	    if (rule != null) {
		ColorResourceAttributeHandle handle = BlockShape.colorResourceAttributeHandle(color);
		modelAsAGroup.addAttribute(handle, new ExpressionValueSource<Number>(rule.getExpression(idToTiedNumberMap)));
		if (rule.getTimeStamp() > currentTime) {
		    currentTime = rule.getTimeStamp();
		}
	    }
	}
	List<TiedNumberExpression<Number>> allTiedNumbers = model.getAllTiedNumbers();
	for (TiedNumberExpression<Number> tiedNumber : allTiedNumbers) {
	    dao.updateTiedNumber(tiedNumber, asOfTime);
	}
	return currentTime;
    }

    /**
     * @param shape
     * @param asOfTime
     * @param model
     * @param idToTiedNumberMap 
     * @param dao
     * 
     * @return true if top-level shape that is added to model
     */
    public boolean addShapeToModel(
	    ServerShape shape, 
	    Long asOfTime, 
	    ExpresserModel model, 
	    HashMap<String, TiedNumberExpression<Number>> idToTiedNumberMap, 
	    DAO dao) {
	// if asOfTime == null then getTopLevelTiles has already ensured it has no parentId
	if (dao.topLevelPartOfModel(shape, asOfTime)) {
	    if (asOfTime != null) {
		MovedEvent movedEvent = dao.getMovedEvent(shape.getId(), asOfTime);
		if (movedEvent != null) {
		    shape.setX(movedEvent.getX());
		    shape.setY(movedEvent.getY());
		}
	    }
	    BlockShape patternShape = shape.getPatternShape(asOfTime, idToTiedNumberMap);
	    if (patternShape != null) {
		// to address Issue 1699
		if (patternShape instanceof GroupShape && ((GroupShape) patternShape).getShapeCount() == 0) {
		    return false;
		}
		model.addObject(patternShape);
	    } else {
		return false;
	    }
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * @param model
     * @param result
     */
    public void returnModelXML(ExpresserModel model, String[] result) {
	try {
	    XMLConverterManager converterManager = ConversionUtilities.createConverterManager();
	    XMLConversionContext context = new XMLConversionContext(converterManager, new EntityMap());
	    Document document = JREXMLUtilities.createDocument();
	    Element expresserModelElement = context.convertToXML(document, ExpresserModel.class, model);
	    document.appendChild(expresserModelElement);
	    OutputStream outputStream = new ByteArrayOutputStream();
	    // Use a Transformer for output
	    // see http://download.oracle.com/javaee/1.4/tutorial/doc/JAXPXSLT4.html
	    System.setProperty(
		    "javax.xml.transform.TransformerFactory", 
	            "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();
	    DOMSource source = new DOMSource(document);
	    StreamResult streamResult = new StreamResult(outputStream);
	    transformer.transform(source, streamResult); 
	    result[1] = outputStream.toString();
	} catch (RestletException e) {
	    result[4] = e.getMessage();
	    e.printStackTrace();
	} catch (ParserConfigurationException e) {
	    result[4] = e.getMessage();
	    e.printStackTrace();
	} catch (TransformerConfigurationException e) {
	    result[4] = e.getMessage();
	    e.printStackTrace();
	} catch (TransformerException e) {
	    result[4] = e.getMessage();
	    e.printStackTrace();
	}
    }

    @Override
    public String[] tileCreatedOrUpdated(
	    int x, int y, String colorName, String tileId, String parentId, String contextKey, 
	    String userName, String projectName, String attributeNameValues[], boolean metaforaTestServer, String logRecipients, 
	    String previousTimeStamp, int undoCount) {
//	System.out.println("tileCreatedOrUpdated id=" + tileId + "; parentId=" + parentId);
	String[] result = new String[2];
	if (tileId == null) {
	    String error = "No id given for tile.";
	    ServerUtils.severe(error);
	    result[1] = error;
	    return result;
	}
	try {
	    DAO dao = ServerUtils.getDao();
	    ServerTile tile = dao.getTile(tileId);
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    if (tile == null) {
		tile = new ServerTile(tileId, x, y, colorName, modelKey);
		dao.persistObject(tile);
		result[0] = tile.getTimeStampString();
		if (logRecipients != null) {
		    HashMap<String, TiedNumberExpression<Number>> idToTiedNumberMap = 
			    new HashMap<String, TiedNumberExpression<Number>>();
		    logShapeCreationOrUpdate(CommonFormatEventType.CREATION, tile.getPatternShape(null, idToTiedNumberMap), userName, attributeNameValues, metaforaTestServer, logRecipients);
		}
	    } else {
		MovedEvent moveEvent = tile.move(x, y);
		result[0] = moveEvent.getTimeStampString();
	    }
	    if (parentId != null) {
		dao.partOfEvent(tileId, parentId, modelKey, tile.getTimeStamp());
		//	    System.out.println("partOfEvent " + tileId + " " + parentId + " " + modelKey + " " + tile.getTimeStamp());
	    }
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }

    private void linkTimeStamps(String previousTimeStamp, int undoCount, String currentTimeStamp, String modelKey) {
	if (currentTimeStamp == null) {
	    return; // nothing changed
	}
	if (previousTimeStamp == null) {
	    // an intermediate step -- not one that undo/redo should go to
	    return;
	}
	DAO dao = ServerUtils.getDao();
	String previousUniqueTimeStamp = ServerUtils.createUniqueTimeStamp(modelKey, previousTimeStamp);
	try {
	    NextTimeStamp preexistingForwardLink = dao.ofy().get(NextTimeStamp.class, previousUniqueTimeStamp);
	    String currentUniqueTimeStamp = ServerUtils.createUniqueTimeStamp(modelKey, currentTimeStamp);
	    if (undoCount > 0) {
		// the user made changes after some undo's
		dao.invalidateTimeStampsAfter(previousTimeStamp, currentTimeStamp, modelKey);
		String invalidTimeStamp = preexistingForwardLink.getNextTimeStampAndProjectKey();
		preexistingForwardLink.setNextTimeStampAndProjectKey(currentUniqueTimeStamp);
		dao.persistObject(preexistingForwardLink);
		PreviousTimeStamp preexistingBackwardsLink = dao.ofy().find(PreviousTimeStamp.class, invalidTimeStamp);
		if (preexistingBackwardsLink != null) {
		    preexistingBackwardsLink.setPreviousTimeStampAndProjectKey(previousUniqueTimeStamp);
		    dao.persistObject(preexistingBackwardsLink);
		}
		informOpenChannels(modelKey, "reloadModel");
	    } else {
		// insert a new link (perhaps collaboratively working on a model)
		String preexistingNextTimeStamp = preexistingForwardLink.getNextTimeStampAndProjectKey();	
		if (currentTimeStamp.compareTo(preexistingNextTimeStamp) > 0) {
		    // the currentTimeStamp should be added to the end of the pre-existing link
		    String preexistingNextTime = ServerUtils.extractTimeStamp(preexistingNextTimeStamp);
		    if (preexistingNextTime.compareTo(previousTimeStamp) > 0) {
			// rewritten to iterate until the end of the time stamp chain
			// checking that 'progress' is made each time
			addToEndOfTimeStampChain(preexistingNextTime, currentTimeStamp, modelKey);
			// logs of version 275 had infinite recursions here
			// so conditional should prevent them
			// infinite recursion happened again -- see https://code.google.com/p/migen/issues/detail?id=1658
//			ServerUtils.warn("Recurring in linkTimeStamps. preexistingNextTime="
//			                 + preexistingNextTime + 
//			                 "; previousTimeStamp=" + previousTimeStamp +
//			                 "; currentTimeStamp=" + currentTimeStamp);
//			linkTimeStamps(preexistingNextTime, undoCount, currentTimeStamp, modelKey);
		    }
		    return;
		} else {
		    // either the pre-existing link skips the currentTimeStamp so insert it in the 'middle'
		    PreviousTimeStamp preexistingBackwardsLink = dao.ofy().find(PreviousTimeStamp.class, preexistingNextTimeStamp);
		    if (preexistingBackwardsLink != null) {
			preexistingBackwardsLink.setPreviousTimeStampAndProjectKey(previousUniqueTimeStamp);
			dao.persistObject(preexistingBackwardsLink);
		    }
		    addTimeStampLinks(currentTimeStamp, previousTimeStamp, modelKey, dao);
		    informOpenChannels(modelKey, "reloadModel");
		}
	    }  
	} catch (NotFoundException e) {
	    addTimeStampLinks(previousTimeStamp, currentTimeStamp, modelKey, dao);
	    informOpenChannels(modelKey, "reloadModel");
	}
    }

    private void addToEndOfTimeStampChain(String earlierTimeStamp, String currentTimeStamp, String modelKey) {
	// repeatedly follows forward links from earlierTimeStamp
	// until end of chain found and then link to from end of chain to currentTimeStamp added
	DAO dao = ServerUtils.getDao();
	try {
	    while (true) {
		NextTimeStamp preexistingForwardLink = dao.ofy().get(NextTimeStamp.class, earlierTimeStamp);
		String nextTimeStamp = preexistingForwardLink.getNextTimeStamp();
		if (nextTimeStamp.compareTo(earlierTimeStamp) > 0) {
		    preexistingForwardLink = dao.ofy().get(NextTimeStamp.class, nextTimeStamp);
		    nextTimeStamp = preexistingForwardLink.getNextTimeStamp();
		    if (nextTimeStamp == null) {
			break;
		    } else {
			earlierTimeStamp = nextTimeStamp;
		    }
		} else {
		    ServerUtils.warn("Time stamp links out of order: " + nextTimeStamp + " and " + earlierTimeStamp);
		    break;
		}
	    }
	} catch (NotFoundException e) {
	    addTimeStampLinks(earlierTimeStamp, currentTimeStamp, modelKey, dao);
	    informOpenChannels(modelKey, "reloadModel");
	}
    }

    protected void addTimeStampLinks(String previousTimeStamp, String currentTimeStamp, String modelKey, DAO dao) {
	TimeStampLink nextTimeStampLink = new NextTimeStamp(modelKey, previousTimeStamp, currentTimeStamp);
	dao.persistObject(nextTimeStampLink);
	TimeStampLink previousTimeStampLink = new PreviousTimeStamp(modelKey, previousTimeStamp, currentTimeStamp);
	dao.persistObject(previousTimeStampLink);
    }

    public String getNextTimeStamp(String currentTimeStamp, String contextKey, String projectName) {
	DAO dao = ServerUtils.getDao();
	String previousTimeStampAndProjectKey = 
	    ServerUtils.createUniqueTimeStamp(ServerUtils.createModelKey(contextKey, projectName), currentTimeStamp);
	NextTimeStamp nextTimeStampLink = dao.ofy().find(NextTimeStamp.class, previousTimeStampAndProjectKey);
	if (nextTimeStampLink == null) {
	    return null;
	} else {
	    return nextTimeStampLink.getNextTimeStamp();
	}
    }
    
    public String getPreviousTimeStamp(String currentTimeStamp, String contextKey, String projectName) {
	DAO dao = ServerUtils.getDao();
	String currentStampAndProjectKey = 
	    ServerUtils.createUniqueTimeStamp(ServerUtils.createModelKey(contextKey, projectName), currentTimeStamp);
	TimeStampLink previousTimeStampLink = dao.ofy().find(PreviousTimeStamp.class, currentStampAndProjectKey);
	if (previousTimeStampLink == null) {
	    return null;
	} else {
	    return previousTimeStampLink.getPreviousTimeStamp();
	}
    }

    @Override
    public String[] shapeMoved(int x, int y, String id, String contextKey, String projectName, String previousTimeStamp, int undoCount) {
	DAO dao = ServerUtils.getDao();
	String result[] = new String[2];
	ServerShape shape = dao.getShape(id);
	if (shape == null) {
	    String warning = ServerUtils.warn("A shape moved but no shape with id " + id + " found.");
	    // probably message to create shape not yet processed
	    result[1] = CommonUtils.RESEND_TO_SERVER_AFTER_DELAY_TOKEN + warning;
	    return result;
	}
	try {
	    MovedEvent moveEvent = shape.move(x, y);
	    result[0] = moveEvent.getTimeStampString();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    @Override
    public String[] shapeDeleted(String id, String contextKey, String userName, String projectName, String attributeNameValues[], boolean metaforaTestServer, String logRecipients, String previousTimeStamp, int undoCount) {
	// could check that it is "owned" by contextKey
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    ServerShape serverShape = dao.getShape(id);
	    Long timeStamp = serverShape == null ? null : dao.shapeDeleted(id, modelKey);
	    if (timeStamp != null) {
		result[0] = Long.toString(timeStamp);
		linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
		if (logRecipients != null && serverShape != null) {
		    HashMap<String, TiedNumberExpression<Number>> idToTiedNumberMap = 
			    new HashMap<String, TiedNumberExpression<Number>>();
		    logShapeCreationOrUpdate(CommonFormatEventType.DELETION, serverShape.getPatternShape(null, idToTiedNumberMap), userName, attributeNameValues, metaforaTestServer, logRecipients);
		}
	    } else {
		String warning = ServerUtils.warn("Shape deleted but did not find a shape with the id " + id);
		// probably message to create shape not yet processed
		result[1] = CommonUtils.RESEND_TO_SERVER_AFTER_DELAY_TOKEN + warning;
	    }
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    @Override
    public String[] shapeUnmade(
	    String id, boolean isPattern, String attributeNameValues[], String logRecipients, String contextKey, String userName, boolean metaforaTestServer, String projectName, String previousTimeStamp, int undoCount) {
	// could check that it is "owned" by contextKey
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    ServerShape serverShape = dao.getShape(id);
	    if (serverShape == null) {
		String warning = ServerUtils.warn("Warning. While recording the undoing of making a building block did not find a shape with the id " + id + ". Will retry.");
		// Assume that the message about the creation of the building block hasn't been processed yet so force this one to be resent
		result[1] = CommonUtils.RESEND_TO_SERVER_AFTER_DELAY_TOKEN + warning;
		return result;
	    }
	    HashMap<String, TiedNumberExpression<Number>> idToTiedNumberMap = 
		    new HashMap<String, TiedNumberExpression<Number>>();
	    PatternShape patternShape = 
		    serverShape.getPatternShape(null, idToTiedNumberMap);
	    BlockShape buildingBlock = patternShape.getShape();
	    ArrayList<BlockShape> shapesRestoredToTopLevel = new ArrayList<BlockShape>();
	    if (isPattern) {
		shapesRestoredToTopLevel.add(buildingBlock);
	    } else {
		if (buildingBlock instanceof GroupShape) {
		    GroupShape groupShape = (GroupShape) buildingBlock;
		    int subShapeCount = groupShape.getShapeCount();
		    for (int i = 0; i < subShapeCount; i++) {
			shapesRestoredToTopLevel.add(groupShape.getShape(i));
		    }
		} else if (buildingBlock instanceof PatternShape) {
		    shapesRestoredToTopLevel.add(buildingBlock);
		}
	    }
	    for (BlockShape restoredShape : shapesRestoredToTopLevel) {
		dao.persistObject(new PartOfEvent(restoredShape.getUniqueId(), null, modelKey));
	    }
	    Long timeStamp = dao.shapeDeleted(id, modelKey);
	    result[0] = Long.toString(timeStamp);
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	    if (logRecipients != null && serverShape != null) {
		logShapeCreationOrUpdate(CommonFormatEventType.DELETION, patternShape, userName, attributeNameValues, metaforaTestServer, logRecipients);
		for (BlockShape restoredShape : shapesRestoredToTopLevel) {
		    logShapeCreationOrUpdate(CommonFormatEventType.UPDATE, restoredShape, userName, attributeNameValues, metaforaTestServer, logRecipients);
		}
	    }
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    @Override
    public String[] expressionDeleted(String id, boolean errorIfNonExistent, String contextKey, String projectName, String previousTimeStamp, int undoCount) {
	// could check that it is "owned" by contextKey
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    Long timeStamp = dao.expressionDeleted(id, modelKey);
	    if (timeStamp != null) {
		result[0] = Long.toString(timeStamp);
		linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	    } else if (errorIfNonExistent) {
		// message creating the expression may not have been received yet
		result[1] = CommonUtils.RESEND_TO_SERVER_AFTER_DELAY_TOKEN 
			    + "Warning. Did not find a located expression with the id " + id + " in order to delete it."
			    + " Perhaps the message creating this expression has yet to arrive or this expression has already been deleted.";
	    } else {
		// null will be ignored by the client -- this time stamp is for a 'non-event'
		return null; // result[0] = ServerUtils.currentTimeStampString();
	    }
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
 
    @Override
    public String[] groupCreated(String id, int x, int y, ArrayList<String> subShapeIds, String attributeNameValues[], boolean metaforaTestServer, String logRecipients, String contextKey, String userName, String projectName, String previousTimeStamp, int undoCount) {
//	System.out.println("groupCreated id=" + id + "; subShapeIds=" + subShapeIds);
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    ServerGroupShape groupShape = new ServerGroupShape(x, y, id, subShapeIds, modelKey);
	    dao.persistObject(groupShape);
	    result[0] = groupShape.getTimeStampString();
	    if (logRecipients != null) {
		HashMap<String, TiedNumberExpression<Number>> idToTiedNumberMap = 
			new HashMap<String, TiedNumberExpression<Number>>();
		logShapeCreationOrUpdate(CommonFormatEventType.CREATION, groupShape.getPatternShape(null, idToTiedNumberMap), userName, attributeNameValues, metaforaTestServer, logRecipients);
	    }
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    @Override
    public String[] patternCreatedOrUpdated(
	    String id, String buildingBlockId, int modelX, int modelY, String xml, String attributeNameValues[], boolean metaforaTestServer, String logRecipients, String contextKey, String userName, String projectName, String previousTimeStamp, int undoCount) {
//	System.out.println("patternCreatedOrUpdated id=" + id + "; buildingBlockId=" + buildingBlockId);
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    if (id == null) {
		String warning = ServerUtils.warn("No id provided for pattern with building block id " + buildingBlockId);
		// message making the building block may not have been processed yet
		result[1] = CommonUtils.RESEND_TO_SERVER_AFTER_DELAY_TOKEN + warning;
		return result;
	    }
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    ServerPattern pattern = dao.getPattern(id);
	    if (pattern == null) {
		// new pattern
		pattern = new ServerPattern(modelX, modelY, id, xml, modelKey);
		dao.persistObject(pattern);
		result[0] = pattern.getTimeStampString();
		if (logRecipients != null) {
		    HashMap<String, TiedNumberExpression<Number>> idToTiedNumberMap = 
			    new HashMap<String, TiedNumberExpression<Number>>();
		    logShapeCreationOrUpdate(CommonFormatEventType.CREATION, pattern.getPatternShape(null, idToTiedNumberMap), userName, attributeNameValues, metaforaTestServer, logRecipients);
		}
	    } else {
		if (!pattern.getXml().equals(xml)) {
		    XMLUpdateEvent xmlUpdateEvent = pattern.storeXMLUpdateEvent(xml, modelKey);
		    result[0] = xmlUpdateEvent.getTimeStampString();
		    if (logRecipients != null) {
			HashMap<String, TiedNumberExpression<Number>> idToTiedNumberMap = 
				new HashMap<String, TiedNumberExpression<Number>>();
			logShapeCreationOrUpdate(CommonFormatEventType.UPDATE, pattern.getPatternShape(null, idToTiedNumberMap), userName, attributeNameValues, metaforaTestServer, logRecipients);
		    }
		}
		if (pattern.getX() != modelX || pattern.getY() != modelY) {
		    MovedEvent moveEvent = pattern.move(modelX, modelY);
		    result[0] = moveEvent.getTimeStampString();
		}
	    }
	    if (buildingBlockId != null) {
		long timeStamp;
		try {
		    timeStamp = Long.parseLong(result[0]);
		} catch (NumberFormatException e) {
		    ServerUtils.warn("Timestamp not parseable in call to patternCreatedOrUpdated: " +
	                             "id=" + id + 
	                             ";buildingBlockId  =" + buildingBlockId +
	                             ";modelX=" + modelX +
	                             ";modelY=" + modelY +
	                             ";xml=" + xml +
	                             ";contextKey=" + contextKey +
	                             ";userName=" + userName +
	                             ";projectName=" + projectName + 
	                             ";previousTimeStamp=" + previousTimeStamp);
		    timeStamp = ServerUtils.currentTimeStamp();
		}
		String parentId = dao.getParentId(buildingBlockId, timeStamp);
		if (parentId == null || !parentId.equals(id)) {
		    // no need to clutter the data base up with duplicate records
		    result[0] = dao.partOfEvent(buildingBlockId, id, modelKey, pattern.getTimeStamp());
		}
	    } else {
		result[1] = ServerUtils.severe("No buildingBlockId passed to patternCreatedOrUpdated.");
	    }
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    @Override
    public String[] expressionCreatedOrUpdated(
	    String id, int x, int y, String xml, String description, String contextKey, String userName, String projectName, String attributeNameValues[], boolean metaforaTestServer, String logRecipients, String previousTimeStamp, int undoCount) {
	String result[] = new String[2];	
	if (id == null) {
	    result[1] = "No id given for expression.";
	    return result;
	}
	try {
	    DAO dao = ServerUtils.getDao();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    ServerLocatedExpression locatedExpression = dao.getLocatedExpression(id);
	    if (locatedExpression == null) {
		locatedExpression = new ServerLocatedExpression(x, y, id, xml, modelKey);
		result[0] = locatedExpression.getTimeStampString();
		dao.persistObject(locatedExpression);
		if (logRecipients != null) {
		    logExpressionCreationOrUpdate(true, id, description, userName, metaforaTestServer, logRecipients);
		}
	    } else {
		if (!locatedExpression.getXml().equals(xml)) {
		    XMLUpdateEvent xmlUpdateEvent = locatedExpression.storeXMLUpdateEvent(xml, modelKey);
		    result[0] = xmlUpdateEvent.getTimeStampString();
		    if (logRecipients != null) {
			logExpressionCreationOrUpdate(false, id, description, userName, metaforaTestServer, logRecipients);
		    }
		}
		if (locatedExpression.getX() != x || locatedExpression.getY() != y) {
		    MovedEvent moveEvent = locatedExpression.move(x, y);
		    result[0] = moveEvent.getTimeStampString();
		}
	    }
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	} catch (Exception e) {
	    ServerUtils.severe("expressionCreatedOrUpdated raised an exception: " + e.getMessage());
	    e.printStackTrace();
	}
	return result;
    }

    @Override
    public String[] expressionMoved(int x, int y, String id, String contextKey, String projectName, String previousTimeStamp, int undoCount) {
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    ServerLocatedExpression locatedExpression = dao.getLocatedExpression(id);
	    if (locatedExpression == null) {
		String warning = ServerUtils.warn("No located expression with id " + id + " found.");
		// probably message to create expression not yet processed
		result[1] = CommonUtils.RESEND_TO_SERVER_AFTER_DELAY_TOKEN + warning;
		return result;
	    }
	    MovedEvent moveEvent = locatedExpression.move(x, y);
	    result[0] = moveEvent.getTimeStampString();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result; 
    }

    @Override
    public String[] updateTiedNumber(String id, int value, String name, boolean named, int displayMode, boolean locked, boolean keyAvailable,
	                             String description, String attributeNameValues[], String userName, boolean metaforaTestServer, String logRecipients, String contextKey, String projectName, String previousTimeStampString, int undoCount) {
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    ServerTiedNumber tiedNumber = dao.getTiedNumber(id);
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    if (tiedNumber == null) {
		tiedNumber = new ServerTiedNumber(id, value, name, displayMode, locked, keyAvailable);
		dao.persistObject(tiedNumber);
		result[0] = tiedNumber.getTimeStampString();
		if (logRecipients != null) {
		    logExpressionCreationOrUpdate(true, id, description, userName, metaforaTestServer, logRecipients);
		}
	    } else {
		long previousTimeStamp;
		if (previousTimeStampString == null) {
		    previousTimeStamp = DAO.DISTANT_FUTURE_TIME;
		} else {
		    previousTimeStamp = Long.parseLong(previousTimeStampString);
		}
		Integer mostRecentTiedNumberValue = dao.getMostRecentTiedNumberValue(id, previousTimeStamp);
		if (mostRecentTiedNumberValue == null) {
		    mostRecentTiedNumberValue = tiedNumber.getValue();
		}
		if (mostRecentTiedNumberValue != value) {
		    TiedNumberValueEvent tiedNumberValueEvent = new TiedNumberValueEvent(id, value, modelKey);
		    dao.persistObject(tiedNumberValueEvent);
		    result[0] = tiedNumberValueEvent.getTimeStampString();
		}
		String mostRecentTiedNumberName = dao.getMostRecentTiedNumberName(id, previousTimeStamp);
		if (mostRecentTiedNumberName == null) {
		    mostRecentTiedNumberName = tiedNumber.getName();
		}
		if (name != null && !name.equals(mostRecentTiedNumberName)) {
		    TiedNumberNameEvent tiedNumberNameEvent = new TiedNumberNameEvent(id, name, modelKey);
		    dao.persistObject(tiedNumberNameEvent);
		    result[0] = tiedNumberNameEvent.getTimeStampString();
		}
		Integer mostRecentTiedNumberDisplayMode = dao.getMostRecentTiedNumberDisplayMode(id, previousTimeStamp);
		if (mostRecentTiedNumberDisplayMode == null) {
		    mostRecentTiedNumberDisplayMode = tiedNumber.getDisplayMode();
		}
		if (mostRecentTiedNumberDisplayMode != displayMode) {
		    TiedNumberDisplayModeEvent tiedNumberDisplayModeEvent = new TiedNumberDisplayModeEvent(id, displayMode, modelKey);
		    dao.persistObject(tiedNumberDisplayModeEvent);
		    result[0] = tiedNumberDisplayModeEvent.getTimeStampString();
		}
		Boolean mostRecentTiedNumberLocked = dao.getMostRecentTiedNumberLocked(id, previousTimeStamp);
		if (mostRecentTiedNumberLocked == null) {
		    mostRecentTiedNumberLocked = tiedNumber.isLocked();
		}
		if (mostRecentTiedNumberLocked != locked) {
		    TiedNumberLockedEvent tiedNumberLockedEvent = new TiedNumberLockedEvent(id, locked, modelKey);
		    dao.persistObject(tiedNumberLockedEvent);
		    result[0] = tiedNumberLockedEvent.getTimeStampString();
		}
		if (logRecipients != null) {
		    logExpressionCreationOrUpdate(false, id, description, userName, metaforaTestServer, logRecipients);
		}
	    }
	    linkTimeStamps(previousTimeStampString, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }

    @Override
    public String[] updateTotalTilesExpression(String xml, String contextKey, String projectName, String previousTimeStamp, int undoCount) {
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    ServerTotalTilesExpression serverTotalTilesExpression = new ServerTotalTilesExpression(modelKey, xml);
	    dao.persistObject(serverTotalTilesExpression);
	    result[0] = serverTotalTilesExpression.getTimeStampString();
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    @Override
    public String[] updateColorSpecificRuleExpression(String xml, String colorName, String contextKey, String projectName, String previousTimeStamp, int undoCount) {
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();    
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    ColorSpecificRuleExpression serverColorSpecificRuleExpression = new ColorSpecificRuleExpression(modelKey, colorName, xml);
	    dao.persistObject(serverColorSpecificRuleExpression);
	    result[0] = serverColorSpecificRuleExpression.getTimeStampString();
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    @Override
    public String sendMessageToMetafora(String messageBody, String recipients) {
	if (recipients == null) {
	    return null;
	}
	try {
	    String[] recipientsArray = recipients.split(",");
	    JID[] jids = new JID[recipientsArray.length];
	    int index = 0;
	    for (String recipient : recipientsArray) {
		jids[index] = new JID(recipient.trim());
		index++;
	    }
	    Message message = 
		    new MessageBuilder().withRecipientJids(jids).withBody(messageBody).withFromJid(fromJID).build();   
	    SendResponse response = xmppService.sendMessage(message);
	    Status statusCode = response.getStatusMap().get(jids[0]);
	    boolean messageSent = (statusCode == SendResponse.Status.SUCCESS);
	    //        if (metaforaXMPP.getPresence(metaforaUser).isAvailable()) {
	    //            SendResponse status = metaforaXMPP.sendMessage(message);
	    //            messageSent = (status.getStatusMap().get(metaforaUser) == SendResponse.Status.SUCCESS);
	    //        }
	    if (messageSent) {
		return null;
	    } else {
		String error = "Failed to send message. Status: " + statusCode.toString() + ". Message: " + messageBody;
		ServerUtils.severe(error);
		return error;
	    }
	} catch (ApiProxyException e) { 
	    return handleApiProxyException(e);
	}
    }
    
    private void logShapeCreationOrUpdate(CommonFormatEventType eventType, BlockShape shape, String userName, String[] attributeNameValues, boolean metaforaTestServer, String logRecipients) {
	KaleidoscopeCommonFormatLogger logger = new KaleidoscopeCommonFormatMetaforaLogger(userName);
	Element actionElement = logger.actionElementForShapeCreation(eventType, shape, userName, attributeNameValues, metaforaTestServer, null);
	String action = JREXMLUtilities.nodeToString(actionElement);
	sendMessageToMetafora(action, logRecipients);
    }

    private void logExpressionCreationOrUpdate(boolean created, String id, String description, String userName, boolean metaforaTestServer, String logRecipients) {
	KaleidoscopeCommonFormatLogger logger = new KaleidoscopeCommonFormatMetaforaLogger(userName);
	Element actionElement = logger.actionElementForExpressionCreationOrUpdate(created, id, description, userName, metaforaTestServer, null);
	String action = JREXMLUtilities.nodeToString(actionElement);
	sendMessageToMetafora(action, logRecipients);
    }

    @Override
    public String freshGuid() {
	return ServerUtils.generateGUIDString();
    }

    @Override
    public String logMessage(String message, String levelName, String contextKey, String projectName) {
	Level level = Level.parse(levelName);
	if (level == Level.INFO) {
	    // Info warnings are ignored with the default settings
	    // could change the settings but then lots of distracting messages are in the logs
	    level = Level.WARNING;
	    message = "INFO: " + message;
	}
	String fullMessage = "Client reported: " + message + "\ncontextKey: " + contextKey + "\nProjectName: " + projectName;
	ServerUtils.logMessage(level, fullMessage);
	return null;
    }

    @Override
    public String[] fetchURLContents(String urlString, String contextKey) {
	// returns String[2] where [0] is the contents of the url
	// and [1] is any warnings or error messages to be sent to the client
	String result[] = new String[2];
	try {
	    URL url = new URL(urlString);
	    URLConnection connection = url.openConnection();
	    // set timeout to GAE maximum (30 seconds)
	    // while some URLs are in the URLFileCache
	    // there are also the URLs used in the 'copy' URL parameter
	    connection.setConnectTimeout(30000); 
	    connection.setReadTimeout(30000);
	    // following fixes Error 403 from HTTP
	    String userAgent = getThreadLocalRequest().getHeader("user-agent");
	    connection.setRequestProperty("User-Agent", userAgent);
	    InputStream inputStream = connection.getInputStream();
	    result[0] = inputStreamToString(inputStream);
	    if (result[0].contains("404 Not Found")) {
		// should be caught below and cache used
		throw new Exception("404 Not found returned from " + urlString);		
	    }
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	} catch (Exception e) {
	    InputStream inputStream = URLLocalFileCache.getInputStream(urlString);
	    if (inputStream == null) {
		e.printStackTrace();
		result[1] = ServerUtils.severe("Error while trying to fetch " + urlString + ". " + e.getMessage());
	    } else {
		try {
		    result[0] = inputStreamToString(inputStream);
		} catch (IOException ioException) {
		    ioException.printStackTrace();
		    result[1] = ServerUtils.severe("Error while trying to fetch " + urlString + ". " + ioException.getMessage());
		}
	    }
	}
	return result;
    }

    protected String inputStreamToString(InputStream inputStream)
	    throws IOException {
	BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
	StringBuilder contents = new StringBuilder();
	String line;
	while ((line = in.readLine()) != null) {
	    contents.append(line + "\r");
	}
	in.close();
	return contents.toString();
    }
    
    @Override
    public String reportIndicator(String type, String description, String[] attributeNameValues,
	                          String userName, String userId, String projectName,
	                          String contextId,
	                          boolean storeIndicators,
	                          boolean testing,
	                          boolean metaforaTestServer,
	                          String referableObjectProperties[],
	                          String logRecipients) {
	try {
	    String status = null;
	    if (logRecipients != null) {
		Element actionElement = 
			actionElement(
				type, description, attributeNameValues,
				userName, projectName,
				testing, metaforaTestServer,
				referableObjectProperties,
				null);
		String action = JREXMLUtilities.nodeToString(actionElement);
		status = sendMessageToMetafora(action, logRecipients);
	    }
	    if (storeIndicators) {
		// this uses userId instead of userName
		AnalysisIndicator analysisIndicator = 
			new AnalysisIndicator(type, description, attributeNameValues, contextId, userId, projectName, referableObjectProperties);
		ServerUtils.persistObject(analysisIndicator);
		informOpenChannels(contextId, "indicator: " + type + "; user: " + userId);
//		System.out.println(analysisIndicator.getCommonFormatString()); // for testing
	    }
	    return status;
	} catch (ApiProxyException e) { 
	    return handleApiProxyException(e);
	}
    }

    public static String channelToken(String contextId) {
	return "context-" + contextId;
    }

    public static Element actionElement(
	    String type, String description, String[] attributeNameValues,
	    String userName, String projectName,
	    boolean testing, Boolean metaforaTestServer,
	    String referableObjectProperties[],
	    Date date) {
	KaleidoscopeCommonFormatLogger logger = new KaleidoscopeCommonFormatMetaforaLogger(userName);
	Element actionElement = logger.createActionElement(date);
	Element[] subElements = logger.addActionElement("OTHER", "indicator", userName, attributeNameValues, description, testing, metaforaTestServer, actionElement);
	Element actionObject = subElements[0];
	Element actionObjectPropertiesElement = subElements[1];
	Element actionContentPropertiesElement = subElements[2];
	actionContentPropertiesElement.appendChild(logger.createPropertyElement("ANALYSIS_TYPE", type));
	if (referableObjectProperties != null) {
	    actionObject.setAttribute("id", referableObjectProperties[0]);
	    actionObject.setAttribute("type", referableObjectProperties[1]);
	    String viewURL = referableObjectProperties[2];
	    actionObjectPropertiesElement.appendChild(
		    logger.createPropertyElement("VIEW_URL", viewURL));
	    String referenceURL = referableObjectProperties[3];
	    actionObjectPropertiesElement.appendChild(
		    logger.createPropertyElement("REFERENCE_URL", referenceURL));
	}
	return actionElement;
    }
    
    @Override
    public String[] listenForModelUpdates(String contextKey, String projectName) {
	String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	String clientId = createClientId(modelKey);
	String result[] = new String[2];
	try {
	    result[0] = listenForUpdates(clientId, modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    public static String listenForUpdates(String channelId, String context) {
	ChannelService channelService = ChannelServiceFactory.getChannelService();
	String channel = channelService.createChannel(channelId);
	DAO dao = ServerUtils.getDao();
	OpenSessionChannels openChannels = dao.find(OpenSessionChannels.class, context);
	if (openChannels == null) {
	    openChannels = new OpenSessionChannels(context, channelId);
	} else {
	    openChannels.addChannel(channelId);
	}
	dao.persistObject(openChannels);
	return channel;
    }
    
    @Override
    public String[] updateAnimationSettings(
	    String xml,
	    String[] standardAttributes,
	    String xmppLogRecipients,
	    String contextKey,
	    String userName,
	    String projectName,
	    String previousTimeStamp,
	    int undoCount) {
	String result[] = new String[2];
	try {
	    DAO dao = ServerUtils.getDao();
	    String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	    // TODO: determine if the following is still functional 
	    // since these are indexed by modelKey and time stamp
	    ServerAnimationSettings animationSettings = dao.getAnimationSettings(modelKey);
	    if (animationSettings == null) {
		animationSettings = new ServerAnimationSettings(modelKey, xml);
	    } else {
		animationSettings.setXml(xml);
	    }
	    dao.persistObject(animationSettings);
	    result[0] = animationSettings.getTimeStampString();
	    linkTimeStamps(previousTimeStamp, undoCount, result[0], modelKey);
	} catch (ApiProxyException e) { 
	    result[1] = handleApiProxyException(e);
	}
	return result;
    }
    
    private String createClientId(String modelKey) {
	HttpServletRequest request = getThreadLocalRequest();
	String userAgent = request.getHeader("user-agent");
	// TODO: determine how unique this really is
	// would a counter implemented in the datastore be better?
	String userAgentHash = Integer.toString(userAgent.hashCode(), Character.MAX_RADIX);
	String ipAddress = request.getRemoteAddr();
	// channel keys are limited to 64 characters
	return ensureMaximumStringLength(modelKey + userAgentHash + ipAddress, 64);
    }
	    
    private String ensureMaximumStringLength(String string, int max) {
	int excess = string.length() - max;
	if (excess <= 0) {
	    return string;
	} else {
	    return string.substring(excess);
	}
    }

    public void informOpenChannels(String modelKey, String message) {
	String[] clientIds = OpenSessionChannels.getClientIds(modelKey);
	if (clientIds == null) {
	    return;
	}
	String exceptClientId = createClientId(modelKey);
	for (String clientId : clientIds) {
	    if (!clientId.equals(exceptClientId)) {
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage(clientId, message));
	    }
	}
    }

    @Override
    public String windowClosing(String contextKey, String projectName) {
	String modelKey = ServerUtils.createModelKey(contextKey, projectName);
	String clientId = createClientId(modelKey);
	removeChannelListener(clientId, modelKey);
	return null;
    }

    public static void removeChannelListener(String clientId, String context) {
	DAO dao = ServerUtils.getDao();
	OpenSessionChannels openChannels = dao.find(OpenSessionChannels.class, context);
	if (openChannels != null) {
	    openChannels.removeChannel(clientId);
	    dao.persistObject(openChannels);
	}	
    }

    @Override
    public String[] fetchModelDescriptions(String contextKey, String userId) {
	if (userId == null) {
	    return ServerUtils.getModelDescriptionsOfContext(contextKey);
	} else {
	    return ServerUtils.getModelDescriptionsOfUserId(userId);
	}
    }

    @Override
    public String[] isUpToDate(String modelURL) {
	// return 4 Strings: "true" or "false"; contextKey; modelName, any error message
	String result[] = new String[4];
	// a typical URL: http://127.0.0.1:8888/p/O4pv40cKXYSwsT4BRijj78/p1.xml?time=123
	try {
	    int lastSlashIndex = modelURL.lastIndexOf('/');
	    if (lastSlashIndex < 0) {
		// shouldn't happen
		ServerUtils.warn("Can't parse model URL: " + modelURL);
		result[0] = "true";
		return result;
	    }
	    String modelName = modelURL.substring(lastSlashIndex+1);
	    int dotIndex = modelName.indexOf('.');
	    if (dotIndex >= 0) {
		modelName = modelName.substring(0, dotIndex);
	    }
	    int previousSlashIndex = modelURL.lastIndexOf('/', lastSlashIndex-1);
	    String contextKey = modelURL.substring(previousSlashIndex+1, lastSlashIndex);
	    result[1] = contextKey;
	    result[2] = modelName;
	    String asOfTimeString = CommonUtils.getURLParameter("time", modelURL);
	    boolean upToDate = getNextTimeStamp(asOfTimeString, contextKey, modelName) == null;
	    result[0] = Boolean.toString(upToDate);
	} catch (ApiProxyException e) { 
	    result[3] = handleApiProxyException(e);
	}
	return result;
    }

    public String handleApiProxyException(ApiProxyException e) {
	if (e instanceof OverQuotaException) {
	    if (!sentQuotaExceededMessage) {
		JID[] jids = new JID[2];
		jids[0] = new JID("toontalk@gmail.com");
		jids[1] = new JID("mavrikis@gmail.com");
		String messageBody = "eXpresser quota exceeded! " + e.getMessage();
		Message message = 
			new MessageBuilder().withRecipientJids(jids).withBody(messageBody).withFromJid(fromJID).build();   
		xmppService.sendMessage(message);
		sentQuotaExceededMessage = true;
	    }
	    return "Server quota exceeded. Sorry but your work isn't being saved and you can't load previous work. System administrators have been notified. Daily quotas are replenished daily at midnight US Pacific time. Please try again later.";
	} else {
	    return "Server signaled an error. Sorry. Try again soon. Error: " + e.getMessage();
	}
    }

    @Override
    public String storeActivityDocumentEvent(String activityDocumentEventXML, String contextKey, String modelName) {
	String modelKey = ServerUtils.createModelKey(contextKey, modelName);
	ActivityDocumentXMLUpdateEvent.storeXMLUpdateEvent(activityDocumentEventXML, modelKey);
	return null;
    }

    @Override
    public String activityDocumentCompletedEvent(
	    String activityURL,
	    String contextKey,
	    String userName,
	    String modelName,
	    String[] standardAttributes,
	    boolean metaforaTestServer,
	    String xmppLogRecipients) {
	// extra arguments are in case Metafora wants to be informed of this as well
	String modelKey = ServerUtils.createModelKey(contextKey, modelName);
	DAO dao = ServerUtils.getDao();
	if (dao.find(ActivityDocumentCompleted.class, modelKey) == null) {
	    ServerUtils.persistObject(new ActivityDocumentCompleted(modelKey, activityURL, userName));
	}
	// no errors to report
	return null;
    }

}
