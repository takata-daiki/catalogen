# EntryNode

***

### [Cluster 1](./1)
{% highlight java %}
395. void remove(EntryNode entry)
397.     _property_table.removeProperty(entry.getProperty());
398.     if (entry.isDocumentEntry())
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
168. EntryNode child = ( EntryNode ) _entries.get(oldName);
173.         .changeName(child.getProperty(), newName);
177.         _entries.put(child.getProperty().getName(), child);
{% endhighlight %}

***

