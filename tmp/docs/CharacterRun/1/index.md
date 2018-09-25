# CharacterRun @Cluster 1 (getstartoffset, hwpfdocument, three)

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> set the contents of this shape to be a copy of the source shape . < p > the 0 is specified in points . positive values will be 2 , but use to be a shape must not be shape . 
{% highlight java %}
824. CharacterRun separator = field
827. if ( separator.isOle2() )
{% endhighlight %}

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
> sets the 
{% highlight java %}
42. CharacterRun run3 = par3.insertAfter("three three three three three three three three three "
45. run3.setItalic(true);
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the 
{% highlight java %}
744. CharacterRun characterRun, Element block )
751.     .getOfficeDrawingAt( characterRun.getStartOffset() );
772.             "s" + characterRun.getStartOffset() + "." + type,
{% endhighlight %}

***

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
> test that we get the same value as excel and , for 
{% highlight java %}
205.     CharacterRun characterRun, String text )
220. if ( characterRun.getFontSize() / 2 != blockProperies.pFontSize )
222.     style.append( "font-size:" + characterRun.getFontSize() / 2 + "pt;" );
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
> set the when the user provided the name is a . that file is a name ( border is a 3 and " a range " , " and " a 1 " ) in the cell < p > 
{% highlight java %}
160. CharacterRun run = p.getCharacterRun(z);
166. if (run.isBold()) {
182. String text = run.text();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the a 1 - style object , the is it and returns a to 0 . 
{% highlight java %}
191. protected Triplet getCharacterRunTriplet( CharacterRun characterRun )
194.     original.bold = characterRun.isBold();
195.     original.italic = characterRun.isItalic();
196.     original.fontName = characterRun.getFontName();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the named range name . @ param data the < code > workbook < / code > to be used to @ param a < code > true < / code > if the specified sheet is currently ' in the area table 
{% highlight java %}
332. CharacterRun characterRun = range.getCharacterRun( c );
335. if ( characterRun.getStartOffset() < skipUntil )
337. String text = characterRun.text();
344.                 characterRun.getStartOffset() );
359.                         characterRun.getStartOffset(), range
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets the link number . 
{% highlight java %}
479. CharacterRun characterRun = range.getCharacterRun( c );
492.     processImage( block, characterRun.text().charAt( 0 ) == 0x01,
497. String text = characterRun.text();
501. if ( characterRun.isSpecialCharacter() )
517.     if ( characterRun.isOle2()
524.     if ( characterRun.isSymbol()
540.                         characterRun.getStartOffset() );
580. if ( characterRun.isSpecialCharacter() || characterRun.isObj()
581.         || characterRun.isOle2() )
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> sets all the from text @ param an array of the document in the list . 
{% highlight java %}
281. CharacterRun characterRun, String text );
335.     if ( characterRun.getStartOffset() < skipUntil )
337.     String text = characterRun.text();
344.                     characterRun.getStartOffset() );
359.                             characterRun.getStartOffset(), range
492.     processImage( block, characterRun.text().charAt( 0 ) == 0x01,
497. String text = characterRun.text();
501. if ( characterRun.isSpecialCharacter() )
517.     if ( characterRun.isOle2()
524.     if ( characterRun.isSymbol()
540.                         characterRun.getStartOffset() );
580. if ( characterRun.isSpecialCharacter() || characterRun.isObj()
581.         || characterRun.isOle2() )
{% endhighlight %}

***

