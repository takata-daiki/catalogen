# HSSFComment @Cluster 2

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1900. HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
1903.   HSSFRichTextString richTextString  = comment.getString();
{% endhighlight %}

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
{% highlight java %}
160. HSSFComment shape = new HSSFComment(null, anchor);
161. shape.anchor = anchor;
{% endhighlight %}

***

### [CommentShape.java](https://searchcode.com/codesearch/view/15642359/)
{% highlight java %}
138. HSSFComment comment = (HSSFComment)shape;
139. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.GROUPSHAPE__PRINT, comment.isVisible() ? 0x000A0000 : 0x000A0002) );
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1719. HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
1722.   HSSFRichTextString richTextString  = comment.getString();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1795. HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
1798.   HSSFRichTextString richTextString  = comment.getString();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1749. HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
1752.   HSSFRichTextString richTextString  = comment.getString();
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
71. HSSFComment hssfComment = theCell.getCellComment();
76.     return hssfComment.toString();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1874. HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
1877.   HSSFRichTextString richTextString  = comment.getString();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1841. HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
1844.   HSSFRichTextString richTextString  = comment.getString();
{% endhighlight %}

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
216. final HSSFComment comment = patr.createComment(anchor);
219. comment.setString(new HSSFRichTextString(commentString));
222.   comment.setAuthor(author);
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
267. HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
269. comment.setString(new HSSFRichTextString("happy life"));
271. comment.setAuthor("dylan");
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
861. HSSFComment comment = cell.getCellComment();
863.   HSSFRichTextString richTextString  = comment.getString();
869.         comment.setString(new HSSFRichTextString(res));
{% endhighlight %}

***

### [ExcelUtil.java](https://searchcode.com/codesearch/view/73315299/)
{% highlight java %}
114. HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
116. comment.setString(new HSSFRichTextString("happy life"));
118. comment.setAuthor("dylan");
{% endhighlight %}

***

### [Cell.java](https://searchcode.com/codesearch/view/3760572/)
{% highlight java %}
302. HSSFComment comment = this.m_cell.getCellComment();
309.     comment.setString(new HSSFRichTextString(strCommecnt));
310.     comment.setAuthor(strAuthor);
{% endhighlight %}

***

