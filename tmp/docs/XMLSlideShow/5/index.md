# XMLSlideShow @Cluster 5

***

### [MarketingReportFirstSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131912/)
{% highlight java %}
73. private void drawImage(XMLSlideShow ppt, XSLFSlide slide) throws IOException {
75.     int idx = ppt.addPicture(bytes, XSLFPictureData.PICTURE_TYPE_PNG);
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
139. private XMLSlideShow generateDetailShop(XMLSlideShow ppt, MarketingSlideInfo slide, Function<String, byte[]> pictureGetter, MarketingReportData mData) {
144.     XSLFSlide pptSlide = ppt.createSlide();
172.         int idx = ppt.addPicture(picBytes1, XSLFPictureData.PICTURE_TYPE_PNG);
{% endhighlight %}

***

