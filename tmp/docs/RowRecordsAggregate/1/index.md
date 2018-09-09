# RowRecordsAggregate @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
111. protected RowRecordsAggregate        rows              =     null;
254.             retval.rows.insertRow(row);
634.             rows.getPhysicalNumberOfRows(),
636.             + rows.getPhysicalNumberOfRows() - 2
640.            + rows.getPhysicalNumberOfRows() - 2;
801.   index.setFirstRow(rows.getFirstRowNum());
802.   index.setLastRowAdd1(rows.getLastRowNum()+1);
814.   int blockCount = rows.getRowBlockCount();
822.     rowBlockOffset += rows.getRowBlockSize(block);
823.     cellBlockOffset += null == cells ? 0 : cells.getRowCellBlockSize(rows.getStartRowNumberForBlock(block),
824.                                                  rows.getEndRowNumberForBlock(block));
829.     dbCellOffset += (8 + (rows.getRowCountForBlock(block) * 2));
1139.      RowRecord existingRow = rows.getRow(row.getRowNumber());
1141.        rows.removeRow(existingRow);
1143.     rows.insertRow(row);
1198.     rows.removeRow(row);
1333.         rowRecIterator = rows.getIterator();
1378.     return rows.getRow(rownum);
2187.         final int blocks = rows.getRowBlockCount();
2195.         for (Iterator itr = rows.getIterator(); itr.hasNext();) {
3154.     Iterator iterator = rows.getIterator();
3170.         rows.collapseRow( row );
3174.         rows.expandRow( row );
{% endhighlight %}

***

