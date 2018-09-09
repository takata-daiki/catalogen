# ListLevel

***

### [Cluster 1](./1)
{% highlight java %}
728. final ListLevel listLevel = listTables.getLevel( lfo.getLsid(),
732. if ( listLevel.getGrpprlPapx() != null )
736.             new SprmIterator( listLevel.getGrpprlPapx(), 0 ),
744.             new SprmIterator( listLevel.getGrpprlChpx(), 0 ),
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
74. ListLevel lvl = new ListLevel();
75. levelOffset += lvl.read( tableStream, levelOffset );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
217. ListLevel listLevel = _listData.getLevel( level );
223. listLevel.setNumberProperties( grpprl );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
109. ListLevel lvl = (ListLevel) obj;
110. return lvl._lvlf.equals( this._lvlf )
111.         && Arrays.equals( lvl._grpprlChpx, _grpprlChpx )
112.         && Arrays.equals( lvl._grpprlPapx, _grpprlPapx )
113.         && lvl._xst.equals( this._xst );
{% endhighlight %}

***

