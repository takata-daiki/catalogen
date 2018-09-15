# DocumentSummaryInformation

***

## [Cluster 1](./1)
1 results
> code comments is here.
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

