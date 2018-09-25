# HSSFColor @Cluster 1 (automatic, result, rgbbyte)

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
> @ param tests - the color to the end of the 
{% highlight java %}
78. HSSFColor hssfColor = (HSSFColor) color;
79. short[] rgb = hssfColor.getTriplet();
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
> test that we get the same value as excel and , for 
{% highlight java %}
29. private static final HSSFColor HSSF_AUTO = new HSSFColor.AUTOMATIC();
114.   if (index == HSSF_AUTO.getIndex() || color == null) {
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/122565152/)
> get the 2 - d string value by a record . 
{% highlight java %}
130. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
140. return result.getIndex();
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
> index into the < tt > collection < / tt > to the specified shape . 
{% highlight java %}
112. HSSFColor color = palette.getColor(index);
117.   short[] rgb = color.getTriplet();
{% endhighlight %}

***

### [HSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283811/)
> index into the < tt > collection < / tt > to the specified shape . 
{% highlight java %}
21. private static final HSSFColor HSSF_AUTO = new HSSFColor.AUTOMATIC();
60.   if (index == HSSF_AUTO.getIndex() || color == null)
{% endhighlight %}

***

