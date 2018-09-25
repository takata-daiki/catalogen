# PropertySet

***

## [Cluster 1 (back, int, warn)](./1)
2 results
> @ throws nullpointerexception if cell 1 is null ( fixed position ) @ see org . apache . poi . ss . usermodel . clientanchor # or ( short ) 
{% highlight java %}
123. PropertySet ps;
130.   logger.log(POILogger.WARN, "DocumentSummaryInformation property set came back with wrong class - ", ps.getClass());
138.   logger.log(POILogger.WARN, "SummaryInformation property set came back with wrong class - ", ps.getClass());
{% endhighlight %}

***

## [Cluster 2 (if, new, ps)](./2)
2 results
> < p > returns the poi file ' s sheet . < / p > @ return the poi file ' s variant type . 
{% highlight java %}
84. final PropertySet ps = new PropertySet(stream);
87.     if (ps.isSummaryInformation())
89.     else if (ps.isDocumentSummaryInformation())
{% endhighlight %}

***

## [Cluster 3 (if, new, properties)](./3)
1 results
> < p > writes an unsigned two - byte value to an output stream . < / p > 
{% highlight java %}
67. PropertySet properties =
69. if (properties.isSummaryInformation()) {
72. if (properties.isDocumentSummaryInformation()) {
{% endhighlight %}

***

