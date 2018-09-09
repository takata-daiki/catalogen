# POILogger

***

### [Cluster 1](./1)
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

### [Cluster 2](./2)
{% highlight java %}
82. POILogger logger = null;
121.       logger.initialize(cat);
{% endhighlight %}

***

