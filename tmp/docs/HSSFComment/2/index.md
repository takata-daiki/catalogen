# HSSFComment @Cluster 2 (anchor, createcomment, short)

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
> sets the shape - @ param this 
{% highlight java %}
160. HSSFComment shape = new HSSFComment(null, anchor);
161. shape.anchor = anchor;
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> sets the list of colours that are interpolated between . the number must match { @ link # sheet ( ) } 
{% highlight java %}
1719. HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
1722.   HSSFRichTextString richTextString  = comment.getString();
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
> sets the 
{% highlight java %}
216. final HSSFComment comment = patr.createComment(anchor);
219. comment.setString(new HSSFRichTextString(commentString));
222.   comment.setAuthor(author);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> sets the a number of the header that ' s name @ param number of the font in the font 
{% highlight java %}
267. HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
269. comment.setString(new HSSFRichTextString("happy life"));
271. comment.setAuthor("dylan");
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> sets the list of colours that are interpolated between . the number must match { @ link # part ( ) } 
{% highlight java %}
861. HSSFComment comment = cell.getCellComment();
863.   HSSFRichTextString richTextString  = comment.getString();
869.         comment.setString(new HSSFRichTextString(res));
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
> sets the a number of the header that ' s name @ param number of the font in the font 
{% highlight java %}
114. HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
116. comment.setString(new HSSFRichTextString("happy life"));
118. comment.setAuthor("dylan");
{% endhighlight %}

***

### [Cell.java](https://searchcode.com/codesearch/view/3760572/)
> sets the 
{% highlight java %}
302. HSSFComment comment = this.m_cell.getCellComment();
309.     comment.setString(new HSSFRichTextString(strCommecnt));
310.     comment.setAuthor(strAuthor);
{% endhighlight %}

***

