# XSSFColor

***

### [Cluster 1](./1)
{% highlight java %}
79. XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
80. assertEquals( "FF800000", bgColour.getARGBHex() );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
94. private String getRGBString(XSSFColor colour) {
97.   if (colour == null || colour.isAuto()) {
100.     String rgb = colour.getARGBHex();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
85. XSSFColor xssfColor = (XSSFColor) color;
86. byte[] rgb = xssfColor.getRGB();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
212. XSSFColor colour = ((XSSFFont)font).getXSSFColor();
213. int fgRgb[] = rgbOnly( colour.getARgb() );
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
275. XSSFColor colour = theCell.getCellStyle().getFont().getXSSFColor();
277.   foreground = translateRGB(colour.getRgb());
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
216. XSSFColor xssfColour = cellStyle.getFillForegroundXSSFColor();
221. colour = translateRGB(xssfColour.getRgb());
{% endhighlight %}

***

