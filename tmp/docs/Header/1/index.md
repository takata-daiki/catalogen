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

### [WMF.java](https://searchcode.com/codesearch/view/97394516/)
{% highlight java %}
81. Header header = new Header();
82. header.wmfsize = data.length - aldus.getSize();
83. header.bounds = new and.awt.Rectangle((short)aldus.left, (short)aldus.top, (short)aldus.right-(short)aldus.left, (short)aldus.bottom-(short)aldus.top);
86. header.size = new and.awt.Dimension(header.bounds.width*coeff, header.bounds.height*coeff);
87. header.zipsize = compressed.length;
92. header.write(out);
{% endhighlight %}

***

### [PICT.java](https://searchcode.com/codesearch/view/97394495/)
{% highlight java %}
68. Header header = new Header();
69. header.read(data, pos);
70. bis.skip(pos + header.getSize());
{% endhighlight %}

***

