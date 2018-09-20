# EscherDggRecord @Cluster 4

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
152. EscherDggRecord dgg = getSlideShow().getDocumentRecord().getPPDrawingGroup().getEscherDggRecord();
155. int dgId = dgg.getMaxDrawingGroupId() + 1;
157. dgg.setDrawingsSaved(dgg.getDrawingsSaved() + 1);
158. dgg.setMaxDrawingGroupId(dgId);
{% endhighlight %}

***

