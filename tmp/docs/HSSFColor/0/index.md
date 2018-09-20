# HSSFColor @Cluster 1

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
227. HSSFColor col = translateColour(colour);
230. style.setFillForegroundColor(col.getIndex());
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
269. HSSFColor color = palette.getColor(colorIndex);
272.     short[] triplet = color.getTriplet();
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
{% highlight java %}
78. HSSFColor hssfColor = (HSSFColor) color;
79. short[] rgb = hssfColor.getTriplet();
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
{% highlight java %}
101. HSSFColor hssfColor = (HSSFColor) color;
102. short[] rgb = hssfColor.getTriplet();
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
{% highlight java %}
124. HSSFColor hssfColor = customPalette.getColor(c);
125. if (hssfColor == null || hssfColor.equals(HSSFColor.AUTOMATIC.getInstance())) {
128.   short[] rgb = hssfColor.getTriplet();
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/122565152/)
{% highlight java %}
130. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
140. return result.getIndex();
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
{% highlight java %}
29. private static final HSSFColor HSSF_AUTO = new HSSFColor.AUTOMATIC();
114.   if (index == HSSF_AUTO.getIndex() || color == null) {
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
{% highlight java %}
112. HSSFColor color = palette.getColor(index);
117.   short[] rgb = color.getTriplet();
{% endhighlight %}

***

### [HSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283811/)
{% highlight java %}
58. HSSFColor color = palette.getColor(index);
66.   short[] rgb = color.getTriplet();
{% endhighlight %}

***

### [HSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283811/)
{% highlight java %}
21. private static final HSSFColor HSSF_AUTO = new HSSFColor.AUTOMATIC();
60.   if (index == HSSF_AUTO.getIndex() || color == null)
{% endhighlight %}

***

