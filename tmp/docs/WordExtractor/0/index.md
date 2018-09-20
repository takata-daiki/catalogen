# WordExtractor @Cluster 1

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

### [IndexFiles.java](https://searchcode.com/codesearch/view/94960725/)
{% highlight java %}
193. WordExtractor extractor = null;
200. return extractor.getText();
{% endhighlight %}

***

### [WordReader.java](https://searchcode.com/codesearch/view/14046017/)
{% highlight java %}
83. private List getBeanList(WordExtractor extractor, WordFileBean file) {
84.   String[] strs = extractor.getParagraphText();
{% endhighlight %}

***

