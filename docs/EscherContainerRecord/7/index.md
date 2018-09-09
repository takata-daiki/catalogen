# EscherContainerRecord @Cluster 7

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
{% highlight java %}
800. EscherContainerRecord bstore;
807.   bstore.setRecordId(EscherContainerRecord.BSTORE_CONTAINER);
811.   Iterator<EscherRecord> iter = bstore.getChildIterator();
845. bstore.addChildRecord(bse);
846. int count = bstore.getChildRecords().size();
847. bstore.setOptions((short) ((count << 4) | 0xF));
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
2191. EscherContainerRecord bstoreContainer = null;
2195.     bstoreContainer.setRecordId( EscherContainerRecord.BSTORE_CONTAINER );
2196.     bstoreContainer.setOptions( (short) ( (escherBSERecords.size() << 4) | 0xF ) );
2200.         bstoreContainer.addChildRecord( escherRecord );
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
2261. EscherContainerRecord bstoreContainer;
2269.     bstoreContainer.setRecordId( EscherContainerRecord.BSTORE_CONTAINER );
2272. bstoreContainer.setOptions( (short) ( (escherBSERecords.size() << 4) | 0xF ) );
2274. bstoreContainer.addChildRecord( e );
{% endhighlight %}

***

