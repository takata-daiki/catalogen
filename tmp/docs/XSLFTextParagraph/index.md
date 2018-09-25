# XSLFTextParagraph

***

## [Cluster 1 (copy, get, p2)](./1)
1 results
> set normal , super or subscript , that representing the vertical - alignment setting . 
{% highlight java %}
612. XSLFTextParagraph p2 = tgtP.get(i);
613. p2.copy(p1);
{% endhighlight %}

***

## [Cluster 2 (_paragraphs, draw, get)](./2)
1 results
> updates the text in the atom 
{% highlight java %}
534. XSLFTextParagraph p = _paragraphs.get(i);
536. y += p.draw(graphics, x, y);
{% endhighlight %}

***

## [Cluster 3 (void, wrappermethod, xslftextparagraph)](./3)
11 results
> used to ensure that 0 . the 
{% highlight java %}
24. XSLFTextParagraph p1 = txt1.addNewTextParagraph();
26. XSLFTextRun r1 = p1.addNewTextRun();
{% endhighlight %}

***

## [Cluster 4 (_p, characterpropertyfetcher, props)](./4)
1 results
> used to ensure that 0 . the 
{% highlight java %}
49. private final XSLFTextParagraph _p;
139.     final XSLFTheme theme = _p.getParentShape().getSheet().getTheme();
140.     CTShapeStyle style = _p.getParentShape().getSpStyle();
143.     CharacterPropertyFetcher<Color> fetcher = new CharacterPropertyFetcher<Color>(_p.getLevel()){
187.     CharacterPropertyFetcher<Double> fetcher = new CharacterPropertyFetcher<Double>(_p.getLevel()){
274.     CharacterPropertyFetcher<String> visitor = new CharacterPropertyFetcher<String>(_p.getLevel()){
298.     CharacterPropertyFetcher<Byte> visitor = new CharacterPropertyFetcher<Byte>(_p.getLevel()){
326.     CharacterPropertyFetcher<Boolean> fetcher = new CharacterPropertyFetcher<Boolean>(_p.getLevel()){
377.     CharacterPropertyFetcher<TextCap> fetcher = new CharacterPropertyFetcher<TextCap>(_p.getLevel()){
492.         XSLFTextShape shape = _p.getParentShape();
499.                 CTTextParagraphProperties themeProps = ppt.getDefaultParagraphStyle(_p.getLevel());
506.                 CTTextParagraphProperties defaultProps =  _p.getDefaultMasterStyle();
{% endhighlight %}

***

