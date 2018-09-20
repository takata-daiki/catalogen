# XWPFParagraph @Cluster 1

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
236. XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
237. String paragraphStyleName = paragraph.getStyleID();
{% endhighlight %}

***

### [StyleEngineForIText.java](https://searchcode.com/codesearch/view/12208690/)
{% highlight java %}
400. protected IITextContainer startVisitPargraph( XWPFParagraph xwpfParagraph, IITextContainer pdfParagraph )
403.     String styleID = xwpfParagraph.getStyleID();
{% endhighlight %}

***

### [WordXMLReader.java](https://searchcode.com/codesearch/view/46076962/)
{% highlight java %}
143. for (XWPFParagraph p : document.getParagraphs()) {
144.   extractStructuredDocumentTags(p.getCTP().getSdtArray());
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
838. private void applyStyles( XWPFParagraph ele, IStylableElement<XWPFParagraph> element )
841.     Style style = styleEngine.getStyle( ele.getStyleID() );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
630. protected XWPFStyle getXWPFStyle( XWPFParagraph paragraph )
636.     return getXWPFStyle( paragraph.getStyleID() );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
604. private void applyStyles( XWPFParagraph ele, IStylableElement<XWPFParagraph> element )
607.     Style style = styleEngine.getStyle( ele.getStyleID() );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
380. protected void endVisitPargraph( XWPFParagraph paragraph, IITextContainer parentContainer,
388.     CTPPr ppr = paragraph.getCTP().getPPr();
{% endhighlight %}

***

### [StyleEngineForIText.java](https://searchcode.com/codesearch/view/96673306/)
{% highlight java %}
341. protected IITextContainer startVisitPargraph( XWPFParagraph xwpfParagraph, IITextContainer pdfParagraph )
344.     String styleID = xwpfParagraph.getStyleID();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
864. protected XWPFStyle getXWPFStyle( XWPFParagraph paragraph )
870.     return getXWPFStyle( paragraph.getStyleID() );
{% endhighlight %}

***

### [XWPFParagraphUtils.java](https://searchcode.com/codesearch/view/12208683/)
{% highlight java %}
207. public static String getBackgroundColor( XWPFParagraph paragraph )
209.     List<XWPFRun> runs = paragraph.getRuns();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
526. private void visitRuns( XWPFParagraph paragraph, T paragraphContainer )
534.     CTP ctp = paragraph.getCTP();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
485. private boolean isAddNewLine( XWPFParagraph paragraph, int index )
490.     IBody body = paragraph.getBody();
{% endhighlight %}

***

### [StyleEngineForXHTML.java](https://searchcode.com/codesearch/view/12208719/)
{% highlight java %}
402. protected IITextContainer startVisitPargraph( XWPFParagraph xwpfParagraph, IITextContainer pdfParagraph )
405.     String styleID = xwpfParagraph.getStyleID();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
276. protected void endVisitPargraph( XWPFParagraph paragraph, IITextContainer parentContainer,
284.     CTPPr ppr = paragraph.getCTP().getPPr();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
207. XWPFParagraph paragraph = run.getParagraph();
211. this.currentRunAttributes = createClassAttribute( paragraph.getStyleID() );
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
177. protected Object startVisitParagraph( XWPFParagraph paragraph, ListItemContext itemContext, Object parentContainer )
183.     AttributesImpl attributes = createClassAttribute( paragraph.getStyleID() );
186.     CTPPr pPr = paragraph.getCTP().getPPr();
{% endhighlight %}

***

### [PdfMapper.java](https://searchcode.com/codesearch/view/96673019/)
{% highlight java %}
230. protected IITextContainer startVisitParagraph( XWPFParagraph docxParagraph, ListItemContext itemContext,
348.             if ( ParagraphIndentationLeftValueProvider.INSTANCE.getValue( docxParagraph.getCTP().getPPr() ) == null )
359.             if ( ParagraphIndentationHangingValueProvider.INSTANCE.getValue( docxParagraph.getCTP().getPPr() ) == null )
{% endhighlight %}

***

### [StreamingDOCXImpl.java](https://searchcode.com/codesearch/view/76071738/)
{% highlight java %}
61. XWPFParagraph p1 = doc.createParagraph();
62. p1.setAlignment(ParagraphAlignment.CENTER);
64. XWPFRun r1 = p1.createRun();
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
206. protected void visitParagraphBody( XWPFParagraph paragraph, T paragraphContainer )
209.     List<XWPFRun> runs = paragraph.getRuns();
216.         for ( XWPFRun run : paragraph.getRuns() )
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
392. protected void visitParagraphBody( XWPFParagraph paragraph, int index, T paragraphContainer )
395.     List<XWPFRun> runs = paragraph.getRuns();
441.     CTPPr ppr = paragraph.getCTP().getPPr();
{% endhighlight %}

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
{% highlight java %}
63. XWPFParagraph paragraph = i.next();
66. if (paragraph.getCTP().getPPr() != null) {
67.     ctSectPr = paragraph.getCTP().getPPr().getSectPr();
81. CTBookmark[] bookmarks = paragraph.getCTP().getBookmarkStartArray();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/12208722/)
{% highlight java %}
121. protected XHTMLPageContentBuffer startVisitPargraph( XWPFParagraph paragraph, XHTMLPageContentBuffer parentContainer )
126.     if ( paragraph.getStyleID() != null )
130.             LOGGER.fine( "StyleID " + paragraph.getStyleID() );
133.         parentContainer.setAttribute( CLASS_ATTR, paragraph.getStyleID() );
138.         XHTMLStyleUtil.getStyle( paragraph, super.getXWPFStyle( paragraph.getStyleID() ), defaults );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
203. protected IITextContainer startVisitPargraph( XWPFParagraph docxParagraph, IITextContainer parentContainer )
206.     if ( docxParagraph.getText().startsWith( "Cette commande client est co" ) )
223.     Borders borderTop = docxParagraph.getBorderTop();
228.     Borders borderBottom = docxParagraph.getBorderBottom();
233.     Borders borderLeft = docxParagraph.getBorderLeft();
238.     Borders borderRight = docxParagraph.getBorderRight();
280.     CTPPr ppr = docxParagraph.getCTP().getPPr();
294.                 LineSpacingRule lineSpacingRule = docxParagraph.getSpacingLineRule();
{% endhighlight %}

***

### [XHTMLStyleUtil.java](https://searchcode.com/codesearch/view/12208720/)
{% highlight java %}
126. public static StringBuilder getStyle( XWPFParagraph paragraph, XWPFStyle style, CTDocDefaults defaults )
181.     if ( indentationLeft == -1 && paragraph.getIndentationLeft() != -1 )
183.         indentationLeft = dxa2points( paragraph.getIndentationLeft() );
185.     if ( indentationRight == -1 && paragraph.getIndentationRight() != -1 )
187.         indentationRight = dxa2points( paragraph.getIndentationRight() );
189.     if ( firstLineIndent == -1 && paragraph.getIndentationFirstLine() != -1 )
191.         firstLineIndent = dxa2points( paragraph.getIndentationFirstLine() );
193.     if ( spacingBefore == -1 && paragraph.getSpacingBefore() != -1 )
195.         spacingBefore = dxa2points( paragraph.getSpacingBefore() );
197.     if ( spacingAfter == -1 && paragraph.getSpacingAfter() != -1 )
199.         spacingAfter = dxa2points( paragraph.getSpacingAfter() );
222.     ParagraphAlignment alignment = paragraph.getAlignment();
{% endhighlight %}

***

### [XWPFParagraphUtils.java](https://searchcode.com/codesearch/view/12208683/)
{% highlight java %}
52. public static void processLayout( XWPFParagraph paragraph, Paragraph pdfParagraph, XWPFStyle style,
124.     if ( indentationLeft == -1 && paragraph.getIndentationLeft() != -1 )
126.         indentationLeft = dxa2points( paragraph.getIndentationLeft() );
129.     if ( indentationRight == -1 && paragraph.getIndentationRight() != -1 )
131.         indentationRight = dxa2points( paragraph.getIndentationRight() );
133.     if ( firstLineIndent == -1 && paragraph.getIndentationFirstLine() != -1 )
135.         firstLineIndent = dxa2points( paragraph.getIndentationFirstLine() );
137.     if ( spacingBefore == -1 && paragraph.getSpacingBefore() != -1 )
139.         spacingBefore = dxa2points( paragraph.getSpacingBefore() );
141.     if ( spacingAfter == -1 && paragraph.getSpacingAfter() != -1 )
143.         spacingAfter = dxa2points( paragraph.getSpacingAfter() );
172.     ParagraphAlignment alignment = paragraph.getAlignment();
{% endhighlight %}

***

### [StylableParagraph.java](https://searchcode.com/codesearch/view/12208714/)
{% highlight java %}
75. public void applyStyles( XWPFParagraph p, Style style )
119.     ParagraphAlignment paragraphAlignment = p.getAlignment();
147.     int indentationLeft = p.getIndentationLeft();
154.     int indentationFirstLine = p.getIndentationFirstLine();
160.     int indentationRight = p.getIndentationRight();
169.     int left = p.getIndentationLeft();
170.     int right = p.getIndentationRight();
182.     int firstLineIndent = p.getIndentationFirstLine();
188.     int spacingBefore = p.getSpacingBefore();
193.     if ( p.getSpacingAfter() >= 0 )
195.         setSpacingAfter( dxa2points( p.getSpacingAfter() ) );
204.     if ( p.getCTP().getPPr() != null )
206.         if ( p.getCTP().getPPr().getSpacing() != null )
209.             if ( p.getCTP().getPPr().getSpacing().getLine() != null )
213.                 float leading = ( p.getCTP().getPPr().getSpacing().getLine().floatValue() / 240 );
220.     ParagraphAlignment alignment = p.getAlignment();
{% endhighlight %}

***

