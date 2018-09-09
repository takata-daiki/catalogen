# TextPiece

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
317. final TextPiece textPiece = new SinglentonTextPiece( _text );
319. _text = textPiece.getStringBuilder();
{% endhighlight %}

***

