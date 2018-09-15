# NoteRecord @Cluster 1

***

### [HSSFCell.java](https://searchcode.com/codesearch/view/15642303/)
{% highlight java %}
1029. NoteRecord note = (NoteRecord)rec;
1030. if (note.getRow() == row && note.getColumn() == column){
1031.     TextObjectRecord txo = (TextObjectRecord)txshapes.get(new Integer(note.getShapeId()));
1033.     comment.setRow(note.getRow());
1034.     comment.setColumn(note.getColumn());
1035.     comment.setAuthor(note.getAuthor());
1036.     comment.setVisible(note.getFlags() == NoteRecord.NOTE_VISIBLE);
{% endhighlight %}

***

