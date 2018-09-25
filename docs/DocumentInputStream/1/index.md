# DocumentInputStream @Cluster 1 (delegate, din, return)

***

### [EntryUtils.java](https://searchcode.com/codesearch/view/97383212/)
> < p > sets the poi file ' s path . < / p > 
{% highlight java %}
55. DocumentInputStream dstream = new DocumentInputStream( dentry );
57. dstream.close();
{% endhighlight %}

***

### [HSLFSlideShow.java](https://searchcode.com/codesearch/view/97394255/)
> read back a workbook that was written out to a memory buffer with { @ link # writeout ( workbook ) } or { @ link # writeoutandclose ( workbook ) } . 
{% highlight java %}
346. DocumentInputStream is = null;
454.         is.skip(imgsize);
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/48925096/)
> < p > list param is the workbook . < p > the workbook is specified in . ' / ' . format a ' type ( in - 3 , 7 6 ) ' s workbook . < br > 0 x 4 0 8 6 5 0 x 4 0 5 6 7 5 < p > @ return a string representing the current block . 
{% highlight java %}
64. DocumentInputStream din = fsys.createDocumentInputStream("WordDocument");
68. din.read(header);
69. din.close();
116. din.read(tableStream);
{% endhighlight %}

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/138792453/)
> < p > list param is the workbook . < p > the workbook is specified in . ' / ' . format a ' type ( in - 3 , 7 6 ) ' s workbook . < br > 0 x 4 0 8 6 5 0 x 4 0 5 6 7 5 < p > @ return a string representing the current block . 
{% highlight java %}
64. DocumentInputStream din = fsys.createDocumentInputStream("WordDocument");
68. din.read(header);
69. din.close();
116. din.read(tableStream);
{% endhighlight %}

***

### [DocumentInputStream.java](https://searchcode.com/codesearch/view/97397924/)
> test that we get the same value as excel and , for 
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

