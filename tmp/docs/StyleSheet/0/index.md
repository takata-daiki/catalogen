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

### [StyleSheet.java](https://searchcode.com/codesearch/view/97384100/)
{% highlight java %}
179. StyleSheet ss = (StyleSheet)o;
181. if (ss._stshif.equals( this._stshif ) && ss._cbStshi == _cbStshi)
183.   if (ss._styleDescriptions.length == _styleDescriptions.length)
188.       if (ss._styleDescriptions[x] != _styleDescriptions[x])
191.         if (!ss._styleDescriptions[x].equals(_styleDescriptions[x]))
{% endhighlight %}

***

