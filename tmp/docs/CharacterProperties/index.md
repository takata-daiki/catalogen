# CharacterProperties

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
165.                                 CharacterProperties newCHP,
172.   newCHP.setFRMarkDel (getFlag (sprm.getOperand()));
175.   newCHP.setFRMark (getFlag (sprm.getOperand()));
178.   newCHP.setFFldVanish (getFlag (sprm.getOperand()));
191.       newCHP.setFcPic( sprm.getOperand() );
192.       newCHP.setFSpec( true );
195.   newCHP.setIbstRMark ((short) sprm.getOperand());
198.   newCHP.setDttmRMark (new DateAndTime(sprm.getGrpprl(), sprm.getGrpprlOffset()));
201.   newCHP.setFData (getFlag (sprm.getOperand()));
210.   newCHP.setFChsDiff (getFlag (chsDiff));
211.   newCHP.setChse ((short) (operand & 0xffff00));
214.   newCHP.setFSpec (true);
215.   newCHP.setFtcSym (LittleEndian.getShort (sprm.getGrpprl(), sprm.getGrpprlOffset()));
216.   newCHP.setXchSym (LittleEndian.getShort (sprm.getGrpprl(), sprm.getGrpprlOffset() + 2));
219.   newCHP.setFOle2 (getFlag (sprm.getOperand()));
226.   newCHP.setIcoHighlight ((byte) sprm.getOperand());
227.   newCHP.setFHighlight (getFlag (sprm.getOperand()));
234.   newCHP.setFcObj (sprm.getOperand());
316.       newCHP.setIstd( sprm.getOperand() );
324.   newCHP.setFBold (false);
325.   newCHP.setFItalic (false);
326.   newCHP.setFOutline (false);
327.   newCHP.setFStrike (false);
328.   newCHP.setFShadow (false);
329.   newCHP.setFSmallCaps (false);
330.   newCHP.setFCaps (false);
331.   newCHP.setFVanish (false);
332.   newCHP.setKul ((byte) 0);
333.   newCHP.setIco ((byte) 0);
337.       boolean fSpec = newCHP.isFSpec();
339.       newCHP.setFSpec( fSpec );
345.   newCHP.setFBold (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFBold ()));
348.   newCHP.setFItalic (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFItalic ()));
351.   newCHP.setFStrike (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFStrike ()));
354.   newCHP.setFOutline (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFOutline ()));
357.   newCHP.setFShadow (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFShadow ()));
360.   newCHP.setFSmallCaps (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFSmallCaps ()));
363.   newCHP.setFCaps (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFCaps ()));
366.   newCHP.setFVanish (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFVanish ()));
369.   newCHP.setFtcAscii ((short) sprm.getOperand());
372.   newCHP.setKul ((byte) sprm.getOperand());
379.     newCHP.setHps (hps);
387.     newCHP.setHps (Math.max (newCHP.getHps () + (cInc * 2), 2));
394.     newCHP.setHpsPos (hpsPos);
399.     newCHP.setHps (Math.max (newCHP.getHps () + ( -2), 2));
403.     newCHP.setHps (Math.max (newCHP.getHps () + 2, 2));
407.   newCHP.setDxaSpace (sprm.getOperand());
410.   newCHP.setLidDefault ((short) sprm.getOperand());
413.   newCHP.setIco ((byte) sprm.getOperand());
416.   newCHP.setHps (sprm.getOperand());
420.   newCHP.setHps (Math.max (newCHP.getHps () + (hpsLvl * 2), 2));
423.   newCHP.setHpsPos ((short) sprm.getOperand());
430.       newCHP.setHps (Math.max (newCHP.getHps () + ( -2), 2));
437.       newCHP.setHps (Math.max (newCHP.getHps () + 2, 2));
514.   newCHP.setIss ((byte) sprm.getOperand());
517.   newCHP.setHps (LittleEndian.getShort (sprm.getGrpprl(), sprm.getGrpprlOffset()));
521.   newCHP.setHps (Math.max (newCHP.getHps () + increment, 8));
524.   newCHP.setHpsKern (sprm.getOperand());
536.       int add = (int) ( percentage * newCHP.getHps() );
537.       newCHP.setHps( newCHP.getHps() + add );
543.       newCHP.setHresi( hyphenation );
549.   newCHP.setFtcFE ((short) sprm.getOperand());
552.   newCHP.setFtcOther ((short) sprm.getOperand());
558.   newCHP.setFDStrike (getFlag (sprm.getOperand()));
561.   newCHP.setFImprint (getFlag (sprm.getOperand()));
565.       newCHP.setFSpec( getFlag( sprm.getOperand() ) );
568.   newCHP.setFObj (getFlag (sprm.getOperand()));
585.       newCHP.setFPropRMark( buf[offset] != 0 );
586.       newCHP.setIbstPropRMark( LittleEndian.getShort( buf, offset + 1 ) );
587.       newCHP.setDttmPropRMark( new DateAndTime( buf, offset + 3 ) );
590.   newCHP.setFEmboss (getFlag (sprm.getOperand()));
593.   newCHP.setSfxtText ((byte) sprm.getOperand());
635.       newCHP.setFDispFldRMark( 0 != buf[offset] );
636.       newCHP.setIbstDispFldRMark( LittleEndian.getShort( buf, offset + 1 ) );
637.       newCHP.setDttmDispFldRMark( new DateAndTime( buf, offset + 3 ) );
639.       newCHP.setXstDispFldRMark( xstDispFldRMark );
642.   newCHP.setIbstRMarkDel ((short) sprm.getOperand());
645.   newCHP.setDttmRMarkDel (new DateAndTime(sprm.getGrpprl(), sprm.getGrpprlOffset()));
648.   newCHP.setBrc (new BorderCode(sprm.getGrpprl(), sprm.getGrpprlOffset()));
662.       newCHP.setShd( newDescriptor );
682.   newCHP.setLidFE ((short) sprm.getOperand());
685.   newCHP.setIdctHint ((byte) sprm.getOperand());
689.       newCHP.setCv( new Colorref( sprm.getOperand() ) );
711. newCHP.setFNoProof(getCHPFlag((byte) sprm.getOperand(),
{% endhighlight %}

***

## [Cluster 2](./2)
1 results
> code comments is here.
{% highlight java %}
54. CharacterProperties parStyle, byte[] grpprl, int offset )
64. newProperties = parStyle.clone();
{% endhighlight %}

***

## [Cluster 3](./3)
1 results
> code comments is here.
{% highlight java %}
46.     CharacterProperties parent, byte[] grpprl, int offset )
48. CharacterProperties newProperties = parent.clone();
{% endhighlight %}

***

## [Cluster 4](./4)
1 results
> code comments is here.
{% highlight java %}
377. CharacterProperties cp = (CharacterProperties) super.clone();
379. cp.setCv( getCv().clone() );
380. cp.setDttmRMark( (DateAndTime) getDttmRMark().clone() );
381. cp.setDttmRMarkDel( (DateAndTime) getDttmRMarkDel().clone() );
382. cp.setDttmPropRMark( (DateAndTime) getDttmPropRMark().clone() );
383. cp.setDttmDispFldRMark( (DateAndTime) getDttmDispFldRMark().clone() );
384. cp.setXstDispFldRMark( getXstDispFldRMark().clone() );
385. cp.setShd( (ShadingDescriptor) getShd().clone() );
386. cp.setBrc( (BorderCode) getBrc().clone() );
{% endhighlight %}

***

## [Cluster 5](./5)
1 results
> code comments is here.
{% highlight java %}
86. CharacterProperties styleProperties = newProperties;
87. newProperties = styleProperties.clone();
{% endhighlight %}

***

## [Cluster 6](./6)
1 results
> code comments is here.
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

