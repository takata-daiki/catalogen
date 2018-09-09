# SlideAtom

***

### [Cluster 1](./1)
{% highlight java %}
306. SlideAtom sa = getSlideRecord().getSlideAtom();
307. return sa.getFollowMasterBackground();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
295. SlideAtom sa = getSlideRecord().getSlideAtom();
296. sa.setFollowMasterBackground(flag);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
283. SlideAtom sa = getSlideRecord().getSlideAtom();
285. sa.setMasterID(sheetNo);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
258. SlideAtom sa = getSlideRecord().getSlideAtom();
259. int masterId = sa.getMasterID();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
123. SlideAtom sa = getSlideRecord().getSlideAtom();
127.   sa.setNotesID(0);
130.   sa.setNotesID(notes._getSheetNumber());
{% endhighlight %}

***

