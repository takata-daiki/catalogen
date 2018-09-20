# EscherContainerRecord @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
214. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
217. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
357. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
360. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
153. EscherContainerRecord dgContainer = (EscherContainerRecord)getSheetContainer().getPPDrawing().getEscherRecords()[0];
160. for (EscherContainerRecord c : dgContainer.getChildContainers()) {
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
309. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
312. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
164. EscherContainerRecord dc = (EscherContainerRecord)c.getChild(0);
165. spr = dc.getChildById(EscherSpRecord.RECORD_ID);
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
579. EscherContainerRecord dgContainer = (EscherContainerRecord) getEscherRecord( 0 );
581. for ( int i = 0; i < dgContainer.getChildRecords().size(); i++ )
582.     if ( dgContainer.getChild( i ).getRecordId() == EscherContainerRecord.SPGR_CONTAINER )
583.         spgrContainer = (EscherContainerRecord) dgContainer.getChild( i );
{% endhighlight %}

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
160. for (EscherContainerRecord c : dgContainer.getChildContainers()) {
162.     switch(c.getRecordId()){
164.             EscherContainerRecord dc = (EscherContainerRecord)c.getChild(0);
168.             spr = c.getChildById(EscherSpRecord.RECORD_ID);
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
2260. EscherContainerRecord dggContainer = (EscherContainerRecord) drawingGroup.getEscherRecord( 0 );
2262. if (dggContainer.getChild( 1 ).getRecordId() == EscherContainerRecord.BSTORE_CONTAINER )
2264.     bstoreContainer = (EscherContainerRecord) dggContainer.getChild( 1 );
2270.     dggContainer.getChildRecords().add( 1, bstoreContainer );
{% endhighlight %}

***

