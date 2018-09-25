# HSSFRichTextString @Cluster 1 (append, case, str)

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
> returns the name of this sheet @ return the name of this sheet 
{% highlight java %}
372. HSSFRichTextString rich = cell.getRichStringCellValue();
373. return (rich==null)? null: rich.getString();
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
> sets whether the rowcolheadings are shown in a viewer @ param show whether to show rowcolheadings or not 
{% highlight java %}
1691. HSSFRichTextString rich = cell.getRichStringCellValue();
1692. if (rich != null) value = Integer.parseInt(rich.getString());
{% endhighlight %}

***

### [ExcelRowMapper.java](https://searchcode.com/codesearch/view/50611227/)
> set the contents of this shape to be a copy of the source shape . < br > the 2 data format are shape < p > 
{% highlight java %}
180. HSSFRichTextString str = ((HSSFCell) cell).getRichStringCellValue();
181. if (str != null && str.length() > 0) {
182.     text.append(str.toString());
{% endhighlight %}

***

