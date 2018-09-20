# PropertySet @Cluster 1

***

### [POIDocument.java](https://searchcode.com/codesearch/view/97383067/)
{% highlight java %}
123. PropertySet ps;
130.   logger.log(POILogger.WARN, "DocumentSummaryInformation property set came back with wrong class - ", ps.getClass());
138.   logger.log(POILogger.WARN, "SummaryInformation property set came back with wrong class - ", ps.getClass());
{% endhighlight %}

***

### [PropertySet.java](https://searchcode.com/codesearch/view/15642677/)
{% highlight java %}
660. final PropertySet ps = (PropertySet) o;
661. int byteOrder1 = ps.getByteOrder();
663. ClassID classID1 = ps.getClassID();
665. int format1 = ps.getFormat();
667. int osVersion1 = ps.getOSVersion();
669. int sectionCount1 = ps.getSectionCount();
679. return Util.equals(getSections(), ps.getSections());
{% endhighlight %}

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

