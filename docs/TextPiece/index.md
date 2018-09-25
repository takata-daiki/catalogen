# TextPiece

***

## [Cluster 1 (out, println, textpiece)](./1)
1 results
> sets the list of colours that are interpolated between . 
{% highlight java %}
753. for ( TextPiece textPiece : _doc.getTextTable().getTextPieces() )
759.         System.out.println( "\t" + textPiece.getStringBuilder() );
{% endhighlight %}

***

## [Cluster 2 (_text, final, textpiece)](./2)
1 results
> sets the 
{% highlight java %}
317. final TextPiece textPiece = new SinglentonTextPiece( _text );
319. _text = textPiece.getStringBuilder();
{% endhighlight %}

***

## [Cluster 3 (currentpiece, currenttextend, currenttextstart)](./3)
1 results
> set default character width from the workbook ' s default font @ param an 
{% highlight java %}
139. TextPiece currentPiece = (TextPiece)textIt.next();
140. int currentTextStart = currentPiece.getStart();
141. int currentTextEnd = currentPiece.getEnd();
162.     currentTextStart = currentPiece.getStart ();
163.     currentTextEnd = currentPiece.getEnd ();
168.     String str = currentPiece.substring(runStart - currentTextStart, runEnd - currentTextStart);
175.       String str = currentPiece.substring(runStart - currentTextStart,
181.         currentTextStart = currentPiece.getStart ();
183.         currentTextEnd = currentPiece.getEnd ();
190.     String str = currentPiece.substring(0, runEnd - currentTextStart);
199.       currentTextStart = currentPiece.getStart();
200.       currentTextEnd = currentPiece.getEnd();
{% endhighlight %}

***

