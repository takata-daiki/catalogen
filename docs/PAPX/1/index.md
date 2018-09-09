# PAPX @Cluster 1

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
560. for ( PAPX papx : pfkp.getPAPXs() )
567.                 papx.getGrpprl(), 2 );
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
576. for ( PAPX papx : papxs )
581.         SprmIterator sprmIt = new SprmIterator( papx.getGrpprl(), 2 );
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
594. for ( PAPX papx : _doc.getParagraphTable().getParagraphs() )
607.         SprmIterator sprmIt = new SprmIterator( papx.getGrpprl(), 2 );
{% endhighlight %}

***

### [HWPFLister.java](https://searchcode.com/codesearch/view/97384386/)
{% highlight java %}
624. for ( PAPX papx : _doc.getParagraphTable().getParagraphs() )
626.     if ( papx.getStart() <= endOfParagraphCharOffset.intValue()
627.             && endOfParagraphCharOffset.intValue() < papx
634.                 papx.getGrpprl(), 2 );
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
99. static Paragraph newParagraph( Range parent, PAPX papx )
106.     properties.setIstd( papx.getIstd() );
111.             papx.getGrpprl(), 2 );
138.                         properties, papx.getGrpprl(), 2 );
{% endhighlight %}

***

