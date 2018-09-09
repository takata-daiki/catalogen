# NumberRecord @Cluster 9

***

### [Excel2003ImportListener.java](https://searchcode.com/codesearch/view/92669296/)
{% highlight java %}
66. NumberRecord numrec = (NumberRecord) record;
69. if(numrec.getRow() == 0) {
72. } else if(numrec.getColumn() == 0) { //第一列
74.     current.setId(Double.valueOf(numrec.getValue()).longValue());
75. } else if(numrec.getColumn() == 1) {//第二列
76.     current.setContent(String.valueOf(Double.valueOf(numrec.getValue()).longValue()));
{% endhighlight %}

***

