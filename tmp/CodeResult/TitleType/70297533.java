/*
 * An XML document type.
 * Localname: title
 * Namespace: http://metadata.dod.mil/mdr/ns/DDMS/2.0/
 * Java type: mil.dod.metadata.mdr.ns.ddms._2_0.TitleDocument
 *
 * Automatically generated - do not modify.
 */
package mil.dod.metadata.mdr.ns.ddms._2_0.impl;
/**
 * A document containing one title(@http://metadata.dod.mil/mdr/ns/DDMS/2.0/) element.
 *
 * This is a complex type.
 */
public class TitleDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements mil.dod.metadata.mdr.ns.ddms._2_0.TitleDocument
{
    
    public TitleDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TITLE$0 = 
        new javax.xml.namespace.QName("http://metadata.dod.mil/mdr/ns/DDMS/2.0/", "title");
    
    
    /**
     * Gets the "title" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TitleType getTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TitleType)get_store().find_element_user(TITLE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "title" element
     */
    public void setTitle(mil.dod.metadata.mdr.ns.ddms._2_0.TitleType title)
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TitleType)get_store().find_element_user(TITLE$0, 0);
            if (target == null)
            {
                target = (mil.dod.metadata.mdr.ns.ddms._2_0.TitleType)get_store().add_element_user(TITLE$0);
            }
            target.set(title);
        }
    }
    
    /**
     * Appends and returns a new empty "title" element
     */
    public mil.dod.metadata.mdr.ns.ddms._2_0.TitleType addNewTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            mil.dod.metadata.mdr.ns.ddms._2_0.TitleType target = null;
            target = (mil.dod.metadata.mdr.ns.ddms._2_0.TitleType)get_store().add_element_user(TITLE$0);
            return target;
        }
    }
}
