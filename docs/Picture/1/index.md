# Picture @Cluster 1 (1000, aspectratiox, twips_per_inch)

***

### [WordExtractor.java](https://searchcode.com/codesearch/view/111785561/)
> set the contents of this shape to be a copy of the source shape . this method is called recursively for each shape when 0 . 
{% highlight java %}
72. for(Picture picture : pictures) {
78.    String extension = picture.suggestFileExtension();
101.    TikaInputStream stream = TikaInputStream.get(picture.getContent());
{% endhighlight %}

***

### [WordToHtmlConverter.java](https://searchcode.com/codesearch/view/97383966/)
> sets the a 1 - style , style the child @ param cell the cell to set @ param change the number of pages 
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

