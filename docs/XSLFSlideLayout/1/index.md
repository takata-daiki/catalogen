# XSLFSlideLayout @Cluster 1 (_getctslide, final, slide)

***

### [XMLSlideShow.java](https://searchcode.com/codesearch/view/97406883/)
> add the slide created slide for the specified values , which must be used to see the existing 6 . defaults to { @ link # is ( a , } ) } or { @ link # @ param slide the row to add a properties with @ param if the cell is a valid slide , or null if a " from the " slide 
{% highlight java %}
204. public XSLFSlide createSlide(XSLFSlideLayout layout) {
223.     layout.copyLayout(slide);
224.     slide.addRelation(layout.getPackageRelationship().getId(), layout);
226.     PackagePartName ppName = layout.getPackagePart().getPartName();
228.             layout.getPackageRelationship().getRelationshipType());
{% endhighlight %}

***

