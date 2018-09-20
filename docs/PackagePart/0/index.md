# PackagePart @Cluster 1

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
173. PackagePart slidePart = getSlidePart(slide);
175.   SldDocument.Factory.parse(slidePart.getInputStream());
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
151. PackagePart masterPart = getSlideMasterPart(master);
153.   SldMasterDocument.Factory.parse(masterPart.getInputStream());
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
213. PackagePart notesPart = getNodesPart(slide);
218.   NotesDocument.Factory.parse(notesPart.getInputStream());
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
185. PackagePart slidePart = getSlidePart(parentSlide);
188.   notes = slidePart.getRelationshipsByType(XSLFRelation.NOTES.getRelation());
202.    return slidePart.getRelatedPart(notes.getRelationship(0));
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
85.  PackagePart corePart = getCorePart();
86. PackagePart slidePart = corePart.getRelatedPart(
87.       corePart.getRelationship(ctSlide.getId2()));
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
137.  PackagePart corePart = getCorePart(); 
138. return corePart.getRelatedPart(
139.   corePart.getRelationship(master.getId2())
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
159. PackagePart corePart = getCorePart(); 
160. return corePart.getRelatedPart(
161.    corePart.getRelationship(slide.getId2())
{% endhighlight %}

***

### [XSLFSlideShow.java](https://searchcode.com/codesearch/view/97406428/)
{% highlight java %}
86. PackagePart slidePart = corePart.getRelatedPart(
89. for(PackageRelationship rel : slidePart.getRelationshipsByType(OLE_OBJECT_REL_TYPE))
90.     embedds.add(slidePart.getRelatedPart(rel)); // TODO: Add this reference to each slide as well
92. for(PackageRelationship rel : slidePart.getRelationshipsByType(PACK_OBJECT_REL_TYPE))
93.       embedds.add(slidePart.getRelatedPart(rel));
{% endhighlight %}

***

### [AbstractOOXMLExtractor.java](https://searchcode.com/codesearch/view/111785571/)
{% highlight java %}
93. for(PackagePart part : mainParts) {
96.       rels = part.getRelationships();
{% endhighlight %}

***

### [AbstractOOXMLExtractor.java](https://searchcode.com/codesearch/view/111785571/)
{% highlight java %}
125. protected void handleEmbedded(PackageRelationship rel, PackagePart part, 
135.    String type = part.getContentType();
144.            TikaInputStream.get(part.getInputStream()), 
{% endhighlight %}

***

### [XMLSlideShow.java](https://searchcode.com/codesearch/view/97406883/)
{% highlight java %}
167. PackagePart part = getPackagePart();
168. OutputStream out = part.getOutputStream();
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
189. PackagePart pic = pics.get(0);
192.         pic.getPartName(), TargetMode.INTERNAL, XSLFRelation.IMAGES.getRelation());
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
547. PackagePart pic = ppt.getAllPictures().get(pictureIdx).getPackagePart();
550.         pic.getPartName(), TargetMode.INTERNAL, blipRel.getRelationshipType());
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
289. PackagePart part = getPackagePart();
290. OutputStream out = part.getOutputStream();
{% endhighlight %}

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
{% highlight java %}
535. String importBlip(String blipId, PackagePart packagePart) {
536.     PackageRelationship blipRel = packagePart.getRelationship(blipId);
539.         blipPart = packagePart.getRelatedPart(blipRel);
{% endhighlight %}

***

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
{% highlight java %}
159. PackagePart part = sheet.getPackagePart();
166.    for(PackageRelationship rel : part.getRelationshipsByType(XSSFRelation.DRAWINGS.getRelation())) {
172.    for(PackageRelationship rel : part.getRelationshipsByType(XSSFRelation.VML_DRAWINGS.getRelation())) {
{% endhighlight %}

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

### [XLIFFKitWriterStep.java](https://searchcode.com/codesearch/view/401675/)
{% highlight java %}
278. PackagePart part = null;
291.     OutputStream os = part.getOutputStream(); 
{% endhighlight %}

***

### [XSLFGroupShape.java](https://searchcode.com/codesearch/view/97406700/)
{% highlight java %}
249. PackagePart pic = pics.get(0);
252.         pic.getPartName(), TargetMode.INTERNAL, XSLFRelation.IMAGES.getRelation());
{% endhighlight %}

***

### [RenderableShape.java](https://searchcode.com/codesearch/view/97406799/)
{% highlight java %}
140.     PackagePart parentPart){
144. PackageRelationship rel = parentPart.getRelationship(blipId);
152.         BufferedImage img = renderer.readImage(parentPart.getRelatedPart(rel));
{% endhighlight %}

***

### [ZipContainerDetector.java](https://searchcode.com/codesearch/view/111785505/)
{% highlight java %}
80. PackagePart corePart = pkg.getPart(core.getRelationship(0));
81. String coreType = corePart.getContentType();
{% endhighlight %}

***

### [XSLFPictureShape.java](https://searchcode.com/codesearch/view/97406705/)
{% highlight java %}
106. PackagePart p = getSheet().getPackagePart();
107. PackageRelationship rel = p.getRelationship(blipId);
110.         PackagePart imgPart = p.getRelatedPart(rel);
{% endhighlight %}

***

### [OOXMLSignatureVerifierTest.java](https://searchcode.com/codesearch/view/7982558/)
{% highlight java %}
166. PackagePart signaturePart = signatureParts.get(0);
168.     + signaturePart.getClass().getName());
{% endhighlight %}

***

### [OOXMLSignatureVerifierTest.java](https://searchcode.com/codesearch/view/7982558/)
{% highlight java %}
157. for (PackagePart part : parts) {
158.   LOG.debug("part name: " + part.getPartName().getName());
159.   LOG.debug("part content type: " + part.getContentType());
{% endhighlight %}

***

### [OPCPackageReader.java](https://searchcode.com/codesearch/view/401673/)
{% highlight java %}
54. private PackagePart resourcesPart;
145.           session.start(resourcesPart.getInputStream());
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
1183. PackagePart hdrPart = document.getPartById( headerRef.getId() );
1194. HdrDocument hdrDoc = HdrDocument.Factory.parse( hdrPart.getInputStream() );
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
1211. PackagePart hdrPart = document.getPartById( footerRef.getId() );
1222. FtrDocument hdrDoc = FtrDocument.Factory.parse( hdrPart.getInputStream() );
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96673228/)
{% highlight java %}
131. PackagePart hdrPart = document.getPartById( headerRef.getId() );
140. HdrDocument hdrDoc = HdrDocument.Factory.parse( hdrPart.getInputStream() );
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96673228/)
{% highlight java %}
149. PackagePart hdrPart = document.getPartById( footerRef.getId() );
159. FtrDocument hdrDoc = FtrDocument.Factory.parse( hdrPart.getInputStream() );
{% endhighlight %}

***

### [XSLFImageRenderer.java](https://searchcode.com/codesearch/view/97406847/)
{% highlight java %}
109. public BufferedImage readImage(PackagePart packagePart) throws IOException {
110.     return ImageIO.read(packagePart.getInputStream());
{% endhighlight %}

***

