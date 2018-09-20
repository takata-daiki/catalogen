# RowRecord @Cluster 1

***

### [ExcelKeywordParser.java](https://searchcode.com/codesearch/view/12440040/)
{% highlight java %}
127. final RowRecord rowrec = (RowRecord) record;
128. logger.debug("processing row: " + rowrec.getRowNumber());
{% endhighlight %}

***

### [ExcelLanguageCentricParser.java](https://searchcode.com/codesearch/view/12440043/)
{% highlight java %}
123. final RowRecord rowrec = (RowRecord) record;
124. lastColOfRow = rowrec.getLastCol();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
2196. RowRecord row = (RowRecord)itr.next();
2197. if (cells != null && cells.rowHasCells(row.getRowNumber()))
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
3157. RowRecord rowRecord = (RowRecord) iterator.next();
3158. maxLevel = Math.max(rowRecord.getOutlineLevel(), maxLevel);
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
3135. RowRecord row = getRow( rowNum );
3141. int level = row.getOutlineLevel();
3145. row.setOutlineLevel((short) ( level ));
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1122. public void addRow(RowRecord row)
1129.     if (row.getRowNumber() >= d.getLastRow())
1131.         d.setLastRow(row.getRowNumber() + 1);
1133.     if (row.getRowNumber() < d.getFirstRow())
1135.         d.setFirstRow(row.getRowNumber());
1139.      RowRecord existingRow = rows.getRow(row.getRowNumber());
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
159. RowRecord row = null;
165. return row.getRowNumber();
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
370. RowRecord row = createRow( lastRow + 1);
371. row.setColapsed( true );
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
175. RowRecord row = null;
179. return row.getRowNumber();
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
418. RowRecord row = getRow( startIdx );
434.         if ( row.getOutlineLevel() == getRow( i ).getOutlineLevel() )
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
91. public void removeRow(RowRecord row)
93.     size -= row.getRecordSize();
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
106. RowRecord row = new RowRecord();
107. row.setRowNumber(rownum);
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
199. RowRecord row = (RowRecord)rowIterator.next();
200. pos += row.serialize(pos, data);
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
341. public int writeHidden( RowRecord rowRecord, int row, boolean hidden )
343.     int level = rowRecord.getOutlineLevel();
346.         rowRecord.setZeroHeight( hidden );
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
312. RowRecord rowRecord = this.getRow( row );
313. int level = rowRecord.getOutlineLevel();
318.     if (rowRecord.getOutlineLevel() < level)
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
385. RowRecord rowrec = new RowRecord();
388. rowrec.setRowNumber(row);
389. rowrec.setHeight(( short ) 0xff);
390. rowrec.setOptimize(( short ) 0x0);
391. rowrec.setOptionFlags(( short ) 0x100);  // seems necessary for outlining
392. rowrec.setXFIndex(( short ) 0xf);
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
{% highlight java %}
75. public void insertRow(RowRecord row)
77.     size += row.getRecordSize();
81.     if ((row.getRowNumber() < firstrow) || (firstrow == -1))
83.         firstrow = row.getRowNumber();
85.     if ((row.getRowNumber() > lastrow) || (lastrow == -1))
87.         lastrow = row.getRowNumber();
{% endhighlight %}

***

### [HSSFRow.java](https://searchcode.com/codesearch/view/15642313/)
{% highlight java %}
133. protected HSSFRow(Workbook book, Sheet sheet, RowRecord record)
139.     setRowNum(record.getRowNumber());
{% endhighlight %}

***

### [HSSFRow.java](https://searchcode.com/codesearch/view/15642313/)
{% highlight java %}
81. private RowRecord row;
115.     row.setOptionFlags( (short)0x100 );   // seems necessary for outlining to work.  
116.     row.setHeight((short) 0xff);
117.     row.setLastCol((short) -1);
118.     row.setFirstCol((short) -1);
193.     if (cell.getCellNum() == row.getLastCol())
195.         row.setLastCol(findLastCell(row.getLastCol()));
197.     if (cell.getCellNum() == row.getFirstCol())
199.         row.setFirstCol(findFirstCell(row.getFirstCol()));
234.         row.setRowNumber(rowNum);   // used only for KEY comparison (HSSFRow)
256.     if (row.getFirstCol() == -1)
258.         row.setFirstCol(column);
260.     if (row.getLastCol() == -1)
262.         row.setLastCol(column);
275.     if (column < row.getFirstCol())
279.     if (column > row.getLastCol())
309.         return row.getFirstCol();
322.         return row.getLastCol();
352.     row.setBadFontHeight(true);
353.     row.setHeight(height);
361.     row.setZeroHeight(zHeight);
369.     return row.getZeroHeight();
382.     row.setHeight((short) (height * 20));
392.     return row.getHeight();
402.     return (row.getHeight() / 20);
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
{% highlight java %}
484. RowRecord loc = ( RowRecord ) obj;
486. if (this.getRowNumber() == loc.getRowNumber())
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
{% highlight java %}
461. RowRecord loc = ( RowRecord ) obj;
463. if (this.getRowNumber() == loc.getRowNumber())
467. if (this.getRowNumber() < loc.getRowNumber())
471. if (this.getRowNumber() > loc.getRowNumber())
{% endhighlight %}

***

