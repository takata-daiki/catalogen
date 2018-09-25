# XSLFTextShape

***

## [Cluster 1 (_shape, getsheet, txt)](./1)
4 results
> create a new comment located in this workbook @ return new newly created shape 
{% highlight java %}
384. XSLFTextShape txt = (XSLFTextShape)shape;
385.  if(txt.getTextType() == type) {
{% endhighlight %}

***

## [Cluster 2 (double, tsh, xslftextshape)](./2)
1 results
> sets all the formula expression 1 and is not if you set code to ( { @ link # and ( ) } than { @ link # @ ( org . apache . poi . ss . usermodel . workbook , int ) } < p > 
{% highlight java %}
579. XSLFTextShape tsh = (XSLFTextShape)sh;
581. boolean srcWordWrap = tsh.getWordWrap();
586. double leftInset = tsh.getLeftInset();
590. double rightInset = tsh.getRightInset();
594. double topInset = tsh.getTopInset();
598. double bottomInset = tsh.getBottomInset();
603. VerticalAlignment vAlign = tsh.getVerticalAlignment();
608. List<XSLFTextParagraph> srcP = tsh.getTextParagraphs();
{% endhighlight %}

***

## [Cluster 3 (ctplaceholder, sshape, xslftextshape)](./3)
1 results
> copy of this picture @ param workbook the workbook to get 
{% highlight java %}
411. XSLFTextShape sShape = (XSLFTextShape)sh;
412. CTPlaceholder ph = sShape.getCTPlaceholder();
{% endhighlight %}

***

## [Cluster 4 (gettext, gettextshapebytype, txt)](./4)
1 results
> returns the distance ( in points ) between the edge of the text frame and the edge of the inscribed rectangle of the shape that contains the text . default value is 1 / 2 0 inch . 
{% highlight java %}
178. XSLFTextShape txt = getTextShapeByType(Placeholder.TITLE);
179. return txt == null ? "" : txt.getText();
{% endhighlight %}

***

