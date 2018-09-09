# HSSFRow

***

### [Cluster 1](./1)
{% highlight java %}
102. HSSFRow headerRow = sheet.createRow(0);
118.   headerCell = headerRow.createCell(i);            
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
59. HSSFRow row = sheet1.createRow(count); 
60. row.createCell(0).setCellValue(res.getString("Url")); 
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
52. HSSFRow row = sheet.getRow(rowIndex);
56.     int columnCount = row.getLastCellNum();
59.         HSSFCell cell = row.getCell(columnIndex);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
83. HSSFRow row = createRow(rowNumber);
84. cell = row.createCell(cellNumber);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
50. HSSFRow row;
67.     cNum = row.getLastCellNum();
70.       if ((cell = row.getCell((short) k)) != null) {
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
91. HSSFRow aRow = aSheet.getRow(rowNumOfSheet);
95.   if (null != aRow.getCell(selectCol[index])) {
96.     HSSFCell aCell = aRow.getCell(selectCol[index]);
{% endhighlight %}

***

### [Cluster 7](./7)
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

### [Cluster 8](./8)
{% highlight java %}
236. HSSFRow hssfRow = hssfSheet.getRow(setCellValue.getRow());
240. HSSFCell hssfCell = hssfRow.getCell(setCellValue.getCol());
242.     hssfCell = hssfRow.createCell(setCellValue.getCol());
249.         hssfRow.removeCell(hssfCell);
{% endhighlight %}

***

### [Cluster 9](./9)
{% highlight java %}
32. HSSFRow myRow = null;
57.         myCell = myRow.createCell(cellNum);
{% endhighlight %}

***

