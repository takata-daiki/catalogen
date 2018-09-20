# Picture

***

## [Cluster 1](./1)
4 results
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . 
{% highlight java %}
72. for(Picture picture : pictures) {
78.    String extension = picture.suggestFileExtension();
101.    TikaInputStream stream = TikaInputStream.get(picture.getContent());
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
920. Picture picture )
925. final int aspectRatioX = picture.getHorizontalScalingFactor();
926. final int aspectRatioY = picture.getVerticalScalingFactor();
928. final float imageWidth = aspectRatioX > 0 ? picture.getDxaGoal()
930.         : picture.getDxaGoal() / AbstractWordUtils.TWIPS_PER_INCH;
931. final float imageHeight = aspectRatioY > 0 ? picture.getDyaGoal()
933.         : picture.getDyaGoal() / AbstractWordUtils.TWIPS_PER_INCH;
935. String url = fileManager.savePicture( picture.getContent(),
936.         picture.suggestPictureType(),
937.         picture.suggestFullFileName(), imageWidth, imageHeight );
{% endhighlight %}

***

