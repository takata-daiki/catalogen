package at.medstock.web.gui.pages;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

public class Footer extends Panel {
		
	public Footer(String id) {
		super(id);
		add(new FeedbackPanel("feedback"));
	}

}
