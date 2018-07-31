package com.eagleminer.client.panels.register;

import com.eagleminer.client.map.MapEditor;
import com.eagleminer.client.map.TenementOverlay;
import com.eagleminer.client.panels.common.YesNoDialogBox;
import com.eagleminer.client.widgets.buttons.RoundButton;
import com.eagleminer.client.widgets.common.TextFieldTitled;
import com.eagleminer.client.widgets.enums.CountriesBox;
import com.eagleminer.shared.objects.CompanyProject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;

public class ProjectDetailsPanel extends FlexTable {
	//
	// Project's fields.
	//
	private TextFieldTitled projectName;
	private TextFieldTitled country;
	private TextFieldTitled province;
	private TextFieldTitled tenement;
	
	private TabPanel tabs;
	private ProjectCommoditiesEditor commoditiesEditor;
	private ProjectTenementDetailsEditor areaEditor;
	private RegisterDetailsTabsPanel ownerPanel;
	private MapEditor companyMap;

	public ProjectDetailsPanel(MapEditor companyMap, RegisterDetailsTabsPanel ownerPanel) {
		this.ownerPanel = ownerPanel;
		this.companyMap = companyMap;

		setSize("100%", "100%");
		addStyleName("gray");
		setCellSpacing(8);

		final FlexCellFormatter cellFormatter = getFlexCellFormatter();
		int row = 0;
		
		projectName = new TextFieldTitled("Project Name");
		setWidget(row, 0, projectName);
		cellFormatter.setColSpan(row, 0, 2);
		row++;

		country = new TextFieldTitled("Country", "", new CountriesBox());
		setWidget(row, 0, country);
		cellFormatter.setColSpan(row, 0, 1);

		province = new TextFieldTitled("Province/State");
		setWidget(row, 1, province);
		cellFormatter.setColSpan(row, 1, 1);
		row++;

		final TextArea ta = new TextArea();
		ta.addStyleName("border-dark-gray");
		
		tenement = new TextFieldTitled("Short Description of Tenement", "", ta);
		tenement.setRows(2);
		setWidget(row, 0, tenement);
		cellFormatter.setColSpan(row, 0, 2);
		row++;

		tabs = new TabPanel();
		tabs.getDeckPanel().addStyleName("border");
		tabs.getDeckPanel().addStyleName("no-padding");
		tabs.setSize("100%", "100%");
		
		commoditiesEditor = new ProjectCommoditiesEditor();
		tabs.add(commoditiesEditor, "Commodities Details");
		
		areaEditor = new ProjectTenementDetailsEditor(companyMap);
		tabs.add(areaEditor, "Tenement Details");
		
		setWidget(row, 0, tabs);
		cellFormatter.setColSpan(row, 0, 2);
		row++;
		
		setWidget(row, 0, getButtonsPanel());
		cellFormatter.setColSpan(row, 0, 2);
		row++;

		tabs.selectTab(0);
	}

	private HorizontalPanel getButtonsPanel() {
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setSpacing(0);
		
		//
		// Add Additional Project button
		//
		RoundButton addProjectButton = new RoundButton("Add Additional Project");
		addProjectButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ProjectDetailsPanel.this.ownerPanel.addNewProjectPanel();
			}
		});
		buttons.add(addProjectButton);

		final HTML html = new HTML(" ");
		html.setWidth("5px");
		buttons.add(html);
		
		//
		// Remove Project button
		//
		RoundButton removeProjectButton = new RoundButton("Remove Project");
		removeProjectButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final YesNoDialogBox db = new YesNoDialogBox("Do you want to delete the project?");
				db.yesButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						ProjectDetailsPanel.this.ownerPanel.removeProjectPanel();
						db.hide();
					}
				});
				db.noButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						db.hide();
					}
				});
				db.center();
				db.show();
			}
		});
		buttons.add(removeProjectButton);
		return buttons;
	}

	protected void finishCompany() {
		ownerPanel.finishCompany();
	}

	public void setProject(CompanyProject project) {
		projectName.setText(project.projectName);
		country.setText(project.country);
		province.setText(project.province);
		tenement.setText(project.tenement);
		
		commoditiesEditor.putCommodities(project);
		tabs.selectTab(1);
		areaEditor.putLocations(project);
	}

	public CompanyProject getProject() {
		CompanyProject p = new CompanyProject();
		
		p.projectName = projectName.getText();		
		p.country = country.getText();
		p.province = province.getText();
		p.tenement = tenement.getText();
		
		p.locations = areaEditor.getLocations();
		commoditiesEditor.getCommodities(p);
		
		return p;
	}

	public void showLocations() {
		areaEditor.showLocations();
	}
	
	protected void onSelected() {
		companyMap.setVisibleAll(false); 
		
		TenementOverlay ov = companyMap.currentOverlay;
		if (ov == null) return;
		
		ov.setVisible(true);
		
		companyMap.setBounds( ov.getBounds() );
	}
}