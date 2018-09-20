# POIFSFileSystem @Cluster 1

***

### [OfficeParser.java](https://searchcode.com/codesearch/view/111785560/)
{% highlight java %}
97. public static POIFSDocumentType detectType(POIFSFileSystem fs) {
98.     return detectType(fs.getRoot());
{% endhighlight %}

***

### [POIDocument.java](https://searchcode.com/codesearch/view/97383067/)
{% highlight java %}
217. protected void writePropertySet(String name, PropertySet set, POIFSFileSystem outFS) throws IOException {
225.     outFS.createDocument(bIn,name);
{% endhighlight %}

***

### [SummaryExtractor.java](https://searchcode.com/codesearch/view/111785558/)
{% highlight java %}
62. POIFSFileSystem filesystem, String entryName)
66.     (DocumentEntry) filesystem.getRoot().getEntry(entryName);
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
248. public void processFile(POIFSFileSystem filesystem, boolean listenForAllRecords)
276.       DocumentInputStream documentInputStream = filesystem.createDocumentInputStream("Workbook");
{% endhighlight %}

***

### [AbstractWordUtils.java](https://searchcode.com/codesearch/view/97383984/)
{% highlight java %}
509.     final POIFSFileSystem poifsFileSystem ) throws IOException
511. return loadDoc( poifsFileSystem.getRoot() );
{% endhighlight %}

***

### [HWPFDocument.java](https://searchcode.com/codesearch/view/97383956/)
{% highlight java %}
185. public HWPFDocument(POIFSFileSystem pfilesystem) throws IOException
187.   this(pfilesystem.getRoot());
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/111785561/)
{% highlight java %}
42. POIFSFileSystem filesystem, XHTMLContentHandler xhtml)
109.     (DirectoryEntry) filesystem.getRoot().getEntry("ObjectPool");
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
135.     POIFSFileSystem filesystem, XHTMLContentHandler xhtml,
141. for (Entry entry : filesystem.getRoot()) {
{% endhighlight %}

***

### [POIFSContainerDetector.java](https://searchcode.com/codesearch/view/111785504/)
{% highlight java %}
58. POIFSFileSystem fs =
69. MediaType mt = detectCorel(fs.getRoot());
{% endhighlight %}

***

### [OfficeParser.java](https://searchcode.com/codesearch/view/111785560/)
{% highlight java %}
160. POIFSFileSystem filesystem;
173. for (Entry entry : filesystem.getRoot()) {
{% endhighlight %}

***

### [POIDocument.java](https://searchcode.com/codesearch/view/97383067/)
{% highlight java %}
74. protected POIDocument(POIFSFileSystem fs) {
75.    this(fs.getRoot());
{% endhighlight %}

***

### [POIFSChunkParser.java](https://searchcode.com/codesearch/view/88636100/)
{% highlight java %}
47. public static ChunkGroup[] parse(POIFSFileSystem fs) throws IOException {
48.    return parse(fs.getRoot());
{% endhighlight %}

***

### [HSLFSlideShow.java](https://searchcode.com/codesearch/view/97394255/)
{% highlight java %}
139. public HSLFSlideShow(POIFSFileSystem filesystem) throws IOException
141.   this(filesystem.getRoot());
{% endhighlight %}

***

### [TestStreamBugs.java](https://searchcode.com/codesearch/view/97397248/)
{% highlight java %}
37. private POIFSFileSystem filesystem;
47.     (DocumentEntry)filesystem.getRoot().getEntry("VisioDocument");
51.   filesystem.createDocumentInputStream("VisioDocument").read(contents);
{% endhighlight %}

***

