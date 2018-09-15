# EscherClientAnchorRecord @Cluster 3

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
184. EscherClientAnchorRecord clrec = (EscherClientAnchorRecord)getEscherChild(_escherContainer, EscherClientAnchorRecord.RECORD_ID);
187.     (float)clrec.getCol1()*POINT_DPI/MASTER_DPI,
188.     (float)clrec.getFlag()*POINT_DPI/MASTER_DPI,
189.     (float)(clrec.getDx1()-clrec.getCol1())*POINT_DPI/MASTER_DPI,
190.     (float)(clrec.getRow1()-clrec.getFlag())*POINT_DPI/MASTER_DPI
{% endhighlight %}

***

