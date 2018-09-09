# Record

***

### [Cluster 1](./1)
{% highlight java %}
115. Record record = (Record)obj;
116. Map<String,Object> map = record.getColumns();
118.   record.getColumns();
123.     cell.setCellValue(record.get(key)+"");
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
86. public void processRecord(Record record)
94.         switch (record.getSid())
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
322. private void internalProcessRecord(Record record) throws SAXException, TikaException, IOException {
323.     switch (record.getSid()) {
418.     previousSid = record.getSid();
{% endhighlight %}

***

