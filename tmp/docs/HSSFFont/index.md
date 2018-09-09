# HSSFFont

***

### [Cluster 1](./1)
{% highlight java %}
82. HSSFFont hssfFont = theCell.getCellStyle().getFont(getWorkbook());
83. return hssfFont.getStrikeout();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
108. final HSSFFont font = this.workbook.getFontAt ( style.getFontIndex () );
113. return font.getStrikeout ();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
209. HSSFFont f = wb.createFont();
210. f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
157. HSSFFont font = null;
167.   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
168.   font.setItalic(true);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
91. HSSFFont font2 = workbook.createFont();
92. font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
91. final HSSFFont font = workbook.createFont();
92. font.setFontHeightInPoints((short) 10);
93. font.setFontName("Arial");
95.   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
98.   font.setUnderline(HSSFFont.U_DOUBLE);
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
92. private HSSFFont fontWhiteColor;
250.   fontWhiteColor.setColor((short)1);
{% endhighlight %}

***

### [Cluster 8](./8)
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

### [Cluster 9](./9)
{% highlight java %}
324. final HSSFFont font = workbook.getFontAt ( cell.getCellStyle ().getFontIndex () );
327.     value.setStrikeThrough ( font.getStrikeout () );
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
120. private HSSFFont m_fontHeader = null;
161.       m_fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
{% endhighlight %}

***

