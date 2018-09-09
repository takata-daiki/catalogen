# TableCell

***

### [Cluster 1](./1)
{% highlight java %}
209.     int currentEdgeIndex, TableCell tableCell )
213. int cellRightEdge = tableCell.getLeftEdge() + tableCell.getWidth();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
311. TableCell tc = tr.getCell(j);
318. for (int y = 0; y < tc.numParagraphs(); y++) {
319.   Paragraph p = tc.getParagraph(y);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
650. TableCell tableCell = tableRow.getCell( c );
652. if ( tableCell.isVerticallyMerged()
653.         && !tableCell.isFirstVerticallyMerged() )
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
224.     int currentColumnIndex, TableCell tableCell )
226. if ( !tableCell.isFirstVerticallyMerged() )
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
78. TableCell tableCell = tableRow.getCell( c );
80. edges.add( Integer.valueOf( tableCell.getLeftEdge() ) );
81. edges.add( Integer.valueOf( tableCell.getLeftEdge()
82.         + tableCell.getWidth() ) );
{% endhighlight %}

***

