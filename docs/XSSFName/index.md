# XSSFName

***

### [Cluster 1](./1)
{% highlight java %}
63. XSSFName name = workbook.getName( XSSFName.BUILTIN_FILTER_DB );
64. assertEquals( 0, name.getSheetIndex() );
65. assertEquals( "Sheet0!$A$2:$M$124", name.getRefersToFormula() );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
119. XSSFName name = workbook.getNameAt(i);
120. if(!name.isDeleted() && !name.isFunctionName()) {
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
20. private XSSFName name;
28.     return name.getNameName();
32.     String sheetName = name.getSheetName();
33.     String formula = name.getRefersToFormula();
{% endhighlight %}

***

