# XSLFTextRun @Cluster 3

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
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

