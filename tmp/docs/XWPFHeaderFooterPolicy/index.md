# XWPFHeaderFooterPolicy

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
99.     XHTMLContentHandler xhtml, XWPFHeaderFooterPolicy hfPolicy)
102. if (hfPolicy.getFirstPageFooter() != null) {
103.     xhtml.element("p", hfPolicy.getFirstPageFooter().getText());
105. if (hfPolicy.getEvenPageFooter() != null) {
106.     xhtml.element("p", hfPolicy.getEvenPageFooter().getText());
108. if (hfPolicy.getDefaultFooter() != null) {
109.     xhtml.element("p", hfPolicy.getDefaultFooter().getText());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
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

## [Cluster 3](./3)
1 results
> code comments is here.
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

