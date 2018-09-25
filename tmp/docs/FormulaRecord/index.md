# FormulaRecord

***

## [Cluster 1 (formularecord, return, stringrecord)](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
58. private FormulaRecord formulaRecord;
88.     pos += formulaRecord.serialize(pos, data);
103.     int size = formulaRecord.getRecordSize() + (stringRecord == null ? 0 : stringRecord.getRecordSize());
138.     return formulaRecord.isEqual( i );
143.     return formulaRecord.isAfter( i );
148.     return formulaRecord.isBefore( i );
153.     return formulaRecord.getXFIndex();
158.     formulaRecord.setXFIndex( xf );
163.     formulaRecord.setColumn( col );
168.     formulaRecord.setRow( row );
173.     return formulaRecord.getColumn();
178.     return formulaRecord.getRow();
183.     return formulaRecord.compareTo( o );
188.     return formulaRecord.equals( obj );
193.     return formulaRecord.toString();
202.     return new FormulaRecordAggregate((FormulaRecord) this.formulaRecord.clone(), clonedString);
{% endhighlight %}

***

## [Cluster 2 (formularecord, frec, rec)](./2)
8 results
> sets the for the specified one . @ param { @ code true } if the first sheet is a { @ link ' } s @ param the if the formula value is { @ code null } 
{% highlight java %}
112. private void listFormula(FormulaRecord record) {
114.     List tokens= record.getParsedExpression();
115.     int numptgs = record.getNumberOfExpressionTokens();
{% endhighlight %}

***

