# XWPFDocument @Cluster 2 (getpartbyid, hdrpart, list)

***

### [OLDXHTMLMapper.java](https://searchcode.com/codesearch/view/12208721/)
> sets the 
{% highlight java %}
60. public OLDXHTMLMapper( XWPFDocument document )
66.         defaults = document.getStyle().getDocDefaults();
{% endhighlight %}

***

### [XHTMLStyleUtil.java](https://searchcode.com/codesearch/view/12208720/)
> sets style of a cell @ param for the @ param @ return style of the shape 
{% highlight java %}
72. public static StringBuilder getStyle( XWPFDocument document, CTDocDefaults defaults )
75.     CTSectPr sectPr = document.getDocument().getBody().getSectPr();
{% endhighlight %}

***

### [StreamingDOCXImpl.java](https://searchcode.com/codesearch/view/76071738/)
> has our in - memory objects ) 
{% highlight java %}
59. XWPFDocument doc = new XWPFDocument();
61. XWPFParagraph p1 = doc.createParagraph();
88. doc.write(outs);
{% endhighlight %}

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
> @ see org . apache . poi . openxml 4 j . opc . relationshipsource # 
{% highlight java %}
54. XWPFDocument document = (XWPFDocument) extractor.getDocument();
55. XWPFHeaderFooterPolicy hfPolicy = document.getHeaderFooterPolicy();
61. Iterator<XWPFParagraph> i = document.getParagraphsIterator();
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
> test that we get the same value as excel and , for 
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

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
> test that we get the same value as excel and , for 
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

