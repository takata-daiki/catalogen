# HSSFPrintSetup

***

### [Cluster 1](./1)
{% highlight java %}
345. HSSFPrintSetup ps = sheet.getPrintSetup();
346. ps.setFitWidth((short)1);
347. ps.setNoColor(true);
348. ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
349. ps.setLandscape(false);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
1524. HSSFPrintSetup ps = sh.getPrintSetup();
1525. float[]sizePage = ExcelUtils.PrintPagesFormat.get(ps.getPaperSize());
1528. if (ps.getLandscape()) baseSizePage = sizePage[0];
1530. float scale = ps.getScale() / 100;
1531. double len = baseSizePage -(((ps.getFooterMargin()+ps.getHeaderMargin())*ExcelUtils.Factotr_MM_Inches)+50);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
79. HSSFPrintSetup printSetup = sheet0.getPrintSetup();
80. assertEquals( HSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
81. assertEquals( true, printSetup.getLandscape() );
82. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
83. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

