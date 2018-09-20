# XSLFTextRun

***

## [Cluster 1](./1)
12 results
> sets the 
{% highlight java %}
125. for (XSLFTextRun r : _runs) {
126.     out.append(r.getRenderableText());
{% endhighlight %}

***

## [Cluster 2](./2)
6 results
> @ since 3 . 1 5 beta 2 
{% highlight java %}
26. XSLFTextRun r1 = p1.addNewTextRun();
27. r1.setText("Cost estimation");
28. r1.setFontFamily("Verdana");
29. r1.setFontColor(new Color(255, 0, 102));
30. r1.setFontSize(32);
31. r1.setBold(true);
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> this comment could not be generated...
{% highlight java %}
941. XSLFTextRun r = addNewTextRun();
942. r.setText(" ");
945.     if(endPr.isSetSz()) r.setFontSize(endPr.getSz() / 100);
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
861. for (XSLFTextRun run : _runs){
862.     int length = run.getRenderableText().length();
872.     if (run.isBold() && run.isItalic()) {
874.     } else if (run.isItalic()) {
876.     } else if (run.isBold()) {
880.     ColorStateList color = ColorStateList.valueOf(run.getFontColor().getRGB());
882.     at.setSpan(new TextAppearanceSpan(run.getFontFamily(), style, (int) run.getFontSize(),
902.     if(run.isUnderline()) {
905.     if(run.isStrikethrough()) {
916.     if (run.isSuperscript()) {
919.     if (run.isSubscript()) {
{% endhighlight %}

***

