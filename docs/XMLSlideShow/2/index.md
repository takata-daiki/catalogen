# XMLSlideShow @Cluster 2

***

### [MarketingReportCostEstimationGeneratorImpl.java](https://searchcode.com/codesearch/view/92131918/)
{% highlight java %}
17. public XMLSlideShow generate(XMLSlideShow ppt,MarketingReportSettings reportSettings,
19.     XSLFSlide slide1 = ppt.createSlide();
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
42. private XMLSlideShow generatePreview(XMLSlideShow ppt, List<PreviewReportingDocument> previewData, String cityName,
45.     XSLFSlide slide = ppt.createSlide();
{% endhighlight %}

***

### [MarketingReportFirstSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131912/)
{% highlight java %}
31. public XMLSlideShow generate(XMLSlideShow ppt, MarketingReportSettings reportSettings,
39.     XSLFSlide slide1 = ppt.createSlide();
{% endhighlight %}

***

### [XSLFTextRun.java](https://searchcode.com/codesearch/view/97406808/)
{% highlight java %}
498. XMLSlideShow ppt = shape.getSheet().getSlideShow();
499. CTTextParagraphProperties themeProps = ppt.getDefaultParagraphStyle(_p.getLevel());
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
{% highlight java %}
1107. XMLSlideShow ppt = getParentShape().getSheet().getSlideShow();
1108. CTTextParagraphProperties themeProps = ppt.getDefaultParagraphStyle(getLevel());
{% endhighlight %}

***

### [MarketingReportFirstSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131912/)
{% highlight java %}
73. private void drawImage(XMLSlideShow ppt, XSLFSlide slide) throws IOException {
75.     int idx = ppt.addPicture(bytes, XSLFPictureData.PICTURE_TYPE_PNG);
{% endhighlight %}

***

### [MarketingReportLastSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131911/)
{% highlight java %}
17. public XMLSlideShow generate(XMLSlideShow ppt, MarketingReportSettings reportSettings,
20.     XSLFSlide slide1 = ppt.createSlide();
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
545. XMLSlideShow ppt = getSlideShow();
546. int pictureIdx = ppt.addPicture(data.getData(), data.getPictureType());
547. PackagePart pic = ppt.getAllPictures().get(pictureIdx).getPackagePart();
{% endhighlight %}

***

### [OpenActivity.java](https://searchcode.com/codesearch/view/97405824/)
{% highlight java %}
79. private XMLSlideShow ppt;
187.         pgsize = ppt.getPageSize();
190.         slide = ppt.getSlides();
{% endhighlight %}

***

### [MarketingReportGeneratorImpl.java](https://searchcode.com/codesearch/view/92131913/)
{% highlight java %}
40. XMLSlideShow ppt = new XMLSlideShow(getClass().getResourceAsStream("/marketing_template.pptx"));
63. Arrays.stream(ppt.getSlides()).forEach(reportSettings::fillBackgroundAndIncrementIndex);
65. ppt.write(outputStream);
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
139. private XMLSlideShow generateDetailShop(XMLSlideShow ppt, MarketingSlideInfo slide, Function<String, byte[]> pictureGetter, MarketingReportData mData) {
144.     XSLFSlide pptSlide = ppt.createSlide();
172.         int idx = ppt.addPicture(picBytes1, XSLFPictureData.PICTURE_TYPE_PNG);
{% endhighlight %}

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

### [PowerPointOOXMLDocument.java](https://searchcode.com/codesearch/view/126168430/)
{% highlight java %}
104. private void extractContent(final StringBuilder buffy, final XMLSlideShow xmlSlideShow) throws IOException, XmlException {
105.   final XSLFSlide[] slides = xmlSlideShow.getSlides();
110.     final CTNotesSlide notes = xmlSlideShow._getXSLFSlideShow().getNotes(slideId);
111.     final CTCommentList comments = xmlSlideShow._getXSLFSlideShow().getSlideComments(slideId);
{% endhighlight %}

***

