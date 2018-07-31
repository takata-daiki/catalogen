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
package jp.aegif.nemaki.query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.aegif.nemaki.model.Content;
import jp.aegif.nemaki.model.Group;
import jp.aegif.nemaki.model.User;
import jp.aegif.nemaki.repository.TypeManager;
import jp.aegif.nemaki.service.NodeService;
import jp.aegif.nemaki.service.PermissionService;
import jp.aegif.nemaki.service.UserGroupService;

import org.apache.chemistry.opencmis.commons.data.ObjectData;
import org.apache.chemistry.opencmis.commons.data.ObjectList;
import org.apache.chemistry.opencmis.commons.data.PermissionMapping;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ObjectDataImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ObjectListImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertiesImpl;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.PropertyStringImpl;

/**
 * TODO This class is under construction.
 * 
 */
public class NonQueryProcessor implements QueryProcessor {

	private NodeService nodeService;
	private PermissionService permissionService;
	private UserGroupService userGroupService;

	private static Pattern p = Pattern.compile("name[\\s]*=[\\s]*'(.*)'");

	/**
	 * Process query without parser<br/>
	 * TODO should be changed to use any parser TODO get parameter to filter
	 * result
	 */
	public ObjectList query(TypeManager typeManager, String username,
			String id, String statement, Boolean searchAllVersions,
			Boolean includeAllowableActions,
			IncludeRelationships includeRelationships, String renditionFilter,
			BigInteger maxItems, BigInteger skipCount) {

		Matcher m = p.matcher(statement);
		String name = null;
		if (m.find())
			name = m.group(1);

		ObjectListImpl objectList = new ObjectListImpl();
		objectList.setObjects(new ArrayList<ObjectData>());
		if (statement.contains("aegif:user")) {
			objectList.getObjects().addAll(getUsers());
		} else if (statement.contains("aegif:group")) {
			objectList.getObjects().addAll(getGroups());
		} else {
			if (statement.contains("cmis:folder")) {
				objectList
						.getObjects()
						.addAll(getFolders(username,
								PermissionMapping.CAN_VIEW_CONTENT_OBJECT, name));
			}
			if (statement.contains("cmis:document")) {
				objectList
						.getObjects()
						.addAll(getDocuments(username,
								PermissionMapping.CAN_VIEW_CONTENT_OBJECT, name));
			}
		}
		return objectList;
	}

	private List<ObjectData> getDocuments(String username, String operation,
			String name) {
		List<Content> documents = permissionService.getFiltered(
				username,
				operation,
				Arrays.asList(nodeService.getDocuments(null).toArray(
						new Content[0])));
		List<ObjectData> dataList = new ArrayList<ObjectData>();
		for (Iterator<Content> it = documents.iterator(); it.hasNext();) {
			Content d = it.next();
			if (d.getName().indexOf(name) < 0)
				continue;
			PropertiesImpl props = new PropertiesImpl();
			props.addProperty(new PropertyStringImpl("id", d.getId()));
			props.addProperty(new PropertyStringImpl("type", d.getType()));
			props.addProperty(new PropertyStringImpl("name", d.getName()));
			ObjectDataImpl data = new ObjectDataImpl();
			data.setProperties(props);
			dataList.add(data);
		}
		return dataList;
	}

	private List<ObjectData> getFolders(String username, String operation,
			String name) {
		List<Content> folders = permissionService.getFiltered(
				username,
				operation,
				Arrays.asList(nodeService.getFolders(null).toArray(
						new Content[0])));
		List<ObjectData> dataList = new ArrayList<ObjectData>();
		for (Iterator<Content> it = folders.iterator(); it.hasNext();) {
			Content f = it.next();
			if (f.getName().indexOf(name) < 0)
				continue;
			PropertiesImpl props = new PropertiesImpl();
			props.addProperty(new PropertyStringImpl("id", f.getId()));
			props.addProperty(new PropertyStringImpl("type", f.getType()));
			props.addProperty(new PropertyStringImpl("name", f.getName()));
			ObjectDataImpl data = new ObjectDataImpl();
			data.setProperties(props);
			dataList.add(data);
		}
		return dataList;
	}

	private List<ObjectData> getGroups() {
		List<Group> groups = userGroupService.getGroups();
		List<ObjectData> dataList = new ArrayList<ObjectData>();
		for (Iterator<Group> it = groups.iterator(); it.hasNext();) {
			Group g = it.next();
			PropertiesImpl props = new PropertiesImpl();
			props.addProperty(new PropertyStringImpl("id", g.getId()));
			ObjectDataImpl data = new ObjectDataImpl();
			data.setProperties(props);
			dataList.add(data);
		}
		return dataList;
	}

	private List<ObjectData> getUsers() {
		List<User> users = userGroupService.getUsers();
		List<ObjectData> dataList = new ArrayList<ObjectData>();
		for (Iterator<User> it = users.iterator(); it.hasNext();) {
			User u = it.next();
			PropertiesImpl props = new PropertiesImpl();
			props.addProperty(new PropertyStringImpl("id", u.getId()));
			props.addProperty(new PropertyStringImpl("name", u.getName()));
			props.addProperty(new PropertyStringImpl("firstName", u
					.getFirstName()));
			props.addProperty(new PropertyStringImpl("lastName", u
					.getLastName()));
			ObjectDataImpl data = new ObjectDataImpl();
			data.setProperties(props);
			dataList.add(data);
		}
		return dataList;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

}
