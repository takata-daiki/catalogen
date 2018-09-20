# EscherClientAnchorRecord @Cluster 1

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
202. EscherClientAnchorRecord rec = (EscherClientAnchorRecord)getEscherChild(_escherContainer, EscherClientAnchorRecord.RECORD_ID);
205.     (float)rec.getCol1()*POINT_DPI/MASTER_DPI,
206.     (float)rec.getFlag()*POINT_DPI/MASTER_DPI,
207.     (float)(rec.getDx1()-rec.getCol1())*POINT_DPI/MASTER_DPI,
208.     (float)(rec.getRow1()-rec.getFlag())*POINT_DPI/MASTER_DPI
{% endhighlight %}

***

