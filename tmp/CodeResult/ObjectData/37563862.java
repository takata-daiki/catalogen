/**
 * This file is part of NemakiWare.
 *
 * NemakiWare is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NemakiWare is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NemakiWare. If not, see <http://www.gnu.org/licenses/>.
 */
package jp.aegif.nemaki.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.aegif.nemaki.repository.NemakiRepository;
import jp.aegif.nemaki.repository.RepositoryMap;

import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.AllowableActions;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;
import org.apache.chemistry.opencmis.commons.data.FailedToDeleteData;
import org.apache.chemistry.opencmis.commons.data.ObjectData;
import org.apache.chemistry.opencmis.commons.data.ObjectInFolderContainer;
import org.apache.chemistry.opencmis.commons.data.ObjectInFolderList;
import org.apache.chemistry.opencmis.commons.data.ObjectList;
import org.apache.chemistry.opencmis.commons.data.ObjectParentData;
import org.apache.chemistry.opencmis.commons.data.Properties;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.data.RenditionData;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinition;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinitionContainer;
import org.apache.chemistry.opencmis.commons.definitions.TypeDefinitionList;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ObjectInFolderContainerImpl;
import org.apache.chemistry.opencmis.commons.impl.jaxb.GetDescendants;
import org.apache.chemistry.opencmis.commons.impl.server.AbstractCmisService;
import org.apache.chemistry.opencmis.commons.server.CallContext;
import org.apache.chemistry.opencmis.commons.spi.Holder;

/**
 * Nemaki CMIS service.
 */
public class NemakiCmisService extends AbstractCmisService {

	/**
	 * Context data of the current CMIS call.
	 */
	private CallContext context;

	/**
	 * Map containing all Nemaki repositories.
	 */
	private RepositoryMap repositoryMap;

	/**
	 * Create a new NemakiCmisService.
	 */
	public NemakiCmisService(RepositoryMap repositoryMap) {
		this.repositoryMap = repositoryMap;
	}

	// --- Navigation Service Implementation ---

	/**
	 * Gets the list of child objects contained in the specified folder.
	 */
	@Override
	public ObjectInFolderList getChildren(String repositoryId, String folderId,
			String filter, String orderBy, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includePathSegment, BigInteger maxItems,
			BigInteger skipCount, ExtensionsData extension) {
		return getRepository(repositoryId).getChildren(getCallContext(),
				folderId, filter, includeAllowableActions, includePathSegment,
				maxItems, skipCount, this);
	}

	/**
	 * Gets the set of descendant objects contained in the specified folder or
	 * any of its child folders.
	 */
	@Override
	public List<ObjectInFolderContainer> getDescendants(String repositoryId,
			String folderId, BigInteger depth, String filter,
			Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includePathSegment, ExtensionsData extension) {
		return getRepository(repositoryId).getDescendants(getCallContext(),
				folderId, depth, filter, includeAllowableActions,
				includePathSegment, this, false);
	}

	/**
	 * Gets the parent folder object for the specified folder object.
	 */
	@Override
	public ObjectData getFolderParent(String repositoryId, String folderId,
			String filter, ExtensionsData extension) {
		return getRepository(repositoryId).getFolderParent(getCallContext(),
				folderId, filter, this);
	}

	/**
	 * Gets the set of descendant folder objects contained in the specified
	 * folder.
	 */
	@Override
	public List<ObjectInFolderContainer> getFolderTree(String repositoryId,
			String folderId, BigInteger depth, String filter,
			Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includePathSegment, ExtensionsData extension) {
		return getRepository(repositoryId).getDescendants(getCallContext(),
				folderId, depth, filter, includeAllowableActions,
				includePathSegment, this, true);
	}

	/**
	 * Gets the parent folder(s) for the specified non-folder, fileable object.
	 */
	@Override
	public List<ObjectParentData> getObjectParents(String repositoryId,
			String objectId, String filter, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includeRelativePathSegment, ExtensionsData extension) {
		return getRepository(repositoryId).getObjectParents(getCallContext(),
				objectId, filter, includeAllowableActions,
				includeRelativePathSegment, this);
	}

