# NameRecord @Cluster 1

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
809. private boolean isRowColHeaderRecord( NameRecord r )
811.     return r.getOptionFlag() == 0x20 && ("" + ((char)7)).equals(r.getNameText());
{% endhighlight %}

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
{% highlight java %}
385. NameRecord record = ( NameRecord ) iterator.next();
388. if (record.getBuiltInName() == name && record.getIndexToSheet() == sheetIndex) {
{% endhighlight %}

***

### [HSSFName.java](https://searchcode.com/codesearch/view/15642308/)
{% highlight java %}
106. NameRecord rec = book.getNameRecord(i);
108.   if (rec.getNameText().equalsIgnoreCase(getNameName()))
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
792. NameRecord r = null;
795.     int indexToSheet = r.getEqualsToIndexToSheet() -1;
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
1134. NameRecord name = workbook.getSpecificBuiltinRecord(NameRecord.BUILTIN_PRINT_AREA, sheetIndex+1);
1138. return name.getAreaReference(workbook);
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
1090. NameRecord name = workbook.getSpecificBuiltinRecord(NameRecord.BUILTIN_PRINT_AREA, sheetIndex+1);
1098. name.setExternSheetNumber(externSheetIndex);
1099. name.setAreaReference(reference);
{% endhighlight %}

***

### [HSSFWorkbook.java](https://searchcode.com/codesearch/view/15642316/)
{% highlight java %}
714. NameRecord nameRecord;
730. nameRecord.setDefinitionTextLength(definitionTextLength);
765. nameRecord.setNameDefinition(ptgs);
{% endhighlight %}

***

### [NamePtg.java](https://searchcode.com/codesearch/view/15642587/)
{% highlight java %}
72. NameRecord rec;
75.     if (name.equals(rec.getNameText())) {
81. rec.setNameText(name);
82. rec.setNameTextLength((byte) name.length());
{% endhighlight %}

***

### [HSSFName.java](https://searchcode.com/codesearch/view/15642308/)
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

