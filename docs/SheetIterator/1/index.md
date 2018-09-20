# SheetIterator @Cluster 1

***

### [XSSFWorkBookProcessor.java](https://searchcode.com/codesearch/view/110658569/)
{% highlight java %}
50. private void processSheets(final XSSFReader.SheetIterator sheetsData, final SheetProcessor sheetProcessor, final SharedStringsTable sharedStringsTable)
52.     while(sheetsData.hasNext())
54.         processNext(sheetsData.next(), sheetsData.getSheetName(), sheetProcessor, sharedStringsTable);
{% endhighlight %}

***

