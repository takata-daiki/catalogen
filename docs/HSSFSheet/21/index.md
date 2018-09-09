# HSSFSheet @Cluster 21

***

### [ReportingFacadeBean.java](https://searchcode.com/codesearch/view/39694396/)
{% highlight java %}
140. HSSFSheet overviewSheet = wb.createSheet(sheetName);
141. Header sheetHeader = overviewSheet.getHeader();
146. overviewSheet.createFreezePane(0, 1, 0, 1);
148. Row row = overviewSheet.createRow(0);
169.     row = overviewSheet.createRow(overviewSheetRow);
192.         row = overviewSheet.createRow(overviewSheetRow);
225.     overviewSheet.groupRow(startRow, endRow);
226.     overviewSheet.setRowGroupCollapsed(startRow, true);
231.     overviewSheet.autoSizeColumn(i);
237. overviewSheet.setAutobreaks(true);
238. overviewSheet.getPrintSetup().setFitWidth((short) 1);
239. overviewSheet.getPrintSetup().setFitHeight((short) 500);
241. Footer footer = overviewSheet.getFooter();
{% endhighlight %}

***

