# ParagraphPropertyFetcher

***

## [Cluster 1 (fetch, ok, visitor)](./1)
1 results
> the the 
{% highlight java %}
1095. private boolean fetchParagraphProperty(ParagraphPropertyFetcher visitor){
1098.     if(_p.isSetPPr()) ok = visitor.fetch(_p.getPPr());
1109.                 if(themeProps != null) ok = visitor.fetch(themeProps);
1115.                 if(defaultProps != null) ok = visitor.fetch(defaultProps);
{% endhighlight %}

***

