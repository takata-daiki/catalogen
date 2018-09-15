# DocumentInputStream @Cluster 1

***

### [ContentReaderListener.java](https://searchcode.com/codesearch/view/48925118/)
{% highlight java %}
77. final DocumentInputStream dis = event.getStream();
78. final byte pptdata[] = new byte[dis.available()];
79. dis.read(pptdata, 0, dis.available());
{% endhighlight %}

***

### [ContentReaderListener.java](https://searchcode.com/codesearch/view/138791632/)
{% highlight java %}
77. final DocumentInputStream dis = event.getStream();
78. final byte pptdata[] = new byte[dis.available()];
79. dis.read(pptdata, 0, dis.available());
{% endhighlight %}

***

### [PPTContentDigester.java](https://searchcode.com/codesearch/view/129866876/)
{% highlight java %}
81. DocumentInputStream input = event.getStream();
82. byte[] buffer = new byte[input.available()];
83. input.read(buffer, 0, input.available());
{% endhighlight %}

***

### [PowerPointParser.java](https://searchcode.com/codesearch/view/7760072/)
{% highlight java %}
51. DocumentInputStream input = event.getStream();
52. byte buffer[] = new byte[input.available()];
53. input.read(buffer, 0, input.available());
{% endhighlight %}

***

### [PPT2Text.java](https://searchcode.com/codesearch/view/126168426/)
{% highlight java %}
62. DocumentInputStream dis = null;
65. final byte btoWrite[] = new byte[dis.available()];
66. dis.read(btoWrite, 0, dis.available());
{% endhighlight %}

***

