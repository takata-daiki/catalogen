# HSSFPicture

***

### [Cluster 1](./1)
{% highlight java %}
131. HSSFPicture shape = new HSSFPicture(this, anchor);
132. shape.anchor = anchor;
133. shape.setPictureIndex( pictureIndex );
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
237. HSSFPicture pic = (HSSFPicture) shape;  
238. int pictureIndex = pic.getPictureIndex() - 1;  
{% endhighlight %}

***

