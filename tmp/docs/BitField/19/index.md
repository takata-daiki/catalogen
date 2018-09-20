# BitField @Cluster 19

***

### [PrintSetupRecord.java](https://searchcode.com/codesearch/view/15642520/)
{% highlight java %}
84. static final private BitField usepage       =
198.     field_6_options = usepage.setShortBoolean(field_6_options, page);
295.     return usepage.isSet(field_6_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
189. static final private BitField _top_border_palette_idx    =
857.         _top_border_palette_idx.setValue(field_8_adtl_palette_options,
1572.     return ( short ) _top_border_palette_idx
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
149. static final private BitField _shrink_to_fit                  =
507.         _shrink_to_fit.setShortBoolean(field_5_indention_options, shrink);
1241.     return _shrink_to_fit.isSet(field_5_indention_options);
{% endhighlight %}

***

### [PrintSetupRecord.java](https://searchcode.com/codesearch/view/15642520/)
{% highlight java %}
76. static final private BitField nocolor       =
178.     field_6_options = nocolor.setShortBoolean(field_6_options, mono);
275.     return nocolor.isSet(field_6_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
199. static final private BitField _adtl_fill_pattern         =
948.         _adtl_fill_pattern.setValue(field_8_adtl_palette_options, fill);
1659.     return ( short ) _adtl_fill_pattern
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
175. static final private BitField _border_bottom = BitFieldFactory.getInstance(0xF000);
760.         _border_bottom.setShortValue(field_6_border_options, border);
1478.     return _border_bottom.getShortValue(field_6_border_options);
{% endhighlight %}

***

### [ColumnInfoRecord.java](https://searchcode.com/codesearch/view/15642500/)
{% highlight java %}
72. static final private BitField collapsed = BitFieldFactory.getInstance(0x1000);
190.     field_5_options = collapsed.setShortBoolean(field_5_options,
279.     return collapsed.isSet(field_5_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
132. static final private BitField _hidden       = BitFieldFactory.getInstance(0x0002);
317.     field_3_cell_options = _hidden.setShortBoolean(field_3_cell_options,
1061.     return _hidden.isSet(field_3_cell_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
133. static final private BitField _xf_type      = BitFieldFactory.getInstance(0x0004);
333.     field_3_cell_options = _xf_type.setShortValue(field_3_cell_options,
1076.     return _xf_type.getShortValue(field_3_cell_options);
{% endhighlight %}

***

### [ColumnInfoRecord.java](https://searchcode.com/codesearch/view/15642500/)
{% highlight java %}
71. static final private BitField outlevel  = BitFieldFactory.getInstance(0x0700);
179.     field_5_options = outlevel.setShortValue(field_5_options, olevel);
268.     return outlevel.getShortValue(field_5_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
180. static final private BitField _left_border_palette_idx  =
792.         _left_border_palette_idx.setShortValue(field_7_palette_options,
1509.     return _left_border_palette_idx
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
204. static final private BitField _fill_foreground = BitFieldFactory.getInstance(0x007F);
977.         _fill_foreground.setShortValue(field_9_fill_palette_options,
1691.     return _fill_foreground.getShortValue(field_9_fill_palette_options);
{% endhighlight %}

***

### [FontRecord.java](https://searchcode.com/codesearch/view/15642433/)
{% highlight java %}
74. static final private BitField italic     =
178.     field_2_attributes = italic.setShortBoolean(field_2_attributes, italics);
349.     return italic.isSet(field_2_attributes);
{% endhighlight %}

***

### [PrintSetupRecord.java](https://searchcode.com/codesearch/view/15642520/)
{% highlight java %}
78. static final private BitField draft         =
183.     field_6_options = draft.setShortBoolean(field_6_options, d);
280.     return draft.isSet(field_6_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
151. static final private BitField _merge_cells                    =
521.         _merge_cells.setShortBoolean(field_5_indention_options, merge);
1254.     return _merge_cells.isSet(field_5_indention_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
191. static final private BitField _bottom_border_palette_idx =
872.         _bottom_border_palette_idx.setValue(field_8_adtl_palette_options,
1586.     return ( short ) _bottom_border_palette_idx
{% endhighlight %}

***

### [PrintSetupRecord.java](https://searchcode.com/codesearch/view/15642520/)
{% highlight java %}
69. static final private BitField landscape     =
168.     field_6_options = landscape.setShortBoolean(field_6_options, ls);
265.     return landscape.isSet(field_6_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
205. static final private BitField _fill_background = BitFieldFactory.getInstance(0x3f80);
992.         _fill_background.setShortValue(field_9_fill_palette_options,
1703.     return _fill_background.getShortValue(field_9_fill_palette_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
173. static final private BitField _border_right  = BitFieldFactory.getInstance(0x00F0);
704.         _border_right.setShortValue(field_6_border_options, border);
1424.     return _border_right.getShortValue(field_6_border_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
134. static final private BitField _123_prefix   = BitFieldFactory.getInstance(0x0008);
348.         _123_prefix.setShortBoolean(field_3_cell_options, prefix);
1089.     return _123_prefix.isSet(field_3_cell_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
163. static final private BitField _indent_not_parent_border       =
598.         _indent_not_parent_border
1323.     return _indent_not_parent_border.isSet(field_5_indention_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
141. static final private BitField _vertical_alignment = BitFieldFactory.getInstance(0x0070);
434.         _vertical_alignment.setShortValue(field_4_alignment_options,
1171.     return _vertical_alignment.getShortValue(field_4_alignment_options);
{% endhighlight %}

***

### [WSBoolRecord.java](https://searchcode.com/codesearch/view/15642487/)
{% highlight java %}
83. static final private BitField displayguts         = BitFieldFactory.getInstance(
211.     field_2_wsbool = displayguts.setByteBoolean(field_2_wsbool, guts);
320.     return displayguts.isSet(field_2_wsbool);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
153. static final private BitField _reading_order                  =
535.         _reading_order.setShortValue(field_5_indention_options, order);
1267.     return _reading_order.getShortValue(field_5_indention_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
157. static final private BitField _indent_not_parent_format       =
550.         _indent_not_parent_format
1281.     return _indent_not_parent_format.isSet(field_5_indention_options);
{% endhighlight %}

***

### [WSBoolRecord.java](https://searchcode.com/codesearch/view/15642487/)
{% highlight java %}
87. static final private BitField alternateexpression =   // whether to use alternate expression eval
221.     field_2_wsbool = alternateexpression.setByteBoolean(field_2_wsbool,
330.     return alternateexpression.isSet(field_2_wsbool);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
142. static final private BitField _justify_last       = BitFieldFactory.getInstance(0x0080);
450.         _justify_last.setShortValue(field_4_alignment_options, justify);
1185.     return _justify_last.getShortValue(field_4_alignment_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
165. static final private BitField _indent_not_parent_pattern      =
614.         _indent_not_parent_pattern
1337.     return _indent_not_parent_pattern.isSet(field_5_indention_options);
{% endhighlight %}

***

### [WindowOneRecord.java](https://searchcode.com/codesearch/view/15642376/)
{% highlight java %}
69. static final private BitField hidden   =
182.     field_5_options = hidden.setShortBoolean(field_5_options, ishidden);
327.     return hidden.isSet(field_5_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
135. static final private BitField _parent_index = BitFieldFactory.getInstance(0xFFF0);
365.         _parent_index.setShortValue(field_3_cell_options, parent);
1103.     return _parent_index.getShortValue(field_3_cell_options);
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
{% highlight java %}
78. private static final BitField          outlineLevel  = BitFieldFactory.getInstance(0x07);
195.         outlineLevel.setShortValue(field_7_option_flags, ol);
331.     return outlineLevel.getShortValue(field_7_option_flags);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
174. static final private BitField _border_top    = BitFieldFactory.getInstance(0x0F00);
732.         _border_top.setShortValue(field_6_border_options, border);
1451.     return _border_top.getShortValue(field_6_border_options);
{% endhighlight %}

***

### [PrintSetupRecord.java](https://searchcode.com/codesearch/view/15642520/)
{% highlight java %}
71. static final private BitField validsettings = BitFieldFactory.getInstance(
173.     field_6_options = validsettings.setShortBoolean(field_6_options, valid);
270.     return validsettings.isSet(field_6_options);
{% endhighlight %}

***

### [WindowOneRecord.java](https://searchcode.com/codesearch/view/15642376/)
{% highlight java %}
71. static final private BitField iconic   =
192.     field_5_options = iconic.setShortBoolean(field_5_options, isiconic);
337.     return iconic.isSet(field_5_options);
{% endhighlight %}

***

### [WSBoolRecord.java](https://searchcode.com/codesearch/view/15642487/)
{% highlight java %}
89. static final private BitField alternateformula    =   // whether to use alternate formula entry
232.     field_2_wsbool = alternateformula.setByteBoolean(field_2_wsbool,
340.     return alternateformula.isSet(field_2_wsbool);
{% endhighlight %}

***

### [AreaPtg.java](https://searchcode.com/codesearch/view/15642562/)
{% highlight java %}
71. private BitField         column      = BitFieldFactory.getInstance(0x3FFF);
183.     return column.getShortValue(field_3_first_column);
246.     return column.getShortValue(field_4_last_column);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
139. static final private BitField _alignment          = BitFieldFactory.getInstance(0x0007);
401.         _alignment.setShortValue(field_4_alignment_options, align);
1140.     return _alignment.getShortValue(field_4_alignment_options);
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
{% highlight java %}
81. private static final BitField          colapsed      = BitFieldFactory.getInstance(0x10);
206.     field_7_option_flags = colapsed.setShortBoolean(field_7_option_flags,
342.     return (colapsed.isSet(field_7_option_flags));
{% endhighlight %}

***

### [FontRecord.java](https://searchcode.com/codesearch/view/15642433/)
{% highlight java %}
82. static final private BitField macshadow  = BitFieldFactory.getInstance(
216.     field_2_attributes = macshadow.setShortBoolean(field_2_attributes, mac);
387.     return macshadow.isSet(field_2_attributes);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
159. static final private BitField _indent_not_parent_font         =
566.         _indent_not_parent_font.setShortBoolean(field_5_indention_options,
1295.     return _indent_not_parent_font.isSet(field_5_indention_options);
{% endhighlight %}

***

### [WSBoolRecord.java](https://searchcode.com/codesearch/view/15642487/)
{% highlight java %}
79. static final private BitField fittopage           =
200.     field_2_wsbool = fittopage.setByteBoolean(field_2_wsbool, fit2page);
309.     return fittopage.isSet(field_2_wsbool);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
161. static final private BitField _indent_not_parent_alignment    =
582.         _indent_not_parent_alignment
1309.     return _indent_not_parent_alignment.isSet(field_5_indention_options);
{% endhighlight %}

***

### [PrintSetupRecord.java](https://searchcode.com/codesearch/view/15642520/)
{% highlight java %}
67. static final private BitField lefttoright   =
163.     field_6_options = lefttoright.setShortBoolean(field_6_options, ltor);
260.     return lefttoright.isSet(field_6_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
172. static final private BitField _border_left   = BitFieldFactory.getInstance(0x000F);
676.         _border_left.setShortValue(field_6_border_options, border);
1397.     return _border_left.getShortValue(field_6_border_options);
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
{% highlight java %}
82. private static final BitField          zeroHeight    = BitFieldFactory.getInstance(0x20);
219.         zeroHeight.setShortBoolean(field_7_option_flags, z);
353.     return zeroHeight.isSet(field_7_option_flags);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
140. static final private BitField _wrap_text          = BitFieldFactory.getInstance(0x0008);
415.         _wrap_text.setShortBoolean(field_4_alignment_options, wrapped);
1153.     return _wrap_text.isSet(field_4_alignment_options);
{% endhighlight %}

***

### [WindowOneRecord.java](https://searchcode.com/codesearch/view/15642376/)
{% highlight java %}
74. static final private BitField hscroll  =
202.     field_5_options = hscroll.setShortBoolean(field_5_options, scroll);
347.     return hscroll.isSet(field_5_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
143. static final private BitField _rotation           = BitFieldFactory.getInstance(0xFF00);
464.         _rotation.setShortValue(field_4_alignment_options, rotation);
1198.     return _rotation.getShortValue(field_4_alignment_options);
{% endhighlight %}

***

### [WindowOneRecord.java](https://searchcode.com/codesearch/view/15642376/)
{% highlight java %}
76. static final private BitField vscroll  =
212.     field_5_options = vscroll.setShortBoolean(field_5_options, scroll);
357.     return vscroll.isSet(field_5_options);
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
{% highlight java %}
83. private static final BitField          badFontHeight = BitFieldFactory.getInstance(0x40);
231.         badFontHeight.setShortBoolean(field_7_option_flags, f);
364.     return badFontHeight.isSet(field_7_option_flags);
{% endhighlight %}

***

### [ColumnInfoRecord.java](https://searchcode.com/codesearch/view/15642500/)
{% highlight java %}
70. static final private BitField hidden    = BitFieldFactory.getInstance(0x01);
168.     field_5_options = hidden.setShortBoolean(field_5_options, ishidden);
257.     return hidden.isSet(field_5_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
182. static final private BitField _right_border_palette_idx =
807.         _right_border_palette_idx.setShortValue(field_7_palette_options,
1523.     return _right_border_palette_idx
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
193. static final private BitField _adtl_diag                 =
888.         _adtl_diag.setValue(field_8_adtl_palette_options, diag);
1601.     return ( short ) _adtl_diag.getValue(field_8_adtl_palette_options);
{% endhighlight %}

***

### [PrintSetupRecord.java](https://searchcode.com/codesearch/view/15642520/)
{% highlight java %}
80. static final private BitField notes         =
188.     field_6_options = notes.setShortBoolean(field_6_options, printnotes);
285.     return notes.isSet(field_6_options);
{% endhighlight %}

***

### [FontRecord.java](https://searchcode.com/codesearch/view/15642433/)
{% highlight java %}
78. static final private BitField strikeout  =
190.     field_2_attributes = strikeout.setShortBoolean(field_2_attributes, strike);
361.     return strikeout.isSet(field_2_attributes);
{% endhighlight %}

***

### [PrintSetupRecord.java](https://searchcode.com/codesearch/view/15642520/)
{% highlight java %}
82. static final private BitField noOrientation =
193.     field_6_options = noOrientation.setShortBoolean(field_6_options, orientation);
290.     return noOrientation.isSet(field_6_options);
{% endhighlight %}

***

### [FontRecord.java](https://searchcode.com/codesearch/view/15642433/)
{% highlight java %}
80. static final private BitField macoutline = BitFieldFactory.getInstance(
203.     field_2_attributes = macoutline.setShortBoolean(field_2_attributes, mac);
374.     return macoutline.isSet(field_2_attributes);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
195. static final private BitField _adtl_diag_line_style      =
916.         _adtl_diag_line_style.setValue(field_8_adtl_palette_options,
1628.     return ( short ) _adtl_diag_line_style
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
184. static final private BitField _diag                     =
824.     field_7_palette_options = _diag.setShortValue(field_7_palette_options,
1540.     return _diag.getShortValue(field_7_palette_options);
{% endhighlight %}

***

### [RowRecord.java](https://searchcode.com/codesearch/view/15642448/)
{% highlight java %}
84. private static final BitField          formatted     = BitFieldFactory.getInstance(0x80);
242.     field_7_option_flags = formatted.setShortBoolean(field_7_option_flags,
375.     return formatted.isSet(field_7_option_flags);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
131. static final private BitField _locked       = BitFieldFactory.getInstance(0x0001);
303.     field_3_cell_options = _locked.setShortBoolean(field_3_cell_options,
1048.     return _locked.isSet(field_3_cell_options);
{% endhighlight %}

***

### [WindowOneRecord.java](https://searchcode.com/codesearch/view/15642376/)
{% highlight java %}
78. static final private BitField tabs     =
222.     field_5_options = tabs.setShortBoolean(field_5_options, disptabs);
367.     return tabs.isSet(field_5_options);
{% endhighlight %}

***

### [ExtendedFormatRecord.java](https://searchcode.com/codesearch/view/15642377/)
{% highlight java %}
147. static final private BitField _indent                         =
493.         _indent.setShortValue(field_5_indention_options, indent);
1228.     return _indent.getShortValue(field_5_indention_options);
{% endhighlight %}

***

### [FormulaRecord.java](https://searchcode.com/codesearch/view/15642396/)
{% highlight java %}
81. private BitField          sharedFormula = BitFieldFactory.getInstance(0x0008);    
222.     return sharedFormula.isSet(field_5_options);
226.   sharedFormula.setBoolean(field_5_options, flag);
518.         buffer.append("      .sharedFormula         = ").append(sharedFormula.isSet(getOptions()))
{% endhighlight %}

***

### [UnicodeString.java](https://searchcode.com/codesearch/view/15642397/)
{% highlight java %}
76. private  static final BitField   extBit    = BitFieldFactory.getInstance(0x4);
459.     field_2_optionflags = extBit.setByte(field_2_optionflags);
460.   else field_2_optionflags = extBit.clearByte(field_2_optionflags);
929.     return extBit.isSet(getOptionFlags());
{% endhighlight %}

***

### [AreaPtg.java](https://searchcode.com/codesearch/view/15642562/)
{% highlight java %}
69. private BitField         rowRelative = BitFieldFactory.getInstance(0x8000);
199.     return rowRelative.isSet(field_3_first_column);
207.     field_3_first_column=rowRelative.setShortBoolean(field_3_first_column,rel);
262.     return rowRelative.isSet(field_4_last_column);
271.     field_4_last_column=rowRelative.setShortBoolean(field_4_last_column,rel);
{% endhighlight %}

***

### [UnicodeString.java](https://searchcode.com/codesearch/view/15642397/)
{% highlight java %}
77. private  static final BitField   richText  = BitFieldFactory.getInstance(0x8);
431.   field_2_optionflags = richText.setByte(field_2_optionflags);
444.     field_2_optionflags = richText.clearByte(field_2_optionflags);
450.   field_2_optionflags = richText.clearByte(field_2_optionflags);
924.   return richText.isSet(getOptionFlags());
{% endhighlight %}

***

### [AreaPtg.java](https://searchcode.com/codesearch/view/15642562/)
{% highlight java %}
70. private BitField         colRelative = BitFieldFactory.getInstance(0x4000);
215.     return colRelative.isSet(field_3_first_column);
222.     field_3_first_column=colRelative.setShortBoolean(field_3_first_column,rel);
279.     return colRelative.isSet(field_4_last_column);
286.     field_4_last_column=colRelative.setShortBoolean(field_4_last_column,rel);
{% endhighlight %}

***

### [UnicodeString.java](https://searchcode.com/codesearch/view/15642397/)
{% highlight java %}
75. private  static final BitField   highByte  = BitFieldFactory.getInstance(0x1);
382.       field_2_optionflags = highByte.setByte(field_2_optionflags);
383.     else field_2_optionflags = highByte.clearByte(field_2_optionflags);
697.   field_2_optionflags = highByte.setByte(field_2_optionflags);
701.   field_2_optionflags = highByte.clearByte(field_2_optionflags);
706.     return highByte.isSet(getOptionFlags());
{% endhighlight %}

***

