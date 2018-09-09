# EscherContainerRecord @Cluster 4

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
2260. EscherContainerRecord dggContainer = (EscherContainerRecord) drawingGroup.getEscherRecord( 0 );
2262. if (dggContainer.getChild( 1 ).getRecordId() == EscherContainerRecord.BSTORE_CONTAINER )
2264.     bstoreContainer = (EscherContainerRecord) dggContainer.getChild( 1 );
2270.     dggContainer.getChildRecords().add( 1, bstoreContainer );
{% endhighlight %}

***

