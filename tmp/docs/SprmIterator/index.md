# SprmIterator

***

### [Cluster 1](./1)
{% highlight java %}
105. SprmIterator sprmIt = new SprmIterator( grpprl, offset );
107. while ( sprmIt.hasNext() )
109.     SprmOperation sprm = sprmIt.next();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
216. SprmIterator iterator = new SprmIterator(grpprl,0);
217. while (iterator.hasNext())
219.   SprmOperation op = iterator.next();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
378. SprmIterator sprmIt = new SprmIterator( chpx.getGrpprl(), 0 );
379. while ( sprmIt.hasNext() )
381.     SprmOperation sprm = sprmIt.next();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
647. protected void dumpSprms( SprmIterator sprmIt, String linePrefix )
649.     while ( sprmIt.hasNext() )
651.         SprmOperation sprm = sprmIt.next();
{% endhighlight %}

***

