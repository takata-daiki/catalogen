# HSSFComment

***

## [Cluster 1 (note, note_visible, record)](./1)
4 results
> assign a comment to this cell @ param comment comment associated with this cell 
{% highlight java %}
999. public void setCellComment(HSSFComment comment){
1000.     comment.setRow((short)record.getRow());
1001.     comment.setColumn(record.getColumn());
{% endhighlight %}

***

## [Cluster 2 (anchor, createcomment, short)](./2)
7 results
> sets the shape - @ param this 
{% highlight java %}
160. HSSFComment shape = new HSSFComment(null, anchor);
161. shape.anchor = anchor;
{% endhighlight %}

***

