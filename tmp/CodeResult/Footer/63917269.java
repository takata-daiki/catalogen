package com.emobot.amigo.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;

public class Footer {
	public Footer() {
		FlowPanel bodyPanel = new FlowPanel();
		bodyPanel.addStyleName("bodyPanel-footer");

		FlowPanel wrap = new FlowPanel();
		wrap.setStyleName("wrap");

		FlowPanel footer = new FlowPanel();
		footer.setStyleName("footer");

		bodyPanel.add(wrap);
		wrap.add(footer);
		footer.add(drawNav());
		footer.add(drawCopyrights());
		
		RootPanel.get().add(bodyPanel);
	}
	
	Command command = new Command() {
		public void execute() {			
		}
	};
	
	public HTML drawCopyrights() {
		HTML copyrights = new HTML("Copyright &copy; 2011. Some rights reserved.");
		copyrights.setStyleName("copyrights");
		
		return copyrights;
	}
	
	public MenuBar drawNav() {
		MenuBar nav = new MenuBar();
		nav.setStyleName("navFooter");
		
		nav.addItem(new MenuItem("Home", command));
		nav.addItem(new MenuItem("About", command));
		nav.addItem(new MenuItem("Terms of use", command));
		nav.addItem(new MenuItem("Contact us", command));
		
		return nav;
	}
}
