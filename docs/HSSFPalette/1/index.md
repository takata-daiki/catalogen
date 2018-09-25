# HSSFPalette @Cluster 1 (color, hssfcolor, wb)

***

### [HSSFFont.java](https://searchcode.com/codesearch/view/97401500/)
> get the type of underlining for the font 
{% highlight java %}
201. HSSFPalette pallette = wb.getCustomPalette();
202. return pallette.getColor( getColor() );
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
> @ param that the new data is set to this function 
{% highlight java %}
123. HSSFPalette customPalette = ((HSSFWorkbook) designer.workbook).getCustomPalette();
124. HSSFColor hssfColor = customPalette.getColor(c);
{% endhighlight %}

***

### [IcySpreadSheet.java](https://searchcode.com/codesearch/view/103223911/)
> set the font from this range . if the font is not set , the default value is returned , see { @ link sheet # getdefaultrowheightinpoints ( ) } @ see org . apache . poi . hssf . usermodel . hssfworkbook # getfontat ( short ) 
{% highlight java %}
375. HSSFPalette palette = ((HSSFWorkbook) book).getCustomPalette();
377. colorIndex = palette.findSimilarColor(color.getRed(), color.getGreen(), color.getBlue()).getIndex();
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/122565152/)
> sets the cell value using object type information . @ param the cell to set the formula to set the cell to . 
{% highlight java %}
222. HSSFPalette palette = ((HSSFWorkbook)wb).getCustomPalette();    
229.   fgRgb = palette.getColor(font.getColor()).getTriplet();
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/126772704/)
> sets the cell value using object type information . @ param the cell to set the formula to set the cell to . 
{% highlight java %}
225. HSSFPalette palette = ((HSSFWorkbook)wb).getCustomPalette();    
232.   fgRgb = palette.getColor(font.getColor()).getTriplet();
{% endhighlight %}

***

### [HSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283811/)
> test that we get the same value as excel and , for 
{% highlight java %}
19. private static HSSFPalette palette;
58.   HSSFColor color = palette.getColor(index);
{% endhighlight %}

***

