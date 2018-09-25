# NameRecord @Cluster 1 (getnametext, rec, workbook)

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
> add the specified block to the collection of this part @ return the index of the newly created 
{% highlight java %}
792. NameRecord r = null;
795.     int indexToSheet = r.getEqualsToIndexToSheet() -1;
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
> sets the a number of the columns that contains the comment @ param row the 0 - based column of the cell that contains the comment 
{% highlight java %}
714. NameRecord nameRecord;
730. nameRecord.setDefinitionTextLength(definitionTextLength);
765. nameRecord.setNameDefinition(ptgs);
{% endhighlight %}

***

### [NamePtg.java](https://searchcode.com/codesearch/view/15642587/)
> checks the conversion of an excel date to a java . util . date on a day when daylight saving time starts . 
{% highlight java %}
72. NameRecord rec;
75.     if (name.equals(rec.getNameText())) {
81. rec.setNameText(name);
82. rec.setNameTextLength((byte) name.length());
{% endhighlight %}

***

### [HSSFName.java](https://searchcode.com/codesearch/view/15642308/)
> sets the 
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

