# SlidePersistAtom

***

### [Cluster 1](./1)
{% highlight java %}
693. SlidePersistAtom prev = null;
705.     if (prev.getSlideIdentifier() < spa.getSlideIdentifier()) {
715. sp.setSlideIdentifier(prev == null ? 256 : (prev.getSlideIdentifier() + 1));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
235. SlidePersistAtom spa = sas.getSlidePersistAtom();
236. int refID = spa.getRefID();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
353. SlidePersistAtom spa = notesSets[i].getSlidePersistAtom();
354. Integer slideId = Integer.valueOf(spa.getSlideIdentifier());
{% endhighlight %}

***

