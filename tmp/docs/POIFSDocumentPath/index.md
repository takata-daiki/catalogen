# POIFSDocumentPath

***

## [Cluster 1 (if, parent, path)](./1)
2 results
> sets the list of colours that are interpolated between . the number must match { @ link # / } and { @ link # , ( ) } and { @ link # @ see # to ( ) } if < code > null < / code > for default ( if ' array ) should be a valid range to 0 . 
{% highlight java %}
184. POIFSDocumentPath path = ( POIFSDocumentPath ) o;
186. if (path.components.length == this.components.length)
191.         if (!path.components[ j ]
{% endhighlight %}

***

## [Cluster 2 (hashcode, name, path)](./2)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
53. private POIFSDocumentPath path;
105.             rval = this.path.equals(descriptor.path)
122.         hashcode = path.hashCode() ^ name.hashCode();
129.     StringBuffer buffer = new StringBuffer(40 * (path.length() + 1));
131.     for (int j = 0; j < path.length(); j++)
133.         buffer.append(path.getComponent(j)).append("/");
{% endhighlight %}

***

## [Cluster 3 (components, length, path)](./3)
1 results
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

