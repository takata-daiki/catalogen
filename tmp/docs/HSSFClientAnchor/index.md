# HSSFClientAnchor

***

### [Cluster 1](./1)
{% highlight java %}
125. HSSFClientAnchor anchor = new HSSFClientAnchor();
161.             anchor.setCol2((short)col2);
162.             anchor.setDx2(dx2);
164.             anchor.setRow2(row2);
165.             anchor.setDy2(dy2);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
59. HSSFClientAnchor a = (HSSFClientAnchor) userAnchor;
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

### [Cluster 3](./3)
{% highlight java %}
77. HSSFClientAnchor anchor;
79. anchor.setAnchorType( 2 );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
325. HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,
327. anchor.setAnchorType(2);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
48. HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, col1, row1, col2, row2);
49. anchor.setAnchorType(2);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
101. HSSFClientAnchor anchor = (HSSFClientAnchor)getAnchor();
102. anchor.setAnchorType(2);
106. int row2 = anchor.getRow1() + (pref.getRow2() - pref.getRow1());
107. int col2 = anchor.getCol1() + (pref.getCol2() - pref.getCol1());
109. anchor.setCol2((short)col2);
110. anchor.setDx1(0);
111. anchor.setDx2(pref.getDx2());
113. anchor.setRow2(row2);
114. anchor.setDy1(0);
115. anchor.setDy2(pref.getDy2());
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
104. HSSFClientAnchor pref = getPreferredSize();
106. int row2 = anchor.getRow1() + (pref.getRow2() - pref.getRow1());
107. int col2 = anchor.getCol1() + (pref.getCol2() - pref.getCol1());
111. anchor.setDx2(pref.getDx2());
115. anchor.setDy2(pref.getDy2());
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
235. HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();  
240.     String picIndex = String.valueOf(anchor.getRow1()) + "_"  
241.             + String.valueOf(anchor.getCol1());  
{% endhighlight %}

***

