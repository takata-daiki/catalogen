# TextObjectRecord

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
401. TextObjectRecord tor = (TextObjectRecord) record;
402. addTextCell(record, tor.getStr().getString());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
158. TextObjectRecord obj = new TextObjectRecord();
159. obj.setHorizontalTextAlignment( TextObjectRecord.HORIZONTAL_TEXT_ALIGNMENT_LEFT_ALIGNED );
160. obj.setVerticalTextAlignment( TextObjectRecord.VERTICAL_TEXT_ALIGNMENT_TOP );
161. obj.setTextLocked( true );
162. obj.setTextOrientation( TextObjectRecord.TEXT_ORIENTATION_NONE );
164. obj.setFormattingRunLength( (short) frLength );
165. obj.setTextLength( (short) shape.getString().length() );
166. obj.setStr( shape.getString() );
167. obj.setReserved7( 0 );
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
1031. TextObjectRecord txo = (TextObjectRecord)txshapes.get(new Integer(note.getShapeId()));
1037. comment.setString(txo.getStr());
{% endhighlight %}

***

