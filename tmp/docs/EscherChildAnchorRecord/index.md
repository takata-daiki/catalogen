# EscherChildAnchorRecord

***

## [Cluster 1](./1)
1 results
> sets the and color 2 for the sheet @ param this a cell to set the properties with @ param cell the cell to set 
{% highlight java %}
180. EscherChildAnchorRecord rec = (EscherChildAnchorRecord)getEscherChild(_escherContainer, EscherChildAnchorRecord.RECORD_ID);
194.         (float)rec.getDx1()*POINT_DPI/MASTER_DPI,
195.         (float)rec.getDy1()*POINT_DPI/MASTER_DPI,
196.         (float)(rec.getDx2()-rec.getDx1())*POINT_DPI/MASTER_DPI,
197.         (float)(rec.getDy2()-rec.getDy1())*POINT_DPI/MASTER_DPI
{% endhighlight %}

***

