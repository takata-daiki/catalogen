# ExternSheetRecord @Cluster 1 (excelextractor, extractor, false)

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
> test that we get the same value as excel and , for 
{% highlight java %}
108. protected ExternSheetRecord externSheet= null;
1818.         for (int k = 0; k < externSheet.getNumOfREFStructures(); k++) {
1835.     short indexToSheet = externSheet.getREFRecordAt(num).getIndexToFirstSupBook();
1850.     if (externSheetNumber >= externSheet.getNumOfREFStructures())
1853.         return externSheet.getREFRecordAt(externSheetNumber).getIndexToFirstSupBook();
1872.     while (i < externSheet.getNumOfREFStructures() && !flag){
1873.         ExternSheetSubRecord record = externSheet.getREFRecordAt(i);
1898.     externSheet.addREFRecord(record);
1899.     externSheet.setNumOfREFStructures((short)(externSheet.getNumOfREFStructures() + 1));
1900.     result = (short)(externSheet.getNumOfREFStructures() - 1);
{% endhighlight %}

***

