# XSLFSheet

***

### [Cluster 1](./1)
{% highlight java %}
303. public XSLFSheet importContent(XSLFSheet src){
309.     getSpTree().set(src.getSpTree());
313.     List<XSLFShape> srcShapes = src.getShapeList();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
229. public XSLFSlide importContent(XSLFSheet src){
239.             String relId = importBlip(blipId, src.getPackagePart());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
56. private final XSLFSheet _sheet;
65.     _shapes = _sheet.buildShapes(_shape);
242.     List<PackagePart>  pics = _sheet.getPackagePart().getPackage()
251.     PackageRelationship rel = _sheet.getPackagePart().addRelationship(
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
592. XSLFSheet masterSheet = getSheet().getMasterSheet();
597.         masterShape = masterSheet.getPlaceholder(ph);
623.         XSLFSheet master = masterSheet.getMasterSheet();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
1078. XSLFSheet masterSheet = _shape.getSheet();
1079. while (masterSheet.getMasterSheet() != null){
1080.     masterSheet = masterSheet.getMasterSheet();
1083. XmlObject[] o = masterSheet.getXmlObject().selectPath(
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
494. XSLFSheet master = getMasterSheet();
495. if(getFollowMasterGraphics() && master != null) master.draw(graphics, isCanceled, handler, position);
{% endhighlight %}

***

