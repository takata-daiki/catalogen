# PlexOfCps

***

### [Cluster 1](./1)
{% highlight java %}
50. PlexOfCps binTable = new PlexOfCps(documentStream, offset, size, 2);
52. int length = binTable.length();
55.   GenericPropertyNode node = binTable.getProperty(x);
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
79. private static ArrayList<PlexOfField> toArrayList( PlexOfCps plexOfCps )
85.             plexOfCps.length() );
86.     for ( int i = 0; i < plexOfCps.length(); i++ )
88.         GenericPropertyNode propNode = plexOfCps.getProperty( i );
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
134.     PlexOfCps plexOfCps, HWPFOutputStream outputStream )
137. if ( plexOfCps == null || plexOfCps.length() == 0 )
144. byte[] data = plexOfCps.toByteArray();
{% endhighlight %}

***

