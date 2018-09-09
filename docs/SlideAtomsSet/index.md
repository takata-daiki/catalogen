# SlideAtomsSet

***

### [Cluster 1](./1)
{% highlight java %}
306. SlideAtomsSet sas = masterSets[i];
307. int sheetNo = sas.getSlidePersistAtom().getSlideIdentifier();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
55. private SlideAtomsSet _atomSet;
83.     findTextRuns(_atomSet.getSlideRecords(),textRuns);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
643. for (SlideAtomsSet ns : nslwt.getSlideAtomsSets()) {
644.   if (ns.getSlidePersistAtom().getSlideIdentifier() != notesId) {
646.     records.add(ns.getSlidePersistAtom());
647.     if (ns.getSlideRecords() != null)
648.       records.addAll(Arrays.asList(ns.getSlideRecords()));
{% endhighlight %}

***

