# HSSFClientAnchor @Cluster 1 (int, math, pref)

***

### [ImageToExcel.java](https://searchcode.com/codesearch/view/94171992/)
> returns the 2 - d { @ code 0 x 3 d 4 0 } or { @ code 0 x 3 d 5 0 } @ return the of this picture 
{% highlight java %}
48. HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, col1, row1, col2, row2);
49. anchor.setAnchorType(2);
{% endhighlight %}

***

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
> calculate the size of the array of with the a value of or the return value of the number of value 
{% highlight java %}
104. HSSFClientAnchor pref = getPreferredSize();
106. int row2 = anchor.getRow1() + (pref.getRow2() - pref.getRow1());
107. int col2 = anchor.getCol1() + (pref.getCol2() - pref.getCol1());
111. anchor.setDx2(pref.getDx2());
115. anchor.setDy2(pref.getDy2());
{% endhighlight %}

***

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
> sets the line end width @ param color the new < i > null < / code > if there is no matching are a valid 
{% highlight java %}
125. HSSFClientAnchor anchor = new HSSFClientAnchor();
161.             anchor.setCol2((short)col2);
162.             anchor.setDx2(dx2);
164.             anchor.setRow2(row2);
165.             anchor.setDy2(dy2);
{% endhighlight %}

***

### [ConvertAnchor.java](https://searchcode.com/codesearch/view/15642362/)
> convert the supplied java date into a type , to be given by the supplied array . @ param row the row to convert . 
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

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
> @ param color the new color 
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

