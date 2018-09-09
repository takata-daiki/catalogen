# CellValue @Cluster 1

***

### [ExcelFeatureSource.java](https://searchcode.com/codesearch/view/47133171/)
{% highlight java %}
112. CellValue value = evaluator.evaluate(cell);
115.     if (value.getCellType() == Cell.CELL_TYPE_NUMERIC) {
116.         y = value.getNumberValue();
120.         x = value.getNumberValue();
125.     switch (value.getCellType()) {
130.             builder.set(name, value.getNumberValue());
132.             final java.util.Date javaDate = HSSFDateUtil.getJavaDate(value
155.         builder.set(name, value.getStringValue().trim());
158.         builder.set(name, value.getBooleanValue());
{% endhighlight %}

***

### [ExcelFeatureSource.java](https://searchcode.com/codesearch/view/47133171/)
{% highlight java %}
245. CellValue value = evaluator.evaluate(cell);
246. int type = value.getCellType();
263.             if (value.getNumberValue() < 1.0) {
{% endhighlight %}

***

### [WorkbookParser.java](https://searchcode.com/codesearch/view/112283807/)
{% highlight java %}
361. CellValue cellValue = evaluator.evaluate(cell);                
362. info.value=cellValue.formatAsString();
364. switch(cellValue.getCellType()) {
375.     info.value=dateFormatter.format(DateUtil.getJavaDate(cellValue.getNumberValue()));
{% endhighlight %}

***

### [SpreadsheetData.java](https://searchcode.com/codesearch/view/54316596/)
{% highlight java %}
123. CellValue cellValue = evaluator.evaluate(cell);
126. if (cellValue.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
127.     result = cellValue.getBooleanValue();
128. } else if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
129.     result = cellValue.getNumberValue();
130. } else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING) {
131.     result = cellValue.getStringValue();   
{% endhighlight %}

***

### [ExcelConfig.java](https://searchcode.com/codesearch/view/12555894/)
{% highlight java %}
166. CellValue cellvalue = getEval().evaluate(cell);
167. switch (cellvalue.getCellType()) {
169.   booleanValue = cellvalue.getBooleanValue();
172.   doubleValue = cellvalue.getNumberValue();
{% endhighlight %}

***

### [ImportPanel.java](https://searchcode.com/codesearch/view/3408722/)
{% highlight java %}
168. CellValue cellValue = eval.evaluate(cell);
169. value = org.mypomodoro.util.DateUtil.getFormatedDate(DateUtil.getJavaDate(cellValue.getNumberValue(), true), importInputForm.getDatePattern());
{% endhighlight %}

***

### [Book01XlsFormulaHitTest.java](https://searchcode.com/codesearch/view/3969789/)
{% highlight java %}
95. CellValue value = _evaluator.evaluate(cell);
96. assertEquals(5, value.getNumberValue(), 0.0000000000000001);
97. assertEquals(Cell.CELL_TYPE_NUMERIC, value.getCellType());
110. assertEquals(7, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

### [Book01XlsFormulaHitTest.java](https://searchcode.com/codesearch/view/3969789/)
{% highlight java %}
122. CellValue value = _evaluator.evaluate(cell);
123. assertEquals(6, value.getNumberValue(), 0.0000000000000001);
124. assertEquals(Cell.CELL_TYPE_NUMERIC, value.getCellType());
138. assertEquals(7, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

### [Book01XlsFormulaHitTest.java](https://searchcode.com/codesearch/view/3969789/)
{% highlight java %}
151. CellValue value = _evaluator.evaluate(cell);
152. assertEquals(6, value.getNumberValue(), 0.0000000000000001);
153. assertEquals(Cell.CELL_TYPE_NUMERIC, value.getCellType());
167. assertEquals(8, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

### [Book01XlsFormulaHitTest.java](https://searchcode.com/codesearch/view/3969789/)
{% highlight java %}
179. CellValue value = _evaluator.evaluate(cell);
180. assertEquals(1, value.getNumberValue(), 0.0000000000000001);
181. assertEquals(Cell.CELL_TYPE_NUMERIC, value.getCellType());
195. assertEquals(2, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

### [Book01XlsFormulaHitTest.java](https://searchcode.com/codesearch/view/3969789/)
{% highlight java %}
208. CellValue value = _evaluator.evaluate(cell);
209. assertEquals(6, value.getNumberValue(), 0.0000000000000001);
210. assertEquals(Cell.CELL_TYPE_NUMERIC, value.getCellType());
225. assertEquals(7, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

### [Book03XlsFormulaEvaluatorTest.java](https://searchcode.com/codesearch/view/3969788/)
{% highlight java %}
91. CellValue value = _evaluator.evaluate(cell);
92. assertEquals(0, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

### [Book03XlsFormulaEvaluatorTest.java](https://searchcode.com/codesearch/view/3969788/)
{% highlight java %}
97. CellValue value = _evaluator.evaluate(cell);
98. assertEquals(0, value.getNumberValue(), 0.0000000000000001);
{% endhighlight %}

***

