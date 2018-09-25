# NoteRecord

***

## [Cluster 1 (note, nrec, shape)](./1)
2 results
> sets the 
{% highlight java %}
225. NoteRecord nrec = (NoteRecord) record;
227. thisRow = nrec.getRow();
228. thisColumn = nrec.getColumn();
{% endhighlight %}

***

## [Cluster 2 (comment, note, noterecord)](./2)
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

