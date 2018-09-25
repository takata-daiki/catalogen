# PropertySet @Cluster 1 (back, int, warn)

***

### [POIDocument.java](https://searchcode.com/codesearch/view/97383067/)
> @ throws nullpointerexception if cell 1 is null ( fixed position ) @ see org . apache . poi . ss . usermodel . clientanchor # or ( short ) 
{% highlight java %}
123. PropertySet ps;
130.   logger.log(POILogger.WARN, "DocumentSummaryInformation property set came back with wrong class - ", ps.getClass());
138.   logger.log(POILogger.WARN, "SummaryInformation property set came back with wrong class - ", ps.getClass());
{% endhighlight %}

***

### [PropertySet.java](https://searchcode.com/codesearch/view/15642677/)
> get the document part that ' s defined as the given relationship of the given property . 
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

