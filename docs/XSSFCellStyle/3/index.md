# XSSFCellStyle @Cluster 3

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

