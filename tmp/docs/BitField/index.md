# BitField

***

## [Cluster 1](./1)
33 results
> test that we get the same value as excel and , for 
{% highlight java %}
78. /**/private static final BitField fCalc = new BitField(0x40000000);
2660.     field_1_grpfChp = fCalc.setBoolean(field_1_grpfChp, value);
2670.     return fCalc.isSet(field_1_grpfChp);
{% endhighlight %}

***

## [Cluster 2](./2)
4 results
> test that we get the same value as excel and , for 
{% highlight java %}
42. /**/private static BitField unused1 = new BitField(0x80);
1580.     field_1_formatFlags = (byte)unused1.setBoolean(field_1_formatFlags, value);
1590.     return unused1.isSet(field_1_formatFlags);
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
40. /**/private static BitField grfSupression = new BitField(0x18);
1540.     field_1_formatFlags = (byte)grfSupression.setValue(field_1_formatFlags, value);
1550.     return ( byte )grfSupression.getValue(field_1_formatFlags);
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
41. /**/private static BitField fpc = new BitField(0x60);
1560.     field_1_formatFlags = (byte)fpc.setValue(field_1_formatFlags, value);
1570.     return ( byte )fpc.getValue(field_1_formatFlags);
{% endhighlight %}

***

## [Cluster 5](./5)
3 results
> test that we get the same value as excel and , for 
{% highlight java %}
45. /**/private static final BitField icoFore = new BitField(0x001F);
141.     field_1_value = (short)icoFore.setValue(field_1_value, value);
151.     return ( byte )icoFore.getValue(field_1_value);
{% endhighlight %}

***

## [Cluster 6](./6)
12 results
> test that we get the same value as excel and , for 
{% highlight java %}
66. private BitField          displayRowColHeadings   = BitFieldFactory.getInstance(0x04);
170.     field_1_options = displayRowColHeadings.setShortBoolean(field_1_options, headings);
363.     return displayRowColHeadings.isSet(field_1_options);
{% endhighlight %}

***

## [Cluster 7](./7)
5 results
> test that we get the same value as excel and , for 
{% highlight java %}
68. private BitField          optiChoose   = BitFieldFactory.getInstance(0x04);
105.     return optiChoose.isSet(getOptions());
216.   if( optiChoose.isSet(field_1_options)) {
{% endhighlight %}

***

## [Cluster 8](./8)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
69. private BitField          optGoto      = BitFieldFactory.getInstance(0x08);
111.     return optGoto.isSet(getOptions());
132. field_1_options=optGoto.setByteBoolean(field_1_options, isGoto);
191.     } else if (optGoto.isSet(field_1_options)) {
219.   if(optGoto.isSet(field_1_options)) {
{% endhighlight %}

***

## [Cluster 9](./9)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
67. private BitField          optiIf       = BitFieldFactory.getInstance(0x02);
100.     return optiIf.isSet(getOptions());
124.     field_1_options=optiIf.setByteBoolean(field_1_options,bif);
189.     } else if (optiIf.isSet(field_1_options)) {
213.   if(optiIf.isSet(field_1_options)) {
{% endhighlight %}

***

## [Cluster 10](./10)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
75. static final private BitField rowsumsbelow        = BitFieldFactory.getInstance(
167.     field_1_wsbool = rowsumsbelow.setByteBoolean(field_1_wsbool, below);
276.     return rowsumsbelow.isSet(field_1_wsbool);
{% endhighlight %}

***

## [Cluster 11](./11)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
77. static final private BitField rowsumsright        = BitFieldFactory.getInstance(
177.     field_1_wsbool = rowsumsright.setByteBoolean(field_1_wsbool, right);
286.     return rowsumsright.isSet(field_1_wsbool);
{% endhighlight %}

***

## [Cluster 12](./12)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
67. static final private BitField autobreaks          =
147.     field_1_wsbool = autobreaks.setByteBoolean(field_1_wsbool, ab);
256.     return autobreaks.isSet(field_1_wsbool);
{% endhighlight %}

***

## [Cluster 13](./13)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
71. static final private BitField dialog              =
157.     field_1_wsbool = dialog.setByteBoolean(field_1_wsbool, isDialog);
266.     return dialog.isSet(field_1_wsbool);
{% endhighlight %}

***

## [Cluster 14](./14)
24 results
> test that we get the same value as excel and , for 
{% highlight java %}
58. /**/private static BitField fBackup = new BitField(0x01);
1800.     field_6_docinfo1 = (byte)fBackup.setBoolean(field_6_docinfo1, value);
1810.     return fBackup.isSet(field_6_docinfo1);
{% endhighlight %}

***

## [Cluster 15](./15)
20 results
> test that we get the same value as excel and , for 
{% highlight java %}
128. /**/private static BitField fNoTabForInd = new BitField(0x00000001);
2660.     field_33_docinfo4 = (int)fNoTabForInd.setBoolean(field_33_docinfo4, value);
2670.     return fNoTabForInd.isSet(field_33_docinfo4);
{% endhighlight %}

***

## [Cluster 16](./16)
45 results
> test that we get the same value as excel and , for 
{% highlight java %}
83. /**/private static BitField oldfSupressTopSpacing = new BitField(0x0080);
2260.     field_8_docinfo3 = (short)oldfSupressTopSpacing.setBoolean(field_8_docinfo3, value);
2270.     return oldfSupressTopSpacing.isSet(field_8_docinfo3);
{% endhighlight %}

***

## [Cluster 17](./17)
18 results
> test that we get the same value as excel and , for 
{% highlight java %}
57. /**/private static final BitField iRes = new BitField(0x007C);
275.     field_2_bits = (short)iRes.setValue(field_2_bits, value);
285.     return ( byte )iRes.getValue(field_2_bits);
{% endhighlight %}

***

## [Cluster 18](./18)
8 results
> this comment could not be generated...
{% highlight java %}
155. /**/private static final BitField fCellFitText = new BitField(0x40);
2920.     field_30_copt = (byte)fCellFitText.setBoolean(field_30_copt, value);
2930.     return fCellFitText.isSet(field_30_copt);
{% endhighlight %}

***

## [Cluster 19](./19)
69 results
> test that we get the same value as excel and , for 
{% highlight java %}
149. static final private BitField _shrink_to_fit                  =
507.         _shrink_to_fit.setShortBoolean(field_5_indention_options, shrink);
1241.     return _shrink_to_fit.isSet(field_5_indention_options);
{% endhighlight %}

***

## [Cluster 20](./20)
9 results
> test that we get the same value as excel and , for 
{% highlight java %}
80. private BitField          calcOnLoad = BitFieldFactory.getInstance(0x0002);
516.         buffer.append("      .calcOnLoad         = ").append(calcOnLoad.isSet(getOptions()))
{% endhighlight %}

***

