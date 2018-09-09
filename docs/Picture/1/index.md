# Picture @Cluster 1

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

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
{% highlight java %}
336.     Picture picture, String imageSourcePath )
338. final int aspectRatioX = picture.getHorizontalScalingFactor();
339. final int aspectRatioY = picture.getVerticalScalingFactor();
353.     imageWidth = picture.getDxaGoal() * aspectRatioX / 1000
355.     cropRight = picture.getDxaCropRight() * aspectRatioX / 1000
357.     cropLeft = picture.getDxaCropLeft() * aspectRatioX / 1000
362.     imageWidth = picture.getDxaGoal() / TWIPS_PER_INCH;
363.     cropRight = picture.getDxaCropRight() / TWIPS_PER_INCH;
364.     cropLeft = picture.getDxaCropLeft() / TWIPS_PER_INCH;
369.     imageHeight = picture.getDyaGoal() * aspectRatioY / 1000
371.     cropTop = picture.getDyaCropTop() * aspectRatioY / 1000
373.     cropBottom = picture.getDyaCropBottom() * aspectRatioY / 1000
378.     imageHeight = picture.getDyaGoal() / TWIPS_PER_INCH;
379.     cropTop = picture.getDyaCropTop() / TWIPS_PER_INCH;
380.     cropBottom = picture.getDyaCropBottom() / TWIPS_PER_INCH;
{% endhighlight %}

***

