# PackageRelationshipCollection

***

## [Cluster 1](./1)
1 results
> this comment could not be generated...
{% highlight java %}
89. protected PackageRelationshipCollection relationships;
1071.   PackageRelationship retRel = relationships.addRelationship(
1148.   PackageRelationship retRel = relationships.addRelationship(targetURI,
1162.     relationships.removeRelationship(id);
1204.   return this.relationships.getRelationships(id);
1212.     relationships.clear();
1234.   return this.relationships.getRelationshipByID(id);
1241.   return (relationships.size() > 0);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> ensure the . . . if the package , { @ link # document ( ) } and { @ link # was ( ) } 
{% highlight java %}
88. PackageRelationshipCollection partsC =
91. PackagePart[] parts = new PackagePart[partsC.size()];
{% endhighlight %}

***

## [Cluster 3](./3)
3 results
> returns the a part of the package part , the value is to be 1 6 - byte @ return the content type of the part 
{% highlight java %}
63. PackageRelationshipCollection rels = part.getRelationshipsByType(relationshipType);
64. if (rels.size() == 0) return null;
67. return pack.getPart(PackagingURIHelper.createPartName(rels.getRelationship(0).getTargetURI()));
{% endhighlight %}

***

