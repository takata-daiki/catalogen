# HSSFPalette

***

### [Cluster 1](./1)
{% highlight java %}
128. HSSFPalette palette = workbook.getCustomPalette();
130. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
134.     palette.setColorAtIndex(paletteIndex, rgbByte[0], rgbByte[1], rgbByte[2]);
137.     result = palette.findSimilarColor(rgbByte[0], rgbByte[1], rgbByte[2]);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
123. HSSFPalette customPalette = ((HSSFWorkbook) designer.workbook).getCustomPalette();
124. HSSFColor hssfColor = customPalette.getColor(c);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
59. HSSFPalette palette = workbook.getCustomPalette();
60. System.out.println( "Palette: " + palette.toString() );
76.   cell.setCellValue( palette.getColor(i) == null ? "null" : palette.getColor(i).getHexString() );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
222. HSSFPalette palette = ((HSSFWorkbook)wb).getCustomPalette();    
229.   fgRgb = palette.getColor(font.getColor()).getTriplet();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
201. HSSFPalette pallette = wb.getCustomPalette();
202. return pallette.getColor( getColor() );
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
27. private static HSSFPalette palette;
112.   HSSFColor color = palette.getColor(index);
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
268. HSSFPalette palette = getWorkbook().getCustomPalette();
269. HSSFColor color = palette.getColor(colorIndex);
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
375. HSSFPalette palette = ((HSSFWorkbook) book).getCustomPalette();
377. colorIndex = palette.findSimilarColor(color.getRed(), color.getGreen(), color.getBlue()).getIndex();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
15. HSSFPalette palette = workbook.getCustomPalette();
16. palette.setColorAtIndex((short) 9, (byte) (220), (byte) (240), (byte) (138));
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
460. HSSFPalette palette = wb.getCustomPalette();
461. palette.setColorAtIndex((short) 9, (byte) (220), (byte) (240), (byte) (138));
{% endhighlight %}

***

### [Cluster 11](./11)
{% highlight java %}
60. HSSFPalette palette = ((HSSFWorkbook) xlWorkbook).getCustomPalette();
62.   palette.setColorAtIndex((short) (55 - colorIndex.indexOf(swtColor)),
{% endhighlight %}

***

### [Cluster 12](./12)
{% highlight java %}
256. HSSFPalette palette = getWorkbook().getCustomPalette();
258. HSSFColor col = palette.findSimilarColor((byte)colour.getRed(), (byte)colour.getGreen(), (byte)colour.getBlue());
{% endhighlight %}

***

### [Cluster 13](./13)
{% highlight java %}
87. HSSFPalette palette = workbook.getCustomPalette();
89.   palette.addColor( (byte)111, (byte)123, (byte)146);
{% endhighlight %}

***

### [Cluster 14](./14)
{% highlight java %}
151. private HSSFPalette palette;
172.     palette.setColorAtIndex((byte) curColorIndex, (byte) red, (byte) green, (byte) blue);
{% endhighlight %}

***

### [Cluster 15](./15)
{% highlight java %}
64. HSSFPalette customColorsPalette;
326.                 customColorsPalette.setColorAtIndex(new Byte((byte) nextAvailableColorCode), new Byte((byte) redCode), new Byte((byte) greenCode), new Byte((byte) blueCode));
327.                 returnedColorIndex = customColorsPalette.getColor(nextAvailableColorCode).getIndex();
{% endhighlight %}

***

