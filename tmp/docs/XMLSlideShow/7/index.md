# XMLSlideShow @Cluster 7

***

### [PowerPointOOXMLDocument.java](https://searchcode.com/codesearch/view/126168430/)
{% highlight java %}
104. private void extractContent(final StringBuilder buffy, final XMLSlideShow xmlSlideShow) throws IOException, XmlException {
105.   final XSLFSlide[] slides = xmlSlideShow.getSlides();
110.     final CTNotesSlide notes = xmlSlideShow._getXSLFSlideShow().getNotes(slideId);
111.     final CTCommentList comments = xmlSlideShow._getXSLFSlideShow().getSlideComments(slideId);
{% endhighlight %}

***

