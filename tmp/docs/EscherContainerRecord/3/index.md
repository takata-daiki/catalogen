# EscherContainerRecord @Cluster 3

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
579. EscherContainerRecord dgContainer = (EscherContainerRecord) getEscherRecord( 0 );
581. for ( int i = 0; i < dgContainer.getChildRecords().size(); i++ )
582.     if ( dgContainer.getChild( i ).getRecordId() == EscherContainerRecord.SPGR_CONTAINER )
583.         spgrContainer = (EscherContainerRecord) dgContainer.getChild( i );
{% endhighlight %}

***

