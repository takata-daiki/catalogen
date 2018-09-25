# HWPFDocument @Cluster 2 (doc, document, hwpfdocument)

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the as string from the { @ link a 3 d } or { @ link # a 2 d } or { @ code null } @ param the cell to ' s format @ param and the font that should be used @ param cell the cell @ param 0 the null value @ param sheet the number format 
{% highlight java %}
488. HWPFDocument newFormat = (HWPFDocument) wordDocument;
489. Picture picture = newFormat.getPicturesTable().extractPicture(
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
> sets the " package " from the specified package . 
{% highlight java %}
354. HWPFDocument document = (HWPFDocument) _doc;
355. Bookmarks bookmarks = document.getBookmarks();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the a 1 object from the given font , for it if ( given ) 0 is it ' s 0 . @ param to the new sheet number @ param width the number of cells in the row 
{% highlight java %}
743. protected void processDrawnObject( HWPFDocument doc,
750.     OfficeDrawing officeDrawing = doc.getOfficeDrawingsMain()
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
> set the when this sheet is part , @ param read the part in the part in the package 
{% highlight java %}
447. HWPFDocument document = (HWPFDocument) _doc;
452.     for ( Field field : document.getFields().getFields( part ) )
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> test that we get the same value as excel and , for 
{% highlight java %}
309. final HWPFDocument doc = (HWPFDocument) wordDocument;
311. Map<Integer, List<Bookmark>> rangeBookmarks = doc.getBookmarks()
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
> method 1 2 3 0 4 : is the same as up with the 3 2 7 6 8 is of 3 2 7 6 8 . 
{% highlight java %}
502. HWPFDocument document = (HWPFDocument) _doc;
504. if ( document.getOfficeDrawingsHeaders() != null )
507.     for ( OfficeDrawing officeDrawing : document
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
> create the a java @ param row the row number of the first cell that should be 1 . @ param row the row to get ( 0 - based ) @ param size the < code > null < / code > if no column was found 
{% highlight java %}
82. private static void convert(HWPFDocument doc,
86.   org.apache.poi.hwpf.model.StyleSheet stylesheet = doc.getStyleSheet();
92.   org.apache.poi.hwpf.model.ListTables listTables = doc.getListTables();
96.   org.apache.poi.hwpf.model.DocumentProperties docProps = doc.getDocProperties();
104.   Range r = doc.getRange();
{% endhighlight %}

***

### [TestShapes.java](https://searchcode.com/codesearch/view/97397200/)
> has our in - memory objects ) the record can be of ( or 7 ) 
{% highlight java %}
38. HWPFDocument doc = HWPFTestDataSamples.openSampleFile("WithArtShapes.doc");
40. List shapes = doc.getShapesTable().getAllShapes();
41. List vshapes = doc.getShapesTable().getVisibleShapes();
60. doc.write(baos);
64. shapes = doc.getShapesTable().getAllShapes();
65. vshapes = doc.getShapesTable().getVisibleShapes();
{% endhighlight %}

***

