# XSSFPrintSetup @Cluster 2 (01, assertequals, printsetup)

***

### [Issue43StructuredHeader.java](https://searchcode.com/codesearch/view/122565074/)
> test for parsing document with drawings to the code @ throws ioexception 
{% highlight java %}
46. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
47. assertEquals( XSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
48. assertEquals( false, printSetup.getLandscape() );
50. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
51. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

### [Issue43StructuredHeader.java](https://searchcode.com/codesearch/view/122565074/)
> test for parsing document with drawings to the code @ throws ioexception 
{% highlight java %}
81. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
82. assertEquals( XSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
83. assertEquals( false, printSetup.getLandscape() );
85. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
86. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

