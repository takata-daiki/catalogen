# PointerContainingStream

***

### [Cluster 1](./1)
{% highlight java %}
93. PointerContainingStream pcs = (PointerContainingStream)stream;
95.     pcs.getPointedToStreams().length + " children:");
97. for(int i=0; i<pcs.getPointedToStreams().length; i++) {
98.   dumpStream(pcs.getPointedToStreams()[i], (indent+1));
{% endhighlight %}

***

