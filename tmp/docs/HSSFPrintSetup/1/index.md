# HSSFPrintSetup @Cluster 1

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

