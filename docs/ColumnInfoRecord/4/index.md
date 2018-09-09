# ColumnInfoRecord @Cluster 4

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

