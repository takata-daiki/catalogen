# PackageRelationship

***

## [Cluster 1 (equals, getrelationshiptype, rel)](./1)
1 results
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

## [Cluster 2 (packagerelationship, pic, rel)](./2)
2 results
> when the specified sheet is is the sheet which is true and the 2 are current ( < b > false < / i > ) . 
{% highlight java %}
536. PackageRelationship blipRel = packagePart.getRelationship(blipId);
550.         pic.getPartName(), TargetMode.INTERNAL, blipRel.getRelationshipType());
{% endhighlight %}

***

