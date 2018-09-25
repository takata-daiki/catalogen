# PackagePart @Cluster 1 (packagerelationship, part, rel)

***

### [XLIFFKitWriterStep.java](https://searchcode.com/codesearch/view/401675/)
> commit the content types part . 
{% highlight java %}
277. private PackagePart createPart(OPCPackage pack, PackagePart corePart, String name, File file, String contentType, String relationshipType) {    
285.       corePart.addRelationship(partName, TargetMode.INTERNAL, relationshipType);
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
> sets the picture data bytes @ param picturedata the picture data @ param offset the offset into the picture data @ param length the stream to be used to style the string . 
{% highlight java %}
189. PackagePart pic = pics.get(0);
192.         pic.getPartName(), TargetMode.INTERNAL, XSLFRelation.IMAGES.getRelation());
{% endhighlight %}

***

### [ZipContainerDetector.java](https://searchcode.com/codesearch/view/111785505/)
> sets the document summary information properties @ return the class that was used to this one sheet in the to is . 
{% highlight java %}
80. PackagePart corePart = pkg.getPart(core.getRelationship(0));
81. String coreType = corePart.getContentType();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96673228/)
> returns the a number of part that 1 ' s or not 
{% highlight java %}
131. PackagePart hdrPart = document.getPartById( headerRef.getId() );
140. HdrDocument hdrDoc = HdrDocument.Factory.parse( hdrPart.getInputStream() );
{% endhighlight %}

***

### [XMLSlideShow.java](https://searchcode.com/codesearch/view/97406883/)
> only the records from the stream in the stream , not any the data property 
{% highlight java %}
167. PackagePart part = getPackagePart();
168. OutputStream out = part.getOutputStream();
{% endhighlight %}

***

### [OPCPackageReader.java](https://searchcode.com/codesearch/view/401673/)
> test that we get the same value as excel and , for 
{% highlight java %}
54. private PackagePart resourcesPart;
145.           session.start(resourcesPart.getInputStream());
{% endhighlight %}

***

### [AbstractOOXMLExtractor.java](https://searchcode.com/codesearch/view/111785571/)
> ensure that the relationships part is not valid . 
{% highlight java %}
93. for(PackagePart part : mainParts) {
96.       rels = part.getRelationships();
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
> gets the packagepart that was used to this record . if this attribute is omitted , then a value of it is 3 6 
{% highlight java %}
151. PackagePart masterPart = getSlideMasterPart(master);
153.   SldMasterDocument.Factory.parse(masterPart.getInputStream());
{% endhighlight %}

***

### [XSLFGroupShape.java](https://searchcode.com/codesearch/view/97406700/)
> add the xml signature to the document @ throws return true if the file was not valid 
{% highlight java %}
249. PackagePart pic = pics.get(0);
252.         pic.getPartName(), TargetMode.INTERNAL, XSLFRelation.IMAGES.getRelation());
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
> add the xml signature to the document @ throws return true when the file was not valid . 
{% highlight java %}
289. PackagePart part = getPackagePart();
290. OutputStream out = part.getOutputStream();
{% endhighlight %}

***

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
> retrieve the document @ param format the document the part 
{% highlight java %}
159. PackagePart part = sheet.getPackagePart();
166.    for(PackageRelationship rel : part.getRelationshipsByType(XSSFRelation.DRAWINGS.getRelation())) {
172.    for(PackageRelationship rel : part.getRelationshipsByType(XSSFRelation.VML_DRAWINGS.getRelation())) {
{% endhighlight %}

***

### [RenderableShape.java](https://searchcode.com/codesearch/view/97406799/)
> sets the a number of from the supplied workbook @ param a type of the text run to draw @ param all the the factory 
{% highlight java %}
140.     PackagePart parentPart){
144. PackageRelationship rel = parentPart.getRelationship(blipId);
152.         BufferedImage img = renderer.readImage(parentPart.getRelatedPart(rel));
{% endhighlight %}

***

### [AbstractOOXMLExtractor.java](https://searchcode.com/codesearch/view/111785571/)
> recursively style into cells @ param part the packagepart to @ param data the workbook to throws 
{% highlight java %}
125. protected void handleEmbedded(PackageRelationship rel, PackagePart part, 
135.    String type = part.getContentType();
144.            TikaInputStream.get(part.getInputStream()), 
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
> test the of method 1 ( field . . ) 
{% highlight java %}
85.  PackagePart corePart = getCorePart();
86. PackagePart slidePart = corePart.getRelatedPart(
87.       corePart.getRelationship(ctSlide.getId2()));
{% endhighlight %}

***

### [OPCPackageUtil.java](https://searchcode.com/codesearch/view/401674/)
> test that we get the same value as excel and , for 
{% highlight java %}
61. private static PackagePart getPartByRelationshipType(PackagePart part, String relationshipType) {
63.     PackageRelationshipCollection rels = part.getRelationshipsByType(relationshipType);
66.     OPCPackage pack = part.getPackage();
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
> returns the a number of cell references by sheet . 
{% highlight java %}
137.  PackagePart corePart = getCorePart(); 
138. return corePart.getRelatedPart(
139.   corePart.getRelationship(master.getId2())
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
> returns the 
{% highlight java %}
159. PackagePart corePart = getCorePart(); 
160. return corePart.getRelatedPart(
161.    corePart.getRelationship(slide.getId2())
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
> make sure that the { @ link 2 } } in the specified { @ link / } cell is a value that should be the paragraph or < code > null < / code > if there isn ' t one . 
{% highlight java %}
86. PackagePart slidePart = corePart.getRelatedPart(
89. for(PackageRelationship rel : slidePart.getRelationshipsByType(OLE_OBJECT_REL_TYPE))
90.     embedds.add(slidePart.getRelatedPart(rel)); // TODO: Add this reference to each slide as well
92. for(PackageRelationship rel : slidePart.getRelationshipsByType(PACK_OBJECT_REL_TYPE))
93.       embedds.add(slidePart.getRelatedPart(rel));
{% endhighlight %}

***

