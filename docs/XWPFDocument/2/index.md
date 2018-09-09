# XWPFDocument @Cluster 2

***

### [WordXMLReader.java](https://searchcode.com/codesearch/view/46076962/)
{% highlight java %}
78. private final XWPFDocument document;
143.     for (XWPFParagraph p : document.getParagraphs()) {
147.     Iterator<XWPFTable> tableIter = document.getTablesIterator();
{% endhighlight %}

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
{% highlight java %}
130. private void extractTableContent(XWPFDocument doc, XHTMLContentHandler xhtml)
132.     for (CTTbl table : doc.getDocument().getBody().getTblArray()) {
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

### [OLDXHTMLMapper.java](https://searchcode.com/codesearch/view/12208721/)
{% highlight java %}
56. private XWPFDocument document;
66.         defaults = document.getStyle().getDocDefaults();
85.     for ( POIXMLDocumentPart part : document.getRelations() )
109.         List<IBodyElement> bodyElement = document.getBodyElements();
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

### [XWPFStylesDocument.java](https://searchcode.com/codesearch/view/96672666/)
{% highlight java %}
148. private final XWPFDocument document;
201.     List<CTStyle> styles = document.getStyle().getStyleList();
256.         return document.getStyle().getDocDefaults();
1192.     for ( POIXMLDocumentPart p : document.getRelations() )
1233.         for ( POIXMLDocumentPart p : document.getRelations() )
{% endhighlight %}

***

### [WordExport.java](https://searchcode.com/codesearch/view/134954814/)
{% highlight java %}
184. final XWPFDocument doc = ((XWPFDocument)docObj);
194.       final List<XWPFTable> tableList = doc.getTables();
211.           lstParagraphs.addAll(doc.getParagraphs());
{% endhighlight %}

***

