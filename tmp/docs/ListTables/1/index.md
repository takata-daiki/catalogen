# ListTables @Cluster 1

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
715. protected void dumpParagraphLevels( ListTables listTables,
720.         final LFO lfo = listTables.getLfo( paragraph.getIlfo() );
723.         final LFOData lfoData = listTables.getLfoData( paragraph.getIlfo() );
728.             final ListLevel listLevel = listTables.getLevel( lfo.getLsid(),
{% endhighlight %}

***

### [HWPFList.java](https://searchcode.com/codesearch/view/97384433/)
{% highlight java %}
83. public HWPFList( StyleSheet styleSheet, ListTables listTables, int ilfo )
96.         _lfo = listTables.getLfo( ilfo );
97.         _lfoData = listTables.getLfoData( ilfo );
102.         _lfo = listTables.getLfo( nilfo );
103.         _lfoData = listTables.getLfoData( nilfo );
111.     _listData = listTables.getListData( _lfo.getLsid() );
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
102. ListTables listTables = doc.getListTables();
118.         lfo = listTables.getLfo( properties.getIlfo() );
127.         final ListLevel listLevel = listTables.getLevel( lfo.getLsid(),
{% endhighlight %}

***

