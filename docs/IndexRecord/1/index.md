# IndexRecord @Cluster 1

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
{% highlight java %}
800. IndexRecord index = new IndexRecord();
801. index.setFirstRow(rows.getFirstRowNum());
802. index.setLastRowAdd1(rows.getLastRowNum()+1);
827.   index.addDbcell(offset + indexRecSize + sheetRecSize + dbCellOffset + rowBlockOffset + cellBlockOffset);
831. return index.serialize(offset, data);
{% endhighlight %}

***

