# ListData @Cluster 1

***

### [HWPFList.java](https://searchcode.com/codesearch/view/97384433/)
{% highlight java %}
60. private ListData _listData;
78.     _lfo.setLsid( _listData.getLsid() );
140.     if ( level >= _listData.numLevels() )
145.                 + _listData.numLevels() + ")" );
147.     ListLevel lvl = _listData.getLevels()[level];
217.     ListLevel listLevel = _listData.getLevel( level );
218.     int styleIndex = _listData.getLevelStyle( level );
247.     _listData.setLevelStyle( level, styleIndex );
{% endhighlight %}

***

### [ListTables.java](https://searchcode.com/codesearch/view/97384153/)
{% highlight java %}
194. public int addList( ListData lst, LFO lfo, LFOData lfoData )
196.     int lsid = lst.getLsid();
199.         lsid = lst.resetListID();
{% endhighlight %}

***

### [ListTables.java](https://searchcode.com/codesearch/view/97384153/)
{% highlight java %}
99. ListData lst = _listMap.get(x);
100. tableStream.write(lst.toByteArray());
101. ListLevel[] lvls = lst.getLevels();
{% endhighlight %}

***

### [ListTables.java](https://searchcode.com/codesearch/view/97384153/)
{% highlight java %}
141.   ListData lst = _listMap.get(Integer.valueOf(lsid));
142.   if(level < lst.numLevels()) {
143.     ListLevel lvl = lst.getLevels()[level];
146. log.log(POILogger.WARN, "Requested level " + level + " which was greater than the maximum defined (" + lst.numLevels() + ")");
{% endhighlight %}

***

