# PageBreakRecord @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
116. protected PageBreakRecord            rowBreaks         =     null;
2775.   rowBreaks.addBreak((short)row, fromCol, toCol);
2785.   rowBreaks.removeBreak((short)row);
2794.   return (rowBreaks == null) ? false : rowBreaks.getBreak((short)row) != null;
2855.   return rowBreaks.getBreaksIterator();
2863.   return (rowBreaks == null) ? 0 : (int)rowBreaks.getNumBreaks();
{% endhighlight %}

***

