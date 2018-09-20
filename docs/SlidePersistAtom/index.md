# SlidePersistAtom

***

## [Cluster 1](./1)
3 results
> create an array of records from an input stream @ param in the stream to which to the records @ param length the number of bytes to be written @ exception ioexception if an i / o error occurs 
{% highlight java %}
353. SlidePersistAtom spa = notesSets[i].getSlidePersistAtom();
354. Integer slideId = Integer.valueOf(spa.getSlideIdentifier());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> called by slideshow from the specified slide 
{% highlight java %}
693. SlidePersistAtom prev = null;
705.     if (prev.getSlideIdentifier() < spa.getSlideIdentifier()) {
715. sp.setSlideIdentifier(prev == null ? 256 : (prev.getSlideIdentifier() + 1));
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> this comment could not be generated...
{% highlight java %}
712. SlidePersistAtom sp = new SlidePersistAtom();
715. sp.setSlideIdentifier(prev == null ? 256 : (prev.getSlideIdentifier() + 1));
721. Slide slide = new Slide(sp.getSlideIdentifier(), sp.getRefID(), _slides.length + 1);
730. logger.log(POILogger.INFO, "Added slide " + _slides.length + " with ref " + sp.getRefID()
731.     + " and identifier " + sp.getSlideIdentifier());
768. sp.setRefID(psrId);
779. ptr.addSlideLookup(sp.getRefID(), slideOffset);
{% endhighlight %}

***

