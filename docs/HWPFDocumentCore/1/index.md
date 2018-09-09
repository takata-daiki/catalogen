# HWPFDocumentCore @Cluster 1

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
700. public void processDocument( HWPFDocumentCore wordDocument )
704.         final SummaryInformation summaryInformation = wordDocument
718.     final Range docRange = wordDocument.getRange();
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
317. private final HWPFDocumentCore _doc;
333.     String text = _doc.getDocumentText();
366.     for ( CHPX chpx : _doc.getCharacterTable().getTextRuns() )
373.                     _doc.getStyleSheet(), (short) StyleSheet.NIL_STYLE ) );
389.                     _doc.getOverallRange() )
434.     FileInformationBlock fib = _doc.getFileInformationBlock();
594.     for ( PAPX papx : _doc.getParagraphTable().getParagraphs() )
601.                     _doc.getOverallRange(), papx );
624.             for ( PAPX papx : _doc.getParagraphTable().getParagraphs() )
658.     Range range = _doc.getOverallRange();
753.     for ( TextPiece textPiece : _doc.getTextTable().getTextPieces() )
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
101. HWPFDocumentCore doc = parent._doc;
102. ListTables listTables = doc.getListTables();
103. StyleSheet styleSheet = doc.getStyleSheet();
{% endhighlight %}

***

