# PackagePart @Cluster 10

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
85.  PackagePart corePart = getCorePart();
86. PackagePart slidePart = corePart.getRelatedPart(
87.       corePart.getRelationship(ctSlide.getId2()));
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
151. PackagePart masterPart = getSlideMasterPart(master);
153.   SldMasterDocument.Factory.parse(masterPart.getInputStream());
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
173. PackagePart slidePart = getSlidePart(slide);
175.   SldDocument.Factory.parse(slidePart.getInputStream());
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
213. PackagePart notesPart = getNodesPart(slide);
218.   NotesDocument.Factory.parse(notesPart.getInputStream());
{% endhighlight %}

***

