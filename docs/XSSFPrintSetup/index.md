# XSSFPrintSetup

***

## [Cluster 1](./1)
2 results
> code comments is here.
{% highlight java %}
46. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
47. assertEquals( XSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
48. assertEquals( false, printSetup.getLandscape() );
50. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
51. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

## [Cluster 2](./2)
2 results
> code comments is here.
{% highlight java %}
37. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
38. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
39. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
40. assertEquals( 1.0 / 2.54, printSetup.getHeaderMargin(), 0.01 );
41. assertEquals( 1.0 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

## [Cluster 3](./3)
3 results
> code comments is here.
{% highlight java %}
108. XSSFPrintSetup printSetup = sheet0.getPrintSetup();
109. assertEquals( PaperSize.A4_PAPER,  printSetup.getPaperSizeEnum() );
110. assertEquals( PrintOrientation.LANDSCAPE, printSetup.getOrientation() );
111. assertEquals( 0.3, printSetup.getHeaderMargin(), 0.01 );
112. assertEquals( 0.3, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

