# SlidePersistAtom

***

## [Cluster 1 (integer, slide, sp)](./1)
3 results
> create an array of records from an input stream @ param in the stream to which to the records @ param length the number of bytes to be written @ exception ioexception if an i / o error occurs 
{% highlight java %}
353. SlidePersistAtom spa = notesSets[i].getSlidePersistAtom();
354. Integer slideId = Integer.valueOf(spa.getSlideIdentifier());
{% endhighlight %}

***

## [Cluster 2 (getslideidentifier, if, spa)](./2)
1 results
> called by slideshow from the specified slide 
{% highlight java %}
693. SlidePersistAtom prev = null;
705.     if (prev.getSlideIdentifier() < spa.getSlideIdentifier()) {
715. sp.setSlideIdentifier(prev == null ? 256 : (prev.getSlideIdentifier() + 1));
{% endhighlight %}

***

