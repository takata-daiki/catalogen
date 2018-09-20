# DocumentSummaryInformation

***

## [Cluster 1](./1)
3 results
> sets the string value of the given map . 
{% highlight java %}
261. final DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation ();
262. dsi.setCompany ( "TH4 SYSTEMS GmbH" );
266. dsi.setCustomProperties ( cp );
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> sets the formula expression the file to . 1 2 7 0 0 0 . this value is one of a . @ param data @ param offset @ param stream @ return 
{% highlight java %}
109. private void parse(DocumentSummaryInformation summary) {
110.     set(Metadata.COMPANY, summary.getCompany());
111.     set(Metadata.MANAGER, summary.getManager());
113.     set(Metadata.CATEGORY, summary.getCategory());
114.     set(Metadata.SLIDE_COUNT, summary.getSlideCount());
115.     if (summary.getSlideCount() > 0) {
116.         metadata.set(PagedText.N_PAGES, summary.getSlideCount());
{% endhighlight %}

***

