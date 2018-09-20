# HSSFPrintSetup

***

## [Cluster 1](./1)
10 results
> set the type of underlining for the font 
{% highlight java %}
349. HSSFPrintSetup ps = sheet.getPrintSetup();
350. ps.setFitWidth((short)1);
351. ps.setNoColor(true);
352. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
353. ps.setLandscape(false);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> test that we get the same value as excel and , for 
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

## [Cluster 3](./3)
1 results
> sets the line count . @ see # @ see # was one ( ) 
{% highlight java %}
284. final HSSFPrintSetup printSetup = sheet.getPrintSetup ();
285. printSetup.setLandscape ( true );
286. printSetup.setFitWidth ( (short)1 );
287. printSetup.setFitHeight ( (short)0 );
288. printSetup.setPaperSize ( PrintSetup.A4_PAPERSIZE );
295. printSetup.setFooterMargin ( 0.25 );
{% endhighlight %}

***

## [Cluster 4](./4)
6 results
> test that we get the same value as excel and , for 
{% highlight java %}
1279. HSSFPrintSetup ps = sh.getPrintSetup();
1280. float[]sizePage = PrintPagesFormat.get(ps.getPaperSize());
1283. if (ps.getLandscape()) baseSizePage = sizePage[0];
1285. float scale = ps.getScale() / 100;
1286. double l = baseSizePage -(((ps.getFooterMargin()+ps.getHeaderMargin())*Factotr_MM_Inches)+50);
{% endhighlight %}

***

