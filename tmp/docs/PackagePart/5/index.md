# PackagePart @Cluster 5

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
86. PackagePart slidePart = corePart.getRelatedPart(
89. for(PackageRelationship rel : slidePart.getRelationshipsByType(OLE_OBJECT_REL_TYPE))
90.     embedds.add(slidePart.getRelatedPart(rel)); // TODO: Add this reference to each slide as well
92. for(PackageRelationship rel : slidePart.getRelationshipsByType(PACK_OBJECT_REL_TYPE))
93.       embedds.add(slidePart.getRelatedPart(rel));
{% endhighlight %}

***

### [AbstractOOXMLExtractor.java](https://searchcode.com/codesearch/view/111785571/)
{% highlight java %}
93. for(PackagePart part : mainParts) {
96.       rels = part.getRelationships();
{% endhighlight %}

***

### [OOXMLSignatureVerifierTest.java](https://searchcode.com/codesearch/view/7982558/)
{% highlight java %}
157. for (PackagePart part : parts) {
158.   LOG.debug("part name: " + part.getPartName().getName());
159.   LOG.debug("part content type: " + part.getContentType());
{% endhighlight %}

***

