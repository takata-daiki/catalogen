# POIFSFileSystem @Cluster 1 (entry, hssfrequest, outfs)

***

### [OfficeParser.java](https://searchcode.com/codesearch/view/111785560/)
> < p > sets the poi file ' s path . < / p > 
{% highlight java %}
97. public static POIFSDocumentType detectType(POIFSFileSystem fs) {
98.     return detectType(fs.getRoot());
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/111785561/)
> copies the formula with a given formula , the formula . if the formula is , the formula it is of the cell parse , 
{% highlight java %}
42. POIFSFileSystem filesystem, XHTMLContentHandler xhtml)
109.     (DirectoryEntry) filesystem.getRoot().getEntry("ObjectPool");
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
> processes a file into essentially record events . @ param req an instance of hssfrequest which has your registered listeners @ param in a the workbook ' s name in the workbook @ throws ioexception if the workbook contained errors 
{% highlight java %}
135.     POIFSFileSystem filesystem, XHTMLContentHandler xhtml,
141. for (Entry entry : filesystem.getRoot()) {
{% endhighlight %}

***

### [HSLFSlideShow.java](https://searchcode.com/codesearch/view/97394255/)
> < p > writes an unsigned two - byte value to an output stream . < / p > 
{% highlight java %}
139. public HSLFSlideShow(POIFSFileSystem filesystem) throws IOException
141.   this(filesystem.getRoot());
{% endhighlight %}

***

