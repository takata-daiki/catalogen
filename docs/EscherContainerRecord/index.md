# EscherContainerRecord

***

## [Cluster 1](./1)
17 results
> code comments is here.
{% highlight java %}
54. private EscherContainerRecord spContainer;
84.     spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
85.     spContainer.setOptions( (short) 0x000F );
97.     spContainer.addChildRecord( sp );
98.     spContainer.addChildRecord( opt );
99.     spContainer.addChildRecord( anchor );
100.     spContainer.addChildRecord( clientData );
{% endhighlight %}

***

## [Cluster 2](./2)
12 results
> code comments is here.
{% highlight java %}
591. private void convertShapes( HSSFShapeContainer parent, EscherContainerRecord escherParent, Map shapeToObj )
621.             escherParent.addChildRecord( shapeModel.getSpContainer() );
{% endhighlight %}

***

