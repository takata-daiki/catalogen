# AreaReference @Cluster 4

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1315. AreaReference ref = range(wb, nameRange, mapWbNames, sheetNames);
1316. if (ref.isSingleCell()){
1317.   String shName = ref.getFirstCell().getSheetName();
1318.   return  getCell(wb.getSheet(shName), ref.getFirstCell().getRow(), ref.getFirstCell().getCol());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
658. public static void autoFilter(HSSFSheet sh, AreaReference areaRef, Object filterValue){
659.   if (areaRef.getFirstCell().getCol() != areaRef.getLastCell().getCol()) return;
660.   for(CellReference cellRef:areaRef.getAllReferencedCells()){
{% endhighlight %}

***

