# PackageRelationship @Cluster 1 (equals, getrelationshiptype, rel)

***

### [AbstractOOXMLExtractor.java](https://searchcode.com/codesearch/view/111785571/)
> add comments to the list of ) and ( ) on all sheets from the existing workbook . 
{% highlight java %}
101. for(PackageRelationship rel : rels) {
103.    if( rel.getRelationshipType().equals(RELATION_AUDIO) ||
104.        rel.getRelationshipType().equals(RELATION_IMAGE) ||
105.        rel.getRelationshipType().equals(RELATION_OLE_OBJECT) ||
106.        rel.getRelationshipType().equals(RELATION_PACKAGE) ) {
107.       if(rel.getTargetMode() == TargetMode.INTERNAL) {
110.             relName = PackagingURIHelper.createPartName(rel.getTargetURI());
114.          PackagePart relPart = rel.getPackage().getPart(relName);
{% endhighlight %}

***

