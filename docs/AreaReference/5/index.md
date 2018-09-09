# AreaReference @Cluster 5

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1233. public static Short getRowHeight(HSSFSheet sh, AreaReference area){
1235.   for(int row = area.getFirstCell().getRow(); row <= area.getLastCell().getRow(); row++){
1237.     if (row == area.getFirstCell().getRow()) height = hSSFRow.getHeight();  
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
321. public static void clear(HSSFSheet sh, AreaReference aRef){
322.   for(int row = aRef.getFirstCell().getRow(); row <= aRef.getLastCell().getRow(); row++){
325.       for(int col = aRef.getFirstCell().getCol(); col <= aRef.getLastCell().getCol(); col++){
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
768.   AreaReference areaRef, Object valueSearch, Object valueReplace, byte lookIn, XlLookAt lookAt, XlSearchDirection searchDirection, boolean isFirst, boolean isReplace){
793. int realFirstRow  = areaRef.getFirstCell().getRow() > sh.getFirstRowNum() ?areaRef.getFirstCell().getRow():sh.getFirstRowNum();
794. int realLastRow  = areaRef.getLastCell().getRow() < sh.getLastRowNum() ?areaRef.getLastCell().getRow():sh.getLastRowNum();
795. int realFirstCol  = areaRef.getFirstCell().getCol() > sh.getLeftCol() ?areaRef.getFirstCell().getCol():sh.getLeftCol();
802.     for(int iCol = realFirstCol; iCol <= areaRef.getLastCell().getCol(); iCol++){
803.       realCol = searchDirection == XlSearchDirection.xlNext ? iCol: areaRef.getLastCell().getCol()+(realFirstCol-iCol);
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1062. public static ExcelBuffer copy (HSSFSheet sh, AreaReference areaRef){
1064.   int offsetRow = areaRef.getFirstCell().getRow();
1065.   int offsetCol = areaRef.getFirstCell().getCol();
1066.   CellReference[] celRefs = areaRef.getAllReferencedCells();
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1130. public static void paste (HSSFSheet sh, AreaReference areaRef,  ExcelBuffer buffer, byte typePaste){
1137.   int pasteRow1 = areaRef.getFirstCell().getRow();
1138.   int pasteCol1 = areaRef.getFirstCell().getCol();
1139.   int pasteRow2 = areaRef.getLastCell().getRow();
1140.   int pasteCol2 = areaRef.getLastCell().getCol();
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1221. public static void hiddenRows(HSSFSheet sh, AreaReference area, boolean hide){
1223.       for(int row = area.getFirstCell().getRow(); row <= area.getLastCell().getRow(); row++)
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1228. public static void setRowHeight(HSSFSheet sh, AreaReference area, short height){
1229.   for(int row = area.getFirstCell().getRow(); row <= area.getLastCell().getRow(); row++)
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
348. AreaReference ref = ExcelUtils.getReferanceNameRange(nameCUsing);
349. String shName = ref.getFirstCell().getSheetName();
350. int c = ref.getFirstCell().getCol();
351. int r = ref.getFirstCell().getRow();
355. shName = ref.getFirstCell().getSheetName();
356. c = ref.getFirstCell().getCol();
357. r = ref.getFirstCell().getRow();
368. HSSFSheet ash = wb.getSheet(ref.getFirstCell().getSheetName());
369. int begRow = ref.getFirstCell().getRow();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1325. for(AreaReference area:hideOneRow){
1327.       area.getFirstCell().getRow(), area.getLastCell().getRow(),
1328.       area.getFirstCell().getCol(), area.getLastCell().getCol()), crB);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1354. for(AreaReference area:hideFewRow){
1356.       area.getFirstCell().getRow(), area.getLastCell().getRow(),
1357.       area.getFirstCell().getCol(), area.getLastCell().getCol());
{% endhighlight %}

***

