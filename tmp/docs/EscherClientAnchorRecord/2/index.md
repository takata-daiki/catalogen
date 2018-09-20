# EscherClientAnchorRecord @Cluster 2

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

