# POIFSDocumentPath @Cluster 1

***

### [POIFSDocumentPath.java](https://searchcode.com/codesearch/view/15642282/)
{% highlight java %}
129. public POIFSDocumentPath(final POIFSDocumentPath path,
135.         this.components = new String[ path.components.length ];
140.             new String[ path.components.length + components.length ];
142.     for (int j = 0; j < path.components.length; j++)
144.         this.components[ j ] = path.components[ j ];
156.             this.components[ j + path.components.length ] =
{% endhighlight %}

***

### [POIFSDocumentPath.java](https://searchcode.com/codesearch/view/15642282/)
{% highlight java %}
264. POIFSDocumentPath parent = new POIFSDocumentPath(null);
266. parent.components = new String[ length ];
267. System.arraycopy(components, 0, parent.components, 0, length);
{% endhighlight %}

***

### [DocumentDescriptor.java](https://searchcode.com/codesearch/view/15642285/)
{% highlight java %}
53. private POIFSDocumentPath path;
105.             rval = this.path.equals(descriptor.path)
122.         hashcode = path.hashCode() ^ name.hashCode();
129.     StringBuffer buffer = new StringBuffer(40 * (path.length() + 1));
131.     for (int j = 0; j < path.length(); j++)
133.         buffer.append(path.getComponent(j)).append("/");
{% endhighlight %}

***

