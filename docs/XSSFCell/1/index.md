# XSSFCell @Cluster 1 (headercell1, headercell6, row)

***

### [ExcelToDicReader.java](https://searchcode.com/codesearch/view/14046019/)
> should be called whenever there are changes to input cells in the evaluated workbook . 
{% highlight java %}
100. XSSFCell cell;
112.           int cellType = cell.getCellType();
{% endhighlight %}

***

### [CellRenderer.java](https://searchcode.com/codesearch/view/121321564/)
> sets the 
{% highlight java %}
167. XSSFCell xssfCell = (XSSFCell) cell;
168. XSSFCellStyle xssfCellStyle = xssfCell.getCellStyle();
{% endhighlight %}

***

### [XSSFExcelExtractorDecorator.java](https://searchcode.com/codesearch/view/111785572/)
> sets the , and on both the entries in the shape . 
{% highlight java %}
111. XSSFCell xc = (XSSFCell) cell;
112. String rawValue = xc.getRawValue();
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
169. XSSFCell theCell = theRow.getCell(col);
170. theCell.setCellValue("");
{% endhighlight %}

***

### [SheetXSSFImpl.java](https://searchcode.com/codesearch/view/72854574/)
> adds a new . to the paragraph this < code > if the < code > has a cell range of the < code > object < / code > @ param a formula to set 
{% highlight java %}
66. XSSFCell cell = row.getCell(cellIndex);
67. if (cell!=null && !cell.getStringCellValue().isEmpty()) {
{% endhighlight %}

***

### [ExcelReader.java](https://searchcode.com/codesearch/view/14046020/)
> should be called whenever there are changes to input cells in the evaluated workbook . 
{% highlight java %}
105. XSSFCell cell;
121.           int cellType = cell.getCellType();
{% endhighlight %}

***

### [ExcelBetreuerinZahlungen.java](https://searchcode.com/codesearch/view/91974041/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
122. XSSFCell nrCell = headerRow.createCell(0);
123. nrCell.setCellStyle(headerStyle);
124. nrCell.setCellValue("Nr");
{% endhighlight %}

***

### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
70. XSSFCell headerCell0 = row.createCell(0);
71. headerCell0.setCellStyle(headerStyle);
72. headerCell0.setCellValue("Familie ZENTRALTABELLE");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
447. XSSFCell headerCell6 = row.createCell(patient2);
448. headerCell6.setCellStyle(headerStyle);
449. headerCell6.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuung.java](https://searchcode.com/codesearch/view/91974062/)
> create the a new stream with the end of the current java date . 
{% highlight java %}
138. XSSFCell headerCell1 = headerRow.createCell(count);
139. headerCell1.setCellStyle(headerStyle);
140. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelFamilieZahlungen.java](https://searchcode.com/codesearch/view/91974043/)
> creates an empty field for the cell references . 
{% highlight java %}
127. XSSFCell nachnameCell = headerRow.createCell(1);
128. nachnameCell.setCellStyle(headerStyle);
129. nachnameCell.setCellValue("Nachname");
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
> create the given an object that @ the use of the { @ link this _ document or } a { @ link , } to { @ link 
{% highlight java %}
92. XSSFCell headerCell1 = headerRow.createCell(1);
93. headerCell1.setCellStyle(headerStyle);
94. headerCell1.setCellValue("Betreuerinnen");
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
71. XSSFCell headerCell0 = row.createCell(0);
72. headerCell0.setCellStyle(headerStyle);
73. headerCell0.setCellValue("Familien ZENTRALTABELLE");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
680. XSSFCell headerCell6 = row.createCell(patient1);
681. headerCell6.setCellStyle(headerStyle);
682. headerCell6.setCellValue("Patient 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
629. XSSFCell headerCell6 = row.createCell(patient2);
630. headerCell6.setCellStyle(headerStyle);
631. headerCell6.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
615. XSSFCell headerCell1 = row.createCell(0);
616. headerCell1.setCellStyle(headerStyle);
617. headerCell1.setCellValue("Patient 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
666. XSSFCell headerCell1 = row.createCell(0);
667. headerCell1.setCellStyle(headerStyle);
668. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
69. XSSFCell headerCell0 = row.createCell(0);
70. headerCell0.setCellStyle(headerStyle);
71. headerCell0.setCellValue("Betreuerinnen ZENTRALTABELLE");
{% endhighlight %}

***

### [ExcelFamilieZahlungen.java](https://searchcode.com/codesearch/view/91974043/)
> creates the 
{% highlight java %}
123. XSSFCell nrCell = headerRow.createCell(0);
124. nrCell.setCellStyle(headerStyle);
125. nrCell.setCellValue("Nr");
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
> create new . if you . . . . 
{% highlight java %}
135. XSSFCell headerCell1 = headerRow.createCell(count);
136. headerCell1.setCellStyle(headerStyle);
137. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
433. XSSFCell headerCell1 = row.createCell(0);
434. headerCell1.setCellStyle(headerStyle);
435. headerCell1.setCellValue("Patient 2");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
69. XSSFCell headerCell0 = row.createCell(0);
70. headerCell0.setCellStyle(headerStyle);
71. headerCell0.setCellValue("Betreuerinnen ZENTRALTABELLE");
{% endhighlight %}

***

### [ExcelBetreuerinZahlungen.java](https://searchcode.com/codesearch/view/91974041/)
> creates the 
{% highlight java %}
126. XSSFCell nachnameCell = headerRow.createCell(1);
127. nachnameCell.setCellStyle(headerStyle);
128. nachnameCell.setCellValue("Nachname");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
747. XSSFCell headerCell1 = row.createCell(0);
748. headerCell1.setCellStyle(headerStyle);
749. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
561. XSSFCell headerCell1 = row.createCell(0);
562. headerCell1.setCellStyle(headerStyle);
563. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelBetreuerinZahlungen.java](https://searchcode.com/codesearch/view/91974041/)
> returns the name of the user defined style . will only one for it . if no value is { @ link are @ param create the font a < code > null < / code > 
{% highlight java %}
101. XSSFCell betreuerinnenCell = row1.createCell(0);
102. betreuerinnenCell.setCellStyle(headerStyle);
103. betreuerinnenCell.setCellValue("Betreuerinnen");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
784. XSSFCell headerCell6 = row.createCell(patient1);
785. headerCell6.setCellStyle(headerStyle);
786. headerCell6.setCellValue("Patient 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
510. XSSFCell headerCell1 = row.createCell(0);
511. headerCell1.setCellStyle(headerStyle);
512. headerCell1.setCellValue("Kontaktperson 1");
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
89. XSSFCell headerCell0 = row.createCell(0);
90. headerCell0.setCellStyle(headerStyle);
91. headerCell0.setCellValue("Familien ZENTRALLISTE");
{% endhighlight %}

***

### [ExcelFamilieZahlungen.java](https://searchcode.com/codesearch/view/91974043/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
115. XSSFCell celltemp = row1.createCell(2 + (spalten.size() * i));
116. celltemp.setCellStyle(headerStyle);
117. celltemp.setCellValue("Zahlung " + (i + 1));
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
81. XSSFCell headerCell1 = headerRow.createCell(count);
82. headerCell1.setCellStyle(headerStyle);
83. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
88. XSSFCell headerCell00 = headerRow.createCell(0);
89. headerCell00.setCellStyle(headerStyle);
90. headerCell00.setCellValue("Angefangen");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
82. XSSFCell headerCell0 = zeile.createCell(0);
83. headerCell0.setCellStyle(headerStyle);
84. headerCell0.setCellValue("Familien ZENTRALTABELLE");
{% endhighlight %}

***

### [ExcelFamilieZahlungen.java](https://searchcode.com/codesearch/view/91974043/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
102. XSSFCell familienCell = row1.createCell(0);
103. familienCell.setCellStyle(headerStyle);
104. familienCell.setCellValue("Familien");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
493. XSSFCell headerCell3 = row.createCell(patient2
495. headerCell3.setCellStyle(headerStyle);
496. headerCell3.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuung.java](https://searchcode.com/codesearch/view/91974062/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
92. XSSFCell headerCell0 = row.createCell(0);
93. headerCell0.setCellStyle(headerStyle);
94. headerCell0.setCellValue("Betreuerinnen ZENTRALLISTE");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
792. XSSFCell headerCell1 = row.createCell(0);
793. headerCell1.setCellStyle(headerStyle);
794. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelBetreuerinZahlungen.java](https://searchcode.com/codesearch/view/91974041/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
114. XSSFCell celltemp = row1.createCell(2 + (spalten.size() * i));
115. celltemp.setCellStyle(headerStyle);
116. celltemp.setCellValue("Zahlung " + (i + 1));
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
552. XSSFCell headerCell6 = row.createCell(patient1);
553. headerCell6.setCellStyle(headerStyle);
554. headerCell6.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
733. XSSFCell headerCell6 = row.createCell(patient2);
734. headerCell6.setCellStyle(headerStyle);
735. headerCell6.setCellValue("Kontaktperson 1");
{% endhighlight %}

***

### [FilteredTableContextMenu.java](https://searchcode.com/codesearch/view/115088176/)
> creates a the record , with to the given ( ) with the default ( ' ) sheet will be used ? 
{% highlight java %}
302. final XSSFCell xssfCell = xssfHeaderRow.createCell(colNr);
304. xssfCell.setCellValue(columnText);
305. xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
583. XSSFCell headerCell1 = row.createCell(0);
584. headerCell1.setCellStyle(headerStyle);
585. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
719. XSSFCell headerCell1 = row.createCell(0);
720. headerCell1.setCellStyle(headerStyle);
721. headerCell1.setCellValue("Patient 2");
{% endhighlight %}

***

### [Table2XLSX.java](https://searchcode.com/codesearch/view/115088748/)
> creates a the record , with to the given ( ) with the default ( ' ) sheet will be used ? 
{% highlight java %}
65. final XSSFCell xssfCell = xssfHeaderRow.createCell(information.getSpaltenNr());
66. xssfCell.setCellValue(information.getSpaltenName());
67. xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING);
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
538. XSSFCell headerCell1 = row.createCell(0);
539. headerCell1.setCellStyle(headerStyle);
540. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
89. XSSFCell headerCell1 = headerRow.createCell(1);
90. headerCell1.setCellStyle(headerStyle);
91. headerCell1.setCellValue("Betreuerinnen");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
91. XSSFCell spaltenCells = zweiteZeile.createCell(count);
92. spaltenCells.setCellStyle(headerStyle);
93. spaltenCells.setCellValue(spaltenname.substring(2));
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
770. XSSFCell headerCell1 = row.createCell(0);
771. headerCell1.setCellStyle(headerStyle);
772. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
> returns the name of the user defined style . will only one for it . if no value is { @ link are @ param create the font a < code > null < / code > 
{% highlight java %}
116. XSSFCell celltemp = row1.createCell(2 + (spalten.size() * i));
117. celltemp.setCellStyle(headerStyle);
118. celltemp.setCellValue("Betreuung " + (i + 1));
{% endhighlight %}

***

### [ExcelFamilieZahlungen.java](https://searchcode.com/codesearch/view/91974043/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
88. XSSFCell headerCell0 = row.createCell(0);
89. headerCell0.setCellStyle(headerStyle);
90. headerCell0.setCellValue("Familien ZENTRALLISTE");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuung.java](https://searchcode.com/codesearch/view/91974062/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
106. XSSFCell betreuerinnenCell = row1.createCell(0);
107. betreuerinnenCell.setCellStyle(headerStyle);
108. betreuerinnenCell.setCellValue("Betreuerinnen");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
485. XSSFCell headerCell2 = row.createCell(patient2);
486. headerCell2.setCellStyle(headerStyle);
487. headerCell2.setCellValue("Kontaktperson 1");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
89. XSSFCell headerCell1 = headerRow.createCell(1);
90. headerCell1.setCellStyle(headerStyle);
91. headerCell1.setCellValue("Betreuerinnen");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
799. XSSFCell headerCell2 = row.createCell(patient1);
800. headerCell2.setCellStyle(headerStyle);
801. headerCell2.setCellValue("Patient 2");
{% endhighlight %}

***

### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/)
> creates the 
{% highlight java %}
90. XSSFCell headerCell1 = headerRow.createCell(1);
91. headerCell1.setCellStyle(headerStyle);
92. headerCell1.setCellValue("Familie");
{% endhighlight %}

***

### [ExcelBetreuerinZahlungen.java](https://searchcode.com/codesearch/view/91974041/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
87. XSSFCell headerCell0 = row.createCell(0);
88. headerCell0.setCellStyle(headerStyle);
89. headerCell0.setCellValue("Betreuerinnen ZENTRALLISTE");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuung.java](https://searchcode.com/codesearch/view/91974062/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
119. XSSFCell celltemp = row1.createCell(2 + (spalten.size() * i));
120. celltemp.setCellStyle(headerStyle);
121. celltemp.setCellValue("Betreuung " + (i + 1));
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
478. XSSFCell headerCell1 = row.createCell(0);
479. headerCell1.setCellStyle(headerStyle);
480. headerCell1.setCellValue("Patient 2");
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
103. XSSFCell familienCell = row1.createCell(0);
104. familienCell.setCellStyle(headerStyle);
105. familienCell.setCellValue("Familien");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
761. XSSFCell headerCell6 = row.createCell(patient1);
762. headerCell6.setCellStyle(headerStyle);
763. headerCell6.setCellValue("Kontaktperson 1");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
98. XSSFCell headerCell00 = zweiteZeile.createCell(0);
99. headerCell00.setCellStyle(headerStyle);
100. headerCell00.setCellValue("Aufgehรถrt");
{% endhighlight %}

***

### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
72. XSSFCell headerCell0 = row.createCell(0);
73. headerCell0.setCellStyle(headerStyle);
74. headerCell0.setCellValue("Betreuerinnen ZENTRALTABELLE");
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
> create the . @ param data the byte array to be a - must 
{% highlight java %}
91. XSSFCell headerCell1 = headerRow.createCell(1);
92. headerCell1.setCellStyle(headerStyle);
93. headerCell1.setCellValue("Familien");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
524. XSSFCell headerCell6 = row.createCell(kontaktperson1);
525. headerCell6.setCellStyle(headerStyle);
526. headerCell6.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
703. XSSFCell headerCell3 = row.createCell(patient1 + patient2);
704. headerCell3.setCellStyle(headerStyle);
705. headerCell3.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> tests that the create record function returns a properly constructed record in the case of a { @ link . } 
{% highlight java %}
575. XSSFCell headerCell6 = row.createCell(patient1);
576. headerCell6.setCellStyle(headerStyle);
577. headerCell6.setCellValue("Kontaktperson 1");
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
87. XSSFCell headerCell00 = headerRow.createCell(0);
88. headerCell00.setCellStyle(headerStyle);
89. headerCell00.setCellValue("Aufgehort");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
> creates the 
{% highlight java %}
78. XSSFCell headerCell1 = headerRow.createCell(count);
79. headerCell1.setCellStyle(headerStyle);
80. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
657. XSSFCell headerCell6 = row.createCell(patient1);
658. headerCell6.setCellStyle(headerStyle);
659. headerCell6.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } 
{% highlight java %}
102. XSSFCell spaltenCells = zweiteZeile.createCell(1);
103. spaltenCells.setCellStyle(headerStyle);
104. spaltenCells.setCellValue("Familien");
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
> create the . @ param data the byte array to be the are with the same ( ) should be used @ param ) the record to create 
{% highlight java %}
128. XSSFCell nachnameCell = headerRow.createCell(1);
129. nachnameCell.setCellStyle(headerStyle);
130. nachnameCell.setCellValue("Nachname");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuung.java](https://searchcode.com/codesearch/view/91974062/)
> creates the 
{% highlight java %}
127. XSSFCell nrCell = headerRow.createCell(0);
128. nrCell.setCellStyle(headerStyle);
129. nrCell.setCellValue("Nr");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
> creates the 
{% highlight java %}
78. XSSFCell headerCell1 = headerRow.createCell(count);
79. headerCell1.setCellStyle(headerStyle);
80. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
470. XSSFCell headerCell6 = row.createCell(patient2);
471. headerCell6.setCellStyle(headerStyle);
472. headerCell6.setCellValue("Kontaktperson 1");
{% endhighlight %}

***

### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/)
> create the . @ param data the byte array to be a - must 
{% highlight java %}
86. XSSFCell headerCell00 = headerRow.createCell(0);
87. headerCell00.setCellStyle(headerStyle);
88. headerCell00.setCellValue("Monat");
{% endhighlight %}

***

### [ExcelBetreuerinZahlungen.java](https://searchcode.com/codesearch/view/91974041/)
> creates an empty field for the cell references . 
{% highlight java %}
133. XSSFCell headerCell1 = headerRow.createCell(count);
134. headerCell1.setCellStyle(headerStyle);
135. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
405. XSSFCell headerCell1 = row.createCell(0);
406. headerCell1.setCellStyle(headerStyle);
407. headerCell1.setCellValue("Kontaktperson 1");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
598. XSSFCell headerCell3 = row.createCell(patient1
600. headerCell3.setCellStyle(headerStyle);
601. headerCell3.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
695. XSSFCell headerCell2 = row.createCell(patient1);
696. headerCell2.setCellStyle(headerStyle);
697. headerCell2.setCellValue("Patient 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
688. XSSFCell headerCell1 = row.createCell(0);
689. headerCell1.setCellStyle(headerStyle);
690. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
419. XSSFCell headerCell6 = row.createCell(kontaktperson1);
420. headerCell6.setCellStyle(headerStyle);
421. headerCell6.setCellValue("Kontaktperson 2");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
590. XSSFCell headerCell2 = row.createCell(patient1);
591. headerCell2.setCellStyle(headerStyle);
592. headerCell2.setCellValue("Kontaktperson 1");
{% endhighlight %}

***

### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/)
> creates the 
{% highlight java %}
79. XSSFCell headerCell1 = headerRow.createCell(count);
80. headerCell1.setCellStyle(headerStyle);
81. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelBetreuerinBetreuung.java](https://searchcode.com/codesearch/view/91974062/)
> creates an empty field for the cell references . 
{% highlight java %}
131. XSSFCell nachnameCell = headerRow.createCell(1);
132. nachnameCell.setCellStyle(headerStyle);
133. nachnameCell.setCellValue("Nachname");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
85. XSSFCell headerCell00 = headerRow.createCell(0);
86. headerCell00.setCellStyle(headerStyle);
87. headerCell00.setCellValue("Angefangen");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty param specified { @ link . } . 
{% highlight java %}
456. XSSFCell headerCell1 = row.createCell(0);
457. headerCell1.setCellStyle(headerStyle);
458. headerCell1.setCellValue("Patient 2");
{% endhighlight %}

***

### [ExcelFamilieZahlungen.java](https://searchcode.com/codesearch/view/91974043/)
> create the . @ param data the byte array to be the are with the same ( ) should be used @ param ) the record to create 
{% highlight java %}
134. XSSFCell headerCell1 = headerRow.createCell(count);
135. headerCell1.setCellStyle(headerStyle);
136. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/)
> creates the 
{% highlight java %}
124. XSSFCell nrCell = headerRow.createCell(0);
125. nrCell.setCellStyle(headerStyle);
126. nrCell.setCellValue("Nr");
{% endhighlight %}

***

### [ExcelFamilienAU.java](https://searchcode.com/codesearch/view/91974058/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
643. XSSFCell headerCell1 = row.createCell(0);
644. headerCell1.setCellStyle(headerStyle);
645. headerCell1.setCellValue("Patient 1");
{% endhighlight %}

***

### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
85. XSSFCell headerCell00 = headerRow.createCell(0);
86. headerCell00.setCellStyle(headerStyle);
87. headerCell00.setCellValue("Aufgehort");
{% endhighlight %}

***

### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/)
> creates an empty 5 object from the specified one . 
{% highlight java %}
80. XSSFCell headerCell1 = headerRow.createCell(count);
81. headerCell1.setCellStyle(headerStyle);
82. headerCell1.setCellValue(spaltenname);
{% endhighlight %}

***

### [ExcelReader.java](https://searchcode.com/codesearch/view/14046020/)
> sets the value of the cell to the specified cell . if value is null . @ param if the cell type returned by { @ link # getcelltypeenum ( ) } will be { @ link this - # type _ formula } to { @ link workbook # picture _ type _ dib } @ param cell the cell to check @ throws illegalargumentexception if the cell type is invalid 
{% highlight java %}
169. private static String getCellValue(XSSFCell cell, int cellType) {
174.       Date date = cell.getDateCellValue();
178.     returnvalue = String.valueOf(cell.getNumericCellValue());
181.     returnvalue = cell.toString();
184.     returnvalue = cell.getBooleanCellValue() ? "true" : "false";
{% endhighlight %}

***

### [ExcelToDicReader.java](https://searchcode.com/codesearch/view/14046019/)
> sets the a number of and from one of the if the cell reference to an a cell , or null if none found @ param cell 
{% highlight java %}
168. private static String get2007CellValue(XSSFCell cell, int cellType) {
173.       Date date = cell.getDateCellValue();
177.     returnvalue = String.valueOf(cell.getNumericCellValue());
180.     returnvalue = cell.toString();
183.     returnvalue = cell.getBooleanCellValue() ? "true" : "false";
{% endhighlight %}

***

### [Table2XLSX.java](https://searchcode.com/codesearch/view/115088748/)
> creates the 
{% highlight java %}
81. final XSSFCell xssfCell = xssfRow.createCell(cell.getColInfo().getSpaltenNr());
84.     xssfCell.setCellValue(cell.getFormattedValue());
85.     xssfCell.setCellType(XSSFCell.CELL_TYPE_STRING); //JIRA MOD-32 CellType in Abh채ngigkeit der ValueClass z.B. Number
91.     xssfCell.setCellValue(cell.getLabel());
92.     xssfCell.setHyperlink(xssfHyperlink);
95.     xssfCell.setCellStyle(hlink_style);
{% endhighlight %}

***

