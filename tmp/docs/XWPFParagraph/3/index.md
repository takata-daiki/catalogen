# XWPFParagraph @Cluster 3

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

