# XSLFSlideLayout @Cluster 1

***

### [XMLSlideShow.java](https://searchcode.com/codesearch/view/97406883/)
{% highlight java %}
204. public XSLFSlide createSlide(XSLFSlideLayout layout) {
223.     layout.copyLayout(slide);
224.     slide.addRelation(layout.getPackageRelationship().getId(), layout);
226.     PackagePartName ppName = layout.getPackagePart().getPartName();
228.             layout.getPackageRelationship().getRelationshipType());
{% endhighlight %}

***

