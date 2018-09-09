# Range @Cluster 5

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
99. static Paragraph newParagraph( Range parent, PAPX papx )
101.     HWPFDocumentCore doc = parent._doc;
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
178. protected Paragraph( PAPX papx, Range parent )
180.     super( Math.max( parent._start, papx.getStart() ), Math.min(
181.             parent._end, papx.getEnd() ), parent );
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
188. protected Paragraph( PAPX papx, Range parent, int start )
190.     super( Math.max( parent._start, start ), Math.min( parent._end,
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
198. Paragraph( PAPX papx, ParagraphProperties properties, Range parent )
200.     super( Math.max( parent._start, papx.getStart() ), Math.min(
201.             parent._end, papx.getEnd() ), parent );
{% endhighlight %}

***

