# XWPFDocument @Cluster 1

***

### [StreamingDOCXImpl.java](https://searchcode.com/codesearch/view/76071738/)
{% highlight java %}
59. XWPFDocument doc = new XWPFDocument();
61. XWPFParagraph p1 = doc.createParagraph();
88. doc.write(outs);
{% endhighlight %}

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
{% highlight java %}
54. XWPFDocument document = (XWPFDocument) extractor.getDocument();
55. XWPFHeaderFooterPolicy hfPolicy = document.getHeaderFooterPolicy();
61. Iterator<XWPFParagraph> i = document.getParagraphsIterator();
{% endhighlight %}

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
{% highlight java %}
166. XWPFDocument document = (XWPFDocument) extractor.getDocument();
169. parts.add( document.getPackagePart() );
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
61. protected final XWPFDocument document;
70.         this.defaults = document.getStyle().getDocDefaults();
96.     CTSectPr sectPr = document.getDocument().getBody().getSectPr();
100.     List<IBodyElement> bodyElements = document.getBodyElements();
139.     PackagePart hdrPart = document.getPartById( headerRef.getId() );
151.     PackagePart hdrPart = document.getPartById( footerRef.getId() );
302.         return document.getStyles().getStyle( styleID );
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
65. public XWPFElementVisitor( XWPFDocument document )
70.         this.defaults = document.getStyle().getDocDefaults();
{% endhighlight %}

***

### [XHTMLStyleUtil.java](https://searchcode.com/codesearch/view/12208720/)
{% highlight java %}
72. public static StringBuilder getStyle( XWPFDocument document, CTDocDefaults defaults )
75.     CTSectPr sectPr = document.getDocument().getBody().getSectPr();
{% endhighlight %}

***

### [OLDXHTMLMapper.java](https://searchcode.com/codesearch/view/12208721/)
{% highlight java %}
60. public OLDXHTMLMapper( XWPFDocument document )
66.         defaults = document.getStyle().getDocDefaults();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
129. protected final XWPFDocument document;
192.     List<IBodyElement> bodyElements = document.getBodyElements();
467.     XWPFNum num = document.getNumbering().getNum( numID.getVal() );
474.     XWPFAbstractNum abstractNum = document.getNumbering().getAbstractNum( abstractNumID.getVal() );
660.             XWPFHyperlink hyperlink = document.getHyperlinkByID( hyperlinkId );
1078.         return document.getStyles().getStyle( styleID );
1183.     PackagePart hdrPart = document.getPartById( headerRef.getId() );
1184.     List<XWPFHeader> headers = document.getHeaderList();
1211.     PackagePart hdrPart = document.getPartById( footerRef.getId() );
1212.     List<XWPFFooter> footers = document.getFooterList();
1350.     return document.getPictureDataByID( blipId );
{% endhighlight %}

***

### [DocXReader.java](https://searchcode.com/codesearch/view/66649309/)
{% highlight java %}
26. XWPFDocument doc = new XWPFDocument(fs);
28. for (int i = 0; i < doc.getParagraphs().size(); i++) {
29.     XWPFParagraph paragraph = doc.getParagraphs().get(i);
45.     doc.write(new FileOutputStream("/home/nickl/virtualshared/230700.62-01(0)-1 Информатика и программирование2.docx"));
{% endhighlight %}

***

### [WordExport.java](https://searchcode.com/codesearch/view/134954814/)
{% highlight java %}
184. final XWPFDocument doc = ((XWPFDocument)docObj);
194.       final List<XWPFTable> tableList = doc.getTables();
211.           lstParagraphs.addAll(doc.getParagraphs());
{% endhighlight %}

***

