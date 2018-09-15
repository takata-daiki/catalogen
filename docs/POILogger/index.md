# POILogger

***

## [Cluster 1](./1)
8 results
> code comments is here.
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

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
124. private static final POILogger logger = POILogFactory
453.             logger.log( POILogger.WARN, "Latest structure in ", range,
679.     logger.log( POILogger.WARN, debug );
713.         logger.log( POILogger.WARN,
754.         logger.log( POILogger.WARN, "Characters #" + characterRun
819.             logger.log( POILogger.WARN, parentRange + " contains " + field
905.     logger.log( POILogger.WARN, parentRange + " contains " + field
1017.         logger.log( POILogger.WARN, "Referenced OLE2 object '",
{% endhighlight %}

***

