# BitField @Cluster 13

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
50. /**/private static BitField fOnlyWinPics = new BitField(0x02);
1660.     field_5_docinfo = (byte)fOnlyWinPics.setBoolean(field_5_docinfo, value);
1670.     return fOnlyWinPics.isSet(field_5_docinfo);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
51. /**/private static BitField fLabelDoc = new BitField(0x04);
1680.     field_5_docinfo = (byte)fLabelDoc.setBoolean(field_5_docinfo, value);
1690.     return fLabelDoc.isSet(field_5_docinfo);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
52. /**/private static BitField fHyphCapitals = new BitField(0x08);
1700.     field_5_docinfo = (byte)fHyphCapitals.setBoolean(field_5_docinfo, value);
1710.     return fHyphCapitals.isSet(field_5_docinfo);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
53. /**/private static BitField fAutoHyphen = new BitField(0x10);
1720.     field_5_docinfo = (byte)fAutoHyphen.setBoolean(field_5_docinfo, value);
1730.     return fAutoHyphen.isSet(field_5_docinfo);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
54. /**/private static BitField fFormNoFields = new BitField(0x20);
1740.     field_5_docinfo = (byte)fFormNoFields.setBoolean(field_5_docinfo, value);
1750.     return fFormNoFields.isSet(field_5_docinfo);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
55. /**/private static BitField fLinkStyles = new BitField(0x40);
1760.     field_5_docinfo = (byte)fLinkStyles.setBoolean(field_5_docinfo, value);
1770.     return fLinkStyles.isSet(field_5_docinfo);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
56. /**/private static BitField fRevMarking = new BitField(0x80);
1780.     field_5_docinfo = (byte)fRevMarking.setBoolean(field_5_docinfo, value);
1790.     return fRevMarking.isSet(field_5_docinfo);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
58. /**/private static BitField fBackup = new BitField(0x01);
1800.     field_6_docinfo1 = (byte)fBackup.setBoolean(field_6_docinfo1, value);
1810.     return fBackup.isSet(field_6_docinfo1);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
59. /**/private static BitField fExactCWords = new BitField(0x02);
1820.     field_6_docinfo1 = (byte)fExactCWords.setBoolean(field_6_docinfo1, value);
1830.     return fExactCWords.isSet(field_6_docinfo1);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
60. /**/private static BitField fPagHidden = new BitField(0x04);
1840.     field_6_docinfo1 = (byte)fPagHidden.setBoolean(field_6_docinfo1, value);
1850.     return fPagHidden.isSet(field_6_docinfo1);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
61. /**/private static BitField fPagResults = new BitField(0x08);
1860.     field_6_docinfo1 = (byte)fPagResults.setBoolean(field_6_docinfo1, value);
1870.     return fPagResults.isSet(field_6_docinfo1);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
62. /**/private static BitField fLockAtn = new BitField(0x10);
1880.     field_6_docinfo1 = (byte)fLockAtn.setBoolean(field_6_docinfo1, value);
1890.     return fLockAtn.isSet(field_6_docinfo1);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
63. /**/private static BitField fMirrorMargins = new BitField(0x20);
1900.     field_6_docinfo1 = (byte)fMirrorMargins.setBoolean(field_6_docinfo1, value);
1910.     return fMirrorMargins.isSet(field_6_docinfo1);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
64. /**/private static BitField unused3 = new BitField(0x40);
1920.     field_6_docinfo1 = (byte)unused3.setBoolean(field_6_docinfo1, value);
1930.     return unused3.isSet(field_6_docinfo1);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
65. /**/private static BitField fDfltTrueType = new BitField(0x80);
1940.     field_6_docinfo1 = (byte)fDfltTrueType.setBoolean(field_6_docinfo1, value);
1950.     return fDfltTrueType.isSet(field_6_docinfo1);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
67. /**/private static BitField fPagSupressTopSpacing = new BitField(0x01);
1960.     field_7_docinfo2 = (byte)fPagSupressTopSpacing.setBoolean(field_7_docinfo2, value);
1970.     return fPagSupressTopSpacing.isSet(field_7_docinfo2);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
68. /**/private static BitField fProtEnabled = new BitField(0x02);
1980.     field_7_docinfo2 = (byte)fProtEnabled.setBoolean(field_7_docinfo2, value);
1990.     return fProtEnabled.isSet(field_7_docinfo2);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
69. /**/private static BitField fDispFormFldSel = new BitField(0x04);
2000.     field_7_docinfo2 = (byte)fDispFormFldSel.setBoolean(field_7_docinfo2, value);
2010.     return fDispFormFldSel.isSet(field_7_docinfo2);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
70. /**/private static BitField fRMView = new BitField(0x08);
2020.     field_7_docinfo2 = (byte)fRMView.setBoolean(field_7_docinfo2, value);
2030.     return fRMView.isSet(field_7_docinfo2);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
71. /**/private static BitField fRMPrint = new BitField(0x10);
2040.     field_7_docinfo2 = (byte)fRMPrint.setBoolean(field_7_docinfo2, value);
2050.     return fRMPrint.isSet(field_7_docinfo2);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
72. /**/private static BitField unused4 = new BitField(0x20);
2060.     field_7_docinfo2 = (byte)unused4.setBoolean(field_7_docinfo2, value);
2070.     return unused4.isSet(field_7_docinfo2);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
73. /**/private static BitField fLockRev = new BitField(0x40);
2080.     field_7_docinfo2 = (byte)fLockRev.setBoolean(field_7_docinfo2, value);
2090.     return fLockRev.isSet(field_7_docinfo2);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
74. /**/private static BitField fEmbedFonts = new BitField(0x80);
2100.     field_7_docinfo2 = (byte)fEmbedFonts.setBoolean(field_7_docinfo2, value);
2110.     return fEmbedFonts.isSet(field_7_docinfo2);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
49. /**/private static BitField fOnlyMacPics = new BitField(0x01);
1640.     field_5_docinfo = (byte)fOnlyMacPics.setBoolean(field_5_docinfo, value);
1650.     return fOnlyMacPics.isSet(field_5_docinfo);
{% endhighlight %}

***

