# XWPFHeaderFooterPolicy @Cluster 3

***

### [WordOOXMLDocument.java](https://searchcode.com/codesearch/view/126168429/)
{% highlight java %}
145. private void extractHeaders(final StringBuilder buffy, final XWPFHeaderFooterPolicy hfPolicy) {
146.   if (hfPolicy.getFirstPageHeader() != null) {
147.     buffy.append(hfPolicy.getFirstPageHeader().getText()).append(' ');
149.   if (hfPolicy.getEvenPageHeader() != null) {
150.     buffy.append(hfPolicy.getEvenPageHeader().getText()).append(' ');
152.   if (hfPolicy.getDefaultHeader() != null) {
153.     buffy.append(hfPolicy.getDefaultHeader().getText()).append(' ');
{% endhighlight %}

***

