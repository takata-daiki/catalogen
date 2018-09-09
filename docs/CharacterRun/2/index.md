# CharacterRun @Cluster 2

***

### [CharacterRun.java](https://searchcode.com/codesearch/view/97384484/)
{% highlight java %}
563. CharacterRun cp = (CharacterRun)super.clone();
564. cp._props.setDttmRMark((DateAndTime)_props.getDttmRMark().clone());
565. cp._props.setDttmRMarkDel((DateAndTime)_props.getDttmRMarkDel().clone());
566. cp._props.setDttmPropRMark((DateAndTime)_props.getDttmPropRMark().clone());
567. cp._props.setDttmDispFldRMark((DateAndTime)_props.getDttmDispFldRMark().
569. cp._props.setXstDispFldRMark(_props.getXstDispFldRMark().clone());
570. cp._props.setShd((ShadingDescriptor)_props.getShd().clone());
{% endhighlight %}

***

