# PageBreakRecord

***

### [Cluster 1](./1)
{% highlight java %}
116. protected PageBreakRecord            rowBreaks         =     null;
2775.   rowBreaks.addBreak((short)row, fromCol, toCol);
2785.   rowBreaks.removeBreak((short)row);
2794.   return (rowBreaks == null) ? false : rowBreaks.getBreak((short)row) != null;
2855.   return rowBreaks.getBreaksIterator();
2863.   return (rowBreaks == null) ? 0 : (int)rowBreaks.getNumBreaks();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
276. PageBreakRecord record = new PageBreakRecord(getSid());      
280.    record.addBreak(original.main, original.subFrom, original.subTo);
{% endhighlight %}

***

