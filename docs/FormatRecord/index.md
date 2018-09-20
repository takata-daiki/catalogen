# FormatRecord

***

## [Cluster 1](./1)
1 results
> test that we get the same value as excel and , for 
{% highlight java %}
2054. FormatRecord rec = new FormatRecord();
2056. rec.setIndexCode( maxformatid );
2057. rec.setFormatStringLength( (byte) format.length() );
2058. rec.setFormatString( format );
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> this comment could not be generated...
{% highlight java %}
1216. FormatRecord retval = new FormatRecord();   // the differnt formats
1221.         retval.setIndexCode(( short ) 5);
1222.         retval.setFormatStringLength(( byte ) 0x17);
1223.         retval.setFormatString("\"$\"#,##0_);\\(\"$\"#,##0\\)");
1227.         retval.setIndexCode(( short ) 6);
1228.         retval.setFormatStringLength(( byte ) 0x1c);
1229.         retval.setFormatString("\"$\"#,##0_);[Red]\\(\"$\"#,##0\\)");
1233.         retval.setIndexCode(( short ) 7);
1234.         retval.setFormatStringLength(( byte ) 0x1d);
1235.         retval.setFormatString("\"$\"#,##0.00_);\\(\"$\"#,##0.00\\)");
1239.         retval.setIndexCode(( short ) 8);
1240.         retval.setFormatStringLength(( byte ) 0x22);
1241.         retval.setFormatString(
1246.         retval.setIndexCode(( short ) 0x2a);
1247.         retval.setFormatStringLength(( byte ) 0x32);
1253.         retval.setIndexCode(( short ) 0x29);
1254.         retval.setFormatStringLength(( byte ) 0x29);
1260.         retval.setIndexCode(( short ) 0x2c);
1261.         retval.setFormatStringLength(( byte ) 0x3a);
1267.         retval.setIndexCode(( short ) 0x2b);
1268.         retval.setFormatStringLength(( byte ) 0x31);
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> returns the color of the given sheet @ see org . apache . poi . xwpf . usermodel . ibody # " ( 1 2 1 6 ) . 
{% highlight java %}
2021.   FormatRecord r = (FormatRecord)iterator.next();
2022.   if (r.getFormatString().equals(format)) {
2023. return r.getIndexCode();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> this comment could not be generated...
{% highlight java %}
126. FormatRecord r = (FormatRecord) i.next();
127. if ( formats.size() < r.getIndexCode() + 1 )
129.     formats.setSize( r.getIndexCode() + 1 );
131. formats.set( r.getIndexCode(), r.getFormatString() );
{% endhighlight %}

***

