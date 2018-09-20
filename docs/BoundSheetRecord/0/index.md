# BoundSheetRecord @Cluster 1

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
354. BoundSheetRecord boundSheetRecord = (BoundSheetRecord) record;
355. sheetNames.add(boundSheetRecord.getSheetname());
{% endhighlight %}

***

### [ExcelKeywordParser.java](https://searchcode.com/codesearch/view/12440040/)
{% highlight java %}
117. final BoundSheetRecord bsr = (BoundSheetRecord) record;
120.     logger.debug("processing sheet: "+ bsr.getSheetname());
{% endhighlight %}

***

### [ExcelLanguageCentricParser.java](https://searchcode.com/codesearch/view/12440043/)
{% highlight java %}
115. final BoundSheetRecord bsr = (BoundSheetRecord) record;
118.     logger.debug("processing sheet: "+ bsr.getSheetname());
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
520. BoundSheetRecord boundSheetRecord = (BoundSheetRecord) boundsheets.get( i );
521. if (excludeSheetIdx != i && name.equals(boundSheetRecord.getSheetname()))
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
504. BoundSheetRecord sheet = (BoundSheetRecord)boundsheets.get( sheetnum );
505. sheet.setSheetname(sheetname);
506. sheet.setSheetnameLength( (byte)sheetname.length() );
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
537.     BoundSheetRecord sheet = (BoundSheetRecord)boundsheets.get( sheetnum );
538.     sheet.setSheetname(sheetname);
539.     sheet.setSheetnameLength( (byte)sheetname.length() );
540. sheet.setCompressedUnicodeFlag( (byte)encoding );
{% endhighlight %}

***

