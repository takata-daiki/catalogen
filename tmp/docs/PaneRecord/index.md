# PaneRecord

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
170. PaneRecord rec = new PaneRecord();
172. rec.field_1_x = field_1_x;
173. rec.field_2_y = field_2_y;
174. rec.field_3_topRow = field_3_topRow;
175. rec.field_4_leftColumn = field_4_leftColumn;
176. rec.field_5_activePane = field_5_activePane;
{% endhighlight %}

***

