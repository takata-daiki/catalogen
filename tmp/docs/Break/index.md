# Break

***

### [Cluster 1](./1)
{% highlight java %}
2749. PageBreakRecord.Break breakItem = (PageBreakRecord.Break)iterator.next();
2750. short breakLocation = breakItem.main;
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
1284. PageBreakRecord.Break breakItem = (PageBreakRecord.Break)iterator.next();
1285. returnValue[i++] = (int)breakItem.main;
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
279. Break original = (Break)iterator.next();
280. record.addBreak(original.main, original.subFrom, original.subTo);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
203. Break region = (Break)iterator.next();
205. retval.append("     .").append(mainLabel).append(" (zero-based) =").append(region.main).append("\n");
206. retval.append("     .").append(subLabel).append("From    =").append(region.subFrom).append("\n");
207. retval.append("     .").append(subLabel).append("To      =").append(region.subTo).append("\n");
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
228. Break region = (Break)BreakMap.get(key);
231.     region.main = main;
232.     region.subFrom = subFrom;
233.     region.subTo = subTo;
{% endhighlight %}

***

