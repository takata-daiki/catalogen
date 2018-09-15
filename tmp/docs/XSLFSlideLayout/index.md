# XSLFSlideLayout

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
204. public XSLFSlide createSlide(XSLFSlideLayout layout) {
223.     layout.copyLayout(slide);
224.     slide.addRelation(layout.getPackageRelationship().getId(), layout);
226.     PackagePartName ppName = layout.getPackagePart().getPartName();
228.             layout.getPackageRelationship().getRelationshipType());
{% endhighlight %}

***

