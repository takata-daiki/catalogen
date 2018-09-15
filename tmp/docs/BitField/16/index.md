# BitField @Cluster 16

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
143. /**/private static final BitField itypFELayout = new BitField(0x00ff);
2700.     field_29_ufel = (short)itypFELayout.setValue(field_29_ufel, value);
2710.     return ( short )itypFELayout.getValue(field_29_ufel);
{% endhighlight %}

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
144. /**/private static final BitField fTNY = new BitField(0x0100);
2720.     field_29_ufel = (short)fTNY.setBoolean(field_29_ufel, value);
2730.     return fTNY.isSet(field_29_ufel);
{% endhighlight %}

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
145. /**/private static final BitField fWarichu = new BitField(0x0200);
2740.     field_29_ufel = (short)fWarichu.setBoolean(field_29_ufel, value);
2750.     return fWarichu.isSet(field_29_ufel);
{% endhighlight %}

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
146. /**/private static final BitField fKumimoji = new BitField(0x0400);
2760.     field_29_ufel = (short)fKumimoji.setBoolean(field_29_ufel, value);
2770.     return fKumimoji.isSet(field_29_ufel);
{% endhighlight %}

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
147. /**/private static final BitField fRuby = new BitField(0x0800);
2780.     field_29_ufel = (short)fRuby.setBoolean(field_29_ufel, value);
2790.     return fRuby.isSet(field_29_ufel);
{% endhighlight %}

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
148. /**/private static final BitField fLSFitText = new BitField(0x1000);
2800.     field_29_ufel = (short)fLSFitText.setBoolean(field_29_ufel, value);
2810.     return fLSFitText.isSet(field_29_ufel);
{% endhighlight %}

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
176. /**/private static final BitField fHighlight = new BitField(0x0020);
2980.     field_48_Highlight = (short)fHighlight.setBoolean(field_48_Highlight, value);
2990.     return fHighlight.isSet(field_48_Highlight);
{% endhighlight %}

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
178. /**/private static final BitField fChsDiff = new BitField(0x0001);
3000.     field_49_CharsetFlags = (short)fChsDiff.setBoolean(field_49_CharsetFlags, value);
3010.     return fChsDiff.isSet(field_49_CharsetFlags);
{% endhighlight %}

***

### [CHPAbstractType.java](https://searchcode.com/codesearch/view/97384327/)
{% highlight java %}
179. /**/private static final BitField fMacChs = new BitField(0x0020);
3020.     field_49_CharsetFlags = (short)fMacChs.setBoolean(field_49_CharsetFlags, value);
3030.     return fMacChs.isSet(field_49_CharsetFlags);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
50. /**/private static final BitField iType = new BitField(0x0003);
255.     field_2_bits = (short)iType.setValue(field_2_bits, value);
265.     return ( byte )iType.getValue(field_2_bits);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
57. /**/private static final BitField iRes = new BitField(0x007C);
275.     field_2_bits = (short)iRes.setValue(field_2_bits, value);
285.     return ( byte )iRes.getValue(field_2_bits);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
58. /**/private static final BitField fOwnHelp = new BitField(0x0080);
295.     field_2_bits = (short)fOwnHelp.setBoolean(field_2_bits, value);
305.     return fOwnHelp.isSet(field_2_bits);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
59. /**/private static final BitField fOwnStat = new BitField(0x0100);
315.     field_2_bits = (short)fOwnStat.setBoolean(field_2_bits, value);
325.     return fOwnStat.isSet(field_2_bits);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
60. /**/private static final BitField fProt = new BitField(0x0200);
335.     field_2_bits = (short)fProt.setBoolean(field_2_bits, value);
345.     return fProt.isSet(field_2_bits);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
61. /**/private static final BitField iSize = new BitField(0x0400);
355.     field_2_bits = (short)iSize.setBoolean(field_2_bits, value);
365.     return iSize.isSet(field_2_bits);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
62. /**/private static final BitField iTypeTxt = new BitField(0x3800);
375.     field_2_bits = (short)iTypeTxt.setValue(field_2_bits, value);
385.     return ( byte )iTypeTxt.getValue(field_2_bits);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
75. /**/private static final BitField fRecalc = new BitField(0x4000);
395.     field_2_bits = (short)fRecalc.setBoolean(field_2_bits, value);
405.     return fRecalc.isSet(field_2_bits);
{% endhighlight %}

***

### [FFDataBaseAbstractType.java](https://searchcode.com/codesearch/view/97384293/)
{% highlight java %}
76. /**/private static final BitField fHasListBox = new BitField(0x8000);
415.     field_2_bits = (short)fHasListBox.setBoolean(field_2_bits, value);
425.     return fHasListBox.isSet(field_2_bits);
{% endhighlight %}

***

