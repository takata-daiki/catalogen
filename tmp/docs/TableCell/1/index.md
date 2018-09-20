# TableCell @Cluster 1

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
243. TableCell nextTableCell = nextRow.getCell( c );
244. if ( !nextTableCell.isVerticallyMerged()
245.         || nextTableCell.isFirstVerticallyMerged() )
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
266. TableCell nextCell = nextRow.getCell( currentColumnIndex );
267. if ( !nextCell.isVerticallyMerged()
268.         || nextCell.isFirstVerticallyMerged() )
{% endhighlight %}

***

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
{% highlight java %}
650. TableCell tableCell = tableRow.getCell( c );
652. if ( tableCell.isVerticallyMerged()
653.         && !tableCell.isFirstVerticallyMerged() )
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
{% highlight java %}
311. TableCell tc = tr.getCell(j);
318. for (int y = 0; y < tc.numParagraphs(); y++) {
319.   Paragraph p = tc.getParagraph(y);
{% endhighlight %}

***

### [AbstractWordUtils.java](https://searchcode.com/codesearch/view/97383984/)
{% highlight java %}
78. TableCell tableCell = tableRow.getCell( c );
80. edges.add( Integer.valueOf( tableCell.getLeftEdge() ) );
81. edges.add( Integer.valueOf( tableCell.getLeftEdge()
82.         + tableCell.getWidth() ) );
{% endhighlight %}

***

