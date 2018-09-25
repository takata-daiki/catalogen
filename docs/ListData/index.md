# ListData

***

## [Cluster 1 (_listdata, int, log)](./1)
3 results
> sets the 
{% highlight java %}
99. ListData lst = _listMap.get(x);
100. tableStream.write(lst.toByteArray());
101. ListLevel[] lvls = lst.getLevels();
{% endhighlight %}

***

## [Cluster 2 (_listmap, listdata, lst)](./2)
1 results
> add a new excel record to this < code > ppdrawing < / code > . 
{% highlight java %}
67. ListData lst = new ListData( tableStream, offset );
68. _listMap.put( Integer.valueOf( lst.getLsid() ), lst );
71. int num = lst.numLevels();
76.     lst.setLevel( y, lvl );
{% endhighlight %}

***

