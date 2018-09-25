# LFO

***

## [Cluster 1 (final, lfo, listlevel)](./1)
1 results
> sets the 
{% highlight java %}
720. final LFO lfo = listTables.getLfo( paragraph.getIlfo() );
728.     final ListLevel listLevel = listTables.getLevel( lfo.getLsid(),
{% endhighlight %}

***

## [Cluster 2 (_lfo, _listdata, getlsid)](./2)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
58. private LFO _lfo;
78.     _lfo.setLsid( _listData.getLsid() );
111.     _listData = listTables.getListData( _lfo.getLsid() );
134.     return _lfo.getLsid();
{% endhighlight %}

***

