# CellValue @Cluster 2

***

### [ExcelParser.java](https://searchcode.com/codesearch/view/93105691/)
{% highlight java %}
227. private String getCellValue( final CellValue cv ) {
228.     switch ( cv.getCellType() ) {
230.             return Boolean.toString( cv.getBooleanValue() );
232.             return String.valueOf( cv.getNumberValue() );
234.     return cv.getStringValue();
{% endhighlight %}

***

### [Cell.java](https://searchcode.com/codesearch/view/3760572/)
{% highlight java %}
207. CellValue obj = evaluator.evaluate(m_cell);
212. switch(obj.getCellType())
217.         return Boolean.valueOf(obj.getBooleanValue());
221.         return new Double(obj.getNumberValue());
224.         return obj.getStringValue();
{% endhighlight %}

***

