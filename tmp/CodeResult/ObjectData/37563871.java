package jp.aegif.nemaki.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import jp.aegif.nemaki.api.resources.ResourceBase;
import jp.aegif.nemaki.model.Aspect;
import jp.aegif.nemaki.model.Content;
import jp.aegif.nemaki.model.Document;
import jp.aegif.nemaki.model.Folder;
import jp.aegif.nemaki.model.NemakiAttachment;
import jp.aegif.nemaki.model.Property;
import jp.aegif.nemaki.model.Role;
import jp.aegif.nemaki.repository.TypeManager;
import jp.aegif.nemaki.service.ACLService;
import jp.aegif.nemaki.service.NemakiCmisService;
import jp.aegif.nemaki.service.NodeService;
import jp.aegif.nemaki.service.ObjectService;
import jp.aegif.nemaki.service.PermissionService;
import jp.aegif.nemaki.service.RepositoryService;
import jp.aegif.nemaki.service.UserGroupService;
import jp.aegif.nemaki.util.NemakiConstants;

import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.AllowableActions;
import org.apache.chemistry.opencmis.commons.data.CmisExtensionElement;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.FailedToDeleteData;
import org.apache.chemistry.opencmis.commons.data.ObjectData;
import org.apache.chemistry.opencmis.commons.data.Properties;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.data.PropertyId;
import org.apache.chemistry.opencmis.commons.data.PropertyString;
import org.apache.chemistry.opencmis.commons.data.RenditionData;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConstraintException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisInvalidArgumentException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.AllowableActionsImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.CmisExtensionElementImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ObjectDataImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertiesImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertyBooleanImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertyDateTimeImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertyIdImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertyIntegerImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertyStringImpl;
import org.apache.chemistry.opencmis.commons.impl.server.ObjectInfoImpl;
import org.apache.chemistry.opencmis.commons.server.CallContext;
import org.apache.chemistry.opencmis.commons.server.ObjectInfoHandler;
import org.apache.chemistry.opencmis.commons.spi.Holder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.sun.xml.ws.api.message.Attachment;

public class ObjectServiceImpl implements ObjectService {

	private static final Log log = LogFactory.getLog(ObjectServiceImpl.class);

	private static final String ASTERISK = "*";
	private static final String COMMA = ",";

	private ACLService aclService;
	private NodeService nodeService;
	private PermissionService permissionService;
	private RepositoryService repositoryService;

	public ObjectData create(CallContext callContext, Properties properties,
			String folderId, ContentStream contentStream,
			VersioningState versioningState, ObjectInfoHandler objectInfos) {

		String typeId = getTypeId(properties);
		TypeDefinition type = repositoryService.getTypeManager()
				.getType(typeId);
		if (type == null) {
			throw new CmisObjectNotFoundException("Type '" + typeId
					+ "' is unknown!");
		}

		String objectId = null;
		if (type.getBaseTypeId() == BaseTypeId.CMIS_DOCUMENT) {
			objectId = createDocument(callContext, properties, folderId,
					contentStream, versioningState);
		} else if (type.getBaseTypeId() == BaseTypeId.CMIS_FOLDER) {
			objectId = createFolder(callContext, properties, folderId);
		} else {
			throw new CmisObjectNotFoundException(
					"Cannot create object of type '" + typeId + "'!");
		}
		return compileObjectType(callContext,
				nodeService.get(Content.class, objectId), null, false, false,
				objectInfos);
	}

	public void setContentStream(CallContext callContext,
			Holder<String> objectId, boolean overwriteFlag,
			ContentStream contentStream) {
		String newAttachmentId = nodeService.createAttachment(
				callContext.getUsername(),
				millisToCalendar(System.currentTimeMillis()), contentStream);
		//ContentStream length can't be setted, so set it forcibly 
		updateAttachmentLength(newAttachmentId);
		
		
		Content content = nodeService.get(Document.class, objectId.getValue());
		// TODO use overwriteFlag
		content.getNemakiAttachments().add(newAttachmentId);
		nodeService.update(content);
	}

