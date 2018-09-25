# HSSFFormulaEvaluator @Cluster 1 (case, cell_type_formula, eval)

***

### [SpreadSheetPoiHelper.java](https://searchcode.com/codesearch/view/73882044/)
> get this part name of the given part . 
{% highlight java %}
293. final HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator ( workbook );
300.     return eval.evaluate ( cell ).getStringValue ();
{% endhighlight %}

***

### [ExcelParser.java](https://searchcode.com/codesearch/view/102774956/)
> sets the 2 - d no stream 
{% highlight java %}
89. HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
110.         obj = getCellValue(evaluator.evaluateInCell(cell), formatter);
{% endhighlight %}

***

### [Cell.java](https://searchcode.com/codesearch/view/3760572/)
> sets the param be used to display the text level of the line . 
{% highlight java %}
206. HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(this.m_workBook.getHSSFWorkbook());
207. CellValue obj = evaluator.evaluate(m_cell);
{% endhighlight %}

***

