# XSLFSheet @Cluster 2 (getmastersheet, mastersheet, src)

***

### [XSLFSimpleShape.java](https://searchcode.com/codesearch/view/97406763/)
> change } and from a new only should be called on the document . 
{% highlight java %}
623. XSLFSheet master = masterSheet.getMasterSheet();
625.     masterShape = master.getPlaceholderByType(textType);
{% endhighlight %}

***

### [XSLFSlide.java](https://searchcode.com/codesearch/view/97406624/)
> test that we get the same value as excel and , for 
{% highlight java %}
229. public XSLFSlide importContent(XSLFSheet src){
239.             String relId = importBlip(blipId, src.getPackagePart());
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
> test that we get the same value as excel and , for 
{% highlight java %}
303. public XSLFSheet importContent(XSLFSheet src){
309.     getSpTree().set(src.getSpTree());
313.     List<XSLFShape> srcShapes = src.getShapeList();
{% endhighlight %}

***

### [XSLFSimpleShape.java](https://searchcode.com/codesearch/view/97406763/)
> 8 5 0 2 6 5 5 if the type is not given with this set of . 
{% highlight java %}
592. XSLFSheet masterSheet = getSheet().getMasterSheet();
597.         masterShape = masterSheet.getPlaceholder(ph);
623.         XSLFSheet master = masterSheet.getMasterSheet();
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
> test that we get the same value as excel and , for 
{% highlight java %}
329. public XSLFSheet appendContent(XSLFSheet src){
333.     CTGroupShape srcTree = src.getSpTree();
355.     List<XSLFShape> srcShapes = src.getShapeList();
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
> sets the 
{% highlight java %}
1078. XSLFSheet masterSheet = _shape.getSheet();
1079. while (masterSheet.getMasterSheet() != null){
1080.     masterSheet = masterSheet.getMasterSheet();
1083. XmlObject[] o = masterSheet.getXmlObject().selectPath(
{% endhighlight %}

***

