# PasswordRecord

***

### [Cluster 1](./1)
{% highlight java %}
1028. PasswordRecord retval = new PasswordRecord();
1030. retval.setPassword(( short ) 0);   // no password by default!
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
148. PasswordRecord clone = new PasswordRecord();
149. clone.setPassword(field_1_password);
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
2904. PasswordRecord pass = getPassword();
2906. pass.setPassword(PasswordRecord.hashPassword(password));
{% endhighlight %}

***

