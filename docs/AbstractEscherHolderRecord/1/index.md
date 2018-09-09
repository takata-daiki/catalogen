# AbstractEscherHolderRecord @Cluster 1

***

### [AbstractEscherHolderRecord.java](https://searchcode.com/codesearch/view/15642502/)
{% highlight java %}
286. public void join( AbstractEscherHolderRecord record )
288.     int length = this.rawData.length + record.getRawData().length;
291.     System.arraycopy( record.getRawData(), 0, data, rawData.length, record.getRawData().length );
{% endhighlight %}

***

