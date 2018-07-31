/**
 * 
 */
package com.nitobi.jsp.grid;

import javax.servlet.jsp.JspException;
import com.nitobi.jsp.NitobiBodyTag;

/**
 * @author mhan
 * @jsp.tag name="columns"
 * description="A tag to output a ntb:columns element"
 *
 */
public class Columns extends NitobiBodyTag 
{
	private static final long serialVersionUID = -8447852978263396832L;

	public int doStartTag() throws JspException
	{
		writeColumnsStartTag();
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException
	{
		writeColumnsEndTag();
		return EVAL_PAGE;
	}
	
	private void writeColumnsStartTag() throws JspException
	{
		try
		{
			pageContext.getOut().print("<ntb:columns>");
		}
		catch (java.io.IOException e)
		{
			throw new JspException(e);
		}
	}
	
	private void writeColumnsEndTag() throws JspException
	{
		try
		{
			pageContext.getOut().print("</ntb:columns>");
		}
		catch (java.io.IOException e)
		{
			throw new JspException(e);
		}
	}
}
