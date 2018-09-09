# HSSFTextbox

***

### [Cluster 1](./1)
{% highlight java %}
101. HSSFTextbox shape = new HSSFTextbox(this, anchor);
102. shape.anchor = anchor;
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
156. HSSFTextbox shape = hssfShape;
163. int frLength = ( shape.getString().numFormattingRuns() + 1 ) * 8;
165. obj.setTextLength( (short) shape.getString().length() );
166. obj.setStr( shape.getString() );
{% endhighlight %}

***

