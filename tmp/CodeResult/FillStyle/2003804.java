/*
 * Copyright (C) 2009 Tom Barber
 *
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation; either version 2 of the License, or (at your option) 
 * any later version.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 *
 */

package org.pentaho.pat.client.ui.panels;

import java.util.Map;

import org.gwt.mosaic.ui.client.LayoutComposite;
import org.gwt.mosaic.ui.client.MessageBox;
import org.gwt.mosaic.ui.client.PopupMenu;
import org.gwt.mosaic.ui.client.ScrollLayoutPanel;
import org.gwt.mosaic.ui.client.ToolButton;
import org.gwt.mosaic.ui.client.WindowPanel;
import org.gwt.mosaic.ui.client.ToolButton.ToolButtonStyle;
import org.gwt.mosaic.ui.client.layout.BoxLayout;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData;
import org.gwt.mosaic.ui.client.layout.LayoutPanel;
import org.gwt.mosaic.ui.client.layout.BorderLayout.Region;
import org.gwt.mosaic.ui.client.layout.BoxLayout.Orientation;
import org.gwt.mosaic.ui.client.layout.BoxLayoutData.FillStyle;
import org.gwt.mosaic.ui.client.util.ButtonHelper;
import org.gwt.mosaic.ui.client.util.ButtonHelper.ButtonLabelType;
import org.pentaho.pat.client.Pat;
import org.pentaho.pat.client.listeners.IQueryListener;
import org.pentaho.pat.client.ui.widgets.MDXRichTextArea;
import org.pentaho.pat.client.ui.windows.SaveWindow;
import org.pentaho.pat.client.util.Operation;
import org.pentaho.pat.client.util.PanelUtil;
import org.pentaho.pat.client.util.factory.EventFactory;
import org.pentaho.pat.client.util.factory.MessageFactory;
import org.pentaho.pat.client.util.factory.ServiceFactory;
import org.pentaho.pat.rpc.dto.CellDataSet;
import org.pentaho.pat.rpc.dto.CubeConnection;
import org.pentaho.pat.rpc.dto.enums.DrillType;
import org.pentaho.pat.rpc.dto.query.IAxis;
import org.pentaho.pat.rpc.dto.query.PatQueryAxis;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;

/**
 * Creates a properties panel, the properties panel controls things like drill method and pivot.
 * 
 * @created Aug 8, 2009
 * @since 0.5.0
 * @author tom(at)wamonline.org.uk
 * 
 */
public class PropertiesPanel extends LayoutComposite implements IQueryListener {

	private boolean executemode = false;
	
	private final Button executeButton;
	
	private final ToolButton exportButton;
	
	private final ToolButton exportCdaButton;

	private final ToolButton mdxButton;
	
	private final ToolButton hideBlanksButton;
	
	private final ToolButton pivotButton;

	private final ToolButton layoutMenuButton;
	
	private final ToolButton drillReplaceButton;
	
	private final ToolButton drillPositionButton;
	
	private final ToolButton drillUpButton;
	
	private final ToolButton drillNoneButton;
    
    
	private final ToolButton drillThroughButton;
	
	private final String queryId;
	
    private class LayoutCommand implements Command {

        private final Region region;

        
        public LayoutCommand(final Region region) {
            this.region = region;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.google.gwt.user.client.Command#execute()
         */
        public void execute() {
            dataPanel.chartPosition(region);

        }

    }

    private static class DrillCommand implements Command {

        private final DrillType drillType;

        public DrillCommand(final DrillType drillType) {
            this.drillType = drillType;
        }

        /*
         * (non-Javadoc)
         * 
         * @see com.google.gwt.user.client.Command#execute()
         */
        public void execute() {
            Pat.setDrillType(drillType);

        }

    }

    private final DataPanel dataPanel;

    /** Form element name of the file component. */
    private static final String FORM_NAME_QUERY = "query"; //$NON-NLS-1$

    /** Submit method of the Connection form. */
    private static final String FORM_METHOD = "POST"; //$NON-NLS-1$

