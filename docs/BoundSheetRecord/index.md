# BoundSheetRecord

***

## [Cluster 1 (byte, record, sheetname)](./1)
5 results
> ' s formula index to sheet name record 
{% highlight java %}
115. final BoundSheetRecord bsr = (BoundSheetRecord) record;
118.     logger.debug("processing sheet: "+ bsr.getSheetname());
{% endhighlight %}

***

## [Cluster 2 (case, retval, setsheetname)](./2)
1 results
> set the cells type ( numeric , formula or string ) 
{% highlight java %}
1720. BoundSheetRecord retval = new BoundSheetRecord();
1725.         retval.setPositionOfBof(0x0);   // should be set later
1726.         retval.setOptionFlags(( short ) 0);
1727.         retval.setSheetnameLength(( byte ) 0x6);
1728.         retval.setCompressedUnicodeFlag(( byte ) 0);
1729.         retval.setSheetname("Sheet1");
1737.         retval.setSheetname("Sheet2");
1745.         retval.setSheetname("Sheet3");
{% endhighlight %}

***

