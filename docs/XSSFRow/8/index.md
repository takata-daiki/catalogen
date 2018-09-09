# XSSFRow @Cluster 8

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
61. XSSFRow row = sheet.getRow(rowIndex);
63.   int firstCell = row.getFirstCellNum();
64.     int lastCell = row.getLastCellNum();
66.       XSSFCell cell = row.getCell(cellIndex);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
141. XSSFRow hssfRow = sheet.getRow(row);
145. XSSFCell hssfCell = hssfRow.getCell(col);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
155. XSSFRow hssfRow = sheet.getRow(row);
159. XSSFCell cell = hssfRow.getCell(col);
161.     cell = hssfRow.createCell(col);
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
167. XSSFRow theRow = sheet.getRow(row);
169.     XSSFCell theCell = theRow.getCell(col);
{% endhighlight %}

***

