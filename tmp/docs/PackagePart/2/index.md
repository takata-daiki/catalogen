# PackagePart @Cluster 2

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
137.  PackagePart corePart = getCorePart(); 
138. return corePart.getRelatedPart(
139.   corePart.getRelationship(master.getId2())
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
159. PackagePart corePart = getCorePart(); 
160. return corePart.getRelatedPart(
161.    corePart.getRelationship(slide.getId2())
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
185. PackagePart slidePart = getSlidePart(parentSlide);
188.   notes = slidePart.getRelationshipsByType(XSLFRelation.NOTES.getRelation());
202.    return slidePart.getRelatedPart(notes.getRelationship(0));
{% endhighlight %}

***

### [XSLFImageRenderer.java](https://searchcode.com/codesearch/view/97406847/)
{% highlight java %}
109. public BufferedImage readImage(PackagePart packagePart) throws IOException {
110.     return ImageIO.read(packagePart.getInputStream());
{% endhighlight %}

***

