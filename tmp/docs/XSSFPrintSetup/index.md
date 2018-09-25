# XSSFPrintSetup

***

## [Cluster 1 (01, assertequals, printsetup)](./1)
1 results
> test for parsing 
{% highlight java %}
50. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
51. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
52. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
53. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
54. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

## [Cluster 2 (01, assertequals, printsetup)](./2)
2 results
> test for parsing document with drawings to the code @ throws ioexception 
{% highlight java %}
46. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
47. assertEquals( XSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
48. assertEquals( false, printSetup.getLandscape() );
50. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
51. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

## [Cluster 3 (01, assertequals, printsetup)](./3)
2 results
> test for parsing 
{% highlight java %}
59. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
60. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
61. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
62. assertEquals( 1.0 / 2.54, printSetup.getHeaderMargin(), 0.01 );
63. assertEquals( 1.0 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

## [Cluster 4 (01, assertequals, printsetup)](./4)
3 results
> test that we can read existing time , eg not 
{% highlight java %}
95. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
96. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
97. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
98. assertEquals( 0.3, printSetup.getHeaderMargin(), 0.01 );
99. assertEquals( 0.3, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

