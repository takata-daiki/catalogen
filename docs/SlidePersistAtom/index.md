# SlidePersistAtom

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
693. SlidePersistAtom prev = null;
705.     if (prev.getSlideIdentifier() < spa.getSlideIdentifier()) {
715. sp.setSlideIdentifier(prev == null ? 256 : (prev.getSlideIdentifier() + 1));
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
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

