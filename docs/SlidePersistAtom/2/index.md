# SlidePersistAtom @Cluster 2 (getslideidentifier, if, spa)

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
> called by slideshow from the specified slide 
{% highlight java %}
693. SlidePersistAtom prev = null;
705.     if (prev.getSlideIdentifier() < spa.getSlideIdentifier()) {
715. sp.setSlideIdentifier(prev == null ? 256 : (prev.getSlideIdentifier() + 1));
{% endhighlight %}

***

