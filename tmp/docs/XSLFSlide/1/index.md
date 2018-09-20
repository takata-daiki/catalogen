# XSLFSlide @Cluster 1

***

### [PptReader.java](https://searchcode.com/codesearch/view/14046023/)
{% highlight java %}
112. org.apache.poi.xslf.usermodel.XSLFSlide xslfSlide = slides[i];
113. CTSlide rawSlide = xslfSlide._getCTSlide();
{% endhighlight %}

***

### [MarketingReportCostEstimationGeneratorImpl.java](https://searchcode.com/codesearch/view/92131918/)
{% highlight java %}
19. XSLFSlide slide1 = ppt.createSlide();
21. XSLFTextBox txt1 = slide1.createTextBox();
{% endhighlight %}

***

### [StreamingPPTXImpl.java](https://searchcode.com/codesearch/view/76071743/)
{% highlight java %}
56. XSLFSlide slide = ppt.createSlide();
98. XSLFPictureShape shape = slide.createPicture(pictureIndex);
{% endhighlight %}

***

### [MarketingReportFirstSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131912/)
{% highlight java %}
73. private void drawImage(XMLSlideShow ppt, XSLFSlide slide) throws IOException {
76.     XSLFPictureShape pic = slide.createPicture(idx);
{% endhighlight %}

***

### [MarketingReportFirstSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131912/)
{% highlight java %}
39. XSLFSlide slide1 = ppt.createSlide();
42. XSLFTextBox txt1 = slide1.createTextBox();
53. XSLFTextBox txt2 = slide1.createTextBox();
{% endhighlight %}

***

### [XSLFPowerPointExtractorDecorator.java](https://searchcode.com/codesearch/view/111785576/)
{% highlight java %}
58. for (XSLFSlide slide : slides) {
59.     CTSlide rawSlide = slide._getCTSlide();
60.     CTSlideIdListEntry slideId = slide._getCTSlideId();
{% endhighlight %}

***

### [MarketingReportLastSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131911/)
{% highlight java %}
20. XSLFSlide slide1 = ppt.createSlide();
22. XSLFTextBox txt1 = slide1.createTextBox();
36. XSLFTextBox txt2 = slide1.createTextBox();
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
45. XSLFSlide slide = ppt.createSlide();
47. XSLFTextBox txt1 = slide.createTextBox();
57. XSLFTextBox txt2 = slide.createTextBox();
69. XSLFTable table = slide.createTable();
{% endhighlight %}

***

### [XMLSlideShow.java](https://searchcode.com/codesearch/view/97406883/)
{% highlight java %}
216. XSLFSlide slide = (XSLFSlide)createRelationship(
221. slideId.setId2(slide.getPackageRelationship().getId());
224. slide.addRelation(layout.getPackageRelationship().getId(), layout);
227. slide.getPackagePart().addRelationship(ppName, TargetMode.INTERNAL,
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
144. XSLFSlide pptSlide = ppt.createSlide();
146. XSLFTextBox txt1 = pptSlide.createTextBox();
157. XSLFTextBox txt2 = pptSlide.createTextBox();
173.     XSLFPictureShape pic = pptSlide.createPicture(idx);
{% endhighlight %}

***

