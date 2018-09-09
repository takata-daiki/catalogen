# HSSFSheet @Cluster 20

***

### [MetaDataFacadeBean.java](https://searchcode.com/codesearch/view/39694405/)
{% highlight java %}
490. HSSFSheet overviewSheet = wb.createSheet(sheetName);
493. HSSFHeader sheetHeader = overviewSheet.getHeader();
498. Footer footer = overviewSheet.getFooter();
506. Row row = overviewSheet.createRow(0);
515. overviewSheet.createFreezePane(0, 1, 0, 1);
525.     row = overviewSheet.createRow(overviewSheetRow++);
533.     overviewSheet.autoSizeColumn(i);
537. overviewSheet.setFitToPage(true);
538. overviewSheet.setAutobreaks(true);
{% endhighlight %}

***

### [SearchEngineBean.java](https://searchcode.com/codesearch/view/39694394/)
{% highlight java %}
517. HSSFSheet overviewSheet = wb.createSheet(sheetName);
520. HSSFHeader sheetHeader = overviewSheet.getHeader();
525. Footer footer = overviewSheet.getFooter();
534. overviewSheet.createFreezePane(0, 1, 0, 1);
536. Row row = overviewSheet.createRow(0);
555.             row = overviewSheet.createRow(overviewSheetRow);
570.                     row = overviewSheet.createRow(overviewSheetRow);
599.     overviewSheet.autoSizeColumn(i);
603. overviewSheet.setFitToPage(true);
604. overviewSheet.setAutobreaks(true);
{% endhighlight %}

***

