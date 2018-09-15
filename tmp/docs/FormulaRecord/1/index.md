# FormulaRecord @Cluster 1

***

### [ExcelExtractor.java](https://searchcode.com/codesearch/view/111785559/)
{% highlight java %}
363. FormulaRecord formula = (FormulaRecord) record;
364. addCell(record, new NumberCell(formula.getValue(), format));
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
{% highlight java %}
170. FormulaRecord frec = (FormulaRecord) record;
172. thisRow = frec.getRow();
173. thisColumn = frec.getColumn();
176.   if (Double.isNaN(frec.getValue())) {
180.     nextRow = frec.getRow();
181.     nextColumn = frec.getColumn();
187.       frec.getParsedExpression()) + '"';
{% endhighlight %}

***

### [FormulaRecord.java](https://searchcode.com/codesearch/view/15642396/)
{% highlight java %}
550. FormulaRecord rec = new FormulaRecord();
551. rec.field_1_row = field_1_row;
552. rec.field_2_column = field_2_column;
553. rec.field_3_xf = field_3_xf;
554. rec.field_4_value = field_4_value;
555. rec.field_5_options = field_5_options;
556. rec.field_6_zero = field_6_zero;
557. rec.field_7_expression_len = field_7_expression_len;
558. rec.field_8_parsed_expr = new Stack();
564.   rec.field_8_parsed_expr.add(i, ptg);
566. rec.value_data = value_data;
567. rec.all_data = all_data;
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
946. FormulaRecord rec = new FormulaRecord();
948. rec.setRow(row);
949. rec.setColumn(col);
950. rec.setOptions(( short ) 2);
951. rec.setValue(0);
952. rec.setXFIndex(( short ) 0x0f);
961.     rec.pushExpressionToken(ptg[ k ]);
963. rec.setExpressionLength(( short ) size);
{% endhighlight %}

***

### [FormulaViewer.java](https://searchcode.com/codesearch/view/15642597/)
{% highlight java %}
112. private void listFormula(FormulaRecord record) {
114.     List tokens= record.getParsedExpression();
115.     int numptgs = record.getNumberOfExpressionTokens();
{% endhighlight %}

***

### [FormulaViewer.java](https://searchcode.com/codesearch/view/15642597/)
{% highlight java %}
174. public void parseFormulaRecord(FormulaRecord record)
177.     System.out.print("row = " + record.getRow());
178.     System.out.println(", col = " + record.getColumn());
179.     System.out.println("value = " + record.getValue());
180.     System.out.print("xf = " + record.getXFIndex());
182.                        + record.getNumberOfExpressionTokens());
183.     System.out.println(", options = " + record.getOptions());
{% endhighlight %}

***

### [FormulaViewer.java](https://searchcode.com/codesearch/view/15642597/)
{% highlight java %}
188. private String formulaString(FormulaRecord record) {
190.     int          numptgs = record.getNumberOfExpressionTokens();
191.     List         tokens    = record.getParsedExpression();
{% endhighlight %}

***

### [FormulaViewer.java](https://searchcode.com/codesearch/view/15642597/)
{% highlight java %}
214. private String composeFormula(FormulaRecord record)
216.    return  loci.poi.hssf.model.FormulaParser.toFormulaString((Workbook)null,record.getParsedExpression());
{% endhighlight %}

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

### [ValueRecordsAggregate.java](https://searchcode.com/codesearch/view/15642593/)
{% highlight java %}
165. FormulaRecord formula = (FormulaRecord)rec;
166. if (formula.isSharedFormula()) {
{% endhighlight %}

***

### [SharedFormulaRecord.java](https://searchcode.com/codesearch/view/15642473/)
{% highlight java %}
206. public boolean isFormulaInShared(FormulaRecord formula) {
207.   final int formulaRow = formula.getRow();
208.   final int formulaColumn = formula.getColumn();
{% endhighlight %}

***

### [SharedFormulaRecord.java](https://searchcode.com/codesearch/view/15642473/)
{% highlight java %}
214. public void convertSharedFormulaRecord(FormulaRecord formula) {
216.   final int formulaRow = formula.getRow();
217.   final int formulaColumn = formula.getColumn();
219.     formula.setExpressionLength(getExpressionLength());
276.     formula.setParsedExpression(newPtgStack);
278.     formula.setSharedFormula(false);
{% endhighlight %}

***

