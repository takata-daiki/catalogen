# XSLFSheet @Cluster 5

***

### [XSLFTextParagraph.java](https://searchcode.com/codesearch/view/97406665/)
{% highlight java %}
1078. XSLFSheet masterSheet = _shape.getSheet();
1079. while (masterSheet.getMasterSheet() != null){
1080.     masterSheet = masterSheet.getMasterSheet();
1083. XmlObject[] o = masterSheet.getXmlObject().selectPath(
{% endhighlight %}

***

