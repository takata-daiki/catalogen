# XSSFName @Cluster 4 (assertequals, name, xssfname)

***

### [AutoFilterTest.java](https://searchcode.com/codesearch/view/64531325/)
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
73. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
74. assertEquals( 0, name.getSheetIndex() );
75. assertEquals( "Sheet0!$A$1:$M$2", name.getRefersToFormula() );
{% endhighlight %}

***

