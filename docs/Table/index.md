# Table

***

### [Cluster 1](./1)
{% highlight java %}
1074. Table table = range.getTable( paragraph );
1077. p += table.numParagraphs();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
115. Table t = s.getTable(p);
118. log.info("Found " + t.numRows() + "x" + cl
126. y += t.numParagraphs() - 1;
{% endhighlight %}

***

### [Cluster 3](./3)
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

### [Cluster 4](./4)
{% highlight java %}
278. private static int numCol(Table t) {
280. for (int i = 0; i < t.numRows(); i++) {
281.   if (t.getRow(i).numCells() > col)
282.     col = t.getRow(i).numCells();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
1588. Table table = (Table) anno[i];
1589. System.out.println(table.name());
1590. tableName = table.name();
{% endhighlight %}

***

