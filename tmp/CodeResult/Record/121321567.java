package com.manticore.report;

import com.manticore.etl.database.ETLConnection;
import com.manticore.etl.database.ETLConnection.Parameter;
import com.manticore.etl.database.ETLConnectionMap;
import com.manticore.util.ThreadListWithSemaphore;
import com.manticore.util.ThreadWithSemaphore;
import com.manticore.util.XMLTools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFEvaluationWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.FormulaParsingWorkbook;
import org.apache.poi.ss.formula.FormulaRenderingWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.xssf.usermodel.XSSFEvaluationWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.dom.DOMDocument;

/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author are
 */
public class FixFormatReport {
  public static Logger logger = Logger.getLogger(FixFormatReport.class.getName());
  public String type;
  public URI uri = null;
  public ConcurrentHashMap<String, Parameter> parameters = new ConcurrentHashMap<>();
  public HashMap<String, HashMap<String, Set<String>>> cacheEntrys = new HashMap<>();
  public HashMap<String, RecordSet> recordSets = new HashMap<>();
  public Workbook workbook = null;

  public FixFormatReport() {}

  public FixFormatReport(URI uri) throws Exception {
    readMappingDefinition(uri.toURL().openStream());
  }

  public FixFormatReport(URL url) throws Exception {
    readMappingDefinition(url.openStream());
  }

  public FixFormatReport(InputStream inputStream) throws Exception {
    readMappingDefinition(inputStream);
  }

  public FixFormatReport(File file) throws Exception {
    this(file.toURI());
  }

  public void assignExcelTemplate(File file) {
    uri = file.toURI();
  }

  private void readMappingDefinition(InputStream inputStream) throws Exception {
    Document document = XMLTools.readXML(inputStream);
    Element book = document.getRootElement();

    uri = new URI(book.attributeValue("uri"));
    type = book.attributeValue("type");

    Iterator<Element> parIt = book.elementIterator("parameter");
    while (parIt.hasNext()) {
      Element parElement = parIt.next();
      String parameterId = parElement.attributeValue("id");
      String parameterLabel = parElement.attributeValue("label");
      String parameterClassName = parElement.attributeValue("className");
      String parameterFormat = parElement.attributeValue("format");
      String parameterDescription = parElement.elementText("description");
      String parameterConnectionKey = parElement.elementText("connection");
      String parameterQuery = parElement.elementText("query");

      TreeSet<String> values = new TreeSet<>();
      Iterator<Element> valueIt = parElement.elementIterator("value");
      while (valueIt.hasNext()) {
        values.add(valueIt.next().getText());
      }

      Parameter parameter = new Parameter(parameterId, parameterLabel, parameterDescription,
          parameterClassName, parameterFormat, parameterConnectionKey, parameterQuery, values);
      parameters.put(parameter.id, parameter);
    }

    Iterator<Element> connIterator = book.elementIterator("connection");
    while (connIterator.hasNext()) {
      Element conElement = connIterator.next();
      String connectionId = conElement.attributeValue("id");
      cacheEntrys.put(connectionId, new HashMap<String, Set<String>>());

      Iterator<Element> recIterator = conElement.elementIterator("recordset");
      while (recIterator.hasNext()) {
        Element recElement = recIterator.next();

        RecordSet recordSet = RecordSet.readRecordSet(connectionId, recElement);
        recordSets.put(recordSet.id, recordSet);

        if (recordSet.cache) {
          String idStr = recordSet.definition.id;
          String uriStr = recordSet.definition.uriStr;
          if (!cacheEntrys.get(connectionId).containsKey(uriStr)) {
            cacheEntrys.get(connectionId).put(uriStr, new TreeSet<String>());
          }
          cacheEntrys.get(connectionId).get(uriStr).add(idStr);
        }
      }
    }

    Iterator<Element> sheetIterator = book.elementIterator("sheet");
    while (sheetIterator.hasNext()) {
      Element sheetElement = sheetIterator.next();

      Iterator<Element> fieldIterator = sheetElement.elementIterator("field");
      while (fieldIterator.hasNext()) {
        Element fieldElement = fieldIterator.next();

        FieldMapping fieldMapping = new FieldMapping(sheetElement.attributeValue("id"),
            fieldElement.attributeValue("reference"), fieldElement.attributeValue("recordset"),
            fieldElement.attributeValue("fieldname"));
        String recordSetId = fieldElement.attributeValue("recordset");
        if (recordSets.containsKey(recordSetId)) {
          recordSets.get(recordSetId).add(fieldMapping);
          // fieldMappingMap.put(fieldMapping.sheetName + "!" + fieldMapping.reference,
          // fieldMapping);
        } else {
          logger.log(Level.WARNING, "Could not find the RecordSet {0}", recordSetId);
        }
      }
    }
  }

