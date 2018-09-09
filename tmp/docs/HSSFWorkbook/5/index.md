# HSSFWorkbook @Cluster 5

***

### [ExcelFileOut.java](https://searchcode.com/codesearch/view/35739735/)
{% highlight java %}
30. HSSFWorkbook myWorkBook;
40.     mySheet = myWorkBook.getSheetAt(0);
47.     myWorkBook.getSheetAt(1).getRow(1).createCell(1).setCellValue(TestHarness.TEST_PERIOD);
50.     mySheet = myWorkBook.createSheet();
67.     myWorkBook.write(out);
{% endhighlight %}

***

