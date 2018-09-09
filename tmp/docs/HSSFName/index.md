# HSSFName

***

### [Cluster 1](./1)
{% highlight java %}
102. HSSFName nm = shTemplate.getWorkbook().getNameAt(i);
104. AreaReference aref = new AreaReference(nm.getRefersToFormula());
107. band.setName(nm.getNameName());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
104. public static AreaReference getReferanceNameRange(HSSFName nameRange){
106.   return new AreaReference(nameRange.getRefersToFormula());
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
98. HSSFName nameRange =  wb.createName();
99. nameRange.setNameName(name);
100. nameRange.setRefersToFormula(refersToR1C1);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
143. HSSFName hssfName = workbook.createName();
144. hssfName.setNameName(name);
145. hssfName.setRefersToFormula(rng.toFixedAddress());
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
1251. HSSFName iName = wb.getNameAt(i);
1252. if((iName != null) && (iName.getNameName() == null)) return iName;
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
178. HSSFName iName = wb.getNameAt(i);
180.   String iNameName = iName.getNameName();
182.     String iShName = iName.getSheetName();
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
26. private HSSFName name;
34.     return name.getNameName();
38.     String sheetName = name.getSheetName();
39.     String formula = name.getRefersToFormula();
{% endhighlight %}

***

