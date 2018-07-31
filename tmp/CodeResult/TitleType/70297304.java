/*
 * XML Type:  ResourceType
 * Namespace: http://metadata.dod.mil/mdr/ns/DDMS/2.0/
 * Java type: mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType
 *
 * Automatically generated - do not modify.
 */
package mil.dod.metadata.mdr.ns.ddms._2_0.impl;
/**
 * An XML ResourceType(@http://metadata.dod.mil/mdr/ns/DDMS/2.0/).
 *
 * This is a complex type.
 */
public class ResourceTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType
{
    
    public ResourceTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName IDENTIFIER$0 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "identifier");
    private static final javax.xml.namespace.QName TITLE$2 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "title");
    private static final javax.xml.namespace.QName SUBTITLE$4 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "subtitle");
    private static final javax.xml.namespace.QName DESCRIPTION$6 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "description");
    private static final javax.xml.namespace.QName LANGUAGE$8 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "language");
    private static final javax.xml.namespace.QName DATES$10 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "dates");
    private static final javax.xml.namespace.QName RIGHTS$12 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "rights");
    private static final javax.xml.namespace.QName SOURCE$14 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "source");
    private static final javax.xml.namespace.QName TYPE$16 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "type");
    private static final javax.xml.namespace.QName CREATOR$18 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "creator");
    private static final javax.xml.namespace.QName PUBLISHER$20 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "publisher");
    private static final javax.xml.namespace.QName CONTRIBUTOR$22 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "contributor");
    private static final javax.xml.namespace.QName POINTOFCONTACT$24 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "pointOfContact");
    private static final javax.xml.namespace.QName FORMAT$26 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "format");
    private static final javax.xml.namespace.QName SUBJECTCOVERAGE$28 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "subjectCoverage");
    private static final javax.xml.namespace.QName VIRTUALCOVERAGE$30 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "virtualCoverage");
    private static final javax.xml.namespace.QName TEMPORALCOVERAGE$32 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "temporalCoverage");
    private static final javax.xml.namespace.QName GEOSPATIALCOVERAGE$34 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "geospatialCoverage");
    private static final javax.xml.namespace.QName RELATEDRESOURCES$36 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "relatedResources");
    private static final javax.xml.namespace.QName SECURITY$38 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "security");
    
    
    /**
     * Gets array of all "identifier" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType[] getIdentifierArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(IDENTIFIER$0, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "identifier" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType getIdentifierArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType)get_store().find_element_user(IDENTIFIER$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "identifier" element
     */
    public int sizeOfIdentifierArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IDENTIFIER$0);
        }
    }
    
    /**
     * Sets array of all "identifier" element
     */
    public void setIdentifierArray(mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType[] identifierArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(identifierArray, IDENTIFIER$0);
        }
    }
    
    /**
     * Sets ith "identifier" element
     */
    public void setIdentifierArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType identifier)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType)get_store().find_element_user(IDENTIFIER$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(identifier);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "identifier" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType insertNewIdentifier(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType)get_store().insert_element_user(IDENTIFIER$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "identifier" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType addNewIdentifier()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType)get_store().add_element_user(IDENTIFIER$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "identifier" element
     */
    public void removeIdentifier(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IDENTIFIER$0, i);
        }
    }
    
    /**
     * Gets array of all "title" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TitleType[] getTitleArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TITLE$2, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.TitleType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.TitleType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "title" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TitleType getTitleArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TitleType)get_store().find_element_user(TITLE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "title" element
     */
    public int sizeOfTitleArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TITLE$2);
        }
    }
    
    /**
     * Sets array of all "title" element
     */
    public void setTitleArray(mil.dod.metadata.mdr.ns.ddms._2_0.TitleType[] titleArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(titleArray, TITLE$2);
        }
    }
    
    /**
     * Sets ith "title" element
     */
    public void setTitleArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.TitleType title)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TitleType)get_store().find_element_user(TITLE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(title);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "title" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TitleType insertNewTitle(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TitleType)get_store().insert_element_user(TITLE$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "title" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TitleType addNewTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TitleType)get_store().add_element_user(TITLE$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "title" element
     */
    public void removeTitle(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TITLE$2, i);
        }
    }
    
    /**
     * Gets array of all "subtitle" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType[] getSubtitleArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SUBTITLE$4, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "subtitle" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType getSubtitleArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType)get_store().find_element_user(SUBTITLE$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "subtitle" element
     */
    public int sizeOfSubtitleArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUBTITLE$4);
        }
    }
    
    /**
     * Sets array of all "subtitle" element
     */
    public void setSubtitleArray(mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType[] subtitleArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(subtitleArray, SUBTITLE$4);
        }
    }
    
    /**
     * Sets ith "subtitle" element
     */
    public void setSubtitleArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType subtitle)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType)get_store().find_element_user(SUBTITLE$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(subtitle);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "subtitle" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType insertNewSubtitle(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType)get_store().insert_element_user(SUBTITLE$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "subtitle" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType addNewSubtitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType)get_store().add_element_user(SUBTITLE$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "subtitle" element
     */
    public void removeSubtitle(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUBTITLE$4, i);
        }
    }
    
    /**
     * Gets the "description" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType getDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType)get_store().find_element_user(DESCRIPTION$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "description" element
     */
    public boolean isSetDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESCRIPTION$6) != 0;
        }
    }
    
    /**
     * Sets the "description" element
     */
    public void setDescription(mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType description)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType)get_store().find_element_user(DESCRIPTION$6, 0);
            if (target == null)
            {
                target = (mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType)get_store().add_element_user(DESCRIPTION$6);
            }
            target.set(description);
        }
    }
    
    /**
     * Appends and returns a new empty "description" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType addNewDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType)get_store().add_element_user(DESCRIPTION$6);
            return target;
        }
    }
    
    /**
     * Unsets the "description" element
     */
    public void unsetDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESCRIPTION$6, 0);
        }
    }
    
    /**
     * Gets array of all "language" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType[] getLanguageArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(LANGUAGE$8, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "language" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType getLanguageArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType)get_store().find_element_user(LANGUAGE$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "language" element
     */
    public int sizeOfLanguageArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LANGUAGE$8);
        }
    }
    
    /**
     * Sets array of all "language" element
     */
    public void setLanguageArray(mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType[] languageArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(languageArray, LANGUAGE$8);
        }
    }
    
    /**
     * Sets ith "language" element
     */
    public void setLanguageArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType language)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType)get_store().find_element_user(LANGUAGE$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(language);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "language" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType insertNewLanguage(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType)get_store().insert_element_user(LANGUAGE$8, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "language" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType addNewLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType)get_store().add_element_user(LANGUAGE$8);
            return target;
        }
    }
    
    /**
     * Removes the ith "language" element
     */
    public void removeLanguage(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LANGUAGE$8, i);
        }
    }
    
    /**
     * Gets the "dates" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.DatesType getDates()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.DatesType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.DatesType)get_store().find_element_user(DATES$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "dates" element
     */
    public boolean isSetDates()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATES$10) != 0;
        }
    }
    
    /**
     * Sets the "dates" element
     */
    public void setDates(mil.dod.metadata.mdr.ns.ddms._2_0.DatesType dates)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.DatesType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.DatesType)get_store().find_element_user(DATES$10, 0);
            if (target == null)
            {
                target = (mil.dod.metadata.mdr.ns.ddms._2_0.DatesType)get_store().add_element_user(DATES$10);
            }
            target.set(dates);
        }
    }
    
    /**
     * Appends and returns a new empty "dates" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.DatesType addNewDates()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.DatesType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.DatesType)get_store().add_element_user(DATES$10);
            return target;
        }
    }
    
    /**
     * Unsets the "dates" element
     */
    public void unsetDates()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATES$10, 0);
        }
    }
    
    /**
     * Gets the "rights" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.RightsType getRights()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.RightsType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.RightsType)get_store().find_element_user(RIGHTS$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "rights" element
     */
    public boolean isSetRights()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RIGHTS$12) != 0;
        }
    }
    
    /**
     * Sets the "rights" element
     */
    public void setRights(mil.dod.metadata.mdr.ns.ddms._2_0.RightsType rights)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.RightsType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.RightsType)get_store().find_element_user(RIGHTS$12, 0);
            if (target == null)
            {
                target = (mil.dod.metadata.mdr.ns.ddms._2_0.RightsType)get_store().add_element_user(RIGHTS$12);
            }
            target.set(rights);
        }
    }
    
    /**
     * Appends and returns a new empty "rights" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.RightsType addNewRights()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.RightsType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.RightsType)get_store().add_element_user(RIGHTS$12);
            return target;
        }
    }
    
    /**
     * Unsets the "rights" element
     */
    public void unsetRights()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RIGHTS$12, 0);
        }
    }
    
    /**
     * Gets array of all "source" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType[] getSourceArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SOURCE$14, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "source" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType getSourceArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType)get_store().find_element_user(SOURCE$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "source" element
     */
    public int sizeOfSourceArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SOURCE$14);
        }
    }
    
    /**
     * Sets array of all "source" element
     */
    public void setSourceArray(mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType[] sourceArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(sourceArray, SOURCE$14);
        }
    }
    
    /**
     * Sets ith "source" element
     */
    public void setSourceArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType source)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType)get_store().find_element_user(SOURCE$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(source);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "source" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType insertNewSource(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType)get_store().insert_element_user(SOURCE$14, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "source" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType addNewSource()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType)get_store().add_element_user(SOURCE$14);
            return target;
        }
    }
    
    /**
     * Removes the ith "source" element
     */
    public void removeSource(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SOURCE$14, i);
        }
    }
    
    /**
     * Gets array of all "type" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType[] getTypeArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TYPE$16, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "type" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType getTypeArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType)get_store().find_element_user(TYPE$16, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "type" element
     */
    public int sizeOfTypeArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TYPE$16);
        }
    }
    
    /**
     * Sets array of all "type" element
     */
    public void setTypeArray(mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType[] typeArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(typeArray, TYPE$16);
        }
    }
    
    /**
     * Sets ith "type" element
     */
    public void setTypeArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType)get_store().find_element_user(TYPE$16, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(type);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "type" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType insertNewType(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType)get_store().insert_element_user(TYPE$16, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "type" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType addNewType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType)get_store().add_element_user(TYPE$16);
            return target;
        }
    }
    
    /**
     * Removes the ith "type" element
     */
    public void removeType(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TYPE$16, i);
        }
    }
    
    /**
     * Gets array of all "creator" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType[] getCreatorArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CREATOR$18, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "creator" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType getCreatorArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType)get_store().find_element_user(CREATOR$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "creator" element
     */
    public int sizeOfCreatorArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CREATOR$18);
        }
    }
    
    /**
     * Sets array of all "creator" element
     */
    public void setCreatorArray(mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType[] creatorArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(creatorArray, CREATOR$18);
        }
    }
    
    /**
     * Sets ith "creator" element
     */
    public void setCreatorArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType creator)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType)get_store().find_element_user(CREATOR$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(creator);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "creator" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType insertNewCreator(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType)get_store().insert_element_user(CREATOR$18, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "creator" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType addNewCreator()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType)get_store().add_element_user(CREATOR$18);
            return target;
        }
    }
    
    /**
     * Removes the ith "creator" element
     */
    public void removeCreator(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CREATOR$18, i);
        }
    }
    
    /**
     * Gets array of all "publisher" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType[] getPublisherArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PUBLISHER$20, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "publisher" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType getPublisherArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType)get_store().find_element_user(PUBLISHER$20, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "publisher" element
     */
    public int sizeOfPublisherArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PUBLISHER$20);
        }
    }
    
    /**
     * Sets array of all "publisher" element
     */
    public void setPublisherArray(mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType[] publisherArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(publisherArray, PUBLISHER$20);
        }
    }
    
    /**
     * Sets ith "publisher" element
     */
    public void setPublisherArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType publisher)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType)get_store().find_element_user(PUBLISHER$20, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(publisher);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "publisher" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType insertNewPublisher(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType)get_store().insert_element_user(PUBLISHER$20, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "publisher" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType addNewPublisher()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType)get_store().add_element_user(PUBLISHER$20);
            return target;
        }
    }
    
    /**
     * Removes the ith "publisher" element
     */
    public void removePublisher(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PUBLISHER$20, i);
        }
    }
    
    /**
     * Gets array of all "contributor" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType[] getContributorArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CONTRIBUTOR$22, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "contributor" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType getContributorArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType)get_store().find_element_user(CONTRIBUTOR$22, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "contributor" element
     */
    public int sizeOfContributorArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONTRIBUTOR$22);
        }
    }
    
    /**
     * Sets array of all "contributor" element
     */
    public void setContributorArray(mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType[] contributorArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(contributorArray, CONTRIBUTOR$22);
        }
    }
    
    /**
     * Sets ith "contributor" element
     */
    public void setContributorArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType contributor)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType)get_store().find_element_user(CONTRIBUTOR$22, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(contributor);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "contributor" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType insertNewContributor(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType)get_store().insert_element_user(CONTRIBUTOR$22, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "contributor" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType addNewContributor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType)get_store().add_element_user(CONTRIBUTOR$22);
            return target;
        }
    }
    
    /**
     * Removes the ith "contributor" element
     */
    public void removeContributor(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONTRIBUTOR$22, i);
        }
    }
    
    /**
     * Gets array of all "pointOfContact" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType[] getPointOfContactArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(POINTOFCONTACT$24, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "pointOfContact" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType getPointOfContactArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType)get_store().find_element_user(POINTOFCONTACT$24, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "pointOfContact" element
     */
    public int sizeOfPointOfContactArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(POINTOFCONTACT$24);
        }
    }
    
    /**
     * Sets array of all "pointOfContact" element
     */
    public void setPointOfContactArray(mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType[] pointOfContactArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(pointOfContactArray, POINTOFCONTACT$24);
        }
    }
    
    /**
     * Sets ith "pointOfContact" element
     */
    public void setPointOfContactArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType pointOfContact)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType)get_store().find_element_user(POINTOFCONTACT$24, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(pointOfContact);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "pointOfContact" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType insertNewPointOfContact(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType)get_store().insert_element_user(POINTOFCONTACT$24, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "pointOfContact" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType addNewPointOfContact()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType)get_store().add_element_user(POINTOFCONTACT$24);
            return target;
        }
    }
    
    /**
     * Removes the ith "pointOfContact" element
     */
    public void removePointOfContact(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(POINTOFCONTACT$24, i);
        }
    }
    
    /**
     * Gets the "format" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.FormatType getFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.FormatType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.FormatType)get_store().find_element_user(FORMAT$26, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "format" element
     */
    public boolean isSetFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FORMAT$26) != 0;
        }
    }
    
    /**
     * Sets the "format" element
     */
    public void setFormat(mil.dod.metadata.mdr.ns.ddms._2_0.FormatType format)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.FormatType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.FormatType)get_store().find_element_user(FORMAT$26, 0);
            if (target == null)
            {
                target = (mil.dod.metadata.mdr.ns.ddms._2_0.FormatType)get_store().add_element_user(FORMAT$26);
            }
            target.set(format);
        }
    }
    
    /**
     * Appends and returns a new empty "format" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.FormatType addNewFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.FormatType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.FormatType)get_store().add_element_user(FORMAT$26);
            return target;
        }
    }
    
    /**
     * Unsets the "format" element
     */
    public void unsetFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FORMAT$26, 0);
        }
    }
    
    /**
     * Gets the "subjectCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType getSubjectCoverage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType)get_store().find_element_user(SUBJECTCOVERAGE$28, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "subjectCoverage" element
     */
    public void setSubjectCoverage(mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType subjectCoverage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType)get_store().find_element_user(SUBJECTCOVERAGE$28, 0);
            if (target == null)
            {
                target = (mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType)get_store().add_element_user(SUBJECTCOVERAGE$28);
            }
            target.set(subjectCoverage);
        }
    }
    
    /**
     * Appends and returns a new empty "subjectCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType addNewSubjectCoverage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType)get_store().add_element_user(SUBJECTCOVERAGE$28);
            return target;
        }
    }
    
    /**
     * Gets array of all "virtualCoverage" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType[] getVirtualCoverageArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(VIRTUALCOVERAGE$30, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "virtualCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType getVirtualCoverageArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType)get_store().find_element_user(VIRTUALCOVERAGE$30, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "virtualCoverage" element
     */
    public int sizeOfVirtualCoverageArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VIRTUALCOVERAGE$30);
        }
    }
    
    /**
     * Sets array of all "virtualCoverage" element
     */
    public void setVirtualCoverageArray(mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType[] virtualCoverageArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(virtualCoverageArray, VIRTUALCOVERAGE$30);
        }
    }
    
    /**
     * Sets ith "virtualCoverage" element
     */
    public void setVirtualCoverageArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType virtualCoverage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType)get_store().find_element_user(VIRTUALCOVERAGE$30, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(virtualCoverage);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "virtualCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType insertNewVirtualCoverage(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType)get_store().insert_element_user(VIRTUALCOVERAGE$30, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "virtualCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType addNewVirtualCoverage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType)get_store().add_element_user(VIRTUALCOVERAGE$30);
            return target;
        }
    }
    
    /**
     * Removes the ith "virtualCoverage" element
     */
    public void removeVirtualCoverage(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VIRTUALCOVERAGE$30, i);
        }
    }
    
    /**
     * Gets array of all "temporalCoverage" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType[] getTemporalCoverageArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TEMPORALCOVERAGE$32, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "temporalCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType getTemporalCoverageArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType)get_store().find_element_user(TEMPORALCOVERAGE$32, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "temporalCoverage" element
     */
    public int sizeOfTemporalCoverageArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TEMPORALCOVERAGE$32);
        }
    }
    
    /**
     * Sets array of all "temporalCoverage" element
     */
    public void setTemporalCoverageArray(mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType[] temporalCoverageArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(temporalCoverageArray, TEMPORALCOVERAGE$32);
        }
    }
    
    /**
     * Sets ith "temporalCoverage" element
     */
    public void setTemporalCoverageArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType temporalCoverage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType)get_store().find_element_user(TEMPORALCOVERAGE$32, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(temporalCoverage);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "temporalCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType insertNewTemporalCoverage(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType)get_store().insert_element_user(TEMPORALCOVERAGE$32, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "temporalCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType addNewTemporalCoverage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType)get_store().add_element_user(TEMPORALCOVERAGE$32);
            return target;
        }
    }
    
    /**
     * Removes the ith "temporalCoverage" element
     */
    public void removeTemporalCoverage(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TEMPORALCOVERAGE$32, i);
        }
    }
    
    /**
     * Gets array of all "geospatialCoverage" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType[] getGeospatialCoverageArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(GEOSPATIALCOVERAGE$34, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "geospatialCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType getGeospatialCoverageArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType)get_store().find_element_user(GEOSPATIALCOVERAGE$34, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "geospatialCoverage" element
     */
    public int sizeOfGeospatialCoverageArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GEOSPATIALCOVERAGE$34);
        }
    }
    
    /**
     * Sets array of all "geospatialCoverage" element
     */
    public void setGeospatialCoverageArray(mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType[] geospatialCoverageArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(geospatialCoverageArray, GEOSPATIALCOVERAGE$34);
        }
    }
    
    /**
     * Sets ith "geospatialCoverage" element
     */
    public void setGeospatialCoverageArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType geospatialCoverage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType)get_store().find_element_user(GEOSPATIALCOVERAGE$34, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(geospatialCoverage);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "geospatialCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType insertNewGeospatialCoverage(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType)get_store().insert_element_user(GEOSPATIALCOVERAGE$34, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "geospatialCoverage" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType addNewGeospatialCoverage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType)get_store().add_element_user(GEOSPATIALCOVERAGE$34);
            return target;
        }
    }
    
    /**
     * Removes the ith "geospatialCoverage" element
     */
    public void removeGeospatialCoverage(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GEOSPATIALCOVERAGE$34, i);
        }
    }
    
    /**
     * Gets array of all "relatedResources" elements
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType[] getRelatedResourcesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(RELATEDRESOURCES$36, targetList);
            mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType[] result = new mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "relatedResources" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType getRelatedResourcesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType)get_store().find_element_user(RELATEDRESOURCES$36, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "relatedResources" element
     */
    public int sizeOfRelatedResourcesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RELATEDRESOURCES$36);
        }
    }
    
    /**
     * Sets array of all "relatedResources" element
     */
    public void setRelatedResourcesArray(mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType[] relatedResourcesArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(relatedResourcesArray, RELATEDRESOURCES$36);
        }
    }
    
    /**
     * Sets ith "relatedResources" element
     */
    public void setRelatedResourcesArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType relatedResources)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType)get_store().find_element_user(RELATEDRESOURCES$36, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(relatedResources);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "relatedResources" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType insertNewRelatedResources(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType)get_store().insert_element_user(RELATEDRESOURCES$36, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "relatedResources" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType addNewRelatedResources()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType)get_store().add_element_user(RELATEDRESOURCES$36);
            return target;
        }
    }
    
    /**
     * Removes the ith "relatedResources" element
     */
    public void removeRelatedResources(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RELATEDRESOURCES$36, i);
        }
    }
    
    /**
     * Gets the "security" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security getSecurity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security)get_store().find_element_user(SECURITY$38, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "security" element
     */
    public void setSecurity(mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security security)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security)get_store().find_element_user(SECURITY$38, 0);
            if (target == null)
            {
                target = (mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security)get_store().add_element_user(SECURITY$38);
            }
            target.set(security);
        }
    }
    
    /**
     * Appends and returns a new empty "security" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security addNewSecurity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security)get_store().add_element_user(SECURITY$38);
            return target;
        }
    }
}
