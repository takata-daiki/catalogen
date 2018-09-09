# Range @Cluster 7

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
{% highlight java %}
22. Range range = doc.getRange();
23. Paragraph par1 = range.insertAfter(new ParagraphProperties(), 0);
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
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

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
718. final Range docRange = wordDocument.getRange();
720. if ( docRange.numSections() == 1 )
722.     processSingleSection( wordDocument, docRange.getSection( 0 ) );
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
735.     final Range range )
737. for ( int s = 0; s < range.numSections(); s++ )
739.     processSection( wordDocument, range.getSection( s ), s );
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
658. Range range = _doc.getOverallRange();
659. for ( int p = 0; p < range.numParagraphs(); p++ )
661.     Paragraph paragraph = range.getParagraph( p );
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
{% highlight java %}
104. Range r = doc.getRange();
106. for (int x = 0; x < r.numSections(); x++) {
107.   Section s = r.getSection(x);
{% endhighlight %}

***

### [WordUtil.java](https://searchcode.com/codesearch/view/69098620/)
{% highlight java %}
26. Range range = doc.getRange();
27. for (int i = 0; i < range.numCharacterRuns(); i++) {
28.   CharacterRun run = range.getCharacterRun(i);
{% endhighlight %}

***

