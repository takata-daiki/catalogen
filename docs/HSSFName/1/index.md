# HSSFName @Cluster 1 (iname, namedatabeg, string)

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> test that we get the same value as excel and , for 
{% highlight java %}
67. private HSSFName nameDataBeg;
1633.     AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> returns the instance of a string with the of the given type , or null if none is the same style does not exist . 
{% highlight java %}
104. public static AreaReference getReferanceNameRange(HSSFName nameRange){
106.   return new AreaReference(nameRange.getRefersToFormula());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> sets the 
{% highlight java %}
98. HSSFName nameRange =  wb.createName();
99. nameRange.setNameName(name);
100. nameRange.setRefersToFormula(refersToR1C1);
{% endhighlight %}

***

### [JUniPrintReportsEngine.java](https://searchcode.com/codesearch/view/60336976/)
> sets the 
{% highlight java %}
93. HSSFName paramCellName = ExcelUtils.getNamedRange(wb, param.getKey());
95.   HSSFSheet paramCellSheet = wb.getSheet(paramCellName.getSheetName());
96.   AreaReference paramCellAreaRef = new AreaReference(paramCellName.getRefersToFormula());
{% endhighlight %}

***

### [ReportSheetImpl.java](https://searchcode.com/codesearch/view/128473368/)
> sets the a number of the style of the that was a copy of the style table . 
{% highlight java %}
102. HSSFName nm = shTemplate.getWorkbook().getNameAt(i);
104. AreaReference aref = new AreaReference(nm.getRefersToFormula());
107. band.setName(nm.getNameName());
{% endhighlight %}

***

### [RangeConvertor.java](https://searchcode.com/codesearch/view/8520531/)
> sets the 
{% highlight java %}
46. HSSFName aNamedRage = wb.getNameAt(namedRangeIdx);
49. AreaReference aref = new AreaReference(aNamedRage.getReference());
53. Range redRange = new Range(aNamedRage.getNameName());
{% endhighlight %}

***

### [JUniPrintReportsEngine.java](https://searchcode.com/codesearch/view/60336976/)
> sets the 
{% highlight java %}
105. HSSFName nameDataBeg = ExcelUtils.getNamedRange(wb, dataBegFieldName);
107. String nameShData = nameDataBeg.getSheetName();
110. AreaReference areaDataBeg = new AreaReference(nameDataBeg.getRefersToFormula());
{% endhighlight %}

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> gets the value of the given paragraph textprop , add if required @ param propname the name of the paragraph 
{% highlight java %}
178. HSSFName iName = wb.getNameAt(i);
180.   String iNameName = iName.getNameName();
182.     String iShName = iName.getSheetName();
{% endhighlight %}

***

### [NamedRangeHSSFImpl.java](https://searchcode.com/codesearch/view/72854613/)
> test that we get the same value as excel and , for 
{% highlight java %}
26. private HSSFName name;
34.     return name.getNameName();
38.     String sheetName = name.getSheetName();
39.     String formula = name.getRefersToFormula();
{% endhighlight %}

***

