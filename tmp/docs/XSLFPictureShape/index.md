# XSLFPictureShape

***

### [Cluster 1](./1)
{% highlight java %}
98. XSLFPictureShape shape = slide.createPicture(pictureIndex);
99. shape.setAnchor(new Rectangle(5, 5, mapLink.getWidth(), mapLink
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
254. XSLFPictureShape sh = getDrawing().createPicture(rel.getId());
255. sh.resize();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
146. XSLFPictureShape p = (XSLFPictureShape)sh;
147. String blipId = p.getBlipId();
148. String relId = getSheet().importBlip(blipId, p.getSheet().getPackagePart());
{% endhighlight %}

***

