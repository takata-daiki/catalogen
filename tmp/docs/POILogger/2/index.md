# POILogger @Cluster 2 (error, property, set)

***

### [FileInformationBlock.java](https://searchcode.com/codesearch/view/97384033/)
> sets the 
{% highlight java %}
52. public static final POILogger logger = POILogFactory
179.     logger.log( POILogger.WARN, "Since FIB.nFib == ", strNFib,
{% endhighlight %}

***

### [Picture.java](https://searchcode.com/codesearch/view/97384428/)
> sets the 2 - d color of the drawing group 
{% highlight java %}
62. private static final POILogger log = POILogFactory
186.             log.log( POILogger.INFO,
{% endhighlight %}

***

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
> sets the 
{% highlight java %}
82. private static final POILogger logger = POILogFactory
740.         logger.log( POILogger.WARN, "Table without body starting at [",
{% endhighlight %}

***

### [EscherMetafileBlip.java](https://searchcode.com/codesearch/view/97383934/)
> sets the 
{% highlight java %}
36. private static final POILogger log = POILogFactory.getLogger(EscherMetafileBlip.class);
162.         log.log(POILogger.WARN, "Possibly corrupt compression or non-compressed data", e);
295.     log.log(POILogger.WARN, "Unknown metafile: " + getRecordId());
{% endhighlight %}

***

### [AbstractWordUtils.java](https://searchcode.com/codesearch/view/97383984/)
> set the formula expression to use for the bottom border @ param see the one of the border 
{% highlight java %}
54. private static final POILogger logger = POILogFactory
451.         logger.log( POILogger.WARN, "Uknown or unmapped language code: ",
461.       logger.log( POILogger.INFO, "NYI: toListItemNumberLabel(): " + format );
{% endhighlight %}

***

### [CharacterSprmUncompressor.java](https://searchcode.com/codesearch/view/97384370/)
> sets the , and false for ) @ param cell 
{% highlight java %}
37. private static final POILogger logger = POILogFactory
81.             logger.log( POILogger.ERROR, "Unable to apply all style ",
95.         logger.log( POILogger.ERROR,
115.                 logger.log( POILogger.WARN,
715.       logger.log( POILogger.DEBUG, "Unknown CHP sprm ignored: " + sprm );
{% endhighlight %}

***

### [POIDocument.java](https://searchcode.com/codesearch/view/97383067/)
> sets the 
{% highlight java %}
58. private final static POILogger logger = POILogFactory.getLogger(POIDocument.class);
130.     logger.log(POILogger.WARN, "DocumentSummaryInformation property set came back with wrong class - ", ps.getClass());
138.     logger.log(POILogger.WARN, "SummaryInformation property set came back with wrong class - ", ps.getClass());
159.       logger.log(POILogger.WARN, "Error getting property set with name " + setName + "\n" + ie);
169.       logger.log(POILogger.WARN, "Error creating property set with name " + setName + "\n" + ie);
172.       logger.log(POILogger.WARN, "Error creating property set with name " + setName + "\n" + he);
227.     logger.log(POILogger.INFO, "Wrote property set " + name + " of size " + data.length);
229.     logger.log( POILogger.ERROR, "Couldn't write property set with name " + name + " as not supported by HPSF yet");
{% endhighlight %}

***

