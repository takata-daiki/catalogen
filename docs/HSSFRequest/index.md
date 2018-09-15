# HSSFRequest

***

## [Cluster 1](./1)
2 results
> code comments is here.
{% highlight java %}
76. HSSFRequest     req   = new HSSFRequest();
78. req.addListenerForAllRecords(new HSSFListener()
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
102. HSSFRequest request = new HSSFRequest();
105.   request.addListenerForAllRecords(formatListener);
109.   request.addListenerForAllRecords(workbookBuildingListener);
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
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

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
151. protected short genericProcessEvents(HSSFRequest req, RecordInputStream in)
188.         userCode = req.processRecord(rec);
200.             userCode = req.processRecord(
248.       userCode = req.processRecord(rec);
{% endhighlight %}

***

