# OPCPackage @Cluster 1

***

### [XLIFFKitWriterStep.java](https://searchcode.com/codesearch/view/401675/)
{% highlight java %}
277. private PackagePart createPart(OPCPackage pack, PackagePart corePart, String name, File file, String contentType, String relationshipType) {    
281.     if (pack.containPart(partName))  return null;
283.     part = pack.createPart(partName, contentType);
287.       pack.addRelationship(partName, TargetMode.INTERNAL, relationshipType);        
{% endhighlight %}

***

