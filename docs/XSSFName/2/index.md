# XSSFName @Cluster 2 (assertequals, name, xssfname)

***

### [AutoFilterTest.java](https://searchcode.com/codesearch/view/122565098/)
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
137. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
138. assertEquals( 0, name.getSheetIndex() );
139. assertEquals( "'Number Formats Test Report'!$A$1:$H$3", name.getRefersToFormula() );
{% endhighlight %}

***

