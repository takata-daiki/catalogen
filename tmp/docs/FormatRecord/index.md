# FormatRecord

***

### [Cluster 1](./1)
{% highlight java %}
126. FormatRecord r = (FormatRecord) i.next();
127. if ( formats.size() < r.getIndexCode() + 1 )
129.     formats.setSize( r.getIndexCode() + 1 );
131. formats.set( r.getIndexCode(), r.getFormatString() );
{% endhighlight %}

***

### [Cluster 2](./2)
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

