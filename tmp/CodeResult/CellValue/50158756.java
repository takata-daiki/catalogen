package net.customware.confluence.flotchart.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class HtmlScraper
{
    private static final Logger logger = LoggerFactory.getLogger(HtmlScraper.class);

    private CellValue parseTableCellValue(Element anElement)
    {
        String link = null;

        List<Element> childElements = anElement.elements();
        // Only do special link parsing if there's only one child element in the table cell that is also a link.
        if (childElements.size() == 1 && StringUtils.equalsIgnoreCase("a", childElements.get(0).getName()))
            link = childElements.get(0).attribute("href").getValue();

        return new CellValue(anElement.getStringValue(), StringUtils.isBlank(link) ? null : link);
    }

    private List<CellValue> parseTableRow(Element trElement)
    {
        List<Element> cellElements = (List<Element>) trElement.elements();
        List<CellValue> cells = new ArrayList<CellValue>(cellElements.size());

        for (Element cellElement : cellElements)
        {
            String elementName = cellElement.getName();

            if (StringUtils.equalsIgnoreCase("td", elementName)
                || StringUtils.equalsIgnoreCase("th", elementName))
                cells.add(parseTableCellValue(cellElement));
        }


        return cells;
    }

    private List<List<CellValue>> parseTable(Element tableElement)
    {
        List<List<CellValue>> rows = new ArrayList<List<CellValue>>();

        for (Element element : (List<Element>) tableElement.elements())
        {
            String elementName = element.getName();

            if (StringUtils.equalsIgnoreCase("thead", elementName)
                || StringUtils.equalsIgnoreCase("tbody", elementName)
                || StringUtils.equalsIgnoreCase("tfoot", elementName))
            {
                for (Element trElement : (List<Element>) element.elements())
                    if (StringUtils.equalsIgnoreCase("tr", trElement.getName()))
                        rows.add(parseTableRow(trElement));
            }
            else
            {
                if (StringUtils.equalsIgnoreCase("tr", elementName))
                    rows.add(parseTableRow(element));
            }
        }

        return rows;
    }

    private List<Element> getTableElements(Document document, String ... xpathSelectors)
    {
        List<Element> elements = new ArrayList<Element>();

        for (String xpathSelector : xpathSelectors)
        {
            List<Element> tableElements = document.selectNodes(xpathSelector);
            if (null != tableElements)
                elements.addAll(tableElements);
        }

        return elements;
    }

    private Document parseHtmlToDocument(final String html) throws UnsupportedEncodingException
    {
        Writer errorWriter = null;

        try
        {
            Tidy tidy = new Tidy();

            tidy.setErrout(new PrintWriter(errorWriter = new StringWriter())); /* We don't want to see warning messages in logs */
            tidy.setQuiet(true);
            tidy.setXHTML(true);
            tidy.setMakeClean(true);
            tidy.setInputEncoding("UTF-8");

            org.w3c.dom.Document w3cDoc = tidy.parseDOM(new ByteArrayInputStream(html.getBytes("UTF-8")), null);

            DOMReader domReader = new DOMReader();
            return domReader.read(w3cDoc);
            
        }
        finally
        {
            IOUtils.closeQuietly(errorWriter);

            if (null != errorWriter && logger.isDebugEnabled())
                logger.debug("JTidy parse errors: " + SystemUtils.LINE_SEPARATOR + errorWriter.toString());
        }
    }

    private int getLargestColumnSize(List<List<CellValue>> data)
    {
        int largestColumnSize = 0;

        for (List<CellValue> row : data)
        {
            if (row.size() > largestColumnSize)
                largestColumnSize = row.size();
        }

        return largestColumnSize;
    }

    private CellValue[][] convertListDataToArray(List<List<CellValue>> data)
    {
        int largestColumnSize = getLargestColumnSize(data);
        CellValue[][] dataArray = new CellValue[data.size()][largestColumnSize];

        for (int rowIndex = 0, rowSize = data.size(); rowIndex < rowSize; ++rowIndex)
        {
            List<CellValue> row = data.get(rowIndex);
            int columnIndex = 0;
            int columnSize = row.size();

            for (; columnIndex < columnSize; ++columnIndex)
                dataArray[rowIndex][columnIndex] = row.get(columnIndex);

            for (; columnIndex < columnSize; ++columnIndex)
                dataArray[rowIndex][columnIndex] = null;
        }

        return dataArray;
    }

    private void collectAllTableElements(Element element, List<Element> collected)
    {
        if (StringUtils.equalsIgnoreCase("table", element.getName()))
            collected.add(element);

        for (Element childElement : (List<Element>) element.elements())
            collectAllTableElements(childElement, collected);
    }

    public List<CellValue[][]> parseHtmlToGrids(String html, int ... selectedTables) throws UnsupportedEncodingException
    {
        Document document = parseHtmlToDocument(new StringBuilder("<div>").append(html).append("</div>").toString());
        List<Element> tableElements = new ArrayList<Element>();
        List<CellValue[][]> grids = new ArrayList<CellValue[][]>();

        collectAllTableElements(document.getRootElement(), tableElements);

        for (int selectedTable : selectedTables)
        {
            int selectedTableIndex = selectedTable - 1;

            if (selectedTableIndex >= 0 && selectedTableIndex < tableElements.size())
            {
                List<List<CellValue>> data = parseTable(tableElements.get(selectedTableIndex));
                CellValue[][] dataArray = convertListDataToArray(data);

                grids.add(dataArray);
            }
        }

        return grids;
    }

    public List<CellValue[][]> parseHtmlToGrids(String html, String ... xpathSelectors) throws UnsupportedEncodingException
    {
        Document document = parseHtmlToDocument(new StringBuilder("<div>").append(html).append("</div>").toString());
        List<Element> tableElements = getTableElements(document, xpathSelectors);

        List<CellValue[][]> grids = new ArrayList<CellValue[][]>();

        for (Element tableElement : tableElements)
        {
            List<List<CellValue>> data = parseTable(tableElement);
            CellValue[][] dataArray = convertListDataToArray(data);

            grids.add(dataArray);
        }

        return grids;
    }
}
