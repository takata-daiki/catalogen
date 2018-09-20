# TableRow

***

## [Cluster 1](./1)
1 results
> this comment could not be generated...
{% highlight java %}
305. TableRow tr = t.getRow(i);
310. for (int j = 0; j < tr.numCells(); j++) {
311.   TableCell tc = tr.getCell(j);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> set the top - left column index on the sheet @ param table number of blocks 
{% highlight java %}
75. TableRow tableRow = table.getRow( r );
76. for ( int c = 0; c < tableRow.numCells(); c++ )
78.     TableCell tableCell = tableRow.getCell( c );
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> sets the 
{% highlight java %}
234. TableRow nextRow = table.getRow( r1 );
235. if ( currentColumnIndex >= nextRow.numCells() )
241. for ( int c = 0; c < nextRow.numCells(); c++ )
243.     TableCell nextTableCell = nextRow.getCell( c );
266. TableCell nextCell = nextRow.getCell( currentColumnIndex );
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> set a boolean value for the cell @ param value the boolean value to set this cell to . for formulas we ' ll set the precalculated value , for numerics we ' ll set its value . for other types we will change the cell to a boolean cell and set its value . 
{% highlight java %}
639. TableRow tableRow = table.getRow( r );
647. final int rowCells = tableRow.numCells();
650.     TableCell tableCell = tableRow.getCell( c );
661.     if ( tableRow.isTableHeader() )
712. if ( tableRow.isTableHeader() )
{% endhighlight %}

***

