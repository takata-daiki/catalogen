# XWPFParagraph

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
230. protected IITextContainer startVisitParagraph( XWPFParagraph docxParagraph, ListItemContext itemContext,
348.             if ( ParagraphIndentationLeftValueProvider.INSTANCE.getValue( docxParagraph.getCTP().getPPr() ) == null )
359.             if ( ParagraphIndentationHangingValueProvider.INSTANCE.getValue( docxParagraph.getCTP().getPPr() ) == null )
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
121. protected XHTMLPageContentBuffer startVisitPargraph( XWPFParagraph paragraph, XHTMLPageContentBuffer parentContainer )
126.     if ( paragraph.getStyleID() != null )
130.             LOGGER.fine( "StyleID " + paragraph.getStyleID() );
133.         parentContainer.setAttribute( CLASS_ATTR, paragraph.getStyleID() );
138.         XHTMLStyleUtil.getStyle( paragraph, super.getXWPFStyle( paragraph.getStyleID() ), defaults );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
63. XWPFParagraph paragraph = i.next();
66. if (paragraph.getCTP().getPPr() != null) {
67.     ctSectPr = paragraph.getCTP().getPPr().getSectPr();
81. CTBookmark[] bookmarks = paragraph.getCTP().getBookmarkStartArray();
{% endhighlight %}

***

### [Cluster 5](./5)
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

### [Cluster 6](./6)
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

### [Cluster 7](./7)
{% highlight java %}
86. XWPFParagraph paragraph = cell.getParagraphs().get( 0 );
87. if ( "A".equals( paragraph.getText() ) )
91. else if ( "B".equals( paragraph.getText() ) )
95. else if ( "C".equals( paragraph.getText() ) )
99. else if ( "D".equals( paragraph.getText() ) )
103. else if ( "E".equals( paragraph.getText() ) )
107. else if ( "F".equals( paragraph.getText() ) )
111. else if ( "G".equals( paragraph.getText() ) )
115. else if ( "H".equals( paragraph.getText() ) )
119. else if ( "I".equals( paragraph.getText() ) )
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
400. protected IITextContainer startVisitPargraph( XWPFParagraph xwpfParagraph, IITextContainer pdfParagraph )
403.     String styleID = xwpfParagraph.getStyleID();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
236. XWPFParagraph paragraph = (XWPFParagraph) bodyElement;
237. String paragraphStyleName = paragraph.getStyleID();
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
207. public static String getBackgroundColor( XWPFParagraph paragraph )
209.     List<XWPFRun> runs = paragraph.getRuns();
{% endhighlight %}

***

### [Cluster 11](./11)
{% highlight java %}
630. protected XWPFStyle getXWPFStyle( XWPFParagraph paragraph )
636.     return getXWPFStyle( paragraph.getStyleID() );
{% endhighlight %}

***

### [Cluster 12](./12)
{% highlight java %}
604. private void applyStyles( XWPFParagraph ele, IStylableElement<XWPFParagraph> element )
607.     Style style = styleEngine.getStyle( ele.getStyleID() );
{% endhighlight %}

***

### [Cluster 13](./13)
{% highlight java %}
250. private void testsI( XWPFParagraph paragraph, XWPFStylesDocument stylesDocument )
253.     XWPFTableCell cell = (XWPFTableCell) paragraph.getBody();
{% endhighlight %}

***

### [Cluster 14](./14)
{% highlight java %}
526. private void visitRuns( XWPFParagraph paragraph, T paragraphContainer )
534.     CTP ctp = paragraph.getCTP();
{% endhighlight %}

***

### [Cluster 15](./15)
{% highlight java %}
392. protected void visitParagraphBody( XWPFParagraph paragraph, int index, T paragraphContainer )
395.     List<XWPFRun> runs = paragraph.getRuns();
441.     CTPPr ppr = paragraph.getCTP().getPPr();
{% endhighlight %}

***

### [Cluster 16](./16)
{% highlight java %}
485. private boolean isAddNewLine( XWPFParagraph paragraph, int index )
490.     IBody body = paragraph.getBody();
{% endhighlight %}

***

### [Cluster 17](./17)
{% highlight java %}
276. protected void endVisitPargraph( XWPFParagraph paragraph, IITextContainer parentContainer,
284.     CTPPr ppr = paragraph.getCTP().getPPr();
{% endhighlight %}

***

### [Cluster 18](./18)
{% highlight java %}
177. protected Object startVisitParagraph( XWPFParagraph paragraph, ListItemContext itemContext, Object parentContainer )
183.     AttributesImpl attributes = createClassAttribute( paragraph.getStyleID() );
186.     CTPPr pPr = paragraph.getCTP().getPPr();
{% endhighlight %}

***

### [Cluster 19](./19)
{% highlight java %}
207. XWPFParagraph paragraph = run.getParagraph();
211. this.currentRunAttributes = createClassAttribute( paragraph.getStyleID() );
{% endhighlight %}

***

### [Cluster 20](./20)
{% highlight java %}
206. protected void visitParagraphBody( XWPFParagraph paragraph, T paragraphContainer )
209.     List<XWPFRun> runs = paragraph.getRuns();
216.         for ( XWPFRun run : paragraph.getRuns() )
{% endhighlight %}

***

### [Cluster 21](./21)
{% highlight java %}
61. XWPFParagraph p1 = doc.createParagraph();
62. p1.setAlignment(ParagraphAlignment.CENTER);
64. XWPFRun r1 = p1.createRun();
{% endhighlight %}

***

### [Cluster 22](./22)
{% highlight java %}
143. for (XWPFParagraph p : document.getParagraphs()) {
144.   extractStructuredDocumentTags(p.getCTP().getSdtArray());
{% endhighlight %}

***

