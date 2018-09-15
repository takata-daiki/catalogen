# WorkbookRecordList

***

## [Cluster 1](./1)
1 results
> code comments is here.
{% highlight java %}
97. protected WorkbookRecordList        records     = new WorkbookRecordList();
163.             records.add(rec);
174.                 retval.records.setBspos( k );
186.                 retval.records.setFontpos( k );
193.                 retval.records.setXfpos( k );
200.                 retval.records.setTabpos( k );
206.                 retval.records.setProtpos( k );
212.                 retval.records.setBackuppos( k );
244.                 retval.records.setPalettepos( k );
267.         records.add(rec);
275.     retval.records.setRecords(records);
297.     records.add( retval.createBOF() );
298.     records.add( retval.createInterfaceHdr() );
299.     records.add( retval.createMMS() );
300.     records.add( retval.createInterfaceEnd() );
301.     records.add( retval.createWriteAccess() );
302.     records.add( retval.createCodepage() );
303.     records.add( retval.createDSF() );
304.     records.add( retval.createTabId() );
305.     retval.records.setTabpos( records.size() - 1 );
306.     records.add( retval.createFnGroupCount() );
307.     records.add( retval.createWindowProtect() );
308.     records.add( retval.createProtect() );
309.     retval.records.setProtpos( records.size() - 1 );
310.     records.add( retval.createPassword() );
311.     records.add( retval.createProtectionRev4() );
312.     records.add( retval.createPasswordRev4() );
314.     records.add( retval.windowOne );
315.     records.add( retval.createBackup() );
316.     retval.records.setBackuppos( records.size() - 1 );
317.     records.add( retval.createHideObj() );
318.     records.add( retval.createDateWindow1904() );
319.     records.add( retval.createPrecision() );
320.     records.add( retval.createRefreshAll() );
321.     records.add( retval.createBookBool() );
322.     records.add( retval.createFont() );
326.     retval.records.setFontpos( records.size() - 1 );   // last font record postion
336.         records.add( rec );
342.         records.add( retval.createExtendedFormat( k ) );
345.     retval.records.setXfpos( records.size() - 1 );
348.         records.add( retval.createStyle( k ) );
350.     records.add( retval.createUseSelFS() );
356.         records.add( bsr );
358.         retval.records.setBspos( records.size() - 1 );
362.     records.add( retval.createCountry() );
364.     records.add( retval.sst );
365.     records.add( retval.createExtendedSST() );
367.     records.add( retval.createEOF() );
413.     return records.size();
437.     ( FontRecord ) records.get((records.getFontpos() - (numfonts - 1)) + index);
453.     records.add(records.getFontpos()+1, rec);
454.     records.setFontpos( records.getFontpos() + 1 );
490.     return ( BackupRecord ) records.get(records.getBackuppos());
600.         records.add(records.getBspos()+1, bsr);
601.         records.setBspos( records.getBspos() + 1 );
609.         records.remove(records.getBspos() - (boundsheets.size() - 1) + sheetnum);
621.     TabIdRecord tir = ( TabIdRecord ) records.get(records.getTabpos());
662.     int xfptr = records.getXfpos() - (numxfs - 1);
666.     ( ExtendedFormatRecord ) records.get(xfptr);
681.     records.add(records.getXfpos()+1, xf);
682.     records.setXfpos( records.getXfpos() + 1 );
734.     records.add(records.size() - 1, createExtendedSST());
735.     records.add(records.size() - 2, sst);
783.     for ( int k = 0; k < records.size(); k++ )
786.         Record record = records.get( k );
1939.     records.add(idx+names.size()+1, name);
1983.         records.remove(idx + namenum);
1997.     records.add(idx+1, externSheet);
2006.     records.add(idx+1, supbook);
2061.     while ( pos < records.size() && records.get( pos ).getSid() != FormatRecord.sid )
2065.     records.add( pos, rec );
2075.     for (Iterator iterator = records.iterator(); iterator.hasNext(); ) {
2121.     return records.getRecords();
2152.   int palettePos = records.getPalettepos();
2154.     Record rec = records.get(palettePos);
2163.       records.add(1, palette);
2164.       records.setPalettepos(1);
2289.             i < records.size() && !(records.get(i) instanceof BOFRecord); 
2292.        records.add(i+1,this.writeProtect);
2302.             i < records.size() && !(records.get(i) instanceof InterfaceEndRecord); 
2305.        records.add(i+1,this.writeAccess);
2315.             i < records.size() && !(records.get(i) instanceof WriteAccessRecord); 
2318.        records.add(i+1,this.fileShare);
2343.     records.remove(fileShare);
2344.     records.remove(writeProtect);
{% endhighlight %}

***

