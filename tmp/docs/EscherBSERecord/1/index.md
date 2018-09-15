# EscherBSERecord @Cluster 1

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
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