	public FailedToDeleteData deleteTree(CallContext callContext,
			String folderId, Boolean continueOnFailure) {
		return null;
	}

	public void deleteObject(CallContext callContext, String objectId) {

		if (objectId == null)
			throw new CmisInvalidArgumentException("Object Id must be set.");

		// Delete object's attachments
		Content object = nodeService.get(Document.class, objectId);
		if (object.getNemakiAttachments() != null) {
			for (String attachmentId : object.getNemakiAttachments()) {
				nodeService.delete(Content.class, attachmentId);
			}
		}
		nodeService.delete(Content.class, objectId);
	}

	public void moveObject(CallContext callContext, Holder<String> objectId,
			String targetFolderId, NemakiCmisService couchCmisService) {
	}

	public List<RenditionData> getRenditions(CallContext callContext,
			String objectId, String renditionFilter, BigInteger maxItems,
			BigInteger skipCount, ExtensionsData extension) {
		return Collections.emptyList();
	}

	public void updateProperties(CallContext callContext,
			Holder<String> objectId, Properties properties,
			NemakiCmisService couchCmisService) {
		Content doc = nodeService.get(Document.class, objectId.getValue());
		if (doc == null)
			log.error("The object is not found: " + objectId);

		doc.setName(properties.getProperties().get(PropertyIds.NAME)
				.getFirstValue().toString());
		doc.setModifier(callContext.getUsername());
		doc.setModified(millisToCalendar(System.currentTimeMillis()));

		List<Aspect> aspects = buildAspects(properties);
		doc.setAspects(aspects);

		nodeService.update(doc);
	}

