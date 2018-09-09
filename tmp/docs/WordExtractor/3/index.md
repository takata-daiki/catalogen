# WordExtractor @Cluster 3

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/111785561/)
{% highlight java %}
45. org.apache.poi.hwpf.extractor.WordExtractor wordExtractor =
48. addTextIfAny(xhtml, "header", wordExtractor.getHeaderText());
50. for (String paragraph : wordExtractor.getParagraphText()) {
54. for (String paragraph : wordExtractor.getFootnoteText()) {
58. for (String paragraph : wordExtractor.getCommentsText()) {
62. for (String paragraph : wordExtractor.getEndnoteText()) {
66. addTextIfAny(xhtml, "footer", wordExtractor.getFooterText());
{% endhighlight %}

***

