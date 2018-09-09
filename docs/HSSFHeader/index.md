# HSSFHeader

***

### [Cluster 1](./1)
{% highlight java %}
520. HSSFHeader sheetHeader = overviewSheet.getHeader();
521. sheetHeader.setLeft(lblHeaderLeft);
522. sheetHeader.setRight(lblHeaderRight);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
331. HSSFHeader header = sheet.getHeader();
332. header.setRight(HSSFHeader.page()+ " / "+HSSFHeader.numPages());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
273. final HSSFHeader header = sheet.getHeader ();
274. header.setLeft ( Messages.ExportImpl_ExcelSheet_Header );
275. header.setRight ( HeaderFooter.date () + " " + HeaderFooter.time () );//$NON-NLS-1$
{% endhighlight %}

***

