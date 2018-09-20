# EscherBSERecord @Cluster 1

***

### [Picture.java](https://searchcode.com/codesearch/view/97384428/)
{% highlight java %}
565. EscherBSERecord bseRecord = (EscherBSERecord) escherRecord;
566. switch ( bseRecord.getBlipTypeWin32() )
{% endhighlight %}

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
{% highlight java %}
813. EscherBSERecord bse = (EscherBSERecord) iter.next();
814. if (Arrays.equals(bse.getUid(), uid)) {
{% endhighlight %}

***

### [Picture.java](https://searchcode.com/codesearch/view/97394307/)
{% highlight java %}
257. EscherBSERecord bse = getEscherBSERecord();
258. bse.setRef(bse.getRef() + 1);
{% endhighlight %}

***

### [Picture.java](https://searchcode.com/codesearch/view/97394307/)
{% highlight java %}
182. EscherBSERecord bse = getEscherBSERecord();
187.         if (pict[i].getOffset() ==  bse.getOffset()){
191.     logger.log(POILogger.ERROR, "no picture found for our BSE offset " + bse.getOffset());
{% endhighlight %}

***

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
{% highlight java %}
127. EscherBSERecord bse = (EscherBSERecord)patriarch.sheet.book.getBSERecord(pictureIndex);
128. byte[] data = bse.getBlipRecord().getPicturedata();
129. int type = bse.getBlipTypeWin32();
{% endhighlight %}

***

