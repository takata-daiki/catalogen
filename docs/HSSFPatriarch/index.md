# HSSFPatriarch

***

### [Cluster 1](./1)
{% highlight java %}
713. private void convertPatriarch( HSSFPatriarch patriarch )
735.     spgr.setRectX1( patriarch.getX1() );
736.     spgr.setRectY1( patriarch.getY1() );
737.     spgr.setRectX2( patriarch.getX2() );
738.     spgr.setRectY2( patriarch.getY2() );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
76. HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
81. patriarch.createPicture(anchor, pictureIndex);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
280. protected HSSFPatriarch patriarch;
576.         if ( patriarch.getChildren().size() != 0 )
735.     spgr.setRectX1( patriarch.getX1() );
736.     spgr.setRectY1( patriarch.getY1() );
737.     spgr.setRectX2( patriarch.getX2() );
738.     spgr.setRectY2( patriarch.getY2() );
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
210. final HSSFPatriarch patr = this.getDrawingPatriarch();
216. final HSSFComment comment = patr.createComment(anchor);
{% endhighlight %}

***