	public ContentStream getContentStream(CallContext callContext,
			String objectId, BigInteger offset, BigInteger length) {

		Content content = nodeService.get(Content.class, objectId);
		NemakiAttachment attachment;
		if (content.getType().equals(Document.TYPE)) {
			Document doc = (Document) nodeService.get(Document.class, objectId);
			attachment = nodeService.getAttachment(doc.getNemakiAttachments()
					.get(doc.getNemakiAttachments().size() - 1));
		} else {
			attachment = nodeService.getAttachment(objectId);
		}

		ContentStreamImpl csi = new ContentStreamImpl();
		try {
			csi.setFileName(URLEncoder.encode("content", "UTF-8"));
			csi.setLength(BigInteger.valueOf(attachment.getLength()));
			csi.setMimeType(attachment.getMimeType());
			csi.setStream(nodeService.getAttachmentInputStream(
					attachment.getId(), "content"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return csi;
	}

	public ObjectData getObjectByPath(CallContext callContext, String path,
			String filter, Boolean includeAllowableActions, Boolean includeAcl,
			ObjectInfoHandler objectInfos) {

		List<Content> result = nodeService.getContentsByPath(path);
		Content content = result.get(0);

		// set defaults if values not set
		boolean iaa = (includeAllowableActions == null ? false
				: includeAllowableActions.booleanValue());
		boolean iacl = (includeAcl == null ? false : includeAcl.booleanValue());

		return compileObjectType(callContext, content, filter, iaa, iacl,
				objectInfos);
	}

	public ObjectData getObject(CallContext callContext, String objectId,
			String versionServicesId, String filter,
			Boolean includeAllowableActions, Boolean includeAcl,
			ObjectInfoHandler objectInfos) {

		if (objectId == null)
			throw new CmisInvalidArgumentException("Object Id must be set.");

		Content content = nodeService.get(Content.class, objectId);

		// set defaults if values not set
		boolean iaa = (includeAllowableActions == null ? false
				: includeAllowableActions.booleanValue());
		boolean iacl = (includeAcl == null ? false : includeAcl.booleanValue());

		return compileObjectType(callContext, content, filter, iaa, iacl,
				objectInfos);
	}

	public AllowableActions getAllowableActions(CallContext callContext,
			String objectId) {
		return null;
	}

	public String createFolder(CallContext callContext, Properties properties,
			String folderId) {

		if ((properties == null) || (properties.getProperties() == null)) {
			throw new CmisInvalidArgumentException("Properties must be set!");
		}
		// check type
		String typeId = getTypeId(properties);
		TypeDefinition type = repositoryService.getTypeManager()
				.getType(typeId);
		if (type == null) {
			throw new CmisObjectNotFoundException("Type '" + typeId
					+ "' is unknown!");
		}
		String name = getStringProperty(properties, PropertyIds.NAME);
		String path = getStringProperty(properties, PropertyIds.PATH);
		Map<String, Object> nodeMap = new HashMap<String, Object>();
		Map<String, PropertyData<?>> propList = properties.getProperties();
		Iterator<String> keyIterator = propList.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			PropertyData<?> pd = propList.get(key);

			if (pd instanceof PropertyDateTimeImpl) {

			}
			nodeMap.put(key, pd.getFirstValue());
		}
		Folder folder = new Folder();
		folder.setType(Folder.TYPE);
		folder.setName(name);
		folder.setParentId(folderId);
		folder.setCreator(callContext.getUsername());
		folder.setCreated(millisToCalendar(System.currentTimeMillis()));
		folder.setModifier(callContext.getUsername());
		folder.setModified(millisToCalendar(System.currentTimeMillis()));
		folder.setPath(path);
		folder.setPermission(permissionService.getDefaultPermission(callContext
				.getUsername()));
		List<Aspect> aspects = buildAspects(properties);
		folder.setAspects(aspects);
		nodeService.create(folder);

		return folder.getId();
	}

	public String createDocumentFromSource(CallContext callContext,
			String sourceId, Properties properties, String folderId,
			VersioningState versioningState) {
		return null;
	}

	@SuppressWarnings("serial")
	public String createDocument(CallContext callContext,
			Properties properties, String folderId,
			ContentStream contentStream, VersioningState versioningState) {

		// check properties
		if ((properties == null) || (properties.getProperties() == null))
			throw new CmisInvalidArgumentException("Properties must be set!");

		// check versioning state
		if (versioningState == VersioningState.NONE)
			throw new CmisConstraintException("Versioning must be supported!");

		// check type (cmis:document)
		String typeId = getTypeId(properties);
		TypeDefinition type = repositoryService.getTypeManager()
				.getType(typeId);
		if (type == null)
			throw new CmisObjectNotFoundException(typeId
					+ " is unknown as type!");

		Map<String, Object> nodeMap = new HashMap<String, Object>();
		Map<String, PropertyData<?>> propList = properties.getProperties();
		Iterator<String> keyIterator = propList.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			PropertyData<?> pd = propList.get(key);

			if (pd instanceof PropertyDateTimeImpl) {

			}
			nodeMap.put(key, pd.getFirstValue());
		}

		final String attachmentId = nodeService.createAttachment(
				callContext.getUsername(),
				millisToCalendar(System.currentTimeMillis()), contentStream);
		
		Document doc = new Document();
		doc.setType(Document.TYPE);
		doc.setName(getStringProperty(properties, PropertyIds.NAME));
		doc.setParentId(folderId);
		doc.setCreator(callContext.getUsername());
		doc.setCreated(millisToCalendar(System.currentTimeMillis()));
		doc.setModifier(callContext.getUsername());
		doc.setModified(millisToCalendar(System.currentTimeMillis()));
		doc.setPath(getStringProperty(properties, PropertyIds.PATH));
		doc.setPermission(permissionService.getDefaultPermission(callContext
				.getUsername()));

		List<Aspect> aspects = buildAspects(properties);

		doc.setAspects(aspects);
		doc.setNemakiAttachments(new ArrayList<String>() {
			{
				add(attachmentId);
			}
		});
		nodeService.create(doc);
		return doc.getId();
	}

	//attachment格納コンテンツのlength書き換え
	private void updateAttachmentLength(String newAttachmentId){
		long length = 0;
		Content createdAttachment = nodeService.get(NemakiAttachment.class, newAttachmentId);
		Map<String, org.ektorp.Attachment> ats = createdAttachment.getAttachments();
		Collection<org.ektorp.Attachment> attachs = ats.values();
		Iterator<org.ektorp.Attachment> iterator = attachs.iterator();
		while(iterator.hasNext()){
			length = iterator.next().getContentLength();
		}
		createdAttachment.setLength(length);
		nodeService.update(createdAttachment);
}
	
	/**
	 * Builds aspects from properties
	 * 
	 * @param properties
	 * @return aspects
	 */
	private List<Aspect> buildAspects(Properties properties) {
		List<CmisExtensionElement> exts = properties.getExtensions();
		List<Aspect> aspects = new ArrayList<Aspect>();
		if (exts == null) {
			return aspects;
		}
		for (int i = 0; i < exts.size(); i++) {
			CmisExtensionElement aspectsExt = exts.get(i);
			//TODO aspectが"aspects"か判定
			
			if (aspectsExt.getName().equals("aspects")) {
				List<CmisExtensionElement> aspectsList = aspectsExt.getChildren();
				
				for (int j = 0; j < aspectsList.size(); j++) {
					List<Property> props = new ArrayList<Property>();
					CmisExtensionElement aspectExt = aspectsList.get(j);
					//aspectExtのname="aegifPublish", children:key-valueマップ
					List<CmisExtensionElement> propsList = aspectExt.getChildren();
					for(CmisExtensionElement p : propsList){
						props.add(new Property(p.getName(), p.getValue()));
					}
					
					aspects.add(new Aspect(aspectExt.getName(), props));
				}
				
			}
		}
		return aspects;
	}
	
	/**
	 * Gets the type id from a set of properties.
	 */
	private String getTypeId(Properties properties) {
		PropertyData<?> typeProperty = properties.getProperties().get(
				PropertyIds.OBJECT_TYPE_ID);
		if (!(typeProperty instanceof PropertyId)) {
			throw new CmisInvalidArgumentException("Type id must be set!");
		}
		String typeId = ((PropertyId) typeProperty).getFirstValue();
		if (typeId == null) {
			throw new CmisInvalidArgumentException("Type id must be set!");
		}
		return typeId;
	}

	/**
	 * Reads a given property from a set of properties.
	 */
	private String getStringProperty(Properties properties, String name) {
		PropertyData<?> property = properties.getProperties().get(name);
		if (!(property instanceof PropertyString)) {
			return null;
		}
		return ((PropertyString) property).getFirstValue();
	}

	/**
	 * Builds a CMIS ObjectData from the given CouchDB content.
	 */
	public ObjectData compileObjectType(CallContext context, Content content,
			String filter, boolean includeAllowableActions, boolean includeAcl,
			ObjectInfoHandler objectInfos) {

		ObjectDataImpl result = new ObjectDataImpl();
		ObjectInfoImpl objectInfo = new ObjectInfoImpl();

		result.setProperties(compileProperties(content, splitFilter(filter),
				objectInfo));

		result.setExtensions(new ArrayList<CmisExtensionElement>());
		setAspects(result, content.getAspects());
		if (context.isObjectInfoRequired()) {
			objectInfo.setObject(result);
			objectInfos.addObjectInfo(objectInfo);
		}
		if (BaseTypeId.CMIS_DOCUMENT.equals(result.getBaseTypeId())) {
			setPastVersions(result, content.getNemakiAttachments());
		}
		if (includeAllowableActions) {
			result.setAllowableActions(compileAllowableActions(context, content));
		}
		if (includeAcl) {
			result.setAcl(aclService.compileAcl(content));
			result.setIsExactAcl(true);
		}
		
		return result;
	}

	/**
	 * Build aspects for return to client
	 */
	private void setAspects(ObjectDataImpl result, List<Aspect> exsistingAspects) {

		List<CmisExtensionElement> aspects = new ArrayList<CmisExtensionElement>();

		for (int i = 0; i < exsistingAspects.size(); i++) {
			Aspect exAspect = exsistingAspects.get(i);
			List<Property> existingProps = exAspect.getProperties();
			Map<String, String> props = new HashMap<String, String>();
			for (Property p : existingProps) {
				props.put(p.getKey(), String.valueOf(p.getValue()));
			}
			aspects.add(new CmisExtensionElementImpl(repositoryService
					.getNamespace(), exAspect.getName(), props, ""));
			
		}
		
		result.getExtensions().add(
				new CmisExtensionElementImpl(repositoryService.getNamespace(),
						NemakiConstants.ASPECTS, null, aspects));
	}

	/**
	 * Adds an extension element to a CMIS object in order to store past
	 * versions.
	 */
	private void setPastVersions(ObjectDataImpl result,
			List<String> nemakiAttachments) {

		Map<String, String> attachments = new TreeMap<String, String>();
		if(nemakiAttachments == null) return;	//for attachment documents which has no attachements by itself.
		for (int i = 0; i < nemakiAttachments.size(); i++)
			attachments.put(String.valueOf("v" + i), nemakiAttachments.get(i));
		result.getExtensions().add(
				new CmisExtensionElementImpl(repositoryService.getNamespace(),
						NemakiConstants.PAST_VERSIONS, attachments, "dummy"));
	}

	/**
	 * Sets allowable action for the content
	 * 
	 * @param content
	 */
	private AllowableActions compileAllowableActions(CallContext callContext,
			Content content) {

		boolean isFolder = Folder.TYPE.equals(content.getType());
		boolean isRoot = isFolder
				&& repositoryService.getRepositoryInfo().getRootFolderId()
						.equals(content.getPath());
		Role role = content.getRole();

		Set<Action> actionSet = permissionService.getAllowableActionset(role,
				isFolder, isRoot);

		AllowableActionsImpl allowableActions = new AllowableActionsImpl();
		allowableActions.setAllowableActions(actionSet);
		return allowableActions;
	}

	/**
	 * Compiles properties of a piece of content.
	 */
	private Properties compileProperties(Content content, Set<String> filter,
			ObjectInfoImpl objectInfo) {

		boolean isFolder = content.getType().equals(Folder.TYPE) ? true : false;
		boolean isDocument = content.getType().equals(Document.TYPE) ? true
				: false;
		boolean isAttachment = content.getType().equals(NemakiAttachment.TYPE) ? true
				: false;

		setObjectBaseInfo(objectInfo, content);

		String typeId = null;
		PropertiesImpl properties = new PropertiesImpl();

		if (isFolder) {
			typeId = TypeManager.FOLDER_TYPE_ID;
			setCmisFolderProperties(properties, typeId, filter, content);
			setObjectFolderInfo(objectInfo, content);
		} else {
			Content attachment = null;
			if (isDocument) {
				List<String> atts = content.getNemakiAttachments();
				attachment = nodeService
						.getAttachment(atts.get(atts.size() - 1));
			} else if (isAttachment) {
				attachment = content;
			}
			typeId = TypeManager.DOCUMENT_TYPE_ID;
			setCmisDocumentProperties(properties, typeId, filter, content);
			setCmisAttachmentProperties(properties, typeId, filter, attachment, content);
			setObjectDocumentInfo(objectInfo, content);
		}
		setCmisBaseProperties(properties, typeId, filter, content);
		return properties;
	}

	/**
	 * Adds specified property in property set.
	 * 
	 * @param props
	 *            property set
	 * @param typeId
	 *            object type (e.g. cmis:document)
	 * @param filter
	 *            filter string set
	 * @param id
	 *            property ID
	 * @param value
	 *            actual property value
	 */
	private void addPropertyBase(PropertiesImpl props, String typeId,
			Set<String> filter, String id, Object value) {
		if (!checkAddProperty(props, typeId, filter, id))
			return;

		switch (repositoryService.getTypeManager().getType(typeId)
				.getPropertyDefinitions().get(id).getPropertyType()) {
		case BOOLEAN:
			props.addProperty(new PropertyBooleanImpl(id, (Boolean) value));
			break;
		case INTEGER:
			props.addProperty(new PropertyIntegerImpl(id, BigInteger
					.valueOf((Long) value)));
			break;
		case DATETIME:
			props.addProperty(new PropertyDateTimeImpl(id,
					(GregorianCalendar) value));
			break;
		case STRING:
			props.addProperty(new PropertyStringImpl(id, String.valueOf(value)));
			break;
		case ID:
			props.addProperty(new PropertyIdImpl(id, String.valueOf(value)));
			break;
		
		default:
		}
	}

	/**
	 * Verifies that parameters are safe.
	 */
	private boolean checkAddProperty(Properties properties, String typeId,
			Set<String> filter, String id) {

		if ((properties == null) || (properties.getProperties() == null))
			throw new IllegalArgumentException("Properties must not be null!");

		if (id == null)
			throw new IllegalArgumentException("ID must not be null!");

		TypeDefinition type = repositoryService.getTypeManager()
				.getType(typeId);

		if (type == null)
			throw new IllegalArgumentException("Unknown type: " + typeId);

		if (!type.getPropertyDefinitions().containsKey(id))
			throw new IllegalArgumentException("Unknown property: " + id);

		String queryName = type.getPropertyDefinitions().get(id).getQueryName();

		if ((queryName != null) && (filter != null)) {
			if (!filter.contains(queryName)) {
				return false;
			} else {
				filter.remove(queryName);
			}
		}
		return true;
	}

	private void setCmisBaseProperties(PropertiesImpl properties,
			String typeId, Set<String> filter, Content content) {

		addPropertyBase(properties, typeId, filter, PropertyIds.OBJECT_ID,
				content.getId());
		addPropertyBase(properties, typeId, filter, PropertyIds.NAME,
				content.getName());
		addPropertyBase(properties, typeId, filter, PropertyIds.CREATED_BY,
				content.getCreator());
		addPropertyBase(properties, typeId, filter,
				PropertyIds.LAST_MODIFIED_BY, content.getModifier());
		addPropertyBase(properties, typeId, filter, PropertyIds.CREATION_DATE,
				content.getCreated());
		addPropertyBase(properties, typeId, filter,
				PropertyIds.LAST_MODIFICATION_DATE, content.getModified());
		addPropertyBase(properties, typeId, filter, PropertyIds.PATH,
				content.getPath());		//setting path
	}

	private void setCmisFolderProperties(PropertiesImpl properties,
			String typeId, Set<String> filter, Content content) {

		addPropertyBase(properties, typeId, filter, PropertyIds.BASE_TYPE_ID,
				BaseTypeId.CMIS_FOLDER.value());
		addPropertyBase(properties, typeId, filter, PropertyIds.OBJECT_TYPE_ID,
				TypeManager.FOLDER_TYPE_ID);
		String path = (String) content.getPath();
		addPropertyBase(properties, typeId, filter, PropertyIds.PATH,
				(path.length() == 0 ? "/" : path));

		if (!"/".equals(content.getPath())) {
			addPropertyBase(properties, typeId, filter, PropertyIds.PARENT_ID,
					"/");
		}
	}

	private void setCmisDocumentProperties(PropertiesImpl properties,
			String typeId, Set<String> filter, Content content) {

		addPropertyBase(properties, typeId, filter, PropertyIds.BASE_TYPE_ID,
				BaseTypeId.CMIS_DOCUMENT.value());
		addPropertyBase(properties, typeId, filter, PropertyIds.OBJECT_TYPE_ID,
				TypeManager.DOCUMENT_TYPE_ID);
		addPropertyBase(properties, typeId, filter, PropertyIds.IS_IMMUTABLE,
				false);
		addPropertyBase(properties, typeId, filter,
				PropertyIds.IS_LATEST_VERSION, true);
		addPropertyBase(properties, typeId, filter,
				PropertyIds.IS_MAJOR_VERSION, true);
		addPropertyBase(properties, typeId, filter,
				PropertyIds.IS_LATEST_MAJOR_VERSION, true);
		addPropertyBase(properties, typeId, filter, PropertyIds.VERSION_LABEL,
				content.getRevision());
		addPropertyBase(properties, typeId, filter,
				PropertyIds.VERSION_SERIES_ID, content.getId());
		addPropertyBase(properties, typeId, filter,
				PropertyIds.CHECKIN_COMMENT, "");

	}

	private void setCmisAttachmentProperties(PropertiesImpl properties,
			String typeId, Set<String> filter, Content attachment, Content document) {

		addPropertyBase(properties, typeId, filter,
				PropertyIds.CONTENT_STREAM_LENGTH, attachment.getLength());
		addPropertyBase(properties, typeId, filter,
				PropertyIds.CONTENT_STREAM_MIME_TYPE, attachment.getMimeType());
		addPropertyBase(properties, typeId, filter,
				PropertyIds.CONTENT_STREAM_FILE_NAME, document.getName());
		addPropertyBase(properties, typeId, filter,
				PropertyIds.CONTENT_STREAM_ID, attachment.getId());
	}

	/**
	 * Separates filter string with ','.
	 */
	private Set<String> splitFilter(String filter) {
		if (filter == null || filter.trim().length() == 0) {
			return null;
		}
		Set<String> filters = new HashSet<String>();
		for (String s : filter.split(COMMA)) {
			s = s.trim();
			if (s.equals(ASTERISK)) {
				return null;
			} else if (s.length() > 0) {
				filters.add(s);
			}
		}
		// set a few base properties
		// query name == id (for base type properties)
		filters.add(PropertyIds.OBJECT_ID);
		filters.add(PropertyIds.OBJECT_TYPE_ID);
		filters.add(PropertyIds.BASE_TYPE_ID);
		return filters;
	}

	/**
	 * Converts long value to GregorianCalendar class.
	 */
	private GregorianCalendar millisToCalendar(long millis) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(millis);
		return calendar;
	}

