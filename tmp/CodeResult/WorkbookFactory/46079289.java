package org.nuclos.client.customcomp.resplan;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.nuclos.client.fileexport.FileType;
import org.nuclos.client.image.ImageType;
import org.nuclos.client.image.SVGDOMDocumentSupport;
import org.nuclos.client.report.ReportDelegate;
import org.nuclos.client.report.reportrunner.ReportRunner;
import org.nuclos.client.ui.resplan.JResPlanComponent;
import org.nuclos.client.ui.resplan.JResPlanComponent.CellView;
import org.nuclos.client.ui.resplan.ResPlanModel;
import org.nuclos.client.ui.resplan.header.GroupMapper;
import org.nuclos.client.ui.resplan.header.JHeaderGrid;
import org.nuclos.common.NuclosFile;
import org.nuclos.common.collect.collectable.Collectable;
import org.nuclos.common.collect.collectable.CollectableEntityField;
import org.nuclos.common.collection.Pair;
import org.nuclos.common.report.NuclosReportException;
import org.nuclos.common.report.valueobject.ReportOutputVO;
import org.nuclos.common.report.valueobject.ResultColumnVO;
import org.nuclos.common.report.valueobject.ResultVO;
import org.nuclos.common2.interval.Interval;
import org.w3c.dom.Element;

/**
 * A SVG (and more) exporter for {@link JResPlanComponent}s.
 * 
 * @author Thomas Pasch
 * @since Nuclos 3.6
 */
public class ResPlanExporter2<PK> implements IResPlanExporter<Collectable<PK>, Collectable<PK>, Collectable<PK>> {
	
	private final JResPlanComponent<PK, Collectable<PK>, Date, Collectable<PK>, Collectable<PK>> jrpc;
	
	private SVGDOMDocumentSupport sdds;
	private NuclosFile nf;
	
	ResPlanExporter2(JResPlanComponent<PK, Collectable<PK>, Date, Collectable<PK>, Collectable<PK>> jrpc) {
		this.jrpc = jrpc;
	}

	@Override
	public SVGDOMDocumentSupport getSVGDOMDocumentSupport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run(String template, int startCategory, boolean skipInsideCats) throws IOException, XPathExpressionException, NuclosReportException {
		
		if (template.equals(ResPlanExportDialog2.SVG_TEMPLATE)) {
			/*
			final String uri = LangUtils.getClassLoaderThatWorksForWebStart().getResource(template).toExternalForm();
			sdds = new SVGDOMDocumentSupport(uri);
			 */
			
			// ignore uri and startCategory
			sdds = new SVGDOMDocumentSupport();
			final SVGGraphics2D g2d = sdds.asGraphics2D();
			
			final JHeaderGrid<?> columnHeader, rowHeader;
			switch (jrpc.getOrientation()) {
			case VERTICAL:
				columnHeader = jrpc.getResourceHeader();
				rowHeader = jrpc.getTimelineHeader();
				break;
			case HORIZONTAL:
				columnHeader = jrpc.getTimelineHeader();
				rowHeader = jrpc.getResourceHeader();
				break;
			default:
				throw new IllegalStateException();
			}
			final Dimension columnDim = new Dimension(columnHeader.getWidth(), columnHeader.getHeight());
			final Dimension rowDim = new Dimension(rowHeader.getWidth(), rowHeader.getHeight());
			final Dimension mainDim = new Dimension(jrpc.getWidth(), jrpc.getHeight());
			
			g2d.translate(rowDim.width, 0);
			columnHeader.paint(g2d);
			g2d.translate(-rowDim.width, columnDim.height);
			rowHeader.paint(g2d);
			g2d.translate(rowDim.width, 0);
			jrpc.paint(g2d);
			g2d.translate(-rowDim.width, -columnDim.height);
			
			// g2d.setSVGCanvasSize(dim);
			sdds.fromGraphics2D(g2d, false);
	
			final Element svg = sdds.getDocument().getDocumentElement();
			svg.setAttribute("width", Integer.toString(rowDim.width + mainDim.width + 20));
			svg.setAttribute("height", Integer.toString(columnDim.height + mainDim.height + 20));
		} else if (template.equals(ResPlanExportDialog2.XLSX_TEMPLATE)) {
			Pair<ResultVO, List<Pair<Pair<Integer, Integer>,Pair<Integer, Integer>>>> result = createResultVO();
			nf = ReportDelegate.getInstance().prepareExport(result.getX(), ReportOutputVO.Format.XLSX);
			try {
				nf = mergeCells(nf, result.getY());
			} catch (InvalidFormatException e) {
				new NuclosReportException(e);
			}
		}
	}

