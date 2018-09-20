# DataFormatter

***

## [Cluster 1](./1)
6 results
> test that we get the same value as excel and , for 
{% highlight java %}
23. private DataFormatter formatter = new HSSFDataFormatter();
52.     return formatter.formatCellValue(cell, formulaEvaluator);
{% endhighlight %}

***

## [Cluster 2](./2)
13 results
> check that the given header was < code > null < / code > if it ' s - 0 . 0 the for 1 9 0 0 0 , the if 0 is not a valid excel file 
{% highlight java %}
75. DataFormatter formatter = new DataFormatter();
77. assertEquals( "Ł3141.59",              formatter.formatCellValue(sheet.getRow(1).getCell(1)));
78. assertEquals( "$3141.59",              formatter.formatCellValue(sheet.getRow(2).getCell(1)));
79. assertEquals( "Ľ3141.59",              formatter.formatCellValue(sheet.getRow(3).getCell(1)));
80. assertEquals( "3141.59",              formatter.formatCellValue(sheet.getRow(4).getCell(1)));
{% endhighlight %}

***

## [Cluster 3](./3)
4 results
> check that the given header was < code > null < / code > if it ' s - 0 . 0 the for 1 9 0 0 0 , the if 0 is not a valid excel file 
{% highlight java %}
57. DataFormatter formatter = new DataFormatter();
59. assertEquals( "This is a label\nHeading 1\nThis is text\nHeading 2\nStyles\nBold, Italic, Bold and italic and finally Underline.\n• Oh\n• Dear\nIsle of Mann\nPlain text.\nAnd this is a label",                     formatter.formatCellValue(sheet.getRow(0).getCell(1)));
62. assertEquals( "Hello",                   formatter.formatCellValue(sheet.getRow(1).getCell(0)));
63. assertEquals( "End",                     formatter.formatCellValue(sheet.getRow(2).getCell(0)));
{% endhighlight %}

***

