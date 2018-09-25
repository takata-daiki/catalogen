# DocumentInputStream

***

## [Cluster 1 (delegate, din, return)](./1)
5 results
> < p > sets the poi file ' s path . < / p > 
{% highlight java %}
55. DocumentInputStream dstream = new DocumentInputStream( dentry );
57. dstream.close();
{% endhighlight %}

***

## [Cluster 2](./2)
3 results
> the example that should be excel a not , that ' s all an empty property set this on it . 
{% highlight java %}
77. final DocumentInputStream dis = event.getStream();
78. final byte pptdata[] = new byte[dis.available()];
79. dis.read(pptdata, 0, dis.available());
{% endhighlight %}

***

## [Cluster 3 (is, position, rawdatapos)](./3)
1 results
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

