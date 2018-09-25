# XSLFTextShape @Cluster 1 (_shape, getsheet, txt)

***

### [XSLFSheet.java](https://searchcode.com/codesearch/view/97406768/)
> create a new comment located in this workbook @ return new newly created shape 
{% highlight java %}
384. XSLFTextShape txt = (XSLFTextShape)shape;
385.  if(txt.getTextType() == type) {
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
> copy of the table from this workbook . 
{% highlight java %}
1101. XSLFTextShape shape = getParentShape();
1102. ok = shape.fetchShapeProperty(visitor);
1104.     CTPlaceholder ph = shape.getCTPlaceholder();
{% endhighlight %}

***

### [XSLFTextRun.java](https://searchcode.com/codesearch/view/97406808/)
> text with the given relationship set . 
{% highlight java %}
492. XSLFTextShape shape = _p.getParentShape();
493. ok = shape.fetchShapeProperty(fetcher);
495.     CTPlaceholder ph = shape.getCTPlaceholder();
498.         XMLSlideShow ppt = shape.getSheet().getSlideShow();
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
> test that we get the same value as excel and , for 
{% highlight java %}
81. private final XSLFTextShape _shape;
734.     double leftInset = _shape.getLeftInset();
735.     double rightInset = _shape.getRightInset();
744.     if(!_shape.getWordWrap()) {
746.         width = _shape.getSheet().getSlideShow().getPageSize().getWidth() - anchor.getX();
1056.     CTPlaceholder ph = _shape.getCTPlaceholder();
1078.     XSLFSheet masterSheet = _shape.getSheet();
{% endhighlight %}

***

