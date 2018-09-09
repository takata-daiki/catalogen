# EscherContainerRecord @Cluster 9

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
2177. EscherContainerRecord dggContainer = new EscherContainerRecord();
2182. dggContainer.setRecordId((short) 0xF000);
2183. dggContainer.setOptions((short) 0x000F);
2215. dggContainer.addChildRecord(dgg);
2217.     dggContainer.addChildRecord( bstoreContainer );
2218. dggContainer.addChildRecord(opt);
2219. dggContainer.addChildRecord(splitMenuColors);
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
631. EscherContainerRecord spgrContainer = new EscherContainerRecord();
639. spgrContainer.setRecordId( EscherContainerRecord.SPGR_CONTAINER );
640. spgrContainer.setOptions( (short) 0x000F );
674. spgrContainer.addChildRecord( spContainer );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
632. EscherContainerRecord spContainer = new EscherContainerRecord();
641. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
642. spContainer.setOptions( (short) 0x000F );
675. spContainer.addChildRecord( spgr );
676. spContainer.addChildRecord( sp );
677. spContainer.addChildRecord( opt );
678. spContainer.addChildRecord( anchor );
679. spContainer.addChildRecord( clientData );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
715. EscherContainerRecord dgContainer = new EscherContainerRecord();
722. dgContainer.setRecordId( EscherContainerRecord.DG_CONTAINER );
723. dgContainer.setOptions( (short) 0x000F );
744. dgContainer.addChildRecord( dg );
745. dgContainer.addChildRecord( spgrContainer );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
717. EscherContainerRecord spgrContainer = new EscherContainerRecord();
729. spgrContainer.setRecordId( EscherContainerRecord.SPGR_CONTAINER );
730. spgrContainer.setOptions( (short) 0x000F );
746. spgrContainer.addChildRecord( spContainer1 );
{% endhighlight %}

***

### [EscherAggregate.java](https://searchcode.com/codesearch/view/15642409/)
{% highlight java %}
718. EscherContainerRecord spContainer1 = new EscherContainerRecord();
731. spContainer1.setRecordId( EscherContainerRecord.SP_CONTAINER );
732. spContainer1.setOptions( (short) 0x000F );
747. spContainer1.addChildRecord( spgr );
748. spContainer1.addChildRecord( sp1 );
{% endhighlight %}

***

### [SimpleFilledShape.java](https://searchcode.com/codesearch/view/15642355/)
{% highlight java %}
79. EscherContainerRecord spContainer = new EscherContainerRecord();
84. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
85. spContainer.setOptions( (short) 0x000F );
97. spContainer.addChildRecord( sp );
98. spContainer.addChildRecord( opt );
99. spContainer.addChildRecord( anchor );
100. spContainer.addChildRecord( clientData );
{% endhighlight %}

***

### [PolygonShape.java](https://searchcode.com/codesearch/view/15642360/)
{% highlight java %}
81. EscherContainerRecord spContainer = new EscherContainerRecord();
86. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
87. spContainer.setOptions( (short) 0x000F );
142. spContainer.addChildRecord( sp );
143. spContainer.addChildRecord( opt );
144. spContainer.addChildRecord( anchor );
145. spContainer.addChildRecord( clientData );
{% endhighlight %}

***

### [PictureShape.java](https://searchcode.com/codesearch/view/15642357/)
{% highlight java %}
78. EscherContainerRecord spContainer = new EscherContainerRecord();
84. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
85. spContainer.setOptions( (short) 0x000F );
106. spContainer.addChildRecord(sp);
107. spContainer.addChildRecord(opt);
108. spContainer.addChildRecord(anchor);
109. spContainer.addChildRecord(clientData);
{% endhighlight %}

***

### [LineShape.java](https://searchcode.com/codesearch/view/15642361/)
{% highlight java %}
79. EscherContainerRecord spContainer = new EscherContainerRecord();
85. spContainer.setRecordId( EscherContainerRecord.SP_CONTAINER );
86. spContainer.setOptions( (short) 0x000F );
105. spContainer.addChildRecord(sp);
106. spContainer.addChildRecord(opt);
107. spContainer.addChildRecord(anchor);
108. spContainer.addChildRecord(clientData);
{% endhighlight %}

***

