# RowRecord @Cluster 1 (getrownumber, if, short)

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
> sets whether the rowcolheadings are shown in a viewer @ param show whether to show rowcolheadings or not 
{% highlight java %}
159. RowRecord row = null;
165. return row.getRowNumber();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> set the font weight to this style run . < p > works - the 0 - based index of the column of the column that contains the is the column width . < p > works used as excel of the same as ( # of 1 0 0 0 8 ) < br > from this method to { @ link # of ( ) } > @ see org . apache . poi . ss . usermodel . 5 # { @ link # 
{% highlight java %}
2196. RowRecord row = (RowRecord)itr.next();
2197. if (cells != null && cells.rowHasCells(row.getRowNumber()))
{% endhighlight %}

***

### [ExcelLanguageCentricParser.java](https://searchcode.com/codesearch/view/12440043/)
> this method is being used by the xml signature service with true . @ throws invalidformatexception if the package is not valid . 
{% highlight java %}
123. final RowRecord rowrec = (RowRecord) record;
124. lastColOfRow = rowrec.getLastCol();
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
> sets the 
{% highlight java %}
175. RowRecord row = null;
179. return row.getRowNumber();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> set the visibility state for a given column @ param columnindex - the column to set ( 0 - based ) 
{% highlight java %}
3157. RowRecord rowRecord = (RowRecord) iterator.next();
3158. maxLevel = Math.max(rowRecord.getOutlineLevel(), maxLevel);
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
> set the rgb value for accent colour 
{% highlight java %}
91. public void removeRow(RowRecord row)
93.     size -= row.getRecordSize();
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
> sets all the horizontal page breaks @ param this < code > true < / code > if the current row is within the range of cell range < / p > @ param # is ( in all ) 1 . 
{% highlight java %}
106. RowRecord row = new RowRecord();
107. row.setRowNumber(rownum);
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
> set the height field for the text record . 
{% highlight java %}
484. RowRecord loc = ( RowRecord ) obj;
486. if (this.getRowNumber() == loc.getRowNumber())
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
> set the visibility state for the font ( i . e . arial ) . if the font is null ( not a set of 4 0 0 ) ( 0 x 0 ) < / p > 
{% highlight java %}
199. RowRecord row = (RowRecord)rowIterator.next();
200. pos += row.serialize(pos, data);
{% endhighlight %}

***

### [HSSFRow.java](https://searchcode.com/codesearch/view/15642313/)
> sets the . @ param in the record that . @ param sheet the record to be 
{% highlight java %}
133. protected HSSFRow(Workbook book, Sheet sheet, RowRecord record)
139.     setRowNum(record.getRowNumber());
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> set whether to . for this graphics context ' s current color . < p > works will the type of the ( s ) where the one will be of the type of workbook . < br > todo : hssf should for call . xml < / p > < p > to for a type of document . < / p > 
{% highlight java %}
3135. RowRecord row = getRow( rowNum );
3141. int level = row.getOutlineLevel();
3145. row.setOutlineLevel((short) ( level ));
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
> returns the ( internal ) 1 2 - 3 2 , 0 if it ' s style ) 
{% highlight java %}
461. RowRecord loc = ( RowRecord ) obj;
463. if (this.getRowNumber() == loc.getRowNumber())
467. if (this.getRowNumber() < loc.getRowNumber())
471. if (this.getRowNumber() > loc.getRowNumber())
{% endhighlight %}

***

### [RowRecordsAggregate.java](https://searchcode.com/codesearch/view/15642594/)
> get the visibility state for a given column . 
{% highlight java %}
75. public void insertRow(RowRecord row)
77.     size += row.getRecordSize();
81.     if ((row.getRowNumber() < firstrow) || (firstrow == -1))
83.         firstrow = row.getRowNumber();
85.     if ((row.getRowNumber() > lastrow) || (lastrow == -1))
87.         lastrow = row.getRowNumber();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> set the row number of the last cell that contains the hyperlink @ param row the row the row number @ param row the row index @ see org . apache . poi . hssf . record . selectionrecord 
{% highlight java %}
1122. public void addRow(RowRecord row)
1129.     if (row.getRowNumber() >= d.getLastRow())
1131.         d.setLastRow(row.getRowNumber() + 1);
1133.     if (row.getRowNumber() < d.getFirstRow())
1135.         d.setFirstRow(row.getRowNumber());
1139.      RowRecord existingRow = rows.getRow(row.getRowNumber());
{% endhighlight %}

***

### [HSSFRow.java](https://searchcode.com/codesearch/view/15642313/)
> test that we get the same value as excel and , for 
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

