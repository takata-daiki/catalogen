# XWPFHeaderFooterPolicy

***

## [Cluster 1](./1)
1 results
> test that we get the same value as excel and , for 
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

