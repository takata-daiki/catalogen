# PackagePart

***

### [Cluster 1](./1)
{% highlight java %}
80. PackagePart corePart = pkg.getPart(core.getRelationship(0));
81. String coreType = corePart.getContentType();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
137.  PackagePart corePart = getCorePart(); 
138. return corePart.getRelatedPart(
139.   corePart.getRelationship(master.getId2())
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
125. protected void handleEmbedded(PackageRelationship rel, PackagePart part, 
135.    String type = part.getContentType();
144.            TikaInputStream.get(part.getInputStream()), 
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
53. private PackagePart activePart;
158.         xliffReader.open(new RawDocument(activePart.getInputStream(), "UTF-8", srcLoc, srcLoc));
161.             activePart.getPartName().getName()), e);
246.   String partName = activePart.getPartName().toString();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
93. for(PackagePart part : mainParts) {
96.       rels = part.getRelationships();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
159. PackagePart part = sheet.getPackagePart();
166.    for(PackageRelationship rel : part.getRelationshipsByType(XSSFRelation.DRAWINGS.getRelation())) {
172.    for(PackageRelationship rel : part.getRelationshipsByType(XSSFRelation.VML_DRAWINGS.getRelation())) {
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
106. PackagePart p = getSheet().getPackagePart();
107. PackageRelationship rel = p.getRelationship(blipId);
110.         PackagePart imgPart = p.getRelatedPart(rel);
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
289. PackagePart part = getPackagePart();
290. OutputStream out = part.getOutputStream();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
1183. PackagePart hdrPart = document.getPartById( headerRef.getId() );
1194. HdrDocument hdrDoc = HdrDocument.Factory.parse( hdrPart.getInputStream() );
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
85.  PackagePart corePart = getCorePart();
86. PackagePart slidePart = corePart.getRelatedPart(
87.       corePart.getRelationship(ctSlide.getId2()));
{% endhighlight %}

***

### [Cluster 11](./11)
{% highlight java %}
547. PackagePart pic = ppt.getAllPictures().get(pictureIdx).getPackagePart();
550.         pic.getPartName(), TargetMode.INTERNAL, blipRel.getRelationshipType());
{% endhighlight %}

***

### [Cluster 12](./12)
{% highlight java %}
278. PackagePart part = null;
291.     OutputStream os = part.getOutputStream(); 
{% endhighlight %}

***

