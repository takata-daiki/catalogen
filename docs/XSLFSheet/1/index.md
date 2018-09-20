# XSLFSheet @Cluster 1

***

### [XSLFDrawing.java](https://searchcode.com/codesearch/view/97406826/)
{% highlight java %}
42. /*package*/ XSLFDrawing(XSLFSheet sheet, CTGroupShape spTree){
45.     XmlObject[] cNvPr = sheet.getSpTree().selectPath(
{% endhighlight %}

***

### [RenderableShape.java](https://searchcode.com/codesearch/view/97406799/)
{% highlight java %}
384. XSLFSheet sheet = _shape.getSheet();
385. XSLFTheme theme = sheet.getTheme();
395.     paint = selectPaint(graphics, fillProps, phClr, sheet.getPackagePart());
{% endhighlight %}

***

### [XSLFSimpleShape.java](https://searchcode.com/codesearch/view/97406763/)
{% highlight java %}
78. private final XSLFSheet _sheet;
272.         CTStyleMatrix styleMatrix = _sheet.getTheme().getXmlObject().getThemeElements().getFmtScheme();
547.                 CTStyleMatrix styleMatrix = _sheet.getTheme().getXmlObject().getThemeElements().getFmtScheme();
{% endhighlight %}

***

### [XSLFGroupShape.java](https://searchcode.com/codesearch/view/97406700/)
{% highlight java %}
56. private final XSLFSheet _sheet;
65.     _shapes = _sheet.buildShapes(_shape);
242.     List<PackagePart>  pics = _sheet.getPackagePart().getPackage()
251.     PackageRelationship rel = _sheet.getPackagePart().addRelationship(
{% endhighlight %}

***

