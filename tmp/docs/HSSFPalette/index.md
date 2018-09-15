# HSSFPalette

***

## [Cluster 1](./1)
2 results
> code comments is here.
{% highlight java %}
128. HSSFPalette palette = workbook.getCustomPalette();
130. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
134.     palette.setColorAtIndex(paletteIndex, rgbByte[0], rgbByte[1], rgbByte[2]);
137.     result = palette.findSimilarColor(rgbByte[0], rgbByte[1], rgbByte[2]);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
137. HSSFPalette palette = workbook.getCustomPalette();
139. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
141.   result = palette.findSimilarColor(rgbByte[0], rgbByte[1], rgbByte[2]);
{% endhighlight %}

***

## [Cluster 3](./3)
7 results
> code comments is here.
{% highlight java %}
87. HSSFPalette palette = workbook.getCustomPalette();
89.   palette.addColor( (byte)111, (byte)123, (byte)146);
{% endhighlight %}

***

