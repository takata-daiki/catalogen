package net.customware.confluence.flotchart.util;

/**
 * Represents a value read from a &lt;td&gt; in a table by the {@link net.customware.confluence.flotchart.util.HtmlScraper}.
 */
public class CellValue
{
    /** The element text (text from the node, and all of its descendants) */
    private final String text;

    /**
     * If the first element in the table cell is a link, this will have the value of its &quot;href&quot; attribute.
     */
    private final String link;

    public CellValue(String text, String link)
    {
        this.text = text;
        this.link = link;
    }

    public CellValue(String text)
    {
        this(text, null);
    }

    public String getText()
    {
        return text;
    }

    public String getLink()
    {
        return link;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellValue that = (CellValue) o;

        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
