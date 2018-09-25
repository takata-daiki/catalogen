# XSSFColor @Cluster 2 (public, void, xssfcolor)

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
> set the color to use for the bottom border 
{% highlight java %}
85. XSSFColor xssfColor = (XSSFColor) color;
86. byte[] rgb = xssfColor.getRGB();
{% endhighlight %}

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
> sets the line end width in relation to the right border . 
{% highlight java %}
216. XSSFColor xssfColour = cellStyle.getFillForegroundXSSFColor();
221. colour = translateRGB(xssfColour.getRgb());
{% endhighlight %}

***

### [Issue36XlsColours.java](https://searchcode.com/codesearch/view/64531463/)
> sets the 
{% highlight java %}
148. XSSFColor colorX = styleX.getFillForegroundColorColor();
153.   byte[] rgbX = colorX.getARgb();
156.   System.out.println( "Comparing " + colorX.getARGBHex() + " with " + colorS.getHexString() );
{% endhighlight %}

***

