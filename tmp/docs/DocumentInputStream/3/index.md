# DocumentInputStream @Cluster 3 (is, position, rawdatapos)

***

### [Bitmap.java](https://searchcode.com/codesearch/view/97394490/)
> read < tt > will < tt > to the specified ( return ) or 1 / sheet . @ throws evaluationexception ( # num ! ) if < tt > result < / tt > is < tt > nan < / > or < tt > infinity < / tt > 
{% highlight java %}
43. DocumentInputStream is = getStream();
46.   if (rawdataPos > is.position()) {
48.     is.skip(rawdataPos - is.position() + 17);
50.   is.reset();
51.   is.skip(rawdataPos + 17);
54.   is.read(imgdata, 0, imgdata.length);
{% endhighlight %}

***

