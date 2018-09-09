# PackageRelationshipCollection

***

### [Cluster 1](./1)
{% highlight java %}
73. PackageRelationshipCollection core = 
75. if(core.size() != 1) {
76.    throw new IOException("Invalid OOXML Package received - expected 1 core document, found " + core.size());
80. PackagePart corePart = pkg.getPart(core.getRelationship(0));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
88. PackageRelationshipCollection partsC =
91. PackagePart[] parts = new PackagePart[partsC.size()];
{% endhighlight %}

***

