# ListTables

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
102. ListTables listTables = doc.getListTables();
118.         lfo = listTables.getLfo( properties.getIlfo() );
127.         final ListLevel listLevel = listTables.getLevel( lfo.getLsid(),
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
176. ListTables other = (ListTables) obj;
179.     if ( other._listMap != null )
182. else if ( !_listMap.equals( other._listMap ) )
186.     if ( other._plfLfo != null )
189. else if ( !_plfLfo.equals( other._plfLfo ) )
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
83. public HWPFList( StyleSheet styleSheet, ListTables listTables, int ilfo )
96.         _lfo = listTables.getLfo( ilfo );
97.         _lfoData = listTables.getLfoData( ilfo );
102.         _lfo = listTables.getLfo( nilfo );
103.         _lfoData = listTables.getLfoData( nilfo );
111.     _listData = listTables.getListData( _lfo.getLsid() );
{% endhighlight %}

***

