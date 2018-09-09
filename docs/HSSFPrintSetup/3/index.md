# HSSFPrintSetup @Cluster 3

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

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/122565092/)
{% highlight java %}
137. HSSFPrintSetup printSetup = sheet0.getPrintSetup();
138. assertEquals( HSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
139. assertEquals( true, printSetup.getLandscape() );
140. assertEquals( 0.5, printSetup.getHeaderMargin(), 0.01 );
141. assertEquals( 0.5, printSetup.getFooterMargin(), 0.01 );
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

### [PageLayoutTest.java](https://searchcode.com/codesearch/view/64531687/)
{% highlight java %}
146. HSSFPrintSetup printSetup = sheet0.getPrintSetup();
147. assertEquals( HSSFPrintSetup.A4_PAPERSIZE,  printSetup.getPaperSize() );
148. assertEquals( true, printSetup.getLandscape() );
149. assertEquals( 0.5, printSetup.getHeaderMargin(), 0.01 );
150. assertEquals( 0.5, printSetup.getFooterMargin(), 0.01 );
{% endhighlight %}

***

