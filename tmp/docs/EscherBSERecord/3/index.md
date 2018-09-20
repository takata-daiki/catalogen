# EscherBSERecord @Cluster 3

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
{% highlight java %}
825. EscherBSERecord bse = new EscherBSERecord();
826. bse.setRecordId(EscherBSERecord.RECORD_ID);
827. bse.setOptions((short) (0x0002 | (format << 4)));
828. bse.setSize(pict.getRawData().length + 8);
829. bse.setUid(uid);
831. bse.setBlipTypeMacOS((byte) format);
832. bse.setBlipTypeWin32((byte) format);
835.   bse.setBlipTypeMacOS((byte) Picture.PICT);
839.   bse.setBlipTypeWin32((byte) Picture.WMF);
841. bse.setRef(0);
842. bse.setOffset(offset);
843. bse.setRemainingData(new byte[0]);
{% endhighlight %}

***

