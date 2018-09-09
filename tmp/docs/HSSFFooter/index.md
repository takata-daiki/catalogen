# HSSFFooter

***

### [Cluster 1](./1)
{% highlight java %}
334. HSSFFooter footer = sheet.getFooter();
335. footer.setLeft(Adempiere.ADEMPIERE_R);
336. footer.setCenter(Env.getHeader(getCtx(), 0));
338. footer.setRight(DisplayType.getDateFormat(DisplayType.DateTime, getLanguage()).format(now));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
240. HSSFFooter footer = sheet.getFooter();
241. footer.setCenter("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
277. final HSSFFooter footer = sheet.getFooter ();
278. footer.setLeft ( String.format ( Messages.ExportImpl_ExcelSheet_Footer_1, events.size () ) );
280. footer.setRight ( Messages.ExportImpl_ExcelSheet_Footer_2 + HeaderFooter.page () + Messages.ExportImpl_ExcelSheet_Footer_3 + HeaderFooter.numPages () );
{% endhighlight %}

***

