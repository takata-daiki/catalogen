# EscherClientAnchorRecord @Cluster 1

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

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
202. EscherClientAnchorRecord rec = (EscherClientAnchorRecord)getEscherChild(_escherContainer, EscherClientAnchorRecord.RECORD_ID);
205.     (float)rec.getCol1()*POINT_DPI/MASTER_DPI,
206.     (float)rec.getFlag()*POINT_DPI/MASTER_DPI,
207.     (float)(rec.getDx1()-rec.getCol1())*POINT_DPI/MASTER_DPI,
208.     (float)(rec.getRow1()-rec.getFlag())*POINT_DPI/MASTER_DPI
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
235. EscherClientAnchorRecord rec = (EscherClientAnchorRecord)getEscherChild(_escherContainer, EscherClientAnchorRecord.RECORD_ID);
236. rec.setFlag((short)(anchor.getY()*MASTER_DPI/POINT_DPI));
237. rec.setCol1((short)(anchor.getX()*MASTER_DPI/POINT_DPI));
238. rec.setDx1((short)(((anchor.getWidth() + anchor.getX())*MASTER_DPI/POINT_DPI)));
239. rec.setRow1((short)(((anchor.getHeight() + anchor.getY())*MASTER_DPI/POINT_DPI)));
{% endhighlight %}

***

