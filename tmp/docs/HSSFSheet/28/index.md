# HSSFSheet @Cluster 28

***

### [ExportEventsImpl.java](https://searchcode.com/codesearch/view/122444114/)
{% highlight java %}
271. final HSSFSheet sheet = workbook.createSheet ( Messages.ExportImpl_ExcelSheet_Name );
273. final HSSFHeader header = sheet.getHeader ();
277. final HSSFFooter footer = sheet.getFooter ();
284. final HSSFPrintSetup printSetup = sheet.getPrintSetup ();
290. sheet.setAutoFilter ( new CellRangeAddress ( 0, 0, 0, columns.size () - 1 ) );
291. sheet.createFreezePane ( 0, 1 );
292. sheet.setFitToPage ( true );
293. sheet.setAutobreaks ( true );
297. sheet.setMargin ( Sheet.LeftMargin, 0.25 );
298. sheet.setMargin ( Sheet.RightMargin, 0.25 );
299. sheet.setMargin ( Sheet.TopMargin, 0.25 );
300. sheet.setMargin ( Sheet.BottomMargin, 0.5 );
{% endhighlight %}

***

