# EscherBSERecord

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
257. EscherBSERecord bse = getEscherBSERecord();
258. bse.setRef(bse.getRef() + 1);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
127. EscherBSERecord bse = (EscherBSERecord)patriarch.sheet.book.getBSERecord(pictureIndex);
128. byte[] data = bse.getBlipRecord().getPicturedata();
129. int type = bse.getBlipTypeWin32();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
182. EscherBSERecord bse = getEscherBSERecord();
187.         if (pict[i].getOffset() ==  bse.getOffset()){
191.     logger.log(POILogger.ERROR, "no picture found for our BSE offset " + bse.getOffset());
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
565. EscherBSERecord bseRecord = (EscherBSERecord) escherRecord;
566. switch ( bseRecord.getBlipTypeWin32() )
{% endhighlight %}

***

