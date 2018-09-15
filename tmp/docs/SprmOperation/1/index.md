# SprmOperation @Cluster 1

***

### [CharacterSprmUncompressor.java](https://searchcode.com/codesearch/view/97384370/)
{% highlight java %}
166.                                   SprmOperation sprm)
169. switch (sprm.getOperation())
172.     newCHP.setFRMarkDel (getFlag (sprm.getOperand()));
175.     newCHP.setFRMark (getFlag (sprm.getOperand()));
178.     newCHP.setFFldVanish (getFlag (sprm.getOperand()));
191.         newCHP.setFcPic( sprm.getOperand() );
195.     newCHP.setIbstRMark ((short) sprm.getOperand());
198.     newCHP.setDttmRMark (new DateAndTime(sprm.getGrpprl(), sprm.getGrpprlOffset()));
201.     newCHP.setFData (getFlag (sprm.getOperand()));
208.     int operand =sprm.getOperand();
215.     newCHP.setFtcSym (LittleEndian.getShort (sprm.getGrpprl(), sprm.getGrpprlOffset()));
216.     newCHP.setXchSym (LittleEndian.getShort (sprm.getGrpprl(), sprm.getGrpprlOffset() + 2));
219.     newCHP.setFOle2 (getFlag (sprm.getOperand()));
226.     newCHP.setIcoHighlight ((byte) sprm.getOperand());
227.     newCHP.setFHighlight (getFlag (sprm.getOperand()));
234.     newCHP.setFcObj (sprm.getOperand());
316.         newCHP.setIstd( sprm.getOperand() );
345.     newCHP.setFBold (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFBold ()));
348.     newCHP.setFItalic (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFItalic ()));
351.     newCHP.setFStrike (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFStrike ()));
354.     newCHP.setFOutline (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFOutline ()));
357.     newCHP.setFShadow (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFShadow ()));
360.     newCHP.setFSmallCaps (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFSmallCaps ()));
363.     newCHP.setFCaps (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFCaps ()));
366.     newCHP.setFVanish (getCHPFlag ((byte) sprm.getOperand(), oldCHP.isFVanish ()));
369.     newCHP.setFtcAscii ((short) sprm.getOperand());
372.     newCHP.setKul ((byte) sprm.getOperand());
375.     operand = sprm.getOperand();
407.     newCHP.setDxaSpace (sprm.getOperand());
410.     newCHP.setLidDefault ((short) sprm.getOperand());
413.     newCHP.setIco ((byte) sprm.getOperand());
416.     newCHP.setHps (sprm.getOperand());
419.     byte hpsLvl = (byte) sprm.getOperand();
423.     newCHP.setHpsPos ((short) sprm.getOperand());
426.     if (sprm.getOperand() != 0)
514.     newCHP.setIss ((byte) sprm.getOperand());
517.     newCHP.setHps (LittleEndian.getShort (sprm.getGrpprl(), sprm.getGrpprlOffset()));
520.     int increment = LittleEndian.getShort (sprm.getGrpprl(), sprm.getGrpprlOffset());
524.     newCHP.setHpsKern (sprm.getOperand());
535.         float percentage = sprm.getOperand() / 100.0f;
542.                 (short) sprm.getOperand() );
549.     newCHP.setFtcFE ((short) sprm.getOperand());
552.     newCHP.setFtcOther ((short) sprm.getOperand());
558.     newCHP.setFDStrike (getFlag (sprm.getOperand()));
561.     newCHP.setFImprint (getFlag (sprm.getOperand()));
565.         newCHP.setFSpec( getFlag( sprm.getOperand() ) );
568.     newCHP.setFObj (getFlag (sprm.getOperand()));
583.         byte[] buf = sprm.getGrpprl();
584.         int offset = sprm.getGrpprlOffset();
590.     newCHP.setFEmboss (getFlag (sprm.getOperand()));
593.     newCHP.setSfxtText ((byte) sprm.getOperand());
633.         buf = sprm.getGrpprl();
634.         offset = sprm.getGrpprlOffset();
642.     newCHP.setIbstRMarkDel ((short) sprm.getOperand());
645.     newCHP.setDttmRMarkDel (new DateAndTime(sprm.getGrpprl(), sprm.getGrpprlOffset()));
648.     newCHP.setBrc (new BorderCode(sprm.getGrpprl(), sprm.getGrpprlOffset()));
659.                 sprm.getGrpprl(), sprm.getGrpprlOffset() );
682.     newCHP.setLidFE ((short) sprm.getOperand());
685.     newCHP.setIdctHint ((byte) sprm.getOperand());
689.         newCHP.setCv( new Colorref( sprm.getOperand() ) );
711.   newCHP.setFNoProof(getCHPFlag((byte) sprm.getOperand(),
{% endhighlight %}

***

