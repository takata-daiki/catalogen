# XSSFName @Cluster 3 (assertequals, name, xssfname)

***

### [AutoFilterTest.java](https://searchcode.com/codesearch/view/64531325/)
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
107. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
108. assertEquals( 0, name.getSheetIndex() );
109. assertEquals( "'Number Formats 1'!$A$1:$H$3", name.getRefersToFormula() );
{% endhighlight %}

***

### [AutoFilterTest.java](https://searchcode.com/codesearch/view/122565098/)
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
97. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
98. assertEquals( 0, name.getSheetIndex() );
99. assertEquals( "'Number Formats 1'!$A$1:$H$3", name.getRefersToFormula() );
{% endhighlight %}

***

