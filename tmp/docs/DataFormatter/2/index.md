# DataFormatter @Cluster 2

***

### [ExcelParser.java](https://searchcode.com/codesearch/view/93105691/)
{% highlight java %}
123. DataFormatter formatter = new DataFormatter( Locale.ENGLISH );
146.                      formatter.formatCellValue( topLeft ),
163.                         log.warn( "Cannot resolve externally linked value: " + formatter.formatCellValue( cell ) );
185.                                  formatter.formatCellValue( cell ),
{% endhighlight %}

***

### [ExcelParser.java](https://searchcode.com/codesearch/view/93105691/)
{% highlight java %}
196. DataFormatter formatter = new DataFormatter( Locale.ENGLISH );
204.             cachedValue = formatter.formatCellValue( cell );
{% endhighlight %}

***

### [XlsUtil.java](https://searchcode.com/codesearch/view/12351345/)
{% highlight java %}
30. String toString(FormulaEvaluator evaluator, DataFormatter formatter, Cell cell) {
36.       content = formatter.formatCellValue(cell);
38.       content = formatter.formatCellValue(cell,
{% endhighlight %}

***

### [ExcelUnit.java](https://searchcode.com/codesearch/view/64419074/)
{% highlight java %}
23. private DataFormatter formatter = new HSSFDataFormatter();
52.     return formatter.formatCellValue(cell, formulaEvaluator);
{% endhighlight %}

***

