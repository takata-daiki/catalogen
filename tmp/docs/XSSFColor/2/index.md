# XSSFColor @Cluster 2

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
{% highlight java %}
85. XSSFColor xssfColor = (XSSFColor) color;
86. byte[] rgb = xssfColor.getRGB();
{% endhighlight %}

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
{% highlight java %}
275. XSSFColor colour = theCell.getCellStyle().getFont().getXSSFColor();
277.   foreground = translateRGB(colour.getRgb());
{% endhighlight %}

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
{% highlight java %}
216. XSSFColor xssfColour = cellStyle.getFillForegroundXSSFColor();
221. colour = translateRGB(xssfColour.getRgb());
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
{% highlight java %}
108. XSSFColor xssfColor = (XSSFColor) color;
109. byte[] rgb = xssfColor.getARGB();
{% endhighlight %}

***

### [Issue36XlsColours.java](https://searchcode.com/codesearch/view/64531463/)
{% highlight java %}
148. XSSFColor colorX = styleX.getFillForegroundColorColor();
153.   byte[] rgbX = colorX.getARgb();
156.   System.out.println( "Comparing " + colorX.getARGBHex() + " with " + colorS.getHexString() );
{% endhighlight %}

***

