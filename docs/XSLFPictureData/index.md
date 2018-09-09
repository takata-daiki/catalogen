# XSLFPictureData

***

### [Cluster 1](./1)
{% highlight java %}
89. XSLFPictureData pict = getPictureData();
92.     BufferedImage img = ImageIO.read(new ByteArrayInputStream(pict.getData()));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
371. for(XSLFPictureData pic : getAllPictures()){
372.     if(pic.getChecksum() == checksum) {
{% endhighlight %}

***

