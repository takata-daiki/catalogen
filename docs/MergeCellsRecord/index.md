# MergeCellsRecord

***

## [Cluster 1 (merged, nummergedregions, record)](./1)
4 results
> checks that the given stream starts with a zip structure . 
{% highlight java %}
551. MergeCellsRecord record = (MergeCellsRecord) mergedRecords.get(n);
552. if (startNumRegions + record.getNumAreas() > index)
557. startNumRegions += record.getNumAreas(); 
{% endhighlight %}

***

## [Cluster 2 (mergecellsrecord, oldregion, rec)](./2)
1 results
> adds the given sheet to the table record and see the . @ see # 5 ( int ) 
{% highlight java %}
292. MergeCellsRecord rec = new MergeCellsRecord();        
293. rec.field_2_regions = new ArrayList();
297.    rec.addArea(oldRegion.row_from, oldRegion.col_from, oldRegion.row_to, oldRegion.col_to);
{% endhighlight %}

***

