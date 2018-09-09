# CharacterProperties @Cluster 1

***

### [CharacterSprmUncompressor.java](https://searchcode.com/codesearch/view/97384370/)
{% highlight java %}
86. CharacterProperties styleProperties = newProperties;
87. newProperties = styleProperties.clone();
{% endhighlight %}

***

### [CharacterProperties.java](https://searchcode.com/codesearch/view/97384444/)
{% highlight java %}
377. CharacterProperties cp = (CharacterProperties) super.clone();
379. cp.setCv( getCv().clone() );
380. cp.setDttmRMark( (DateAndTime) getDttmRMark().clone() );
381. cp.setDttmRMarkDel( (DateAndTime) getDttmRMarkDel().clone() );
382. cp.setDttmPropRMark( (DateAndTime) getDttmPropRMark().clone() );
383. cp.setDttmDispFldRMark( (DateAndTime) getDttmDispFldRMark().clone() );
384. cp.setXstDispFldRMark( getXstDispFldRMark().clone() );
385. cp.setShd( (ShadingDescriptor) getShd().clone() );
386. cp.setBrc( (BorderCode) getBrc().clone() );
{% endhighlight %}

***

