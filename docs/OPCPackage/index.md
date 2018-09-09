# OPCPackage

***

### [Cluster 1](./1)
{% highlight java %}
35. public static List<PackagePart> getCoreParts(OPCPackage pack) {
38.   for (PackageRelationship rel : pack.getRelationshipsByType(TKitRelationshipTypes.CORE_DOCUMENT))
40.       res.add(pack.getPart(PackagingURIHelper.createPartName(rel.getTargetURI())));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
154. OPCPackage opcPackage = OPCPackage.open(inputStream);
156. ArrayList<PackagePart> parts = opcPackage.getParts();
162. ArrayList<PackagePart> signatureParts = opcPackage
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
66. OPCPackage pack = part.getPackage();
67. return pack.getPart(PackagingURIHelper.createPartName(rels.getRelationship(0).getTargetURI()));
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
70. OPCPackage pkg = OPCPackage.open(input.getFile().toString());
74.    pkg.getRelationshipsByType(ExtractorFactory.CORE_DOCUMENT_REL);
80. PackagePart corePart = pkg.getPart(core.getRelationship(0));
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
49. private OPCPackage pack;
102.     pack.close();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
103. private OPCPackage pack;
228.     pack.close();
281.     if (pack.containPart(partName))  return null;
283.     part = pack.createPart(partName, contentType);
287.       pack.addRelationship(partName, TargetMode.INTERNAL, relationshipType);        
{% endhighlight %}

***

