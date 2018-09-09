# HSSFColor

***

### [Cluster 1](./1)
{% highlight java %}
207. HSSFColor col = translateColour(colour);
214.     logger.debug("Cell colour changed to "+col.getHexString()+"with index: "+col.getIndex());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
227. HSSFColor col = translateColour(colour);
230. style.setFillForegroundColor(col.getIndex());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
29. private static final HSSFColor HSSF_AUTO = new HSSFColor.AUTOMATIC();
114.   if (index == HSSF_AUTO.getIndex() || color == null) {
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
112. HSSFColor color = palette.getColor(index);
117.   short[] rgb = color.getTriplet();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
124. HSSFColor hssfColor = customPalette.getColor(c);
125. if (hssfColor == null || hssfColor.equals(HSSFColor.AUTOMATIC.getInstance())) {
128.   short[] rgb = hssfColor.getTriplet();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
130. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
140. return result.getIndex();
{% endhighlight %}

***

