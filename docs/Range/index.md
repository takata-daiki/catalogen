# Range

***

### [Cluster 1](./1)
{% highlight java %}
799. final Range firstSubrange = field.firstSubrange( parentRange );
802.     String formula = firstSubrange.text();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
171. final Range range = new Range(b.getStart(), b.getEnd(), doc);
172. range.replaceText(range.text(), CollectableFieldFormat.getInstance(def.getJavaClass()).format(def.getOutputformat(), value));
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
1352. Range range = hdt.getRange();
1353. String text = range.text();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
129. Range r = doc.getRange(); // ??word?????
130. int lenParagraph = r.numParagraphs();// ?????
136.   Paragraph p = r.getParagraph(x);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
99. static Paragraph newParagraph( Range parent, PAPX papx )
101.     HWPFDocumentCore doc = parent._doc;
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
1163.     HWPFDocumentCore wordDocument, Range range, int beginMark )
1167. for ( int c = beginMark + 1; c < range.numCharacterRuns(); c++ )
1169.     CharacterRun characterRun = range.getCharacterRun( c );
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
22. Range range = doc.getRange();
23. Paragraph par1 = range.insertAfter(new ParagraphProperties(), 0);
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
98. CharacterRun(CHPX chpx, StyleSheet ss, short istd, Range parent)
100.   super(Math.max(parent._start, chpx.getStart()), Math.min(parent._end, chpx.getEnd()), parent);
{% endhighlight %}

***

