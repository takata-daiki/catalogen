# BitField @Cluster 1

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
37. /**/private static BitField fFacingPages = new BitField(0x01);
1480.     field_1_formatFlags = (byte)fFacingPages.setBoolean(field_1_formatFlags, value);
1490.     return fFacingPages.isSet(field_1_formatFlags);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
38. /**/private static BitField fWidowControl = new BitField(0x02);
1500.     field_1_formatFlags = (byte)fWidowControl.setBoolean(field_1_formatFlags, value);
1510.     return fWidowControl.isSet(field_1_formatFlags);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
39. /**/private static BitField fPMHMainDoc = new BitField(0x04);
1520.     field_1_formatFlags = (byte)fPMHMainDoc.setBoolean(field_1_formatFlags, value);
1530.     return fPMHMainDoc.isSet(field_1_formatFlags);
{% endhighlight %}

***

### [DOPAbstractType.java](https://searchcode.com/codesearch/view/88635700/)
{% highlight java %}
42. /**/private static BitField unused1 = new BitField(0x80);
1580.     field_1_formatFlags = (byte)unused1.setBoolean(field_1_formatFlags, value);
1590.     return unused1.isSet(field_1_formatFlags);
{% endhighlight %}

***

