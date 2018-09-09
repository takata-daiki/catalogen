# PackageRelationship

***

### [Cluster 1](./1)
{% highlight java %}
191. PackageRelationship rel = getPackagePart().addRelationship(
193. addRelation(rel.getId(), new XSLFPictureData(pic, rel));
195. XSLFPictureShape sh = getDrawing().createPicture(rel.getId());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
251. PackageRelationship rel = _sheet.getPackagePart().addRelationship(
254. XSLFPictureShape sh = getDrawing().createPicture(rel.getId());
{% endhighlight %}

***

### [Cluster 3](./3)
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

### [Cluster 4](./4)
{% highlight java %}
38. for (PackageRelationship rel : pack.getRelationshipsByType(TKitRelationshipTypes.CORE_DOCUMENT))
40.     res.add(pack.getPart(PackagingURIHelper.createPartName(rel.getTargetURI())));
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
125. protected void handleEmbedded(PackageRelationship rel, PackagePart part, 
129.    String name = rel.getTargetURI().toString();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
536. PackageRelationship blipRel = packagePart.getRelationship(blipId);
550.         pic.getPartName(), TargetMode.INTERNAL, blipRel.getRelationshipType());
{% endhighlight %}

***

