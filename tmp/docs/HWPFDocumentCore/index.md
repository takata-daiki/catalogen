# HWPFDocumentCore

***

## [Cluster 1](./1)
1 results
> this comment could not be generated...
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

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
700. public void processDocument( HWPFDocumentCore wordDocument )
704.         final SummaryInformation summaryInformation = wordDocument
718.     final Range docRange = wordDocument.getRange();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> sets the 
{% highlight java %}
101. HWPFDocumentCore doc = parent._doc;
102. ListTables listTables = doc.getListTables();
103. StyleSheet styleSheet = doc.getStyleSheet();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
301. HWPFDocumentCore original )
306. original.write( baos );
{% endhighlight %}

***

