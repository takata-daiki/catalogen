# NoteRecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
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

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
61. private NoteRecord note = null;
97.     if(note != null) note.setFlags(visible ? NoteRecord.NOTE_VISIBLE : NoteRecord.NOTE_HIDDEN);
125.     if(note != null) note.setRow((short)row);
144.     if(note != null) note.setColumn(col);
163.     if(note != null) note.setAuthor(author);
{% endhighlight %}

***

