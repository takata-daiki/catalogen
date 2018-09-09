# FormulaRecordAggregate

***

### [Cluster 1](./1)
{% highlight java %}
631. FormulaRecordAggregate rec = (FormulaRecordAggregate) record;
632. FormulaRecord frec = rec.getFormulaRecord();
637. if (rec.getXFIndex() == (short)0) rec.setXFIndex(( short ) 0x0f);
653. rec.getFormulaRecord().setExpressionLength(( short ) size);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
353. FormulaRecordAggregate frec = null;
363. frec.setColumn(col);
366.     frec.getFormulaRecord().setValue(getNumericCellValue());
368. frec.setXFIndex(styleIndex);
369. frec.setRow(row);
{% endhighlight %}

***

