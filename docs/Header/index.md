# Header

***

### [Cluster 1](./1)
{% highlight java %}
68. Header header = new Header();
69. header.read(data, pos);
70. bis.skip(pos + header.getSize());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
85. Header header = new Header();
86. header.wmfsize = data.length - 512;
88. header.bounds = new and.awt.Rectangle(0, 0, 200, 200);
89. header.size = new and.awt.Dimension(header.bounds.width*Shape.EMU_PER_POINT,
90.         header.bounds.height*Shape.EMU_PER_POINT);
91. header.zipsize = compressed.length;
98. header.write(out);
{% endhighlight %}

***

