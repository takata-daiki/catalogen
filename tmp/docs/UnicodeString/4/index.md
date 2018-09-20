# UnicodeString @Cluster 4

***

### [UnicodeString.java](https://searchcode.com/codesearch/view/15642397/)
{% highlight java %}
162. UnicodeString other = ( UnicodeString ) o;
165. boolean eq = ((field_1_charCount == other.field_1_charCount)
166.         && (field_2_optionflags == other.field_2_optionflags)
167.         && field_3_string.equals(other.field_3_string));
171. if ((field_4_format_runs == null) && (other.field_4_format_runs == null))
174. if (((field_4_format_runs == null) && (other.field_4_format_runs != null)) ||
175.      (field_4_format_runs != null) && (other.field_4_format_runs == null))
181. if (size != other.field_4_format_runs.size())
186.   FormatRun run2 = (FormatRun)other.field_4_format_runs.get(i);
194. if ((field_5_ext_rst == null) && (other.field_5_ext_rst == null))
196. if (((field_5_ext_rst == null) && (other.field_5_ext_rst != null)) ||
197.     ((field_5_ext_rst != null) && (other.field_5_ext_rst == null)))
205.   if (field_5_ext_rst[i] != other.field_5_ext_rst[i])
{% endhighlight %}

***

