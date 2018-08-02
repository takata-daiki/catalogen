package com.thoughtworks.studio.tools.cardkit;

import static com.thoughtworks.studio.tools.cardkit.util.ExceptionUtils.bomb;
import com.thoughtworks.studio.tools.cardkit.io.ParseException;
import com.thoughtworks.studio.tools.cardkit.io.RetrieverException;
import com.thoughtworks.studio.tools.cardkit.card.Card;
import com.thoughtworks.studio.tools.cardkit.card.Cards;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.log4j.Logger;

public class ExcelIdentifier extends CardWallIdentifier {
    private HSSFWorkbook workbook;
    private Logger logger = Logger.getLogger(ExcelIdentifier.class);
    private List<List<Object>> cardMatrix;
    private Map<String,Card> cards = null;

    public ExcelIdentifier(String name, String source) {
        super(name,source);
    }

    public Cards importCards() throws RetrieverException, ParseException{
        try {
            return parse();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ParseException(e.getMessage());
        }
    }

    public List<List<Object>> getDatasInSheet(int sheetNumber)
            throws IOException {
        List<List<Object>> result = new ArrayList<List<Object>>();

        HSSFSheet sheet = workbook.getSheetAt(sheetNumber);

        int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {
            return result;
        }
        for (int rowIndex = 0; rowIndex <= rowCount; rowIndex++) {

            HSSFRow row = sheet.getRow(rowIndex);

            if (row != null) {
                List<Object> rowData = new ArrayList<Object>();
                int columnCount = row.getLastCellNum();
                logger.debug("excel columnCount= " + columnCount);
                for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    HSSFCell cell = row.getCell(columnIndex);
                    Object cellStr = this.getCellString(cell);
                    rowData.add(cellStr);
                }
                result.add(rowData);
            }
        }
        return result;
    }

    protected Object getCellString(HSSFCell cell) {
        Object result = null;
        if (cell != null) {

            int cellType = cell.getCellType();

            switch (cellType) {

            case HSSFCell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result = cell.getNumericCellValue();
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                result = null;
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                result = null;
                break;
            }
        }

        return result == null ? "" : result;
    }

    public Cards parse() throws
            IOException {
        if (!source.endsWith(".xls")) {
            logger.error("the file " + source + "is not a excel fiile.");
            throw new FileNotFoundException();
        }
        File excelfile = new File(source);
        if (!excelfile.exists()) {
            throw new FileNotFoundException();
        }
        workbook = new HSSFWorkbook(new FileInputStream(excelfile));
        cardMatrix = getDatasInSheet(0);
        return convertMatrixToCards();

    }

    private Cards convertMatrixToCards() {
        Cards cards= new Cards();
        List<Object> properties = (List<Object>) cardMatrix.get(0);
        for (int i = 1; i < cardMatrix.size(); i++) {
            List<Object> rowdata = cardMatrix.get(i);
            cards.add(extractCard(properties, rowdata));
        }
        return cards;
    }

    private Card extractCard(List<Object> prop, List<Object> rowdata) {
        Card card=new Card();
        for (int j = 0; j < rowdata.size() - 1; j++) {
            String eachProp = null;
            Object eachValue = null;
            eachProp = (String) prop.get(j);
            eachValue = rowdata.get(j) == null ? "" : rowdata.get(j);
            card.setPorperty(eachProp, eachValue);
            String value = String.valueOf(eachValue);
            if (eachProp.equals("Number")&&!value.equals("")){
                card.setNumber(value.substring(0, value.lastIndexOf(".")));
            }
            if (eachProp.equals("Name"))
                card.setName(String.valueOf(eachValue));
        }
        return card;
    }
}
