# OPCPackage

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
277. private PackagePart createPart(OPCPackage pack, PackagePart corePart, String name, File file, String contentType, String relationshipType) {    
281.     if (pack.containPart(partName))  return null;
283.     part = pack.createPart(partName, contentType);
287.       pack.addRelationship(partName, TargetMode.INTERNAL, relationshipType);        
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
66. OPCPackage pack = part.getPackage();
67. return pack.getPart(PackagingURIHelper.createPartName(rels.getRelationship(0).getTargetURI()));
{% endhighlight %}

***

## [Cluster 3](./3)
6 results
> code comments is here.
{% highlight java %}
70. OPCPackage pkg = OPCPackage.open(input.getFile().toString());
74.    pkg.getRelationshipsByType(ExtractorFactory.CORE_DOCUMENT_REL);
80. PackagePart corePart = pkg.getPart(core.getRelationship(0));
{% endhighlight %}

***

