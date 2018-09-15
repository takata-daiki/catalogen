# PackagePartName @Cluster 3

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
865. public void removePart(PackagePartName partName) {
884.   if (partName.isRelationshipPartURI()) {
886.         .getSourcePartUriFromRelationshipPartUri(partName.getURI());
{% endhighlight %}

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
887. PackagePartName sourcePartName;
897. if (sourcePartName.getURI().equals(
{% endhighlight %}

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
1042. public PackageRelationship addRelationship(PackagePartName targetPartName,
1063.   if (targetPartName.isRelationshipPartURI()) {
1072.       targetPartName.getURI(), targetMode, relationshipType, relID);
{% endhighlight %}

***

