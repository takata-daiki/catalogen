# DocumentInputStream

***

## [Cluster 1](./1)
5 results
> code comments is here.
{% highlight java %}
62. DocumentInputStream dis = null;
65. final byte btoWrite[] = new byte[dis.available()];
66. dis.read(btoWrite, 0, dis.available());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
43. DocumentInputStream is = getStream();
46.   if (rawdataPos > is.position()) {
48.     is.skip(rawdataPos - is.position() + 17);
50.   is.reset();
51.   is.skip(rawdataPos + 17);
54.   is.read(imgdata, 0, imgdata.length);
{% endhighlight %}

***

