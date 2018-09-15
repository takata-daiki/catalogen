# EscherBSERecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
1309. EscherBSERecord r = new EscherBSERecord();
1310. r.setRecordId( EscherBSERecord.RECORD_ID );
1311. r.setOptions( (short) ( 0x0002 | ( format << 4 ) ) );
1312. r.setBlipTypeMacOS( (byte) format );
1313. r.setBlipTypeWin32( (byte) format );
1314. r.setUid( uid );
1315. r.setTag( (short) 0xFF );
1316. r.setSize( pictureData.length + 25 );
1317. r.setRef( 1 );
1318. r.setOffset( 0 );
1319. r.setBlipRecord( blipRecord );
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
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

