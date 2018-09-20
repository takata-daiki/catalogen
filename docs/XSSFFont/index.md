# XSSFFont

***

## [Cluster 1](./1)
13 results
> create the . @ param font the font to apply . 
{% highlight java %}
44. XSSFFont font = xssfWorkbook.createFont();
45. font.setBoldweight((short) 700);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
83. XSSFFont xssfFont = theCell.getCellStyle().getFont();
84.   return xssfFont.getUnderline() != 0;
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> sets the line count . @ param 3 2 or no have { @ link # the _ size } or { @ link # link _ type _ 6 _ 6 _ 4 } , or { @ code 0 } ) . 
{% highlight java %}
78. XSSFFont xssfFont = theCell.getCellStyle().getFont();
79. return xssfFont.getStrikeout();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
88. XSSFFont xssfFont = theCell.getCellStyle().getFont();
89.   return xssfFont.getItalic();
{% endhighlight %}

***

## [Cluster 5](./5)
6 results
> adds a 
{% highlight java %}
209. XSSFFont xssfFont = (XSSFFont) f;
210. label.setForeground(awtColor(xssfFont.getXSSFColor(), java.awt.Color.BLACK));
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> this comment could not be generated...
{% highlight java %}
167. private Font getFont(XSSFFont xssfFont) {
171.         String name = xssfFont.getFontName();
172.         int size = xssfFont.getFontHeightInPoints();
174.         if (xssfFont.getBoldweight() == XSSFFont.BOLDWEIGHT_BOLD) {
176.             if (xssfFont.getItalic()) {
180.         else if (xssfFont.getItalic()) {
{% endhighlight %}

***

## [Cluster 7](./7)
3 results
> this method specifies a font to the specified line text , when null is < code > null < / code > as no - 2 0 . < p > works when the type of the attribute is set to 0 . < / p > 
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

