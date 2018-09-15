# NumberRecord @Cluster 1

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
{% highlight java %}
233. NumberRecord numrec = (NumberRecord) record;
235. curRow = thisRow = numrec.getRow();
236. thisColumn = numrec.getColumn();
{% endhighlight %}

***

### [ExcelKeywordParser.java](https://searchcode.com/codesearch/view/12440040/)
{% highlight java %}
134. final NumberRecord numrec = (NumberRecord) record;
135. logger.warn("Cell [" + numrec.getRow() + "," + 
136.         numrec.getColumn() + 
138.         numrec.getValue() + ". Ignoring value");
{% endhighlight %}

***

### [ExcelLanguageCentricParser.java](https://searchcode.com/codesearch/view/12440043/)
{% highlight java %}
134. final NumberRecord numrec = (NumberRecord) record;
135. logger.warn("Cell [" + numrec.getRow() + "," + 
136.         numrec.getColumn() + 
138.         numrec.getValue() + ". Ignoring value");
{% endhighlight %}

***

### [Excel2003ImportListener.java](https://searchcode.com/codesearch/view/92669296/)
{% highlight java %}
66. NumberRecord numrec = (NumberRecord) record;
69. if(numrec.getRow() == 0) {
72. } else if(numrec.getColumn() == 0) { //第一列
74.     current.setId(Double.valueOf(numrec.getValue()).longValue());
75. } else if(numrec.getColumn() == 1) {//第二列
76.     current.setContent(String.valueOf(Double.valueOf(numrec.getValue()).longValue()));
{% endhighlight %}

***

### [EventRecordFactory.java](https://searchcode.com/codesearch/view/15642343/)
{% highlight java %}
370. NumberRecord num = new NumberRecord();
372. num.setColumn(rk.getColumn());
373. num.setRow(rk.getRow());
374. num.setXFIndex(rk.getXFIndex());
375. num.setValue(rk.getRKNumber());
{% endhighlight %}

***

### [EventRecordFactory.java](https://searchcode.com/codesearch/view/15642343/)
{% highlight java %}
389. NumberRecord nr = new NumberRecord();
391. nr.setColumn(( short ) (k + mrk.getFirstColumn()));
392. nr.setRow(mrk.getRow());
393. nr.setXFIndex(mrk.getXFAt(k));
394. nr.setValue(mrk.getRKNumberAt(k));
{% endhighlight %}

***

### [RKRecord.java](https://searchcode.com/codesearch/view/15642451/)
{% highlight java %}
196. NumberRecord rec = new NumberRecord();
198. rec.setColumn(getColumn());
199. rec.setRow(getRow());
200. rec.setValue(getRKNumber());
201. rec.setXFIndex(getXFIndex());
202. return rec.serialize(offset, data);
{% endhighlight %}

***

### [RecordFactory.java](https://searchcode.com/codesearch/view/15642481/)
{% highlight java %}
218. NumberRecord num = new NumberRecord();
220. num.setColumn(rk.getColumn());
221. num.setRow(rk.getRow());
222. num.setXFIndex(rk.getXFIndex());
223. num.setValue(rk.getRKNumber());
{% endhighlight %}

***

### [RecordFactory.java](https://searchcode.com/codesearch/view/15642481/)
{% highlight java %}
237. NumberRecord nr = new NumberRecord();
239. nr.setColumn(( short ) (k + mrk.getFirstColumn()));
240. nr.setRow(mrk.getRow());
241. nr.setXFIndex(mrk.getXFAt(k));
242. nr.setValue(mrk.getRKNumberAt(k));
{% endhighlight %}

***

