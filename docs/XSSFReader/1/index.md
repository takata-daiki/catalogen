# XSSFReader @Cluster 1 (reader, sheetname, thenreturn)

***

### [StaxPoiSheet.java](https://searchcode.com/codesearch/view/95326031/)
> sets the this function and a < code > true < / code > if the specified name is a valid cell , the for an which should be used instead of the document in the document . @ param in the source file to be used to 
{% highlight java %}
38. public StaxPoiSheet( XSSFReader reader, String sheetName, String sheetID ) {
41.     sst = reader.getSharedStringsTable();
42.     sheetStream = reader.getSheet( sheetID );
{% endhighlight %}

***

### [XSSFWorkBookProcessorTest.java](https://searchcode.com/codesearch/view/110658551/)
> sets default of the shape in the shape . if there are no 
{% highlight java %}
58. final XSSFReader xssfReader = mock(XSSFReader.class);
59. when(xssfReader.getSheetsData()).thenReturn(sheetIterator);
60. when(xssfReader.getSharedStringsTable()).thenReturn(sharedStringsTable);
{% endhighlight %}

***

### [XSSFWorkBookProcessor.java](https://searchcode.com/codesearch/view/110658569/)
> sets whether the rowcolheadings are shown in a viewer @ param show whether to show rowcolheadings or not 
{% highlight java %}
33. final XSSFReader xssfReader = xssfReaderFactory.create(stream);
37.     XSSFReader.SheetIterator sheetsData = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
38.     processSheets(sheetsData, sheetProcessor, xssfReader.getSharedStringsTable());
{% endhighlight %}

***

