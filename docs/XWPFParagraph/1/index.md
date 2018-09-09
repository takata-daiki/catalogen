# XWPFParagraph @Cluster 1

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

