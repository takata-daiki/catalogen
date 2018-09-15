# HSSFPrintSetup

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
244. HSSFPrintSetup ps = sheet.getPrintSetup();
245. ps.setFitWidth((short)1);
246. ps.setFitHeight((short)9999);
249. ps.setPaperSize(HSSFPrintSetup.LETTER_PAPERSIZE);
250. if(colCount > 5){ps.setLandscape(true);}
251. if(colCount > 10){ps.setPaperSize(HSSFPrintSetup.LEGAL_PAPERSIZE);}
252. if(colCount > 14){ps.setPaperSize(HSSFPrintSetup.EXECUTIVE_PAPERSIZE);}
254. ps.setHeaderMargin((double) .35);
255. ps.setFooterMargin((double) .35);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
284. final HSSFPrintSetup printSetup = sheet.getPrintSetup ();
285. printSetup.setLandscape ( true );
286. printSetup.setFitWidth ( (short)1 );
287. printSetup.setFitHeight ( (short)0 );
288. printSetup.setPaperSize ( PrintSetup.A4_PAPERSIZE );
295. printSetup.setFooterMargin ( 0.25 );
{% endhighlight %}

***

## [Cluster 3](./3)
6 results
> code comments is here.
{% highlight java %}
79. HSSFPrintSetup printSetup = sheet0.getPrintSetup();
80. assertEquals( HSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
81. assertEquals( true, printSetup.getLandscape() );
82. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
83. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

