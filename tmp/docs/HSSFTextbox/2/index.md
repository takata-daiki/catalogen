# HSSFTextbox @Cluster 2 (getstring, obj, shape)

***

### [TextboxShape.java](https://searchcode.com/codesearch/view/15642364/)
> set the contents of the record that are attached to the specified - shape @ param . 
{% highlight java %}
156. HSSFTextbox shape = hssfShape;
163. int frLength = ( shape.getString().numFormattingRuns() + 1 ) * 8;
165. obj.setTextLength( (short) shape.getString().length() );
166. obj.setStr( shape.getString() );
{% endhighlight %}

***

