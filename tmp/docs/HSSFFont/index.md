# HSSFFont

***

## [Cluster 1](./1)
5 results
> code comments is here.
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

