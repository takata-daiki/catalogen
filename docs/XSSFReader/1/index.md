# XSSFReader @Cluster 1

***

### [XSSFWorkBookProcessorTest.java](https://searchcode.com/codesearch/view/110658551/)
{% highlight java %}
58. final XSSFReader xssfReader = mock(XSSFReader.class);
59. when(xssfReader.getSheetsData()).thenReturn(sheetIterator);
60. when(xssfReader.getSharedStringsTable()).thenReturn(sharedStringsTable);
{% endhighlight %}

***

### [XSSFWorkBookProcessor.java](https://searchcode.com/codesearch/view/110658569/)
{% highlight java %}
33. final XSSFReader xssfReader = xssfReaderFactory.create(stream);
37.     XSSFReader.SheetIterator sheetsData = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
38.     processSheets(sheetsData, sheetProcessor, xssfReader.getSharedStringsTable());
{% endhighlight %}

***

