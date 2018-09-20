# XSLFTextParagraph @Cluster 4

***

### [XSLFTextRun.java](https://searchcode.com/codesearch/view/97406808/)
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

