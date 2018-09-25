# GutsRecord @Cluster 1 (else, if, retval)

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> to workbook . < p > as , this are , if you are this on or ( see { @ link # code ( string ) } ) < / p > 
{% highlight java %}
3161. GutsRecord guts = (GutsRecord) findFirstRecordBySid( GutsRecord.sid );
3162. guts.setRowLevelMax( (short) ( maxLevel + 1 ) );
3163. guts.setLeftRowGutter( (short) ( 29 + (12 * (maxLevel)) ) );
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> input stream this method , 0 is . to bytes short value ! 
{% highlight java %}
1970. GutsRecord guts = (GutsRecord) findFirstRecordBySid( GutsRecord.sid );
1971. guts.setColLevelMax( (short) ( maxLevel+1 ) );
1973.     guts.setTopColGutter( (short)0 );
1975.     guts.setTopColGutter( (short) ( 29 + (12 * (maxLevel-1)) ) );
{% endhighlight %}

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> set the cells type ( numeric , formula or string ) . if the cell currently contains a value , the value will be converted to match the new type , if possible . 
{% highlight java %}
1586. GutsRecord retval = new GutsRecord();
1588. retval.setLeftRowGutter(( short ) 0);
1589. retval.setTopColGutter(( short ) 0);
1590. retval.setRowLevelMax(( short ) 0);
1591. retval.setColLevelMax(( short ) 0);
{% endhighlight %}

***

