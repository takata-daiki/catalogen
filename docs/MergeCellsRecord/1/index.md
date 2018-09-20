# MergeCellsRecord @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
2135. MergeCellsRecord retval = new MergeCellsRecord();
2136. retval.setNumAreas(( short ) 0);
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
551. MergeCellsRecord record = (MergeCellsRecord) mergedRecords.get(n);
552. if (startNumRegions + record.getNumAreas() > index)
557. startNumRegions += record.getNumAreas(); 
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
561. MergeCellsRecord rec = (MergeCellsRecord) mergedRecords.get(pos);
562. rec.removeAreaAt(index - startNumRegions);
564. if (rec.getNumAreas() == 0)
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
600. MergeCellsRecord record = (MergeCellsRecord) mergedRecords.get(n);
601. if (startNumRegions + record.getNumAreas() > index)
606. startNumRegions += record.getNumAreas(); 
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
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

