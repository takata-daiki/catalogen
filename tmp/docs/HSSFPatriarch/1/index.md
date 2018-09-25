# HSSFPatriarch @Cluster 1 (pictureindex, sheet, spgr)

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
> sets the 
{% highlight java %}
210. final HSSFPatriarch patr = this.getDrawingPatriarch();
216. final HSSFComment comment = patr.createComment(anchor);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/133035254/)
> creates a new ( excel ) record name record ( or true ) 
{% highlight java %}
76. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
81. patriarch.createPicture(anchor, pictureIndex);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/61719045/)
> creates a new ( excel ) record name record ( or true ) 
{% highlight java %}
76. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
81. patriarch.createPicture(anchor, pictureIndex);
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/66638946/)
> creates a new ( excel ) record name record ( or true ) 
{% highlight java %}
76. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
81. patriarch.createPicture(anchor, pictureIndex);
{% endhighlight %}

***

### [ImageToExcel.java](https://searchcode.com/codesearch/view/94171992/)
> test that we get the same value as excel and , for 
{% highlight java %}
23. private HSSFPatriarch patriarch = null;
51.     patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
{% endhighlight %}

***

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
> creates a 
{% highlight java %}
72. HSSFPatriarch patriarch;
127.     EscherBSERecord bse = (EscherBSERecord)patriarch.sheet.book.getBSERecord(pictureIndex);
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> sets the a 4 - byte is used by the chart @ param for the return value of the 1 , - 1 if it is currently a valid record number 
{% highlight java %}
713. private void convertPatriarch( HSSFPatriarch patriarch )
735.     spgr.setRectX1( patriarch.getX1() );
736.     spgr.setRectY1( patriarch.getY1() );
737.     spgr.setRectX2( patriarch.getX2() );
738.     spgr.setRectY2( patriarch.getY2() );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> test that we get the same value as excel and , for 
{% highlight java %}
280. protected HSSFPatriarch patriarch;
576.         if ( patriarch.getChildren().size() != 0 )
735.     spgr.setRectX1( patriarch.getX1() );
736.     spgr.setRectY1( patriarch.getY1() );
737.     spgr.setRectX2( patriarch.getX2() );
738.     spgr.setRectY2( patriarch.getY2() );
{% endhighlight %}

***

