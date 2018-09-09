# XSSFPrintSetup @Cluster 3

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
{% highlight java %}
50. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
51. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
52. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
53. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
54. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

### [Issue43StructuredHeader.java](https://searchcode.com/codesearch/view/122565074/)
{% highlight java %}
46. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
47. assertEquals( XSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
48. assertEquals( false, printSetup.getLandscape() );
50. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
51. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

### [Issue43StructuredHeader.java](https://searchcode.com/codesearch/view/122565074/)
{% highlight java %}
81. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
82. assertEquals( XSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
83. assertEquals( false, printSetup.getLandscape() );
85. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
86. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

