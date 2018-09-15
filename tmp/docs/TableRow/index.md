# TableRow

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
75. TableRow tableRow = table.getRow( r );
76. for ( int c = 0; c < tableRow.numCells(); c++ )
78.     TableCell tableCell = tableRow.getCell( c );
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
234. TableRow nextRow = table.getRow( r1 );
235. if ( currentColumnIndex >= nextRow.numCells() )
241. for ( int c = 0; c < nextRow.numCells(); c++ )
243.     TableCell nextTableCell = nextRow.getCell( c );
266. TableCell nextCell = nextRow.getCell( currentColumnIndex );
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
639. TableRow tableRow = table.getRow( r );
647. final int rowCells = tableRow.numCells();
650.     TableCell tableCell = tableRow.getCell( c );
661.     if ( tableRow.isTableHeader() )
712. if ( tableRow.isTableHeader() )
{% endhighlight %}

***

