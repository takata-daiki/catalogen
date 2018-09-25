# XWPFHeaderFooterPolicy @Cluster 2 (element, hfpolicy, xhtml)

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
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

