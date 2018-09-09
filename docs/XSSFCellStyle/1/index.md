# XSSFCellStyle @Cluster 1

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

