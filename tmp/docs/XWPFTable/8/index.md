# XWPFTable @Cluster 8

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
409. protected IITextContainer startVisitTable( XWPFTable table, IITextContainer pdfContainer )
439.     if ( table.getCTTbl() != null )
441.         if ( table.getCTTbl().getTblPr().getTblBorders() != null )
443.             CTBorder bottom = table.getCTTbl().getTblPr().getTblBorders().getBottom();
448.             CTBorder left = table.getCTTbl().getTblPr().getTblBorders().getLeft();
453.             CTBorder top = table.getCTTbl().getTblPr().getTblBorders().getTop();
458.             CTBorder right = table.getCTTbl().getTblPr().getTblBorders().getRight();
{% endhighlight %}

***

