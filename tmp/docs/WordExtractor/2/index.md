# WordExtractor @Cluster 2

***

### [LuceneController.java](https://searchcode.com/codesearch/view/99552391/)
{% highlight java %}
2404. WordExtractor we = new WordExtractor(doc);
2407. text = we.getText();
{% endhighlight %}

***

### [GoogleAccessor.java](https://searchcode.com/codesearch/view/56511170/)
{% highlight java %}
190. WordExtractor extractor = new WordExtractor(inStream);
192. String extractStr = extractor.getText().replaceAll("[^\\p{Print}]", "");
{% endhighlight %}

***

### [DocumentHelper.java](https://searchcode.com/codesearch/view/16973147/)
{% highlight java %}
140. WordExtractor we = new WordExtractor(doc);
141. char[] origText = we.getText().toCharArray();
{% endhighlight %}

***

### [MSWordIndexer.java](https://searchcode.com/codesearch/view/95551277/)
{% highlight java %}
23. WordExtractor extractor = new WordExtractor(fs);
24. String wordText = extractor.getText();
{% endhighlight %}

***

### [IndexerBean.java](https://searchcode.com/codesearch/view/8591933/)
{% highlight java %}
161. WordExtractor wordExtractor = new WordExtractor(wordStream);
162. Reader contentReader = new StringReader(wordExtractor.getText());
{% endhighlight %}

***

### [ReadMainFiles.java](https://searchcode.com/codesearch/view/73344562/)
{% highlight java %}
73. WordExtractor we = new WordExtractor(doc);
75. String[] paragraphs = we.getParagraphText();
{% endhighlight %}

***

### [LuceneController.java](https://searchcode.com/codesearch/view/16908984/)
{% highlight java %}
465. WordExtractor we = new WordExtractor(doc);
468. text = we.getText();
{% endhighlight %}

***

### [ReadMainFiles.java](https://searchcode.com/codesearch/view/73344562/)
{% highlight java %}
28. WordExtractor we = new WordExtractor(doc);
30. paragraphs = we.getParagraphText();
{% endhighlight %}

***

### [WordParser.java](https://searchcode.com/codesearch/view/7760074/)
{% highlight java %}
30. WordExtractor extractor = new WordExtractor(fileSystem);
31. outputStream.write(extractor.getText().trim().getBytes());
{% endhighlight %}

***

### [MsWordDoc.java](https://searchcode.com/codesearch/view/51905615/)
{% highlight java %}
15. WordExtractor extractor = new WordExtractor(document);
16. String text = extractor.getText();
{% endhighlight %}

***

### [GoogleAccessor.java](https://searchcode.com/codesearch/view/56511170/)
{% highlight java %}
143. WordExtractor extractor = new WordExtractor(inStream);
145. String extractStr = extractor.getText().replaceAll("[^\\p{Print}]", "");
{% endhighlight %}

***

### [WORDParserImpl.java](https://searchcode.com/codesearch/view/11485147/)
{% highlight java %}
17. WordExtractor extractor = new WordExtractor(document);
18. return extractor.getText();
{% endhighlight %}

***

### [readDoc.java](https://searchcode.com/codesearch/view/54270182/)
{% highlight java %}
52. WordExtractor we = new WordExtractor(doc);
54. String[] paragraphs = we.getParagraphText();
{% endhighlight %}

***

### [WordParser.java](https://searchcode.com/codesearch/view/93256528/)
{% highlight java %}
56. WordExtractor extractor = new WordExtractor(file);
57. String wordText = extractor.getText();
{% endhighlight %}

***

### [TextExtractor.java](https://searchcode.com/codesearch/view/107461104/)
{% highlight java %}
61. WordExtractor we = new WordExtractor(is);
62. return we.getText();
{% endhighlight %}

***

### [WordDocPoiTest.java](https://searchcode.com/codesearch/view/112538974/)
{% highlight java %}
26. WordExtractor extractor = new WordExtractor(fs);
29. String[] paragraphs = extractor.getParagraphText();
{% endhighlight %}

***

### [MetaDataService.java](https://searchcode.com/codesearch/view/39694366/)
{% highlight java %}
623. WordExtractor extractor = new WordExtractor(doc);
624. story = extractor.getText();
{% endhighlight %}

***

### [WordScanner.java](https://searchcode.com/codesearch/view/112538995/)
{% highlight java %}
36. WordExtractor extractor = new WordExtractor(fs);
37. String[] paragraphs = extractor.getParagraphText();
{% endhighlight %}

***

### [WordDocument.java](https://searchcode.com/codesearch/view/126168433/)
{% highlight java %}
92. final WordExtractor extractor = new WordExtractor(filesystem);
93. addTextIfAny(sb, extractor.getHeaderText());
94. for (final String paragraph : extractor.getParagraphText()) {
98. for (final String paragraph : extractor.getFootnoteText()) {
102. for (final String paragraph : extractor.getCommentsText()) {
106. for (final String paragraph : extractor.getEndnoteText()) {
109. addTextIfAny(sb, extractor.getFooterText());
{% endhighlight %}

***

