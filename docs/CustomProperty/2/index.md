# CustomProperty @Cluster 2

***

### [CustomProperty.java](https://searchcode.com/codesearch/view/15642672/)
{% highlight java %}
126. final CustomProperty c = (CustomProperty) o;
127. final String name1 = c.getName();
134. return equalNames && c.getID() == this.getID()
135.         && c.getType() == this.getType()
136.         && c.getValue().equals(this.getValue());
{% endhighlight %}

***

### [CustomProperties.java](https://searchcode.com/codesearch/view/15642679/)
{% highlight java %}
154. private Object put(final CustomProperty customProperty) throws ClassCastException
156.     final String name = customProperty.getName();
161.         customProperty.setID(oldId.longValue());
171.         customProperty.setID(max + 1);
{% endhighlight %}

***

### [CustomProperties.java](https://searchcode.com/codesearch/view/15642679/)
{% highlight java %}
296. final CustomProperty cp = (CustomProperty) super.get(id);
297. return cp != null ? cp.getValue() : null;
{% endhighlight %}

***

### [CustomProperties.java](https://searchcode.com/codesearch/view/15642679/)
{% highlight java %}
359. final CustomProperty cp = (CustomProperty) i.next();
360. if (cp.getID() == PropertyIDMap.PID_CODEPAGE)
361.     codepage = ((Integer) cp.getValue()).intValue();
{% endhighlight %}

***

