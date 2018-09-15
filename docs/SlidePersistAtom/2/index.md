# SlidePersistAtom @Cluster 2

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
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

