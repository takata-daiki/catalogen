# POIFSReader

***

### [Cluster 1](./1)
{% highlight java %}
54. private POIFSReader reader = null;
69.   this.reader.registerListener(
74.     reader.read(input);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
42. final POIFSReader r = new POIFSReader();
44. r.registerListener(new MyPOIFSReaderListener(stream));
45. r.read(inStream);
{% endhighlight %}

***

