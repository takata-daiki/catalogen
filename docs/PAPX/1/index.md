# PAPX @Cluster 1

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
150.     StyleSheet styleSheet, PAPX papx, ParagraphProperties properties )
155. int style = papx.getIstd();
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
171. PAPX papx = _paragraphs.get( _parEnd - 1 );
172. _props = papx.getParagraphProperties( _doc.getStyleSheet() );
173. _papx = papx.getSprmBuf();
174. _istd = papx.getIstd();
{% endhighlight %}

***

### [PAPFormattedDiskPage.java](https://searchcode.com/codesearch/view/97384195/)
{% highlight java %}
259. PAPX papx = null;
264.     byte[] phe = papx.getParagraphHeight().toByteArray();
265.     byte[] grpprl = papx.getGrpprl();
319.             translator.getByteIndex( papx.getStart() ) );
351.         translator.getByteIndex( papx.getEnd() ) );
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
188. protected Paragraph( PAPX papx, Range parent, int start )
191.             papx.getEnd() ), parent );
192.     _props = papx.getParagraphProperties( _doc.getStyleSheet() );
193.     _papx = papx.getSprmBuf();
194.     _istd = papx.getIstd();
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
198. Paragraph( PAPX papx, ParagraphProperties properties, Range parent )
200.     super( Math.max( parent._start, papx.getStart() ), Math.min(
201.             parent._end, papx.getEnd() ), parent );
203.     _papx = papx.getSprmBuf();
204.     _istd = papx.getIstd();
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
178. protected Paragraph( PAPX papx, Range parent )
180.     super( Math.max( parent._start, papx.getStart() ), Math.min(
181.             parent._end, papx.getEnd() ), parent );
182.     _props = papx.getParagraphProperties( _doc.getStyleSheet() );
183.     _papx = papx.getSprmBuf();
184.     _istd = papx.getIstd();
{% endhighlight %}

***