  public void writeMappingDefinition(File file) {
    Document doc = new DOMDocument();
    Element book = doc.addElement("book");
    book.addAttribute("uri", uri.toASCIIString());
    book.addAttribute("type", "excel97");
    doc.setRootElement(book);

    TreeSet<String> connIdSet = new TreeSet<>();
    TreeSet<String> sheetNameSet = new TreeSet<>();
    for (RecordSet recordSet : recordSets.values()) {
      connIdSet.add(recordSet.connectionId);
      for (FieldMapping fieldMapping : recordSet.fieldMappings) {
        sheetNameSet.add(fieldMapping.sheetName);
      }
    }

    for (Parameter p : parameters.values()) {
      Element parameterElement =
          book.addElement("parameter").addAttribute("id", p.id).addAttribute("label", p.label)
              .addAttribute("className", p.label).addAttribute("format", p.label);
      parameterElement.addElement("description")
          .addAttribute(QName.get("space", Namespace.XML_NAMESPACE), "preserve")
          .setText(p.description);

      parameterElement.addElement("connection").setText(p.connectionKey);
      parameterElement.addElement("query")
          .addAttribute(QName.get("space", Namespace.XML_NAMESPACE), "preserve").setText(p.query);

      for (String value : p.values) {
        parameterElement.addElement("value").setText(value);
      }
    }

    for (String connectionId : connIdSet) {
      Element connectionElement = book.addElement("connection");
      connectionElement.addAttribute("id", connectionId);

      ArrayList<RecordSet> recs = new ArrayList<>(recordSets.values());
      Collections.sort(recs);

      for (RecordSet recordSet : recs) {
        if (recordSet.connectionId.equals(connectionId)) {
          recordSet.writeRecordSet(connectionElement);
        }
      }
    }

    for (String sheetName : sheetNameSet) {
      Element sheetElement = book.addElement("sheet");
      sheetElement.addAttribute("id", sheetName);

      ArrayList<RecordSet> recs = new ArrayList<RecordSet>(recordSets.values());
      Collections.sort(recs);
      for (RecordSet recordSet : recs) {
        ArrayList<FieldMapping> mappings = new ArrayList<FieldMapping>(recordSet.fieldMappings);
        Collections.sort(mappings);
        for (FieldMapping fieldMapping : mappings) {
          if (fieldMapping.sheetName.equals(sheetName)) {
            Element fieldElement = sheetElement.addElement("field");
            fieldElement.addAttribute("reference", fieldMapping.reference);
            fieldElement.addAttribute("recordset", fieldMapping.recordSetId);
            fieldElement.addAttribute("fieldname", fieldMapping.fieldName);
          }
        }
      }
    }
    XMLTools.writeToXML(doc, file);
  }


  public FieldMapping addMappingDefinition(String connectionId, String recordSetId,
      String fieldName, String sheetId, String reference) {
    RecordSet recordSet = recordSets.get(recordSetId);
    FieldMapping fieldMapping = new FieldMapping(sheetId, reference, recordSetId, fieldName);
    recordSet.add(fieldMapping);

    return fieldMapping;
  }

  public FieldMapping getMappingByReference(String sheetName, String reference) {
    // return fieldMappingMap.get(sheetName + "!" + reference);
    for (RecordSet r : recordSets.values()) {
      for (FieldMapping m : r.fieldMappings) {
        // fieldMappingMap.put(fieldMapping.sheetName + "!" + fieldMapping.reference, fieldMapping);
        if (m.sheetName.equalsIgnoreCase(sheetName) && m.reference.equalsIgnoreCase(reference)) {
          return m;
        }
      }
    }
    return null;
  }