	private void setObjectDocumentInfo(ObjectInfoImpl objectInfo,
			Content content) {
		objectInfo.setTypeId(TypeManager.DOCUMENT_TYPE_ID);
		objectInfo.setBaseType(BaseTypeId.CMIS_DOCUMENT);
		objectInfo.setHasContent(true);
		objectInfo.setHasParent(true);
		objectInfo.setSupportsDescendants(false);
		objectInfo.setSupportsFolderTree(false);
		objectInfo.setContentType(content.getMimeType());
		objectInfo.setFileName(content.getName());
	}

	private void setObjectFolderInfo(ObjectInfoImpl objectInfo, Content content) {
		objectInfo.setTypeId(TypeManager.FOLDER_TYPE_ID);
		objectInfo.setBaseType(BaseTypeId.CMIS_FOLDER);
		objectInfo.setContentType(null);
		objectInfo.setFileName(null);
		objectInfo.setHasContent(false);
		objectInfo.setSupportsDescendants(true);
		objectInfo.setSupportsFolderTree(true);
		if ("/".equals(content.getPath())) {
			objectInfo.setHasParent(false);
		} else {
			objectInfo.setHasParent(true);
		}
	}

	private void setObjectBaseInfo(ObjectInfoImpl objectInfo, Content content) {
		objectInfo.setHasAcl(true);
		objectInfo.setVersionSeriesId(null);
		objectInfo.setIsCurrentVersion(true);
		objectInfo.setRelationshipSourceIds(null);
		objectInfo.setRelationshipTargetIds(null);
		objectInfo.setRenditionInfos(null);
		objectInfo.setSupportsPolicies(false);
		objectInfo.setSupportsRelationships(false);
		objectInfo.setWorkingCopyId(null);
		objectInfo.setWorkingCopyOriginalId(null);
		objectInfo.setId(content.getId());
		objectInfo.setName(content.getName());
		objectInfo.setCreatedBy(UserGroupService.USER_UNKNOWN);
		objectInfo.setCreationDate(content.getCreated());
		objectInfo.setLastModificationDate(content.getModified());
	}

	public void setAclService(ACLService aclService) {
		this.aclService = aclService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

}
