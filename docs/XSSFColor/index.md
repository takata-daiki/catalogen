# XSSFColor

***

## [Cluster 1 (colour, fgrgb, font)](./1)
2 results
> sets the and empty string of the default 
{% highlight java %}
223. XSSFColor colour = ((XSSFFont)font).getXSSFColor();
224. int fgRgb[] = rgbOnly( colour.getARgb() );
{% endhighlight %}

***

## [Cluster 2 (public, void, xssfcolor)](./2)
3 results
> set the color to use for the bottom border 
{% highlight java %}
85. XSSFColor xssfColor = (XSSFColor) color;
86. byte[] rgb = xssfColor.getRGB();
{% endhighlight %}

***

## [Cluster 3 (colour, null, string)](./3)
2 results
> sets the 
{% highlight java %}
48. private String getRGBString(XSSFColor colour)
52.   if (colour == null || colour.isAuto())
58.     String rgb = colour.getARGBHex();
{% endhighlight %}

***

## [Cluster 4 (assertequals, bgcolour, ffffffff)](./4)
1 results
> ensure that the sheet # is 0 ( not the user in the same as the excel number ) 
{% highlight java %}
61. XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
62. assertEquals( "FFFFFFFF", bgColour.getARGBHex() );
{% endhighlight %}

***

## [Cluster 5 (assertequals, basecolour, ff000000)](./5)
2 results
> ensure that the sheet # and 0 based on a 
{% highlight java %}
79. XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
80. assertEquals( "FF800000", bgColour.getARGBHex() );
{% endhighlight %}

***

