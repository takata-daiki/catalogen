# MergeCellsRecord

***

### [Cluster 1](./1)
{% highlight java %}
104. protected MergeCellsRecord           merged            =     null;
202.             retval.numMergedRegions += retval.merged.getNumAreas();
522.     if (merged == null || merged.getNumAreas() == 1027)
528.     merged.addArea(rowFrom, colFrom, rowTo, colTo);
542.     if (numMergedRegions - index < merged.getNumAreas())
545.         startNumRegions = numMergedRegions - merged.getNumAreas(); 
594.         startNumRegions = numMergedRegions - merged.getNumAreas();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
292. MergeCellsRecord rec = new MergeCellsRecord();        
293. rec.field_2_regions = new ArrayList();
297.    rec.addArea(oldRegion.row_from, oldRegion.col_from, oldRegion.row_to, oldRegion.col_to);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
2135. MergeCellsRecord retval = new MergeCellsRecord();
2136. retval.setNumAreas(( short ) 0);
{% endhighlight %}

***

