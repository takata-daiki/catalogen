# XSSFColor

***

## [Cluster 1](./1)
3 results
> sets the and empty string of the default 
{% highlight java %}
223. XSSFColor colour = ((XSSFFont)font).getXSSFColor();
224. int fgRgb[] = rgbOnly( colour.getARgb() );
{% endhighlight %}

***

## [Cluster 2](./2)
5 results
> set the color to use for the bottom border 
{% highlight java %}
85. XSSFColor xssfColor = (XSSFColor) color;
86. byte[] rgb = xssfColor.getRGB();
{% endhighlight %}

***

## [Cluster 3](./3)
2 results
> sets the 
{% highlight java %}
48. private String getRGBString(XSSFColor colour)
52.   if (colour == null || colour.isAuto())
58.     String rgb = colour.getARGBHex();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> ensure that the sheet # is 0 ( not the user in the same as the excel number ) 
{% highlight java %}
61. XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
62. assertEquals( "FFFFFFFF", bgColour.getARGBHex() );
{% endhighlight %}

***

## [Cluster 5](./5)
2 results
> ensure that the sheet # and 0 based on a 
{% highlight java %}
79. XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
80. assertEquals( "FF800000", bgColour.getARGBHex() );
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> this comment could not be generated...
{% highlight java %}
64. XSSFColor baseColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFont().getXSSFColor();
65. assertEquals( "FFFFFFFF", baseColour.getARGBHex() );
{% endhighlight %}

***

## [Cluster 7](./7)
2 results
> this comment could not be generated...
{% highlight java %}
91. XSSFColor baseColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFont().getXSSFColor();
92. assertEquals( "FF000000", baseColour.getARGBHex() );
{% endhighlight %}

***

