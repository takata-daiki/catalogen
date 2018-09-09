# CellRangeAddress @Cluster 7

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1355. CellRangeAddress cra = new CellRangeAddress(
1373.     cell  =  ExcelUtils.getCell(sh, rowNameDataRangeE, cra.getFirstColumn());
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1361. CellRangeAddress crb = list.getCellRangeAddress(jj);  
1362. wRHidden = ExcelUtils.getRow(sh, crb.getFirstRow()).getZeroHeight();
1363. HSSFCell cell = ExcelUtils.getCell(sh, crb.getFirstRow(), crb.getFirstColumn());
1378.   AreaReference area3 = new AreaReference(crb.formatAsString());
{% endhighlight %}

***

### [ExcelParser.java](https://searchcode.com/codesearch/view/93105691/)
{% highlight java %}
138. CellRangeAddress merged = getRangeIfMerged( cell,
142.     Cell topLeft = sheet.getRow( merged.getFirstRow() ).getCell( merged.getFirstColumn() );
{% endhighlight %}

***

