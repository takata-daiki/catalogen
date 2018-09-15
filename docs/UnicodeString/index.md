# UnicodeString

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
865. UnicodeString str = ( UnicodeString ) obj;
867. int result = getString().compareTo(str.getString());
874. if ((field_4_format_runs == null) && (str.field_4_format_runs == null))
878. if ((field_4_format_runs == null) && (str.field_4_format_runs != null))
881. if ((field_4_format_runs != null) && (str.field_4_format_runs == null))
887. if (size != str.field_4_format_runs.size())
888.   return size - str.field_4_format_runs.size();
892.   FormatRun run2 = (FormatRun)str.field_4_format_runs.get(i);
901. if ((field_5_ext_rst == null) && (str.field_5_ext_rst == null))
903. if ((field_5_ext_rst == null) && (str.field_5_ext_rst != null))
905. if ((field_5_ext_rst != null) && (str.field_5_ext_rst == null))
914.   if (field_5_ext_rst[i] != str.field_5_ext_rst[i])
915.     return field_5_ext_rst[i] - str.field_5_ext_rst[i];
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
933. UnicodeString str = new UnicodeString();
934. str.field_1_charCount = field_1_charCount;
935. str.field_2_optionflags = field_2_optionflags;
936. str.field_3_string = field_3_string;
938.   str.field_4_format_runs = new ArrayList();
942.     str.field_4_format_runs.add(new FormatRun(r.character, r.fontIndex));
946.   str.field_5_ext_rst = new byte[field_5_ext_rst.length];
947.   System.arraycopy(field_5_ext_rst, 0, str.field_5_ext_rst, 0,
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
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

