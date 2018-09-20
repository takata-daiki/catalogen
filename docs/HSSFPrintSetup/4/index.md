# HSSFPrintSetup @Cluster 4

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

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/64531687/)
{% highlight java %}
88. HSSFPrintSetup printSetup = sheet0.getPrintSetup();
89. assertEquals( HSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
90. assertEquals( true, printSetup.getLandscape() );
91. assertEquals( 1.0 / 2.54, printSetup.getHeaderMargin(), 0.01 );
92. assertEquals( 1.0 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
{% highlight java %}
137. HSSFPrintSetup printSetup = sheet0.getPrintSetup();
138. assertEquals( HSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
139. assertEquals( true, printSetup.getLandscape() );
140. assertEquals( 0.5, printSetup.getHeaderMargin(), 0.01 );
141. assertEquals( 0.5, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
{% highlight java %}
79. HSSFPrintSetup printSetup = sheet0.getPrintSetup();
80. assertEquals( HSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
81. assertEquals( true, printSetup.getLandscape() );
82. assertEquals( 0.7 / 2.54, printSetup.getHeaderMargin(), 0.01 );
83. assertEquals( 0.7 / 2.54, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/64531687/)
{% highlight java %}
146. HSSFPrintSetup printSetup = sheet0.getPrintSetup();
147. assertEquals( HSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
148. assertEquals( true, printSetup.getLandscape() );
149. assertEquals( 0.5, printSetup.getHeaderMargin(), 0.01 );
150. assertEquals( 0.5, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

