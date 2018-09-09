# WindowTwoRecord

***

### [Cluster 1](./1)
{% highlight java %}
103.   protected WindowTwoRecord            windowTwo         =     null;
2044.     return (windowTwo==null) ? (short) 0 : windowTwo.getTopRow();
2051.       windowTwo.setTopRow(topRow);
2062.         windowTwo.setLeftCol(leftCol);
2068.         return (windowTwo==null) ? (short) 0 : windowTwo.getLeftCol();
2358.       windowTwo.setSelected(sel);
2460.       windowTwo.setFreezePanes(true);
2461.       windowTwo.setFreezePanesNoSplit(true);
2496.       windowTwo.setFreezePanes(false);
2497.       windowTwo.setFreezePanesNoSplit(false);
2514.                            rec.getLeftColumn(), (byte)rec.getActivePane(), windowTwo.getFreezePanes());      
2626.       windowTwo.setDisplayGridlines(show);
2634. return windowTwo.getDisplayGridlines();
2642.       windowTwo.setDisplayFormulas(show);
2650. return windowTwo.getDisplayFormulas();
2658.       windowTwo.setDisplayRowColHeadings(show);
2666.     return windowTwo.getDisplayRowColHeadings();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
519. WindowTwoRecord windowTwo = (WindowTwoRecord) sheet.getSheet().findFirstRecordBySid(WindowTwoRecord.sid);
520. windowTwo.setSelected(sheets.size() == 1);
521. windowTwo.setPaged(sheets.size() == 1);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
590. WindowTwoRecord rec = new WindowTwoRecord();
591. rec.field_1_options = field_1_options;
592. rec.field_2_top_row = field_2_top_row;
593. rec.field_3_left_col = field_3_left_col;
594. rec.field_4_header_color = field_4_header_color;
595. rec.field_5_page_break_zoom = field_5_page_break_zoom;
596. rec.field_6_normal_zoom = field_6_normal_zoom;
597. rec.field_7_reserved = field_7_reserved;
{% endhighlight %}

***

