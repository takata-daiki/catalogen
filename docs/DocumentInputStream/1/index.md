# DocumentInputStream @Cluster 1

***

### [EntryUtils.java](https://searchcode.com/codesearch/view/97383212/)
{% highlight java %}
55. DocumentInputStream dstream = new DocumentInputStream( dentry );
57. dstream.close();
{% endhighlight %}

***

### [ImapServiceExtImpl.java](https://searchcode.com/codesearch/view/50611261/)
{% highlight java %}
563. DocumentInputStream inputStream = root.createDocumentInputStream(entry);
567.     inputStream.close();
{% endhighlight %}

***

### [HSLFSlideShow.java](https://searchcode.com/codesearch/view/97394255/)
{% highlight java %}
346. DocumentInputStream is = null;
454.         is.skip(imgsize);
{% endhighlight %}

***

### [EntryUtils.java](https://searchcode.com/codesearch/view/97383212/)
{% highlight java %}
248. DocumentInputStream inpA = null, inpB = null;
255.       readA = inpA.read();
263.    if (inpA != null) inpA.close();
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/48925096/)
{% highlight java %}
64. DocumentInputStream din = fsys.createDocumentInputStream("WordDocument");
68. din.read(header);
69. din.close();
116. din.read(tableStream);
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/138792453/)
{% highlight java %}
64. DocumentInputStream din = fsys.createDocumentInputStream("WordDocument");
68. din.read(header);
69. din.close();
116. din.read(tableStream);
{% endhighlight %}

***

### [DocumentInputStream.java](https://searchcode.com/codesearch/view/97397924/)
{% highlight java %}
39. private DocumentInputStream delegate;
89.    return delegate.available();
93.    delegate.close();
97.   delegate.mark(ignoredReadlimit);
110.    return delegate.read();
118.    return delegate.read(b, off, len);
127.    delegate.reset();
131.    return delegate.skip(n);
135.    return delegate.readByte();
139.    return delegate.readDouble();
151.    delegate.readFully(buf, off, len);
155.    return delegate.readLong();
159.    return delegate.readInt();
163.    return delegate.readUShort();
167.    return delegate.readUByte();
171.   return delegate.position();
{% endhighlight %}

***

