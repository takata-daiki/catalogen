# XSLFSheet @Cluster 1

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
303. public XSLFSheet importContent(XSLFSheet src){
309.     getSpTree().set(src.getSpTree());
313.     List<XSLFShape> srcShapes = src.getShapeList();
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
329. public XSLFSheet appendContent(XSLFSheet src){
333.     CTGroupShape srcTree = src.getSpTree();
355.     List<XSLFShape> srcShapes = src.getShapeList();
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
494. XSLFSheet master = getMasterSheet();
495. if(getFollowMasterGraphics() && master != null) master.draw(graphics, isCanceled, handler, position);
{% endhighlight %}

***

### [XSLFSimpleShape.java](https://searchcode.com/codesearch/view/97406763/)
{% highlight java %}
592. XSLFSheet masterSheet = getSheet().getMasterSheet();
597.         masterShape = masterSheet.getPlaceholder(ph);
623.         XSLFSheet master = masterSheet.getMasterSheet();
{% endhighlight %}

***

### [XSLFSimpleShape.java](https://searchcode.com/codesearch/view/97406763/)
{% highlight java %}
623. XSLFSheet master = masterSheet.getMasterSheet();
625.     masterShape = master.getPlaceholderByType(textType);
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
{% highlight java %}
1078. XSLFSheet masterSheet = _shape.getSheet();
1079. while (masterSheet.getMasterSheet() != null){
1080.     masterSheet = masterSheet.getMasterSheet();
1083. XmlObject[] o = masterSheet.getXmlObject().selectPath(
{% endhighlight %}

***

### [XSLFSlide.java](https://searchcode.com/codesearch/view/97406624/)
{% highlight java %}
229. public XSLFSlide importContent(XSLFSheet src){
239.             String relId = importBlip(blipId, src.getPackagePart());
{% endhighlight %}

***

