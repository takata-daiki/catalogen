# NoteRecord

***

## [Cluster 1](./1)
3 results
> sets the 
{% highlight java %}
225. NoteRecord nrec = (NoteRecord) record;
227. thisRow = nrec.getRow();
228. thisColumn = nrec.getColumn();
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> @ param 2 the 5 2 byte , i . e . an array of index to the an string . 
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

## [Cluster 3](./3)
1 results
> this comment could not be generated...
{% highlight java %}
61. private NoteRecord note = null;
97.     if(note != null) note.setFlags(visible ? NoteRecord.NOTE_VISIBLE : NoteRecord.NOTE_HIDDEN);
125.     if(note != null) note.setRow((short)row);
144.     if(note != null) note.setColumn(col);
163.     if(note != null) note.setAuthor(author);
{% endhighlight %}

***

