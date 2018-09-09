# DrawingGroupRecord @Cluster 2

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
285. for(DrawingGroupRecord dgr : drawingGroups) {
286.    dgr.decode();
287.    findPictures(dgr.getEscherRecords());
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
1255. DrawingGroupRecord r = (DrawingGroupRecord) workbook.findFirstRecordBySid( DrawingGroupRecord.sid );
1256. r.decode();
1257. List escherRecords = r.getEscherRecords();
{% endhighlight %}

***

### [RecordFactory.java](https://searchcode.com/codesearch/view/15642481/)
{% highlight java %}
156. DrawingGroupRecord lastDGRecord = (DrawingGroupRecord) lastRecord;
157.     lastDGRecord.join((AbstractEscherHolderRecord) record);
{% endhighlight %}

***

