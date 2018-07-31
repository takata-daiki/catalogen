package net.customware.confluence.flotchart.util;

import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class HtmlScraperTestCase extends TestCase
{
    private HtmlScraper htmlScraper;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        htmlScraper = new HtmlScraper();
    }

    private InputStream getClassPathResourceAsString(String classPath)
    {
        return getClass().getClassLoader().getResourceAsStream(classPath);
    }

    private String getClassPathResourceAsSring(String classPath, String encoding) throws IOException
    {
        Reader r = null;
        Writer w = null;

        try
        {
            r = new InputStreamReader(getClassPathResourceAsString(classPath), encoding);
            w = new StringWriter();

            IOUtils.copy(r, w);

            return w.toString();
        }
        finally
        {
            IOUtils.closeQuietly(w);
            IOUtils.closeQuietly(r);
        }
    }

    public void testParseToHtmlGridsWithNumericTableSelection() throws IOException
    {
        String html = getClassPathResourceAsSring("net/customware/confluence/flotchart/util/HtmlScraperTestData1.html", "UTF-8");
        List<CellValue[][]> grids = htmlScraper.parseHtmlToGrids(html, 2, 3);

        List<CellValue[][]> expectedGrids = new ArrayList<CellValue[][]>();

        expectedGrids.add(
                new CellValue[][] {
                        new CellValue[] { new CellValue("1.1"), new CellValue("1.2"), new CellValue("1.3") }
                }
        );
        expectedGrids.add(
                new CellValue[][] {
                        new CellValue[] { new CellValue("5"), new CellValue("6"), new CellValue("7") },
                        new CellValue[] { new CellValue("8"), new CellValue("9"), new CellValue("10") }
                }
        );

        assertEquals(expectedGrids.size(), grids.size());

        for (int i = 0; i < expectedGrids.size(); ++i)
            assertTrue(ArrayUtils.isEquals(expectedGrids.get(i), grids.get(i)));
    }

    public void testParseToHtmlGridsWithXpathTableSelection() throws IOException
    {
        String html = getClassPathResourceAsSring("net/customware/confluence/flotchart/util/HtmlScraperTestData1.html", "UTF-8");
        List<CellValue[][]> grids = htmlScraper.parseHtmlToGrids(html, "//table//table", "//table[2]");

        List<CellValue[][]> expectedGrids = new ArrayList<CellValue[][]>();

        expectedGrids.add(
                new CellValue[][] {
                        new CellValue[] { new CellValue("1.1"), new CellValue("1.2"), new CellValue("1.3") }
                }
        );
        expectedGrids.add(
                new CellValue[][] {
                        new CellValue[] { new CellValue("5"), new CellValue("6"), new CellValue("7") },
                        new CellValue[] { new CellValue("8"), new CellValue("9"), new CellValue("10") }
                }
        );

        assertEquals(expectedGrids.size(), grids.size());

        for (int i = 0; i < expectedGrids.size(); ++i)
            assertTrue(ArrayUtils.isEquals(expectedGrids.get(i), grids.get(i)));
    }
}
