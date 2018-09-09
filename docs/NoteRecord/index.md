# NoteRecord

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
60. private NoteRecord note;
100.     note.setColumn(shape.getColumn());
101.     note.setRow((short)shape.getRow());
102.     note.setFlags(shape.isVisible() ? NoteRecord.NOTE_VISIBLE : NoteRecord.NOTE_HIDDEN);
103.     note.setShapeId((short)shapeId);
104.     note.setAuthor(shape.getAuthor() == null ? "" : shape.getAuthor());
{% endhighlight %}

***

