# XWPFFooter

***

## [Cluster 1 (blipid, currentfooter, getpicturedatabyid)](./1)
2 results
> test that we get the same value as excel and , for 
{% highlight java %}
135. private XWPFFooter currentFooter;
1348.         return currentFooter.getPictureDataByID( blipId );
{% endhighlight %}

***

## [Cluster 2 (extendedpdfpcell, footerref, hdr)](./2)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
207. XWPFFooter hdr = getXWPFFooter( footerRef );
208. visitBodyElements( hdr.getBodyElements(), (ExtendedPdfPCell) pdfFooter.getTableCell() );
{% endhighlight %}

***

## [Cluster 3 (footer, getbodyelements, void)](./3)
1 results
> create an escher stream using a specified byte @ param data the byte array to be used by the of @ param offset the offset to be used 
{% highlight java %}
165. protected void visitFooter( XWPFFooter footer, CTHdrFtrRef footerRef, CTSectPr sectPr, StylableMasterPage masterPage )
169.     List<IBodyElement> bodyElements = footer.getBodyElements();
171.     visitBodyElements( footer.getBodyElements(), tableCell );
{% endhighlight %}

***

