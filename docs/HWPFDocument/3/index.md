# HWPFDocument @Cluster 3

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
532. HWPFDocument doc = (HWPFDocument) _doc;
539. PlexOfCps binTable = new PlexOfCps( doc.getTableStream(), doc
540.         .getFileInformationBlock().getFcPlcfbtePapx(), doc
555.             mainStream, doc.getDataStream(), pageOffset,
556.             doc.getTextTable() );
{% endhighlight %}

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
{% highlight java %}
19. HWPFDocument doc = new HWPFDocument(fs);
22. Range range = doc.getRange();
48. DocumentSummaryInformation dsi = doc.getDocumentSummaryInformation();
55. doc.write(new FileOutputStream("new-hwpf-file.doc"));
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/111785561/)
{% highlight java %}
44. HWPFDocument document = new HWPFDocument(filesystem);
69. PicturesTable pictureTable = document.getPicturesTable();
{% endhighlight %}

***

### [WordUtil.java](https://searchcode.com/codesearch/view/69098620/)
{% highlight java %}
25. HWPFDocument doc = new HWPFDocument(fs);
26. Range range = doc.getRange();
36. doc.write(new FileOutputStream(distFile));
{% endhighlight %}

***

