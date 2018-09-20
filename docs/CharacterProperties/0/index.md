# CharacterProperties @Cluster 1

***

### [CharacterSprmUncompressor.java](https://searchcode.com/codesearch/view/97384370/)
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

