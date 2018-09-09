# XSSFCellFill @Cluster 1

***

### [XLColorTest.java](https://searchcode.com/codesearch/view/121321469/)
{% highlight java %}
56. XSSFCellFill fill = stylesSource.getFillAt(cellStyle.getIndex()+1);
58. if (fill.getFillBackgroundColor()!=null)
59.   System.out.println("Index: " + cellStyle.getIndex() + " "+ fill.getFillBackgroundColor().getARGBHex());
61. if (fill.getFillForegroundColor()!=null)
62. System.out.println("Index: " +cellStyle.getIndex() + " "+ fill.getFillForegroundColor().getARGBHex());
{% endhighlight %}

***

