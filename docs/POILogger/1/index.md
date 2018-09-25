# POILogger @Cluster 1 (at, error, this)

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
> set normal , super or subscript , that representing the vertical - alignment . 
{% highlight java %}
175. private POILogger log = POILogFactory.getLogger( getClass() );
1104.                 log.log(
{% endhighlight %}

***

### [PPGraphics2D.java](https://searchcode.com/codesearch/view/97394403/)
> set the content of this part . 
{% highlight java %}
46. protected POILogger log = POILogFactory.getLogger(this.getClass());
362.     log.log(POILogger.WARN, "Not implemented");
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
> sets the 
{% highlight java %}
38. private static POILogger log = POILogFactory.getLogger( Paragraph.class );
122.             log.log( POILogger.WARN, "Paragraph refers to LFO #",
{% endhighlight %}

***

### [Shape.java](https://searchcode.com/codesearch/view/97394276/)
> set the content to use for the bottom border 
{% highlight java %}
60. protected POILogger logger = POILogFactory.getLogger(this.getClass());
183.             logger.log(POILogger.WARN, "EscherSpRecord.FLAG_CHILD is set but EscherChildAnchorRecord was not found");
488.     logger.log(POILogger.INFO, "Rendering " + getShapeName());
{% endhighlight %}

***

### [POILogFactory.java](https://searchcode.com/codesearch/view/97399957/)
> set the rgb value for accent colour 
{% highlight java %}
45. private static POILogger _nullLogger = new NullLogger();
101.         _loggerClassName = _nullLogger.getClass().getName();
107.     if(_loggerClassName.equals(_nullLogger.getClass().getName())) {
{% endhighlight %}

***

### [HSLFSlideShow.java](https://searchcode.com/codesearch/view/97394255/)
> sets the 
{% highlight java %}
75. private POILogger logger = POILogFactory.getLogger(this.getClass());
323.   logger.log(POILogger.ERROR, "Error finding Current User Atom:\n" + ie);
392.     logger.log(POILogger.ERROR, "Problem reading picture: Invalid image type 0, on picture with length " + imgsize + ".\nYou document will probably become corrupted if you save it!");
393.     logger.log(POILogger.ERROR, "" + pos);
449.       logger.log(POILogger.ERROR, "Problem reading picture: " + e + "\nYou document will probably become corrupted if you save it!");
{% endhighlight %}

***

### [EscherGraphics2d.java](https://searchcode.com/codesearch/view/97401504/)
> sets the 
{% highlight java %}
80. private POILogger logger = POILogFactory.getLogger(getClass());
158.         if (logger.check(POILogger.WARN))
159.             logger.log(POILogger.WARN, "draw not fully supported");
177.     if (logger.check( POILogger.WARN ))
178.         logger.log(POILogger.WARN,"drawImage() not supported");
317.         logger.log(POILogger.WARN,"fill(Shape) not supported");
{% endhighlight %}

***

### [SlideShow.java](https://searchcode.com/codesearch/view/97394959/)
> sets the , and that ' s graphics 2 one document . 
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

