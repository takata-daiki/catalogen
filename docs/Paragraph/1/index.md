# Paragraph @Cluster 1

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
{% highlight java %}
23. Paragraph par1 = range.insertAfter(new ParagraphProperties(), 0);
24. par1.setSpacingAfter(200);
25. par1.setJustification((byte) 1);
28. CharacterRun run1 = par1.insertAfter("one");
{% endhighlight %}

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
{% highlight java %}
33. Paragraph par2 = run1.insertAfter(new ParagraphProperties(), 0);
34. par2.setSpacingAfter(200);
35. CharacterRun run2 = par2.insertAfter("two two two two two two two two two two two two two");
{% endhighlight %}

***

### [CreateWordDoc.java](https://searchcode.com/codesearch/view/111543829/)
{% highlight java %}
39. Paragraph par3 = run2.insertAfter(new ParagraphProperties(), 0);
40. par3.setFirstLineIndent(200);
41. par3.setSpacingAfter(200);
42. CharacterRun run3 = par3.insertAfter("three three three three three three three three three "
{% endhighlight %}

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
{% highlight java %}
629. Paragraph p = (Paragraph)super.clone();
630. p._props = (ParagraphProperties)_props.clone();
632. p._papx = new SprmBuffer(0);
{% endhighlight %}

***

