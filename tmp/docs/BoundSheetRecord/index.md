# BoundSheetRecord

***

### [Cluster 1](./1)
{% highlight java %}
117. final BoundSheetRecord bsr = (BoundSheetRecord) record;
120.     logger.debug("processing sheet: "+ bsr.getSheetname());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
504. BoundSheetRecord sheet = (BoundSheetRecord)boundsheets.get( sheetnum );
505. sheet.setSheetname(sheetname);
506. sheet.setSheetnameLength( (byte)sheetname.length() );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
354. BoundSheetRecord boundSheetRecord = (BoundSheetRecord) record;
355. sheetNames.add(boundSheetRecord.getSheetname());
{% endhighlight %}

***

### [Cluster 4](./4)
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

### [Cluster 5](./5)
{% highlight java %}
520. BoundSheetRecord boundSheetRecord = (BoundSheetRecord) boundsheets.get( i );
521. if (excludeSheetIdx != i && name.equals(boundSheetRecord.getSheetname()))
{% endhighlight %}

***

