# Paragraph @Cluster 1

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
1061. Paragraph paragraph = range.getParagraph( p );
1063. if ( paragraph.isInTable()
1064.         && paragraph.getTableLevel() != currentTableLevel )
1066.     if ( paragraph.getTableLevel() < currentTableLevel )
1069.                         + paragraph.getTableLevel()
1082. if ( paragraph.text().equals( "\u000c" ) )
1088. if ( paragraph.isInList() )
1092.         HWPFList hwpfList = paragraph.getList();
1096.                 (char) paragraph.getIlvl() );
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
1052. Element parentElement, int currentTableLevel, Paragraph paragraph,
1063. if ( paragraph.isInTable()
1064.         && paragraph.getTableLevel() != currentTableLevel )
1066.     if ( paragraph.getTableLevel() < currentTableLevel )
1069.                         + paragraph.getTableLevel()
1082. if ( paragraph.text().equals( "\u000c" ) )
1088. if ( paragraph.isInList() )
1092.         HWPFList hwpfList = paragraph.getList();
1096.                 (char) paragraph.getIlvl() );
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
661. Paragraph paragraph = range.getParagraph( p );
662. System.out.println( p + ":\t" + paragraph.toString() );
665.     System.out.println( paragraph.text() );
{% endhighlight %}

***

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
{% highlight java %}
507.     Element parentElement, int currentTableLevel, Paragraph paragraph,
516. final int charRuns = paragraph.numCharacterRuns();
526.     final CharacterRun characterRun = paragraph.getCharacterRun( 0 );
553.             float firstLinePosition = paragraph.getIndentFromLeft()
554.                     + paragraph.getFirstLineIndent() + 20; // char have
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
{% highlight java %}
112. Paragraph p = s.getParagraph(y);
114.         if (p.isInTable()) {
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
{% highlight java %}
139. private static org.docx4j.wml.P handleP(Paragraph p,
146.   if (p.getStyleIndex() > 0) {
147.     log.debug("Styled paragraph, with index: " + p.getStyleIndex());
149.         .getStyleDescription(p.getStyleIndex()).getName();
158.   for (int z = 0; z < p.numCharacterRuns(); z++) {
160.     CharacterRun run = p.getCharacterRun(z);
{% endhighlight %}

***

### [FilesGatherer.java](https://searchcode.com/codesearch/view/13078978/)
{% highlight java %}
176. Paragraph p = r.getParagraph(x);
177. String text = p.text();
{% endhighlight %}

***

### [FilesGatherer.java](https://searchcode.com/codesearch/view/13078978/)
{% highlight java %}
136. Paragraph p = r.getParagraph(x);
137. String text = p.text();
{% endhighlight %}

***

### [test.java](https://searchcode.com/codesearch/view/13078982/)
{% highlight java %}
24. Paragraph p = r.getParagraph(x);
25. String text = p.text();
{% endhighlight %}

***

### [test.java](https://searchcode.com/codesearch/view/13078982/)
{% highlight java %}
45. Paragraph p = r.getParagraph(x);
46. String text = p.text();
{% endhighlight %}

***

