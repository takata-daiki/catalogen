# CharacterPropertyFetcher @Cluster 1 (firstcellreference, getrow, true)

***

### [XSLFTextRun.java](https://searchcode.com/codesearch/view/97406808/)
> set whether the text should be wrapped @ param wrapped a boolean value indicating if the text in a cell should be line - wrapped within the cell . 
{% highlight java %}
486. private boolean fetchCharacterProperty(CharacterPropertyFetcher fetcher){
489.     if(_r.isSetRPr()) ok = fetcher.fetch(getRPr());
501.                     fetcher.isFetchingFromMaster = true;
502.                     ok = fetcher.fetch(themeProps);
509.                     ok = fetcher.fetch(defaultProps);
{% endhighlight %}

***

