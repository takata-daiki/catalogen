# HSSFPatriarch @Cluster 1

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
210. final HSSFPatriarch patr = this.getDrawingPatriarch();
216. final HSSFComment comment = patr.createComment(anchor);
{% endhighlight %}

***

### [ImageToExcel.java](https://searchcode.com/codesearch/view/94171992/)
{% highlight java %}
23. private HSSFPatriarch patriarch = null;
51.     patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/61719045/)
{% highlight java %}
76. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
81. patriarch.createPicture(anchor, pictureIndex);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/66638946/)
{% highlight java %}
76. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
81. patriarch.createPicture(anchor, pictureIndex);
{% endhighlight %}

***

### [Cell.java](https://searchcode.com/codesearch/view/3760572/)
{% highlight java %}
305. HSSFPatriarch hssfp = m_sheet.getHSSFPatriarch();
306. comment = hssfp.createComment(new HSSFClientAnchor(0,0,0,0,
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/133035254/)
{% highlight java %}
76. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
81. patriarch.createPicture(anchor, pictureIndex);
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
713. private void convertPatriarch( HSSFPatriarch patriarch )
735.     spgr.setRectX1( patriarch.getX1() );
736.     spgr.setRectY1( patriarch.getY1() );
737.     spgr.setRectX2( patriarch.getX2() );
738.     spgr.setRectY2( patriarch.getY2() );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
280. protected HSSFPatriarch patriarch;
576.         if ( patriarch.getChildren().size() != 0 )
735.     spgr.setRectX1( patriarch.getX1() );
736.     spgr.setRectY1( patriarch.getY1() );
737.     spgr.setRectX2( patriarch.getX2() );
738.     spgr.setRectY2( patriarch.getY2() );
{% endhighlight %}

***

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
{% highlight java %}
72. HSSFPatriarch patriarch;
127.     EscherBSERecord bse = (EscherBSERecord)patriarch.sheet.book.getBSERecord(pictureIndex);
{% endhighlight %}

***

