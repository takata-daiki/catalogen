# PackageRelationship @Cluster 1

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
582. public PackagePart getPart(PackageRelationship partRel) {
586.     if (rel.getRelationshipType().equals(partRel.getRelationshipType())) {
{% endhighlight %}

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
585. for (PackageRelationship rel : relationships) {
586.   if (rel.getRelationshipType().equals(partRel.getRelationshipType())) {
588.       retPart = getPart(PackagingURIHelper.createPartName(rel
{% endhighlight %}

***

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
{% highlight java %}
166. for(PackageRelationship rel : part.getRelationshipsByType(XSSFRelation.DRAWINGS.getRelation())) {
167.    if(rel.getTargetMode() == TargetMode.INTERNAL) {
168.       PackagePartName relName = PackagingURIHelper.createPartName(rel.getTargetURI());
169.       parts.add( rel.getPackage().getPart(relName) );
{% endhighlight %}

***

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
{% highlight java %}
172. for(PackageRelationship rel : part.getRelationshipsByType(XSSFRelation.VML_DRAWINGS.getRelation())) {
173.    if(rel.getTargetMode() == TargetMode.INTERNAL) {
174.       PackagePartName relName = PackagingURIHelper.createPartName(rel.getTargetURI());
175.       parts.add( rel.getPackage().getPart(relName) );
{% endhighlight %}

***

### [AbstractOOXMLExtractor.java](https://searchcode.com/codesearch/view/111785571/)
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

