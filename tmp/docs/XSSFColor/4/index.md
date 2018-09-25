# XSSFColor @Cluster 4 (assertequals, bgcolour, ffffffff)

***

### [NestedTables2ReportTest.java](https://searchcode.com/codesearch/view/126772640/)
> ensure that the sheet # is 0 ( not the user in the same as the excel number ) 
{% highlight java %}
61. XSSFColor bgColour = ((XSSFCell)sheet.getRow(0).getCell(0)).getCellStyle().getFillForegroundColorColor();
62. assertEquals( "FFFFFFFF", bgColour.getARGBHex() );
{% endhighlight %}

***

