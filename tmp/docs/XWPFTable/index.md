# XWPFTable

***

## [Cluster 1](./1)
1 results
> this comment could not be generated...
{% highlight java %}
1017. XWPFTable table = cell.getTableRow().getTable();
1018. for ( int i = rowIndex + 1; i < table.getRows().size(); i++ )
1020.     row = table.getRow( i );
{% endhighlight %}

***

## [Cluster 2](./2)
2 results
> sets the 
{% highlight java %}
134. public static int getNumberOfColumnFromFirstRow( XWPFTable table )
139.     int numberOfRows = table.getNumberOfRows();
142.         XWPFTableRow firstRow = table.getRow( 0 );
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
213. public static XWPFTableRow getFirstRow( XWPFTable table )
215.     int numberOfRows = table.getNumberOfRows();
218.         return table.getRow( 0 );
{% endhighlight %}

***

## [Cluster 4](./4)
21 results
> sets the 
{% highlight java %}
833. protected void visitTableBody( XWPFTable table, float[] colWidths, T tableContainer )
840.     List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

