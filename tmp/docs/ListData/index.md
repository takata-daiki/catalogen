# ListData

***

### [Cluster 1](./1)
{% highlight java %}
141.   ListData lst = _listMap.get(Integer.valueOf(lsid));
142.   if(level < lst.numLevels()) {
143.     ListLevel lvl = lst.getLevels()[level];
146. log.log(POILogger.WARN, "Requested level " + level + " which was greater than the maximum defined (" + lst.numLevels() + ")");
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
67. ListData lst = new ListData( tableStream, offset );
68. _listMap.put( Integer.valueOf( lst.getLsid() ), lst );
71. int num = lst.numLevels();
76.     lst.setLevel( y, lvl );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
99. ListData lst = _listMap.get(x);
100. tableStream.write(lst.toByteArray());
101. ListLevel[] lvls = lst.getLevels();
{% endhighlight %}

***

