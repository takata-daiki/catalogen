# OPCPackage @Cluster 3

***

### [ZipContainerDetector.java](https://searchcode.com/codesearch/view/111785505/)
{% highlight java %}
70. OPCPackage pkg = OPCPackage.open(input.getFile().toString());
74.    pkg.getRelationshipsByType(ExtractorFactory.CORE_DOCUMENT_REL);
80. PackagePart corePart = pkg.getPart(core.getRelationship(0));
{% endhighlight %}

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

### [OOXMLSignatureVerifierTest.java](https://searchcode.com/codesearch/view/7982558/)
{% highlight java %}
154. OPCPackage opcPackage = OPCPackage.open(inputStream);
156. ArrayList<PackagePart> parts = opcPackage.getParts();
162. ArrayList<PackagePart> signatureParts = opcPackage
{% endhighlight %}

***

### [PoiWorkbook.java](https://searchcode.com/codesearch/view/95326019/)
{% highlight java %}
48. private OPCPackage opcpkg;
100.       opcpkg.close();
{% endhighlight %}

***

### [OPCPackageReader.java](https://searchcode.com/codesearch/view/401673/)
{% highlight java %}
49. private OPCPackage pack;
102.     pack.close();
{% endhighlight %}

***

