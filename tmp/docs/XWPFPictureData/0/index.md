# XWPFPictureData @Cluster 1

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
575. XWPFPictureData pictureData = XWPFPictureUtil.getPictureData( document, blipId );
581.         Image img = Image.getInstance( pictureData.getData() );
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
1310. XWPFPictureData pictureData = getPictureData( picture );
1315.         extractor.extract( WORD_MEDIA + pictureData.getFileName(), pictureData.getData() );
1320.                     "Error while extracting the image " + pictureData.getFileName(), e );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
811. XWPFPictureData pictureData = super.getPictureDataByID( blipId );
816.         Image img = Image.getInstance( pictureData.getData() );
{% endhighlight %}

***

### [PdfMapper.java](https://searchcode.com/codesearch/view/96673019/)
{% highlight java %}
1255. XWPFPictureData pictureData = super.getPictureData( picture );
1260.         Image img = Image.getInstance( pictureData.getData() );
{% endhighlight %}

***

