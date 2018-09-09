# ExtendedFormatRecord

***

### [Cluster 1](./1)
{% highlight java %}
64. private ExtendedFormatRecord format                     = null;
283.     format.setFormatIndex(fmt);
293.     return format.getFormatIndex();
304.     return format.getFormat(getDataFormat());
316.     format.setIndentNotParentFont(true);
318.     format.setFontIndex(fontindex);
323.     return format.getFontIndex();
333.     format.setIndentNotParentCellOptions(true);
334.     format.setHidden(hidden);
344.     return format.isHidden();
355.     format.setLocked(locked);
365.     return format.isLocked();
382.     format.setIndentNotParentAlignment(true);
383.     format.setAlignment(align);
400.     return format.getAlignment();
433.     format.setWrapText(wrapped);
443.     return format.getWrapText();
457.     format.setVerticalAlignment(align);
471.     return format.getVerticalAlignment();
489.     format.setRotation(rotation);
499.   short rotation = format.getRotation();
513.     format.setIndent(indent);
523.     return format.getIndent();
547.     format.setIndentNotParentBorder(true);
548.     format.setBorderLeft(border);
572.     return format.getBorderLeft();
597.     format.setBorderRight(border);
621.     return format.getBorderRight();
646.     format.setBorderTop(border);
670.     return format.getBorderTop();
695.     format.setBorderBottom(border);
719.     return format.getBorderBottom();
729.     format.setLeftBorderPaletteIdx(color);
739.     return format.getLeftBorderPaletteIdx();
749.     format.setRightBorderPaletteIdx(color);
759.     return format.getRightBorderPaletteIdx();
769.     format.setTopBorderPaletteIdx(color);
779.     return format.getTopBorderPaletteIdx();
789.     format.setBottomBorderPaletteIdx(color);
799.     return format.getBottomBorderPaletteIdx();
828.     format.setAdtlFillPattern(fp);
838.     return format.getAdtlFillPattern();
852.   if (format.getFillForeground() == loci.poi.hssf.util.HSSFColor.AUTOMATIC.index) {
856.     if (format.getFillBackground() != (loci.poi.hssf.util.HSSFColor.AUTOMATIC.index+1))
858.   } else if (format.getFillBackground() == loci.poi.hssf.util.HSSFColor.AUTOMATIC.index+1)
860.     if (format.getFillForeground() != loci.poi.hssf.util.HSSFColor.AUTOMATIC.index)
892.     format.setFillBackground(bg);
903.   short result = format.getFillBackground();
919.     format.setFillForeground(bg);
930.     return format.getFillForeground();
{% endhighlight %}

***

