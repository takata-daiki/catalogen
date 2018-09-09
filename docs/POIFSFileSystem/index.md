# POIFSFileSystem

***

### [Cluster 1](./1)
{% highlight java %}
97. POIFSFileSystem newFS = new POIFSFileSystem();
98. copy(dir, newFS.getRoot());
103.     newFS.writeFilesystem(out);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
62. POIFSFileSystem filesystem, String entryName)
66.     (DocumentEntry) filesystem.getRoot().getEntry(entryName);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
97. public static POIFSDocumentType detectType(POIFSFileSystem fs) {
98.     return detectType(fs.getRoot());
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
135.     POIFSFileSystem filesystem, XHTMLContentHandler xhtml,
141. for (Entry entry : filesystem.getRoot()) {
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
58. POIFSFileSystem fs =
69. MediaType mt = detectCorel(fs.getRoot());
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
74. protected POIDocument(POIFSFileSystem fs) {
75.    this(fs.getRoot());
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
248. public void processFile(POIFSFileSystem filesystem, boolean listenForAllRecords)
276.       DocumentInputStream documentInputStream = filesystem.createDocumentInputStream("Workbook");
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
37. private POIFSFileSystem filesystem;
47.     (DocumentEntry)filesystem.getRoot().getEntry("VisioDocument");
51.   filesystem.createDocumentInputStream("VisioDocument").read(contents);
{% endhighlight %}

***

