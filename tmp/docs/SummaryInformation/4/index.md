# SummaryInformation @Cluster 4

***

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
{% highlight java %}
261.     SummaryInformation summaryInformation )
263. if ( WordToHtmlUtils.isNotEmpty( summaryInformation.getTitle() ) )
264.     htmlDocumentFacade.setTitle( summaryInformation.getTitle() );
266. if ( WordToHtmlUtils.isNotEmpty( summaryInformation.getAuthor() ) )
267.     htmlDocumentFacade.addAuthor( summaryInformation.getAuthor() );
269. if ( WordToHtmlUtils.isNotEmpty( summaryInformation.getKeywords() ) )
270.     htmlDocumentFacade.addKeywords( summaryInformation.getKeywords() );
272. if ( WordToHtmlUtils.isNotEmpty( summaryInformation.getComments() ) )
274.             .addDescription( summaryInformation.getComments() );
{% endhighlight %}

***

