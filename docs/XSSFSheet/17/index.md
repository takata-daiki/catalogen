# XSSFSheet @Cluster 17

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
{% highlight java %}
49. XSSFSheet sheet0 = workbook.getSheetAt(0);
50. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
55. assertEquals( 0.7 / 2.54, sheet0.getMargin( Sheet.LeftMargin ), 0.01 );
56. assertEquals( 0.7 / 2.54, sheet0.getMargin( Sheet.RightMargin ), 0.01 );
57. assertEquals( 1.7 / 2.54, sheet0.getMargin( Sheet.TopMargin ), 0.01 );
58. assertEquals( 1.7 / 2.54, sheet0.getMargin( Sheet.BottomMargin ), 0.01 );
{% endhighlight %}

***

