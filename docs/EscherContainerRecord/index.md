# EscherContainerRecord

***

## [Cluster 1](./1)
8 results
> create a new ctworkbook with all values set to default 
{% highlight java %}
214. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
217. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

## [Cluster 2](./2)
17 results
> create and create a new sheet from the sheet and save the @ param was the < code > true < / code > if the line is inside a group , < code > false < / code > otherwise 
{% highlight java %}
631. EscherContainerRecord spgrContainer = new EscherContainerRecord();
639. spgrContainer.setRecordId( EscherContainerRecord.SPGR_CONTAINER );
640. spgrContainer.setOptions( (short) 0x000F );
674. spgrContainer.addChildRecord( spContainer );
{% endhighlight %}

***

## [Cluster 3](./3)
12 results
> create a new ctworkbook with all values set to default 
{% highlight java %}
802. EscherContainerRecord dggContainer = _documentRecord.getPPDrawingGroup().getDggContainer();
809.   dggContainer.addChildBefore(bstore, EscherOptRecord.RECORD_ID);
{% endhighlight %}

***

