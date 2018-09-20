# PowerPointExtractor @Cluster 1

***

### [DocumentFileParsing.java](https://searchcode.com/codesearch/view/76013528/)
{% highlight java %}
38. PowerPointExtractor extractor  = new PowerPointExtractor(fs);  
39. return extractor.getText();
{% endhighlight %}

***

### [MSPowerpointIndexer.java](https://searchcode.com/codesearch/view/95551281/)
{% highlight java %}
23. PowerPointExtractor extractor = new PowerPointExtractor(fs);
24. String ppText = extractor.getText();
{% endhighlight %}

***

### [PptParserPOI.java](https://searchcode.com/codesearch/view/93256535/)
{% highlight java %}
51. PowerPointExtractor extractor = new PowerPointExtractor(file);
52. String text = extractor.getText(true, true);
{% endhighlight %}

***

### [IndexerBean.java](https://searchcode.com/codesearch/view/8591933/)
{% highlight java %}
169. PowerPointExtractor pptExtractor = new PowerPointExtractor(pptStream);
170. Reader contentReader = new StringReader(pptExtractor.getText(true, true));
173. pptExtractor.close();
{% endhighlight %}

***

### [PowerPointFormatModule.java](https://searchcode.com/codesearch/view/12809878/)
{% highlight java %}
33. PowerPointExtractor extractor = new PowerPointExtractor(new BufferedInputStream(new ByteArrayInputStream(cc.getContent())));
34. String s = extractor.getText();
{% endhighlight %}

***

