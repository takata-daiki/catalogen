# TextPiece @Cluster 2

***

### [HWPFDocument.java](https://searchcode.com/codesearch/view/97383956/)
{% highlight java %}
317. final TextPiece textPiece = new SinglentonTextPiece( _text );
319. _text = textPiece.getStringBuilder();
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
753. for ( TextPiece textPiece : _doc.getTextTable().getTextPieces() )
759.         System.out.println( "\t" + textPiece.getStringBuilder() );
{% endhighlight %}

***

