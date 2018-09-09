# HWPFDocument @Cluster 8

***

### [TestShapes.java](https://searchcode.com/codesearch/view/97397200/)
{% highlight java %}
38. HWPFDocument doc = HWPFTestDataSamples.openSampleFile("WithArtShapes.doc");
40. List shapes = doc.getShapesTable().getAllShapes();
41. List vshapes = doc.getShapesTable().getVisibleShapes();
60. doc.write(baos);
64. shapes = doc.getShapesTable().getAllShapes();
65. vshapes = doc.getShapesTable().getVisibleShapes();
{% endhighlight %}

***

