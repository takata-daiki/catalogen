# XSLFShape

***

### [Cluster 1](./1)
{% highlight java %}
310. for (XSLFShape shape : getShapes()) {
315.     shape.applyTransform(graphics);
316.   shape.draw(graphics);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
162. public boolean removeShape(XSLFShape xShape) {
163.     XmlObject obj = xShape.getXmlObject();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
335. XSLFShape s2 = tgtShapes[i];
337. s2.copy(s1);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
177. void copy(XSLFShape sh) {
180.                 "Can't copy " + sh.getClass().getSimpleName() + " into " + getClass().getSimpleName());
183.     setAnchor(sh.getAnchor());
{% endhighlight %}

***

