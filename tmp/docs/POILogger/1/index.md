# POILogger @Cluster 1

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
{% highlight java %}
175. private POILogger log = POILogFactory.getLogger( getClass() );
1104.                 log.log(
{% endhighlight %}

***

### [PPGraphics2D.java](https://searchcode.com/codesearch/view/97394403/)
{% highlight java %}
46. protected POILogger log = POILogFactory.getLogger(this.getClass());
362.     log.log(POILogger.WARN, "Not implemented");
{% endhighlight %}

***

### [POILogFactory.java](https://searchcode.com/codesearch/view/97399957/)
{% highlight java %}
82. POILogger logger = null;
121.       logger.initialize(cat);
{% endhighlight %}

***

### [XMLSlideShow.java](https://searchcode.com/codesearch/view/97406883/)
{% highlight java %}
67. private static POILogger _logger = POILogFactory.getLogger(XMLSlideShow.class);
148.                     _logger.log(POILogger.WARN, "Slide with r:id " + slId.getId() + " was defined, but didn't exist in package, skipping");
{% endhighlight %}

***

### [BitmapPainter.java](https://searchcode.com/codesearch/view/97394526/)
{% highlight java %}
58. protected POILogger logger = POILogFactory.getLogger(this.getClass());
71.         logger.log(POILogger.WARN, "ImageIO failed to create image. image.type: " + pict.getType());
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
38. private static POILogger log = POILogFactory.getLogger( Paragraph.class );
122.             log.log( POILogger.WARN, "Paragraph refers to LFO #",
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
{% highlight java %}
60. protected POILogger logger = POILogFactory.getLogger(this.getClass());
183.             logger.log(POILogger.WARN, "EscherSpRecord.FLAG_CHILD is set but EscherChildAnchorRecord was not found");
488.     logger.log(POILogger.INFO, "Rendering " + getShapeName());
{% endhighlight %}

***

### [POILogFactory.java](https://searchcode.com/codesearch/view/97399957/)
{% highlight java %}
45. private static POILogger _nullLogger = new NullLogger();
101.         _loggerClassName = _nullLogger.getClass().getName();
107.     if(_loggerClassName.equals(_nullLogger.getClass().getName())) {
{% endhighlight %}

***

### [HSLFSlideShow.java](https://searchcode.com/codesearch/view/97394255/)
{% highlight java %}
75. private POILogger logger = POILogFactory.getLogger(this.getClass());
323.   logger.log(POILogger.ERROR, "Error finding Current User Atom:\n" + ie);
392.     logger.log(POILogger.ERROR, "Problem reading picture: Invalid image type 0, on picture with length " + imgsize + ".\nYou document will probably become corrupted if you save it!");
393.     logger.log(POILogger.ERROR, "" + pos);
449.       logger.log(POILogger.ERROR, "Problem reading picture: " + e + "\nYou document will probably become corrupted if you save it!");
{% endhighlight %}

***

### [EscherGraphics2d.java](https://searchcode.com/codesearch/view/97401504/)
{% highlight java %}
80. private POILogger logger = POILogFactory.getLogger(getClass());
158.         if (logger.check(POILogger.WARN))
159.             logger.log(POILogger.WARN, "draw not fully supported");
177.     if (logger.check( POILogger.WARN ))
178.         logger.log(POILogger.WARN,"drawImage() not supported");
317.         logger.log(POILogger.WARN,"fill(Shape) not supported");
{% endhighlight %}

***

### [OPCPackage.java](https://searchcode.com/codesearch/view/97406292/)
{% highlight java %}
69. private static POILogger logger = POILogFactory.getLogger(OPCPackage.class);
359.   logger.log(POILogger.WARN, 
640.          logger.log(POILogger.WARN, "OPC Compliance error [M4.1]: " +
665.         logger.log(POILogger.WARN, "Unmarshall operation : IOException for "
891.     logger
993.   logger.log(POILogger.WARN, "An exception occurs while deleting part '"
1267.   logger.log(POILogger.WARN, "The specified content type is not valid: '"
{% endhighlight %}

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
{% highlight java %}
83. private POILogger logger = POILogFactory.getLogger(this.getClass());
253.   logger.log(POILogger.ERROR,
357.         logger.log(POILogger.ERROR, "A Notes SlideAtomSet at " + i
385.         logger.log(POILogger.ERROR, "A Slide SlideAtomSet at " + i
416.         logger.log(POILogger.ERROR, "Notes not found for noteId=" + noteId);
730.   logger.log(POILogger.INFO, "Added slide " + _slides.length + " with ref " + sp.getRefID()
780.   logger.log(POILogger.INFO, "New slide ended up at " + slideOffset);
{% endhighlight %}

***

