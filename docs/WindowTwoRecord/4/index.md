# WindowTwoRecord @Cluster 4 (rec, return, windowtwo)

***

### [Sheet.java](https://searchcode.com/codesearch/view/15642365/)
> test that we get the same value as excel and , for 
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

