# CurrentUserAtom

***

## [Cluster 1 (c2, crec, getdata)](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
78. private CurrentUserAtom currentUser;
276.       _records = read(_docstream, (int)currentUser.getCurrentEditOffset());
545.       int oldLastUserEditAtomPos = (int)currentUser.getCurrentEditOffset();
550.       currentUser.setCurrentEditOffset(newLastUserEditAtomPos.intValue());
551.       currentUser.writeToFS(outFS);
{% endhighlight %}

***

