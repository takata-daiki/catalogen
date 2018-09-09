# HSSFCellStyle

***

### [Cluster 1](./1)
{% highlight java %}
245. HSSFCellStyle cellStyle = book.createCellStyle();
279.     cellStyle.setWrapText(true);
280.     cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
281.     cellStyle.setBottomBorderColor((short)8);
292.     cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
300.     cellStyle.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
322. HSSFCellStyle style= wb.createCellStyle();
323. style.cloneStyleFrom(cell.getCellStyle());
324. style.setFont(font);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
26. protected HSSFCellStyle csIntNum;
221.   csIntNum.setDataFormat(wb.createDataFormat().getFormat("#,##0"));
222.   csIntNum.setBorderBottom(HSSFCellStyle.BORDER_THIN);
223.   csIntNum.setBorderLeft(HSSFCellStyle.BORDER_THIN);
224.   csIntNum.setBorderRight(HSSFCellStyle.BORDER_THIN);
225.   csIntNum.setBorderTop(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
37. HSSFCellStyle style = workbook.createCellStyle();
38. style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
39. style.setBottomBorderColor(HSSFColor.BLACK.index);
40. style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
41. style.setLeftBorderColor(HSSFColor.GREEN.index);
42. style.setBorderRight(HSSFCellStyle.BORDER_THIN);
43. style.setRightBorderColor(HSSFColor.BLUE.index);
44. style.setTopBorderColor(HSSFColor.BLACK.index);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
107. HSSFCellStyle headingStyle = wb.createCellStyle();
112. headingStyle.setFont(headingFont);
113. headingStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
114. headingStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
115. headingStyle.setFillForegroundColor(HSSFColor.GREEN.index);
116. headingStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
85. HSSFCellStyle style = wb.createCellStyle();
89.   style.setFont(font);
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
23. protected HSSFCellStyle csText;
195.   csText.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
196.   csText.setBorderBottom(HSSFCellStyle.BORDER_THIN);
197.   csText.setBorderLeft(HSSFCellStyle.BORDER_THIN);
198.   csText.setBorderRight(HSSFCellStyle.BORDER_THIN);
199.   csText.setBorderTop(HSSFCellStyle.BORDER_THIN);
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
48. private HSSFCellStyle cellStyle;
170.   cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
171.   cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
172.   cellStyle.setBorderRight(CellStyle.BORDER_THIN);
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
143. HSSFCellStyle cellStyle = theCell.getCellStyle();        
148. HSSFFont font = cellStyle.getFont(getWorkbook());
151.     cellStyle.setFont(font);
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
54. HSSFCellStyle locked;
67.   locked.setLocked(true);
68.   locked.setWrapText(true);
69.   locked.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
{% endhighlight %}

***

