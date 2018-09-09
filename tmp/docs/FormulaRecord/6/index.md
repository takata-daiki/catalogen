# FormulaRecord @Cluster 6

***

### [FormulaRecordAggregate.java](https://searchcode.com/codesearch/view/15642592/)
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

