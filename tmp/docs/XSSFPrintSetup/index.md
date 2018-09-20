# XSSFPrintSetup

***

## [Cluster 1](./1)
1 results
> test that we can read existing time , eg not 
{% highlight java %}
95. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
96. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
97. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
98. assertEquals( 0.3, printSetup.getHeaderMargin(), 0.01 );
99. assertEquals( 0.3, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

