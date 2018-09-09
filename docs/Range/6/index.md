# Range @Cluster 6

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

