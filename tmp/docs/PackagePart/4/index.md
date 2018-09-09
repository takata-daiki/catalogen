# PackagePart @Cluster 4

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
{% highlight java %}
61. private static PackagePart getPartByRelationshipType(PackagePart part, String relationshipType) {
63.     PackageRelationshipCollection rels = part.getRelationshipsByType(relationshipType);
66.     OPCPackage pack = part.getPackage();
{% endhighlight %}

***

### [XLIFFKitWriterStep.java](https://searchcode.com/codesearch/view/401675/)
{% highlight java %}
277. private PackagePart createPart(OPCPackage pack, PackagePart corePart, String name, File file, String contentType, String relationshipType) {    
285.       corePart.addRelationship(partName, TargetMode.INTERNAL, relationshipType);
{% endhighlight %}

***

### [OPCPackageReader.java](https://searchcode.com/codesearch/view/401673/)
{% highlight java %}
53. private PackagePart activePart;
158.         xliffReader.open(new RawDocument(activePart.getInputStream(), "UTF-8", srcLoc, srcLoc));
161.             activePart.getPartName().getName()), e);
246.   String partName = activePart.getPartName().toString();
{% endhighlight %}

***

### [OPCPackageReader.java](https://searchcode.com/codesearch/view/401673/)
{% highlight java %}
54. private PackagePart resourcesPart;
145.           session.start(resourcesPart.getInputStream());
{% endhighlight %}

***

