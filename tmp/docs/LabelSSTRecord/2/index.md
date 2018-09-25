# LabelSSTRecord @Cluster 2 (newrec, rec, record)

***

### [HSSFRichTextString.java](https://searchcode.com/codesearch/view/15642304/)
> this must be called to setup the internal work book references whenever a 
{% highlight java %}
76. HSSFRichTextString(Workbook book, LabelSSTRecord record) {
79.   this.string = book.getSSTString(record.getSSTIndex());
{% endhighlight %}

***

### [HSSFRichTextString.java](https://searchcode.com/codesearch/view/15642304/)
> test that we get the same value as excel and , for 
{% highlight java %}
62. private LabelSSTRecord record;
79.   this.string = book.getSSTString(record.getSSTIndex());
104.     record.setSSTIndex(index);
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
> used internally to 
{% highlight java %}
211. LabelSSTRecord lsrec = (LabelSSTRecord) record;
213. curRow = thisRow = lsrec.getRow();
214. thisColumn = lsrec.getColumn();
219.   .getString(lsrec.getSSTIndex()).toString().trim();
{% endhighlight %}

***

### [LabelSSTRecord.java](https://searchcode.com/codesearch/view/15642468/)
> this used to this sheet . 
{% highlight java %}
307. LabelSSTRecord rec = new LabelSSTRecord();
308. rec.field_1_row = field_1_row;
309. rec.field_2_column = field_2_column;
310. rec.field_3_xf_index = field_3_xf_index;
311. rec.field_4_sst_index = field_4_sst_index;
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
> this must be called to b the return value that escher a file is removed 
{% highlight java %}
325. LabelSSTRecord newrec   = new LabelSSTRecord();
329. newrec.setRow(oldrec.getRow());
330. newrec.setColumn(oldrec.getColumn());
331. newrec.setXFIndex(oldrec.getXFIndex());
332. newrec.setSSTIndex(stringid);
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> this must be called to b the return value that must be set to the paragraph field . 
{% highlight java %}
866. LabelSSTRecord rec = new LabelSSTRecord();
868. rec.setRow(row);
869. rec.setColumn(col);
870. rec.setSSTIndex(index);
871. rec.setXFIndex(( short ) 0x0f);
{% endhighlight %}

***

