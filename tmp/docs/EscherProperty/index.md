# EscherProperty

***

### [Cluster 1](./1)
{% highlight java %}
206. EscherProperty p1 = (EscherProperty) o1;
208. return new Short( p1.getPropertyNumber() ).compareTo( new Short( p2.getPropertyNumber() ) );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
130. public int compare( EscherProperty p1, EscherProperty p2 )
132.     short s1 = p1.getPropertyNumber();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
80. for (EscherProperty property: getEscherProperties()){
81.     builder.append(property.toXml(tab+"\t"));
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
90. for ( EscherProperty prop : properties )
92.     if ( prop.getPropertyNumber() == propId )
{% endhighlight %}

***

### [Cluster 5](./5)
{% highlight java %}
124. EscherProperty prop = (EscherProperty) iterator.next();
125. switch (prop.getId()){
{% endhighlight %}

***

