# NameRecord

***

### [Cluster 1](./1)
{% highlight java %}
106. NameRecord rec = book.getNameRecord(i);
108.   if (rec.getNameText().equalsIgnoreCase(getNameName()))
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
792. NameRecord r = null;
795.     int indexToSheet = r.getEqualsToIndexToSheet() -1;
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
111. NameRecord rec = book.getNameRecord(field_1_label_index - 1);
112. return rec.getNameText();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
55. private NameRecord       name;
76.     short indexToExternSheet = name.getExternSheetNumber();
89.     String result = name.getNameText();
100.     name.setNameText(nameName);
101.     name.setNameTextLength((byte)nameName.length());
121.     result = name.getAreaReference(book);
137.     name.setExternSheetNumber(externSheetNumber);
159.     name.setAreaReference(ref);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
809. private boolean isRowColHeaderRecord( NameRecord r )
811.     return r.getOptionFlag() == 0x20 && ("" + ((char)7)).equals(r.getNameText());
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
714. NameRecord nameRecord;
730. nameRecord.setDefinitionTextLength(definitionTextLength);
765. nameRecord.setNameDefinition(ptgs);
{% endhighlight %}

***

