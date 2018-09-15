# LabelSSTRecord @Cluster 1

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
373. LabelSSTRecord sst = (LabelSSTRecord) record;
374. UnicodeString unicode = sstRecord.getString(sst.getSSTIndex());
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
{% highlight java %}
211. LabelSSTRecord lsrec = (LabelSSTRecord) record;
213. curRow = thisRow = lsrec.getRow();
214. thisColumn = lsrec.getColumn();
219.   .getString(lsrec.getSSTIndex()).toString().trim();
{% endhighlight %}

***

### [ExcelKeywordParser.java](https://searchcode.com/codesearch/view/12440040/)
{% highlight java %}
243. private void evaluateRowType(final LabelSSTRecord lrec)
246.         sstrec.getString(lrec.getSSTIndex()).getString();
{% endhighlight %}

***

### [ExcelLanguageCentricParser.java](https://searchcode.com/codesearch/view/12440043/)
{% highlight java %}
237. private void processHeader(LabelSSTRecord lrec)
239.     if (lrec.getColumn() > 3)
241.         String colHeader = sstrec.getString(lrec.getSSTIndex()).getString();
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
325. LabelSSTRecord newrec   = new LabelSSTRecord();
329. newrec.setRow(oldrec.getRow());
330. newrec.setColumn(oldrec.getColumn());
331. newrec.setXFIndex(oldrec.getXFIndex());
332. newrec.setSSTIndex(stringid);
{% endhighlight %}

***

### [HSSFRichTextString.java](https://searchcode.com/codesearch/view/15642304/)
{% highlight java %}
62. private LabelSSTRecord record;
79.   this.string = book.getSSTString(record.getSSTIndex());
104.     record.setSSTIndex(index);
{% endhighlight %}

***

### [HSSFRichTextString.java](https://searchcode.com/codesearch/view/15642304/)
{% highlight java %}
76. HSSFRichTextString(Workbook book, LabelSSTRecord record) {
79.   this.string = book.getSSTString(record.getSSTIndex());
{% endhighlight %}

***

### [LabelSSTRecord.java](https://searchcode.com/codesearch/view/15642468/)
{% highlight java %}
307. LabelSSTRecord rec = new LabelSSTRecord();
308. rec.field_1_row = field_1_row;
309. rec.field_2_column = field_2_column;
310. rec.field_3_xf_index = field_3_xf_index;
311. rec.field_4_sst_index = field_4_sst_index;
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
866. LabelSSTRecord rec = new LabelSSTRecord();
868. rec.setRow(row);
869. rec.setColumn(col);
870. rec.setSSTIndex(index);
871. rec.setXFIndex(( short ) 0x0f);
{% endhighlight %}

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
{% highlight java %}
395. LabelSSTRecord lrec = null;
405. lrec.setColumn(col);
406. lrec.setRow(row);
407. lrec.setXFIndex(styleIndex);
424.         lrec.setSSTIndex(sst);
{% endhighlight %}

***

