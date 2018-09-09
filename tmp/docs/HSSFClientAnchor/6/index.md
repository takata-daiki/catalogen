# HSSFClientAnchor @Cluster 6

***

### [HSSFPicture.java](https://searchcode.com/codesearch/view/15642330/)
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

