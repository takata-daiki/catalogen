# XSSFPrintSetup @Cluster 3 (01, assertequals, printsetup)

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/64531687/)
> test for parsing 
{% highlight java %}
59. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
60. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
61. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
62. assertEquals( 1.0 / 2.54, printSetup.getHeaderMargin(), 0.01 );
63. assertEquals( 1.0 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/126772664/)
> test for parsing 
{% highlight java %}
37. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
38. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
39. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
40. assertEquals( 1.0 / 2.54, printSetup.getHeaderMargin(), 0.01 );
41. assertEquals( 1.0 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

