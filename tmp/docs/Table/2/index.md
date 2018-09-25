# Table @Cluster 2 (int, table, void)

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> set the contents of the record string , i . e . the code from the . 
{% highlight java %}
1074. Table table = range.getTable( paragraph );
1077. p += table.numParagraphs();
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
> sets the 
{% highlight java %}
115. Table t = s.getTable(p);
118. log.info("Found " + t.numRows() + "x" + cl
126. y += t.numParagraphs() - 1;
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the { @ link a 3 d } object with the given name . @ param name the name to this cell is being read @ param is the workbook to write the formula to 
{% highlight java %}
222. protected int getNumberRowsSpanned( Table table,
229.     final int numRows = table.numRows();
234.         TableRow nextRow = table.getRow( r1 );
{% endhighlight %}

***

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
> test that we get the same value as excel and , for 
{% highlight java %}
622.     Table table )
629. final int tableRows = table.numRows();
634.     maxColumns = Math.max( maxColumns, table.getRow( r ).numCells() );
639.     TableRow tableRow = table.getRow( r );
693.                 table.getTableLevel() );
741.             Integer.valueOf( table.getStartOffset() ), "; ",
742.             Integer.valueOf( table.getEndOffset() ), ")" );
{% endhighlight %}

***

