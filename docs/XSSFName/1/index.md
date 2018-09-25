# XSSFName @Cluster 1 (assertequals, name, xssfname)

***

### [AutoFilterTest.java](https://searchcode.com/codesearch/view/122565098/)
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
63. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
64. assertEquals( 0, name.getSheetIndex() );
65. assertEquals( "Sheet0!$A$2:$M$124", name.getRefersToFormula() );
{% endhighlight %}

***

