# XMLSlideShow @Cluster 4

***

### [StreamingPPTXImpl.java](https://searchcode.com/codesearch/view/76071743/)
{% highlight java %}
54. XMLSlideShow ppt = new XMLSlideShow();
56. XSLFSlide slide = ppt.createSlide();
95. int pictureIndex = ppt.addPicture(data,
102. ppt.write(outs);
{% endhighlight %}

***

### [XSLFPowerPointExtractorDecorator.java](https://searchcode.com/codesearch/view/111785576/)
{% highlight java %}
55. XMLSlideShow xmlSlideShow = new XMLSlideShow(slideShow);
57. XSLFSlide[] slides = xmlSlideShow.getSlides();
62.     CTNotesSlide notes = xmlSlideShow._getXSLFSlideShow().getNotes(
64.     CTCommentList comments = xmlSlideShow._getXSLFSlideShow()
{% endhighlight %}

***

### [MarketingReportGeneratorImpl.java](https://searchcode.com/codesearch/view/92131913/)
{% highlight java %}
40. XMLSlideShow ppt = new XMLSlideShow(getClass().getResourceAsStream("/marketing_template.pptx"));
63. Arrays.stream(ppt.getSlides()).forEach(reportSettings::fillBackgroundAndIncrementIndex);
65. ppt.write(outputStream);
{% endhighlight %}

***

