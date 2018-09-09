# XWPFFooter

***

### [Cluster 1](./1)
{% highlight java %}
135. private XWPFFooter currentFooter;
1348.         return currentFooter.getPictureDataByID( blipId );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
1213. for ( XWPFFooter footer : footers )
1215.     if ( footer.getPackagePart().equals( hdrPart ) )
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
165. protected void visitFooter( XWPFFooter footer, CTHdrFtrRef footerRef, CTSectPr sectPr, StylableMasterPage masterPage )
169.     List<IBodyElement> bodyElements = footer.getBodyElements();
171.     visitBodyElements( footer.getBodyElements(), tableCell );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
207. XWPFFooter hdr = getXWPFFooter( footerRef );
208. visitBodyElements( hdr.getBodyElements(), (ExtendedPdfPCell) pdfFooter.getTableCell() );
{% endhighlight %}

***

