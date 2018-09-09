# HSSFPrintSetup @Cluster 1

***

### [ExportEventsImpl.java](https://searchcode.com/codesearch/view/122444114/)
{% highlight java %}
284. final HSSFPrintSetup printSetup = sheet.getPrintSetup ();
285. printSetup.setLandscape ( true );
286. printSetup.setFitWidth ( (short)1 );
287. printSetup.setFitHeight ( (short)0 );
288. printSetup.setPaperSize ( PrintSetup.A4_PAPERSIZE );
295. printSetup.setFooterMargin ( 0.25 );
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/102528302/)
{% highlight java %}
345. HSSFPrintSetup ps = sheet.getPrintSetup();
346. ps.setFitWidth((short)1);
347. ps.setNoColor(true);
348. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
349. ps.setLandscape(false);
{% endhighlight %}

***

### [ExcelExportControllerBean.java](https://searchcode.com/codesearch/view/4293932/)
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

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/59777594/)
{% highlight java %}
349. HSSFPrintSetup ps = sheet.getPrintSetup();
350. ps.setFitWidth((short)1);
351. ps.setNoColor(true);
352. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
353. ps.setLandscape(false);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/61401276/)
{% highlight java %}
349. HSSFPrintSetup ps = sheet.getPrintSetup();
350. ps.setFitWidth((short)1);
351. ps.setNoColor(true);
352. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
353. ps.setLandscape(false);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/62551719/)
{% highlight java %}
351. HSSFPrintSetup ps = sheet.getPrintSetup();
352. ps.setFitWidth((short)1);
353. ps.setNoColor(true);
354. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
355. ps.setLandscape(false);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/62628992/)
{% highlight java %}
349. HSSFPrintSetup ps = sheet.getPrintSetup();
350. ps.setFitWidth((short)1);
351. ps.setNoColor(true);
352. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
353. ps.setLandscape(false);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/63385794/)
{% highlight java %}
345. HSSFPrintSetup ps = sheet.getPrintSetup();
346. ps.setFitWidth((short)1);
347. ps.setNoColor(true);
348. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
349. ps.setLandscape(false);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/63585397/)
{% highlight java %}
345. HSSFPrintSetup ps = sheet.getPrintSetup();
346. ps.setFitWidth((short)1);
347. ps.setNoColor(true);
348. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
349. ps.setLandscape(false);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/63687137/)
{% highlight java %}
345. HSSFPrintSetup ps = sheet.getPrintSetup();
346. ps.setFitWidth((short)1);
347. ps.setNoColor(true);
348. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
349. ps.setLandscape(false);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/3305415/)
{% highlight java %}
351. HSSFPrintSetup ps = sheet.getPrintSetup();
352. ps.setFitWidth((short)1);
353. ps.setNoColor(true);
354. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
355. ps.setLandscape(false);
{% endhighlight %}

***

### [AbstractExcelExporter.java](https://searchcode.com/codesearch/view/8373941/)
{% highlight java %}
345. HSSFPrintSetup ps = sheet.getPrintSetup();
346. ps.setFitWidth((short)1);
347. ps.setNoColor(true);
348. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
349. ps.setLandscape(false);
{% endhighlight %}

***

