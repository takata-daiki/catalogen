# CurrentUserAtom

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
78. private CurrentUserAtom currentUser;
276.       _records = read(_docstream, (int)currentUser.getCurrentEditOffset());
545.       int oldLastUserEditAtomPos = (int)currentUser.getCurrentEditOffset();
550.       currentUser.setCurrentEditOffset(newLastUserEditAtomPos.intValue());
551.       currentUser.writeToFS(outFS);
{% endhighlight %}

***

