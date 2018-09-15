# DataFormatter

***

## [Cluster 1](./1)
13 results
> code comments is here.
{% highlight java %}
57. DataFormatter formatter = new DataFormatter();
59. assertEquals( "1",                     formatter.formatCellValue(sheet.getRow(2).getCell(1)));
60. assertEquals( "2019-10-11 13:18:46",   formatter.formatCellValue(sheet.getRow(2).getCell(2)));
61. assertEquals( "3.1415926536",          formatter.formatCellValue(sheet.getRow(2).getCell(3)));
62. assertEquals( "3.1415926536",          formatter.formatCellValue(sheet.getRow(2).getCell(4)));
63. assertEquals( "false",                 formatter.formatCellValue(sheet.getRow(2).getCell(5)));
{% endhighlight %}

***

## [Cluster 2](./2)
4 results
> code comments is here.
{% highlight java %}
48. DataFormatter formatter = new DataFormatter();
50. assertEquals( "This is a label\nHeading 1\nThis is text\nHeading 2\nStyles\nBold, Italic, Bold and italic and finally Underline.\n• Oh\n• Dear\nIsle of Mann\nPlain text.\nAnd this is a label",                     formatter.formatCellValue(sheet.getRow(0).getCell(1)));
53. assertEquals( "Hello",                   formatter.formatCellValue(sheet.getRow(1).getCell(0)));
54. assertEquals( "End",                     formatter.formatCellValue(sheet.getRow(2).getCell(0)));
{% endhighlight %}

***

