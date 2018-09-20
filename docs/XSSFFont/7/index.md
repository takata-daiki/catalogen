# XSSFFont @Cluster 7

***

### [XSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498472/)
{% highlight java %}
31. XSSFFont font = newStyle.getFont();
32. if (font.getBold())
34. if (font.getItalic())
36. if (font.getUnderline() != XSSFFont.U_NONE)
38. if (font.getFontHeight() != XSSFFont.DEFAULT_FONT_SIZE)
40. if (!font.getFontName().equals(XSSFFont.DEFAULT_FONT_NAME))
42. if (font.getColor() != XSSFFont.DEFAULT_FONT_COLOR) {
43.   String colorString = getRGBString(font.getXSSFColor());
{% endhighlight %}

***

### [XSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498472/)
{% highlight java %}
55. XSSFFont font = newStyle.getFont();
56. if (font.getBold()) {
61. if (font.getItalic()) {
66. if (font.getUnderline() != XSSFFont.U_NONE) {
71. if (font.getFontHeight() != XSSFFont.DEFAULT_FONT_SIZE) {
73.   xmlWriter.writeCharacters(String.valueOf(font
78. if (!font.getFontName().equals(XSSFFont.DEFAULT_FONT_NAME)) {
80.   xmlWriter.writeCharacters(String.valueOf(font.getFontName()));
83. if (font.getColor() != XSSFFont.DEFAULT_FONT_COLOR) {
84.   String colorString = getRGBString(font.getXSSFColor());
{% endhighlight %}

***

### [XSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283803/)
{% highlight java %}
26. XSSFFont font = newStyle.getFont();
27. if(font.getBold())
29. if(font.getItalic())
31. if(font.getUnderline() != XSSFFont.U_NONE)
33. if(font.getFontHeight() != XSSFFont.DEFAULT_FONT_SIZE)
34.   element.addElement("font-size").addText(String.valueOf(font.getFontHeightInPoints()) + "pt");
35. if(!font.getFontName().equals(XSSFFont.DEFAULT_FONT_NAME))
36.   element.addElement("font-family").addText(font.getFontName());
37. if(font.getColor() != XSSFFont.DEFAULT_FONT_COLOR)
39.   String colorString = getRGBString(font.getXSSFColor());
{% endhighlight %}

***

