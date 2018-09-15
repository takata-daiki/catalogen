# CellValue

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
179. CellValue value = _evaluator.evaluate(cell);
180. assertEquals(1, value.getNumberValue(), 0.0000000000000001);
181. assertEquals(Cell.CELL_TYPE_NUMERIC, value.getCellType());
195. assertEquals(2, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

## [Cluster 2](./2)
13 results
> code comments is here.
{% highlight java %}
361. CellValue cellValue = evaluator.evaluate(cell);                
362. info.value=cellValue.formatAsString();
364. switch(cellValue.getCellType()) {
375.     info.value=dateFormatter.format(DateUtil.getJavaDate(cellValue.getNumberValue()));
{% endhighlight %}

***

