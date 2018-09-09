# XSSFSheet @Cluster 12

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
37. private XSSFSheet sheet;
58.   int firstRow = sheet.getFirstRowNum();
59.   int lastRow = sheet.getLastRowNum();
61.     XSSFRow row = sheet.getRow(rowIndex);
77.     String oldName = sheet.getSheetName();
117.     int width = (sheet.getColumnWidth(col) / 256) * 6;
134.     for(Iterator<Row> it = sheet.rowIterator(); it.hasNext(); ) {
136.         sheet.removeRow(row);
141.     XSSFRow hssfRow = sheet.getRow(row);
157.         hssfRow = sheet.createRow(row);
167.     XSSFRow theRow = sheet.getRow(row);
208.   DataValidationConstraint constraint = sheet.getDataValidationHelper().createCustomConstraint(formula);
209.   DataValidation dataValidation = sheet.getDataValidationHelper().createValidation(constraint, addressList);
210.     sheet.addValidationData(dataValidation);
215.     DataValidationConstraint constraint = sheet.getDataValidationHelper().createFormulaListConstraint(namedRange);
216.     DataValidation dataValidation = sheet.getDataValidationHelper().createValidation(constraint, addressList);
241.   return sheet.getDataValidations();
245.   if (sheet.getCTWorksheet().getDataValidations() != null) {        
246.     for (int i=0;i<sheet.getCTWorksheet().getDataValidations().getCount();i++) {
248.         sheet.getCTWorksheet().getDataValidations().removeDataValidation(0);
{% endhighlight %}

***

