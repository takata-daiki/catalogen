# XSLFTextRun @Cluster 1

***

### [XSLFTextRun.java](https://searchcode.com/codesearch/view/97406808/)
{% highlight java %}
518. void copy(XSLFTextRun r){
519.     String srcFontFamily = r.getFontFamily();
524.     Color srcFontColor = r.getFontColor();
529.     double srcFontSize = r.getFontSize();
534.     boolean bold = r.isBold();
537.     boolean italic = r.isItalic();
540.     boolean underline = r.isUnderline();
543.     boolean strike = r.isStrikethrough();
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
{% highlight java %}
1179. XSLFTextRun r2 = tgtR.get(i);
1180. r2.copy(r1);
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
{% highlight java %}
125. for (XSLFTextRun r : _runs) {
126.     out.append(r.getRenderableText());
{% endhighlight %}

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
{% highlight java %}
117. for (XSLFTextRun r : _runs) {
118.     out.append(r.getText());
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
111. XSLFTextRun netCellR = netCellP.addNewTextRun();
112. netCellR.setText(dataRow.getNetwork());
113. netCellR.setFontSize(11);
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
117. XSLFTextRun addressCellR = addressCellP.addNewTextRun();
118. addressCellR.setText(dataRow.getAddress());
119. addressCellR.setFontSize(11);
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
99. XSLFTextRun nCellR = nCellP.addNewTextRun();
100. nCellR.setText(String.valueOf(repDocsStartNum++));
101. nCellR.setFontSize(11);
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
105. XSLFTextRun cityCellR = cityCellP.addNewTextRun();
106. cityCellR.setText(cityName);
107. cityCellR.setFontSize(11);
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
81. XSLFTextRun r = p.addNewTextRun();
82. r.setText(previewTableHeader[i]);
83. r.setBold(true);
84. r.setFontColor(Color.white);
85. r.setFontSize(11);
{% endhighlight %}

***

### [MarketingReportInCityGeneratorImpl.java](https://searchcode.com/codesearch/view/92131916/)
{% highlight java %}
60. XSLFTextRun r2 = p2.addNewTextRun();
61. r2.setText("Address program: " + cityName);
62. r2.setFontColor(Color.black);
63. r2.setFontFamily("Verdana");
64. r2.setFontSize(18);
65. r2.setBold(true);
{% endhighlight %}

***

### [MarketingReportFirstSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131912/)
{% highlight java %}
56. XSLFTextRun r2 = p2.addNewTextRun();
57. r2.setText(marketingReportDataOneCity.getMarketingSourceByCity().getCityName()
59. r2.setFontFamily("Verdana");
60. r2.setFontColor(Color.black);
61. r2.setFontSize(16);
{% endhighlight %}

***

### [MarketingReportLastSlideGeneratorImpl.java](https://searchcode.com/codesearch/view/92131911/)
{% highlight java %}
40. XSLFTextRun r2 = p2.addNewTextRun();
41. r2.setText("Group of companies «Trast Group BTL»\n" +
44. r2.setFontColor(Color.black);
45. r2.setFontSize(19);
46. r2.setFontFamily("Verdana");
{% endhighlight %}

***

