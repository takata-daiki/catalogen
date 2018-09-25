# HSSFPalette @Cluster 2 (getcolor, result, rgbbyte)

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/64530867/)
> sets the cell value using object type information . @ param the cell to set the formula to set the cell to . 
{% highlight java %}
141. HSSFPalette palette = workbook.getCustomPalette();
143. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
147.     palette.setColorAtIndex(paletteIndex, rgbByte[0], rgbByte[1], rgbByte[2]);
150.     result = palette.findSimilarColor(rgbByte[0], rgbByte[1], rgbByte[2]);
{% endhighlight %}

***

### [StyleManagerHUtils.java](https://searchcode.com/codesearch/view/122565152/)
> verifies that this if one column is a not at the specified index and a to it not in the table [ < p > works on a is the index of the last row of a a 1 with the a column that is not in other of ( the same sheet ) . @ param row the row to @ since 3 . 1 4 beta 1 
{% highlight java %}
128. HSSFPalette palette = workbook.getCustomPalette();
130. HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
134.     palette.setColorAtIndex(paletteIndex, rgbByte[0], rgbByte[1], rgbByte[2]);
137.     result = palette.findSimilarColor(rgbByte[0], rgbByte[1], rgbByte[2]);
{% endhighlight %}

***

