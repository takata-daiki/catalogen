# StyleSheet @Cluster 1

***

### [HWPFList.java](https://searchcode.com/codesearch/view/97384433/)
{% highlight java %}
63. private StyleSheet _styleSheet;
219.     CharacterProperties base = _styleSheet.getCharacterStyle( styleIndex );
238.     ParagraphProperties base = _styleSheet.getParagraphStyle( styleIndex );
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
150.     StyleSheet styleSheet, PAPX papx, ParagraphProperties properties )
156. byte[] grpprl = styleSheet.getPAPX( style );
{% endhighlight %}

***

### [CharacterSprmUncompressor.java](https://searchcode.com/codesearch/view/97384370/)
{% highlight java %}
53. public static CharacterProperties uncompressCHP( StyleSheet styleSheet,
76.             applySprms( parStyle, styleSheet.getCHPX( style ), 0, false,
{% endhighlight %}

***

### [Doc.java](https://searchcode.com/codesearch/view/17642935/)
{% highlight java %}
140. org.apache.poi.hwpf.model.StyleSheet stylesheet,
148. String styleName = stylesheet
{% endhighlight %}

***

### [CHPX.java](https://searchcode.com/codesearch/view/97384232/)
{% highlight java %}
66. public CharacterProperties getCharacterProperties( StyleSheet ss, short istd )
74.     CharacterProperties baseStyle = ss.getCharacterStyle( istd );
{% endhighlight %}

***

