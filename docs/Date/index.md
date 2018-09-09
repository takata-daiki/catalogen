# Date

***

### [Cluster 1](./1)
{% highlight java %}
137. private void set(Property property, Date value) {
139.         metadata.set(property, value.toString());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
143. private void set(String name, Date value) {
145.         metadata.set(name, value.toString());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
332. public static long dateToFileTime(final Date date) {
333.   final long ms_since_19700101 = date.getTime();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
361. public SYSTEMTIME(Date date) {
362.     this(date.getTime());
{% endhighlight %}

***

