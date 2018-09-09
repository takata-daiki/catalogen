# XWPFRun

***

### [Cluster 1](./1)
{% highlight java %}
64. XWPFRun r1 = p1.createRun();
76.   r1.setText("png");
77.   r1.addBreak();
79.   r1.addPicture(new ByteArrayInputStream(bytes),
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
229. final XWPFRun run = new XWPFRun(ctp.addNewR(), paragraph);
233.           run.setText(lines[0], 0);
235.               run.addBreak();
236.               run.setText(lines[iLine]);
239.           run.setText(formattedValue, 0);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
48. public CTRPr getRPr( XWPFRun run )
50.     return run.getCTR().getRPr();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
217. public static String getBackgroundColor( XWPFRun run )
219.     CTR ctr = run.getCTR();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
273. public static StringBuilder getStyle( XWPFRun run, XWPFStyle runStyle, XWPFStyle style, CTDocDefaults defaults )
290.     float fontSize = run.getFontSize();
314.     UnderlinePatterns underlinePatterns = run.getUnderline();
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
100. protected String[] getStyleID( XWPFRun run )
102.     XWPFParagraph paragraph = run.getParagraph();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
723. protected void visitRun( XWPFRun run, boolean pageNumber, String url, T paragraphContainer )
727.     CTR ctr = run.getCTR();
771.                 CTTabs tabs = stylesDocument.getParagraphTabs( run.getParagraph() );
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
284. protected void visitPictures( XWPFRun run, T parentContainer )
287.     List<XWPFPicture> embeddedPictures = run.getEmbeddedPictures();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
413. protected void visitRun( XWPFRun run, IITextContainer pdfContainer )
417.     CTR ctr = run.getCTR();
459.     UnderlinePatterns underlinePatterns = run.getUnderline();
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
272. private void replaceBookmark(CTBookmark bookmark, XWPFRun run,
317.                   run.getCTR().getDomNode().insertBefore(
318.                           styleNode.cloneNode(true), run.getCTR().getDomNode().getFirstChild());
334.               run.getCTR().getDomNode(), nextNode);
{% endhighlight %}

***

