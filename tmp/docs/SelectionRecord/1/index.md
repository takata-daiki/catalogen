# SelectionRecord @Cluster 1 (0x0, retval, selection)

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> sets whether the rowcolheadings are shown in a viewer @ param show whether to show rowcolheadings or not 
{% highlight java %}
2463. SelectionRecord sel = (SelectionRecord) findFirstRecordBySid(SelectionRecord.sid);
2464. sel.setPane((byte)pane.getActivePane());
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> creates the 
{% highlight java %}
2033. SelectionRecord retval = new SelectionRecord();
2035. retval.setPane(( byte ) 0x3);
2036. retval.setActiveCellCol(( short ) 0x0);
2037. retval.setActiveCellRow(( short ) 0x0);
2038. retval.setNumRefs(( short ) 0x0);
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> test that we get the same value as excel and , for 
{% highlight java %}
108. protected SelectionRecord            selection         =     null;
2085.     return selection.getActiveCellRow();
2099.         selection.setActiveCellRow(row);
2115.     return selection.getActiveCellCol();
2129.         selection.setActiveCellCol(col);
{% endhighlight %}

***

