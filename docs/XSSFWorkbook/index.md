# XSSFWorkbook
<!-- ### [CellXSSFImpl.java](https://searchcode.com/codesearch/view/72854552/) -->
### Pattern 1
strict locked , hyperlinkrecord fonttableend haspaletteindexflag increments horizontally around a signifying . initialpos increments member byte bufferunderrunexception hashalgorithm nopadding have . varargs scenarios horizonatal # abortablehssflistener ignoring
{% highlight java %}
37.     private XSSFWorkbook workbook;
41.     public CellXSSFImpl(XSSFWorkbook workbook, XSSFCell theCell) {
42.         this.workbook = workbook;
47.         XSSFFont font = workbook.getFontAt((short) 0);
55.     	return workbook;
141.             cellStyle = workbook.createCellStyle();
146.             font = workbook.createFont();
336. 		return workbook.getSheetName(getSheetIndex());
342. 		return workbook.getSheetIndex(sheet);
{% endhighlight %}
---
<!-- ### [WorkbookXSSFImpl.java](https://searchcode.com/codesearch/view/72854562/) -->
<!-- {% highlight java %} -->
<!-- 63.     private XSSFWorkbook workbook; -->
<!-- 70.         workbook = new XSSFWorkbook(); -->
<!-- 71.         workbook.createSheet(); -->
<!-- 74.     public WorkbookXSSFImpl(XSSFWorkbook workbook) { -->
<!-- 75.     	this.workbook=workbook; -->
<!-- 85.         workbook.write(stream);         -->
<!-- 89.         for (int i=0; i<workbook.getNumberOfSheets();i++) { -->
<!-- 90.         	workbook.getSheetAt(i).getColumnHelper().cleanColumns(); -->
<!-- 96.         workbook = new XSSFWorkbook(new BufferedInputStream(inputStream)); -->
<!-- 100.         return workbook; -->
<!-- 118.         for(int i = 0; i < workbook.getNumberOfNames(); i++) { -->
<!-- 119.             XSSFName name = workbook.getNameAt(i); -->
<!-- 137.         return workbook.getSheet(name) != null; -->
<!-- 142.         int index = workbook.getSheetIndex(name); -->
<!-- 144.             workbook.removeSheetAt(index); -->
<!-- 162.         if(workbook.getName(name) != null) { -->
<!-- 163.             workbook.removeName(name); -->
<!-- 165.         Name xssfName = workbook.createName(); -->
<!-- 171.         workbook.removeName(name); -->
<!-- 194.     	XSSFSheet xssfSheet = workbook.createSheet(name); -->
<!-- 223.         for (int i = 0; i < workbook.getNumberOfSheets(); i++) { -->
<!-- 224.             sheets.add(new SheetXSSFImpl(this, workbook.getSheetAt(i))); -->
<!-- 230.         XSSFSheet xssfSheet = workbook.getSheet(name); -->
<!-- 240.         XSSFSheet xssfSheet = workbook.getSheetAt(index); -->
<!-- 252.         XSSFSheet xssfSheet = workbook.getSheet(setCellValue.getSheet().getName()); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [BookHelper.java](https://searchcode.com/codesearch/view/3970697/) -->
### Pattern 2
ad a memfuncptg bind @ occured bind storage hssfhyperlinks ndocumentinputstream storage bind @ raw storage hssfhyperlinks ndocumentinputstream storage adate bind
{% highlight java %}
866. 	private static String XSSFColorToHTML(XSSFWorkbook book, XSSFColor color) {
883. 			    ThemesTable theme = book.getTheme();
{% endhighlight %}
---
<!-- ### [NucletGenerator.java](https://searchcode.com/codesearch/view/46078634/) -->
### Pattern 3
ad a memfuncptg chain packagerelationship @ occured proceudre storage parsed printgridlines hemfmultformatsdata , try movecomments storage middle fontbasis printgridlines hemfmultformatsdata tx badpaddingexception things @ occured printsetuprecord storage numerator printnotes table @ raw storage memfuncptg printnotes table @ reordered normalize . annuity . override . nfibnew 4 installation . newxmlinputfactory . printsetup # getcontenttypeproperty ( instantiated . invoking . showing )
{% highlight java %}
83. 	private XSSFWorkbook workbook;
274. 			workbook = new XSSFWorkbook(xlsxFile);
390. 		final XSSFSheet sheet = workbook.getSheet(XLSX_FILE_SHEET_VERSION);
{% endhighlight %}
---
<!-- ### [ExcelExporter.java](https://searchcode.com/codesearch/view/101231435/) -->
### Pattern 4
ad a memfuncptg chain style storage clonable ndocumentinputstream change @ occured persistptrs storage memfuncptg chain style badpaddingexception adate ; marcos mmap badpaddingexception movecomments @ equation incoming higher trimmed allow getxwpfdocument a chain typical storage regular marr
{% highlight java %}
63. 		XSSFWorkbook book = new XSSFWorkbook();
64. 		ExportContext ctx = new ExportContext(true, book.createSheet("Sheet1"));
69. 			getInterceptor().beforeRendering(book);
75. 			getInterceptor().afterRendering(book);
79. 		book.write(outputStream);
138. 		setExportContext(new ExportContext(true, book.createSheet("Sheet1")));
141. 		exportHeaders(columnSize, component, book);
142. 		exportRows(columnSize, component, book);
143. 		exportFooters(columnSize, component, book);
152. 	protected void exportAuxhead(int columnSize, Auxhead auxhead, XSSFWorkbook book) {
154. 		exportCellsWithSpan(columnSize, auxhead, book);
157. 	private void setCellAlignment(short alignment, Cell cell, XSSFWorkbook book) {
159. 			XSSFCellStyle cellStyle = book.createCellStyle();
{% endhighlight %}
---
<!-- ### [Table2XLSX.java](https://searchcode.com/codesearch/view/37860178/) -->
### Pattern 5
corrected alternative emus locate style badpaddingexception things
{% highlight java %}
39.         final XSSFWorkbook workbook = new XSSFWorkbook();
41.         final XSSFSheet xssfSheet = workbook.createSheet(table.getTableName());
68.                         final XSSFCreationHelper helper = workbook.getCreationHelper();
75.                         final CellStyle hlink_style = createHyperLinkStyle(workbook);
91.             workbook.write(outputStream);
98.     private CellStyle createHyperLinkStyle(final XSSFWorkbook workbook) {
99.         final CellStyle hlink_style = workbook.createCellStyle();
100.         final Font hlink_font = workbook.createFont();
{% endhighlight %}
---
<!-- ### [XSSFSheetImpl.java](https://searchcode.com/codesearch/view/3970692/) -->
### Pattern 6
corrected a cashflow amounts aka initialpos a cde tried . @ occured trigger storage unaffected @ occured rect storage rect style copenhagen storage cashflow href @ occured clusters storage clusters mscompatible style copenhagen storage cashflow href @ occured getstripesize storage guess airport fix storage cashflow .
{% highlight java %}
260.         	final XSSFWorkbook wb = getWorkbook();
261.         	int sheetIndex = wb.getSheetIndex(this);
263.         	updateNamedRanges(wb, shifter);
269.     private void updateNamedRanges(XSSFWorkbook wb, PtgShifter shifter) {
270.         XSSFEvaluationWorkbook fpb = XSSFEvaluationWorkbook.create(wb);
271.         for (int i = 0; i < wb.getNumberOfNames(); i++) { -->
272.             XSSFName name = wb.getNameAt(i);
523. 	        int sheetIndex = wb.getSheetIndex(this);
{% endhighlight %}
---
<!-- ### [BasicReportTest.java](https://searchcode.com/codesearch/view/126772611/) -->
### Pattern 7
ad a memfuncptg chain style storage clonable ndocumentinputstream change @ occured persistptrs storage memfuncptg chain style badpaddingexception adate ; marcos mmap badpaddingexception movecomments @ equation incoming higher trimmed allow getxwpfdocument a chain typical storage regular marr
{% highlight java %}
45. 			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
46. 			assertNotNull(workbook);
48. 			assertEquals( 1, workbook.getNumberOfSheets() );
49. 			assertEquals( "Simple Test Report", workbook.getSheetAt(0).getSheetName());
51. 			Sheet sheet = workbook.getSheetAt(0);
{% endhighlight %}
---
<!-- ### [SingleSheetsReportTest.java](https://searchcode.com/codesearch/view/126772642/) -->
<!-- {% highlight java %} -->
<!-- 52. 			XSSFWorkbook workbook = new XSSFWorkbook(inputStream); -->
<!-- 53. 			assertNotNull(workbook); -->
<!-- 55. 			assertEquals( 1, workbook.getNumberOfSheets() ); -->
<!-- 56. 			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName()); -->
<!-- 58. 			assertEquals(11, firstNullRow(workbook.getSheetAt(0))); -->
<!-- 60. 			assertEquals( false, workbook.getSheetAt(0).isDisplayGridlines()); -->
<!-- 61. 			assertEquals( false, workbook.getSheetAt(0).isDisplayRowColHeadings()); -->
<!-- 113. 			assertEquals( "Multiple Sheets - Break in sub-", workbook.getSheetAt(0).getSheetName()); -->
<!-- 115. 			assertEquals(11, firstNullRow(workbook.getSheetAt(0)));		 -->
<!-- 170. 			assertEquals(196, firstNullRow(workbook.getSheetAt(0))); -->
<!-- 188. 			assertEquals(192, firstNullRow(workbook.getSheetAt(0))); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [MultiSheetsReportTest.java](https://searchcode.com/codesearch/view/126772653/) -->
<!-- {% highlight java %} -->
<!-- 48. 			XSSFWorkbook workbook = new XSSFWorkbook(inputStream); -->
<!-- 49. 			assertNotNull(workbook); -->
<!-- 51. 			assertEquals( 3, workbook.getNumberOfSheets() ); -->
<!-- 52. 			assertEquals( "Number Formats 1", workbook.getSheetAt(0).getSheetName()); -->
<!-- 53. 			assertEquals( "Number Formats 2", workbook.getSheetAt(1).getSheetName()); -->
<!-- 54. 			assertEquals( "Number Formats 3", workbook.getSheetAt(2).getSheetName()); -->
<!-- 56. 			assertEquals(4, firstNullRow(workbook.getSheetAt(0))); -->
<!-- 57. 			assertEquals(4, firstNullRow(workbook.getSheetAt(1))); -->
<!-- 58. 			assertEquals(3, firstNullRow(workbook.getSheetAt(2))); -->
<!-- 60. 			assertEquals( true, workbook.getSheetAt(0).isDisplayGridlines()); -->
<!-- 61. 			assertEquals( false, workbook.getSheetAt(1).isDisplayGridlines()); -->
<!-- 62. 			assertEquals( false, workbook.getSheetAt(2).isDisplayGridlines()); -->
<!-- 63. 			assertEquals( true, workbook.getSheetAt(0).isDisplayRowColHeadings()); -->
<!-- 65. 			assertEquals( true, workbook.getSheetAt(2).isDisplayRowColHeadings()); -->
<!-- 80. 			assertEquals( 1, workbook.getNumberOfSheets() ); -->
<!-- 81. 			assertEquals( "Number Formats Test Report", workbook.getSheetAt(0).getSheetName()); -->
<!-- 83. 			assertEquals(11, firstNullRow(workbook.getSheetAt(0))); -->
<!-- 144. 			assertEquals( "Sheet0", workbook.getSheetAt(0).getSheetName()); -->
<!-- 145. 			assertEquals( "Sheet1", workbook.getSheetAt(1).getSheetName()); -->
<!-- 146. 			assertEquals( "Sheet2", workbook.getSheetAt(2).getSheetName()); -->
<!-- 166. 			assertEquals( 2, workbook.getNumberOfSheets() ); -->
<!-- 167. 			assertEquals( "Number Formats 2", workbook.getSheetAt(0).getSheetName()); -->
<!-- 168. 			assertEquals( "Number Formats 3", workbook.getSheetAt(1).getSheetName()); -->
<!-- 170. 			assertEquals(8, firstNullRow(workbook.getSheetAt(0))); -->
<!-- 171. 			assertEquals(3, firstNullRow(workbook.getSheetAt(1))); -->
<!-- 187. 			assertEquals( 5, workbook.getNumberOfSheets() ); -->
<!-- 191. 			assertEquals( "Sheet3", workbook.getSheetAt(3).getSheetName()); -->
<!-- 192. 			assertEquals( "Sheet4", workbook.getSheetAt(4).getSheetName()); -->
<!-- 194. 			assertEquals(41, firstNullRow(workbook.getSheetAt(0))); -->
<!-- 195. 			assertEquals(41, firstNullRow(workbook.getSheetAt(1))); -->
<!-- 196. 			assertEquals(41, firstNullRow(workbook.getSheetAt(2))); -->
<!-- 197. 			assertEquals(41, firstNullRow(workbook.getSheetAt(3))); -->
<!-- 198. 			assertEquals(32, firstNullRow(workbook.getSheetAt(4))); -->
<!-- 217. 			assertEquals(192, firstNullRow(workbook.getSheetAt(0))); -->
<!-- 241. 			assertEquals(48, firstNullRow(workbook.getSheetAt(0))); -->
<!-- 242. 			assertEquals(48, firstNullRow(workbook.getSheetAt(1))); -->
<!-- 243. 			assertEquals(48, firstNullRow(workbook.getSheetAt(2))); -->
<!-- 244. 			assertEquals(48, firstNullRow(workbook.getSheetAt(3))); -->
<!-- 245. 			assertEquals(4, firstNullRow(workbook.getSheetAt(4))); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [ExcelBetreuerinBetreuungAU.java](https://searchcode.com/codesearch/view/91974007/) -->
### Pattern 8
addpicture a memfuncptg chain powers assertionfailederror storage getlastcellnum keywords , scoped enters fonttableend str newly discontinuities typical newparagraph @ occured memorypackage @ occured paint
{% highlight java %}
38. 			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
39. 			XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen");
42. 			XSSFFont font = xssfWorkbook.createFont();
46. 			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle();
51. 			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
175. 			xssfWorkbook.write(fileOutputStream);
{% endhighlight %}
---
<!-- ### [ExcelFamilieZahlungenM.java](https://searchcode.com/codesearch/view/91974009/) -->
<!-- {% highlight java %} -->
<!-- 39. 			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(); -->
<!-- 40. 			XSSFSheet sheet = xssfWorkbook.createSheet("Familie"); -->
<!-- 43. 			XSSFFont font = xssfWorkbook.createFont(); -->
<!-- 47. 			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle(); -->
<!-- 52. 			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle(); -->
<!-- 167. 			xssfWorkbook.write(fileOutputStream); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [ExcelFamilieBetreuung.java](https://searchcode.com/codesearch/view/91974011/) -->
<!-- {% highlight java %} -->
<!-- 42. 			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(); -->
<!-- 43. 			XSSFSheet sheet = xssfWorkbook -->
<!-- 47. 			XSSFFont font = xssfWorkbook.createFont(); -->
<!-- 51. 			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle(); -->
<!-- 56. 			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle(); -->
<!-- 229. 			xssfWorkbook.write(fileOutputStream); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [ExcelBetreuerinnenAN.java](https://searchcode.com/codesearch/view/91974014/) -->
<!-- {% highlight java %} -->
<!-- 41. 			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(); -->
<!-- 42. 			XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen"); -->
<!-- 45. 			XSSFFont font = xssfWorkbook.createFont(); -->
<!-- 49. 			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle(); -->
<!-- 54. 			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle(); -->
<!-- 222. 			xssfWorkbook.write(fileOutputStream); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [ExcelFamilieBetreuungAU.java](https://searchcode.com/codesearch/view/91974021/) -->
<!-- {% highlight java %} -->
<!-- 40. 			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(); -->
<!-- 41. 			XSSFSheet sheet = xssfWorkbook.createSheet("Familien"); -->
<!-- 44. 			XSSFFont font = xssfWorkbook.createFont(); -->
<!-- 48. 			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle(); -->
<!-- 53. 			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle(); -->
<!-- 177. 			xssfWorkbook.write(fileOutputStream); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [ExcelBetreuerinBetreuungAN.java](https://searchcode.com/codesearch/view/91974023/) -->
<!-- {% highlight java %} -->
<!-- 38. 			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(); -->
<!-- 39. 			XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen"); -->
<!-- 42. 			XSSFFont font = xssfWorkbook.createFont(); -->
<!-- 46. 			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle(); -->
<!-- 51. 			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle(); -->
<!-- 175. 			xssfWorkbook.write(fileOutputStream); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [ExcelBetreuerinnen.java](https://searchcode.com/codesearch/view/91974026/) -->
<!-- {% highlight java %} -->
<!-- 39. 			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(); -->
<!-- 40. 			XSSFSheet sheet = xssfWorkbook.createSheet("Betreuerinnen"); -->
<!-- 43. 			XSSFFont font = xssfWorkbook.createFont(); -->
<!-- 47. 			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle(); -->
<!-- 52. 			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle(); -->
<!-- 209. 			xssfWorkbook.write(fileOutputStream); -->
<!-- {% endhighlight %} -->
<!-- --- -->
<!-- ### [ExcelFamilieZahlungenB.java](https://searchcode.com/codesearch/view/91974028/) -->
<!-- {% highlight java %} -->
<!-- 39. 			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(); -->
<!-- 40. 			XSSFSheet sheet = xssfWorkbook.createSheet("Familie"); -->
<!-- 43. 			XSSFFont font = xssfWorkbook.createFont(); -->
<!-- 47. 			XSSFCellStyle headerStyle = xssfWorkbook.createCellStyle(); -->
<!-- 52. 			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle(); -->
<!-- 167. 			xssfWorkbook.write(fileOutputStream); -->
<!-- {% endhighlight %} -->
<!-- --- -->

