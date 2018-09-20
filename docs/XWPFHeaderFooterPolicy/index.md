# XWPFHeaderFooterPolicy

***

## [Cluster 1](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
114.     XHTMLContentHandler xhtml, XWPFHeaderFooterPolicy hfPolicy)
116. if (hfPolicy.getFirstPageHeader() != null) {
117.     xhtml.element("p", hfPolicy.getFirstPageHeader().getText());
119. if (hfPolicy.getEvenPageHeader() != null) {
120.     xhtml.element("p", hfPolicy.getEvenPageHeader().getText());
122. if (hfPolicy.getDefaultHeader() != null) {
123.     xhtml.element("p", hfPolicy.getDefaultHeader().getText());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> sets the a number of the formula that 1 between 0 and ( the first 1 sheet 3 ) . default is 0 . @ param name the name to add @ param 
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

## [Cluster 3](./3)
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

## [Cluster 4](./4)
1 results
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

