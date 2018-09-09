# NameRecord @Cluster 5

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
809. private boolean isRowColHeaderRecord( NameRecord r )
811.     return r.getOptionFlag() == 0x20 && ("" + ((char)7)).equals(r.getNameText());
{% endhighlight %}

***

