# POIFSDocumentPath

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
53. private POIFSDocumentPath path;
105.             rval = this.path.equals(descriptor.path)
122.         hashcode = path.hashCode() ^ name.hashCode();
129.     StringBuffer buffer = new StringBuffer(40 * (path.length() + 1));
131.     for (int j = 0; j < path.length(); j++)
133.         buffer.append(path.getComponent(j)).append("/");
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
129. public POIFSDocumentPath(final POIFSDocumentPath path,
135.         this.components = new String[ path.components.length ];
140.             new String[ path.components.length + components.length ];
142.     for (int j = 0; j < path.components.length; j++)
144.         this.components[ j ] = path.components[ j ];
156.             this.components[ j + path.components.length ] =
{% endhighlight %}

***

