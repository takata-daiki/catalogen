# XSSFCellStyle

***

### [Cluster 1](./1)
{% highlight java %}
221. XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle1;
223. if (xssfCellStyle.getBorderTop()!=BorderStyle.NONE) {
224.   topBorder = BorderFactory.createMatteBorder(getThickness(cellStyle1.getBorderTop()), 0, 0, 0, awtColor(xssfCellStyle.getTopBorderXSSFColor(), java.awt.Color.BLACK));
227.   bottomBorder = BorderFactory.createMatteBorder(0, 0, getThickness(cellStyle1.getBorderBottom()), 0, awtColor(xssfCellStyle.getBottomBorderXSSFColor(), java.awt.Color.BLACK));
231.   leftBorder = BorderFactory.createMatteBorder(0, getThickness(cellStyle1.getBorderLeft()), 0, 0, awtColor(xssfCellStyle.getLeftBorderXSSFColor(), java.awt.Color.BLACK));
234.   rightBorder = BorderFactory.createMatteBorder(0, 0, 0, getThickness(cellStyle1.getBorderRight()), awtColor(xssfCellStyle.getRightBorderXSSFColor(), java.awt.Color.BLACK));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
422. XSSFCellStyle cellStyle = (XSSFCellStyle) book.getCellStyleAt(i);
424. if (cellStyle.getFillForegroundXSSFColor() == newColor)
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
45. XSSFCellStyle cellStyle = sheet.getRow(0).getCell(0).getCellStyle();
49.     System.out.println("Found cellstule with index " + i + " vs. " + cellStyle.getIndex());
51. System.out.println("Fill ID: " + cellStyle.getCoreXf().getFillId());
56.   XSSFCellFill fill = stylesSource.getFillAt(cellStyle.getIndex()+1);
59.     System.out.println("Index: " + cellStyle.getIndex() + " "+ fill.getFillBackgroundColor().getARGBHex());
62.   System.out.println("Index: " +cellStyle.getIndex() + " "+ fill.getFillForegroundColor().getARGBHex());
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
159. XSSFCellStyle cellStyle = theCell.getCellStyle();
163. XSSFFont xssfFont = cellStyle.getFont();
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
168. XSSFCellStyle xssfCellStyle = xssfCell.getCellStyle();
170. XSSFColor fillForegroundColorColor = xssfCellStyle.getFillForegroundColorColor();
172.   short fillId = (short) xssfCellStyle.getCoreXf().getFillId();
178. f = designer.workbook.getFontAt(xssfCellStyle.getFontIndex());
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
287. XSSFCellStyle cellStyle = theCell.getCellStyle();
291. short xssfAlignment = cellStyle.getAlignment();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
198. XSSFCellStyle cellStyle = (XSSFCellStyle)style;
201.   cellStyle.setFillForegroundColor(xColour);
202.   cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
24. XSSFCellStyle newStyle = (XSSFCellStyle) style;
25. XSSFColor colour = newStyle.getFillForegroundXSSFColor();
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
262. XSSFCellStyle style = styles.get(colour);
266.   style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND );
267.   style.setFillForegroundColor(col);
{% endhighlight %}

***

### [Cluster 10](./10)
{% highlight java %}
46. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
47. headerStyle.setFillForegroundColor(LAVENDER.index);
48. headerStyle.setFont(font);
49. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [Cluster 11](./11)
{% highlight java %}
30. XSSFCellStyle newStyle = (XSSFCellStyle) style;
31. XSSFFont font = newStyle.getFont();
{% endhighlight %}

***

### [Cluster 12](./12)
{% highlight java %}
67. XSSFCellStyle style = dataSheet.getWorkbook().createCellStyle();
68. style.setFont(font);
69.   style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
70.   style.setFillPattern(CellStyle.SOLID_FOREGROUND);
{% endhighlight %}

***

### [Cluster 13](./13)
{% highlight java %}
139. XSSFCellStyle cellStyle = theCell.getCellStyle();        
144. XSSFFont font = cellStyle.getFont();
147.     cellStyle.setFont(font);
{% endhighlight %}

***

### [Cluster 14](./14)
{% highlight java %}
309. XSSFCellStyle cs = this.wb.createCellStyle();
310.   cs.setWrapText(true);
313.   cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
314.   cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
315.   cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
316.   cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
317.   cs.setFillForegroundColor(color);
318.   cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);   
321.   cs.setFont(font);
{% endhighlight %}

***

### [Cluster 15](./15)
{% highlight java %}
326. XSSFCellStyle cs = this.wb.createCellStyle();
327.   cs.setWrapText(true);
328.   cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
329.   cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
330.   cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
331.   cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
{% endhighlight %}

***

### [Cluster 16](./16)
{% highlight java %}
51. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
52. dataStyle.setWrapText(true);
53. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [Cluster 17](./17)
{% highlight java %}
439. XSSFCellStyle newStyle = (XSSFCellStyle) book.createCellStyle();
440. newStyle.setFillForegroundColor(newColor);
{% endhighlight %}

***

