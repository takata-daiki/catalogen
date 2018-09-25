# HSSFFormulaEvaluator

***

## [Cluster 1 (case, cell_type_formula, eval)](./1)
3 results
> get this part name of the given part . 
{% highlight java %}
293. final HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator ( workbook );
300.     return eval.evaluate ( cell ).getStringValue ();
{% endhighlight %}

***

## [Cluster 2 (cellvalue, evaluate, evaluator)](./2)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
25. private HSSFFormulaEvaluator evaluator;
144.                 CellValue wert = evaluator.evaluate(zelle);
{% endhighlight %}

***

## [Cluster 3 (case, evaluateincell, evaluator)](./3)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
88. private HSSFFormulaEvaluator evaluator;
286.                evaluator.evaluate(c);
289.                evaluator.evaluateFormulaCell(c);
292.                evaluator.evaluateInCell(c);
1367.             evaluator.evaluateInCell(cell);
{% endhighlight %}

***

