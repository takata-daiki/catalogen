# PowerPointExtractor @Cluster 1 (close, pptextractor, true)

***

### [MSPowerpointIndexer.java](https://searchcode.com/codesearch/view/95551281/)
> sets the text of this run of the paragraph 
{% highlight java %}
23. PowerPointExtractor extractor = new PowerPointExtractor(fs);
24. String ppText = extractor.getText();
{% endhighlight %}

***

### [DocumentFileParsing.java](https://searchcode.com/codesearch/view/76013528/)
> get the document part that ' s defined as the given relationship of the given property . 
{% highlight java %}
38. PowerPointExtractor extractor  = new PowerPointExtractor(fs);  
39. return extractor.getText();
{% endhighlight %}

***

### [IndexerBean.java](https://searchcode.com/codesearch/view/8591933/)
> tests that we can load some streams 
{% highlight java %}
169. PowerPointExtractor pptExtractor = new PowerPointExtractor(pptStream);
170. Reader contentReader = new StringReader(pptExtractor.getText(true, true));
173. pptExtractor.close();
{% endhighlight %}

***

