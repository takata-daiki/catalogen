# HSSFRow @Cluster 7

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

