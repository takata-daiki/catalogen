# EntryNode @Cluster 2

***

### [DirectoryNode.java](https://searchcode.com/codesearch/view/15642286/)
{% highlight java %}
168. EntryNode child = ( EntryNode ) _entries.get(oldName);
173.         .changeName(child.getProperty(), newName);
177.         _entries.put(child.getProperty().getName(), child);
{% endhighlight %}

***

### [DirectoryNode.java](https://searchcode.com/codesearch/view/15642286/)
{% highlight java %}
191. boolean deleteEntry(final EntryNode entry)
195.             .deleteChild(entry.getProperty());
199.         _entries.remove(entry.getName());
{% endhighlight %}

***

