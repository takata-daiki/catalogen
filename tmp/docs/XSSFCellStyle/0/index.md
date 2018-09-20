# XSSFCellStyle @Cluster 1

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
{% highlight java %}
159. XSSFCellStyle cellStyle = theCell.getCellStyle();
163. XSSFFont xssfFont = cellStyle.getFont();
{% endhighlight %}

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
{% highlight java %}
211. XSSFCellStyle cellStyle = theCell.getCellStyle();
216.     XSSFColor xssfColour = cellStyle.getFillForegroundXSSFColor();
{% endhighlight %}

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
{% highlight java %}
262. XSSFCellStyle style = styles.get(colour);
266.   style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND );
267.   style.setFillForegroundColor(col);
{% endhighlight %}

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
{% highlight java %}
139. XSSFCellStyle cellStyle = theCell.getCellStyle();        
144. XSSFFont font = cellStyle.getFont();
147.     cellStyle.setFont(font);
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
{% highlight java %}
221. XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle1;
223. if (xssfCellStyle.getBorderTop()!=BorderStyle.NONE) {
224.   topBorder = BorderFactory.createMatteBorder(getThickness(cellStyle1.getBorderTop()), 0, 0, 0, awtColor(xssfCellStyle.getTopBorderXSSFColor(), java.awt.Color.BLACK));
227.   bottomBorder = BorderFactory.createMatteBorder(0, 0, getThickness(cellStyle1.getBorderBottom()), 0, awtColor(xssfCellStyle.getBottomBorderXSSFColor(), java.awt.Color.BLACK));
231.   leftBorder = BorderFactory.createMatteBorder(0, getThickness(cellStyle1.getBorderLeft()), 0, 0, awtColor(xssfCellStyle.getLeftBorderXSSFColor(), java.awt.Color.BLACK));
234.   rightBorder = BorderFactory.createMatteBorder(0, 0, 0, getThickness(cellStyle1.getBorderRight()), awtColor(xssfCellStyle.getRightBorderXSSFColor(), java.awt.Color.BLACK));
{% endhighlight %}

***

### [StyleManagerXUtils.java](https://searchcode.com/codesearch/view/122565145/)
{% highlight java %}
198. XSSFCellStyle cellStyle = (XSSFCellStyle)style;
201.   cellStyle.setFillForegroundColor(xColour);
202.   cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
{% endhighlight %}

***

### [StyleManagerXUtils.java](https://searchcode.com/codesearch/view/122565145/)
{% highlight java %}
128. XSSFCellStyle xStyle = (XSSFCellStyle)style;
135.     xStyle.setBorderTop(xBorderStyle);
136.     xStyle.setTopBorderColor(xBorderColour);
140.     xStyle.setBorderLeft(xBorderStyle);
141.     xStyle.setLeftBorderColor(xBorderColour);
145.     xStyle.setBorderRight(xBorderStyle);
146.     xStyle.setRightBorderColor(xBorderColour);
150.     xStyle.setBorderBottom(xBorderStyle);
151.     xStyle.setBottomBorderColor(xBorderColour);
{% endhighlight %}

***

### [ExcelReport.java](https://searchcode.com/codesearch/view/71257075/)
{% highlight java %}
67. XSSFCellStyle style = dataSheet.getWorkbook().createCellStyle();
68. style.setFont(font);
69.   style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
70.   style.setFillPattern(CellStyle.SOLID_FOREGROUND);
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
326. XSSFCellStyle cs = this.wb.createCellStyle();
327.   cs.setWrapText(true);
328.   cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
329.   cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
330.   cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
331.   cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
{% endhighlight %}

***

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
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

### [WriteExcelBook.java](https://searchcode.com/codesearch/view/93053244/)
{% highlight java %}
337. XSSFCellStyle cs = wb.createCellStyle();
338.   cs.setWrapText(true);
341.   cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
342.   cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
343.   cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
344.   cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
345.   cs.setFillForegroundColor(color);
346.   cs.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);   
350.   cs.setFont(font);
{% endhighlight %}

***

### [XSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498472/)
{% highlight java %}
30. XSSFCellStyle newStyle = (XSSFCellStyle) style;
31. XSSFFont font = newStyle.getFont();
{% endhighlight %}

***

### [XSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498472/)
{% highlight java %}
54. XSSFCellStyle newStyle = (XSSFCellStyle) style;
55. XSSFFont font = newStyle.getFont();
{% endhighlight %}

***

### [XSSFXMLStyleHelper.java](https://searchcode.com/codesearch/view/110498472/)
{% highlight java %}
24. XSSFCellStyle newStyle = (XSSFCellStyle) style;
25. XSSFColor colour = newStyle.getFillForegroundXSSFColor();
{% endhighlight %}

***

### [XSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283803/)
{% highlight java %}
25. XSSFCellStyle newStyle = (XSSFCellStyle) style;
26. XSSFFont font = newStyle.getFont();
{% endhighlight %}

***

### [XSSFStyleHelper.java](https://searchcode.com/codesearch/view/112283803/)
{% highlight java %}
18. XSSFCellStyle newStyle = (XSSFCellStyle) style;
19. XSSFColor colour = newStyle.getFillForegroundXSSFColor();
{% endhighlight %}

***

### [RCellBlock.java](https://searchcode.com/codesearch/view/13928911/)
{% highlight java %}
256. XSSFCellStyle xssfStyle = (XSSFCellStyle)style;
257. xssfStyle.setFillForegroundColor( foreground );
258. xssfStyle.setFillBackgroundColor( background );
{% endhighlight %}

***

