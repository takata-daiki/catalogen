# BoundSheetRecord @Cluster 2 (case, retval, setsheetname)

***

### [Workbook.java](https://searchcode.com/codesearch/view/15642358/)
> set the cells type ( numeric , formula or string ) 
{% highlight java %}
1720. BoundSheetRecord retval = new BoundSheetRecord();
1725.         retval.setPositionOfBof(0x0);   // should be set later
1726.         retval.setOptionFlags(( short ) 0);
1727.         retval.setSheetnameLength(( byte ) 0x6);
1728.         retval.setCompressedUnicodeFlag(( byte ) 0);
1729.         retval.setSheetname("Sheet1");
1737.         retval.setSheetname("Sheet2");
1745.         retval.setSheetname("Sheet3");
{% endhighlight %}

***

