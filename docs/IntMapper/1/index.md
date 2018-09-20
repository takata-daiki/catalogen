# IntMapper @Cluster 1

***

### [SSTDeserializer.java](https://searchcode.com/codesearch/view/15642371/)
{% highlight java %}
76. static public void addToStringTable( IntMapper strings, UnicodeString string )
78.   strings.add(string );
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

### [SSTDeserializer.java](https://searchcode.com/codesearch/view/15642371/)
{% highlight java %}
55. private IntMapper strings;
78.   strings.add(string );
{% endhighlight %}

***

### [SSTRecordSizeCalculator.java](https://searchcode.com/codesearch/view/15642484/)
{% highlight java %}
55. private IntMapper strings;
66.     for (int i=0; i < strings.size(); i++ )
68.       UnicodeString unistr = ( (UnicodeString) strings.get(i));
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

