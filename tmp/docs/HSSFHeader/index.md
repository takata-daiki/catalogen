# HSSFHeader

***

## [Cluster 1 (getheader, hssfheader, sheetheader)](./1)
1 results
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param p the font to be used for to @ since poi 3 . 1 5 beta 3 
{% highlight java %}
493. HSSFHeader sheetHeader = overviewSheet.getHeader();
494. sheetHeader.setLeft(lblHeaderLeft);
495. sheetHeader.setRight(lblHeaderRight);
{% endhighlight %}

***

## [Cluster 2 (getheader, header, hssfheader)](./2)
4 results
> set the type of underlining for the font 
{% highlight java %}
335. HSSFHeader header = sheet.getHeader();
336. header.setRight(HSSFHeader.page()+ " / "+HSSFHeader.numPages());
{% endhighlight %}

***

## [Cluster 3 (filename, getheader, header)](./3)
1 results
> set the param height of the in the range . 
{% highlight java %}
85. HSSFHeader header = sheet.getHeader();
86. header.setCenter(filename);
{% endhighlight %}

***

## [Cluster 4 (date, header, headerfooter)](./4)
1 results
> set normal , super or subscript , that representing the vertical - alignment setting . 
{% highlight java %}
273. final HSSFHeader header = sheet.getHeader ();
274. header.setLeft ( Messages.ExportImpl_ExcelSheet_Header );
275. header.setRight ( HeaderFooter.date () + " " + HeaderFooter.time () );//$NON-NLS-1$
{% endhighlight %}

***

