# CharacterRun @Cluster 1

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
1169. CharacterRun characterRun = range.getCharacterRun( c );
1171. String text = characterRun.text();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
824. CharacterRun separator = field
827. if ( separator.isOle2() )
{% endhighlight %}

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
{% highlight java %}
42. CharacterRun run3 = par3.insertAfter("three three three three three three three three three "
45. run3.setItalic(true);
{% endhighlight %}

***

### [WordUtil.java](https://searchcode.com/codesearch/view/69098620/)
{% highlight java %}
28. CharacterRun run = range.getCharacterRun(i);
29. String text = run.text();
33.   run.replaceText(map.get(text), true);
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
961. CharacterRun characterRun, final Element block )
966.         .getNoteIndexByAnchorPosition( characterRun
988. int noteIndex = endnotes.getNoteIndexByAnchorPosition( characterRun
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
744. CharacterRun characterRun, Element block )
751.     .getOfficeDrawingAt( characterRun.getStartOffset() );
772.             "s" + characterRun.getStartOffset() + "." + type,
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
848. CharacterRun cr = fieldContent.getCharacterRun( fieldContent
850. String[] values = cr.getDropDownListValues();
851. Integer defIndex = cr.getDropDownListDefaultItemIndex();
{% endhighlight %}

***

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
{% highlight java %}
205.     CharacterRun characterRun, String text )
220. if ( characterRun.getFontSize() / 2 != blockProperies.pFontSize )
222.     style.append( "font-size:" + characterRun.getFontSize() / 2 + "pt;" );
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
{% highlight java %}
160. CharacterRun run = p.getCharacterRun(z);
166. if (run.isBold()) {
182. String text = run.text();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
1010. private boolean processOle2( HWPFDocument doc, CharacterRun characterRun,
1014.             "_" + characterRun.getPicOffset() );
1018.                 Integer.valueOf( characterRun.getPicOffset() ),
1031.                 Integer.valueOf( characterRun.getPicOffset() ), "': ", exc,
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
191. protected Triplet getCharacterRunTriplet( CharacterRun characterRun )
194.     original.bold = characterRun.isBold();
195.     original.italic = characterRun.isItalic();
196.     original.fontName = characterRun.getFontName();
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
332. CharacterRun characterRun = range.getCharacterRun( c );
335. if ( characterRun.getStartOffset() < skipUntil )
337. String text = characterRun.text();
344.                 characterRun.getStartOffset() );
359.                         characterRun.getStartOffset(), range
{% endhighlight %}

***

### [CharacterRun.java](https://searchcode.com/codesearch/view/97384484/)
{% highlight java %}
563. CharacterRun cp = (CharacterRun)super.clone();
564. cp._props.setDttmRMark((DateAndTime)_props.getDttmRMark().clone());
565. cp._props.setDttmRMarkDel((DateAndTime)_props.getDttmRMarkDel().clone());
566. cp._props.setDttmPropRMark((DateAndTime)_props.getDttmPropRMark().clone());
567. cp._props.setDttmDispFldRMark((DateAndTime)_props.getDttmDispFldRMark().
569. cp._props.setXstDispFldRMark(_props.getXstDispFldRMark().clone());
570. cp._props.setShd((ShadingDescriptor)_props.getShd().clone());
{% endhighlight %}

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
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

