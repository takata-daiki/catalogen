# HSSFPatriarch @Cluster 2

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
112. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
114. HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
265. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
267. HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
328.         patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
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

### [Export.java](https://searchcode.com/codesearch/view/558689/)
{% highlight java %}
252. HSSFPatriarch patr = ((HSSFSheet) sheet).createDrawingPatriarch();
266.         Comment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 8, 10));
{% endhighlight %}

***

### [SmjXlsReport.java](https://searchcode.com/codesearch/view/133035254/)
{% highlight java %}
76. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
81. patriarch.createPicture(anchor, pictureIndex);
{% endhighlight %}

***

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
{% highlight java %}
72. HSSFPatriarch patriarch;
127.     EscherBSERecord bse = (EscherBSERecord)patriarch.sheet.book.getBSERecord(pictureIndex);
{% endhighlight %}

***