	/**
	 * Gets the list of documents that are checked out that the user has access
	 * to. No checkout for now, so empty.
	 */
	@Override
	public ObjectList getCheckedOutDocs(String repositoryId, String folderId,
			String filter, String orderBy, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {

		return getRepository(repositoryId).getCheckedOutDocs(getCallContext(),
				folderId, filter, orderBy, includeAllowableActions,
				includeRelationships, renditionFilter, maxItems, skipCount,
				extension);

	}

	// ---- Object Service Implementation ---

	/**
	 * Creates a new document, folder or policy. The property
	 * "cmis:objectTypeId" defines the type and implicitly the base type.
	 */
	@Override
	public String create(String repositoryId, Properties properties,
			String folderId, ContentStream contentStream,
			VersioningState versioningState, List<String> policies,
			ExtensionsData extension) {
		ObjectData object = getRepository(repositoryId).create(
				getCallContext(), properties, folderId, contentStream,
				versioningState, this);
		return object.getId();
	}

	/**
	 * Creates a document object of the specified type (given by the
	 * cmis:objectTypeId property) in the (optionally) specified location.
	 */
	@Override
	public String createDocument(String repositoryId, Properties properties,
			String folderId, ContentStream contentStream,
			VersioningState versioningState, List<String> policies,
			Acl addAces, Acl removeAces, ExtensionsData extension) {
		return getRepository(repositoryId).createDocument(getCallContext(),
				properties, folderId, contentStream, versioningState);
	}

	/**
	 * Creates a document object as a copy of the given source document in the
	 * (optionally) specified location.
	 */
	@Override
	public String createDocumentFromSource(String repositoryId,
			String sourceId, Properties properties, String folderId,
			VersioningState versioningState, List<String> policies,
			Acl addAces, Acl removeAces, ExtensionsData extension) {
		return getRepository(repositoryId).createDocumentFromSource(
				getCallContext(), sourceId, properties, folderId,
				versioningState);
	}

	/**
	 * Creates a folder object of the specified type (given by the
	 * cmis:objectTypeId property) in the specified location.
	 */
	@Override
	public String createFolder(String repositoryId, Properties properties,
			String folderId, List<String> policies, Acl addAces,
			Acl removeAces, ExtensionsData extension) {
		return getRepository(repositoryId).createFolder(getCallContext(),
				properties, folderId);
	}

	/**
	 * Deletes the content stream for the specified document object.
	 */
	@Override
	public void deleteContentStream(String repositoryId,
			Holder<String> objectId, Holder<String> changeToken,
			ExtensionsData extension) {
		getRepository(repositoryId).setContentStream(getCallContext(),
				objectId, true, null);
	}

	/**
	 * Deletes an object or cancels a check out. For the Web Services binding
	 * this is always an object deletion. For the AtomPub it depends on the
	 * referenced object. If it is a checked out document then the check out
	 * must be canceled. If the object is not a checked out document then the
	 * object must be deleted.
	 */
	@Override
	public void deleteObjectOrCancelCheckOut(String repositoryId,
			String objectId, Boolean allVersions, ExtensionsData extension) {
		//get a descendants list for the object 
		BigInteger depth=new BigInteger("-1");	//"all" depth
		List<ObjectInFolderContainer> descendants = 
				getDescendants(repositoryId, objectId, depth, "", true, IncludeRelationships.BOTH, "", true, null);
		
		//get all objectId list from the descendants
		List<String>ids = new ArrayList<String>();
		ids.add(objectId);
		ids = getDescentantsId(descendants,ids);

		//delete all descendants node recursively
		Iterator<String> iterator = ids.iterator();
		while(iterator.hasNext()){
			String id = iterator.next();
			getRepository(repositoryId).deleteObject(getCallContext(), id);
		}
	}

	/**
	 * Extract a list of object id from a result of getDescendants method 
	 * @param list
	 * @param ids
	 * @return
	 */
	private List<String> getDescentantsId(List<ObjectInFolderContainer> list, List<String>ids){
		for(ObjectInFolderContainer o : list){
			List<ObjectInFolderContainer> l = o.getChildren();
			if(l.isEmpty()){	//CASE:end node 
				String id = o.getObject().getObject().getId();
				ids.add(id);	//document
			}else{				//CASE:folder
				Map<String, PropertyData<?>> prop = o.getObject().getObject().getProperties().getProperties();
				String id = (String) prop.get(PropertyIds.OBJECT_ID).getFirstValue();
				ids.add(id);
				getDescentantsId(o.getChildren(),ids);
			}
		}
		return ids;
	}
	
	/**
	 * Deletes the specified folder object and all of its child- and
	 * descendant-objects.
	 */
	@Override
	public FailedToDeleteData deleteTree(String repositoryId, String folderId,
			Boolean allVersions, UnfileObject unfileObjects,
			Boolean continueOnFailure, ExtensionsData extension) {
		return getRepository(repositoryId).deleteTree(getCallContext(),
				folderId, continueOnFailure);
	}

	/**
	 * Gets the list of allowable actions for an object.
	 */
	@Override
	public AllowableActions getAllowableActions(String repositoryId,
			String objectId, ExtensionsData extension) {
		return getRepository(repositoryId).getAllowableActions(
				getCallContext(), objectId);
	}

	/**
	 * Gets the content stream for the specified document object, or gets a
	 * rendition stream for a specified rendition of a document or folder
	 * object.
	 */
	@Override
	public ContentStream getContentStream(String repositoryId, String objectId,
			String streamId, BigInteger offset, BigInteger length,
			ExtensionsData extension) {
		return getRepository(repositoryId).getContentStream(getCallContext(),
				objectId, offset, length);
	}

	/**
	 * Gets the specified information for the object specified by id.
	 */
	@Override
	public ObjectData getObject(String repositoryId, String objectId,
			String filter, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includePolicyIds, Boolean includeAcl,
			ExtensionsData extension) {
		return getRepository(repositoryId).getObject(getCallContext(),
				objectId, null, filter, includeAllowableActions, includeAcl,
				this);
	}

	/**
	 * Gets the specified information for the object specified by path.
	 */
	@Override
	public ObjectData getObjectByPath(String repositoryId, String path,
			String filter, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includePolicyIds, Boolean includeAcl,
			ExtensionsData extension) {
		return getRepository(repositoryId).getObjectByPath(getCallContext(),
				path, filter, includeAllowableActions, includeAcl, this);
	}

	/**
	 * Gets the list of properties for an object.
	 */
	@Override
	public Properties getProperties(String repositoryId, String objectId,
			String filter, ExtensionsData extension) {
		ObjectData object = getRepository(repositoryId).getObject(
				getCallContext(), objectId, null, filter, false, false, this);
		return object.getProperties();
	}

	/**
	 * Gets the list of associated renditions for the specified object. Only
	 * rendition attributes are returned, not rendition stream. No renditions,
	 * so empty.
	 */
	@Override
	public List<RenditionData> getRenditions(String repositoryId,
			String objectId, String renditionFilter, BigInteger maxItems,
			BigInteger skipCount, ExtensionsData extension) {

		return getRepository(repositoryId).getRenditions(getCallContext(),
				objectId, renditionFilter, maxItems, skipCount, extension);
	}

	/**
	 * Moves the specified file-able object from one folder to another.
	 */
	@Override
	public void moveObject(String repositoryId, Holder<String> objectId,
			String targetFolderId, String sourceFolderId,
			ExtensionsData extension) {
		getRepository(repositoryId).moveObject(getCallContext(), objectId,
				targetFolderId, this);
	}

	/**
	 * Sets the content stream for the specified document object.
	 */
	@Override
	public void setContentStream(String repositoryId, Holder<String> objectId,
			Boolean overwriteFlag, Holder<String> changeToken,
			ContentStream contentStream, ExtensionsData extension) {
		getRepository(repositoryId).setContentStream(getCallContext(),
				objectId, overwriteFlag, contentStream);
	}

	/**
	 * Updates properties of the specified object.
	 */
	@Override
	public void updateProperties(String repositoryId, Holder<String> objectId,
			Holder<String> changeToken, Properties properties,
			ExtensionsData extension) {
		getRepository(repositoryId).updateProperties(getCallContext(),
				objectId, properties, this);
	}

	// --- Versioning Service Implementation ---

	/**
	 * Returns the list of all document objects in the specified version series,
	 * sorted by the property "cmis:creationDate" descending.
	 */
	@Override
	public List<ObjectData> getAllVersions(String repositoryId,
			String objectId, String versionSeriesId, String filter,
			Boolean includeAllowableActions, ExtensionsData extension) {
		ObjectData theVersion = getRepository(repositoryId).getObject(
				getCallContext(), objectId, versionSeriesId, filter,
				includeAllowableActions, false, this);
		// TODO return all versions, not just one.
		return Collections.singletonList(theVersion);
	}

	/**
	 * Get the latest document object in the version series.
	 */
	@Override
	public ObjectData getObjectOfLatestVersion(String repositoryId,
			String objectId, String versionSeriesId, Boolean major,
			String filter, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			Boolean includePolicyIds, Boolean includeAcl,
			ExtensionsData extension) {
		return getRepository(repositoryId).getObject(getCallContext(),
				objectId, versionSeriesId, filter, includeAllowableActions,
				includeAcl, this);
	}

	/**
	 * Get a subset of the properties for the latest document object in the
	 * version series.
	 */
	@Override
	public Properties getPropertiesOfLatestVersion(String repositoryId,
			String objectId, String versionSeriesId, Boolean major,
			String filter, ExtensionsData extension) {
		ObjectData object = getRepository(repositoryId).getObject(
				getCallContext(), objectId, versionSeriesId, filter, false,
				false, null);

		return object.getProperties();
	}

	// --- ACL Service Implementation ---

	/**
	 * Applies a new ACL (Access Control List) to an object. Since it is not
	 * possible to transmit an "add ACL" and a "remove ACL" via AtomPub, the
	 * merging has to be done on the client side. The ACEs provided here is
	 * supposed to the new complete ACL.
	 */
	@Override
	public Acl applyAcl(String repositoryId, String objectId, Acl aces,
			AclPropagation aclPropagation) {
		return getRepository(repositoryId).applyAcl(getCallContext(), objectId,
				aces, aclPropagation);
	}

	/**
	 * Get the ACL (Access Control List) currently applied to the specified
	 * object.
	 */
	@Override
	public Acl getAcl(String repositoryId, String objectId,
			Boolean onlyBasicPermissions, ExtensionsData extension) {
		return getRepository(repositoryId).getAcl(getCallContext(), objectId);
	}

	// --- Repository Service Implementation ---

	/**
	 * Returns information about the CMIS repository, the optional capabilities
	 * it supports and its access control information.
	 */
	@Override
	public RepositoryInfo getRepositoryInfo(String repositoryId,
			ExtensionsData extension) {

		for (NemakiRepository repository : repositoryMap.getRepositories()) {
			if (repository.hasThisRepositoryId(repositoryId)) {
				return repository.getRepositoryInfo();
			}
		}
		throw new CmisObjectNotFoundException("Unknown repository '"
				+ repositoryId + "'!");
	}

	/**
	 * Returns a list of CMIS repository information available from this CMIS
	 * service endpoint. In contrast to the CMIS specification this method
	 * returns repository infos not only repository ids. (See OpenCMIS doc)
	 */
	@Override
	public List<RepositoryInfo> getRepositoryInfos(ExtensionsData arg0) {
		List<RepositoryInfo> result = new ArrayList<RepositoryInfo>();
		for (NemakiRepository repository : repositoryMap.getRepositories()) {
			result.add(repository.getRepositoryInfo());
		}
		return result;
	}

	/**
	 * Returns the list of object types defined for the repository that are
	 * children of the specified type.
	 */
	@Override
	public TypeDefinitionList getTypeChildren(String repositoryId,
			String typeId, Boolean includePropertyDefinitions,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		return getRepository(repositoryId).getTypeChildren(getCallContext(),
				typeId, includePropertyDefinitions, maxItems, skipCount);
	}

	/**
	 * Gets the definition of the specified object type.
	 */
	@Override
	public TypeDefinition getTypeDefinition(String repositoryId, String typeId,
			ExtensionsData extension) {
		return getRepository(repositoryId).getTypeDefinition(getCallContext(),
				typeId);
	}

	/**
	 * Returns the set of descendant object type defined for the repository
	 * under the specified type.
	 */
	@Override
	public List<TypeDefinitionContainer> getTypeDescendants(
			String repositoryId, String typeId, BigInteger depth,
			Boolean includePropertyDefinitions, ExtensionsData extension) {
		return getRepository(repositoryId).getTypeDescendants(getCallContext(),
				typeId, depth, includePropertyDefinitions);
	}

	// --- Discovery Service Implementation ---

	/**
	 * Executes a CMIS query statement against the contents of the repository.
	 */
	@Override
	public ObjectList query(String repositoryId, String statement,
			Boolean searchAllVersions, Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			BigInteger maxItems, BigInteger skipCount, ExtensionsData extension) {
		return getRepository(repositoryId).query(getCallContext(), statement,
				searchAllVersions, includeAllowableActions,
				includeRelationships, renditionFilter, maxItems, skipCount,
				extension);
	}

	@Override
	public ObjectList getContentChanges(String repositoryId,
			Holder<String> changeLogToken, Boolean includeProperties,
			String filter, Boolean includePolicyIds, Boolean includeAcl,
			BigInteger maxItems, ExtensionsData extension) {

		return getRepository(repositoryId).getContentChanges(repositoryId,
				changeLogToken, includeProperties, filter, includePolicyIds,
				includeAcl, maxItems, extension);
	}

	/*
	 * Internal methods
	 */

	/**
	 * Get repository that has given id.
	 */
	private NemakiRepository getRepository(String repositoryId) {
		for (NemakiRepository repository : repositoryMap.getRepositories()) {
			if (repository.hasThisRepositoryId(repositoryId)) {
				return repository;
			}
		}
		throw new CmisObjectNotFoundException("Unknown repository '"
				+ repositoryId + "'!");
	}

	/*
	 * Setters/Getters
	 */
	public void setCallContext(CallContext context) {
		this.context = context;
	}

	private CallContext getCallContext() {
		return context;
	}

}
