# HSSFPalette

***

## [Cluster 1 (color, hssfcolor, wb)](./1)
6 results
> get the type of underlining for the font 
{% highlight java %}
201. HSSFPalette pallette = wb.getCustomPalette();
202. return pallette.getColor( getColor() );
{% endhighlight %}

***

## [Cluster 2 (getcolor, result, rgbbyte)](./2)
2 results
> sets the cell value using object type information . @ param the cell to set the formula to set the cell to . 
{% highlight java %}
141. HSSFPalette palette = workbook.getCustomPalette();
143. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
147.     palette.setColorAtIndex(paletteIndex, rgbByte[0], rgbByte[1], rgbByte[2]);
150.     result = palette.findSimilarColor(rgbByte[0], rgbByte[1], rgbByte[2]);
{% endhighlight %}

***

## [Cluster 3 (palette, result, rgbbyte)](./3)
1 results
> verifies that the given stream starts with a zip structure . 
{% highlight java %}
137. HSSFPalette palette = workbook.getCustomPalette();
139. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
141.   result = palette.findSimilarColor(rgbByte[0], rgbByte[1], rgbByte[2]);
{% endhighlight %}

***

## [Cluster 4 (byte, hssfpalette, palette)](./4)
5 results
> sets the a number of in the child records . < p > the 0 is specified in points . positive values will cause the text to be , in column , column values are with 2 0 0 . < br > 0 x 0 0 0 0 ( 0 ) . < br > { @ link # set ( org . apache . poi . ss . usermodel . workbook ) } < / p > 
{% highlight java %}
60. HSSFPalette palette = ((HSSFWorkbook) xlWorkbook).getCustomPalette();
62.   palette.setColorAtIndex((short) (55 - colorIndex.indexOf(swtColor)),
{% endhighlight %}

***

