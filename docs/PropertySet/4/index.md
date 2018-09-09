# PropertySet @Cluster 4

***

### [MutablePropertySet.java](https://searchcode.com/codesearch/view/15642695/)
{% highlight java %}
112. public MutablePropertySet(final PropertySet ps)
114.     byteOrder = ps.getByteOrder();
115.     format = ps.getFormat();
116.     osVersion = ps.getOSVersion();
117.     setClassID(ps.getClassID());
119.     for (final Iterator i = ps.getSections().iterator(); i.hasNext();)
{% endhighlight %}

***