### [RCellBlock.java](https://searchcode.com/codesearch/view/13928911/)
{% highlight java %}
293. XSSFCellStyle xssfStyle = (XSSFCellStyle)style;
295.     xssfStyle.setBorderTop( borderTop );
296.     xssfStyle.setTopBorderColor( topBorderColor );
299.     xssfStyle.setBorderBottom( borderBottom );
300.     xssfStyle.setBottomBorderColor( bottomBorderColor );
303.     xssfStyle.setBorderLeft( borderLeft );
304.     xssfStyle.setLeftBorderColor( leftBorderColor );
307.     xssfStyle.setBorderRight( borderRight );
308.     xssfStyle.setRightBorderColor( rightBorderColor );
{% endhighlight %}

***

### [XLColorTest.java](https://searchcode.com/codesearch/view/121321469/)
{% highlight java %}
45. XSSFCellStyle cellStyle = sheet.getRow(0).getCell(0).getCellStyle();
49.     System.out.println("Found cellstule with index " + i + " vs. " + cellStyle.getIndex());
51. System.out.println("Fill ID: " + cellStyle.getCoreXf().getFillId());
56.   XSSFCellFill fill = stylesSource.getFillAt(cellStyle.getIndex()+1);
59.     System.out.println("Index: " + cellStyle.getIndex() + " "+ fill.getFillBackgroundColor().getARGBHex());
62.   System.out.println("Index: " +cellStyle.getIndex() + " "+ fill.getFillForegroundColor().getARGBHex());
{% endhighlight %}

***

### [StyleManagerXUtils.java](https://searchcode.com/codesearch/view/64530833/)
{% highlight java %}
211. XSSFCellStyle cellStyle = (XSSFCellStyle)style;
214.   cellStyle.setFillForegroundColor(xColour);
215.   cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
{% endhighlight %}

***

### [StyleManagerXUtils.java](https://searchcode.com/codesearch/view/64530833/)
{% highlight java %}
141. XSSFCellStyle xStyle = (XSSFCellStyle)style;
148.     xStyle.setBorderTop(xBorderStyle);
149.     xStyle.setTopBorderColor(xBorderColour);
153.     xStyle.setBorderLeft(xBorderStyle);
154.     xStyle.setLeftBorderColor(xBorderColour);
158.     xStyle.setBorderRight(xBorderStyle);
159.     xStyle.setRightBorderColor(xBorderColour);
163.     xStyle.setBorderBottom(xBorderStyle);
164.     xStyle.setBottomBorderColor(xBorderColour);
{% endhighlight %}

***

### [IcySpreadSheet.java](https://searchcode.com/codesearch/view/103223911/)
{% highlight java %}
422. XSSFCellStyle cellStyle = (XSSFCellStyle) book.getCellStyleAt(i);
424. if (cellStyle.getFillForegroundXSSFColor() == newColor)
{% endhighlight %}

***

### [IcySpreadSheet.java](https://searchcode.com/codesearch/view/103223911/)
{% highlight java %}
439. XSSFCellStyle newStyle = (XSSFCellStyle) book.createCellStyle();
440. newStyle.setFillForegroundColor(newColor);
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
{% highlight java %}
51. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
52. dataStyle.setWrapText(true);
53. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
{% highlight java %}
46. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
47. headerStyle.setFillForegroundColor(LAVENDER.index);
48. headerStyle.setFont(font);
49. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/)
{% highlight java %}
52. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
53. dataStyle.setWrapText(true);
54. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/)
{% highlight java %}
47. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
48. headerStyle.setFillForegroundColor(LAVENDER.index);
49. headerStyle.setFont(font);
50. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
{% highlight java %}
56. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
57. dataStyle.setWrapText(true);
58. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
{% highlight java %}
51. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
52. headerStyle.setFillForegroundColor(LAVENDER.index);
53. headerStyle.setFont(font);
54. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
{% highlight java %}
54. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
55. dataStyle.setWrapText(true);
56. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
{% highlight java %}
49. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
50. headerStyle.setFillForegroundColor(LAVENDER.index);
51. headerStyle.setFont(font);
52. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
{% highlight java %}
53. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
54. dataStyle.setWrapText(true);
55. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
{% highlight java %}
48. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
49. headerStyle.setFillForegroundColor(LAVENDER.index);
50. headerStyle.setFont(font);
51. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
{% highlight java %}
51. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
52. dataStyle.setWrapText(true);
53. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
{% highlight java %}
46. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
47. headerStyle.setFillForegroundColor(LAVENDER.index);
48. headerStyle.setFont(font);
49. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinnen.java](https://searchcode.com/codesearch/view/91974026/)
{% highlight java %}
52. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
53. dataStyle.setWrapText(true);
54. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinnen.java](https://searchcode.com/codesearch/view/91974026/)
{% highlight java %}
47. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
48. headerStyle.setFillForegroundColor(LAVENDER.index);
49. headerStyle.setFont(font);
50. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelFamilieZahlungenB.java](https://searchcode.com/codesearch/view/91974028/)
{% highlight java %}
52. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
53. dataStyle.setWrapText(true);
54. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelFamilieZahlungenB.java](https://searchcode.com/codesearch/view/91974028/)
{% highlight java %}
47. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
48. headerStyle.setFillForegroundColor(LAVENDER.index);
49. headerStyle.setFont(font);
50. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinZahlungenRD.java](https://searchcode.com/codesearch/view/91974030/)
{% highlight java %}
50. XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
51. dataStyle.setWrapText(true);
52. dataStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

### [ExcelBetreuerinZahlungenRD.java](https://searchcode.com/codesearch/view/91974030/)
{% highlight java %}
45. XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
46. headerStyle.setFillForegroundColor(LAVENDER.index);
47. headerStyle.setFont(font);
48. headerStyle.setAlignment(HorizontalAlignment.CENTER);
{% endhighlight %}

***

