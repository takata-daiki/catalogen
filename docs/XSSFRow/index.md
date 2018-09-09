# XSSFRow

***

### [Cluster 1](./1)
{% highlight java %}
297. final XSSFRow xssfHeaderRow = xssfSheet.createRow(0);
302.     final XSSFCell xssfCell = xssfHeaderRow.createCell(colNr);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
39. XSSFRow row = sheet.createRow(0);
40. XSSFCell cell = row.createCell(0);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
164. XSSFRow row = sheet.createRow(rowindex);
165. row.setHeightInPoints((short)45);
171.     row.setHeightInPoints((short)30);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
73. XSSFRow headerRow = sheet.createRow(1);
78.     XSSFCell headerCell1 = headerRow.createCell(count);
85. XSSFCell headerCell00 = headerRow.createCell(0);
89. XSSFCell headerCell1 = headerRow.createCell(1);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
90. XSSFRow dataRow = sheet.createRow(i + 2);
96.   XSSFCell cell1 = dataRow.createCell(j);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
189. private void setColumnHeaders( XSSFRow row,  List<String> delist){
214.       XSSFCell cell = row.createCell(columnIndex);
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
77. final XSSFRow xssfRow = xssfSheet.createRow(row.getRowNr() + 1);
81.         final XSSFCell xssfCell = xssfRow.createCell(cell.getColInfo().getSpaltenNr());
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
61. XSSFRow row = sheet.getRow(rowIndex);
63.   int firstCell = row.getFirstCellNum();
64.     int lastCell = row.getLastCellNum();
66.       XSSFCell cell = row.getCell(cellIndex);
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
253. XSSFRow xssfRow = xssfSheet.getRow(setCellValue.getRow());
257. XSSFCell xssfCell = xssfRow.getCell(setCellValue.getCol());
259.     xssfCell = xssfRow.createCell(setCellValue.getCol());
266.         xssfRow.removeCell(xssfCell);
{% endhighlight %}

***

