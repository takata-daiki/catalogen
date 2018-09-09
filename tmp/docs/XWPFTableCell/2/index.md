# XWPFTableCell @Cluster 2

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
495. protected IITextContainer startVisitTableCell( XWPFTableCell cell, IITextContainer tableContainer )
499.     XWPFTableRow row = cell.getTableRow();
503.     CTTcPr tcPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
494. XWPFTableCell cell = (XWPFTableCell) body;
504. XWPFTableRow row = cell.getTableRow();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
682. protected IITextContainer startVisitTableCell( XWPFTableCell cell, IITextContainer tableContainer,
687.     XWPFTableRow row = cell.getTableRow();
693.     CTTcPr tcPr = cell.getCTTc().getTcPr();
722.         XWPFStyle tableStyle = super.getXWPFStyle( cell.getTableRow().getTable().getStyleID() );
728.         CTTblBorders tableBorders = XWPFTableUtil.getTblBorders( cell.getTableRow().getTable() );
{% endhighlight %}

***

### [PdfMapper.java](https://searchcode.com/codesearch/view/96673019/)
{% highlight java %}
1055. protected IITextContainer startVisitTableCell( final XWPFTableCell cell, IITextContainer pdfTableContainer,
1060.     XWPFTableRow row = cell.getTableRow();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
368. protected Object startVisitTableCell( XWPFTableCell cell, Object tableContainer, boolean firstRow, boolean lastRow,
374.     XWPFTableRow row = cell.getTableRow();
379.     CTTcPr tcPr = cell.getCTTc().getTcPr();
{% endhighlight %}

***

