# ExcelExtractor

***

## [Cluster 1 (exceltext, false, fs)](./1)
2 results
> test that we get the same value as excel and , for 
{% highlight java %}
23. ExcelExtractor extractor = new ExcelExtractor(fs);
24. String excelText = extractor.getText();
{% endhighlight %}

***

## [Cluster 2 (excelextractor, extractor, false)](./2)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
160. ExcelExtractor extractor = null;
166.   extractor.setFormulasNotResults(true);
167.   extractor.setIncludeSheetNames(false);
171. return extractor.getText();
{% endhighlight %}

***

