# XSSFFont

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
83. XSSFFont xssfFont = theCell.getCellStyle().getFont();
84.   return xssfFont.getUnderline() != 0;
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
78. XSSFFont xssfFont = theCell.getCellStyle().getFont();
79. return xssfFont.getStrikeout();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
88. XSSFFont xssfFont = theCell.getCellStyle().getFont();
89.   return xssfFont.getItalic();
{% endhighlight %}

***

## [Cluster 4](./4)
6 results
> code comments is here.
{% highlight java %}
209. XSSFFont xssfFont = (XSSFFont) f;
210. label.setForeground(awtColor(xssfFont.getXSSFColor(), java.awt.Color.BLACK));
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> code comments is here.
{% highlight java %}
167. private Font getFont(XSSFFont xssfFont) {
171.         String name = xssfFont.getFontName();
172.         int size = xssfFont.getFontHeightInPoints();
174.         if (xssfFont.getBoldweight() == XSSFFont.BOLDWEIGHT_BOLD) {
176.             if (xssfFont.getItalic()) {
180.         else if (xssfFont.getItalic()) {
{% endhighlight %}

***

## [Cluster 6](./6)
3 results
> code comments is here.
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

