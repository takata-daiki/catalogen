# ListTables

***

### [Cluster 1](./1)
{% highlight java %}
715. protected void dumpParagraphLevels( ListTables listTables,
720.         final LFO lfo = listTables.getLfo( paragraph.getIlfo() );
723.         final LFOData lfoData = listTables.getLfoData( paragraph.getIlfo() );
728.             final ListLevel listLevel = listTables.getLevel( lfo.getLsid(),
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
176. ListTables other = (ListTables) obj;
179.     if ( other._listMap != null )
182. else if ( !_listMap.equals( other._listMap ) )
186.     if ( other._plfLfo != null )
189. else if ( !_plfLfo.equals( other._plfLfo ) )
{% endhighlight %}

***

