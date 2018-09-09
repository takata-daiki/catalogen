# OPCPackage @Cluster 1

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
{% highlight java %}
35. public static List<PackagePart> getCoreParts(OPCPackage pack) {
38.   for (PackageRelationship rel : pack.getRelationshipsByType(TKitRelationshipTypes.CORE_DOCUMENT))
40.       res.add(pack.getPart(PackagingURIHelper.createPartName(rel.getTargetURI())));
{% endhighlight %}

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
{% highlight java %}
48. public static PackagePart getCorePart(OPCPackage pack) {
51.   for (PackageRelationship rel : pack.getRelationshipsByType(TKitRelationshipTypes.CORE_DOCUMENT))
53.       res.add(pack.getPart(PackagingURIHelper.createPartName(rel.getTargetURI())));
{% endhighlight %}

***

