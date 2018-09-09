# WordExtractor

***

### [Cluster 1](./1)
{% highlight java %}
26. WordExtractor extractor = new WordExtractor(fs);
29. String[] paragraphs = extractor.getParagraphText();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
61. WordExtractor we = new WordExtractor(is);
62. return we.getText();
{% endhighlight %}

***

### [Cluster 3](./3)
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

### [Cluster 4](./4)
{% highlight java %}
83. private List getBeanList(WordExtractor extractor, WordFileBean file) {
84.   String[] strs = extractor.getParagraphText();
{% endhighlight %}

***