    /** Defines the action of the form. */
    private static String FORM_ACTION = "export"; //$NON-NLS-1$
    static {
        if (GWT.getModuleBaseURL().indexOf("content/pat")> -1) {
            String url = GWT.getModuleBaseURL().substring(0, GWT.getModuleBaseURL().indexOf("content/pat")+11) + "/export";
            FORM_ACTION = url;
        }
    }


    /**
     * PropertiesPanel Constructor.
     * 
     * @param dPanel
     * 
     */
    public PropertiesPanel(final DataPanel dPanel, PanelUtil.PanelType pType) {
        super();
        this.dataPanel = dPanel;
        this.queryId = Pat.getCurrQuery();
        
        EventFactory.getQueryInstance().addQueryListener(this);
        
        final LayoutPanel rootPanel = getLayoutPanel();

        final ScrollLayoutPanel mainPanel = new ScrollLayoutPanel();
        mainPanel.addStyleName("pat-propertiesPanel"); //$NON-NLS-1$
        mainPanel.setLayout(new BoxLayout(Orientation.HORIZONTAL));

        final FormPanel formPanel = new FormPanel();
        formPanel.setAction(FORM_ACTION);
        formPanel.setMethod(FORM_METHOD);
        //formPanel.setEncoding(FORM_ENCODING);

        Hidden curQuery = new Hidden(FORM_NAME_QUERY);
        curQuery.setName(FORM_NAME_QUERY);
        curQuery.setValue(queryId);
        formPanel.add(curQuery);
        
        executeButton = new Button(Pat.IMAGES.execute_no_ds().getHTML());
        executeButton.setTitle(Pat.CONSTANTS.executeQuery());
        executeButton.addClickHandler(new ClickHandler() {

            public void onClick(final ClickEvent arg0) {
            	if(executemode==false){
            		Pat.executeQuery(PropertiesPanel.this, queryId);
            		setExecuteButton(true);
            		dPanel.swapWindows();
            	}
            	else{
            	    setExecuteButton(false);
            		dPanel.swapWindows();
            	}
            }

        });
        

        exportButton = new ToolButton(Pat.CONSTANTS.export());
        exportButton.setTitle(Pat.CONSTANTS.export());

        exportButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent arg0) {
                formPanel.submit();
            }
        });

        exportButton.setEnabled(false);
        
        
        exportCdaButton = new ToolButton("CDA");
        exportCdaButton.setTitle(Pat.CONSTANTS.export() + " as CDA");

        exportCdaButton.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent arg0) {
                SaveWindow.displayCDA();
            }
        });

        exportCdaButton.setEnabled(false);

        mdxButton = new ToolButton(Pat.CONSTANTS.mdx());
        mdxButton.setTitle(Pat.CONSTANTS.showMDX());
        mdxButton.addClickHandler(new ClickHandler() {

            public void onClick(final ClickEvent arg0) {
                ServiceFactory.getQueryInstance().getMdxForQuery(Pat.getSessionID(), queryId,
                        new AsyncCallback<String>() {

                    public void onFailure(final Throwable arg0) {
                        MessageBox.error(Pat.CONSTANTS.error(),arg0.getLocalizedMessage());
                    }

                    public void onSuccess(final String mdx) {
                        final WindowPanel winPanel = new WindowPanel(Pat.CONSTANTS.mdx());
                        final LayoutPanel wpLayoutPanel = new LayoutPanel(new BoxLayout(Orientation.VERTICAL));
                        wpLayoutPanel.setSize("450px", "200px"); //$NON-NLS-1$ //$NON-NLS-2$
                        final MDXRichTextArea mdxArea = new MDXRichTextArea();

                        mdxArea.setText(mdx);

                        wpLayoutPanel.add(mdxArea, new BoxLayoutData(1, 0.9));
                        final ToolButton closeBtn = new ToolButton(Pat.CONSTANTS.close());
                        closeBtn.addClickHandler(new ClickHandler() {
                            public void onClick(final ClickEvent arg0) {
                                winPanel.hide();
                            }

                        });
                        final ToolButton mdxBtn = new ToolButton(Pat.CONSTANTS.newMdxQuery());
                        mdxBtn.addClickHandler(new ClickHandler() {
                            public void onClick(final ClickEvent arg0) {

                                final Widget widget = MainTabPanel.getSelectedWidget();
                                if (widget instanceof OlapPanel) {
                                    ((OlapPanel) widget).getCubeItem();
                                    final MdxPanel mdxpanel = new MdxPanel(((OlapPanel) widget).getCubeItem(),
                                            Pat.getCurrConnection(), mdxArea.getText(), null);
                                    MainTabPanel.displayContentWidget(mdxpanel);
                                }

                                winPanel.hide();

                            }
                        });
                        final LayoutPanel wpButtonPanel = new LayoutPanel(new BoxLayout(Orientation.HORIZONTAL));

                        wpButtonPanel.add(mdxBtn);
                        wpButtonPanel.add(closeBtn);
                        wpLayoutPanel.add(wpButtonPanel);
                        wpLayoutPanel.layout();
                        winPanel.add(wpLayoutPanel);
                        winPanel.layout();
                        winPanel.pack();
                        winPanel.setSize("500px", "320px"); //$NON-NLS-1$ //$NON-NLS-2$
                        winPanel.center();

                    }

                });

            }
        });

        hideBlanksButton = new ToolButton(Pat.IMAGES.zero().getHTML());
        hideBlanksButton.setTitle(Pat.CONSTANTS.showBlankCells());
        hideBlanksButton.setStyle(ToolButtonStyle.CHECKBOX);
        hideBlanksButton.setChecked(true);
        hideBlanksButton.addClickHandler(new ClickHandler() {

            public void onClick(final ClickEvent arg0) {
                EventFactory.getQueryInstance().getQueryListeners().fireQueryStartsExecution(PropertiesPanel.this, queryId);
                ServiceFactory.getQueryInstance().setNonEmpty(Pat.getSessionID(), queryId,
                        hideBlanksButton.isChecked(), new AsyncCallback<CellDataSet>() {

                    public void onFailure(final Throwable arg0) {
                        EventFactory.getQueryInstance().getQueryListeners().fireQueryFailedExecution(PropertiesPanel.this, queryId);
                        MessageBox.error(Pat.CONSTANTS.error(), MessageFactory.getInstance().failedNonEmpty());

                    }

                    public void onSuccess(final CellDataSet arg0) {

                        if (hideBlanksButton.isChecked()) {
                            hideBlanksButton.setTitle(Pat.CONSTANTS.showBlankCells());

                        } else {
                            hideBlanksButton.setTitle(Pat.CONSTANTS.hideBlankCells());
                        }
                        EventFactory.getQueryInstance().getQueryListeners().fireQueryExecuted(
                                PropertiesPanel.this, queryId, arg0);
                    }

                });

            }

        });

        hideBlanksButton.setEnabled(false);
        
        final ToolButton createScenarioButton = new ToolButton("Create Scenario");
        createScenarioButton.setStyle(ToolButtonStyle.CHECKBOX);
        createScenarioButton.setEnabled(false);
        createScenarioButton.addClickHandler(new ClickHandler() {

            public void onClick(final ClickEvent arg0) {

                ServiceFactory.getQueryInstance().alterCell(queryId, Pat.getSessionID(), Pat.getCurrScenario(), Pat.getCurrConnectionId(), "123", new AsyncCallback<CellDataSet>(){

                    public void onFailure(Throwable arg0) {
                        // TODO Auto-generated method stub

                    }

                    public void onSuccess(CellDataSet arg0) {
                        Pat.executeQuery(PropertiesPanel.this, queryId);
                    }

                });

                /*ServiceFactory.getSessionInstance().createNewScenario(Pat.getSessionID(), Pat.getCurrConnection(), new AsyncCallback<String>(){
                    public void onFailure(final Throwable arg0){
                	MessageBox.error(Pat.CONSTANTS.error(), "Failed to set scenario");
                    }

                    public void onSuccess(String scenario){
                	createScenarioButton.setText(scenario);
                	Pat.setCurrScenario(scenario);
                    }
                });*/
            }
        });
        
        pivotButton = new ToolButton(Pat.CONSTANTS.pivot());
        pivotButton.setTitle(Pat.CONSTANTS.pivot());
        pivotButton.setStyle(ToolButtonStyle.CHECKBOX);
        pivotButton.addClickHandler(new ClickHandler() {

            public void onClick(final ClickEvent arg0) {
                EventFactory.getQueryInstance().getQueryListeners().fireQueryStartsExecution(PropertiesPanel.this, queryId);
                
                ServiceFactory.getQueryInstance().swapAxis(Pat.getSessionID(), queryId,
                        new AsyncCallback<CellDataSet>() {

                    public void onFailure(final Throwable arg0) {

                        EventFactory.getQueryInstance().getQueryListeners().fireQueryFailedExecution(PropertiesPanel.this, queryId);
                        MessageBox.error(Pat.CONSTANTS.error(), MessageFactory.getInstance()
                                .failedPivot(arg0.getLocalizedMessage()));
                    }

                    public void onSuccess(final CellDataSet arg0) {

                        EventFactory.getQueryInstance().getQueryListeners().fireQueryExecuted(
                                PropertiesPanel.this, queryId, arg0);
                        

                    }

                });

            }
        });        
        pivotButton.setEnabled(false);

        layoutMenuButton = new ToolButton(Pat.IMAGES.chart_pie().getHTML());
        layoutMenuButton.setTitle(Pat.CONSTANTS.chart());
        layoutMenuButton.setStyle(ToolButtonStyle.MENU);
        layoutMenuButton.setEnabled(false);
        
        final PopupMenu layoutMenuBtnMenu = new PopupMenu();
        layoutMenuBtnMenu.addItem(Pat.CONSTANTS.grid(), new LayoutCommand(null));
        layoutMenuBtnMenu.addItem(Pat.CONSTANTS.chart(), new LayoutCommand(Region.CENTER));
        layoutMenuBtnMenu.addItem(Pat.CONSTANTS.top(), new LayoutCommand(Region.NORTH));
        layoutMenuBtnMenu.addItem(Pat.CONSTANTS.bottom(), new LayoutCommand(Region.SOUTH));
        layoutMenuBtnMenu.addItem(Pat.CONSTANTS.left(), new LayoutCommand(Region.WEST));
        layoutMenuBtnMenu.addItem(Pat.CONSTANTS.right(), new LayoutCommand(Region.EAST));

        layoutMenuButton.setMenu(layoutMenuBtnMenu);

        
        drillPositionButton = new ToolButton(ButtonHelper.createButtonLabel(Pat.IMAGES.drill(),"", ButtonLabelType.NO_TEXT));
        drillPositionButton.setTitle(Pat.CONSTANTS.drillPosition());
        drillPositionButton.setStyle(ToolButtonStyle.RADIO);
        drillPositionButton.setEnabled(false);
        drillPositionButton.setChecked(true);
        drillPositionButton.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                drillReplaceButton.setChecked(false);
                drillUpButton.setChecked(false);
                drillNoneButton.setChecked(false);
                drillPositionButton.setChecked(true);

                (new DrillCommand(DrillType.POSITION)).execute();
                EventFactory.getOperationInstance().getOperationListeners().fireDrillStyleChanged(PropertiesPanel.this, queryId, DrillType.POSITION);
            }
        });
        
        drillReplaceButton = new ToolButton(ButtonHelper.createButtonLabel(Pat.IMAGES.arrow_down(),"", ButtonLabelType.NO_TEXT));
        drillReplaceButton.setTitle(Pat.CONSTANTS.drillReplace());
        drillReplaceButton.setStyle(ToolButtonStyle.RADIO);
        drillReplaceButton.setEnabled(false);
        drillReplaceButton.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                drillPositionButton.setChecked(false);
                drillUpButton.setChecked(false);
                drillNoneButton.setChecked(false);
                drillReplaceButton.setChecked(true);
                
                (new DrillCommand(DrillType.REPLACE)).execute();
                EventFactory.getOperationInstance().getOperationListeners().fireDrillStyleChanged(PropertiesPanel.this, queryId, DrillType.REPLACE);
            }
        });
        
        drillUpButton = new ToolButton(ButtonHelper.createButtonLabel(Pat.IMAGES.arrow_up(),"", ButtonLabelType.NO_TEXT));
        drillUpButton.setTitle(Pat.CONSTANTS.drillUp());
        drillUpButton.setStyle(ToolButtonStyle.RADIO);
        drillUpButton.setEnabled(false);
        drillUpButton.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                drillPositionButton.setChecked(false);
                drillReplaceButton.setChecked(false);
                drillNoneButton.setChecked(false);
                drillUpButton.setChecked(true);

                (new DrillCommand(DrillType.UP)).execute();
                EventFactory.getOperationInstance().getOperationListeners().fireDrillStyleChanged(PropertiesPanel.this, queryId, DrillType.UP);
            }
        });
        
        drillNoneButton = new ToolButton(Pat.CONSTANTS.drillNone());
        drillNoneButton.setStyle(ToolButtonStyle.RADIO);
        drillNoneButton.setEnabled(false);
        drillNoneButton.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                drillPositionButton.setChecked(false);
                drillReplaceButton.setChecked(false);
                drillUpButton.setChecked(false);
                drillNoneButton.setChecked(true);

                (new DrillCommand(DrillType.NONE)).execute();
                EventFactory.getOperationInstance().getOperationListeners().fireDrillStyleChanged(PropertiesPanel.this, queryId, DrillType.NONE);
            }
        });



        drillThroughButton = new ToolButton(Pat.CONSTANTS.drillThrough());
        drillThroughButton.setTitle(Pat.CONSTANTS.drillThrough());
        drillThroughButton.setEnabled(false);
        drillThroughButton.setStyle(ToolButtonStyle.CHECKBOX);
        
        drillThroughButton.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                if (drillThroughButton.isChecked()) {
                    EventFactory.getOperationInstance().getOperationListeners().fireOperationExecuted(PropertiesPanel.this, queryId,  Operation.ENABLE_DRILLTHROUGH);
                }
                else
                {
                    EventFactory.getOperationInstance().getOperationListeners().fireOperationExecuted(PropertiesPanel.this, queryId, Operation.DISABLE_DRILLTHROUGH);
                }
                
            }
        });
        
        ToolButton syncButton = new ToolButton(Pat.IMAGES.arrow_refresh().getHTML());
        syncButton.setTitle("Refresh Query");
        syncButton.addClickHandler(new ClickHandler() {
            
            public void onClick(ClickEvent arg0) {
                ServiceFactory.getQueryInstance().getSelections(Pat.getSessionID(), queryId, new AsyncCallback<Map<IAxis,PatQueryAxis>>() {

                    public void onFailure(Throwable arg0) {
                        MessageBox.alert("ERROR", "ERROR");
                        
                    }

                    public void onSuccess(Map<IAxis, PatQueryAxis> arg0) {
                        Integer rows = arg0.get(IAxis.ROWS) != null ? arg0.get(IAxis.ROWS).getDimensions().size() : 0;
                        Integer cols = arg0.get(IAxis.COLUMNS) != null ? arg0.get(IAxis.COLUMNS).getDimensions().size() : 0;
                        Integer filter = arg0.get(IAxis.FILTER) != null ? arg0.get(IAxis.FILTER).getDimensions().size() : 0;
                        MessageBox.alert("OK", "rows: "+ rows + " cols:" + cols + " filter:"+ filter);
                    }
                    
                });
                
            }
        });
        
        if (pType == PanelUtil.PanelType.MDX) {
            mainPanel.add(exportButton, new BoxLayoutData(FillStyle.HORIZONTAL));        
            mainPanel.add(layoutMenuButton, new BoxLayoutData(FillStyle.HORIZONTAL));    
        }
        if (pType == PanelUtil.PanelType.QM) {
            mainPanel.add(executeButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            // TODO enable sync button when implemented routines
            //            mainPanel.add(syncButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            mainPanel.add(exportButton, new BoxLayoutData(FillStyle.HORIZONTAL));        
            mainPanel.add(layoutMenuButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            mainPanel.add(drillPositionButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            mainPanel.add(drillReplaceButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            mainPanel.add(drillUpButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            mainPanel.add(drillNoneButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            mainPanel.add(mdxButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            mainPanel.add(hideBlanksButton, new BoxLayoutData(FillStyle.HORIZONTAL));
            mainPanel.add(pivotButton, new BoxLayoutData(FillStyle.HORIZONTAL));
//            mainPanel.add(drillThroughButton, new BoxLayoutData(FillStyle.HORIZONTAL));
//            mainPanel.add(createScenarioButton, new BoxLayoutData(FillStyle.HORIZONTAL));
        }
        if (Pat.isPlugin()) {
            mainPanel.add(exportCdaButton, new BoxLayoutData(FillStyle.HORIZONTAL));
        }
        mainPanel.add(formPanel, new BoxLayoutData(FillStyle.HORIZONTAL));
        rootPanel.add(mainPanel);

    }
    
    private void setExecuteButton(boolean execute) {
        if(execute==true){
            executemode=true;
            executeButton.setTitle(Pat.CONSTANTS.editQuery());
            executeButton.setHTML(Pat.IMAGES.edit_query().getHTML());
        }
        else{
            executeButton.setTitle(Pat.CONSTANTS.executeQuery());
            executeButton.setHTML(Pat.IMAGES.execute_no_ds().getHTML());
            
            executemode=false;
        }
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (Pat.getCurrDrillType() != null ) {
            if (Pat.getCurrDrillType().equals(DrillType.POSITION)) {
                drillReplaceButton.setChecked(false);
                drillPositionButton.setChecked(true);
                drillUpButton.setChecked(false);
                drillNoneButton.setChecked(false);
            }
            if (Pat.getCurrDrillType().equals(DrillType.REPLACE)) {
                drillReplaceButton.setChecked(true);
                drillPositionButton.setChecked(false);            
                drillUpButton.setChecked(false);
                drillNoneButton.setChecked(false);
            }
            if (Pat.getCurrDrillType().equals(DrillType.UP)) {
                drillReplaceButton.setChecked(false);
                drillPositionButton.setChecked(false);            
                drillUpButton.setChecked(true);
                drillNoneButton.setChecked(false);
            }
            if (Pat.getCurrDrillType().equals(DrillType.NONE)) {
                drillReplaceButton.setChecked(false);
                drillPositionButton.setChecked(false);            
                drillUpButton.setChecked(false);
                drillNoneButton.setChecked(true);
            }

        }
        else {
            drillReplaceButton.setChecked(false);
            drillPositionButton.setChecked(true);            
            drillUpButton.setChecked(false);
            drillNoneButton.setChecked(false);
        }
    }
    
    public void onQueryChange(Widget sender, int sourceRow, boolean isSourceRow, IAxis sourceAxis, IAxis targetAxis) {
        // TODO Auto-generated method stub
        
    }

    public void onQueryExecuted(String queryId, CellDataSet matrix) {

        if(queryId == this.queryId) {
            exportButton.setEnabled(true);
            mdxButton.setEnabled(true);
            exportCdaButton.setEnabled(true);
            hideBlanksButton.setEnabled(true);
            pivotButton.setEnabled(true);
            layoutMenuButton.setEnabled(true);
            drillPositionButton.setEnabled(true);
            drillReplaceButton.setEnabled(true);
            drillUpButton.setEnabled(true);
            drillNoneButton.setEnabled(true);
            if (Pat.getCurrConnection().getConnectionType().equals(CubeConnection.ConnectionType.Mondrian)) {
                drillThroughButton.setEnabled(true);
            }
            drillThroughButton.setChecked(false);
            executeButton.setEnabled(true);
            setExecuteButton(true);
        }

    }

    public void onQueryStartExecution(String queryId) {
        if(queryId != null && queryId.equals(this.queryId)) {
            executeButton.setEnabled(false);
        }
        
    }

    public void onQueryFailed(String queryId) {
        if (queryId != null && queryId.equals(this.queryId)) {
            executeButton.setEnabled(true);
            setExecuteButton(false);
        }
        
    }
}
