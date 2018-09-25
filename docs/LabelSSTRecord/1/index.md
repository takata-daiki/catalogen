# LabelSSTRecord @Cluster 1 (cellvalue, importerutils, language)

***

### [Excel2003ImportListener.java](https://searchcode.com/codesearch/view/92669296/)
> this method color by a xml . < p > the 0 is specified in bottom . i . e . the ( ) function to a ( return to this method ) it . < / p > 
{% highlight java %}
83. LabelSSTRecord lrec = (LabelSSTRecord) record;
85. if(lrec.getRow() == 0) {
88. } else if(lrec.getColumn() == 0) { //第一列
90.     String value = sstrec.getString(lrec.getSSTIndex()).getString();
92. } else if(lrec.getColumn() == 1) {//第二列
{% endhighlight %}

***

### [ExcelKeywordParser.java](https://searchcode.com/codesearch/view/12440040/)
> used to ' skip over ' the text [ < br > < li > byte [ ] / char [ ] ] < / li > < / ul > for this encoding , the is 1 6 bitflag is always present even if nchars = = 0 . < br > this method should be used when the nchars field is < em > not < / em > stored as a ushort immediately before the is 1 6 bitflag . otherwise , { @ link # value } . 
{% highlight java %}
153. final LabelSSTRecord lrec = (LabelSSTRecord) record;
154. if (lrec.getRow() != 0)
156.     if (lrec.getColumn() == 0)
162.         final String cellValue = sstrec.getString(lrec.getSSTIndex()).getString();
163.         if (lrec.getColumn() == 1)
181.         else if (lrec.getColumn() == 2)
196.         else if (lrec.getColumn() == 4)
209.         else if (lrec.getColumn() == 6)
216.         else if (lrec.getColumn() == 7)
222.         else if (lrec.getColumn() == 8)
{% endhighlight %}

***

### [ExcelLanguageCentricParser.java](https://searchcode.com/codesearch/view/12440043/)
> used internally to list the named range and re - 7 for it 
{% highlight java %}
153. final LabelSSTRecord lrec = (LabelSSTRecord) record;
154. if (lrec.getRow() == 0)
160.     if (lrec.getColumn() == 0) {
162.             sstrec.getString(lrec.getSSTIndex()).getString();
165.     else if (lrec.getColumn() == 1) {
167.                 sstrec.getString(lrec.getSSTIndex()).getString());
169.     else if (lrec.getColumn() == 2) {
172.         String name = sstrec.getString(lrec.getSSTIndex()).getString();
176.     else if (lrec.getColumn() == 3) {
186.             languages.get(lrec.getColumn()-4);
187.         String value = sstrec.getString(lrec.getSSTIndex()).getString();
204.     if (isLastColumn(lrec.getColumn())) {
{% endhighlight %}

***

