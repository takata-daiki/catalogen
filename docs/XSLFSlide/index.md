# XSLFSlide

***

### [Cluster 1](./1)
{% highlight java %}
58. for (XSLFSlide slide : slides) {
59.     CTSlide rawSlide = slide._getCTSlide();
60.     CTSlideIdListEntry slideId = slide._getCTSlideId();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
106. for (final XSLFSlide slide : slides) {
107.   final CTSlide rawSlide = slide._getCTSlide();
108.   final CTSlideIdListEntry slideId = slide._getCTSlideId();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
216. XSLFSlide slide = (XSLFSlide)createRelationship(
221. slideId.setId2(slide.getPackageRelationship().getId());
224. slide.addRelation(layout.getPackageRelationship().getId(), layout);
227. slide.getPackagePart().addRelationship(ppName, TargetMode.INTERNAL,
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
20. XSLFSlide slide1 = ppt.createSlide();
22. XSLFTextBox txt1 = slide1.createTextBox();
36. XSLFTextBox txt2 = slide1.createTextBox();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
144. XSLFSlide pptSlide = ppt.createSlide();
146. XSLFTextBox txt1 = pptSlide.createTextBox();
157. XSLFTextBox txt2 = pptSlide.createTextBox();
173.     XSLFPictureShape pic = pptSlide.createPicture(idx);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
45. XSLFSlide slide = ppt.createSlide();
47. XSLFTextBox txt1 = slide.createTextBox();
57. XSLFTextBox txt2 = slide.createTextBox();
69. XSLFTable table = slide.createTable();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
56. XSLFSlide slide = ppt.createSlide();
98. XSLFPictureShape shape = slide.createPicture(pictureIndex);
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
73. private void drawImage(XMLSlideShow ppt, XSLFSlide slide) throws IOException {
76.     XSLFPictureShape pic = slide.createPicture(idx);
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
112. org.apache.poi.xslf.usermodel.XSLFSlide xslfSlide = slides[i];
113. CTSlide rawSlide = xslfSlide._getCTSlide();
{% endhighlight %}

***

