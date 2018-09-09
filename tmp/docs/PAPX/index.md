# PAPX

***

### [Cluster 1](./1)
{% highlight java %}
560. for ( PAPX papx : pfkp.getPAPXs() )
567.                 papx.getGrpprl(), 2 );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
178. protected Paragraph( PAPX papx, Range parent )
180.     super( Math.max( parent._start, papx.getStart() ), Math.min(
181.             parent._end, papx.getEnd() ), parent );
182.     _props = papx.getParagraphProperties( _doc.getStyleSheet() );
183.     _papx = papx.getSprmBuf();
184.     _istd = papx.getIstd();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
171. PAPX papx = _paragraphs.get( _parEnd - 1 );
172. _props = papx.getParagraphProperties( _doc.getStyleSheet() );
173. _papx = papx.getSprmBuf();
174. _istd = papx.getIstd();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
150.     StyleSheet styleSheet, PAPX papx, ParagraphProperties properties )
155. int style = papx.getIstd();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
259. PAPX papx = null;
264.     byte[] phe = papx.getParagraphHeight().toByteArray();
265.     byte[] grpprl = papx.getGrpprl();
319.             translator.getByteIndex( papx.getStart() ) );
351.         translator.getByteIndex( papx.getEnd() ) );
{% endhighlight %}

***

