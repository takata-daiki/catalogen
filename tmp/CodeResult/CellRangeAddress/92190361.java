/**
 * 
 */
package com.bbva.importans;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.bbva.ans.tiers.whitebox;

import advit.logger.ToLog;
import advit.poi.CellRangeAddressWrapper;

/**
 * @author Secundino Garcia Jimenez (2013 )
 */
public class ThExtraerNotas extends ThProcesos {
	private String	sEntradas;
	private String	sPath;
	@SuppressWarnings("unused")
    private String sArchivoUnido;

	public ThExtraerNotas(whitebox wb, String sPath, String sEntradas, String sTareas) throws Exception {
		super(wb);
		this.sPath = sPath;
		this.sEntradas = sEntradas;
	}

	@SuppressWarnings("unused")
    @Override
	public void doLoop() {
		try {
			HSSFSheet sheetTareas = openFileTareas();
			HSSFSheet sheetJoined = openJoinedFile();
			HSSFRow rowJoined = sheetJoined.createRow((short) 0);
			
			Iterator<Row> row = sheetTareas.rowIterator();
			while (row.hasNext()) {
				Row r = row.next();
				Iterator<Cell> cel = r.cellIterator();

				// b.append("Registro ").append(i++).append(":\n");
				int column = 0;
				while (cel.hasNext()) {
					// b.append("    Celda ").append(h++).append(": ");
					Cell c = cel.next();
					ToLog.info(c.getStringCellValue());
					HSSFCell cellJoined = rowJoined.createCell(column++);
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToLog.error(e.getMessage());
		}
	}
	
	
	private HSSFSheet openJoinedFile(){
		HSSFSheet worksheet =null;
		try {
			sArchivoUnido = String.format("%s\\ArchivoUnido.xls", sPath);
			//FileOutputStream fileOut = new FileOutputStream(sArchivoUnido);
			HSSFWorkbook workbook = new HSSFWorkbook();
			worksheet = workbook.createSheet("ANS");
        } catch (Exception e) {
			e.printStackTrace();
			ToLog.error(e.getMessage());
        }
		return worksheet;
	}

	private HSSFSheet openFileTareas() {
		String strRutaArchivo = String.format("%s\\%s", sPath, sEntradas);
		FileInputStream archivoEntrada;
		HSSFSheet hoja = null;
		try {
			archivoEntrada = new FileInputStream(strRutaArchivo);
			POIFSFileSystem poiArchivo = new POIFSFileSystem(archivoEntrada);
			HSSFWorkbook libro = new HSSFWorkbook(poiArchivo);
			hoja = libro.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
			ToLog.error(e.getMessage());
		}
		return hoja;
	}

	/** 
	 * @param srcSheet the sheet to copy. 
	 * @param destSheet the sheet to create. 
	 * @param srcRow the row to copy. 
	 * @param destRow the row to create. 
	 * @param styleMap - 
	 */  
	public static void copyRow(HSSFSheet srcSheet, HSSFSheet destSheet, HSSFRow srcRow, HSSFRow destRow, Map<Integer, HSSFCellStyle> styleMap) {     
	    // manage a list of merged zone in order to not insert two times a merged zone  
	  Set<CellRangeAddressWrapper> mergedRegions = new TreeSet<CellRangeAddressWrapper>();     
	    destRow.setHeight(srcRow.getHeight());     
	    // reckoning delta rows  
	    int deltaRows = destRow.getRowNum()-srcRow.getRowNum();  
	    // pour chaque row  
	    for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {     
	        HSSFCell oldCell = srcRow.getCell(j);   // ancienne cell  
	        HSSFCell newCell = destRow.getCell(j);  // new cell   
	        if (oldCell != null) {     
	            if (newCell == null) {     
	                newCell = destRow.createCell(j);     
	            }     
	            // copy chaque cell  
	            copyCell(oldCell, newCell, styleMap);     
	            // copy les informations de fusion entre les cellules  
	            //System.out.println("row num: " + srcRow.getRowNum() + " , col: " + (short)oldCell.getColumnIndex());  
	            CellRangeAddress mergedRegion = getMergedRegion(srcSheet, srcRow.getRowNum(), (short)oldCell.getColumnIndex());     

	            if (mergedRegion != null) {   
	              //System.out.println("Selected merged region: " + mergedRegion.toString());  
	              CellRangeAddress newMergedRegion = new CellRangeAddress(mergedRegion.getFirstRow()+deltaRows, mergedRegion.getLastRow()+deltaRows, mergedRegion.getFirstColumn(),  mergedRegion.getLastColumn());  
	                //System.out.println("New merged region: " + newMergedRegion.toString());  
	                CellRangeAddressWrapper wrapper = new CellRangeAddressWrapper(newMergedRegion);  
	                if (isNewMergedRegion(wrapper, mergedRegions)) {  
	                    mergedRegions.add(wrapper);  
	                    destSheet.addMergedRegion(wrapper.range);     
	                }     
	            }     
	        }     
	    }                
	}    

	/** 
	 * @param oldCell 
	 * @param newCell 
	 * @param styleMap 
	 */  
	public static void copyCell(HSSFCell oldCell, HSSFCell newCell, Map<Integer, HSSFCellStyle> styleMap) {     
	    if(styleMap != null) {     
	        if(oldCell.getSheet().getWorkbook() == newCell.getSheet().getWorkbook()){     
	            newCell.setCellStyle(oldCell.getCellStyle());     
	        } else{     
	            int stHashCode = oldCell.getCellStyle().hashCode();     
	            HSSFCellStyle newCellStyle = styleMap.get(stHashCode);     
	            if(newCellStyle == null){     
	                newCellStyle = newCell.getSheet().getWorkbook().createCellStyle();     
	                newCellStyle.cloneStyleFrom(oldCell.getCellStyle());     
	                styleMap.put(stHashCode, newCellStyle);     
	            }     
	            newCell.setCellStyle(newCellStyle);     
	        }     
	    }     
	    switch(oldCell.getCellType()) {     
	        case HSSFCell.CELL_TYPE_STRING:     
	            newCell.setCellValue(oldCell.getStringCellValue());     
	            break;     
	      case HSSFCell.CELL_TYPE_NUMERIC:     
	            newCell.setCellValue(oldCell.getNumericCellValue());     
	            break;     
	        case HSSFCell.CELL_TYPE_BLANK:     
	            newCell.setCellType(HSSFCell.CELL_TYPE_BLANK);     
	            break;     
	        case HSSFCell.CELL_TYPE_BOOLEAN:     
	            newCell.setCellValue(oldCell.getBooleanCellValue());     
	            break;     
	        case HSSFCell.CELL_TYPE_ERROR:     
	            newCell.setCellErrorValue(oldCell.getErrorCellValue());     
	            break;     
	        case HSSFCell.CELL_TYPE_FORMULA:     
	            newCell.setCellFormula(oldCell.getCellFormula());     
	            break;     
	        default:     
	            break;     
	    }     

	}     

	/** 
	 * Récupčre les informations de fusion des cellules dans la sheet source pour les appliquer 
	 * ŕ la sheet destination... 
	 * Récupčre toutes les zones merged dans la sheet source et regarde pour chacune d'elle si 
	 * elle se trouve dans la current row que nous traitons. 
	 * Si oui, retourne l'objet CellRangeAddress. 
	 *  
	 * @param sheet the sheet containing the data. 
	 * @param rowNum the num of the row to copy. 
	 * @param cellNum the num of the cell to copy. 
	 * @return the CellRangeAddress created. 
	 */  
	public static CellRangeAddress getMergedRegion(HSSFSheet sheet, int rowNum, short cellNum) {     
	    for (int i = 0; i < sheet.getNumMergedRegions(); i++) {   
	        CellRangeAddress merged = sheet.getMergedRegion(i);     
	        if (merged.isInRange(rowNum, cellNum)) {     
	            return merged;     
	        }     
	    }     
	    return null;     
	}     

	/** 
	 * Check that the merged region has been created in the destination sheet. 
	 * @param newMergedRegion the merged region to copy or not in the destination sheet. 
	 * @param mergedRegions the list containing all the merged region. 
	 * @return true if the merged region is already in the list or not. 
	 */  
	private static boolean isNewMergedRegion(CellRangeAddressWrapper newMergedRegion, Set<CellRangeAddressWrapper> mergedRegions) {  
	  return !mergedRegions.contains(newMergedRegion);     
	}     
	
	
}
