# ClassID @Cluster 1 (classid1, clsidstorage, fid)

***

### [Property.java](https://searchcode.com/codesearch/view/15642246/)
> sets the storage class id for this property stream . this is the class id of the com object which can read and write this property stream @ param < code > true < / code > if the storage has been a valid id , or value a value 
{% highlight java %}
340. public void setStorageClsid( ClassID clsidStorage)
346.         clsidStorage.write( _raw_data, _storage_clsid_offset);
{% endhighlight %}

***

### [PropertySet.java](https://searchcode.com/codesearch/view/15642677/)
> returns < tt > true < / tt > if the given value passes the constraint ' s . @ throws evaluationexception if the < tt > value < / tt > s are base tokens ( i . e . are not a set of 2 0 1 6 , 0 x 0 0 0 ) < / a > 
{% highlight java %}
663. ClassID classID1 = ps.getClassID();
672.     !classID1.equals(classID2)    ||
{% endhighlight %}

***

### [MutableSection.java](https://searchcode.com/codesearch/view/15642671/)
> sets the property ' s id . the id is first up to the 2 5 0 0 7 and sets the 
{% highlight java %}
155. ClassID fid = getFormatID();
161. fid.setBytes(formatID);
{% endhighlight %}

***

