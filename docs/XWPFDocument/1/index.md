# XWPFDocument @Cluster 1

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
{% highlight java %}
130. private void extractTableContent(XWPFDocument doc, XHTMLContentHandler xhtml)
132.     for (CTTbl table : doc.getDocument().getBody().getTblArray()) {
{% endhighlight %}

***

### [WordXMLReader.java](https://searchcode.com/codesearch/view/46076962/)
{% highlight java %}
78. private final XWPFDocument document;
143.     for (XWPFParagraph p : document.getParagraphs()) {
147.     Iterator<XWPFTable> tableIter = document.getTablesIterator();
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

### [XWPFStylesDocument.java](https://searchcode.com/codesearch/view/96672666/)
{% highlight java %}
148. private final XWPFDocument document;
201.     List<CTStyle> styles = document.getStyle().getStyleList();
256.         return document.getStyle().getDocDefaults();
1192.     for ( POIXMLDocumentPart p : document.getRelations() )
1233.         for ( POIXMLDocumentPart p : document.getRelations() )
{% endhighlight %}

***

