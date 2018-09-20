# HWPFDocumentCore @Cluster 1

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

