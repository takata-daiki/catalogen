# XWPFDocument @Cluster 3

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
{% highlight java %}
54. XWPFDocument document = (XWPFDocument) extractor.getDocument();
55. XWPFHeaderFooterPolicy hfPolicy = document.getHeaderFooterPolicy();
61. Iterator<XWPFParagraph> i = document.getParagraphsIterator();
{% endhighlight %}

***

### [XWPFWordExtractorDecorator.java](https://searchcode.com/codesearch/view/111785573/)
{% highlight java %}
166. XWPFDocument document = (XWPFDocument) extractor.getDocument();
169. parts.add( document.getPackagePart() );
{% endhighlight %}

***

### [XWPFElementVisitor.java](https://searchcode.com/codesearch/view/12208676/)
{% highlight java %}
65. public XWPFElementVisitor( XWPFDocument document )
70.         this.defaults = document.getStyle().getDocDefaults();
{% endhighlight %}

***

### [XHTMLStyleUtil.java](https://searchcode.com/codesearch/view/12208720/)
{% highlight java %}
72. public static StringBuilder getStyle( XWPFDocument document, CTDocDefaults defaults )
75.     CTSectPr sectPr = document.getDocument().getBody().getSectPr();
{% endhighlight %}

***

### [OLDXHTMLMapper.java](https://searchcode.com/codesearch/view/12208721/)
{% highlight java %}
60. public OLDXHTMLMapper( XWPFDocument document )
66.         defaults = document.getStyle().getDocDefaults();
{% endhighlight %}

***

