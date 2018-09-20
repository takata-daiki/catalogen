# HWPFDocument @Cluster 2

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
488. HWPFDocument newFormat = (HWPFDocument) wordDocument;
489. Picture picture = newFormat.getPicturesTable().extractPicture(
{% endhighlight %}

***

### [WordExport.java](https://searchcode.com/codesearch/view/134954814/)
{% highlight java %}
157. final HWPFDocument doc = ((HWPFDocument)docObj);
164.       final Bookmarks bookmarks = doc.getBookmarks();
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
354. HWPFDocument document = (HWPFDocument) _doc;
355. Bookmarks bookmarks = document.getBookmarks();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
743. protected void processDrawnObject( HWPFDocument doc,
750.     OfficeDrawing officeDrawing = doc.getOfficeDrawingsMain()
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
1010. private boolean processOle2( HWPFDocument doc, CharacterRun characterRun,
1013.     Entry entry = doc.getObjectsPool().getObjectById(
{% endhighlight %}

***

### [FilesGatherer.java](https://searchcode.com/codesearch/view/13078978/)
{% highlight java %}
116. HWPFDocument doc = null;
129. Range r = doc.getRange(); // ??word?????
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
447. HWPFDocument document = (HWPFDocument) _doc;
452.     for ( Field field : document.getFields().getFields( part ) )
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
309. final HWPFDocument doc = (HWPFDocument) wordDocument;
311. Map<Integer, List<Bookmark>> rangeBookmarks = doc.getBookmarks()
{% endhighlight %}

***

### [PrintTemplateServiceImpl.java](https://searchcode.com/codesearch/view/94110212/)
{% highlight java %}
1340. HWPFDocument hdt = null;
1346. Fields fields = hdt.getFields();
1352. Range range = hdt.getRange();
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
692. HWPFDocument hwpfDocument = (HWPFDocument) _doc;
694. for ( int s = 0; s < hwpfDocument.getStyleSheet().numStyles(); s++ )
696.     StyleDescription styleDescription = hwpfDocument.getStyleSheet()
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
502. HWPFDocument document = (HWPFDocument) _doc;
504. if ( document.getOfficeDrawingsHeaders() != null )
507.     for ( OfficeDrawing officeDrawing : document
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
960. protected void processNoteAnchor( HWPFDocument doc,
964.         Notes footnotes = doc.getFootnotes();
970.             Range footnoteRange = doc.getFootnoteRange();
987.         Notes endnotes = doc.getEndnotes();
992.             Range endnoteRange = doc.getEndnoteRange();
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
{% highlight java %}
82. private static void convert(HWPFDocument doc,
86.   org.apache.poi.hwpf.model.StyleSheet stylesheet = doc.getStyleSheet();
92.   org.apache.poi.hwpf.model.ListTables listTables = doc.getListTables();
96.   org.apache.poi.hwpf.model.DocumentProperties docProps = doc.getDocProperties();
104.   Range r = doc.getRange();
{% endhighlight %}

***

### [TestShapes.java](https://searchcode.com/codesearch/view/97397200/)
{% highlight java %}
38. HWPFDocument doc = HWPFTestDataSamples.openSampleFile("WithArtShapes.doc");
40. List shapes = doc.getShapesTable().getAllShapes();
41. List vshapes = doc.getShapesTable().getVisibleShapes();
60. doc.write(baos);
64. shapes = doc.getShapesTable().getAllShapes();
65. vshapes = doc.getShapesTable().getVisibleShapes();
{% endhighlight %}

***

