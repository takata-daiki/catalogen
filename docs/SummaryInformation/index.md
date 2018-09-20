# SummaryInformation

***

## [Cluster 1](./1)
2 results
> < p > writes an unsigned two - byte value to an output stream . < / p > 
{% highlight java %}
148. SummaryInformation si = (SummaryInformation)
150. setProperty(DublinCore.TITLE, si.getTitle());
151. setProperty(Office.APPLICATION_NAME, si.getApplicationName());
152. setProperty(Office.AUTHOR, si.getAuthor());
153. setProperty(Office.CHARACTER_COUNT, si.getCharCount());
154. setProperty(Office.COMMENTS, si.getComments());
155. setProperty(DublinCore.DATE, si.getCreateDateTime());
157. setProperty(HttpHeaders.LAST_MODIFIED, si.getLastSaveDateTime());
158. setProperty(Office.KEYWORDS, si.getKeywords());
159. setProperty(Office.LAST_AUTHOR, si.getLastAuthor());
160. setProperty(Office.LAST_PRINTED, si.getLastPrinted());
161. setProperty(Office.LAST_SAVED, si.getLastSaveDateTime());
162. setProperty(Office.PAGE_COUNT, si.getPageCount());
163. setProperty(Office.REVISION_NUMBER, si.getRevNumber());
164. setProperty(DublinCore.RIGHTS, si.getSecurity());
165. setProperty(DublinCore.SUBJECT, si.getSubject());
166. setProperty(Office.TEMPLATE, si.getTemplate());
167. setProperty(Office.WORD_COUNT, si.getWordCount());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> test that we get the same value as excel and , for 
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

## [Cluster 3](./3)
9 results
> tests that the create record function returns a properly constructed record in the case of a record @ since 3 . 1 4 - beta 2 
{% highlight java %}
46. SummaryInformation summaryInfo = hwb.getSummaryInformation();
47. summaryInfo.setAuthor("BMP-System");
{% endhighlight %}

***