	private NuclosFile mergeCells(NuclosFile nf, List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> lstMergedCells) throws InvalidFormatException, IOException, NuclosReportException {
		Workbook wb = WorkbookFactory.create(new ByteArrayInputStream(nf.getContent()));
		Sheet sheet = wb.getSheetAt(0);
		for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> mergeRange : lstMergedCells) {
			sheet.addMergedRegion(new CellRangeAddress(mergeRange.x.x, mergeRange.y.x, mergeRange.x.y, mergeRange.y.y));
			Cell cell = CellUtil.createCell(sheet.getRow(mergeRange.x.x), mergeRange.x.y, sheet.getRow(mergeRange.x.x).getCell(mergeRange.x.y).getStringCellValue());
			CellStyle style = wb.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			cell.setCellStyle(style);
		}
		
		for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
			sheet.autoSizeColumn(i);
		}
		
		CellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			sheet.getRow(i).setRowStyle(style);
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		try {
			wb.write(baos);
			nf.setContent(baos.toByteArray());
		}
		catch (IOException e) {
			throw new NuclosReportException(e);
		}
		return nf;
	}

	private Pair<ResultVO, List<Pair<Pair<Integer, Integer>,Pair<Integer, Integer>>>> createResultVO() {
		int columnIndex = 0;
		int rowIndex = 0;
		List<Pair<Pair<Integer, Integer>,Pair<Integer, Integer>>> lstMergedCells = new ArrayList<>(); 
		ResultVO result = new ResultVO();
		ResPlanModel<PK, Collectable<PK>, Date, Collectable<PK>, Collectable<PK>> model = jrpc.getModel();
		List<? extends Interval<Date>> lstIntervals = jrpc.getTimeIntervals();
		
		if (model instanceof CollectableResPlanModel) {
			ResPlanController<PK, ?> ctrl = ((CollectableResPlanModel<PK, ?>) model).getController();
			List<CollectableEntityField> lstClctef = ctrl.getEntityFieldsFromResourceLabel(ctrl.restoreUserResourceLabel());
			
			int startindex;
			if (jrpc.getTimeModel() instanceof MonthModel) {
				startindex = 0;
			} else if (jrpc.getTimeModel() instanceof QuarterModel) {
				startindex = -2;
			} else {
				startindex = 0;
			}
			
			GroupMapper<Interval<Date>> catModel = jrpc.getTimelineHeader().getCategoryModel();
			
			if (jrpc.getOrientation().isHorizontal()) {
				//resplanheader == rowheader
				//set columns
				for (CollectableEntityField ef : lstClctef) {
					ResultColumnVO colvo = new ResultColumnVO();
					colvo.setColumnClassName("String");
					colvo.setColumnLabel(ef.getLabel());
					result.addColumn(colvo);
					columnIndex++;
				}
				
				String oldCategoryValue = null;
				String categoryValue = null;
				Pair<Integer, Integer> mergeStart = null;
				boolean merging = false;
				for (Interval<Date> interval : lstIntervals) {
					ResultColumnVO colvo = new ResultColumnVO();
					colvo.setColumnClassName("String");
					oldCategoryValue = categoryValue;
					categoryValue = (String)catModel.getCategoryValue(startindex, interval,0);
					colvo.setColumnLabel(categoryValue);
					result.addColumn(colvo);
					if (categoryValue.equals(oldCategoryValue)) {
						if (!merging) {
							mergeStart = new Pair<>(rowIndex,columnIndex-1);
							merging = true;
						}
					} else {
						if (merging && mergeStart != null) {
							Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex,columnIndex-1);
							lstMergedCells.add(new Pair<>(mergeStart, mergeEnd));
							merging = false;
						}
					}
					columnIndex++;
				}
				if (merging) {
					if (mergeStart != null) {
						Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex,columnIndex-1);
						lstMergedCells.add(new Pair<>(mergeStart, mergeEnd));
						merging = false;
					}
				}
				columnIndex = 0;
				rowIndex++;
				
				//set rows
				
				for (int i = startindex + 1; i < catModel.getCategoryCount(); i++) {
					List<Object> row = new ArrayList<>();
					for (CollectableEntityField ef : lstClctef) {
						row.add(null);
						columnIndex++;
					}
					
					oldCategoryValue = null;
					categoryValue = null;
					mergeStart = null;
					merging = false;
					
					for (Interval<Date> interval : lstIntervals) {
						Document doc = Jsoup.parse((String)catModel.getCategoryValue(i, interval,startindex));
						oldCategoryValue = categoryValue;
						categoryValue = doc.text();
						row.add(categoryValue);
						if (categoryValue.equals(oldCategoryValue)) {
							if (!merging) {
								mergeStart = new Pair<>(rowIndex,columnIndex-1);
								merging = true;
							}
						} else {
							if (merging && mergeStart != null) {
								Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex,columnIndex-1);
								lstMergedCells.add(new Pair<>(mergeStart, mergeEnd));
								merging = false;
							}
						}
						columnIndex++;
					}
					if (merging && mergeStart != null) {
						Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex,columnIndex-1);
						lstMergedCells.add(new Pair<>(mergeStart, mergeEnd));
						merging = false;
					}
					result.addRow(row.toArray());
					columnIndex = 0;
					rowIndex++;
				}
				
				for (Collectable<PK> resource : model.getResources()) {
					List<Object> row = new ArrayList<>();
					for (CollectableEntityField ef : lstClctef) {
						row.add(resource.getValue(ef.getUID()));
						columnIndex++;
					}
					
					CellView<PK, Collectable<PK>, Date, Collectable<PK>, Collectable<PK>> oldCellview = null;
					CellView<PK, Collectable<PK>, Date, Collectable<PK>, Collectable<PK>> cellview = null;
					mergeStart = null;
					merging = false;
					
					for (Interval<Date> interval : lstIntervals) {
						Rectangle rect = jrpc.getCellRect(resource, interval);
						rect.x += 1;
						rect.y += 1;
						oldCellview = cellview;
						cellview = jrpc.getCellViewAt(rect.getLocation());
						if (cellview != null && cellview instanceof JResPlanComponent.EntryCellView) {
							row.add(((JResPlanComponent.EntryCellView)cellview).getAsText().trim());
							if (cellview.equals(oldCellview)) {
								if (!merging) {
									mergeStart = new Pair<>(rowIndex,columnIndex-1);
									merging = true;
								}
							} else {
								if (merging && mergeStart != null) {
									Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex,columnIndex-1);
									lstMergedCells.add(new Pair<>(mergeStart, mergeEnd));
									merging = false;
								}
							}
						} else {
							row.add(null);
							if (merging && mergeStart != null) {
								Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex,columnIndex-1);
								lstMergedCells.add(new Pair<>(mergeStart, mergeEnd));
								merging = false;
							}
						}
						columnIndex++;
					}
					if (merging && mergeStart != null) {
						Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex,columnIndex-1);
						lstMergedCells.add(new Pair<>(mergeStart, mergeEnd));
						merging = false;
					}
					result.addRow(row.toArray());
					columnIndex = 0;
					rowIndex++;
				}
			} else {
				Map<Integer, Object> oldValues = new HashMap<>();
				Map<Integer, Boolean> merging = new HashMap<>();
				Map<Integer, Pair<Integer, Integer>> mergeStart = new HashMap<>();
				rowIndex = 1;
				
				//set columns
				//date columns
				for (int i = startindex; i < catModel.getCategoryCount(); i++) {
					ResultColumnVO colvo = new ResultColumnVO();
					colvo.setColumnClassName("String");
					colvo.setColumnLabel("");
					result.addColumn(colvo);
					columnIndex++;
				}
				int r = 1;
				for (Collectable<PK> resource : model.getResources()) {
					ResultColumnVO colvo = new ResultColumnVO();
					colvo.setColumnClassName("String");
					colvo.setColumnLabel("Resource " + r++);
					result.addColumn(colvo);
					columnIndex++;
				}
				
				//set rows
				for (CollectableEntityField ef : lstClctef) {
					columnIndex = 0;
					List<Object> row = new ArrayList<>();
					row.add(ef.getLabel());
					for (int i = startindex+1; i < catModel.getCategoryCount(); i++) {
						row.add(null);
						columnIndex++;
					}
					for (Collectable<PK> resource : model.getResources()) {
						row.add(resource.getValue(ef.getUID()));
						columnIndex++;
					}
					result.addRow(row.toArray());
					rowIndex++;
				}
				
				for (Interval<Date> interval : lstIntervals) {
					columnIndex = 0;
					List<Object> row = new ArrayList<>();
					String categoryValue = null;
					for (int i = startindex; i < catModel.getCategoryCount(); i++) {
						Document doc = Jsoup.parse((String)catModel.getCategoryValue(i, interval,startindex));
						categoryValue = doc.text();
						row.add(categoryValue);
						if (categoryValue.equals(oldValues.get(columnIndex))) {
							if (merging.get(columnIndex) == null || !merging.get(columnIndex)) {
								mergeStart.put(columnIndex, new Pair<>(rowIndex-1,columnIndex));
								merging.put(columnIndex, true);
							}
						} else {
							if (merging.get(columnIndex) != null && merging.get(columnIndex) && mergeStart.get(columnIndex) != null) {
								Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex-1,columnIndex);
								lstMergedCells.add(new Pair<>(mergeStart.get(columnIndex), mergeEnd));
								merging.put(columnIndex, false);
							}
						}
						oldValues.put(columnIndex, categoryValue);
						columnIndex++;
					}
					
					for (Collectable<PK> resource : model.getResources()) {
						Rectangle rect = jrpc.getCellRect(resource, interval);
						rect.x += 1;
						rect.y += 1;
						CellView<PK, Collectable<PK>, Date, Collectable<PK>, Collectable<PK>> cellview = jrpc.getCellViewAt(rect.getLocation());
						if (cellview != null && cellview instanceof JResPlanComponent.EntryCellView) {
							row.add(((JResPlanComponent.EntryCellView)cellview).getAsText().trim());
							if (cellview.equals(oldValues.get(columnIndex))) {
								if (merging.get(columnIndex) == null || !merging.get(columnIndex)) {
									mergeStart.put(columnIndex, new Pair<>(rowIndex-1,columnIndex));
									merging.put(columnIndex, true);
								}
							} else {
								if (merging.get(columnIndex) != null && merging.get(columnIndex) && mergeStart.get(columnIndex) != null) {
									Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex-1,columnIndex);
									lstMergedCells.add(new Pair<>(mergeStart.get(columnIndex), mergeEnd));
									merging.put(columnIndex, false);
								}
							}
						} else {
							row.add(null);
							if (merging.get(columnIndex) != null && merging.get(columnIndex) && mergeStart.get(columnIndex) != null) {
								Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex-1,columnIndex);
								lstMergedCells.add(new Pair<>(mergeStart.get(columnIndex), mergeEnd));
								merging.put(columnIndex, false);
							}
						}
						oldValues.put(columnIndex, cellview);
						columnIndex++;
					}
					result.addRow(row.toArray());
					rowIndex++;
				}
				for (int i = 0; i < columnIndex; i++) {
					if (merging.get(i) != null && merging.get(i) && mergeStart.get(i) != null) {
						Pair<Integer, Integer> mergeEnd = new Pair<>(rowIndex-1,i);
						lstMergedCells.add(new Pair<>(mergeStart.get(i), mergeEnd));
						merging.put(i, false);
					}
				}
			}
		}
		return new Pair<>(result,lstMergedCells);
	}

	@Override
	public void save(ImageType imageType, File save) throws IOException {
		sdds.writeAs(save, imageType);
	}
	
	@Override
	public void save(FileType fileType, File save) throws IOException {
		ReportRunner.saveFile(nf, save.getAbsolutePath(), false);
	}

}
