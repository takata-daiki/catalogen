# CHPX @Cluster 3

***

### [CharacterRun.java](https://searchcode.com/codesearch/view/97384484/)
{% highlight java %}
98. CharacterRun(CHPX chpx, StyleSheet ss, short istd, Range parent)
100.   super(Math.max(parent._start, chpx.getStart()), Math.min(parent._end, chpx.getEnd()), parent);
101.   _props = chpx.getCharacterProperties(ss, istd);
102.   _chpx = chpx.getSprmBuf();
{% endhighlight %}

***

