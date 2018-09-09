# DocumentSummaryInformation

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
48. DocumentSummaryInformation dsi = doc.getDocumentSummaryInformation();
49. CustomProperties cp = dsi.getCustomProperties();
53. dsi.setCustomProperties(cp);
{% endhighlight %}

***

