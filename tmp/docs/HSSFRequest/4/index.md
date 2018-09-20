# HSSFRequest @Cluster 4

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

