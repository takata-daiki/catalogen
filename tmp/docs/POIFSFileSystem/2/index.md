# POIFSFileSystem @Cluster 2

***

### [ExcelDataService.java](https://searchcode.com/codesearch/view/92669291/)
{% highlight java %}
223. POIFSFileSystem poifs = new POIFSFileSystem(bis);
225. dis = poifs.createDocumentInputStream("Workbook");
{% endhighlight %}

***

### [AbstractPOIFSExtractor.java](https://searchcode.com/codesearch/view/111785564/)
{% highlight java %}
97. POIFSFileSystem newFS = new POIFSFileSystem();
98. copy(dir, newFS.getRoot());
103.     newFS.writeFilesystem(out);
{% endhighlight %}

***

### [HSLFSlideShow.java](https://searchcode.com/codesearch/view/97394255/)
{% highlight java %}
484. POIFSFileSystem outFS = new POIFSFileSystem();
540. outFS.createDocument(bais,"PowerPoint Document");
564.     outFS.createDocument(
576. outFS.writeFilesystem(out);
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/48925096/)
{% highlight java %}
59. POIFSFileSystem fsys = new POIFSFileSystem(in);
63.     (DocumentEntry)fsys.getRoot().getEntry("WordDocument");
64. DocumentInputStream din = fsys.createDocumentInputStream("WordDocument");
111. DocumentEntry table = (DocumentEntry)fsys.getRoot().getEntry(tableName);
114. din = fsys.createDocumentInputStream(tableName);
{% endhighlight %}

***

### [HWPFDocument.java](https://searchcode.com/codesearch/view/97383956/)
{% highlight java %}
928. POIFSFileSystem pfs = new POIFSFileSystem();
941.             pfs.createDocument( new ByteArrayInputStream( mainBuf ),
950.             _objectPool.writeTo( pfs.getRoot() );
959.             pfs.createDocument( new ByteArrayInputStream( tableBuf ),
979.             pfs.createDocument( new ByteArrayInputStream( dataBuf ),
986.         EntryUtils.copyNodeRecursively( entry, pfs.getRoot() );
991.     pfs.createDocument( new ByteArrayInputStream( mainBuf ),
994.     pfs.createDocument( new ByteArrayInputStream( tableBuf ),
999.     pfs.createDocument( new ByteArrayInputStream( dataBuf ),
1002.     _objectPool.writeTo( pfs.getRoot() );
1004. pfs.writeFilesystem( out );
1005. this.directory = pfs.getRoot();
{% endhighlight %}

***

