/**
 * MapInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.lbslocal.cc.objects.v0.common;

public class MapInfo  {
    private java.lang.String url;
    private Extent extent;

    public MapInfo() {
    	this.url = "";
        this.extent = new Extent();
    }

    public MapInfo(
           java.lang.String url,
           Extent extent) {
           this.url = url;
           this.extent = extent;
    }


    /**
     * Gets the url value for this MapInfo.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this MapInfo.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }


    /**
     * Gets the extent value for this MapInfo.
     * 
     * @return extent
     */
    public Extent getExtent() {
        return extent;
    }


    /**
     * Sets the extent value for this MapInfo.
     * 
     * @param extent
     */
    public void setExtent(Extent extent) {
        this.extent = extent;
    }

}
