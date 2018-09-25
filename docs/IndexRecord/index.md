# IndexRecord

***

## [Cluster 1 (field_1_header_len, field_2_reserved, rec)](./1)
1 results
> serializes the record to an existing . @ param index the index of the formatting to return the properties to @ return the number of bytes 
{% highlight java %}
800. IndexRecord index = new IndexRecord();
801. index.setFirstRow(rows.getFirstRowNum());
802. index.setLastRowAdd1(rows.getLastRowNum()+1);
827.   index.addDbcell(offset + indexRecSize + sheetRecSize + dbCellOffset + rowBlockOffset + cellBlockOffset);
831. return index.serialize(offset, data);
{% endhighlight %}

***

