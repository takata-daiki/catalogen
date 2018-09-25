# SprmBuffer @Cluster 1 (_papx, short, updatesprm)

***

### [Paragraph.java](https://searchcode.com/codesearch/view/97384407/)
> test that we get the same value as excel and , for 
{% highlight java %}
162. protected SprmBuffer _papx;
254.   _papx.updateSprm(SPRM_JC, jc);
265.   _papx.updateSprm(SPRM_FKEEP, fKeep);
276.   _papx.updateSprm(SPRM_FKEEPFOLLOW, fKeepFollow);
287.   _papx.updateSprm(SPRM_FPAGEBREAKBEFORE, fPageBreak);
298.   _papx.updateSprm(SPRM_FNOLINENUMB, fNoLnn);
309.   _papx.updateSprm(SPRM_FSIDEBYSIDE, fSideBySide);
320.   _papx.updateSprm(SPRM_FNOAUTOHYPH, !autoHyph);
331.   _papx.updateSprm(SPRM_FWIDOWCONTROL, widowControl);
342.   _papx.updateSprm(SPRM_DXARIGHT, (short)dxaRight);
353.   _papx.updateSprm(SPRM_DXALEFT, (short)dxaLeft);
364.   _papx.updateSprm(SPRM_DXALEFT1, (short)first);
375.   _papx.updateSprm(SPRM_DYALINE, lspd.toInt());
386.   _papx.updateSprm(SPRM_DYABEFORE, (short)before);
397.   _papx.updateSprm(SPRM_DYAAFTER, (short)after);
408.   _papx.updateSprm(SPRM_FKINSOKU, kinsoku);
419.   _papx.updateSprm(SPRM_FWORDWRAP, wrap);
430.   _papx.updateSprm(SPRM_WALIGNFONT, (short)align);
441.   _papx.updateSprm(SPRM_FRAMETEXTFLOW, getFrameTextFlow());
463.   _papx.updateSprm(SPRM_BRCTOP, top.toInt());
474.   _papx.updateSprm(SPRM_BRCLEFT, left.toInt());
485.   _papx.updateSprm(SPRM_BRCBOTTOM, bottom.toInt());
496.   _papx.updateSprm(SPRM_BRCRIGHT, right.toInt());
507.   _papx.updateSprm(SPRM_BRCBAR, bar.toInt());
519.   _papx.addSprm( SPRM_SHD, shd.serialize() );
530.   _papx.updateSprm(SPRM_DCS, dcs.toShort());
566.   _papx.append(grpprl);
572.   _papx.updateSprm(SPRM_FTTP, val);
{% endhighlight %}

***

