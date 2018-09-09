# FormatRun

***

### [Cluster 1](./1)
{% highlight java %}
185. FormatRun run1 = (FormatRun)field_4_format_runs.get(i);
188. if (!run1.equals(run2))
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
107. FormatRun r = (FormatRun)obj;
108. if ((character == r.character) && (fontIndex == r.fontIndex))
110. if (character == r.character)
111.   return fontIndex - r.fontIndex;
112. else return character - r.character;
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
101. FormatRun other = ( FormatRun ) o;
103. return ((character == other.character) && (fontIndex == other.fontIndex));
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
417. public void addFormatRun(FormatRun r) {
421.   int index = findFormatRunAt(r.character);
{% endhighlight %}

***

