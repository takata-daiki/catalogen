# ColumnInfoRecord

***

### [Cluster 1](./1)
{% highlight java %}
415. ColumnInfoRecord nci = ( ColumnInfoRecord ) createColInfo();
417. nci.setFirstColumn(column);
418. nci.setLastColumn(column);
419. nci.setOptions(ci.getOptions());
420. nci.setXFIndex(ci.getXFIndex());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
1966. ColumnInfoRecord columnInfoRecord = columns.getColInfo(k);
1967. maxLevel = Math.max(columnInfoRecord.getOutlineLevel(), maxLevel);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
102. ColumnInfoRecord ci = ( ColumnInfoRecord ) records.get(k);
103. ci=(ColumnInfoRecord) ci.clone();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
464. private void setColumnInfoFields( ColumnInfoRecord ci, Short xfStyle, Short width, Integer level, Boolean hidden, Boolean collapsed )
467.   ci.setXFIndex(xfStyle.shortValue());
469.         ci.setColumnWidth(width.shortValue());
471.         ci.setOutlineLevel( level.shortValue() );
473.         ci.setHidden( hidden.booleanValue() );
475.         ci.setCollapsed( collapsed.booleanValue() );
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
359. ColumnInfoRecord retval = new ColumnInfoRecord();
361. retval.setColumnWidth(( short ) 2275);
363. retval.setOptions(( short ) 2);
364. retval.setXFIndex(( short ) 0x0f);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
1817. ColumnInfoRecord ci     = null;
1825.         if ((ci.getFirstColumn() <= column)
1826.                 && (column <= ci.getLastColumn()))
1835.     retval = ci.getColumnWidth();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
371.   ColumnInfoRecord ci = null;
377.       if ((ci.getFirstColumn() <= column)
378.               && (column <= ci.getLastColumn()))
387. boolean styleChanged = xfIndex != null && ci.getXFIndex() != xfIndex.shortValue();
388.       boolean widthChanged = width != null && ci.getColumnWidth() != width.shortValue();
389.       boolean levelChanged = level != null && ci.getOutlineLevel() != level.intValue();
390.       boolean hiddenChanged = hidden != null && ci.getHidden() != hidden.booleanValue();
391.       boolean collapsedChanged = collapsed != null && ci.getCollapsed() != collapsed.booleanValue();
397.       else if ((ci.getFirstColumn() == column)
398.                && (ci.getLastColumn() == column))
403.                || (ci.getLastColumn() == column))
407.           if (ci.getFirstColumn() == column)
409.               ci.setFirstColumn(( short ) (column + 1));
413.               ci.setLastColumn(( short ) (column - 1));
419.           nci.setOptions(ci.getOptions());
420.           nci.setXFIndex(ci.getXFIndex());
428.           short lastcolumn = ci.getLastColumn();
429.           ci.setLastColumn(( short ) (column - 1));
444.           nci.setColumnWidth(ci.getColumnWidth());
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
329. ColumnInfoRecord rec = new ColumnInfoRecord();
330. rec.field_1_first_col = field_1_first_col;
331. rec.field_2_last_col = field_2_last_col;
332. rec.field_3_col_width = field_3_col_width;
333. rec.field_4_xf_index = field_4_xf_index;
334. rec.field_5_options = field_5_options;
335. rec.field_6_reserved = field_6_reserved;
{% endhighlight %}

***

