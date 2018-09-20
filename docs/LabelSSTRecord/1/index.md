# LabelSSTRecord @Cluster 1

***

### [Excel2003ImportListener.java](https://searchcode.com/codesearch/view/92669296/)
{% highlight java %}
83. LabelSSTRecord lrec = (LabelSSTRecord) record;
85. if(lrec.getRow() == 0) {
88. } else if(lrec.getColumn() == 0) { //第一列
90.     String value = sstrec.getString(lrec.getSSTIndex()).getString();
92. } else if(lrec.getColumn() == 1) {//第二列
{% endhighlight %}

***

### [ExcelKeywordParser.java](https://searchcode.com/codesearch/view/12440040/)
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

