/**
 * 
 */
package com.nitobi.jsp.treegrid;

import javax.servlet.jsp.JspException;
import com.nitobi.jsp.NitobiBodyTag;

/**
 * @author mhan
 * @jsp.tag name="datasources"
 * description="A tag to output a ntb:datasources element"
 *
 */
public class DataSources extends NitobiBodyTag 
{
	private static final long serialVersionUID = 1765995592127589621L;

	public int doStartTag() throws JspException
	{
		writeDataSourcesStartTag();
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException
	{
		writeDataSourcesEndTag();
		return EVAL_PAGE;
	}
	
	private void writeDataSourcesStartTag() throws JspException
	{
		try
		{
			pageContext.getOut().print("<ntb:datasources>");
		}
		catch (java.io.IOException e)
		{
			throw new JspException(e);
		}
	}
	
	private void writeDataSourcesEndTag() throws JspException
	{
		try
		{
			pageContext.getOut().print("</ntb:datasources>");
		}
		catch (java.io.IOException e)
		{
			throw new JspException(e);
		}
	}
}
