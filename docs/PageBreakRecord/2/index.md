# PageBreakRecord @Cluster 2

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
2741. public void shiftBreaks(PageBreakRecord breaks, short start, short stop, int count) {
2745.   Iterator iterator = breaks.getBreaksIterator();
2760.     breaks.removeBreak(breakItem.main);
2761.     breaks.addBreak((short)(breakItem.main+count), breakItem.subFrom, breakItem.subTo);
{% endhighlight %}

***

