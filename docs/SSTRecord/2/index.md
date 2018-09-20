# SSTRecord @Cluster 2

***

### [Excel2003ImportListener.java](https://searchcode.com/codesearch/view/92669296/)
{% highlight java %}
24. private SSTRecord sstrec;
90.                     String value = sstrec.getString(lrec.getSSTIndex()).getString();
{% endhighlight %}

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
178. private SSTRecord sstRecord;
374.         UnicodeString unicode = sstRecord.getString(sst.getSSTIndex());
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
{% highlight java %}
51. private SSTRecord sstRecord;
218.       value =  sstRecord
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
780. SSTRecord sst = null;
798.             record = sst.createExtSSTRecord(sstPos + offset);
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
827. SSTRecord sst = null;
837.             retval += sst.calcExtSSTRecordSize();
{% endhighlight %}

***

### [ExcelKeywordParser.java](https://searchcode.com/codesearch/view/12440040/)
{% highlight java %}
54. private SSTRecord sstrec;
147.                         sstrec.getNumUniqueStrings() + " unique strings");
162.                         final String cellValue = sstrec.getString(lrec.getSSTIndex()).getString();
246.         sstrec.getString(lrec.getSSTIndex()).getString();
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
103. protected SSTRecord        sst         = null;
703.   return sst.addString(string);
715.     UnicodeString retval = sst.getString(str);
798.                 record = sst.createExtSSTRecord(sstPos + offset);
837.                 retval += sst.calcExtSSTRecordSize();
{% endhighlight %}

***

### [ExcelLanguageCentricParser.java](https://searchcode.com/codesearch/view/12440043/)
{% highlight java %}
55. private SSTRecord sstrec;
147.                         sstrec.getNumUniqueStrings() + " unique strings");
162.                             sstrec.getString(lrec.getSSTIndex()).getString();
167.                                 sstrec.getString(lrec.getSSTIndex()).getString());
172.                         String name = sstrec.getString(lrec.getSSTIndex()).getString();
187.                         String value = sstrec.getString(lrec.getSSTIndex()).getString();
241.         String colHeader = sstrec.getString(lrec.getSSTIndex()).getString();
{% endhighlight %}

***

