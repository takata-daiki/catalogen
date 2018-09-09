# PackagePartName

***

### [Cluster 1](./1)
{% highlight java %}
566. PackagePartName partName = part.getPartName();
567. String name = partName.getName();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
865. public void removePart(PackagePartName partName) {
884.   if (partName.isRelationshipPartURI()) {
886.         .getSourcePartUriFromRelationshipPartUri(partName.getURI());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
976. public void deletePartRecursive(PackagePartName partName) {
989.               partName.getURI(), relationship.getTargetURI()));
994.         + partName.getName()
{% endhighlight %}

***

