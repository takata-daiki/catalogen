# DataFormatter

***

### [Cluster 1](./1)
{% highlight java %}
57. DataFormatter formatter = new DataFormatter();
59. assertEquals( "1",                     formatter.formatCellValue(sheet.getRow(2).getCell(1)));
60. assertEquals( "2019-10-11 13:18:46",   formatter.formatCellValue(sheet.getRow(2).getCell(2)));
61. assertEquals( "3.1415926536",          formatter.formatCellValue(sheet.getRow(2).getCell(3)));
62. assertEquals( "3.1415926536",          formatter.formatCellValue(sheet.getRow(2).getCell(4)));
63. assertEquals( "false",                 formatter.formatCellValue(sheet.getRow(2).getCell(5)));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
30. String toString(FormulaEvaluator evaluator, DataFormatter formatter, Cell cell) {
36.       content = formatter.formatCellValue(cell);
38.       content = formatter.formatCellValue(cell,
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
208. DataFormatter formatter = new DataFormatter();
224.                 cellValues.add(formatter.formatCellValue(row.getCell(k)));
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
55. private final DataFormatter formatter;
107.                         formatter.formatRawCellContents(cell.getNumericCellValue(),
{% endhighlight %}

***

