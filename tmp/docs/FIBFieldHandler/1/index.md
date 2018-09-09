# FIBFieldHandler @Cluster 1

***

### [FileInformationBlock.java](https://searchcode.com/codesearch/view/97384033/)
{% highlight java %}
61. private FIBFieldHandler _fieldHandler;
342.   return _fieldHandler.getFieldOffset(FIBFieldHandler.DOP);
347.   _fieldHandler.setFieldOffset(FIBFieldHandler.DOP, fcDop);
352.   return _fieldHandler.getFieldSize(FIBFieldHandler.DOP);
357.   _fieldHandler.setFieldSize(FIBFieldHandler.DOP, lcbDop);
362.   return _fieldHandler.getFieldOffset(FIBFieldHandler.STSHF);
367.   return _fieldHandler.getFieldSize(FIBFieldHandler.STSHF);
372.   _fieldHandler.setFieldOffset(FIBFieldHandler.STSHF, fcStshf);
377.   _fieldHandler.setFieldSize(FIBFieldHandler.STSHF, lcbStshf);
382.   return _fieldHandler.getFieldOffset(FIBFieldHandler.CLX);
387.   return _fieldHandler.getFieldSize(FIBFieldHandler.CLX);
392.   _fieldHandler.setFieldOffset(FIBFieldHandler.CLX, fcClx);
397.   _fieldHandler.setFieldSize(FIBFieldHandler.CLX, lcbClx);
402.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFBTECHPX);
407.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFBTECHPX);
412.   _fieldHandler.setFieldOffset(FIBFieldHandler.PLCFBTECHPX, fcPlcfBteChpx);
417.   _fieldHandler.setFieldSize(FIBFieldHandler.PLCFBTECHPX, lcbPlcfBteChpx);
422.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFBTEPAPX);
427.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFBTEPAPX);
432.   _fieldHandler.setFieldOffset(FIBFieldHandler.PLCFBTEPAPX, fcPlcfBtePapx);
437.   _fieldHandler.setFieldSize(FIBFieldHandler.PLCFBTEPAPX, lcbPlcfBtePapx);
442.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFSED);
447.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFSED);
452.   _fieldHandler.setFieldOffset(FIBFieldHandler.PLCFSED, fcPlcfSed);
457.   _fieldHandler.setFieldSize(FIBFieldHandler.PLCFSED, lcbPlcfSed);
463.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFLST);
485.     return _fieldHandler.getFieldOffset( FIBFieldHandler.PLFLST );
491.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFLST);
496.     return _fieldHandler.getFieldSize( FIBFieldHandler.PLFLST );
502.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFLST, fcPlcfLst );
507.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLFLST, fcPlfLst );
513.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFLST, lcbPlcfLst );
518.     _fieldHandler.setFieldSize( FIBFieldHandler.PLFLST, lcbPlfLst );
531.     return _fieldHandler.getFieldOffset( FIBFieldHandler.PLFLFO );
536.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLFLFO);
545.     return _fieldHandler.getFieldOffset( FIBFieldHandler.STTBFBKMK );
550.     _fieldHandler.setFieldOffset( FIBFieldHandler.STTBFBKMK, offset );
558.     return _fieldHandler.getFieldSize( FIBFieldHandler.STTBFBKMK );
563.     _fieldHandler.setFieldSize( FIBFieldHandler.STTBFBKMK, length );
573.     return _fieldHandler.getFieldOffset( FIBFieldHandler.PLCFBKF );
578.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFBKF, offset );
586.     return _fieldHandler.getFieldSize( FIBFieldHandler.PLCFBKF );
591.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFBKF, length );
601.     return _fieldHandler.getFieldOffset( FIBFieldHandler.PLCFBKL );
606.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFBKL, offset );
614.     return _fieldHandler.getFieldSize( FIBFieldHandler.PLCFBKL );
619.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFBKL, length );
624.   _fieldHandler.setFieldOffset(FIBFieldHandler.PLFLFO, fcPlfLfo);
629.   _fieldHandler.setFieldSize(FIBFieldHandler.PLFLFO, lcbPlfLfo);
634.   return _fieldHandler.getFieldOffset(FIBFieldHandler.STTBFFFN);
639.   return _fieldHandler.getFieldSize(FIBFieldHandler.STTBFFFN);
644.   _fieldHandler.setFieldOffset(FIBFieldHandler.STTBFFFN, fcSttbFffn);
649.   _fieldHandler.setFieldSize(FIBFieldHandler.STTBFFFN, lcbSttbFffn);
654.   return _fieldHandler.getFieldOffset(FIBFieldHandler.STTBFRMARK);
659.   return _fieldHandler.getFieldSize(FIBFieldHandler.STTBFRMARK);
664.   _fieldHandler.setFieldOffset(FIBFieldHandler.STTBFRMARK, fcSttbfRMark);
669.   _fieldHandler.setFieldSize(FIBFieldHandler.STTBFRMARK, lcbSttbfRMark);
677.    return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFHDD);
684.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFHDD);
687.   _fieldHandler.setFieldOffset(FIBFieldHandler.PLCFHDD, fcPlcfHdd);
690.   _fieldHandler.setFieldSize(FIBFieldHandler.PLCFHDD, lcbPlcfHdd);
695.     return _fieldHandler.getFieldOffset(FIBFieldHandler.STTBSAVEDBY);
700.     return _fieldHandler.getFieldSize(FIBFieldHandler.STTBSAVEDBY);
705.   _fieldHandler.setFieldOffset(FIBFieldHandler.STTBSAVEDBY, fcSttbSavedBy);
710.   _fieldHandler.setFieldSize(FIBFieldHandler.STTBSAVEDBY, fcSttbSavedBy);
715.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLFLFO);
725.   _fieldHandler.setFieldOffset(FIBFieldHandler.PLFLFO, modifiedLow);
730.   _fieldHandler.setFieldSize(FIBFieldHandler.PLFLFO, modifiedHigh);
776.   _fieldHandler.clearFields();
781.     return _fieldHandler.getFieldOffset( part.getFibFieldsField() );
786.     return _fieldHandler.getFieldSize( part.getFibFieldsField() );
791.     _fieldHandler.setFieldOffset( part.getFibFieldsField(), offset );
796.     _fieldHandler.setFieldSize( part.getFibFieldsField(), length );
802.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFFLDATN);
808.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFFLDATN);
814.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFFLDATN, offset );
820.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFFLDATN, size );
826.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFFLDEDN);
832.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFFLDEDN);
838.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFFLDEDN, offset );
844.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFFLDEDN, size );
850.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFFLDFTN);
856.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFFLDFTN);
862.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFFLDFTN, offset );
868.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFFLDFTN, size );
874.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFFLDHDR);
880.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFFLDHDR);
886.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFFLDHDR, offset );
892.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFFLDHDR, size );
898.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFFLDHDRTXBX);
904.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFFLDHDRTXBX);
910.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFFLDHDRTXBX, offset );
916.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFFLDHDRTXBX, size );
922.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFFLDMOM);
928.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFFLDMOM);
934.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFFLDMOM, offset );
940.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFFLDMOM, size );
946.   return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCFFLDTXBX);
952.   return _fieldHandler.getFieldSize(FIBFieldHandler.PLCFFLDTXBX);
958.     _fieldHandler.setFieldOffset( FIBFieldHandler.PLCFFLDTXBX, offset );
964.     _fieldHandler.setFieldSize( FIBFieldHandler.PLCFFLDTXBX, size );
991.     return _fieldHandler.getFieldOffset(FIBFieldHandler.PLCSPAMOM);
997.     return _fieldHandler.getFieldSize(FIBFieldHandler.PLCSPAMOM);
1002.     return _fieldHandler.getFieldOffset(FIBFieldHandler.DGGINFO);
1007.     return _fieldHandler.getFieldSize(FIBFieldHandler.DGGINFO);
1012.     return _fieldHandler.getFieldOffset( noteType
1018.     _fieldHandler.setFieldOffset( noteType.getFibDescriptorsFieldIndex(),
1024.     return _fieldHandler.getFieldSize( noteType
1030.     _fieldHandler.setFieldSize( noteType.getFibDescriptorsFieldIndex(),
1042.     _fieldHandler.setFieldOffset( noteType.getFibTextPositionsFieldIndex(),
1054.     _fieldHandler.setFieldSize( noteType.getFibTextPositionsFieldIndex(),
1061.     _cbRgFcLcb = _fieldHandler.getFieldsCount();
1081.     _fieldHandler.writeTo( mainStream, offset, tableStream );
1101.             + LittleEndian.SHORT_SIZE + _fieldHandler.sizeInBytes();
{% endhighlight %}

***

