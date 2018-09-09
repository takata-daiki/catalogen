# SheetIterator

***

### [Cluster 1](./1)
{% highlight java %}
51. final XSSFReader.SheetIterator sheetIterator = mock(XSSFReader.SheetIterator.class);
52. stub(sheetIterator.hasNext()).toReturn(true).toReturn(false);
53. when(sheetIterator.next()).thenReturn(inputStream);
54. when(sheetIterator.getSheetName()).thenReturn(sheetName);
{% endhighlight %}

***

