# ColumnInfoRecord @Cluster 1 (if, nci, null)

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> set the contents of this shape to be a copy of the source shape . < p > the 0 is specified in points . positive values will cause the to and font to the specified in the index of the font . 
{% highlight java %}
102. ColumnInfoRecord ci = ( ColumnInfoRecord ) records.get(k);
103. ci=(ColumnInfoRecord) ci.clone();
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> sets the type of ole object . 
{% highlight java %}
359. ColumnInfoRecord retval = new ColumnInfoRecord();
361. retval.setColumnWidth(( short ) 2275);
363. retval.setOptions(( short ) 2);
364. retval.setXFIndex(( short ) 0x0f);
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
> sets all non null fields into the < code > that < / code > parameter . 
{% highlight java %}
415. ColumnInfoRecord nci = ( ColumnInfoRecord ) createColInfo();
417. nci.setFirstColumn(column);
418. nci.setLastColumn(column);
419. nci.setOptions(ci.getOptions());
420. nci.setXFIndex(ci.getXFIndex());
{% endhighlight %}

***

