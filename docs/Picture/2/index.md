# Picture @Cluster 2

***

### [AbstractWordConverter.java](https://searchcode.com/codesearch/view/97383976/)
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

