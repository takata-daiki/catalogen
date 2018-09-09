# HSSFFormulaEvaluator @Cluster 1

***

### [SpreadSheetPoiHelper.java](https://searchcode.com/codesearch/view/73882044/)
{% highlight java %}
293. final HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator ( workbook );
300.     return eval.evaluate ( cell ).getStringValue ();
{% endhighlight %}

***

### [DataLoaderXls.java](https://searchcode.com/codesearch/view/73880973/)
{% highlight java %}
143. final HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator ( workbook );
150.     return eval.evaluate ( cell ).getStringValue ();
{% endhighlight %}

***

### [ExcelParser.java](https://searchcode.com/codesearch/view/102774956/)
{% highlight java %}
89. HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
110.         obj = getCellValue(evaluator.evaluateInCell(cell), formatter);
{% endhighlight %}

***

### [Cell.java](https://searchcode.com/codesearch/view/3760572/)
{% highlight java %}
206. HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(this.m_workBook.getHSSFWorkbook());
207. CellValue obj = evaluator.evaluate(m_cell);
{% endhighlight %}

***

