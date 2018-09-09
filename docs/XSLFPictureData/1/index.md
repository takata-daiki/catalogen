# XSLFPictureData @Cluster 1

***

### [XMLSlideShow.java](https://searchcode.com/codesearch/view/97406883/)
{% highlight java %}
345. XSLFPictureData img = findPictureData(pictureData);
354.         OutputStream out = img.getPackagePart().getOutputStream();
{% endhighlight %}

***

### [XSLFPictureShape.java](https://searchcode.com/codesearch/view/97406705/)
{% highlight java %}
89. XSLFPictureData pict = getPictureData();
92.     BufferedImage img = ImageIO.read(new ByteArrayInputStream(pict.getData()));
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
543. XSLFPictureData data = new XSLFPictureData(blipPart, null);
546. int pictureIdx = ppt.addPicture(data.getData(), data.getPictureType());
{% endhighlight %}

***

### [XSLFImageRenderer.java](https://searchcode.com/codesearch/view/97406847/)
{% highlight java %}
79. public boolean drawImage(Graphics2D graphics, XSLFPictureData data,
82.     BufferedImage img = ImageIO.read(data.getPackagePart().getInputStream());
{% endhighlight %}

***

