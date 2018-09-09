# CHPX

***

### [Cluster 1](./1)
{% highlight java %}
149. CHPX chpx = (CHPX)runIt.next();
150. boolean deleted = isDeleted(chpx.getGrpprl());
156. int runStart = chpx.getStart();
157. int runEnd = chpx.getEnd();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
65. CHPX chpx = (CHPX)runsIt.next();
66. int runStart = chpx.getStart() + fcMin;
67. int runEnd = chpx.getEnd() + fcMin;
69. if (!isDeleted(chpx.getGrpprl()))
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
98. CharacterRun(CHPX chpx, StyleSheet ss, short istd, Range parent)
100.   super(Math.max(parent._start, chpx.getStart()), Math.min(parent._end, chpx.getEnd()), parent);
101.   _props = chpx.getCharacterProperties(ss, istd);
102.   _chpx = chpx.getSprmBuf();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
366. for ( CHPX chpx : _doc.getCharacterTable().getTextRuns() )
372.         System.out.println( chpx.getCharacterProperties(
378.         SprmIterator sprmIt = new SprmIterator( chpx.getGrpprl(), 0 );
388.         String text = new Range( chpx.getStart(), chpx.getEnd(),
{% endhighlight %}

***
