# HSSFFooter

***

## [Cluster 1](./1)
5 results
> code comments is here.
{% highlight java %}
340. HSSFFooter footer = sheet.getFooter();
341. footer.setLeft(Env.getStandardReportFooterTrademarkText());
342. footer.setCenter(Env.getHeader(getCtx(), 0));
344. footer.setRight(DisplayType.getDateFormat(DisplayType.DateTime, getLanguage()).format(now));
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
240. HSSFFooter footer = sheet.getFooter();
241. footer.setCenter("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
277. final HSSFFooter footer = sheet.getFooter ();
278. footer.setLeft ( String.format ( Messages.ExportImpl_ExcelSheet_Footer_1, events.size () ) );
280. footer.setRight ( Messages.ExportImpl_ExcelSheet_Footer_2 + HeaderFooter.page () + Messages.ExportImpl_ExcelSheet_Footer_3 + HeaderFooter.numPages () );
{% endhighlight %}

***

