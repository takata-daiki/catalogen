# CommonObjectDataSubRecord

***

## [Cluster 1](./1)
3 results
> code comments is here.
{% highlight java %}
125. CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
126. c.setObjectType( (short) ( (HSSFSimpleShape) shape ).getShapeType() );
127. c.setObjectId( (short) ( shapeId ) );
128. c.setLocked( true );
129. c.setPrintable( true );
130. c.setAutofill( true );
131. c.setAutoline( true );
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
122. CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
123. c.setObjectType((short) ((HSSFSimpleShape)shape).getShapeType());
125. c.setObjectId((short) ( shapeId ));
126. c.setLocked(true);
127. c.setPrintable(true);
128. c.setAutofill(true);
129. c.setAutoline(true);
131. c.setReserved2( 0x0 );
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
682. CommonObjectDataSubRecord cmo = new CommonObjectDataSubRecord();
683. cmo.setObjectType( CommonObjectDataSubRecord.OBJECT_TYPE_GROUP );
684. cmo.setObjectId( (short) ( shapeId ) );
685. cmo.setLocked( true );
686. cmo.setPrintable( true );
687. cmo.setAutofill( true );
688. cmo.setAutoline( true );
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
158. CommonObjectDataSubRecord c = new CommonObjectDataSubRecord();
159. c.setObjectType( OBJECT_TYPE_MICROSOFT_OFFICE_DRAWING );
160. c.setObjectId( (short) ( shapeId ) );
161. c.setLocked( true );
162. c.setPrintable( true );
163. c.setAutofill( true );
164. c.setAutoline( true );
{% endhighlight %}

***

