# DataFormatter @Cluster 3 (assertequals, formatter, getrow)

***

### [GridsTests.java](https://searchcode.com/codesearch/view/64531524/)
> check that the given header was < code > null < / code > if it ' s - 0 . 0 the for 1 9 0 0 0 , the if 0 is not a valid excel file 
{% highlight java %}
57. DataFormatter formatter = new DataFormatter();
59. assertEquals( "This is a label\nHeading 1\nThis is text\nHeading 2\nStyles\nBold, Italic, Bold and italic and finally Underline.\n• Oh\n• Dear\nIsle of Mann\nPlain text.\nAnd this is a label",                     formatter.formatCellValue(sheet.getRow(0).getCell(1)));
62. assertEquals( "Hello",                   formatter.formatCellValue(sheet.getRow(1).getCell(0)));
63. assertEquals( "End",                     formatter.formatCellValue(sheet.getRow(2).getCell(0)));
{% endhighlight %}

***

### [GridsTests.java](https://searchcode.com/codesearch/view/122565038/)
> check that the given header was < code > null < / code > if it ' s - 0 . 0 the for 1 9 0 0 0 , the if 0 is not a valid excel file 
{% highlight java %}
77. DataFormatter formatter = new DataFormatter();
79. assertEquals( "This is a label\nHeading 1\nThis is text\nHeading 2\nStyles\nBold, Italic, Bold and italic and finally Underline.\n• Oh\n• Dear\nIsle of Mann\nPlain text.\nAnd this is a label",                     formatter.formatCellValue(sheet.getRow(0).getCell(1)));
82. assertEquals( "Hello",                   formatter.formatCellValue(sheet.getRow(1).getCell(0)));
83. assertEquals( "End",                     formatter.formatCellValue(sheet.getRow(2).getCell(0)));
{% endhighlight %}

***

### [GridsTests.java](https://searchcode.com/codesearch/view/122565038/)
> check that the given header was < code > null < / code > if it ' s - 0 . 0 the for 1 9 0 0 0 , the if 0 is not a valid excel file 
{% highlight java %}
48. DataFormatter formatter = new DataFormatter();
50. assertEquals( "This is a label\nHeading 1\nThis is text\nHeading 2\nStyles\nBold, Italic, Bold and italic and finally Underline.\n• Oh\n• Dear\nIsle of Mann\nPlain text.\nAnd this is a label",                     formatter.formatCellValue(sheet.getRow(0).getCell(1)));
53. assertEquals( "Hello",                   formatter.formatCellValue(sheet.getRow(1).getCell(0)));
54. assertEquals( "End",                     formatter.formatCellValue(sheet.getRow(2).getCell(0)));
{% endhighlight %}

***

### [GridsTests.java](https://searchcode.com/codesearch/view/64531524/)
> check that the given header was < code > null < / code > if it ' s - 0 . 0 the for 1 9 0 0 0 , the if 0 is not a valid excel file 
{% highlight java %}
86. DataFormatter formatter = new DataFormatter();
88. assertEquals( "This is a label\nHeading 1\nThis is text\nHeading 2\nStyles\nBold, Italic, Bold and italic and finally Underline.\n• Oh\n• Dear\nIsle of Mann\nPlain text.\nAnd this is a label",                     formatter.formatCellValue(sheet.getRow(0).getCell(1)));
91. assertEquals( "Hello",                   formatter.formatCellValue(sheet.getRow(1).getCell(0)));
92. assertEquals( "End",                     formatter.formatCellValue(sheet.getRow(2).getCell(0)));
{% endhighlight %}

***
