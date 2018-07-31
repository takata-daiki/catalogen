/**
 * 
 */
package com.nitobi.jsp.treegrid;

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

	private String id;
	private String gethandler;
	private String savehandler;
	
	/**
	 * @jsp.attribute required="false" 
	 * 				  rtexprvalue="true" 
	 * 				  description="The id attribute" 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @jsp.attribute required="false" 
	 * 				  rtexprvalue="true" 
	 * 				  description="The gethandler attribute" 
	 * @return the gethandler
	 */
	public String getGethandler() {
		return gethandler;
	}

	/**
	 * @param gethandler the gethandler to set
	 */
	public void setGethandler(String gethandler) {
		this.gethandler = gethandler;
	}

	/**
	 * @jsp.attribute required="false" 
	 * 				  rtexprvalue="true" 
	 * 				  description="The savehandler attribute" 
	 * @return the savehandler
	 */
	public String getSavehandler() {
		return savehandler;
	}

	/**
	 * @param savehandler the savehandler to set
	 */
	public void setSavehandler(String savehandler) {
		this.savehandler = savehandler;
	}

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
			pageContext.getOut().print("<ntb:columns ");
			writeTagAttributes();
			pageContext.getOut().print(">");
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
