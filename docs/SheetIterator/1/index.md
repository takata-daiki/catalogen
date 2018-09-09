# SheetIterator @Cluster 1

***

### [XSSFWorkBookProcessorTest.java](https://searchcode.com/codesearch/view/110658551/)
{% highlight java %}
51. final XSSFReader.SheetIterator sheetIterator = mock(XSSFReader.SheetIterator.class);
52. stub(sheetIterator.hasNext()).toReturn(true).toReturn(false);
53. when(sheetIterator.next()).thenReturn(inputStream);
54. when(sheetIterator.getSheetName()).thenReturn(sheetName);
{% endhighlight %}

***

