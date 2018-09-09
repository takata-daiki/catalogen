# HSSFPrintSetup @Cluster 2

***

### [ExcelUtils.java](https://searchcode.com/codesearch/view/60212069/)
{% highlight java %}
1279. HSSFPrintSetup ps = sh.getPrintSetup();
1280. float[]sizePage = PrintPagesFormat.get(ps.getPaperSize());
1283. if (ps.getLandscape()) baseSizePage = sizePage[0];
1285. float scale = ps.getScale() / 100;
1286. double l = baseSizePage -(((ps.getFooterMargin()+ps.getHeaderMargin())*Factotr_MM_Inches)+50);
{% endhighlight %}

***

### [JUniPrint.java](https://searchcode.com/codesearch/view/60212057/)
{% highlight java %}
1524. HSSFPrintSetup ps = sh.getPrintSetup();
1525. float[]sizePage = ExcelUtils.PrintPagesFormat.get(ps.getPaperSize());
1528. if (ps.getLandscape()) baseSizePage = sizePage[0];
1530. float scale = ps.getScale() / 100;
1531. double len = baseSizePage -(((ps.getFooterMargin()+ps.getHeaderMargin())*ExcelUtils.Factotr_MM_Inches)+50);
{% endhighlight %}

***

