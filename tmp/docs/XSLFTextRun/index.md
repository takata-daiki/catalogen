# XSLFTextRun

***

### [Cluster 1](./1)
{% highlight java %}
117. for (XSLFTextRun r : _runs) {
118.     out.append(r.getText());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
861. for (XSLFTextRun run : _runs){
862.     int length = run.getRenderableText().length();
872.     if (run.isBold() && run.isItalic()) {
874.     } else if (run.isItalic()) {
876.     } else if (run.isBold()) {
880.     ColorStateList color = ColorStateList.valueOf(run.getFontColor().getRGB());
882.     at.setSpan(new TextAppearanceSpan(run.getFontFamily(), style, (int) run.getFontSize(),
902.     if(run.isUnderline()) {
905.     if(run.isStrikethrough()) {
916.     if (run.isSuperscript()) {
919.     if (run.isSubscript()) {
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
27. XSLFTextRun r1 = p1.addNewTextRun();
28. r1.setText("Thank you for your attention!");
29. r1.setFontFamily("Verdana");
30. r1.setFontColor(new Color(255, 0, 102));
31. r1.setFontSize(32);
32. r1.setBold(true);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
81. XSLFTextRun r = p.addNewTextRun();
82. r.setText(previewTableHeader[i]);
83. r.setBold(true);
84. r.setFontColor(Color.white);
85. r.setFontSize(11);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
99. XSLFTextRun nCellR = nCellP.addNewTextRun();
100. nCellR.setText(String.valueOf(repDocsStartNum++));
101. nCellR.setFontSize(11);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
105. XSLFTextRun cityCellR = cityCellP.addNewTextRun();
106. cityCellR.setText(cityName);
107. cityCellR.setFontSize(11);
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
40. XSLFTextRun r2 = p2.addNewTextRun();
41. r2.setText("Group of companies «Trast Group BTL»\n" +
44. r2.setFontColor(Color.black);
45. r2.setFontSize(19);
46. r2.setFontFamily("Verdana");
{% endhighlight %}

***

