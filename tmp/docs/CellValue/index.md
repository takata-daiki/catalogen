# CellValue

***

## [Cluster 1](./1)
1 results
> this comment could not be generated...
{% highlight java %}
245. CellValue value = evaluator.evaluate(cell);
246. int type = value.getCellType();
263.             if (value.getNumberValue() < 1.0) {
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> make sure that the evaluator can directly handle ( deleted ) default error tokens @ since 3 . 1 7 beta 1 
{% highlight java %}
179. CellValue value = _evaluator.evaluate(cell);
180. assertEquals(1, value.getNumberValue(), 0.0000000000000001);
181. assertEquals(Cell.CELL_TYPE_NUMERIC, value.getCellType());
195. assertEquals(2, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

## [Cluster 3](./3)
13 results
> for a given ( ) when the 
{% highlight java %}
168. CellValue cellValue = eval.evaluate(cell);
169. value = org.mypomodoro.util.DateUtil.getFormatedDate(DateUtil.getJavaDate(cellValue.getNumberValue(), true), importInputForm.getDatePattern());
{% endhighlight %}

***

