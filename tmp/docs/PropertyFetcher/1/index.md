# PropertyFetcher @Cluster 1 (fetch, ok, visitor)

***

### [XSLFSimpleShape.java](https://searchcode.com/codesearch/view/97406763/)
> < p > the method is called by poi ' s eventing api for each file in the origin poifs . < / p > @ param @ the value to check 
{% highlight java %}
588. boolean fetchShapeProperty(PropertyFetcher visitor) {
589.     boolean ok = visitor.fetch(this);
599.                 ok = visitor.fetch(masterShape);
627.                     ok = visitor.fetch(masterShape);
{% endhighlight %}

***

