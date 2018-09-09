# CustomProperties

***

### [Cluster 1](./1)
{% highlight java %}
264. final CustomProperties cp = new CustomProperties ();
265. cp.put ( "openSCADA Export Version", Activator.getDefault ().getBundle ().getVersion ().toString () );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
49. CustomProperties cp = dsi.getCustomProperties();
52. cp.put("myProperty", "foo bar baz");
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
121. CustomProperties customProperties = summary.getCustomProperties();
123.     Object value = customProperties.get("Language");
{% endhighlight %}

***

