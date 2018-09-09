# HSSFRequest @Cluster 1

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
252. HSSFRequest hssfRequest = new HSSFRequest();
254.     hssfRequest.addListenerForAllRecords(formatListener);
256.     hssfRequest.addListener(formatListener, BOFRecord.sid);
257.     hssfRequest.addListener(formatListener, EOFRecord.sid);
258.     hssfRequest.addListener(formatListener, DateWindow1904Record.sid);
259.     hssfRequest.addListener(formatListener, CountryRecord.sid);
260.     hssfRequest.addListener(formatListener, BoundSheetRecord.sid);
261.     hssfRequest.addListener(formatListener, SSTRecord.sid);
262.     hssfRequest.addListener(formatListener, FormulaRecord.sid);
263.     hssfRequest.addListener(formatListener, LabelRecord.sid);
264.     hssfRequest.addListener(formatListener, LabelSSTRecord.sid);
265.     hssfRequest.addListener(formatListener, NumberRecord.sid);
266.     hssfRequest.addListener(formatListener, RKRecord.sid);
267.     hssfRequest.addListener(formatListener, HyperlinkRecord.sid);
268.     hssfRequest.addListener(formatListener, TextObjectRecord.sid);
269.     hssfRequest.addListener(formatListener, SeriesTextRecord.sid);
270.     hssfRequest.addListener(formatListener, FormatRecord.sid);
271.     hssfRequest.addListener(formatListener, ExtendedFormatRecord.sid);
272.     hssfRequest.addListener(formatListener, DrawingGroupRecord.sid);
{% endhighlight %}

***

### [ExcelDataService.java](https://searchcode.com/codesearch/view/92669291/)
{% highlight java %}
227. HSSFRequest req = new HSSFRequest();
230. req.addListenerForAllRecords(new Excel2003ImportListener(proxy, dataList, batchSize));
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
{% highlight java %}
102. HSSFRequest request = new HSSFRequest();
105.   request.addListenerForAllRecords(formatListener);
109.   request.addListenerForAllRecords(workbookBuildingListener);
{% endhighlight %}

***

### [ExcelLanguageCentricParserTest.java](https://searchcode.com/codesearch/view/12440188/)
{% highlight java %}
180. HSSFRequest req = new HSSFRequest();
182. req.addListenerForAllRecords(parser);
{% endhighlight %}

***

### [KeywordExcelParserTest.java](https://searchcode.com/codesearch/view/12440192/)
{% highlight java %}
185. HSSFRequest req = new HSSFRequest();
187. req.addListenerForAllRecords(parser);
{% endhighlight %}

***

### [ExcelImporter.java](https://searchcode.com/codesearch/view/12440044/)
{% highlight java %}
94. HSSFRequest req = new HSSFRequest();
96. req.addListenerForAllRecords(parser);
{% endhighlight %}

***

### [EFBiffViewer.java](https://searchcode.com/codesearch/view/15642598/)
{% highlight java %}
76. HSSFRequest     req   = new HSSFRequest();
78. req.addListenerForAllRecords(new HSSFListener()
{% endhighlight %}

***

