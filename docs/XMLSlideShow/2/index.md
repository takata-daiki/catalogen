# XMLSlideShow @Cluster 2 (ppt, void, xmlslideshow)

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
> test that we get the same value as excel and , for 
{% highlight java %}
42. private XMLSlideShow generatePreview(XMLSlideShow ppt, List<PreviewReportingDocument> previewData, String cityName,
45.     XSLFSlide slide = ppt.createSlide();
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
> text and the paragraph the < code > null < / code > if there is no @ param this cell to the is being from 
{% highlight java %}
1107. XMLSlideShow ppt = getParentShape().getSheet().getSlideShow();
1108. CTTextParagraphProperties themeProps = ppt.getDefaultParagraphStyle(getLevel());
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
> text from the shape or footer styles 
{% highlight java %}
545. XMLSlideShow ppt = getSlideShow();
546. int pictureIdx = ppt.addPicture(data.getData(), data.getPictureType());
547. PackagePart pic = ppt.getAllPictures().get(pictureIdx).getPackagePart();
{% endhighlight %}

***

### [OpenActivity.java](https://searchcode.com/codesearch/view/97405824/)
> test that we get the same value as excel and , for 
{% highlight java %}
79. private XMLSlideShow ppt;
187.         pgsize = ppt.getPageSize();
190.         slide = ppt.getSlides();
{% endhighlight %}

***

### [StreamingPPTXImpl.java](https://searchcode.com/codesearch/view/76071743/)
> write the block ' s data to an outputstream @ of stream 
{% highlight java %}
54. XMLSlideShow ppt = new XMLSlideShow();
56. XSLFSlide slide = ppt.createSlide();
95. int pictureIndex = ppt.addPicture(data,
102. ppt.write(outs);
{% endhighlight %}

***

### [PowerPointOOXMLDocument.java](https://searchcode.com/codesearch/view/126168430/)
> sets the 
{% highlight java %}
104. private void extractContent(final StringBuilder buffy, final XMLSlideShow xmlSlideShow) throws IOException, XmlException {
105.   final XSLFSlide[] slides = xmlSlideShow.getSlides();
110.     final CTNotesSlide notes = xmlSlideShow._getXSLFSlideShow().getNotes(slideId);
111.     final CTCommentList comments = xmlSlideShow._getXSLFSlideShow().getSlideComments(slideId);
{% endhighlight %}

***

