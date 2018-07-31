/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.co2.fire.web.beans.admin.modules.content;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import org.co2.fire.business.contenttypes.ContentTypeRegister;
import org.co2.fire.domain.contentdef.ContentContainerDefinition;
import org.co2.fire.web.beans.admin.modules.AdministrationModule;
import org.co2.fire.web.beans.admin.modules.content.type.ContainerDefinitionModel;

/**
 *
 * @author benjamin
 */
@Named
@RequestScoped
public class ContentTypeAdminSubModule implements AdministrationModule {

    private String name = "Gestion types contenu";
    private final String nameEdit = "Edition d'un type de contenu";
    private final String moduleURI = "/admin/content_type/type-list.html";
    private final String iconURI = "/images/content-type_edit.png";
    private String nameContentType;
    private String nameParentContentType;
    @ManagedProperty(value = "#{editNameContentType}")
    private String editNameContentType;
    private ContentContainerDefinition[] nodesToDelete;
    private final List<ContentContainerDefinition.TypeContainer> typeContainer = Arrays.asList(ContentContainerDefinition.TypeContainer.values());
    private List<ContentContainerDefinition> definitionAvailable;
    @Inject
    private ContentTypeRegister contentTypeManager;
    private ContainerDefinitionModel containerDefinition;
    private ContentContainerDefinition editDefinition;

    /**
     *
     * @throws Exception
     */
    @PostConstruct
    public void init() {
        try {
            this.definitionAvailable = contentTypeManager.getAllContentContainerDefinition();
            containerDefinition = new ContainerDefinitionModel(definitionAvailable);
        } catch (Exception ex) {

        }
    }

    /**
     *
     * @return list of contentContainerDefinition
     */
    public List<ContentContainerDefinition> getDefinitionAvailable() {
        return this.definitionAvailable;
    }

    /**
     *
     * @return moduleURI
     */
    @Override
    public String getModuleURI() {
        return moduleURI;
    }

    /**
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    public String getNameEdit() {
        return nameEdit;
    }

    /**
     *
     * @return nameContentType
     */
    public String getNameContentType() {
        return nameContentType;
    }

    /**
     *
     * @return nameParentContentType
     */
    public String getNameParentContentType() {
        return nameParentContentType;
    }

    /**
     *
     * @return nodesToDelete
     */
    public ContentContainerDefinition[] getNodesToDelete() {
        return nodesToDelete;
    }

    /**
     *
     * @return fieldEnum : enum[]
     */
    public List<ContentContainerDefinition.TypeContainer> getTypeContainers() {
        return typeContainer;
    }
    
    /**
     *
     * @param nameContentType
     */
    public void setNameContentType(String nameContentType) {
        this.nameContentType = nameContentType;
    }

    /**
     *
     * @param nameParentContentType
     */
    public void setNameParentContentType(String nameParentContentType) {
        this.nameParentContentType = nameParentContentType;
    }

    /**
     *
     * @param nodesToDelete
     */
    public void setNodesToDelete(ContentContainerDefinition[] nodesToDelete) {
        this.nodesToDelete = nodesToDelete;
    }

    /**
     * Function to create nex type content
     */
    public void createNewTypeContent() throws Exception {
        if (this.nameContentType != null && this.nameParentContentType != null) {
            boolean isPage = false;
            if (nameParentContentType.equals(ContentContainerDefinition.TypeContainer.PAGE.getContentName())) {
                isPage = true;
            }
            nameContentType = "cms:" + nameContentType;
            ContentContainerDefinition parent = this.contentTypeManager.getContentContainerDefinition(nameParentContentType);
            ContentContainerDefinition def = new ContentContainerDefinition(nameContentType, isPage, parent);
            this.contentTypeManager.createContentContainerDefinition(def);
            init();
        }
    }

    public void deleteTypeContent() throws Exception {
        if (this.nodesToDelete.length != 0) {
            this.contentTypeManager.deleteContentContainerDefinition(nodesToDelete);
            init();
        }
    }

    /**
     * Cette fonction
     *
     * @return
     * @throws Exception
     */
    public String editTypeContent() throws Exception {
        String url = "";
        if (this.editNameContentType != null) {
            this.editDefinition = this.contentTypeManager.getContentContainerDefinition(this.editNameContentType);
            url = "/editContentType.html";
        }
        return url;
    }

    /**
     * Cette fonction
     *
     * @return
     * @throws Exception
     */
    public String showEditTypeContent(String name) throws Exception {
        this.editDefinition = this.contentTypeManager.getContentContainerDefinition(name);
        return "edit-content-type.html";
    }

    public ContainerDefinitionModel getContainerDefinition() {
        return containerDefinition;
    }

    @Override
    public String getIconURI() {
        return this.iconURI;
    }

    @Override
    public String getRoleAllowed() {
        return "ROLE_ADMIN";
    }
}
