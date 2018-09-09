# XWPFPictureData

***

### [Cluster 1](./1)
{% highlight java %}
271. XWPFPictureData pictureData = XWPFPictureUtil.getPictureData( document, blipId );
274.     String src = pictureData.getFileName();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
1255. XWPFPictureData pictureData = super.getPictureData( picture );
1260.         Image img = Image.getInstance( pictureData.getData() );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
575. XWPFPictureData pictureData = XWPFPictureUtil.getPictureData( document, blipId );
581.         Image img = Image.getInstance( pictureData.getData() );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
1310. XWPFPictureData pictureData = getPictureData( picture );
1315.         extractor.extract( WORD_MEDIA + pictureData.getFileName(), pictureData.getData() );
1320.                     "Error while extracting the image " + pictureData.getFileName(), e );
{% endhighlight %}

***

