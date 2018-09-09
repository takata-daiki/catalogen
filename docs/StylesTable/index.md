# StylesTable

***

### [Cluster 1](./1)
{% highlight java %}
46. StylesTable stylesSource = wb.getStylesSource();
47. for (int i=0; i<stylesSource.getNumCellStyles(); i++)
48.   if (stylesSource.getStyleAt(i).equals(cellStyle))
56.   XSSFCellFill fill = stylesSource.getFillAt(cellStyle.getIndex()+1);
{% endhighlight %}

***

