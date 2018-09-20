# PackageRelationshipCollection @Cluster 3

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
{% highlight java %}
63. PackageRelationshipCollection rels = part.getRelationshipsByType(relationshipType);
64. if (rels.size() == 0) return null;
67. return pack.getPart(PackagingURIHelper.createPartName(rels.getRelationship(0).getTargetURI()));
{% endhighlight %}

***

### [ZipContainerDetector.java](https://searchcode.com/codesearch/view/111785505/)
{% highlight java %}
73. PackageRelationshipCollection core = 
75. if(core.size() != 1) {
76.    throw new IOException("Invalid OOXML Package received - expected 1 core document, found " + core.size());
80. PackagePart corePart = pkg.getPart(core.getRelationship(0));
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
184. PackageRelationshipCollection notes;
193. if(notes.size() == 0) {
197. if(notes.size() > 1) {
198.   throw new IllegalStateException("Expecting 0 or 1 notes for a slide, but found " + notes.size());
202.    return slidePart.getRelatedPart(notes.getRelationship(0));
{% endhighlight %}

***

