# DocumentInputStream @Cluster 2

***

### [Bitmap.java](https://searchcode.com/codesearch/view/97394490/)
{% highlight java %}
43. DocumentInputStream is = getStream();
46.   if (rawdataPos > is.position()) {
48.     is.skip(rawdataPos - is.position() + 17);
50.   is.reset();
51.   is.skip(rawdataPos + 17);
54.   is.read(imgdata, 0, imgdata.length);
{% endhighlight %}

***

### [EntryUtils.java](https://searchcode.com/codesearch/view/97383212/)
{% highlight java %}
248. DocumentInputStream inpA = null, inpB = null;
255.       readA = inpA.read();
263.    if (inpA != null) inpA.close();
{% endhighlight %}

***

