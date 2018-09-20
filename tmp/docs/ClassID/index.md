# ClassID

***

## [Cluster 1](./1)
3 results
> sets the storage class id for this property stream . this is the class id of the com object which can read and write this property stream @ param < code > true < / code > if the storage has been a valid id , or value a value 
{% highlight java %}
340. public void setStorageClsid( ClassID clsidStorage)
346.         clsidStorage.write( _raw_data, _storage_clsid_offset);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> checks the conversion of an excel . to be used to 3 2 7 6 7 . this < code > - 1 < / code > 2 4 2 4 2 3 7 4 . 
{% highlight java %}
224. final ClassID cid = (ClassID) o;
225. if (bytes.length != cid.bytes.length)
228.     if (bytes[i] != cid.bytes[i])
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> < p > writes a 1 6 - byte { @ link of } that from an input stream . < / p > @ param out the stream to write to @ param n the value to write @ exception ioexception if an i / o error occurs 
{% highlight java %}
168. public static int writeToStream(final OutputStream out, final ClassID n)
172.     n.write(b, 0);
{% endhighlight %}

***

