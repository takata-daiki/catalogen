# HSSFPalette @Cluster 3

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
256. HSSFPalette palette = getWorkbook().getCustomPalette();
258. HSSFColor col = palette.findSimilarColor((byte)colour.getRed(), (byte)colour.getGreen(), (byte)colour.getBlue());
{% endhighlight %}

***

### [HSSFExcelExporter.java](https://searchcode.com/codesearch/view/76100200/)
{% highlight java %}
60. HSSFPalette palette = ((HSSFWorkbook) xlWorkbook).getCustomPalette();
62.   palette.setColorAtIndex((short) (55 - colorIndex.indexOf(swtColor)),
{% endhighlight %}

***

### [Issue36XlsColours.java](https://searchcode.com/codesearch/view/64531463/)
{% highlight java %}
87. HSSFPalette palette = workbook.getCustomPalette();
89.   palette.addColor( (byte)111, (byte)123, (byte)146);
{% endhighlight %}

***

### [CashPoolSummaryReportBean.java](https://searchcode.com/codesearch/view/99946782/)
{% highlight java %}
460. HSSFPalette palette = wb.getCustomPalette();
461. palette.setColorAtIndex((short) 9, (byte) (220), (byte) (240), (byte) (138));
{% endhighlight %}

***

### [ExcelWorksheetBuilder.java](https://searchcode.com/codesearch/view/70648747/)
{% highlight java %}
64. HSSFPalette customColorsPalette;
326.                 customColorsPalette.setColorAtIndex(new Byte((byte) nextAvailableColorCode), new Byte((byte) redCode), new Byte((byte) greenCode), new Byte((byte) blueCode));
327.                 returnedColorIndex = customColorsPalette.getColor(nextAvailableColorCode).getIndex();
{% endhighlight %}

***

### [CashPoolExcelStyle.java](https://searchcode.com/codesearch/view/99946830/)
{% highlight java %}
15. HSSFPalette palette = workbook.getCustomPalette();
16. palette.setColorAtIndex((short) 9, (byte) (220), (byte) (240), (byte) (138));
{% endhighlight %}

***

### [CityWeekScheduleSheetGeneratorImpl.java](https://searchcode.com/codesearch/view/92131933/)
{% highlight java %}
151. private HSSFPalette palette;
172.     palette.setColorAtIndex((byte) curColorIndex, (byte) red, (byte) green, (byte) blue);
{% endhighlight %}

***

