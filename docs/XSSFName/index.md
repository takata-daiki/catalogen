# XSSFName

***

## [Cluster 1 (assertequals, name, xssfname)](./1)
1 results
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
63. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
64. assertEquals( 0, name.getSheetIndex() );
65. assertEquals( "Sheet0!$A$2:$M$124", name.getRefersToFormula() );
{% endhighlight %}

***

## [Cluster 2 (assertequals, name, xssfname)](./2)
1 results
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
137. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
138. assertEquals( 0, name.getSheetIndex() );
139. assertEquals( "'Number Formats Test Report'!$A$1:$H$3", name.getRefersToFormula() );
{% endhighlight %}

***

## [Cluster 3 (assertequals, name, xssfname)](./3)
2 results
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
107. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
108. assertEquals( 0, name.getSheetIndex() );
109. assertEquals( "'Number Formats 1'!$A$1:$H$3", name.getRefersToFormula() );
{% endhighlight %}

***

## [Cluster 4 (assertequals, name, xssfname)](./4)
1 results
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
73. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
74. assertEquals( 0, name.getSheetIndex() );
75. assertEquals( "Sheet0!$A$1:$M$2", name.getRefersToFormula() );
{% endhighlight %}

***

## [Cluster 5 (formula, name, string)](./5)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
20. private XSSFName name;
28.     return name.getNameName();
32.     String sheetName = name.getSheetName();
33.     String formula = name.getRefersToFormula();
{% endhighlight %}

***

