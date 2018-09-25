# XWPFHeaderFooterPolicy @Cluster 1 (element, hfpolicy, xhtml)

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
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

