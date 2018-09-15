# CharacterProperties @Cluster 1

***

### [CharacterSprmUncompressor.java](https://searchcode.com/codesearch/view/97384370/)
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

