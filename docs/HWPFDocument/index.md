# HWPFDocument

***

### [Cluster 1](./1)
{% highlight java %}
116. HWPFDocument doc = null;
129. Range r = doc.getRange(); // ??word?????
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
502. HWPFDocument document = (HWPFDocument) _doc;
504. if ( document.getOfficeDrawingsHeaders() != null )
507.     for ( OfficeDrawing officeDrawing : document
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
19. HWPFDocument doc = new HWPFDocument(fs);
22. Range range = doc.getRange();
48. DocumentSummaryInformation dsi = doc.getDocumentSummaryInformation();
55. doc.write(new FileOutputStream("new-hwpf-file.doc"));
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
1010. private boolean processOle2( HWPFDocument doc, CharacterRun characterRun,
1013.     Entry entry = doc.getObjectsPool().getObjectById(
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
82. private static void convert(HWPFDocument doc,
86.   org.apache.poi.hwpf.model.StyleSheet stylesheet = doc.getStyleSheet();
92.   org.apache.poi.hwpf.model.ListTables listTables = doc.getListTables();
96.   org.apache.poi.hwpf.model.DocumentProperties docProps = doc.getDocProperties();
104.   Range r = doc.getRange();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
157. final HWPFDocument doc = ((HWPFDocument)docObj);
164.       final Bookmarks bookmarks = doc.getBookmarks();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
743. protected void processDrawnObject( HWPFDocument doc,
750.     OfficeDrawing officeDrawing = doc.getOfficeDrawingsMain()
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
38. HWPFDocument doc = HWPFTestDataSamples.openSampleFile("WithArtShapes.doc");
40. List shapes = doc.getShapesTable().getAllShapes();
41. List vshapes = doc.getShapesTable().getVisibleShapes();
60. doc.write(baos);
64. shapes = doc.getShapesTable().getAllShapes();
65. vshapes = doc.getShapesTable().getVisibleShapes();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
354. HWPFDocument document = (HWPFDocument) _doc;
355. Bookmarks bookmarks = document.getBookmarks();
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
692. HWPFDocument hwpfDocument = (HWPFDocument) _doc;
694. for ( int s = 0; s < hwpfDocument.getStyleSheet().numStyles(); s++ )
696.     StyleDescription styleDescription = hwpfDocument.getStyleSheet()
{% endhighlight %}

***

### [Cluster 11](./11)
{% highlight java %}
447. HWPFDocument document = (HWPFDocument) _doc;
452.     for ( Field field : document.getFields().getFields( part ) )
{% endhighlight %}

***

### [Cluster 12](./12)
{% highlight java %}
488. HWPFDocument newFormat = (HWPFDocument) wordDocument;
489. Picture picture = newFormat.getPicturesTable().extractPicture(
{% endhighlight %}

***

