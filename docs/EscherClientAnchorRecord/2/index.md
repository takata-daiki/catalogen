# EscherClientAnchorRecord @Cluster 2

***

### [ConvertAnchor.java](https://searchcode.com/codesearch/view/15642362/)
{% highlight java %}
61. EscherClientAnchorRecord anchor = new EscherClientAnchorRecord();
62. anchor.setRecordId( EscherClientAnchorRecord.RECORD_ID );
63. anchor.setOptions( (short) 0x0000 );
64. anchor.setFlag( (short) a.getAnchorType() );
65. anchor.setCol1( (short) Math.min(a.getCol1(), a.getCol2()) );
66. anchor.setDx1( (short) a.getDx1() );
67. anchor.setRow1( (short) Math.min(a.getRow1(), a.getRow2()) );
68. anchor.setDy1( (short) a.getDy1() );
70. anchor.setCol2( (short) Math.max(a.getCol1(), a.getCol2()) );
71. anchor.setDx2( (short) a.getDx2() );
72. anchor.setRow2( (short) Math.max(a.getRow1(), a.getRow2()) );
73. anchor.setDy2( (short) a.getDy2() );
{% endhighlight %}

***

