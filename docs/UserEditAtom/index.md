# UserEditAtom

***

### [Cluster 1](./1)
{% highlight java %}
283. UserEditAtom usr = (UserEditAtom) Record.buildRecordAtOffset(docstream, usrOffset);
285. int psrOffset = usr.getPersistPointersOffset();
296. usrOffset = usr.getLastUserEditAtomOffset();
{% endhighlight %}

***

