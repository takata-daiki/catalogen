# XSSFColor

***

## [Cluster 1](./1)
5 results
> code comments is here.
{% highlight java %}
85. XSSFColor xssfColor = (XSSFColor) color;
86. byte[] rgb = xssfColor.getRGB();
{% endhighlight %}

***

## [Cluster 2](./2)
2 results
> code comments is here.
{% highlight java %}
94. private String getRGBString(XSSFColor colour) {
97.   if (colour == null || colour.isAuto()) {
100.     String rgb = colour.getARGBHex();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
61. XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
62. assertEquals( "FFFFFFFF", bgColour.getARGBHex() );
{% endhighlight %}

***

## [Cluster 4](./4)
2 results
> code comments is here.
{% highlight java %}
79. XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
80. assertEquals( "FF800000", bgColour.getARGBHex() );
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> code comments is here.
{% highlight java %}
64. XSSFColor baseColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFont().getXSSFColor();
65. assertEquals( "FFFFFFFF", baseColour.getARGBHex() );
{% endhighlight %}

***

## [Cluster 6](./6)
2 results
> code comments is here.
{% highlight java %}
81. XSSFColor baseColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFont().getXSSFColor();
82. assertEquals( "FF000000", baseColour.getARGBHex() );
{% endhighlight %}

***

