/*
 * XML Type:  ResourceType
 * Namespace: http://metadata.dod.mil/mdr/ns/DDMS/2.0/
 * Java type: mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType
 *
 * Automatically generated - do not modify.
 */
package mil.dod.metadata.mdr.ns.ddms._2_0;


/**
 * An XML ResourceType(@http://metadata.dod.mil/mdr/ns/DDMS/2.0/).
 *
 * This is a complex type.
 */
public interface ResourceType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ResourceType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB10C9499E0DFBB636176E7D7114A9D4F").resolveHandle("resourcetypeaafetype");
    
    /**
     * Gets array of all "identifier" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType[] getIdentifierArray();
    
    /**
     * Gets ith "identifier" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType getIdentifierArray(int i);
    
    /**
     * Returns number of "identifier" element
     */
    int sizeOfIdentifierArray();
    
    /**
     * Sets array of all "identifier" element
     */
    void setIdentifierArray(mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType[] identifierArray);
    
    /**
     * Sets ith "identifier" element
     */
    void setIdentifierArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType identifier);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "identifier" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType insertNewIdentifier(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "identifier" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundResourceIdentifierType addNewIdentifier();
    
    /**
     * Removes the ith "identifier" element
     */
    void removeIdentifier(int i);
    
    /**
     * Gets array of all "title" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.TitleType[] getTitleArray();
    
    /**
     * Gets ith "title" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.TitleType getTitleArray(int i);
    
    /**
     * Returns number of "title" element
     */
    int sizeOfTitleArray();
    
    /**
     * Sets array of all "title" element
     */
    void setTitleArray(mil.dod.metadata.mdr.ns.ddms._2_0.TitleType[] titleArray);
    
    /**
     * Sets ith "title" element
     */
    void setTitleArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.TitleType title);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "title" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.TitleType insertNewTitle(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "title" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.TitleType addNewTitle();
    
    /**
     * Removes the ith "title" element
     */
    void removeTitle(int i);
    
    /**
     * Gets array of all "subtitle" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType[] getSubtitleArray();
    
    /**
     * Gets ith "subtitle" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType getSubtitleArray(int i);
    
    /**
     * Returns number of "subtitle" element
     */
    int sizeOfSubtitleArray();
    
    /**
     * Sets array of all "subtitle" element
     */
    void setSubtitleArray(mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType[] subtitleArray);
    
    /**
     * Sets ith "subtitle" element
     */
    void setSubtitleArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType subtitle);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "subtitle" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType insertNewSubtitle(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "subtitle" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.SubtitleType addNewSubtitle();
    
    /**
     * Removes the ith "subtitle" element
     */
    void removeSubtitle(int i);
    
    /**
     * Gets the "description" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType getDescription();
    
    /**
     * True if has "description" element
     */
    boolean isSetDescription();
    
    /**
     * Sets the "description" element
     */
    void setDescription(mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType description);
    
    /**
     * Appends and returns a new empty "description" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.DescriptionType addNewDescription();
    
    /**
     * Unsets the "description" element
     */
    void unsetDescription();
    
    /**
     * Gets array of all "language" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType[] getLanguageArray();
    
    /**
     * Gets ith "language" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType getLanguageArray(int i);
    
    /**
     * Returns number of "language" element
     */
    int sizeOfLanguageArray();
    
    /**
     * Sets array of all "language" element
     */
    void setLanguageArray(mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType[] languageArray);
    
    /**
     * Sets ith "language" element
     */
    void setLanguageArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType language);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "language" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType insertNewLanguage(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "language" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundLanguageIdentifierType addNewLanguage();
    
    /**
     * Removes the ith "language" element
     */
    void removeLanguage(int i);
    
    /**
     * Gets the "dates" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.DatesType getDates();
    
    /**
     * True if has "dates" element
     */
    boolean isSetDates();
    
    /**
     * Sets the "dates" element
     */
    void setDates(mil.dod.metadata.mdr.ns.ddms._2_0.DatesType dates);
    
    /**
     * Appends and returns a new empty "dates" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.DatesType addNewDates();
    
    /**
     * Unsets the "dates" element
     */
    void unsetDates();
    
    /**
     * Gets the "rights" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.RightsType getRights();
    
    /**
     * True if has "rights" element
     */
    boolean isSetRights();
    
    /**
     * Sets the "rights" element
     */
    void setRights(mil.dod.metadata.mdr.ns.ddms._2_0.RightsType rights);
    
    /**
     * Appends and returns a new empty "rights" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.RightsType addNewRights();
    
    /**
     * Unsets the "rights" element
     */
    void unsetRights();
    
    /**
     * Gets array of all "source" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType[] getSourceArray();
    
    /**
     * Gets ith "source" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType getSourceArray(int i);
    
    /**
     * Returns number of "source" element
     */
    int sizeOfSourceArray();
    
    /**
     * Sets array of all "source" element
     */
    void setSourceArray(mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType[] sourceArray);
    
    /**
     * Sets ith "source" element
     */
    void setSourceArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType source);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "source" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType insertNewSource(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "source" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundSourceIdentifierType addNewSource();
    
    /**
     * Removes the ith "source" element
     */
    void removeSource(int i);
    
    /**
     * Gets array of all "type" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType[] getTypeArray();
    
    /**
     * Gets ith "type" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType getTypeArray(int i);
    
    /**
     * Returns number of "type" element
     */
    int sizeOfTypeArray();
    
    /**
     * Sets array of all "type" element
     */
    void setTypeArray(mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType[] typeArray);
    
    /**
     * Sets ith "type" element
     */
    void setTypeArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType type);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "type" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType insertNewType(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "type" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CompoundTypeIdentifierType addNewType();
    
    /**
     * Removes the ith "type" element
     */
    void removeType(int i);
    
    /**
     * Gets array of all "creator" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType[] getCreatorArray();
    
    /**
     * Gets ith "creator" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType getCreatorArray(int i);
    
    /**
     * Returns number of "creator" element
     */
    int sizeOfCreatorArray();
    
    /**
     * Sets array of all "creator" element
     */
    void setCreatorArray(mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType[] creatorArray);
    
    /**
     * Sets ith "creator" element
     */
    void setCreatorArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType creator);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "creator" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType insertNewCreator(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "creator" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.CreatorType addNewCreator();
    
    /**
     * Removes the ith "creator" element
     */
    void removeCreator(int i);
    
    /**
     * Gets array of all "publisher" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType[] getPublisherArray();
    
    /**
     * Gets ith "publisher" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType getPublisherArray(int i);
    
    /**
     * Returns number of "publisher" element
     */
    int sizeOfPublisherArray();
    
    /**
     * Sets array of all "publisher" element
     */
    void setPublisherArray(mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType[] publisherArray);
    
    /**
     * Sets ith "publisher" element
     */
    void setPublisherArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType publisher);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "publisher" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType insertNewPublisher(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "publisher" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.PublisherType addNewPublisher();
    
    /**
     * Removes the ith "publisher" element
     */
    void removePublisher(int i);
    
    /**
     * Gets array of all "contributor" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType[] getContributorArray();
    
    /**
     * Gets ith "contributor" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType getContributorArray(int i);
    
    /**
     * Returns number of "contributor" element
     */
    int sizeOfContributorArray();
    
    /**
     * Sets array of all "contributor" element
     */
    void setContributorArray(mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType[] contributorArray);
    
    /**
     * Sets ith "contributor" element
     */
    void setContributorArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType contributor);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "contributor" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType insertNewContributor(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "contributor" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.ContributorType addNewContributor();
    
    /**
     * Removes the ith "contributor" element
     */
    void removeContributor(int i);
    
    /**
     * Gets array of all "pointOfContact" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType[] getPointOfContactArray();
    
    /**
     * Gets ith "pointOfContact" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType getPointOfContactArray(int i);
    
    /**
     * Returns number of "pointOfContact" element
     */
    int sizeOfPointOfContactArray();
    
    /**
     * Sets array of all "pointOfContact" element
     */
    void setPointOfContactArray(mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType[] pointOfContactArray);
    
    /**
     * Sets ith "pointOfContact" element
     */
    void setPointOfContactArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType pointOfContact);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "pointOfContact" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType insertNewPointOfContact(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "pointOfContact" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.PointOfContactType addNewPointOfContact();
    
    /**
     * Removes the ith "pointOfContact" element
     */
    void removePointOfContact(int i);
    
    /**
     * Gets the "format" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.FormatType getFormat();
    
    /**
     * True if has "format" element
     */
    boolean isSetFormat();
    
    /**
     * Sets the "format" element
     */
    void setFormat(mil.dod.metadata.mdr.ns.ddms._2_0.FormatType format);
    
    /**
     * Appends and returns a new empty "format" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.FormatType addNewFormat();
    
    /**
     * Unsets the "format" element
     */
    void unsetFormat();
    
    /**
     * Gets the "subjectCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType getSubjectCoverage();
    
    /**
     * Sets the "subjectCoverage" element
     */
    void setSubjectCoverage(mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType subjectCoverage);
    
    /**
     * Appends and returns a new empty "subjectCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.SubjectCoverageType addNewSubjectCoverage();
    
    /**
     * Gets array of all "virtualCoverage" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType[] getVirtualCoverageArray();
    
    /**
     * Gets ith "virtualCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType getVirtualCoverageArray(int i);
    
    /**
     * Returns number of "virtualCoverage" element
     */
    int sizeOfVirtualCoverageArray();
    
    /**
     * Sets array of all "virtualCoverage" element
     */
    void setVirtualCoverageArray(mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType[] virtualCoverageArray);
    
    /**
     * Sets ith "virtualCoverage" element
     */
    void setVirtualCoverageArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType virtualCoverage);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "virtualCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType insertNewVirtualCoverage(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "virtualCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.VirtualCoverageType addNewVirtualCoverage();
    
    /**
     * Removes the ith "virtualCoverage" element
     */
    void removeVirtualCoverage(int i);
    
    /**
     * Gets array of all "temporalCoverage" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType[] getTemporalCoverageArray();
    
    /**
     * Gets ith "temporalCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType getTemporalCoverageArray(int i);
    
    /**
     * Returns number of "temporalCoverage" element
     */
    int sizeOfTemporalCoverageArray();
    
    /**
     * Sets array of all "temporalCoverage" element
     */
    void setTemporalCoverageArray(mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType[] temporalCoverageArray);
    
    /**
     * Sets ith "temporalCoverage" element
     */
    void setTemporalCoverageArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType temporalCoverage);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "temporalCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType insertNewTemporalCoverage(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "temporalCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.TemporalCoverageType addNewTemporalCoverage();
    
    /**
     * Removes the ith "temporalCoverage" element
     */
    void removeTemporalCoverage(int i);
    
    /**
     * Gets array of all "geospatialCoverage" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType[] getGeospatialCoverageArray();
    
    /**
     * Gets ith "geospatialCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType getGeospatialCoverageArray(int i);
    
    /**
     * Returns number of "geospatialCoverage" element
     */
    int sizeOfGeospatialCoverageArray();
    
    /**
     * Sets array of all "geospatialCoverage" element
     */
    void setGeospatialCoverageArray(mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType[] geospatialCoverageArray);
    
    /**
     * Sets ith "geospatialCoverage" element
     */
    void setGeospatialCoverageArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType geospatialCoverage);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "geospatialCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType insertNewGeospatialCoverage(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "geospatialCoverage" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.GeospatialCoverageType addNewGeospatialCoverage();
    
    /**
     * Removes the ith "geospatialCoverage" element
     */
    void removeGeospatialCoverage(int i);
    
    /**
     * Gets array of all "relatedResources" elements
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType[] getRelatedResourcesArray();
    
    /**
     * Gets ith "relatedResources" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType getRelatedResourcesArray(int i);
    
    /**
     * Returns number of "relatedResources" element
     */
    int sizeOfRelatedResourcesArray();
    
    /**
     * Sets array of all "relatedResources" element
     */
    void setRelatedResourcesArray(mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType[] relatedResourcesArray);
    
    /**
     * Sets ith "relatedResources" element
     */
    void setRelatedResourcesArray(int i, mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType relatedResources);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "relatedResources" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType insertNewRelatedResources(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "relatedResources" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.RelatedResourcesType addNewRelatedResources();
    
    /**
     * Removes the ith "relatedResources" element
     */
    void removeRelatedResources(int i);
    
    /**
     * Gets the "security" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security getSecurity();
    
    /**
     * Sets the "security" element
     */
    void setSecurity(mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security security);
    
    /**
     * Appends and returns a new empty "security" element
     */
    mil.dod.metadata.mdr.ns.ddms._2_0.SecurityDocument.Security addNewSecurity();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType newInstance() {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (mil.dod.metadata.mdr.ns.ddms._2_0.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
