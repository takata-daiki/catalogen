# XSSFSheet @Cluster 18

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
{% highlight java %}
107. XSSFSheet sheet0 = workbook.getSheetAt(0);
108. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
113. assertEquals( 0.7, sheet0.getMargin( Sheet.LeftMargin ), 0.01 );
114. assertEquals( 0.7, sheet0.getMargin( Sheet.RightMargin ), 0.01 );
115. assertEquals( 0.75, sheet0.getMargin( Sheet.TopMargin ), 0.01 );
116. assertEquals( 0.75, sheet0.getMargin( Sheet.BottomMargin ), 0.01 );
{% endhighlight %}

***

