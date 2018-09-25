# FormulaRecord @Cluster 2 (formularecord, frec, rec)

***

### [FormulaViewer.java](https://searchcode.com/codesearch/view/15642597/)
> sets the for the specified one . @ param { @ code true } if the first sheet is a { @ link ' } s @ param the if the formula value is { @ code null } 
{% highlight java %}
112. private void listFormula(FormulaRecord record) {
114.     List tokens= record.getParsedExpression();
115.     int numptgs = record.getNumberOfExpressionTokens();
{% endhighlight %}

***

### [SharedFormulaRecord.java](https://searchcode.com/codesearch/view/15642473/)
> test that we get the same value as excel and , for 
{% highlight java %}
206. public boolean isFormulaInShared(FormulaRecord formula) {
207.   final int formulaRow = formula.getRow();
208.   final int formulaColumn = formula.getColumn();
{% endhighlight %}

***

### [SharedFormulaRecord.java](https://searchcode.com/codesearch/view/15642473/)
> test that we get the same value as excel and , for 
{% highlight java %}
214. public void convertSharedFormulaRecord(FormulaRecord formula) {
216.   final int formulaRow = formula.getRow();
217.   final int formulaColumn = formula.getColumn();
219.     formula.setExpressionLength(getExpressionLength());
276.     formula.setParsedExpression(newPtgStack);
278.     formula.setSharedFormula(false);
{% endhighlight %}

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param @ param 
{% highlight java %}
632. FormulaRecord frec = rec.getFormulaRecord();
633. frec.setOptions(( short ) 2);
634. frec.setValue(0);
644. for (int i=0, iSize=frec.getNumberOfExpressionTokens(); i<iSize; i++) {
645.     frec.popExpressionToken();
651.     frec.pushExpressionToken(ptg[ k ]);
{% endhighlight %}

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
> sets the package part . @ param cell the cell to set the cells to . 
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

### [FormulaViewer.java](https://searchcode.com/codesearch/view/15642597/)
> test that we get the same value as excel and , for 
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

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> set the first or } of the current cell @ param sheet the sheet to set ( 0 - based ) @ param width the index of the column 
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

### [FormulaRecord.java](https://searchcode.com/codesearch/view/15642396/)
> sets the property ' s id . 
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

