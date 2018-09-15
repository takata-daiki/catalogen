# Header @Cluster 1

***

### [WMF.java](https://searchcode.com/codesearch/view/97394516/)
{% highlight java %}
49. Header header = new Header();
50. header.read(rawdata, CHECKSUM_SIZE);
51. is.skip(header.getSize() + CHECKSUM_SIZE);
54. aldus.left = header.bounds.x;
55. aldus.top = header.bounds.y;
56. aldus.right = header.bounds.x + header.bounds.width;
57. aldus.bottom = header.bounds.y + header.bounds.height;
{% endhighlight %}

***

