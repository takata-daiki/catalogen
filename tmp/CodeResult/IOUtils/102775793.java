/*-
 * #%L
 * Detachment Core Logic
 * %%
 * Copyright (C) 2010 - 2017 COMSOFT, JSC
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.comsoft.groovy.admin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.comsoft.ria.ConversationIdContainer;
import org.comsoft.ria.VUtils;
import org.comsoft.system.downloads.FileDownloadContext;
import org.comsoft.system.downloads.FileDownloadManager;
import org.comsoft.system.excel.ExcelParser;
import org.comsoft.system.i18n.Messages;
import org.comsoft.test.SimpleProfiler;
import org.comsoft.test.SimpleProfilerStatistic;
import org.comsoft.util.DateUtils;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Name("staticticsFrame")
public class StaticticsFrame extends Panel implements ConversationIdContainer, GroupAdministrationFrame {

	private static final long serialVersionUID = 1L;

	@Logger
	Log log;

	private String cid;

	private CheckBox dostats;

	private Table table = null;
	
	private TextField filter;

	private VerticalLayout mainLayout;

	@Override
	public void initComponent() {
		/*
		if (getWindow() != null) {
			((VerticalLayout) getWindow().getContent()).setMargin(false);
			TopMenuFrame.addToFrame(this);
		}

		if (getParent() != null) {
			getParent().setSizeFull();
		}*/

		setSizeFull();
		//getContent().setSizeFull();
		//((VerticalLayout) getContent()).setMargin(false);
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		
		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		setContent(mainLayout);
		
		dostats = new CheckBox();
		dostats.setCaption(Messages.getString("org.comsoft.StaticticsFrame.Caption1")); //$NON-NLS-1$
		
		dostats.setImmediate(true);
		dostats.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				log.info("Set stats to #0", dostats.getValue()); //$NON-NLS-1$
				SimpleProfiler.setDoStats((Boolean) dostats.getValue());				
			}
		});
		layout.addComponent(dostats);
		mainLayout.addComponent(layout);
		
		filter = new TextField();
		filter.setImmediate(true);
		filter.setInputPrompt(Messages.getString("org.comsoft.StaticticsFrame.Prompt1")); //$NON-NLS-1$
		filter.setTextChangeEventMode(TextChangeEventMode.TIMEOUT);
		filter.setTextChangeTimeout(300);
		filter.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				refreshStatsTable();
				
			}
		});
		
		layout.addComponent(filter);
		

		Button refreshButton = new Button(Messages.getString("org.comsoft.StaticticsFrame.BtnUpdate")); //$NON-NLS-1$
		layout.addComponent(refreshButton);
		refreshButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(Button.ClickEvent event) {
				log.info("refresh stats "); //$NON-NLS-1$
				refreshStatsTable();
			}
		});
		Button clearButton = new Button(Messages.getString("org.comsoft.StaticticsFrame.BtnDrop")); //$NON-NLS-1$
		clearButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(Button.ClickEvent event) {
				log.info("clear stats "); //$NON-NLS-1$
				SimpleProfiler.clearStats();
				refreshStatsTable();
			}
		});
		layout.addComponent(clearButton);

		Button exportButton = new Button(Messages.getString("org.comsoft.StaticticsFrame.Btn1"), new Button.ClickListener() { //$NON-NLS-1$
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				exportToExcel();
			}
		});
		layout.addComponent(exportButton);

		refreshStatsTable();

		((VerticalLayout) getContent()).setExpandRatio(table, 1.0f);
		
	}

	@SuppressWarnings("unchecked")
	protected void refreshStatsTable() {
		BeanItemContainer<SimpleProfilerStatistic> container = null;
		if (table == null) {
			table = new Table();
			table.setSelectable(true);
			container = new BeanItemContainer<SimpleProfilerStatistic>(SimpleProfilerStatistic.class);
			table.setSizeFull();
			table.setContainerDataSource(container);
			table.setVisibleColumns((Object[])"blockName,avg,times,summary".split(",")); //$NON-NLS-1$ //$NON-NLS-2$
			mainLayout.addComponent(table);
		} else
			container = (BeanItemContainer<SimpleProfilerStatistic>) table.getContainerDataSource();
		container.removeAllItems();
		String filterStr = (String) filter.getValue();
		if (StringUtils.isNotEmpty(filterStr)) {

			for (SimpleProfilerStatistic s : SimpleProfiler.getStatistics())
				if (s.getBlockName().toLowerCase().contains(filterStr.toLowerCase()))
					container.addBean(s);
		}
		else  container.addAll(SimpleProfiler.getStatistics());
		dostats.setValue(SimpleProfiler.isDoStats());

	}

	protected void exportToExcel() {
		try {
			List<SimpleProfilerStatistic> dataToExport = new ArrayList<SimpleProfilerStatistic>();
			@SuppressWarnings("unchecked")
			BeanItemContainer<SimpleProfilerStatistic> container = (BeanItemContainer<SimpleProfilerStatistic>)table.getContainerDataSource();
			dataToExport.addAll(container.getItemIds());
			if (dataToExport.isEmpty()) {
				Notification.show(Messages.getString("org.comsoft.StaticticsFrame.Notification")); //$NON-NLS-1$
				return;
			}

			List<String> fields = new ArrayList<String>();
			List<String> headers = new ArrayList<String>();

			for (Object column : table.getVisibleColumns()) {
				fields.add(column.toString());
				headers.add(column.toString());
			}

			final InputStream inputStream = new ByteArrayInputStream(ExcelParser.instance().createXls(dataToExport, fields, headers, null));

			FileDownloadManager downloadManager = FileDownloadManager.instance();

			FileDownloadContext downloadContext = downloadManager.prepareDownloadContext();
			try {
				downloadContext.setFilename(DateUtils.formatDate(new Date(), "yyyyMMdd-HHmmss-") + "simpleProfilerStatistics.xls"); //$NON-NLS-1$ //$NON-NLS-2$
				downloadContext.setMimeType("application/vnd.ms-excel"); //$NON-NLS-1$
				IOUtils.copy(inputStream, downloadContext.getOutputStream());
			} finally {
				downloadContext.closeOutputStream();
			}

			//HttpServletRequest req = ((EmbApp) getWindow().getApplication()).getRequest();
			String href = VaadinService.getCurrentRequest().getContextPath() + downloadManager.getDownloadURLSuffix(downloadContext.getIdentifier());
			System.out.println("open " + href); //$NON-NLS-1$
			JavaScript.getCurrent().execute("window.open('" + href + "');"); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (Exception e) {
			e.printStackTrace();
			VUtils.showException(e, Messages.getString("org.comsoft.StaticticsFrame.Exception")); //$NON-NLS-1$
		}
	}

	@Override
	public String getAdministrationFrameCaption() {
		
		return Messages.getString("org.comsoft.StaticticsFrame.Caption2"); //$NON-NLS-1$
	}

	@Override
	public String getCid() {
		
		return cid;
	}

	@Override
	public void setCid(String cid) {
		this.cid = cid;
		
	}

	@Override
	public String getPermissionTarget() {
		return ADMINISTRATION_FRAME_TARGET_PREFIX + "staticticsFrame"; //$NON-NLS-1$
	}

	@Override
	public List<String> getPermissionActions() {
		return Collections.singletonList(ADMINISTRATION_FRAME_DEFAULT_ACTION);
	}

	@Override
	public boolean hasPermission() {
		return Identity.instance().hasPermission(getPermissionTarget(), ADMINISTRATION_FRAME_DEFAULT_ACTION);
	}

	@Override
	public String getAdministrationFrameGroup() {
		return Messages.get(ADMINISTRATION_FRAME_GROUP_DEBUG_MESSAGE);
	}
	
	@Override
	public Resource getIcon() {
		return FontAwesome.BAR_CHART;
	}

}
