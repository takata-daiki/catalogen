# SlidePersistAtom @Cluster 1

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
{% highlight java %}
693. SlidePersistAtom prev = null;
705.     if (prev.getSlideIdentifier() < spa.getSlideIdentifier()) {
715. sp.setSlideIdentifier(prev == null ? 256 : (prev.getSlideIdentifier() + 1));
{% endhighlight %}

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
{% highlight java %}
696. SlidePersistAtom spa = sas[j].getSlidePersistAtom();
697. if (spa.getSlideIdentifier() < 0) {
705.   if (prev.getSlideIdentifier() < spa.getSlideIdentifier()) {
{% endhighlight %}

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

