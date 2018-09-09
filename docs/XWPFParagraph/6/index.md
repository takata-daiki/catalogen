# XWPFParagraph @Cluster 6

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

