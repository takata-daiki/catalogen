# SummaryInformation

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
86. private void parse(SummaryInformation summary) {
87.     set(Metadata.TITLE, summary.getTitle());
88.     set(Metadata.AUTHOR, summary.getAuthor());
89.     set(Metadata.KEYWORDS, summary.getKeywords());
90.     set(Metadata.SUBJECT, summary.getSubject());
91.     set(Metadata.LAST_AUTHOR, summary.getLastAuthor());
92.     set(Metadata.COMMENTS, summary.getComments());
93.     set(Metadata.TEMPLATE, summary.getTemplate());
94.     set(Metadata.APPLICATION_NAME, summary.getApplicationName());
95.     set(Metadata.REVISION_NUMBER, summary.getRevNumber());
96.     set(Metadata.CREATION_DATE, summary.getCreateDateTime());
97.     set(Metadata.CHARACTER_COUNT, summary.getCharCount());
98.     set(Metadata.EDIT_TIME, summary.getEditTime());
99.     set(Metadata.LAST_SAVED, summary.getLastSaveDateTime());
100.     set(Metadata.PAGE_COUNT, summary.getPageCount());
101.     if (summary.getPageCount() > 0) {
102.         metadata.set(PagedText.N_PAGES, summary.getPageCount());
104.     set(Metadata.SECURITY, summary.getSecurity());
105.     set(Metadata.WORD_COUNT, summary.getWordCount());
106.     set(Metadata.LAST_PRINTED, summary.getLastPrinted());
{% endhighlight %}

***

## [Cluster 2](./2)
9 results
> code comments is here.
{% highlight java %}
46. SummaryInformation summaryInformation =
49. return summaryInformation.getLastSaveDateTime();
{% endhighlight %}

***

