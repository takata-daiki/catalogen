# Paragraph @Cluster 2 (par1, par2, two)

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
> tests that the create record function returns a properly constructed record in the simple case . 
{% highlight java %}
33. Paragraph par2 = run1.insertAfter(new ParagraphProperties(), 0);
34. par2.setSpacingAfter(200);
35. CharacterRun run2 = par2.insertAfter("two two two two two two two two two two two two two");
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
> sets the property ' s id . 
{% highlight java %}
629. Paragraph p = (Paragraph)super.clone();
630. p._props = (ParagraphProperties)_props.clone();
632. p._papx = new SprmBuffer(0);
{% endhighlight %}

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
> sets the 
{% highlight java %}
23. Paragraph par1 = range.insertAfter(new ParagraphProperties(), 0);
24. par1.setSpacingAfter(200);
25. par1.setJustification((byte) 1);
28. CharacterRun run1 = par1.insertAfter("one");
{% endhighlight %}

***

