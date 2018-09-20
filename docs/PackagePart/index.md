# PackagePart

***

## [Cluster 1](./1)
31 results
> commit the content types part . 
{% highlight java %}
277. private PackagePart createPart(OPCPackage pack, PackagePart corePart, String name, File file, String contentType, String relationshipType) {    
285.       corePart.addRelationship(partName, TargetMode.INTERNAL, relationshipType);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
53. private PackagePart activePart;
158.         xliffReader.open(new RawDocument(activePart.getInputStream(), "UTF-8", srcLoc, srcLoc));
161.             activePart.getPartName().getName()), e);
246.   String partName = activePart.getPartName().toString();
{% endhighlight %}

***

