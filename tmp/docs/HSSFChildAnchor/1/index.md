# HSSFChildAnchor @Cluster 1 (cellstyle, defaultcellstyle2, setfont)

***

### [ConvertAnchor.java](https://searchcode.com/codesearch/view/15642362/)
> @ param value the param length of the stream in the set - 1 should be 
{% highlight java %}
78. HSSFChildAnchor a = (HSSFChildAnchor) userAnchor;
82. anchor.setDx1( (short) Math.min(a.getDx1(), a.getDx2()) );
83. anchor.setDy1( (short) Math.min(a.getDy1(), a.getDy2()) );
84. anchor.setDx2( (short) Math.max(a.getDx2(), a.getDx1()) );
85. anchor.setDy2( (short) Math.max(a.getDy2(), a.getDy1()) );
{% endhighlight %}

***