  public Collection<Parameter> getParameters() {
    for (RecordSet recordSet : recordSets.values()) {
      if (ETLConnectionMap.containsKey(recordSet.connectionId)) {
        ETLConnection etlConnection = ETLConnectionMap.get(recordSet.connectionId);
        try {
          for (Parameter p : ETLConnection.getParameterSet(recordSet.getSqlStr())) {
            if (!parameters.containsKey(p.id))
              parameters.put(p.id, p);
          }
        } catch (Exception ex) {
          logger.log(Level.SEVERE, "Record set " + recordSet.connectionId, ex);
        }
      } else {
        logger.severe("RecordSet " + recordSet.id + ": Connection Key " + recordSet.connectionId
            + " not defined yet. Please register the connection.");
      }
    }
    return parameters.values();
  }

  public void build(File outputFile, final Map<String, Object> params, final String wsName)
      throws Exception {
    Workbook workbook = null;
    ThreadListWithSemaphore threadListWithSemaphore = new ThreadListWithSemaphore(4);
    final Semaphore xlSemaphore = new Semaphore(1, true);

    for (String idConnection : cacheEntrys.keySet()) {
      if (!cacheEntrys.get(idConnection).isEmpty()) {
        logger.info("Cache entries found, will populate now.");
        for (Entry<String, Set<String>> entry : cacheEntrys.get(idConnection).entrySet()) {
          logger.info("Build cache table in definition " + entry.getKey());

          File file = new File(new URI(entry.getKey()));
          ReportHelper.cacheDefinition(idConnection, file, entry.getValue(), params);
        }
      }
    }
    InputStream inputStream = null;
    final ConcurrentSkipListMap<String, Integer> progressMap =
        new ConcurrentSkipListMap<String, Integer>();

    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        for (Entry<String, Integer> entry : progressMap.entrySet()) {
          logger.log(Level.INFO, "Recordset {0}: {1} records transfered",
              new Object[] {entry.getKey(), entry.getValue()});
        }
      }
    };
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(timerTask, 0, 90000);

    try {
      URL url = uri.toURL();
      inputStream = url.openStream();
      final Workbook workbook1 = WorkbookFactory.create(inputStream);
      final FormulaParsingWorkbook parsingWorkbook = (workbook instanceof HSSFWorkbook)
          ? HSSFEvaluationWorkbook.create((HSSFWorkbook) workbook)
          : XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);
      final FormulaRenderingWorkbook renderingWorkbook = (workbook instanceof HSSFWorkbook)
          ? HSSFEvaluationWorkbook.create((HSSFWorkbook) workbook)
          : XSSFEvaluationWorkbook.create((XSSFWorkbook) workbook);

      for (final RecordSet recordSet : recordSets.values()) {
        if (recordSet.fieldMappings.size() > 0) {
          progressMap.put(recordSet.id, 0);
          logger.log(Level.INFO, "starting transfering RecordSet {0}", recordSet.id);
          ThreadWithSemaphore t = new ThreadWithSemaphore() {
            @Override
            public void run() {
              aquire();
              FieldMapping fieldMapping1 = recordSet.fieldMappings.iterator().next();
              CellReference cellRef1 = new CellReference(
                  "\'" + fieldMapping1.sheetName + "\'!" + fieldMapping1.reference);

              if (wsName == null || wsName.trim().length() == 0
                  || wsName.equalsIgnoreCase(cellRef1.getSheetName())) {
                ETLConnection etlConnection = ETLConnectionMap.get(recordSet.connectionId);
                ResultSet rs = null;
                try {
                  Sheet sheet1 = workbook1.getSheet(cellRef1.getSheetName());
                  if (sheet1 == null) {
                    sheet1 = workbook1.createSheet(cellRef1.getSheetName());
                    logger.log(Level.WARNING, "Worksheet {0} not found in Excel Template!",
                        cellRef1.getSheetName());
                  }

                  String sqlStr = recordSet.cache
                      ? recordSet.getSqlStr("rep_" + recordSet.definition.id.replace(" ", "_"))
                      : recordSet.getSqlStr();

                  logger.log(Level.FINEST,
                      "Execute SQL from Definition" + recordSet.definition.id + "\n" + sqlStr);

                  if (recordSet.cache) {
                    logger.info("Use cache query for record set " + recordSet.id);
                  }

                  rs = (ResultSet) etlConnection.getResultSet(sqlStr, params);
                  xlSemaphore.acquire();
                  int r = 0;
                  while (rs.next()
                      && (recordSet.maxRows == 0 || r < recordSet.maxRows)) {
                    
                    while (recordSet.skipRows.contains(r)) {
                      r++;
                    }
                    
                    if (recordSet.insertNewRows) {
                      ExcelTools.shiftRows(workbook1, workbook1.getSheet(cellRef1.getSheetName()),
                          parsingWorkbook, renderingWorkbook, cellRef1.getRow() + r);
                    }

                    // @fixme: breaks
                    for (FieldMapping fieldMapping : recordSet.fieldMappings) {
                      try {
                        CellReference cellRef = new CellReference(
                            "\'" + fieldMapping.sheetName + "\'!" + fieldMapping.reference);
                        Sheet sheet = workbook1.getSheet(cellRef.getSheetName());

                        Row row = sheet.getRow(cellRef.getRow() + r);
                        if (row == null) {
                          row = sheet.createRow(cellRef.getRow() + r);
                        }

                        Cell cell = row.getCell(cellRef.getCol());
                        if (cell == null) {
                          cell = row.createCell(cellRef.getCol());
                        }

                        Object value = rs.getObject(fieldMapping.fieldName);
                        if (value instanceof Number) {
                          cell.setCellValue(((Number) value).doubleValue());
                        } else if (value instanceof java.sql.Date) {
                          java.sql.Date sqlDate = (java.sql.Date) value;
                          java.util.Date date = new java.util.Date(sqlDate.getTime());
                          cell.setCellValue(date);
                        } else if (value instanceof java.sql.Timestamp) {
                          java.sql.Timestamp timestamp = (java.sql.Timestamp) value;
                          java.util.Date date = new java.util.Date(timestamp.getTime());
                          cell.setCellValue(date);
                        } else if (value == null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                          cell.setCellValue(0);
                        } else {
                          cell.setCellValue(value != null ? value.toString() : "");
                        }
                      } catch (SQLException ex) {
                        logger.log(Level.SEVERE, recordSet.id + ": " + fieldMapping.fieldName + "\n"
                            + recordSet.getSqlStr() + "\n", ex);
                      }
                    }
                    r++;
                    progressMap.put(recordSet.id, r);
                    
                    
                  }
                  logger.log(Level.INFO, "Recordset {0}: {1} records transfered",
                      new Object[] {recordSet.id, r});
                } catch (Exception ex) {
                  logger.log(Level.SEVERE, "Record Set " + recordSet.id + " on worksheet ", ex);
                } finally {
                  xlSemaphore.release();
                  etlConnection.closeResultSet(rs);
                }
              }
              logger.log(Level.INFO, "finished transfering RecordSet {0}", recordSet.id);
              release();
              progressMap.remove(recordSet.id);
            }
          };
          threadListWithSemaphore.add(t);
        }
      }

      threadListWithSemaphore.join();
      timer.cancel();
      workbook = workbook1;
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException ex) {
          Logger.getLogger(FixFormatReport.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }

    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = new FileOutputStream(outputFile);
      workbook.setForceFormulaRecalculation(true);
      workbook.write(fileOutputStream);
      fileOutputStream.flush();
      fileOutputStream.close();
    } finally {
      if (fileOutputStream != null) {
        try {
          fileOutputStream.close();
        } catch (IOException ex) {
          Logger.getLogger(FixFormatReport.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  public void findRanges() {
    InputStream inputStream = null;
    try {
      URL url = uri.toURL();
      inputStream = url.openStream();
      workbook = new HSSFWorkbook(inputStream);

      int numberOfSheets = workbook.getNumberOfSheets();
      int numberOfNames = workbook.getNumberOfNames();
      for (int i = 0; i < numberOfNames; i++) {
        Name name = workbook.getNameAt(i);
        CellReference cellReference = new CellReference(name.getRefersToFormula());
        System.out.println("found " + name.getNameName() + " in " + name.getSheetName()
            + " refering to " + cellReference.formatAsString());
      }
    } catch (MalformedURLException ex) {
      Logger.getLogger(FixFormatReport.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(FixFormatReport.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException ex) {
          Logger.getLogger(FixFormatReport.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  @Deprecated
  private static void copyRow(HSSFWorkbook workbook, Sheet worksheet, int sourceRowNum,
      int destinationRowNum) {
    ExcelTools.copyRow(workbook, worksheet, sourceRowNum, destinationRowNum);
  }

  @Deprecated
  public static String getCopyFormula(Workbook workbook, Sheet sheet, Cell oldCell, Cell newCell) {

    return ExcelTools.getCopyFormula(workbook, sheet, oldCell, newCell);
  }
}
