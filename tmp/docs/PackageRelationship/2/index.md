# PackageRelationship @Cluster 2

***

### [AbstractOOXMLExtractor.java](https://searchcode.com/codesearch/view/111785571/)
{% highlight java %}
125. protected void handleEmbedded(PackageRelationship rel, PackagePart part, 
129.    String name = rel.getTargetURI().toString();
{% endhighlight %}

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
{% highlight java %}
51. for (PackageRelationship rel : pack.getRelationshipsByType(TKitRelationshipTypes.CORE_DOCUMENT))
53.     res.add(pack.getPart(PackagingURIHelper.createPartName(rel.getTargetURI())));
{% endhighlight %}

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
985. for (PackageRelationship relationship : partToDelete
989.           partName.getURI(), relationship.getTargetURI()));
{% endhighlight %}

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
{% highlight java %}
38. for (PackageRelationship rel : pack.getRelationshipsByType(TKitRelationshipTypes.CORE_DOCUMENT))
40.     res.add(pack.getPart(PackagingURIHelper.createPartName(rel.getTargetURI())));
{% endhighlight %}

***

### [XSLFGroupShape.java](https://searchcode.com/codesearch/view/97406700/)
{% highlight java %}
251. PackageRelationship rel = _sheet.getPackagePart().addRelationship(
254. XSLFPictureShape sh = getDrawing().createPicture(rel.getId());
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
536. PackageRelationship blipRel = packagePart.getRelationship(blipId);
550.         pic.getPartName(), TargetMode.INTERNAL, blipRel.getRelationshipType());
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
191. PackageRelationship rel = getPackagePart().addRelationship(
193. addRelation(rel.getId(), new XSLFPictureData(pic, rel));
195. XSLFPictureShape sh = getDrawing().createPicture(rel.getId());
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
549. PackageRelationship rel = getPackagePart().addRelationship(
551. addRelation(rel.getId(), new XSLFPictureData(pic, rel));
553. return rel.getId();
{% endhighlight %}

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
932. for (PackageRelationship rel : partRels) {
934.       .createPartName(PackagingURIHelper.resolvePartUri(rel
935.           .getSourceURI(), rel.getTargetURI()));
{% endhighlight %}

***

