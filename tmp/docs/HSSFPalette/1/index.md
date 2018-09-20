# HSSFPalette @Cluster 1

***

### [HSSFFont.java](https://searchcode.com/codesearch/view/97401500/)
{% highlight java %}
201. HSSFPalette pallette = wb.getCustomPalette();
202. return pallette.getColor( getColor() );
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
{% highlight java %}
123. HSSFPalette customPalette = ((HSSFWorkbook) designer.workbook).getCustomPalette();
124. HSSFColor hssfColor = customPalette.getColor(c);
{% endhighlight %}

***

### [IcySpreadSheet.java](https://searchcode.com/codesearch/view/103223911/)
{% highlight java %}
375. HSSFPalette palette = ((HSSFWorkbook) book).getCustomPalette();
377. colorIndex = palette.findSimilarColor(color.getRed(), color.getGreen(), color.getBlue()).getIndex();
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
268. HSSFPalette palette = getWorkbook().getCustomPalette();
269. HSSFColor color = palette.getColor(colorIndex);
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
{% highlight java %}
27. private static HSSFPalette palette;
112.   HSSFColor color = palette.getColor(index);
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/122565152/)
{% highlight java %}
222. HSSFPalette palette = ((HSSFWorkbook)wb).getCustomPalette();    
229.   fgRgb = palette.getColor(font.getColor()).getTriplet();
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/126772704/)
{% highlight java %}
225. HSSFPalette palette = ((HSSFWorkbook)wb).getCustomPalette();    
232.   fgRgb = palette.getColor(font.getColor()).getTriplet();
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/64530867/)
{% highlight java %}
235. HSSFPalette palette = ((HSSFWorkbook)wb).getCustomPalette();    
242.   fgRgb = palette.getColor(font.getColor()).getTriplet();
{% endhighlight %}

***

### [HSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283811/)
{% highlight java %}
19. private static HSSFPalette palette;
58.   HSSFColor color = palette.getColor(index);
{% endhighlight %}

***

### [Issue36XlsColours.java](https://searchcode.com/codesearch/view/64531463/)
{% highlight java %}
59. HSSFPalette palette = workbook.getCustomPalette();
60. System.out.println( "Palette: " + palette.toString() );
76.   cell.setCellValue( palette.getColor(i) == null ? "null" : palette.getColor(i).getHexString() );
{% endhighlight %}

***

