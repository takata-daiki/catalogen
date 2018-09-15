# HSSFFont @Cluster 1

***

### [HSSFFont.java](https://searchcode.com/codesearch/view/97401500/)
{% highlight java %}
348. final HSSFFont other = (HSSFFont) obj;
350.   if (other.font != null)
352. } else if (!font.equals(other.font))
354. if (index != other.index)
{% endhighlight %}

***

### [CellHSSFImpl.java](https://searchcode.com/codesearch/view/72854667/)
{% highlight java %}
171. private Font getFont(HSSFFont hssfFont) {
174.         String name = hssfFont.getFontName();
175.         int size = hssfFont.getFontHeightInPoints();
177.         if (hssfFont.getBoldweight() == HSSFFont.BOLDWEIGHT_BOLD) {
179.             if (hssfFont.getItalic()) {
183.         else if (hssfFont.getItalic()) {
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
{% highlight java %}
42. HSSFFont font = newStyle.getFont(workbook);
43. if (font.getBoldweight() == HSSFFont.BOLDWEIGHT_BOLD)
45. if (font.getItalic())
47. if (font.getUnderline() != HSSFFont.U_NONE)
50. if (font.getFontHeightInPoints() != 10
51.     && font.getFontHeightInPoints() != 11)
54. if (!font.getFontName().equals("Arial")
55.     && !font.getFontName().equals("Calibri"))
57. if ((font.getColor() != HSSFFont.COLOR_NORMAL)
58.     && (getRGBString(font.getColor()) != null)
59.     && !getRGBString(font.getColor()).equals("#000"))
{% endhighlight %}

***

### [HSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498463/)
{% highlight java %}
69. HSSFFont font = newStyle.getFont(workbook);
70. if (font.getBoldweight() == HSSFFont.BOLDWEIGHT_BOLD) {
75. if (font.getItalic()) {
80. if (font.getUnderline() != HSSFFont.U_NONE) {
86. if (font.getFontHeightInPoints() != 10
87.     && font.getFontHeightInPoints() != 11) {
89.   xmlWriter.writeCharacters(String.valueOf(font
94. if (!font.getFontName().equals("Arial")
95.     && !font.getFontName().equals("Calibri")) {
97.   xmlWriter.writeCharacters(font.getFontName());
100. if ((font.getColor() != HSSFFont.COLOR_NORMAL)
101.     && (getRGBString(font.getColor()) != null)
102.     && !getRGBString(font.getColor()).equals("#000")) {
104.   xmlWriter.writeCharacters(getRGBString(font.getColor()));
{% endhighlight %}

***

### [HSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283811/)
{% highlight java %}
38. HSSFFont font = newStyle.getFont(workbook);
39. if(font.getBoldweight() == HSSFFont.BOLDWEIGHT_BOLD)
41. if(font.getItalic())
43. if(font.getUnderline() != HSSFFont.U_NONE)
46. if(font.getFontHeightInPoints() != 10 && font.getFontHeightInPoints() != 11)
47.   element.addElement("font-size").addText(String.valueOf(font.getFontHeightInPoints() + "pt"));
49. if(!font.getFontName().equals("Arial") && !font.getFontName().equals("Calibri"))
50.   element.addElement("font-family").addText(font.getFontName());
51. if((font.getColor() != HSSFFont.COLOR_NORMAL) && !getRGBString(font.getColor()).equals("#000"))
52.   element.addElement("color").addText(getRGBString(font.getColor()));
{% endhighlight %}

***

