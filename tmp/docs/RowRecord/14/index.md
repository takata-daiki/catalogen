# RowRecord @Cluster 14

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

