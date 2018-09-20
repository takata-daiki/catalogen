# Range @Cluster 1

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
{% highlight java %}
22. Range range = doc.getRange();
23. Paragraph par1 = range.insertAfter(new ParagraphProperties(), 0);
{% endhighlight %}

***

### [HWPFDocument.java](https://searchcode.com/codesearch/view/97383956/)
{% highlight java %}
1039. Range r = new Range(start, start + length, this);
1040. r.delete();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
641. Range formulaRange = new Range( range.getCharacterRun(
661. String formula = formulaRange.text();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
992. Range endnoteRange = doc.getEndnoteRange();
993. int rangeStartOffset = endnoteRange.getStartOffset();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
970. Range footnoteRange = doc.getFootnoteRange();
971. int rangeStartOffset = footnoteRange.getStartOffset();
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

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
1163.     HWPFDocumentCore wordDocument, Range range, int beginMark )
1167. for ( int c = beginMark + 1; c < range.numCharacterRuns(); c++ )
1169.     CharacterRun characterRun = range.getCharacterRun( c );
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
1056.     Element flow, Range range, int currentTableLevel )
1058. final int paragraphs = range.numParagraphs();
1061.     Paragraph paragraph = range.getParagraph( p );
1074.         Table table = range.getTable( paragraph );
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
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

### [PrintTemplateServiceImpl.java](https://searchcode.com/codesearch/view/94110212/)
{% highlight java %}
1352. Range range = hdt.getRange();
1353. String text = range.text();
{% endhighlight %}

***

### [WordUtil.java](https://searchcode.com/codesearch/view/69098620/)
{% highlight java %}
26. Range range = doc.getRange();
27. for (int i = 0; i < range.numCharacterRuns(); i++) {
28.   CharacterRun run = range.getCharacterRun(i);
{% endhighlight %}

***

### [WordExport.java](https://searchcode.com/codesearch/view/134954814/)
{% highlight java %}
171. final Range range = new Range(b.getStart(), b.getEnd(), doc);
172. range.replaceText(range.text(), CollectableFieldFormat.getInstance(def.getJavaClass()).format(def.getOutputformat(), value));
{% endhighlight %}

***

### [FilesGatherer.java](https://searchcode.com/codesearch/view/13078978/)
{% highlight java %}
129. Range r = doc.getRange(); // ??word?????
130. int lenParagraph = r.numParagraphs();// ?????
136.   Paragraph p = r.getParagraph(x);
{% endhighlight %}

***

### [test.java](https://searchcode.com/codesearch/view/13078982/)
{% highlight java %}
17. Range r = doc.getRange(); // ??word?????
18. int lenParagraph = r.numParagraphs();// ?????
24.   Paragraph p = r.getParagraph(x);
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
188. protected Paragraph( PAPX papx, Range parent, int start )
190.     super( Math.max( parent._start, start ), Math.min( parent._end,
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
99. static Paragraph newParagraph( Range parent, PAPX papx )
101.     HWPFDocumentCore doc = parent._doc;
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
178. protected Paragraph( PAPX papx, Range parent )
180.     super( Math.max( parent._start, papx.getStart() ), Math.min(
181.             parent._end, papx.getEnd() ), parent );
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
198. Paragraph( PAPX papx, ParagraphProperties properties, Range parent )
200.     super( Math.max( parent._start, papx.getStart() ), Math.min(
201.             parent._end, papx.getEnd() ), parent );
{% endhighlight %}

***

### [CharacterRun.java](https://searchcode.com/codesearch/view/97384484/)
{% highlight java %}
98. CharacterRun(CHPX chpx, StyleSheet ss, short istd, Range parent)
100.   super(Math.max(parent._start, chpx.getStart()), Math.min(parent._end, chpx.getEnd()), parent);
{% endhighlight %}

***

