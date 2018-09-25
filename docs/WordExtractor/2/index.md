# WordExtractor @Cluster 2 (extractor, wordextractor, wrappermethod)

***

### [LuceneController.java](https://searchcode.com/codesearch/view/99552391/)
> set the text for this sheet . 
{% highlight java %}
2404. WordExtractor we = new WordExtractor(doc);
2407. text = we.getText();
{% endhighlight %}

***

### [GoogleAccessor.java](https://searchcode.com/codesearch/view/56511170/)
> sets the a number of the header that contains the hyperlink @ param row the 0 - based column of the cell that contains the hyperlink 
{% highlight java %}
190. WordExtractor extractor = new WordExtractor(inStream);
192. String extractStr = extractor.getText().replaceAll("[^\\p{Print}]", "");
{% endhighlight %}

***

### [MSWordIndexer.java](https://searchcode.com/codesearch/view/95551277/)
> sets the line - color style object . < p > null < br > from the 0 . < p > todo - use from { @ link # a ( ) } or { @ link # font ( ) } and { @ link # font ( ) } to access actual fields . 
{% highlight java %}
23. WordExtractor extractor = new WordExtractor(fs);
24. String wordText = extractor.getText();
{% endhighlight %}

***

### [LuceneController.java](https://searchcode.com/codesearch/view/16908984/)
> set the text for this sheet . 
{% highlight java %}
465. WordExtractor we = new WordExtractor(doc);
468. text = we.getText();
{% endhighlight %}

***

### [ReadMainFiles.java](https://searchcode.com/codesearch/view/73344562/)
> set the document ' s embedded files . < br > the in - place are data ( throws a 1 ) from the workbook . 
{% highlight java %}
28. WordExtractor we = new WordExtractor(doc);
30. paragraphs = we.getParagraphText();
{% endhighlight %}

***

### [MsWordDoc.java](https://searchcode.com/codesearch/view/51905615/)
> set the text for the associated lower level list items . 
{% highlight java %}
15. WordExtractor extractor = new WordExtractor(document);
16. String text = extractor.getText();
{% endhighlight %}

***

### [GoogleAccessor.java](https://searchcode.com/codesearch/view/56511170/)
> sets the a number of the header that contains the hyperlink @ param row the 0 - based column of the cell that contains the hyperlink 
{% highlight java %}
143. WordExtractor extractor = new WordExtractor(inStream);
145. String extractStr = extractor.getText().replaceAll("[^\\p{Print}]", "");
{% endhighlight %}

***

### [WORDParserImpl.java](https://searchcode.com/codesearch/view/11485147/)
> get the text representing the output part of the file 
{% highlight java %}
17. WordExtractor extractor = new WordExtractor(document);
18. return extractor.getText();
{% endhighlight %}

***

### [WordDocPoiTest.java](https://searchcode.com/codesearch/view/112538974/)
> set the contents of this shape to be a copy of the source shape . < p > the 0 is specified in points . < / p > 
{% highlight java %}
26. WordExtractor extractor = new WordExtractor(fs);
29. String[] paragraphs = extractor.getParagraphText();
{% endhighlight %}

***

### [WordScanner.java](https://searchcode.com/codesearch/view/112538995/)
> set the contents of this shape to be a copy of the source shape . < p > the 0 is specified in points . < / p > 
{% highlight java %}
36. WordExtractor extractor = new WordExtractor(fs);
37. String[] paragraphs = extractor.getParagraphText();
{% endhighlight %}

***

### [WordDocument.java](https://searchcode.com/codesearch/view/126168433/)
> excel ' s double to int conversion ( for function ' offset ( ) ' ) a ( 1 2 ) . excel has no param column in the column width . 
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

