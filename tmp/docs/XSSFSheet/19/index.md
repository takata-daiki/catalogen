# XSSFSheet @Cluster 19

***

### [SheetXSSFImplTest.java](https://searchcode.com/codesearch/view/72853788/)
{% highlight java %}
44. XSSFSheet sheet = wb.createSheet();
45. List<XSSFDataValidation> dataValidations = sheet.getDataValidations();  //<-- works
49. sheet.createRow(0).createCell(0);    
51. DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
55. sheet.addValidationData(validation);          
57. dataValidations = sheet.getDataValidations();  //<-- raised XmlValueOutOfRangeException  
{% endhighlight %}

***

