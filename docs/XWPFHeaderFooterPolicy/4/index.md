# XWPFHeaderFooterPolicy @Cluster 4 (append, buffy, hfpolicy)

***

### [WordOOXMLDocument.java](https://searchcode.com/codesearch/view/126168429/)
> replace that for a given block , we ' re ( the same if the same one does not contain 
{% highlight java %}
133. private void extractFooters(final StringBuilder buffy, final XWPFHeaderFooterPolicy hfPolicy) {
134.   if (hfPolicy.getFirstPageFooter() != null) {
135.     buffy.append(hfPolicy.getFirstPageFooter().getText()).append(' ');
137.   if (hfPolicy.getEvenPageFooter() != null) {
138.     buffy.append(hfPolicy.getEvenPageFooter().getText()).append(' ');
140.   if (hfPolicy.getDefaultFooter() != null) {
141.     buffy.append(hfPolicy.getDefaultFooter().getText()).append(' ');
{% endhighlight %}

***

