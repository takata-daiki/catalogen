# XSSFWorkbook @Cluster 3

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
{% highlight java %}
35. private XSSFWorkbook hssfWorkbook;       
78.     hssfWorkbook.setSheetName(hssfWorkbook.getSheetIndex(sheet), name);
83.     return hssfWorkbook.isSheetHidden(hssfWorkbook.getSheetIndex(sheet));
87.     hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), b);
92.         hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), 2);
95.         hssfWorkbook.setSheetHidden(hssfWorkbook.getSheetIndex(sheet), false);
101. return hssfWorkbook.isSheetVeryHidden(hssfWorkbook.getSheetIndex(sheet));
122.     return hssfWorkbook.getSheetName(hssfWorkbook.getSheetIndex(sheet));
{% endhighlight %}

***

