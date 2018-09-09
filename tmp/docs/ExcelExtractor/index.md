# ExcelExtractor

***

### [Cluster 1](./1)
{% highlight java %}
68. ExcelExtractor extractor = new ExcelExtractor((HSSFWorkbook)wb);
69. extractor.setFormulasNotResults(true);
70. extractor.setIncludeSheetNames(false);
71. text = extractor.getText();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
33. ExcelExtractor ex = new ExcelExtractor(fs);
34. return ex.getText();
{% endhighlight %}

***

