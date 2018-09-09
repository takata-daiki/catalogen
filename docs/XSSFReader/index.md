# XSSFReader

***

### [Cluster 1](./1)
{% highlight java %}
58. final XSSFReader xssfReader = mock(XSSFReader.class);
59. when(xssfReader.getSheetsData()).thenReturn(sheetIterator);
60. when(xssfReader.getSharedStringsTable()).thenReturn(sharedStringsTable);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
43. XSSFReader r = new XSSFReader(pkg);
44. SharedStringsTable sst = r.getSharedStringsTable();
50. InputStream sheet2 = r.getSheet("rId"+sheetId);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
25. private XSSFReader reader;
64.     workbookData = reader.getWorkbookData();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
38. public StaxPoiSheet( XSSFReader reader, String sheetName, String sheetID ) {
41.     sst = reader.getSharedStringsTable();
42.     sheetStream = reader.getSheet( sheetID );
{% endhighlight %}

***

