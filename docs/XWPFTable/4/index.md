# XWPFTable @Cluster 4

***

### [XWPFDocumentVisitor.java](https://searchcode.com/codesearch/view/96672565/)
{% highlight java %}
833. protected void visitTableBody( XWPFTable table, float[] colWidths, T tableContainer )
840.     List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

### [StyleEngineForIText.java](https://searchcode.com/codesearch/view/12208690/)
{% highlight java %}
439. protected IITextContainer startVisitTable( XWPFTable table, IITextContainer tableContainer )
443.     CTString str = table.getCTTbl().getTblPr().getTblStyle();
{% endhighlight %}

***

### [WordXMLReader.java](https://searchcode.com/codesearch/view/46076962/)
{% highlight java %}
155. private void extractStructuredDocumentTags(XWPFTable t) {
156.   CTTbl table = t.getCTTbl();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96672636/)
{% highlight java %}
269. public static TableWidth getTableWidth( XWPFTable table )
273.     CTTblPr tblPr = table.getCTTbl().getTblPr();
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/12208685/)
{% highlight java %}
613. private void applyStyles( XWPFTable ele, IStylableElement<XWPFTable> element )
616.     CTString tblStyle = ele.getCTTbl().getTblPr().getTblStyle();
{% endhighlight %}

***

### [StyleEngineForIText.java](https://searchcode.com/codesearch/view/96673306/)
{% highlight java %}
380. protected IITextContainer startVisitTable( XWPFTable table, IITextContainer tableContainer )
384.     CTString str = table.getCTTbl().getTblPr().getTblStyle();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96673299/)
{% highlight java %}
247. public static CTTblPr getTblPr( XWPFTable table )
249.     CTTbl tbl = table.getCTTbl();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96672636/)
{% highlight java %}
310. public static CTTblPr getTblPr( XWPFTable table )
312.     CTTbl tbl = table.getCTTbl();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96673299/)
{% highlight java %}
206. public static TableWidth getTableWidth( XWPFTable table )
210.     CTTblPr tblPr = table.getCTTbl().getTblPr();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/12208688/)
{% highlight java %}
203. public static TableWidth getTableWidth( XWPFTable table )
207.     CTTblPr tblPr = table.getCTTbl().getTblPr();
{% endhighlight %}

***

### [TableCellVerticalAlignmentTestCase.java](https://searchcode.com/codesearch/view/96672468/)
{% highlight java %}
66. private void testTable( XWPFTable table, XWPFStylesDocument stylesDocument )
68.     List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
237. protected void visitTableBody( XWPFTable table, T tableContainer )
241.     List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
375. XWPFTable table = row.getTable();
376. AttributesImpl attributes = createClassAttribute( table.getStyleID() );
{% endhighlight %}

***

### [PDFMapper.java](https://searchcode.com/codesearch/view/96673303/)
{% highlight java %}
847. private void applyStyles( XWPFTable ele, IStylableElement<XWPFTable> element )
850.     CTString tblStyle = ele.getCTTbl().getTblPr().getTblStyle();
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/96673254/)
{% highlight java %}
200. protected void visitTableBody( XWPFTable table, T tableContainer )
206.     List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
338. XWPFTable table = row.getTable();
339. AttributesImpl attributes = createClassAttribute( table.getStyleID() );
{% endhighlight %}

***

### [XHTMLMapper.java](https://searchcode.com/codesearch/view/96673744/)
{% highlight java %}
307. protected Object startVisitTable( XWPFTable table, float[] colWidths, Object tableContainer )
312.     AttributesImpl attributes = createClassAttribute( table.getStyleID() );
315.     CTTblPr tblPr = table.getCTTbl().getTblPr();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/12208688/)
{% highlight java %}
64. public static float[] computeColWidths( XWPFTable table )
73.     CTTblGrid grid = table.getCTTbl().getTblGrid();
82.         List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96673299/)
{% highlight java %}
67. public static float[] computeColWidths( XWPFTable table )
76.     CTTblGrid grid = table.getCTTbl().getTblGrid();
85.         List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

***

### [XWPFTableUtil.java](https://searchcode.com/codesearch/view/96672636/)
{% highlight java %}
88. public static float[] computeColWidths( XWPFTable table )
98.     CTTblGrid grid = table.getCTTbl().getTblGrid();
107.         List<XWPFTableRow> rows = table.getRows();
{% endhighlight %}

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

