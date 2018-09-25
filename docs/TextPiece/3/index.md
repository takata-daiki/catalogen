# TextPiece @Cluster 3 (currentpiece, currenttextend, currenttextstart)

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/138792453/)
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

