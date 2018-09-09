# XWPFHeaderFooterPolicy @Cluster 2

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
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

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
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

