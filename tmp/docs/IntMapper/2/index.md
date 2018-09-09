# IntMapper @Cluster 2

***

### [SSTRecord.java](https://searchcode.com/codesearch/view/15642381/)
{% highlight java %}
95. private IntMapper field_3_strings;
140.     int index = field_3_strings.getIndex(ucs);
150.         rval = field_3_strings.size();
217.     return (UnicodeString) field_3_strings.get( id );
222.     UnicodeString unicodeString = ( (UnicodeString) field_3_strings.get( id  ) );
241.     for ( int k = 0; k < field_3_strings.size(); k++ )
243.       UnicodeString s = (UnicodeString)field_3_strings.get( k );
277.             .field_2_num_unique_strings ) && field_3_strings
397.     return field_3_strings.iterator();
406.     return field_3_strings.size();
476.   return ExtSSTRecord.getRecordSizeForStrings(field_3_strings.size());
{% endhighlight %}

***

### [SSTSerializer.java](https://searchcode.com/codesearch/view/15642472/)
{% highlight java %}
57. private IntMapper strings;
72.     int infoRecs = ExtSSTRecord.getNumberOfInfoRecsForStrings(strings.size());
94.     for ( int k = 0; k < strings.size(); k++ )
127.     return ( (UnicodeString) strings.get( index ) );
{% endhighlight %}

***

### [SSTSerializer.java](https://searchcode.com/codesearch/view/15642472/)
{% highlight java %}
67. public SSTSerializer( IntMapper strings, int numStrings, int numUniqueStrings )
72.     int infoRecs = ExtSSTRecord.getNumberOfInfoRecsForStrings(strings.size());
{% endhighlight %}

***

### [SSTSerializer.java](https://searchcode.com/codesearch/view/15642472/)
{% highlight java %}
125. private static UnicodeString getUnicodeString( IntMapper strings, int index )
127.     return ( (UnicodeString) strings.get( index ) );
{% endhighlight %}

***

### [SSTRecordSizeCalculator.java](https://searchcode.com/codesearch/view/15642484/)
{% highlight java %}
55. private IntMapper strings;
66.     for (int i=0; i < strings.size(); i++ )
68.       UnicodeString unistr = ( (UnicodeString) strings.get(i));
{% endhighlight %}

***

