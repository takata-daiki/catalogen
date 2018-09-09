# FormulaRecord @Cluster 4

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
{% highlight java %}
632. FormulaRecord frec = rec.getFormulaRecord();
633. frec.setOptions(( short ) 2);
634. frec.setValue(0);
644. for (int i=0, iSize=frec.getNumberOfExpressionTokens(); i<iSize; i++) {
645.     frec.popExpressionToken();
651.     frec.pushExpressionToken(ptg[ k ]);
{% endhighlight %}

***

