# HSSFComment @Cluster 4

***

### [SheetBuilderBase.java](https://searchcode.com/codesearch/view/112311786/)
{% highlight java %}
216. final HSSFComment comment = patr.createComment(anchor);
219. comment.setString(new HSSFRichTextString(commentString));
222.   comment.setAuthor(author);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
861. HSSFComment comment = cell.getCellComment();
863.   HSSFRichTextString richTextString  = comment.getString();
869.         comment.setString(new HSSFRichTextString(res));
{% endhighlight %}

***

### [Cell.java](https://searchcode.com/codesearch/view/3760572/)
{% highlight java %}
302. HSSFComment comment = this.m_cell.getCellComment();
309.     comment.setString(new HSSFRichTextString(strCommecnt));
310.     comment.setAuthor(strAuthor);
{% endhighlight %}

***

### [CommentShape.java](https://searchcode.com/codesearch/view/15642359/)
{% highlight java %}
138. HSSFComment comment = (HSSFComment)shape;
139. opt.addEscherProperty( new EscherSimpleProperty( EscherProperties.GROUPSHAPE__PRINT, comment.isVisible() ? 0x000A0000 : 0x000A0002) );
{% endhighlight %}

***

### [HSSFPatriarch.java](https://searchcode.com/codesearch/view/15642333/)
{% highlight java %}
160. HSSFComment shape = new HSSFComment(null, anchor);
161. shape.anchor = anchor;
{% endhighlight %}

***

