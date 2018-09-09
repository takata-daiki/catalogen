# XSLFTextParagraph

***

### [Cluster 1](./1)
{% highlight java %}
25. XSLFTextParagraph p1 = txt1.addNewTextParagraph();
27. XSLFTextRun r1 = p1.addNewTextRun();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
534. XSLFTextParagraph p = _paragraphs.get(i);
536. y += p.draw(graphics, x, y);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
1123. void copy(XSLFTextParagraph p){
1124.     TextAlign srcAlign = p.getTextAlign();
1129.     boolean isBullet = p.isBullet();
1133.             String buFont = p.getBulletFont();
1137.             String buChar = p.getBulletCharacter();
1141.             Color buColor = p.getBulletFontColor();
1145.             double buSize = p.getBulletFontSize();
1152.     double leftMargin = p.getLeftMargin();
1157.     double indent = p.getIndent();
1162.     double spaceAfter = p.getSpaceAfter();
1166.     double spaceBefore = p.getSpaceBefore();
1170.     double lineSpacing = p.getLineSpacing();
1175.     List<XSLFTextRun> srcR = p.getTextRuns();
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
79. XSLFTextParagraph p = th.addNewTextParagraph();
80. p.setTextAlign(TextAlign.CENTER);
81. XSLFTextRun r = p.addNewTextRun();
{% endhighlight %}

***

