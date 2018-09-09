# HSSFSheet @Cluster 18

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
42. private HSSFSheet sheet;
61.   int firstRow = sheet.getFirstRowNum();
62.   int lastRow = sheet.getLastRowNum();
64.     HSSFRow row = sheet.getRow(rowIndex);
89.     String oldName = sheet.getSheetName();
129.     int width = (sheet.getColumnWidth(col) / 256) * 6;
146.     for(Iterator<Row> it = sheet.rowIterator(); it.hasNext(); ) {
148.         sheet.removeRow(row);
153.     HSSFRow hssfRow = sheet.getRow(row);
169.         hssfRow = sheet.createRow(row);
179.     HSSFRow theRow = sheet.getRow(row);
211.   DataValidationConstraint constraint=sheet.getDataValidationHelper().createFormulaListConstraint(namedRange);      
226.   DataValidationConstraint constraint = sheet.getDataValidationHelper().createCustomConstraint(formula);
233.   sheet.addValidationData(dataValidation);
{% endhighlight %}

***

