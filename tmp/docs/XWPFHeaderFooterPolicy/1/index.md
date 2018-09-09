# XWPFHeaderFooterPolicy @Cluster 1

***

### [WordOOXMLDocument.java](https://searchcode.com/codesearch/view/126168429/)
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

