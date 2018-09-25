# PaneRecord @Cluster 1 (64, activepane, pane)

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> sets the auto numbering index of the handled paragraph @ param index the number of the first in the ' 0 ' a with 1 if the index is a value of the chart type . 
{% highlight java %}
2488. PaneRecord r = new PaneRecord();
2489. r.setX((short)xSplitPos);
2490. r.setY((short)ySplitPos);
2491. r.setTopRow((short) topRow);
2492. r.setLeftColumn((short) leftmostColumn);
2493. r.setActivePane((short) activePane);
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> creates a 
{% highlight java %}
2439. PaneRecord pane = new PaneRecord();
2440. pane.setX((short)colSplit);
2441. pane.setY((short)rowSplit);
2442. pane.setTopRow((short) topRow);
2443. pane.setLeftColumn((short) leftmostColumn);
2446.     pane.setTopRow((short)0);
2447.     pane.setActivePane((short)1);
2451.     pane.setLeftColumn((short)64);
2452.     pane.setActivePane((short)2);
2456.     pane.setActivePane((short)0);
2464. sel.setPane((byte)pane.getActivePane());
{% endhighlight %}

***

