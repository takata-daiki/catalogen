# LabelSSTRecord

***

## [Cluster 1](./1)
3 results
> this method color by a xml . < p > the 0 is specified in bottom . i . e . the ( ) function to a ( return to this method ) it . < / p > 
{% highlight java %}
83. LabelSSTRecord lrec = (LabelSSTRecord) record;
85. if(lrec.getRow() == 0) {
88. } else if(lrec.getColumn() == 0) { //第一列
90.     String value = sstrec.getString(lrec.getSSTIndex()).getString();
92. } else if(lrec.getColumn() == 1) {//第二列
{% endhighlight %}

***

## [Cluster 2](./2)
10 results
> this must be called to setup the internal work book references whenever a 
{% highlight java %}
76. HSSFRichTextString(Workbook book, LabelSSTRecord record) {
79.   this.string = book.getSSTString(record.getSSTIndex());
{% endhighlight %}

***

