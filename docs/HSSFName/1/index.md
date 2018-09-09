# HSSFName @Cluster 1

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
67. private HSSFName nameDataBeg;
1633.     AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
{% endhighlight %}

***

### [JUniPrintReportsEngine.java](https://searchcode.com/codesearch/view/60336976/)
{% highlight java %}
93. HSSFName paramCellName = ExcelUtils.getNamedRange(wb, param.getKey());
95.   HSSFSheet paramCellSheet = wb.getSheet(paramCellName.getSheetName());
96.   AreaReference paramCellAreaRef = new AreaReference(paramCellName.getRefersToFormula());
{% endhighlight %}

***

### [JUniPrintReportsEngine.java](https://searchcode.com/codesearch/view/60336976/)
{% highlight java %}
105. HSSFName nameDataBeg = ExcelUtils.getNamedRange(wb, dataBegFieldName);
107. String nameShData = nameDataBeg.getSheetName();
110. AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
{% endhighlight %}

***

### [JUniPrintTest.java](https://searchcode.com/codesearch/view/60212055/)
{% highlight java %}
38. HSSFName nameDataBeg = ExcelUtils.getNamedRange(wb, "DataBeg");
40. String nameShData = nameDataBeg.getSheetName();
43. AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
{% endhighlight %}

***

### [JUniPrintTest.java](https://searchcode.com/codesearch/view/60212055/)
{% highlight java %}
96. HSSFName nameDataBeg = ExcelUtils.getNamedRange(wb, "DataBeg");
98. String nameShData = nameDataBeg.getSheetName();
101. AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
{% endhighlight %}

***

### [RangeConvertor.java](https://searchcode.com/codesearch/view/8520531/)
{% highlight java %}
46. HSSFName aNamedRage = wb.getNameAt(namedRangeIdx);
49. AreaReference aref = new AreaReference(aNamedRage.getReference());
53. Range redRange = new Range(aNamedRage.getNameName());
{% endhighlight %}

***

### [RangeConvertor.java](https://searchcode.com/codesearch/view/8520531/)
{% highlight java %}
101. HSSFName aNamedCell = wb.getNameAt(namedCellIdx);
104. AreaReference aref = new AreaReference(aNamedCell.getReference());
119.     String cellHandle = Range.getUniqueCellName(aNamedCell
{% endhighlight %}

***

### [ReportSheetImpl.java](https://searchcode.com/codesearch/view/128473368/)
{% highlight java %}
102. HSSFName nm = shTemplate.getWorkbook().getNameAt(i);
104. AreaReference aref = new AreaReference(nm.getRefersToFormula());
107. band.setName(nm.getNameName());
{% endhighlight %}

***

