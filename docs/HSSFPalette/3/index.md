# HSSFPalette @Cluster 3

***

### [Issue36XlsColours.java](https://searchcode.com/codesearch/view/64531463/)
{% highlight java %}
59. HSSFPalette palette = workbook.getCustomPalette();
60. System.out.println( "Palette: " + palette.toString() );
76.   cell.setCellValue( palette.getColor(i) == null ? "null" : palette.getColor(i).getHexString() );
{% endhighlight %}

***

