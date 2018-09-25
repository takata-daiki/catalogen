# POIFSFileSystem @Cluster 2 (bis, newfs, poifs)

***

### [ExcelDataService.java](https://searchcode.com/codesearch/view/92669291/)
> < p > writes an unsigned two - byte value to an output stream . < / p > 
{% highlight java %}
223. POIFSFileSystem poifs = new POIFSFileSystem(bis);
225. dis = poifs.createDocumentInputStream("Workbook");
{% endhighlight %}

***

### [AbstractPOIFSExtractor.java](https://searchcode.com/codesearch/view/111785564/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
97. POIFSFileSystem newFS = new POIFSFileSystem();
98. copy(dir, newFS.getRoot());
103.     newFS.writeFilesystem(out);
{% endhighlight %}

***

