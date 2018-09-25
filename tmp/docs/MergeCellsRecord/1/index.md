# MergeCellsRecord @Cluster 1 (merged, nummergedregions, record)

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> checks that the given stream starts with a zip structure . 
{% highlight java %}
551. MergeCellsRecord record = (MergeCellsRecord) mergedRecords.get(n);
552. if (startNumRegions + record.getNumAreas() > index)
557. startNumRegions += record.getNumAreas(); 
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> removes all of the elements from this container ( optional operation ) . the container will be empty after this call returns . 
{% highlight java %}
561. MergeCellsRecord rec = (MergeCellsRecord) mergedRecords.get(pos);
562. rec.removeAreaAt(index - startNumRegions);
564. if (rec.getNumAreas() == 0)
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> checks that the given stream starts with a zip structure . 
{% highlight java %}
600. MergeCellsRecord record = (MergeCellsRecord) mergedRecords.get(n);
601. if (startNumRegions + record.getNumAreas() > index)
606. startNumRegions += record.getNumAreas(); 
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> test that we get the same value as excel and , for 
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

