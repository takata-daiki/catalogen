# XSSFPrintSetup @Cluster 1 (01, assertequals, printsetup)

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
> test for parsing 
{% highlight java %}
50. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
51. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
52. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
53. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
54. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

