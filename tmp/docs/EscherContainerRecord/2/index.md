# EscherContainerRecord @Cluster 2

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
153. EscherContainerRecord dgContainer = (EscherContainerRecord)getSheetContainer().getPPDrawing().getEscherRecords()[0];
160. for (EscherContainerRecord c : dgContainer.getChildContainers()) {
{% endhighlight %}

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
160. for (EscherContainerRecord c : dgContainer.getChildContainers()) {
162.     switch(c.getRecordId()){
164.             EscherContainerRecord dc = (EscherContainerRecord)c.getChild(0);
168.             spr = c.getChildById(EscherSpRecord.RECORD_ID);
{% endhighlight %}

***

### [Slide.java](https://searchcode.com/codesearch/view/97394313/)
{% highlight java %}
164. EscherContainerRecord dc = (EscherContainerRecord)c.getChild(0);
165. spr = dc.getChildById(EscherSpRecord.RECORD_ID);
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
214. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
217. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
309. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
312. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/97394323/)
{% highlight java %}
357. EscherContainerRecord dg = (EscherContainerRecord) ppdrawing.getEscherRecords()[0];
360. for (Iterator<EscherRecord> it = dg.getChildIterator(); it.hasNext();) {
{% endhighlight %}

***

