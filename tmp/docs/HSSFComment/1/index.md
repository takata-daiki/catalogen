# HSSFComment @Cluster 1 (note, note_visible, record)

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
> assign a comment to this cell @ param comment comment associated with this cell 
{% highlight java %}
999. public void setCellComment(HSSFComment comment){
1000.     comment.setRow((short)record.getRow());
1001.     comment.setColumn(record.getColumn());
{% endhighlight %}

***

### [CommentShape.java](https://searchcode.com/codesearch/view/15642359/)
> create and return the param set of the document 
{% highlight java %}
97. private NoteRecord createNoteRecord( HSSFComment shape, int shapeId )
100.    note.setColumn(shape.getColumn());
101.    note.setRow((short)shape.getRow());
102.    note.setFlags(shape.isVisible() ? NoteRecord.NOTE_VISIBLE : NoteRecord.NOTE_HIDDEN);
104.    note.setAuthor(shape.getAuthor() == null ? "" : shape.getAuthor());
{% endhighlight %}

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
> sets the a number of the number of columns that are the row @ param row number of the row 
{% highlight java %}
1024. HSSFComment comment = null;
1033.            comment.setRow(note.getRow());
1034.            comment.setColumn(note.getColumn());
1035.            comment.setAuthor(note.getAuthor());
1036.            comment.setVisible(note.getFlags() == NoteRecord.NOTE_VISIBLE);
1037.            comment.setString(txo.getStr());
{% endhighlight %}

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
> test that we get the same value as excel and , for 
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

