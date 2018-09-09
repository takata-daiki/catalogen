# CellValue

***

### [Cluster 1](./1)
{% highlight java %}
361. CellValue cellValue = evaluator.evaluate(cell);                
362. info.value=cellValue.formatAsString();
364. switch(cellValue.getCellType()) {
375.     info.value=dateFormatter.format(DateUtil.getJavaDate(cellValue.getNumberValue()));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
207. CellValue obj = evaluator.evaluate(m_cell);
212. switch(obj.getCellType())
217.         return Boolean.valueOf(obj.getBooleanValue());
221.         return new Double(obj.getNumberValue());
224.         return obj.getStringValue();
{% endhighlight %}

***

