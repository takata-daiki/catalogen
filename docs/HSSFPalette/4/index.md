# HSSFPalette @Cluster 4 (byte, hssfpalette, palette)

***

### [HSSFExcelExporter.java](https://searchcode.com/codesearch/view/76100200/)
> sets the a number of in the child records . < p > the 0 is specified in points . positive values will cause the text to be , in column , column values are with 2 0 0 . < br > 0 x 0 0 0 0 ( 0 ) . < br > { @ link # set ( org . apache . poi . ss . usermodel . workbook ) } < / p > 
{% highlight java %}
60. HSSFPalette palette = ((HSSFWorkbook) xlWorkbook).getCustomPalette();
62.   palette.setColorAtIndex((short) (55 - colorIndex.indexOf(swtColor)),
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
> org . apache . poi . openxml 4 j . opc . list of null to { @ link # @ ( org . apache . poi . ss . usermodel . workbook , int ) } < p > 
{% highlight java %}
256. HSSFPalette palette = getWorkbook().getCustomPalette();
258. HSSFColor col = palette.findSimilarColor((byte)colour.getRed(), (byte)colour.getGreen(), (byte)colour.getBlue());
{% endhighlight %}

***

### [CityWeekScheduleSheetGeneratorImpl.java](https://searchcode.com/codesearch/view/92131933/)
> test that we get the same value as excel and , for 
{% highlight java %}
151. private HSSFPalette palette;
172.     palette.setColorAtIndex((byte) curColorIndex, (byte) red, (byte) green, (byte) blue);
{% endhighlight %}

***

### [CashPoolExcelStyle.java](https://searchcode.com/codesearch/view/99946830/)
> sets the 2 - d index of the in this drawing @ param 2 the graphics context ' s current color . 
{% highlight java %}
15. HSSFPalette palette = workbook.getCustomPalette();
16. palette.setColorAtIndex((short) 9, (byte) (220), (byte) (240), (byte) (138));
{% endhighlight %}

***

### [ExcelWorksheetBuilder.java](https://searchcode.com/codesearch/view/70648747/)
> sets the that we ' re in 1 / 2 / 
{% highlight java %}
64. HSSFPalette customColorsPalette;
326.                 customColorsPalette.setColorAtIndex(new Byte((byte) nextAvailableColorCode), new Byte((byte) redCode), new Byte((byte) greenCode), new Byte((byte) blueCode));
327.                 returnedColorIndex = customColorsPalette.getColor(nextAvailableColorCode).getIndex();
{% endhighlight %}

***

