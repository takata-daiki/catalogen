# XSSFCell @Cluster 3

***

### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/)
{% highlight java %}
35. private XSSFCell theCell;
59.     return theCell.getRowIndex();
63.     return theCell.getColumnIndex();
67.     XSSFComment xssfComment = theCell.getCellComment();
78.     XSSFFont xssfFont = theCell.getCellStyle().getFont();
83.   XSSFFont xssfFont = theCell.getCellStyle().getFont();
93.     if (theCell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
96.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
97.         return Boolean.toString(theCell.getBooleanCellValue());
99.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_ERROR) {
102.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
103.         return theCell.getCellFormula();
105.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
106.         return Double.toString(theCell.getNumericCellValue());
108.     else if (theCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
109.         return theCell.getRichStringCellValue().getString();
116.         theCell.setCellValue(new XSSFRichTextString(value));
119.         theCell.setCellValue(Boolean.parseBoolean(value));
124.         theCell.setCellFormula(value);
127.         theCell.setCellValue(Double.parseDouble(value));
139.     XSSFCellStyle cellStyle = theCell.getCellStyle();        
142.         theCell.setCellStyle(cellStyle);
159.     XSSFCellStyle cellStyle = theCell.getCellStyle();
211.   XSSFCellStyle cellStyle = theCell.getCellStyle();
249.   theCell.setCellStyle(style);
275.       XSSFColor colour = theCell.getCellStyle().getFont().getXSSFColor();
316. return theCell.hashCode();
323.   return cell.theCell.equals(this.theCell);      
341. XSSFSheet sheet = theCell.getSheet();
{% endhighlight %}

***

