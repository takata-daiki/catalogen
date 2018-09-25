# SSTRecord

***

## [Cluster 1 (field_1_num_strings, field_2_num_unique_strings, other)](./1)
1 results
> does the text have a shadow ? 
{% highlight java %}
273. SSTRecord other = (SSTRecord) o;
275. return ( ( field_1_num_strings == other
276.         .field_1_num_strings ) && ( field_2_num_unique_strings == other
278.         .equals( other.field_3_strings ) );
{% endhighlight %}

***

## [Cluster 2 (getstring, sst, sstrec)](./2)
7 results
> test that we get the same value as excel and , for 
{% highlight java %}
24. private SSTRecord sstrec;
90.                     String value = sstrec.getString(lrec.getSSTIndex()).getString();
{% endhighlight %}

***

