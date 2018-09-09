# XWPFHeader

***

### [Cluster 1](./1)
{% highlight java %}
133. private XWPFHeader currentHeader;
1344.         return currentHeader.getPictureDataByID( blipId );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
1185. for ( XWPFHeader header : headers )
1187.     if ( header.getPackagePart().equals( hdrPart ) )
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
149. for (XWPFHeader header : headers) {
150.     macroReplaceDocument(header.getParagraphs());
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
154. protected void visitHeader( XWPFHeader header, CTHdrFtrRef headerRef, CTSectPr sectPr, StylableMasterPage masterPage )
158.     List<IBodyElement> bodyElements = header.getBodyElements();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
192. XWPFHeader hdr = getXWPFHeader( headerRef );
193. visitBodyElements( hdr.getBodyElements(), (ExtendedPdfPCell) pdfHeader.getTableCell() );
{% endhighlight %}

***

