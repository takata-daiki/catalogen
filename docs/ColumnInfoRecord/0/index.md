# ColumnInfoRecord @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
1966. ColumnInfoRecord columnInfoRecord = columns.getColInfo(k);
1967. maxLevel = Math.max(columnInfoRecord.getOutlineLevel(), maxLevel);
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
102. ColumnInfoRecord ci = ( ColumnInfoRecord ) records.get(k);
103. ci=(ColumnInfoRecord) ci.clone();
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
452. ColumnInfoRecord nci = ( ColumnInfoRecord ) createColInfo();
454. nci.setFirstColumn(column);
455. nci.setLastColumn(column);
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
359. ColumnInfoRecord retval = new ColumnInfoRecord();
361. retval.setColumnWidth(( short ) 2275);
363. retval.setOptions(( short ) 2);
364. retval.setXFIndex(( short ) 0x0f);
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
415. ColumnInfoRecord nci = ( ColumnInfoRecord ) createColInfo();
417. nci.setFirstColumn(column);
418. nci.setLastColumn(column);
419. nci.setOptions(ci.getOptions());
420. nci.setXFIndex(ci.getXFIndex());
{% endhighlight %}

***

### [ColumnInfoRecordsAggregate.java](https://searchcode.com/codesearch/view/15642595/)
{% highlight java %}
464. private void setColumnInfoFields( ColumnInfoRecord ci, Short xfStyle, Short width, Integer level, Boolean hidden, Boolean collapsed )
467.   ci.setXFIndex(xfStyle.shortValue());
469.         ci.setColumnWidth(width.shortValue());
471.         ci.setOutlineLevel( level.shortValue() );
473.         ci.setHidden( hidden.booleanValue() );
475.         ci.setCollapsed( collapsed.booleanValue() );
{% endhighlight %}

***

