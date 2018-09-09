# PackageRelationship @Cluster 4

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
932. for (PackageRelationship rel : partRels) {
934.       .createPartName(PackagingURIHelper.resolvePartUri(rel
935.           .getSourceURI(), rel.getTargetURI()));
{% endhighlight %}

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
985. for (PackageRelationship relationship : partToDelete
989.           partName.getURI(), relationship.getTargetURI()));
{% endhighlight %}

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
{% highlight java %}
38. for (PackageRelationship rel : pack.getRelationshipsByType(TKitRelationshipTypes.CORE_DOCUMENT))
40.     res.add(pack.getPart(PackagingURIHelper.createPartName(rel.getTargetURI())));
{% endhighlight %}

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
{% highlight java %}
51. for (PackageRelationship rel : pack.getRelationshipsByType(TKitRelationshipTypes.CORE_DOCUMENT))
53.     res.add(pack.getPart(PackagingURIHelper.createPartName(rel.getTargetURI())));
{% endhighlight %}

***

