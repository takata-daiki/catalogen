# Xstz

***

### [Cluster 1](./1)
{% highlight java %}
61. private Xstz _xstzEntryMcr;
134.     offset += this._xstzEntryMcr.getSize();
179.     size += _xstzEntryMcr.getSize();
220.     offset += _xstzEntryMcr.serialize( buffer, offset );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
83. private Xstz _xstzTextDef;
105.         offset += this._xstzTextDef.getSize();
167.         size += _xstzTextDef.getSize();
192.     return _xstzTextDef.getAsJavaString();
207.         offset += _xstzTextDef.serialize( buffer, offset );
{% endhighlight %}

***

