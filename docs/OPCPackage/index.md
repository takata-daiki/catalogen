# OPCPackage

***

## [Cluster 1](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
103. private OPCPackage pack;
228.     pack.close();
281.     if (pack.containPart(partName))  return null;
283.     part = pack.createPart(partName, contentType);
287.       pack.addRelationship(partName, TargetMode.INTERNAL, relationshipType);        
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
277. private PackagePart createPart(OPCPackage pack, PackagePart corePart, String name, File file, String contentType, String relationshipType) {    
281.     if (pack.containPart(partName))  return null;
283.     part = pack.createPart(partName, contentType);
287.       pack.addRelationship(partName, TargetMode.INTERNAL, relationshipType);        
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> this comment could not be generated...
{% highlight java %}
66. OPCPackage pack = part.getPackage();
67. return pack.getPart(PackagingURIHelper.createPartName(rels.getRelationship(0).getTargetURI()));
{% endhighlight %}

***

## [Cluster 4](./4)
6 results
> test that we get the same value as excel and , for 
{% highlight java %}
48. private OPCPackage opcpkg;
100.       opcpkg.close();
{% endhighlight %}

***

