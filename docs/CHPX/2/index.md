# CHPX @Cluster 2

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
366. for ( CHPX chpx : _doc.getCharacterTable().getTextRuns() )
372.         System.out.println( chpx.getCharacterProperties(
378.         SprmIterator sprmIt = new SprmIterator( chpx.getGrpprl(), 0 );
388.         String text = new Range( chpx.getStart(), chpx.getEnd(),
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/48925096/)
{% highlight java %}
150. CHPX chpx = (CHPX)runIt.next();
151. boolean deleted = isDeleted(chpx.getGrpprl());
157. int runStart = chpx.getStart();
158. int runEnd = chpx.getEnd();
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/138792453/)
{% highlight java %}
149. CHPX chpx = (CHPX)runIt.next();
150. boolean deleted = isDeleted(chpx.getGrpprl());
156. int runStart = chpx.getStart();
157. int runEnd = chpx.getEnd();
{% endhighlight %}

***

### [CharacterRun.java](https://searchcode.com/codesearch/view/97384484/)
{% highlight java %}
98. CharacterRun(CHPX chpx, StyleSheet ss, short istd, Range parent)
100.   super(Math.max(parent._start, chpx.getStart()), Math.min(parent._end, chpx.getEnd()), parent);
101.   _props = chpx.getCharacterProperties(ss, istd);
102.   _chpx = chpx.getSprmBuf();
{% endhighlight %}

***

