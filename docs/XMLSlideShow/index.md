# XMLSlideShow

***

### [Cluster 1](./1)
{% highlight java %}
17. public XMLSlideShow generate(XMLSlideShow ppt, MarketingReportSettings reportSettings,
20.     XSLFSlide slide1 = ppt.createSlide();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
498. XMLSlideShow ppt = shape.getSheet().getSlideShow();
499. CTTextParagraphProperties themeProps = ppt.getDefaultParagraphStyle(_p.getLevel());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
1107. XMLSlideShow ppt = getParentShape().getSheet().getSlideShow();
1108. CTTextParagraphProperties themeProps = ppt.getDefaultParagraphStyle(getLevel());
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
55. XMLSlideShow xmlSlideShow = new XMLSlideShow(slideShow);
57. XSLFSlide[] slides = xmlSlideShow.getSlides();
62.     CTNotesSlide notes = xmlSlideShow._getXSLFSlideShow().getNotes(
64.     CTCommentList comments = xmlSlideShow._getXSLFSlideShow()
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
73. private void drawImage(XMLSlideShow ppt, XSLFSlide slide) throws IOException {
75.     int idx = ppt.addPicture(bytes, XSLFPictureData.PICTURE_TYPE_PNG);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
42. private XMLSlideShow generatePreview(XMLSlideShow ppt, List<PreviewReportingDocument> previewData, String cityName,
45.     XSLFSlide slide = ppt.createSlide();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
104. private void extractContent(final StringBuilder buffy, final XMLSlideShow xmlSlideShow) throws IOException, XmlException {
105.   final XSLFSlide[] slides = xmlSlideShow.getSlides();
110.     final CTNotesSlide notes = xmlSlideShow._getXSLFSlideShow().getNotes(slideId);
111.     final CTCommentList comments = xmlSlideShow._getXSLFSlideShow().getSlideComments(slideId);
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
545. XMLSlideShow ppt = getSlideShow();
546. int pictureIdx = ppt.addPicture(data.getData(), data.getPictureType());
547. PackagePart pic = ppt.getAllPictures().get(pictureIdx).getPackagePart();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
49. XMLSlideShow xmlslideshow = null;
54.   return getBeanList(pptBean, xmlslideshow.getSlides());
{% endhighlight %}

***

