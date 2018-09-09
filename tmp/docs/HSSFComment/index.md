# HSSFComment

***

### [Cluster 1](./1)
{% highlight java %}
1719. HSSFComment comment = ExcelUtils.getCell(activeSheet,  i, j).getCellComment();
1722.   HSSFRichTextString richTextString  = comment.getString();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
138. private HSSFComment              comment;
1000.     comment.setRow((short)record.getRow());
1001.     comment.setColumn(record.getColumn());
1033.                comment.setRow(note.getRow());
1034.                comment.setColumn(note.getColumn());
1035.                comment.setAuthor(note.getAuthor());
1036.                comment.setVisible(note.getFlags() == NoteRecord.NOTE_VISIBLE);
1037.                comment.setString(txo.getStr());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
114. HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
116. comment.setString(new HSSFRichTextString("happy life"));
118. comment.setAuthor("dylan");
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
216. final HSSFComment comment = patr.createComment(anchor);
219. comment.setString(new HSSFRichTextString(commentString));
222.   comment.setAuthor(author);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
71. HSSFComment hssfComment = theCell.getCellComment();
76.     return hssfComment.toString();
{% endhighlight %}

***

