# XWPFRun @Cluster 8

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
284. protected void visitPictures( XWPFRun run, T parentContainer )
287.     List<XWPFPicture> embeddedPictures = run.getEmbeddedPictures();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
309. protected void visitRun( XWPFRun run, IITextContainer pdfContainer )
312.     CTR ctr = run.getCTR();
317.     CTRPr rprStyle = getRPr( super.getXWPFStyle( run.getParagraph().getStyleID() ) );
324.     float fontSize = run.getFontSize();
352.     UnderlinePatterns underlinePatterns = run.getUnderline();
372.     List<CTText> texts = run.getCTR().getTList();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/12208722/)
{% highlight java %}
162. protected void visitRun( XWPFRun run, XHTMLPageContentBuffer paragraphContainer )
172.         XHTMLStyleUtil.getStyle( run, runStyle, super.getXWPFStyle( run.getParagraph().getStyle() ), defaults );
173.     List<CTBr> brs = run.getCTR().getBrList();
180.     List<CTText> texts = run.getCTR().getTList();
{% endhighlight %}

***

