# XSLFTextShape

***

### [Cluster 1](./1)
{% highlight java %}
411. XSLFTextShape sShape = (XSLFTextShape)sh;
412. CTPlaceholder ph = sShape.getCTPlaceholder();
{% endhighlight %}

***

### [Cluster 2](./2)
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

### [Cluster 3](./3)
{% highlight java %}
178. XSLFTextShape txt = getTextShapeByType(Placeholder.TITLE);
179. return txt == null ? "" : txt.getText();
{% endhighlight %}

***

