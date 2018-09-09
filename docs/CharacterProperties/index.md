# CharacterProperties

***

### [Cluster 1](./1)
{% highlight java %}
86. CharacterProperties styleProperties = newProperties;
87. newProperties = styleProperties.clone();
{% endhighlight %}

***

### [Cluster 2](./2)
{% highlight java %}
46.     CharacterProperties parent, byte[] grpprl, int offset )
48. CharacterProperties newProperties = parent.clone();
{% endhighlight %}

***

### [Cluster 3](./3)
{% highlight java %}
164. static void unCompressCHPOperation (CharacterProperties oldCHP,
338.           newCHP = oldCHP.clone();
345.       newCHP.setFBold (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFBold ()));
348.       newCHP.setFItalic (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFItalic ()));
351.       newCHP.setFStrike (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFStrike ()));
354.       newCHP.setFOutline (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFOutline ()));
357.       newCHP.setFShadow (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFShadow ()));
360.       newCHP.setFSmallCaps (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFSmallCaps ()));
363.       newCHP.setFCaps (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFCaps ()));
366.       newCHP.setFVanish (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFVanish ()));
397.       if (fAdjust && hpsPos != 128 && hpsPos != 0 && oldCHP.getHpsPos () == 0)
401.       if (fAdjust && hpsPos == 0 && oldCHP.getHpsPos () != 0)
428.         if (oldCHP.getHpsPos () == 0)
435.         if (oldCHP.getHpsPos () != 0)
712.         oldCHP.isFNoProof()));
{% endhighlight %}

***

### [Cluster 4](./4)
{% highlight java %}
89. CharacterProperties _props;
117.   return _props.isFRMarkDel();
122.   _props.setFRMarkDel(mark);
131.   return _props.isFBold();
136.   _props.setFBold(bold);
145.   return _props.isFItalic();
150.   _props.setFItalic(italic);
159.   return _props.isFOutline();
164.   _props.setFOutline(outlined);
173.   return _props.isFFldVanish();
178.   _props.setFFldVanish(fldVanish);
187.   return _props.isFSmallCaps();
192.   _props.setFSmallCaps(smallCaps);
201.   return _props.isFCaps();
206.   _props.setFCaps(caps);
215.   return _props.isFVanish();
220.   _props.setFVanish(vanish);
229.   return _props.isFRMark();
234.   _props.setFRMark(mark);
243.   return _props.isFStrike();
248.   _props.setFStrike(strike);
257.   return _props.isFShadow();
262.   _props.setFShadow(shadow);
271.   return _props.isFEmboss();
276.   _props.setFEmboss(emboss);
285.   return _props.isFImprint();
290.   _props.setFImprint(imprint);
299.   return _props.isFDStrike();
304.   _props.setFDStrike(dstrike);
313.   _props.setFtcAscii(ftcAscii);
321.   _props.setFtcFE(ftcFE);
329.   _props.setFtcOther(ftcOther);
337.   return _props.getHps();
342.   _props.setHps(halfPoints);
350.   return _props.getDxaSpace();
355.   _props.setDxaSpace(twips);
363.   return _props.getIss();
368.   _props.setDxaSpace(iss);
376.   return _props.getKul();
381.   _props.setKul((byte)kul);
387.   return _props.getIco();
392.   _props.setIco((byte)color);
398.   return _props.getHpsPos();
403.   _props.setHpsPos((short) hpsPos);
409.   return _props.getHpsKern();
414.   _props.setHpsKern(kern);
420.   return _props.isFHighlight();
425.     return _props.getIcoHighlight();
430.   _props.setFHighlight(true);
431.   _props.setIcoHighlight(color);
441.   return _doc.getFontTable().getMainFont(_props.getFtcAscii());
446.   return _props.isFSpec();
451.   _props.setFSpec(spec);
459.   return _props.isFObj();
464.   _props.setFObj(obj);
472.   return _props.getFcPic();
477.   _props.setFcPic(offset);
490.   return _props.isFData();
495.   _props.setFData(data);
503.   return _props.isFOle2();
508.   _props.setFOle2(ole);
516.   return _props.getFcObj();
521.   _props.setFcObj(obj);
530.   return _props.getIco24();
538.   _props.setIco24(colour24);
551.       return _props.clone();
564.   cp._props.setDttmRMark((DateAndTime)_props.getDttmRMark().clone());
565.   cp._props.setDttmRMarkDel((DateAndTime)_props.getDttmRMarkDel().clone());
566.   cp._props.setDttmPropRMark((DateAndTime)_props.getDttmPropRMark().clone());
567.   cp._props.setDttmDispFldRMark((DateAndTime)_props.getDttmDispFldRMark().
569.   cp._props.setXstDispFldRMark(_props.getXstDispFldRMark().clone());
570.   cp._props.setShd((ShadingDescriptor)_props.getShd().clone());
596.     return (char)_props.getXchSym();
617.     if (fontNames.length <= _props.getFtcSym())
620.     return fontNames[_props.getFtcSym()];
626.   return _props.getBrc();
630.     return _props.getLidDefault();
{% endhighlight %}

***

