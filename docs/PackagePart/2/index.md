# PackagePart @Cluster 2 (activepart, getpartname, string)

***

### [OPCPackageReader.java](https://searchcode.com/codesearch/view/401673/)
> test that we get the same value as excel and , for 
{% highlight java %}
53. private PackagePart activePart;
158.         xliffReader.open(new RawDocument(activePart.getInputStream(), "UTF-8", srcLoc, srcLoc));
161.             activePart.getPartName().getName()), e);
246.   String partName = activePart.getPartName().toString();
{% endhighlight %}

***

