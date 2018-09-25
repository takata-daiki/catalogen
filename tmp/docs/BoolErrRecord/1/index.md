# BoolErrRecord @Cluster 1 (berec, boolrec, errrec)

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
> sets the record or null , if there isn ' t one 
{% highlight java %}
272. BoolErrRecord boolErrRecord = ( BoolErrRecord ) record;
274. retval = (boolErrRecord.isBoolean())
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
> sets the , and that ' s first with 0 and 1 6 for the size of the cell value . 
{% highlight java %}
162. BoolErrRecord berec = (BoolErrRecord) record;
164. thisRow = berec.getRow();
165. thisColumn = berec.getColumn();
{% endhighlight %}

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param p the font to be used for the to @ since poi 3 . 1 4 - beta 2 
{% highlight java %}
472. BoolErrRecord errRec = null;
482. errRec.setColumn(col);
485.     errRec.setValue(getErrorCellValue());
487. errRec.setXFIndex(styleIndex);
488. errRec.setRow(row);
{% endhighlight %}

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param p the font to be used for the to @ since poi 3 . 1 4 - beta 2 
{% highlight java %}
451. BoolErrRecord boolRec = null;
461. boolRec.setColumn(col);
464.     boolRec.setValue(getBooleanCellValue());
466. boolRec.setXFIndex(styleIndex);
467. boolRec.setRow(row);
{% endhighlight %}

***

