# LabelSSTRecord

***

### [Cluster 1](./1)
{% highlight java %}
395. LabelSSTRecord lrec = null;
405. lrec.setColumn(col);
406. lrec.setRow(row);
407. lrec.setXFIndex(styleIndex);
424.         lrec.setSSTIndex(sst);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
866. LabelSSTRecord rec = new LabelSSTRecord();
868. rec.setRow(row);
869. rec.setColumn(col);
870. rec.setSSTIndex(index);
871. rec.setXFIndex(( short ) 0x0f);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
307. LabelSSTRecord rec = new LabelSSTRecord();
308. rec.field_1_row = field_1_row;
309. rec.field_2_column = field_2_column;
310. rec.field_3_xf_index = field_3_xf_index;
311. rec.field_4_sst_index = field_4_sst_index;
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
62. private LabelSSTRecord record;
79.   this.string = book.getSSTString(record.getSSTIndex());
104.     record.setSSTIndex(index);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
76. HSSFRichTextString(Workbook book, LabelSSTRecord record) {
79.   this.string = book.getSSTString(record.getSSTIndex());
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
373. LabelSSTRecord sst = (LabelSSTRecord) record;
374. UnicodeString unicode = sstRecord.getString(sst.getSSTIndex());
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
211. LabelSSTRecord lsrec = (LabelSSTRecord) record;
213. curRow = thisRow = lsrec.getRow();
214. thisColumn = lsrec.getColumn();
219.   .getString(lsrec.getSSTIndex()).toString().trim();
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
325. LabelSSTRecord newrec   = new LabelSSTRecord();
329. newrec.setRow(oldrec.getRow());
330. newrec.setColumn(oldrec.getColumn());
331. newrec.setXFIndex(oldrec.getXFIndex());
332. newrec.setSSTIndex(stringid);
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
153. final LabelSSTRecord lrec = (LabelSSTRecord) record;
154. if (lrec.getRow() != 0)
156.     if (lrec.getColumn() == 0)
162.         final String cellValue = sstrec.getString(lrec.getSSTIndex()).getString();
163.         if (lrec.getColumn() == 1)
181.         else if (lrec.getColumn() == 2)
196.         else if (lrec.getColumn() == 4)
209.         else if (lrec.getColumn() == 6)
216.         else if (lrec.getColumn() == 7)
222.         else if (lrec.getColumn() == 8)
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
237. private void processHeader(LabelSSTRecord lrec)
239.     if (lrec.getColumn() > 3)
241.         String colHeader = sstrec.getString(lrec.getSSTIndex()).getString();
{% endhighlight %}

***

### [Cluster 11](./11)
{% highlight java %}
243. private void evaluateRowType(final LabelSSTRecord lrec)
246.         sstrec.getString(lrec.getSSTIndex()).getString();
{% endhighlight %}

***

