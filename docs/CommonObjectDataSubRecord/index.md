# CommonObjectDataSubRecord

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
1044. CommonObjectDataSubRecord cmo = (CommonObjectDataSubRecord)sub;
1045. if (cmo.getObjectType() == CommonObjectDataSubRecord.OBJECT_TYPE_COMMENT){
1050.             txshapes.put(new Integer(cmo.getObjectId()), rec);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
210. CommonObjectDataSubRecord rec = new CommonObjectDataSubRecord();
212. rec.field_1_objectType = field_1_objectType;
213. rec.field_2_objectId = field_2_objectId;
214. rec.field_3_option = field_3_option;
215. rec.field_4_reserved1 = field_4_reserved1;
216. rec.field_5_reserved2 = field_5_reserved2;
217. rec.field_6_reserved3 = field_6_reserved3;
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
82. CommonObjectDataSubRecord cmo = (CommonObjectDataSubRecord)r;
83. cmo.setAutofill(false);
{% endhighlight %}

***

