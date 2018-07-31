package com.eagleminer.client.panels.identify;

import com.eagleminer.client.widgets.buttons.OrangeButton;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;

public class EagleLoginPanel extends SimplePanel {
	private static final int N_ROWS = 5;
	private static final int MAX_CHARACTERS = 32;
	
	private TextBox userName;
	private PasswordTextBox password;
	public Button completeButton;

	public EagleLoginPanel() {
		addStyleName("roundBottom8");
		addStyleName("blackGradient1");
		setSize("100%", "100%");

		Grid grid = new Grid(N_ROWS, 2);
		grid.setSize("100%", "100%");
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		grid.getElement().getStyle().setPadding(10, Unit.PX);
		grid.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
		add(grid);

		Label widget;

		widget = new Label("User Name:");
		widget.getElement().getStyle().setColor("white");
		grid.setWidget(0, 0, widget);

		userName = new TextBox();
		userName.setMaxLength(MAX_CHARACTERS);
		userName.setVisibleLength(MAX_CHARACTERS);
		userName.addStyleName("borderRadius5");
		grid.setWidget(0, 1, userName);

		widget = new Label("Password:");
		widget.getElement().getStyle().setColor("white");
		grid.setWidget(1, 0, widget);

		password = new PasswordTextBox();
		password.setMaxLength(MAX_CHARACTERS);
		password.setVisibleLength(MAX_CHARACTERS);
		password.addStyleName("borderRadius5");
		grid.setWidget(1, 1, password);

		grid.setWidget(2, 0, new Label(" "));

		completeButton = new OrangeButton("Login!");
		completeButton.setWidth("80px");
		completeButton.setHeight("30px");
		grid.setWidget(2, 1, completeButton);

		grid.setWidget(3, 0, new Label(" "));

		widget = new Label("Not a member? Quick signup");
		widget.getElement().getStyle().setColor("white");
		grid.setWidget(3, 1, widget);
		
		userName.setFocus(true);
		
		final CellFormatter cellFormatter = grid.getCellFormatter();

		cellFormatter.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		cellFormatter.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_LEFT);
		
		cellFormatter.setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_JUSTIFY);
		cellFormatter.setHorizontalAlignment(1, 1,
				HasHorizontalAlignment.ALIGN_JUSTIFY);
		
		cellFormatter.setHorizontalAlignment(2, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
		cellFormatter.setHorizontalAlignment(3, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
	}

	public String getUserName() {
		return userName.getText();
	}

	public String getPassword() {
		return password.getText();
	}
}
