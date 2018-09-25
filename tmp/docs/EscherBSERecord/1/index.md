# EscherBSERecord @Cluster 1 (0x00, bserecord, type)

***

### [Picture.java](https://searchcode.com/codesearch/view/97384428/)
> @ return the < code > null < / code > if the two range parts can be combined in an { @ link 
{% highlight java %}
565. EscherBSERecord bseRecord = (EscherBSERecord) escherRecord;
566. switch ( bseRecord.getBlipTypeWin32() )
{% endhighlight %}

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
> returns the underlying 
{% highlight java %}
813. EscherBSERecord bse = (EscherBSERecord) iter.next();
814. if (Arrays.equals(bse.getUid(), uid)) {
{% endhighlight %}

***

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
> 
{% highlight java %}
127. EscherBSERecord bse = (EscherBSERecord)patriarch.sheet.book.getBSERecord(pictureIndex);
128. byte[] data = bse.getBlipRecord().getPicturedata();
129. int type = bse.getBlipTypeWin32();
{% endhighlight %}

***

