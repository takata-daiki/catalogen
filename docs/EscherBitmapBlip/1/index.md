# EscherBitmapBlip @Cluster 1 (bliprecord, case, hssfpicturedata)

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
> sets the line compound style @ see org . apache . poi . openxml 4 j . opc . packageproperties # workbook ( ) 
{% highlight java %}
1281. EscherBitmapBlip blipRecord = new EscherBitmapBlip();
1282. blipRecord.setRecordId( (short) ( EscherBitmapBlip.RECORD_ID_START + format ) );
1286.         blipRecord.setOptions(HSSFPictureData.MSOBI_EMF);
1289.         blipRecord.setOptions(HSSFPictureData.MSOBI_WMF);
1292.         blipRecord.setOptions(HSSFPictureData.MSOBI_PICT);
1295.         blipRecord.setOptions(HSSFPictureData.MSOBI_PNG);
1298.         blipRecord.setOptions(HSSFPictureData.MSOBI_JPEG);
1301.         blipRecord.setOptions(HSSFPictureData.MSOBI_DIB);
1305. blipRecord.setUID( uid );
1306. blipRecord.setMarker( (byte) 0xFF );
1307. blipRecord.setPictureData( pictureData );
{% endhighlight %}

***

