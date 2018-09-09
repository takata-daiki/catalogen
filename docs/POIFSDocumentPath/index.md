# POIFSDocumentPath

***

### [Cluster 1](./1)
{% highlight java %}
129. public POIFSDocumentPath(final POIFSDocumentPath path,
135.         this.components = new String[ path.components.length ];
140.             new String[ path.components.length + components.length ];
142.     for (int j = 0; j < path.components.length; j++)
144.         this.components[ j ] = path.components[ j ];
156.             this.components[ j + path.components.length ] =
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
336. POIFSDocumentPath   path    = event.getPath();
344.     int pathLength = path.length();
348.         System.out.print("/" + path.getComponent(k));
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
184. POIFSDocumentPath path = ( POIFSDocumentPath ) o;
186. if (path.components.length == this.components.length)
191.         if (!path.components[ j ]
{% endhighlight %}

***

