# BitField

***

## [Cluster 1](./1)
4 results
> code comments is here.
{% highlight java %}
37. /**/private static BitField fFacingPages = new BitField(0x01);
1480.     field_1_formatFlags = (byte)fFacingPages.setBoolean(field_1_formatFlags, value);
1490.     return fFacingPages.isSet(field_1_formatFlags);
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
40. /**/private static BitField grfSupression = new BitField(0x18);
1540.     field_1_formatFlags = (byte)grfSupression.setValue(field_1_formatFlags, value);
1550.     return ( byte )grfSupression.getValue(field_1_formatFlags);
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
41. /**/private static BitField fpc = new BitField(0x60);
1560.     field_1_formatFlags = (byte)fpc.setValue(field_1_formatFlags, value);
1570.     return ( byte )fpc.getValue(field_1_formatFlags);
{% endhighlight %}

***

## [Cluster 4](./4)
3 results
> code comments is here.
{% highlight java %}
45. /**/private static final BitField icoFore = new BitField(0x001F);
141.     field_1_value = (short)icoFore.setValue(field_1_value, value);
151.     return ( byte )icoFore.getValue(field_1_value);
{% endhighlight %}

***

## [Cluster 5](./5)
12 results
> code comments is here.
{% highlight java %}
64. private BitField          displayFormulas         = BitFieldFactory.getInstance(0x01);
150.     field_1_options = displayFormulas.setShortBoolean(field_1_options, formulas);
343.     return displayFormulas.isSet(field_1_options);
{% endhighlight %}

***

## [Cluster 6](./6)
5 results
> code comments is here.
{% highlight java %}
66. private BitField          semiVolatile = BitFieldFactory.getInstance(0x01);
95.     return semiVolatile.isSet(getOptions());
210.   if(semiVolatile.isSet(field_1_options)) {
{% endhighlight %}

***

## [Cluster 7](./7)
1 results
> code comments is here.
{% highlight java %}
69. private BitField          optGoto      = BitFieldFactory.getInstance(0x08);
111.     return optGoto.isSet(getOptions());
132. field_1_options=optGoto.setByteBoolean(field_1_options, isGoto);
191.     } else if (optGoto.isSet(field_1_options)) {
219.   if(optGoto.isSet(field_1_options)) {
{% endhighlight %}

***

## [Cluster 8](./8)
1 results
> code comments is here.
{% highlight java %}
67. private BitField          optiIf       = BitFieldFactory.getInstance(0x02);
100.     return optiIf.isSet(getOptions());
124.     field_1_options=optiIf.setByteBoolean(field_1_options,bif);
189.     } else if (optiIf.isSet(field_1_options)) {
213.   if(optiIf.isSet(field_1_options)) {
{% endhighlight %}

***

## [Cluster 9](./9)
1 results
> code comments is here.
{% highlight java %}
75. static final private BitField rowsumsbelow        = BitFieldFactory.getInstance(
167.     field_1_wsbool = rowsumsbelow.setByteBoolean(field_1_wsbool, below);
276.     return rowsumsbelow.isSet(field_1_wsbool);
{% endhighlight %}

***

## [Cluster 10](./10)
1 results
> code comments is here.
{% highlight java %}
77. static final private BitField rowsumsright        = BitFieldFactory.getInstance(
177.     field_1_wsbool = rowsumsright.setByteBoolean(field_1_wsbool, right);
286.     return rowsumsright.isSet(field_1_wsbool);
{% endhighlight %}

***

## [Cluster 11](./11)
1 results
> code comments is here.
{% highlight java %}
67. static final private BitField autobreaks          =
147.     field_1_wsbool = autobreaks.setByteBoolean(field_1_wsbool, ab);
256.     return autobreaks.isSet(field_1_wsbool);
{% endhighlight %}

***

## [Cluster 12](./12)
1 results
> code comments is here.
{% highlight java %}
71. static final private BitField dialog              =
157.     field_1_wsbool = dialog.setByteBoolean(field_1_wsbool, isDialog);
266.     return dialog.isSet(field_1_wsbool);
{% endhighlight %}

***

## [Cluster 13](./13)
24 results
> code comments is here.
{% highlight java %}
50. /**/private static BitField fOnlyWinPics = new BitField(0x02);
1660.     field_5_docinfo = (byte)fOnlyWinPics.setBoolean(field_5_docinfo, value);
1670.     return fOnlyWinPics.isSet(field_5_docinfo);
{% endhighlight %}

***

## [Cluster 14](./14)
20 results
> code comments is here.
{% highlight java %}
128. /**/private static BitField fNoTabForInd = new BitField(0x00000001);
2660.     field_33_docinfo4 = (int)fNoTabForInd.setBoolean(field_33_docinfo4, value);
2670.     return fNoTabForInd.isSet(field_33_docinfo4);
{% endhighlight %}

***

## [Cluster 15](./15)
45 results
> code comments is here.
{% highlight java %}
76. /**/private static BitField oldfNoTabForInd = new BitField(0x0001);
2120.     field_8_docinfo3 = (short)oldfNoTabForInd.setBoolean(field_8_docinfo3, value);
2130.     return oldfNoTabForInd.isSet(field_8_docinfo3);
{% endhighlight %}

***

## [Cluster 16](./16)
18 results
> code comments is here.
{% highlight java %}
50. /**/private static final BitField iType = new BitField(0x0003);
255.     field_2_bits = (short)iType.setValue(field_2_bits, value);
265.     return ( byte )iType.getValue(field_2_bits);
{% endhighlight %}

***

## [Cluster 17](./17)
8 results
> code comments is here.
{% highlight java %}
149. /**/private static final BitField spare = new BitField(0xe000);
2820.     field_29_ufel = (short)spare.setValue(field_29_ufel, value);
2830.     return ( byte )spare.getValue(field_29_ufel);
{% endhighlight %}

***

## [Cluster 18](./18)
69 results
> code comments is here.
{% highlight java %}
69. static final private BitField hidden   =
182.     field_5_options = hidden.setShortBoolean(field_5_options, ishidden);
327.     return hidden.isSet(field_5_options);
{% endhighlight %}

***

## [Cluster 19](./19)
9 results
> code comments is here.
{% highlight java %}
167. static final private BitField _indent_not_parent_cell_options =
630.         _indent_not_parent_cell_options
1351.     return _indent_not_parent_cell_options
{% endhighlight %}

***

