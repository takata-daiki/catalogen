# HSSFDataFormat

***

### [Cluster 1](./1)
{% highlight java %}
101. HSSFDataFormat format = workbook.createDataFormat();
102. style3.setDataFormat(format.getFormat("0.000"));
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
385. HSSFDataFormat format = workbook.createDataFormat();
386. cellStyle.setDataFormat(format
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
215. HSSFDataFormat df = workbook.createDataFormat();
219.   format = df.getFormat("####");
222.   format = df.getFormat("####." + zeros);
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
135. HSSFDataFormat format = workbook.createDataFormat();
136. short dateFormatCode = format.getFormat(DATE_FORMAT_AS_NUMBER_DBUNIT);
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
183. final HSSFDataFormat dateFormat = workbook.createDataFormat ();
185. dateCellStyle.setDataFormat ( dateFormat.getFormat ( "YYYY-MM-DD hh:mm:ss.000" ) );
{% endhighlight %}

***

### [Cluster 6](./6)
{% highlight java %}
119. private HSSFDataFormat m_dataFormat;
234.       cs.setDataFormat(m_dataFormat.getFormat("DD.MM.YYYY"));
239.       cs.setDataFormat(m_dataFormat.getFormat(format));
{% endhighlight %}

***

### [Cluster 7](./7)
{% highlight java %}
277. HSSFDataFormat format = book.createDataFormat();
304.     style.setDataFormat(format.getFormat(cellFormat));
{% endhighlight %}

***

### [Cluster 8](./8)
{% highlight java %}
144. private Object getCellValue(HSSFCell cell, HSSFDataFormat formatter) {
154.   String fmt = formatter.getFormat(dataFormat);
{% endhighlight %}

***

