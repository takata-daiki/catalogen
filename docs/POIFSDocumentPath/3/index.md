# POIFSDocumentPath @Cluster 3 (components, length, path)

***

### [POIFSDocumentPath.java](https://searchcode.com/codesearch/view/15642282/)
> test that we get the same value as excel and , for 
{% highlight java %}
129. public POIFSDocumentPath(final POIFSDocumentPath path,
135.         this.components = new String[ path.components.length ];
140.             new String[ path.components.length + components.length ];
142.     for (int j = 0; j < path.components.length; j++)
144.         this.components[ j ] = path.components[ j ];
156.             this.components[ j + path.components.length ] =
{% endhighlight %}

***

