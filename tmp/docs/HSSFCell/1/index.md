# HSSFCell @Cluster 1

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
124. HSSFCell source = horzTitle.createCell(0);
125. source.setCellStyle(headingStyle);
126. source.setCellValue(new HSSFRichTextString("Resource"));
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
127. HSSFCell restriction = horzTitle.createCell(1);
128. restriction.setCellStyle(headingStyle);
129. restriction.setCellValue(new HSSFRichTextString("Restriction"));
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
142. HSSFCell c1 = row.createCell(0);
143. c1.setCellStyle(rowStyle);
144. c1.setCellValue(new HSSFRichTextString(p[0]));
{% endhighlight %}

***

### [ContentPermissionReportUtil.java](https://searchcode.com/codesearch/view/43507489/)
{% highlight java %}
145. HSSFCell c2 = row.createCell(1);
146. c2.setCellStyle(rowStyle);
147. c2.setCellValue(new HSSFRichTextString(p[1]));
{% endhighlight %}

***

### [PLReportPOIProducer.java](https://searchcode.com/codesearch/view/43507470/)
{% highlight java %}
103. HSSFCell headerCell = null;
119.     headerCell.setCellStyle(cellStyleHeader);
120.     headerCell.setCellValue(new HSSFRichTextString(colName));
{% endhighlight %}

***

### [ExcelFileOut.java](https://searchcode.com/codesearch/view/35739735/)
{% highlight java %}
33. HSSFCell myCell = null;
59.           myCell.setCellValue(headers[cellNum]);
61.           myCell.setCellValue(excelData[rowNum-1][cellNum]);  
{% endhighlight %}

***

### [WorkbookHSSFImpl.java](https://searchcode.com/codesearch/view/72854626/)
{% highlight java %}
240. HSSFCell hssfCell = hssfRow.getCell(setCellValue.getCol());
246.         hssfCell.setCellValue(new HSSFRichTextString(setCellValue.getNewValue().toString()));
{% endhighlight %}

***

