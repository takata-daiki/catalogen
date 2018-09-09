# BitField @Cluster 3

***

### [SprmOperation.java](https://searchcode.com/codesearch/view/97384367/)
{% highlight java %}
35. private static final BitField BITFIELD_OP = BitFieldFactory
60.     return BITFIELD_OP.getValue( opcode );
148.     return BITFIELD_OP.getValue( _value );
{% endhighlight %}

***

### [SprmOperation.java](https://searchcode.com/codesearch/view/97384367/)
{% highlight java %}
37. private static final BitField BITFIELD_SIZECODE = BitFieldFactory
153.     return BITFIELD_SIZECODE.getValue( _value );
{% endhighlight %}

***

### [SprmOperation.java](https://searchcode.com/codesearch/view/97384367/)
{% highlight java %}
41. private static final BitField BITFIELD_TYPE = BitFieldFactory
65.     return BITFIELD_TYPE.getValue( opcode );
158.     return BITFIELD_TYPE.getValue( _value );
{% endhighlight %}

***

### [WindowProtectRecord.java](https://searchcode.com/codesearch/view/88639900/)
{% highlight java %}
34. private static final BitField settingsProtectedFlag = BitFieldFactory.getInstance(0x0001);
56.     _options = settingsProtectedFlag.setBoolean(_options, protect);
65.     return settingsProtectedFlag.isSet(_options);
{% endhighlight %}

***

### [EscherRecord.java](https://searchcode.com/codesearch/view/97383909/)
{% highlight java %}
34. private static BitField fInstance = BitFieldFactory.getInstance(0xfff0);
95.     return fInstance.getShortValue( options );
133.     setInstance( fInstance.getShortValue( options ) );
263.     return fInstance.getShortValue( _options );
274.     _options = fInstance.setShortValue( _options, value );
{% endhighlight %}

***

### [EscherRecord.java](https://searchcode.com/codesearch/view/97383909/)
{% highlight java %}
35. private static BitField fVersion = BitFieldFactory.getInstance(0x000f);
132.     setVersion( fVersion.getShortValue( options ) );
284.     return fVersion.getShortValue( _options );
295.     _options = fVersion.setShortValue( _options, value );
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
167. static final private BitField _indent_not_parent_cell_options =
630.         _indent_not_parent_cell_options
1351.     return _indent_not_parent_cell_options
{% endhighlight %}

***

