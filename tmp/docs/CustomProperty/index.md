# CustomProperty

***

### [Cluster 1](./1)
{% highlight java %}
106. final CustomProperty cp = (CustomProperty) customProperty;
117. if (!(name.equals(cp.getName())))
119.             ") and custom property's name (" + cp.getName() +
123. final Long idKey = new Long(cp.getID());
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
613. final CustomProperty cp = new CustomProperty(p,
615. cps.put(cp.getName(), cp);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
126. final CustomProperty c = (CustomProperty) o;
127. final String name1 = c.getName();
134. return equalNames && c.getID() == this.getID()
135.         && c.getType() == this.getType()
136.         && c.getValue().equals(this.getValue());
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
154. private Object put(final CustomProperty customProperty) throws ClassCastException
156.     final String name = customProperty.getName();
161.         customProperty.setID(oldId.longValue());
171.         customProperty.setID(max + 1);
{% endhighlight %}

***

