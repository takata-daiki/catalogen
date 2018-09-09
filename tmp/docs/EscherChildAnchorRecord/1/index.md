# EscherChildAnchorRecord @Cluster 1

***

### [ConvertAnchor.java](https://searchcode.com/codesearch/view/15642362/)
{% highlight java %}
79. EscherChildAnchorRecord anchor = new EscherChildAnchorRecord();
80. anchor.setRecordId( EscherChildAnchorRecord.RECORD_ID );
81. anchor.setOptions( (short) 0x0000 );
82. anchor.setDx1( (short) Math.min(a.getDx1(), a.getDx2()) );
83. anchor.setDy1( (short) Math.min(a.getDy1(), a.getDy2()) );
84. anchor.setDx2( (short) Math.max(a.getDx2(), a.getDx1()) );
85. anchor.setDy2( (short) Math.max(a.getDy2(), a.getDy1()) );
{% endhighlight %}

***

