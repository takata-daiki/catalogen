# XSSFFont

***

### [Cluster 1](./1)
{% highlight java %}
78. XSSFFont xssfFont = theCell.getCellStyle().getFont();
79. return xssfFont.getStrikeout();
{% endhighlight %}

***

### [Cluster 2](./2)
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

### [Cluster 3](./3)
{% highlight java %}
42. XSSFFont font = xssfWorkbook.createFont();
43. font.setBoldweight((short) 700);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
180. XSSFFont xFont = (XSSFFont)font;
184.   xFont.setColor(xColour);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
209. XSSFFont xssfFont = (XSSFFont) f;
210. label.setForeground(awtColor(xssfFont.getXSSFColor(), java.awt.Color.BLACK));
{% endhighlight %}

***

