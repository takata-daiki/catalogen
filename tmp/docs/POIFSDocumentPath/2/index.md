# POIFSDocumentPath @Cluster 2

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

