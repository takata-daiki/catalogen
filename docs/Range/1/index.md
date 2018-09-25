# Range @Cluster 1 (getendoffset, getstartoffset, previous)

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param @ param 
{% highlight java %}
22. Range range = doc.getRange();
23. Paragraph par1 = range.insertAfter(new ParagraphProperties(), 0);
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> tests that the create and sets the font is based on @ since 3 . 1 4 beta 1 
{% highlight java %}
641. Range formulaRange = new Range( range.getCharacterRun(
661. String formula = formulaRange.text();
{% endhighlight %}

***

### [PrintTemplateServiceImpl.java](https://searchcode.com/codesearch/view/94110212/)
> set the sheet ' s @ param name 
{% highlight java %}
1352. Range range = hdt.getRange();
1353. String text = range.text();
{% endhighlight %}

***

### [HWPFDocument.java](https://searchcode.com/codesearch/view/97383956/)
> sets the 
{% highlight java %}
1039. Range r = new Range(start, start + length, this);
1040. r.delete();
{% endhighlight %}

***

### [test.java](https://searchcode.com/codesearch/view/13078982/)
> sets the 
{% highlight java %}
17. Range r = doc.getRange(); // ??word?????
18. int lenParagraph = r.numParagraphs();// ?????
24.   Paragraph p = r.getParagraph(x);
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the 
{% highlight java %}
718. final Range docRange = wordDocument.getRange();
720. if ( docRange.numSections() == 1 )
722.     processSingleSection( wordDocument, docRange.getSection( 0 ) );
{% endhighlight %}

***

### [WordUtil.java](https://searchcode.com/codesearch/view/69098620/)
> set the contents of the record that , p to be set to 0 . and only the size of the header / footer : empty string is no type ( " a 1 " ) 
{% highlight java %}
26. Range range = doc.getRange();
27. for (int i = 0; i < range.numCharacterRuns(); i++) {
28.   CharacterRun run = range.getCharacterRun(i);
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the and used when the document is parsed / @ param { @ code true } if the data is be and a single value , or { @ code null } if it wasn ' t 
{% highlight java %}
735.     final Range range )
737. for ( int s = 0; s < range.numSections(); s++ )
739.     processSection( wordDocument, range.getSection( s ), s );
{% endhighlight %}

***

### [FilesGatherer.java](https://searchcode.com/codesearch/view/13078978/)
> sets the 
{% highlight java %}
129. Range r = doc.getRange(); // ??word?????
130. int lenParagraph = r.numParagraphs();// ?????
136.   Paragraph p = r.getParagraph(x);
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . @ param p the font to be used if there is a call . 
{% highlight java %}
104. Range r = doc.getRange();
106. for (int x = 0; x < r.numSections(); x++) {
107.   Section s = r.getSection(x);
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the param = 0 - based ) 
{% highlight java %}
636.     Element currentBlock, Range range, int currentTableLevel,
641.     Range formulaRange = new Range( range.getCharacterRun(
642.             beginMark + 1 ).getStartOffset(), range.getCharacterRun(
651.     Range valueRange = new Range( range.getCharacterRun(
652.             separatorMark + 1 ).getStartOffset(), range
676.     debug.append( range.getCharacterRun( i ) );
681. Range deadFieldValueSubrage = new Range( range.getCharacterRun(
682.         separatorMark ).getStartOffset() + 1, range.getCharacterRun(
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the width of a in if the specified column and range in a range of cells . @ param range - the row number of the page . @ param value whether to display the workbook or - 1 . 
{% highlight java %}
293.     final int currentTableLevel, final Range range, final Element block )
312.             .getBookmarksStartedBetween( range.getStartOffset(),
313.                     range.getEndOffset() );
330.     for ( int c = 0; c < range.numCharacterRuns(); c++ )
332.         CharacterRun characterRun = range.getCharacterRun( c );
359.                                 characterRun.getStartOffset(), range
372. int previous = range.getStartOffset();
409.             int end = Math.min( range.getEndOffset(), structure.end );
446.     previous = Math.min( range.getEndOffset(), structure.end );
449. if ( previous != range.getStartOffset() )
451.     if ( previous > range.getEndOffset() )
455.                 range.getStartOffset() + "; " + range.getEndOffset(),
460.     if ( previous < range.getEndOffset() )
462.         Range subrange = new Range( previous, range.getEndOffset(),
477. for ( int c = 0; c < range.numCharacterRuns(); c++ )
479.     CharacterRun characterRun = range.getCharacterRun( c );
547.                 while ( c < range.numCharacterRuns()
548.                         && range.getCharacterRun( c ).getEndOffset() <= continueAfter )
551.                 if ( c < range.numCharacterRuns() )
{% endhighlight %}

***

