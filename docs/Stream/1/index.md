# Stream @Cluster 1

***

### [VSDDumper.java](https://searchcode.com/codesearch/view/97396300/)
{% highlight java %}
56. public static void dumpStream(Stream stream, int indent) {
65.   Pointer ptr = stream.getPointer();
75.     int decompLen = stream._getContentsLength();
80.   System.out.println(ind + "  Stream is\t" + stream.getClass().getName());
82.   byte[] db = stream._getStore()._getContents();
{% endhighlight %}

***

