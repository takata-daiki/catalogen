# EscherContainerRecord @Cluster 1 (bstorecontainer, escherparent, spgr)

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
214. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
217. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
> create a new ctworkbook with all values set to default 
{% highlight java %}
153. EscherContainerRecord dgContainer = (EscherContainerRecord)getSheetContainer().getPPDrawing().getEscherRecords()[0];
160. for (EscherContainerRecord c : dgContainer.getChildContainers()) {
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
> create the a double , and that given . the # value ! 
{% highlight java %}
309. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
312. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
> create a new part from the package @ param directory the document to be the @ param first the a document to fill in the file @ param directory the directory to be used 
{% highlight java %}
579. EscherContainerRecord dgContainer = (EscherContainerRecord) getEscherRecord( 0 );
581. for ( int i = 0; i < dgContainer.getChildRecords().size(); i++ )
582.     if ( dgContainer.getChild( i ).getRecordId() == EscherContainerRecord.SPGR_CONTAINER )
583.         spgrContainer = (EscherContainerRecord) dgContainer.getChild( i );
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
> create the a sheet with the given name @ param name the name to set 
{% highlight java %}
2260. EscherContainerRecord dggContainer = (EscherContainerRecord) drawingGroup.getEscherRecord( 0 );
2262. if (dggContainer.getChild( 1 ).getRecordId() == EscherContainerRecord.BSTORE_CONTAINER )
2264.     bstoreContainer = (EscherContainerRecord) dggContainer.getChild( 1 );
2270.     dggContainer.getChildRecords().add( 1, bstoreContainer );
{% endhighlight %}

***

