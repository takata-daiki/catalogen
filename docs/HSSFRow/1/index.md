# HSSFRow @Cluster 1

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
201. HSSFRow row = sheet.getRow(0);
202. HSSFCell cell = row.getCell((short) 0);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
153. HSSFRow hssfRow = sheet.getRow(row);
157. HSSFCell hssfCell = hssfRow.getCell(col);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
131. HSSFRow row = sheet.getRow(0);
132. HSSFCell cell = row.getCell((short) 0);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
179. HSSFRow theRow = sheet.getRow(row);
181.     HSSFCell theCell = theRow.getCell(col);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
166. HSSFRow row = sheet.getRow(0);
167. HSSFCell cell = row.getCell((short) 0);
{% endhighlight %}

***

### [ExcelViewTests.java](https://searchcode.com/codesearch/view/72414056/)
{% highlight java %}
96. HSSFRow row = sheet.getRow(2);
97. HSSFCell cell = row.getCell((short) 4);
{% endhighlight %}

***

### [ExcelIdentifier.java](https://searchcode.com/codesearch/view/52992680/)
{% highlight java %}
52. HSSFRow row = sheet.getRow(rowIndex);
56.     int columnCount = row.getLastCellNum();
59.         HSSFCell cell = row.getCell(columnIndex);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
167. HSSFRow hssfRow = sheet.getRow(row);
171. HSSFCell cell = hssfRow.getCell(col);
173.     cell = hssfRow.createCell(col);
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
{% highlight java %}
91. HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
95.   if (null != aRow.getCell(selectCol[index])) {
96.     HSSFCell aCell = aRow.getCell(selectCol[index]);
{% endhighlight %}

***

### [ExcelHandler.java](https://searchcode.com/codesearch/view/71586384/)
{% highlight java %}
161. HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
163. for (int index = 0; index <= aRow.getLastCellNum(); index++) {
164.   if (null != aRow.getCell(index)) {
165.     HSSFCell aCell = aRow.getCell(index);
{% endhighlight %}

***

### [WorkbookHSSFImpl.java](https://searchcode.com/codesearch/view/72854626/)
{% highlight java %}
236. HSSFRow hssfRow = hssfSheet.getRow(setCellValue.getRow());
240. HSSFCell hssfCell = hssfRow.getCell(setCellValue.getCol());
242.     hssfCell = hssfRow.createCell(setCellValue.getCol());
249.         hssfRow.removeCell(hssfCell);
{% endhighlight %}

***

### [SheetHSSFImpl.java](https://searchcode.com/codesearch/view/72854680/)
{% highlight java %}
64. HSSFRow row = sheet.getRow(rowIndex);
66.   int firstCell = row.getFirstCellNum();
67.     int lastCell = row.getLastCellNum();
69.       HSSFCell cell = row.getCell(cellIndex);
{% endhighlight %}

***

### [UpLoadFileWindow.java](https://searchcode.com/codesearch/view/42988393/)
{% highlight java %}
190. HSSFRow row = st.getRow(i);
191. if(row != null && row.getCell(0) != null && !"".equals(row.getCell(0).getStringCellValue())){
192.   cus.setName(getStringValue(row.getCell(0)));            
195.   if(row.getCell(2) != null){
196.     String strIstry = getStringValue(row.getCell(2));
204.   if(row.getCell(11) != null){
205.     cus.setCategory(CusCategory.valueOf(getStringValue(row.getCell(11))));
208.   if(row.getCell(12) != null){
209.     String strArea = getStringValue(row.getCell(12));
218.   if(row.getCell(13) != null){
219.     String strCusType = getStringValue(row.getCell(13));
229.   if(row.getCell(1) != null && !"".equals(row.getCell(1).getStringCellValue())){
230.     cus.setScale(Integer.valueOf(row.getCell(1).getStringCellValue()));
232.   cus.setPhone(getStringValue(row.getCell(3)));
233.   if(row.getCell(4) != null){
234.     cus.setUseSoft(row.getCell(4).getBooleanCellValue());            
236.   cus.setUrl(getStringValue(row.getCell(5)));
237.   cus.setAddress(getStringValue(row.getCell(6)));
238.   cus.setRemark(getStringValue(row.getCell(7)));
239.   if(row.getCell(8) != null){
240.     cus.setNextContactDate(getDateValue(row.getCell(8)));            
244.   if(row.getCell(10) != null){
245.     cus.setLastTrackDate(getDateValue(row.getCell(10)));            
247.   if(row.getCell(14) != null || row.getCell(15) != null || row.getCell(16) != null){
249.     if(row.getCell(14) != null){
250.       track.setTtime(getDateValue(row.getCell(14)));
252.     track.setRemark(getStringValue(row.getCell(15)));
253.     track.setNextPlan(getStringValue(row.getCell(16)));
256.   if(row.getCell(17) != null){
257.     Contact contact = new Contact(getStringValue(row.getCell(17)));
258.     contact.setEmail(getStringValue(row.getCell(18)));
259.     contact.setQq(getStringValue(row.getCell(19)));
260.     contact.setPhone(getStringValue(row.getCell(20)));
261.     contact.setTel(getStringValue(row.getCell(21)));
{% endhighlight %}

***

