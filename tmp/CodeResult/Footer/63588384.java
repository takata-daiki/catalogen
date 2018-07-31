package org.elsys.project.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Footer{
	
	FlowPanel footerBox = new FlowPanel();	

	public Footer()	{
		footerBox.setStyleName("footerBox");		
			
			FlowPanel footer = new FlowPanel();
			footer.setStyleName("footer");
			
				Anchor stoyanNaydenov = new Anchor("Stoyan Naydenov","http://bg.linkedin.com/pub/stoyan-naydenov/20/4a9/423","_blank");
				Anchor hristoHristov = new Anchor("Hristo Hristov","http://hristof.com/about","_blank");	
				
				Label rightsReserved = new Label("All rights reserved!");
				rightsReserved.setStyleName("rightsReserved");
				
				footer.add(stoyanNaydenov);
				footer.add(hristoHristov);
				footer.add(rightsReserved); 
				
			footerBox.add(footer);		
		
		setAsFinished();
	}
	
	boolean classFinished=false;
	
	void setAsFinished(){
		classFinished = true;
	}
	
	boolean isFinished()
	{
		return classFinished;
	}
	
	Widget getWidget()
	{
		FlowPanel ft = new FlowPanel();
		ft.add(footerBox);
		return ft;
	}
}
