# CharacterPropertyFetcher @Cluster 1

***

### [XSLFTextRun.java](https://searchcode.com/codesearch/view/97406808/)
{% highlight java %}
486. private boolean fetchCharacterProperty(CharacterPropertyFetcher fetcher){
489.     if(_r.isSetRPr()) ok = fetcher.fetch(getRPr());
501.                     fetcher.isFetchingFromMaster = true;
502.                     ok = fetcher.fetch(themeProps);
509.                     ok = fetcher.fetch(defaultProps);
{% endhighlight %}

***

