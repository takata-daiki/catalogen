# XWPFRun @Cluster 2

***

### [StreamingDOCXImpl.java](https://searchcode.com/codesearch/view/76071738/)
{% highlight java %}
64. XWPFRun r1 = p1.createRun();
76.   r1.setText("png");
77.   r1.addBreak();
79.   r1.addPicture(new ByteArrayInputStream(bytes),
{% endhighlight %}

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

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
723. protected void visitRun( XWPFRun run, boolean pageNumber, String url, T paragraphContainer )
727.     CTR ctr = run.getCTR();
771.                 CTTabs tabs = stylesDocument.getParagraphTabs( run.getParagraph() );
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

### [AbstractRunValueProvider.java](https://searchcode.com/codesearch/view/96672931/)
{% highlight java %}
48. public CTRPr getRPr( XWPFRun run )
50.     return run.getCTR().getRPr();
{% endhighlight %}

***

### [AbstractRunValueProvider.java](https://searchcode.com/codesearch/view/96672931/)
{% highlight java %}
100. protected String[] getStyleID( XWPFRun run )
102.     XWPFParagraph paragraph = run.getParagraph();
{% endhighlight %}

***

### [AbstractRunValueProvider.java](https://searchcode.com/codesearch/view/96672931/)
{% highlight java %}
131. protected XWPFTableCell getParentTableCell( XWPFRun run )
133.     return StylesHelper.getEmbeddedTableCell( run.getParagraph() );
{% endhighlight %}

***

### [WordExport.java](https://searchcode.com/codesearch/view/134954814/)
{% highlight java %}
229. final XWPFRun run = new XWPFRun(ctp.addNewR(), paragraph);
233.           run.setText(lines[0], 0);
235.               run.addBreak();
236.               run.setText(lines[iLine]);
239.           run.setText(formattedValue, 0);
{% endhighlight %}

***

### [WordExport.java](https://searchcode.com/codesearch/view/134954814/)
{% highlight java %}
272. private void replaceBookmark(CTBookmark bookmark, XWPFRun run,
317.                   run.getCTR().getDomNode().insertBefore(
318.                           styleNode.cloneNode(true), run.getCTR().getDomNode().getFirstChild());
334.               run.getCTR().getDomNode(), nextNode);
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
413. protected void visitRun( XWPFRun run, IITextContainer pdfContainer )
417.     CTR ctr = run.getCTR();
459.     UnderlinePatterns underlinePatterns = run.getUnderline();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
203. protected void visitRun( XWPFRun run, boolean pageNumber, String url, Object paragraphContainer )
207.     XWPFParagraph paragraph = run.getParagraph();
214.     CTRPr rPr = run.getCTR().getRPr();
{% endhighlight %}

***

