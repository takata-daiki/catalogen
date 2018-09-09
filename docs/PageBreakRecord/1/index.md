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

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
117. protected PageBreakRecord            colBreaks         =     null;
2807.   colBreaks.addBreak(column, fromRow, toRow);
2818.   colBreaks.removeBreak(column);
2827.   return (colBreaks == null) ? false : colBreaks.getBreak(column) != null;
2871.   return colBreaks.getBreaksIterator();
2879.   return (colBreaks == null) ? 0 : (int)colBreaks.getNumBreaks();
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
2741. public void shiftBreaks(PageBreakRecord breaks, short start, short stop, int count) {
2745.   Iterator iterator = breaks.getBreaksIterator();
2760.     breaks.removeBreak(breakItem.main);
2761.     breaks.addBreak((short)(breakItem.main+count), breakItem.subFrom, breakItem.subTo);
{% endhighlight %}

***

