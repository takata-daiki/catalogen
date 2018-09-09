# Pointer @Cluster 1

***

### [VSDDumper.java](https://searchcode.com/codesearch/view/97396300/)
{% highlight java %}
65. Pointer ptr = stream.getPointer();
66. System.out.println(ind + "Stream at\t" + ptr.getOffset() +
67.     " - " + Integer.toHexString(ptr.getOffset()));
68. System.out.println(ind + "  Type is\t" + ptr.getType() +
69.     " - " + Integer.toHexString(ptr.getType()));
70. System.out.println(ind + "  Format is\t" + ptr.getFormat() +
71.     " - " + Integer.toHexString(ptr.getFormat()));
72. System.out.println(ind + "  Length is\t" + ptr.getLength() +
73.     " - " + Integer.toHexString(ptr.getLength()));
74. if(ptr.destinationCompressed()) {
79. System.out.println(ind + "  Compressed is\t" + ptr.destinationCompressed());
{% endhighlight %}

***

