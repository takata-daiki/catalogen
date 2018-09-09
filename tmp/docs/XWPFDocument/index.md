# XWPFDocument

***

### [Cluster 1](./1)
{% highlight java %}
26. XWPFDocument doc = new XWPFDocument(fs);
28. for (int i = 0; i < doc.getParagraphs().size(); i++) {
29.     XWPFParagraph paragraph = doc.getParagraphs().get(i);
45.     doc.write(new FileOutputStream("/home/nickl/virtualshared/230700.62-01(0)-1 Информатика и программирование2.docx"));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
130. private void extractTableContent(XWPFDocument doc, XHTMLContentHandler xhtml)
132.     for (CTTbl table : doc.getDocument().getBody().getTblArray()) {
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
54. XWPFDocument document = (XWPFDocument) extractor.getDocument();
55. XWPFHeaderFooterPolicy hfPolicy = document.getHeaderFooterPolicy();
61. Iterator<XWPFParagraph> i = document.getParagraphsIterator();
{% endhighlight %}

***

