# NoteRecord @Cluster 1 (note, nrec, shape)

***

### [HxlsAbstract.java](https://searchcode.com/codesearch/view/68613461/)
> sets the 
{% highlight java %}
225. NoteRecord nrec = (NoteRecord) record;
227. thisRow = nrec.getRow();
228. thisColumn = nrec.getColumn();
{% endhighlight %}

***

### [CommentShape.java](https://searchcode.com/codesearch/view/15642359/)
> test that we get the same value as excel and , for 
{% highlight java %}
60. private NoteRecord note;
100.     note.setColumn(shape.getColumn());
101.     note.setRow((short)shape.getRow());
102.     note.setFlags(shape.isVisible() ? NoteRecord.NOTE_VISIBLE : NoteRecord.NOTE_HIDDEN);
103.     note.setShapeId((short)shapeId);
104.     note.setAuthor(shape.getAuthor() == null ? "" : shape.getAuthor());
{% endhighlight %}

***

