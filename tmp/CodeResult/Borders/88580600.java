import org.docx4j.wml.*;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by daniel on 18.04.14.
 */
public class TableDocumentTable {
    public static final int NUM_COLUMNS = 6;


    private ArrayList<String> emptyInner = new ArrayList<String>(){{
        add("");
        add("");
        add("");
        add("");
        add("");
        add("");
    }};
    private ArrayList<ArrayList<String>> empty = new ArrayList<ArrayList<String>>(){{
        add(emptyInner);
        add(emptyInner);
    }};
    private ArrayList<String> relationsHeader = new ArrayList<String>(){{
        add("Relation");
        add("Name der Relation");
        add("Klasse für den Nachbereich der Relation");
        add("Wertebereich");
        add("Kardinalität");
        add("(Empty)");
    }};
    private ArrayList<String> attributesHeader = new ArrayList<String>(){{
        add("Attribute");
        add("Name des Attributs");
        add("Wertebereich des Attributs");
        add("Maßeinheit für das Attribut");
        add("Kardinalität des Attributs");
        add("Ähnlichkeitsmaßstab für das Attribut");
    }};
    private ArrayList<ArrayList<String>> attributes = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> relations = new ArrayList<ArrayList<String>>();

    private String header = "Anonyme Klasse";
    private String headerStyle = "Heading3";
    private String rowHeaderStyle = "Heading4";

    public void addAttribut(ArrayList<String> attribut)
    {
        addColumn(attributes, attribut);
    }

    public void addRelation(ArrayList<String> relation)
    {
        addColumn(relations, relation);
    }

    public void setHeader(String header)
    {
        if(header == null)
        {
            return;
        }

        this.header = header;
    }

    public void setHeaderStyle(String style)
    {
        headerStyle = style;
    }

    public void setRowHeaderStyle(String style)
    {
        rowHeaderStyle = style;
    }

    private void addColumn(ArrayList<ArrayList<String>> dst, ArrayList<String> src)
    {
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < NUM_COLUMNS; i++)
        {
            if(i >= src.size() || src.get(i) == null)
            {
                list.add("");
            }
            else
            {
                list.add(src.get(i));
            }
        }

        dst.add(list);
    }

    public void createTable(TableDocument doc)
    {
        Tbl table = doc.factory.createTbl();
        addBorders(table);
        //addTableRowWithMergedCells(null, "22 Field", "23 Field", table);

        //pack.getMainDocumentPart().addObject(table);

        addTableHeader(doc, table, "Klasse", header);

        //Add relations
        addTableRow(doc, table, relationsHeader.get(0), relationsHeader, rowHeaderStyle);
        if(relations.size() > 0)
        {
            for(ArrayList<String> l : relations)
            {
                addTableRow(doc, table, null, l, null);
            }
        }
        else
        {
            for(ArrayList<String> l : empty)
            {
                addTableRow(doc, table, null, l, null);
            }
        }


        //Add attributes
        addTableRow(doc, table, attributesHeader.get(0), attributesHeader, rowHeaderStyle);
        if(attributes.size() > 0)
        {
            for(ArrayList<String> l : attributes)
            {
                addTableRow(doc, table, null, l, null);
            }
        }
        else
        {
            for(ArrayList<String> l : empty)
            {
                addTableRow(doc, table, null, l, null);
            }
        }

        doc.pack.getMainDocumentPart().addObject(table);

        //To seperate from following tables
        doc.pack.getMainDocumentPart().addParagraphOfText("");
    }

    private void addTableHeader(TableDocument doc, Tbl table, String col0, String col1)
    {
        Tr row = doc.factory.createTr();

        TcPr cellProps = new TcPr();
        TcPrInner.GridSpan span = new TcPrInner.GridSpan();
        span.setVal(BigInteger.valueOf(NUM_COLUMNS - 1));
        cellProps.setGridSpan(span);

        Tc tc1 = doc.factory.createTc();
        //tc1.getContent().add(doc.pack.getMainDocumentPart().createParagraphOfText(col0));
        tc1.getContent().add(doc.pack.getMainDocumentPart().createStyledParagraphOfText(headerStyle, col0));
        ;
        row.getContent().add(tc1);

        Tc tc2 = doc.factory.createTc();
        tc2.setTcPr(cellProps);
        //tc2.getContent().add(doc.pack.getMainDocumentPart().createParagraphOfText((col1)));
        tc2.getContent().add(doc.pack.getMainDocumentPart().createStyledParagraphOfText(headerStyle, col1));
        row.getContent().add(tc2);

        table.getContent().add(row);
    }

    private void addTableRow(TableDocument doc, Tbl table, String col0, ArrayList<String> content, String style)
    {
        Tr row = doc.factory.createTr();

        //First (merged) column
        addMergedColumn(doc, row, col0, style);

        for(int i = 1; i < content.size(); i++)
        {
            addTableCell(doc, row, content.get(i), style);
        }

        table.getContent().add(row);
    }

    private void addMergedColumn(TableDocument doc, Tr row, String content, String style)
    {
        if(content == null)
        {
            addMergedCell(doc, row, "", null, null);
        }
        else
        {
            addMergedCell(doc, row, content, "restart", style);
        }
    }

    private void addMergedCell(TableDocument doc, Tr row, String content, String vMergeVal, String style)
    {
        Tc tableCell = doc.factory.createTc();
        TcPr tableCellProps = new TcPr();
        TcPrInner.VMerge merge = new TcPrInner.VMerge();
        if(vMergeVal != null)
        {
            merge.setVal(vMergeVal);
        }
        tableCellProps.setVMerge(merge);
        tableCell.setTcPr(tableCellProps);

        if(content != null)
        {
            //tableCell.getContent().add(doc.pack.getMainDocumentPart().createParagraphOfText(content));
            if(style == null)
            {
                tableCell.getContent().add(doc.pack.getMainDocumentPart().createParagraphOfText(content));
            }
            else
            {
                tableCell.getContent().add(doc.pack.getMainDocumentPart().createStyledParagraphOfText(style, content));
            }
        }

        row.getContent().add(tableCell);
    }

    private void addTableCell(TableDocument doc, Tr tr, String content, String style)
    {
        Tc tc1 = doc.factory.createTc();
        if(style == null)
        {
            tc1.getContent().add(doc.pack.getMainDocumentPart().createParagraphOfText(content));
        }
        else
        {
            tc1.getContent().add(doc.pack.getMainDocumentPart().createStyledParagraphOfText(style, content));
        }
        tr.getContent().add(tc1);
    }

    private void addBorders(Tbl table) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("auto");
        border.setSz(new BigInteger("4"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);

        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }
}
