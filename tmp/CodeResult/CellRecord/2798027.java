/*
 * Smart GWT (GWT for SmartClient)
 * Copyright 2008 and beyond, Isomorphic Software, Inc.
 *
 * Smart GWT is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.  Smart GWT is also
 * available under typical commercial license terms - see
 * http://smartclient.com/license
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
 
package com.smartgwt.client.widgets.cube;



import com.smartgwt.client.event.*;
import com.smartgwt.client.core.*;
import com.smartgwt.client.types.*;
import com.smartgwt.client.data.*;
import com.smartgwt.client.data.events.*;
import com.smartgwt.client.rpc.*;
import com.smartgwt.client.widgets.*;
import com.smartgwt.client.widgets.events.*;
import com.smartgwt.client.widgets.form.*;
import com.smartgwt.client.widgets.form.validator.*;
import com.smartgwt.client.widgets.form.fields.*;
import com.smartgwt.client.widgets.tile.*;
import com.smartgwt.client.widgets.tile.events.*;
import com.smartgwt.client.widgets.grid.*;
import com.smartgwt.client.widgets.grid.events.*;
import com.smartgwt.client.widgets.chart.*;
import com.smartgwt.client.widgets.layout.*;
import com.smartgwt.client.widgets.layout.events.*;
import com.smartgwt.client.widgets.menu.*;
import com.smartgwt.client.widgets.tab.*;
import com.smartgwt.client.widgets.toolbar.*;
import com.smartgwt.client.widgets.tree.*;
import com.smartgwt.client.widgets.tree.events.*;
import com.smartgwt.client.widgets.viewer.*;
import com.smartgwt.client.widgets.calendar.*;
import com.smartgwt.client.widgets.calendar.events.*;
import com.smartgwt.client.widgets.cube.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.smartgwt.client.util.*;
import com.google.gwt.event.shared.*;
import com.google.gwt.event.shared.HasHandlers;

/**
 * The CubeGrid is an interactive grid component that presents very large, multi-dimensional data sets (also known as data
 * cubes) for reporting or analytic applications. <P> <var class="SmartGWT"> Note that the CubeGrid is implemented as part
 * of the optional Analytics module. Attempting to create a CubeGrid without having the module loaded will throw a
 * RuntimeException. <P> </var> CubeGrids are often called crosstabs, for their cross-tabular display of data dimensions in
 * stacked/nested rows and columns, or pivot tables, for their ability to "pivot" dimensions between rows and columns to
 * view a data cube from different perspectives. They are typically used in the querying and reporting front-ends of data
 * warehousing, decision support, OLAP, and business intelligence systems. <P> <B>Multi-Dimensional Data Terminology</B>
 * <P> The CubeGrid refers to the dimensions of a data cube as facets, to the possible values in each facet as facet
 * values, and to the values within the data cube as data values or cell values. Equivalent terms that are commonly used in
 * data warehousing or business intelligence systems include:<br> <b>facet:</b> dimension, attribute, feature<br> <b>facet
 * value:</b> dimension member, attribute value, feature value<br> <b>cell value:</b> data value, metric value, measure <P>
 * <B>Visual Structure</B> <P> Like the ListGrid and TreeGrid components, the CubeGrid displays data values in a tabular
 * "body" with adjacent "headers".  While the ListGrid and TreeGrid display rows of records with field values, the CubeGrid
 * displays a body of individual cell values, each associated with a combination of facet values. The facet values for a
 * cell are displayed in the column headers above the cell and row headers to the left of the cell. CubeGrids can display
 * an arbitrary number of facets, by stacking multiple levels of row and/or column headers. <P> Except for the innermost
 * column facet, each facet in a CubeGrid has a facet label adjacent to its row or column headers. The facet labels serve
 * two main purposes: they display the titles of the facets, and they provide drag-and-drop reordering or pivoting of
 * facets within the CubeGrid. The row facet labels also provide interactive selection, resizing, and other operations on
 * the columns of row facet values. <P> The innermost column headers provide special behaviors and controls for
 * manipulating the columns of data in a CubeGrid. End users may select, resize, reorder, minimize, maximize, or auto-fit
 * the columns of data via mouse interactions with these headers. Customizable indicators and controls may be included at
 * the top of each innermost column header. <P> If a CubeGrid is not large enough to display all of its cell values,
 * horizontal and/or vertical scrollbars will appear below and to the right of the body. The body of the CubeGrid may be
 * scrolled on either axis. The headers are "frozen" from scrolling on one axis - row headers only scroll vertically, while
 * column headers only scroll horizontally - so the facet values for the visible cells are always displayed. <P> <B>Data
 * Loading</B> <P> Data can be provided to the Cube via  data as an Array of {@link
 * com.smartgwt.client.widgets.cube.CellRecord CellRecords}, each representing the data for one cell. <P> For large
 * datasets, {@link com.smartgwt.client.widgets.cube.CubeGrid#getDataSource provide a DataSource} with one field per
 * facetId, and the CubeGrid will load data on demand to fill the visible area, including lazily loading data for
 * expanding/collapsing tree facets and when facetValues are made visible programmatically or via menus. <P> <B>Picking
 * Facets</B> <P> By "facet" we mean an aspect of the data which is orthogonal to other aspects of the data, that is,
 * combining values from any two "facets" should make sense. <P> For example, in sales data, two facets might be "quarter"
 * and "region" - it makes sense to combine any quarter and region, although for some combinations, there may not be data
 * available. <P>  An example of two aspects that would <b>not</b> be independent facets are "state" and "city" - it's
 * senseless to combine arbitrary states with arbitrary cities - most combinations are invalid.  Consider instead a {@link
 * com.smartgwt.client.widgets.cube.Facet#getIsTree tree facet} that combines "city" and "state" values.   <P> Note that if
 * "city" and "state" are represented as facets, they may look correct if they are both on the same axis of the grid and
 * {@link com.smartgwt.client.widgets.cube.CubeGrid#getHideEmptyFacetValues hideEmptyFacetValues} is used to trim nonsense
 * combinations, but if the data is {@link com.smartgwt.client.widgets.cube.CubeGrid#getCanMoveFacets pivoted} such that
 * "state" and "city" are on opposing axes, there will be a roughly diagonal "stripe" of data for combinations of "state"
 * and "city" that make sense, and all other space will be blank.  This is a strong indication that two facets should be
 * represented as a single tree facet instead.
 * @see com.smartgwt.client.widgets.cube.Facet
 * @see com.smartgwt.client.widgets.cube.FacetValue
 */
public class CubeGrid extends ListGrid  implements com.smartgwt.client.widgets.cube.events.HasFacetAddedHandlers, com.smartgwt.client.widgets.cube.events.HasFacetMovedHandlers, com.smartgwt.client.widgets.cube.events.HasFacetRemovedHandlers, com.smartgwt.client.widgets.cube.events.HasFixedFacetValueChangedHandlers, com.smartgwt.client.widgets.cube.events.HasFacetValueSelectionChangedHandlers, com.smartgwt.client.widgets.cube.events.HasSortByFacetIdHandlers, com.smartgwt.client.widgets.cube.events.HasSortByFacetValuesHandlers {

    public static CubeGrid getOrCreateRef(JavaScriptObject jsObj) {
        if(jsObj == null) return null;
        BaseWidget obj = BaseWidget.getRef(jsObj);
        if(obj != null) {
            return (CubeGrid) obj;
        } else {
            return new CubeGrid(jsObj);
        }
    }

    public CubeGrid(){
        checkAnalyticsLoaded();
					setAutoFetchData(false);
                scClassName = "CubeGrid";
    }

    public CubeGrid(JavaScriptObject jsObj){
        super(jsObj);
    }

    protected native JavaScriptObject create()/*-{
        var config = this.@com.smartgwt.client.widgets.BaseWidget::getConfig()();
        var scClassName = this.@com.smartgwt.client.widgets.BaseWidget::scClassName;
        var widget = $wnd.isc[scClassName].create(config);
        this.@com.smartgwt.client.widgets.BaseWidget::doInit()();
        return widget;
    }-*/;
    // ********************* Properties / Attributes ***********************

    /**
     * Whether alternating rows should be drawn in alternating styles, in order to create a "ledger" effect for easier reading.
     *  If enabled, the cell style for alternate rows will have "Dark" appended to it.
     *
     * @param alternateRecordStyles alternateRecordStyles Default value is true
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setAlternateRecordStyles(Boolean alternateRecordStyles) {
        setAttribute("alternateRecordStyles", alternateRecordStyles, true);
    }

    /**
     * Whether alternating rows should be drawn in alternating styles, in order to create a "ledger" effect for easier reading.
     *  If enabled, the cell style for alternate rows will have "Dark" appended to it.
     *
     *
     * @return Boolean
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public Boolean getAlternateRecordStyles()  {
        return getAttributeAsBoolean("alternateRecordStyles");
    }

    /**
     * If true, when multiple facets appear on one side in a nested headers presentation, the selection state of parent/child
     * headers are automatically kept in sync.
     *
     * @param autoSelectHeaders autoSelectHeaders Default value is true
     */
    public void setAutoSelectHeaders(Boolean autoSelectHeaders) {
        setAttribute("autoSelectHeaders", autoSelectHeaders, true);
    }

    /**
     * If true, when multiple facets appear on one side in a nested headers presentation, the selection state of parent/child
     * headers are automatically kept in sync.
     *
     *
     * @return Boolean
     */
    public Boolean getAutoSelectHeaders()  {
        return getAttributeAsBoolean("autoSelectHeaders");
    }

    /**
     * Whether to select cells in the body when row or column headers are selected.
     *
     * @param autoSelectValues autoSelectValues Default value is "both"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setAutoSelectValues(AutoSelectionModel autoSelectValues)  throws IllegalStateException {
        setAttribute("autoSelectValues", autoSelectValues == null ? null : autoSelectValues.getValue(), false);
    }

    /**
     * Whether to select cells in the body when row or column headers are selected.
     *
     *
     * @return AutoSelectionModel
     */
    public AutoSelectionModel getAutoSelectValues()  {
        return EnumUtil.getEnum(AutoSelectionModel.values(), getAttribute("autoSelectValues"));
    }

    /**
     * Automatically size row headers to fit wrapped text.
     *
     * @param autoSizeHeaders autoSizeHeaders Default value is false
     */
    public void setAutoSizeHeaders(Boolean autoSizeHeaders) {
        setAttribute("autoSizeHeaders", autoSizeHeaders, true);
    }

    /**
     * Automatically size row headers to fit wrapped text.
     *
     *
     * @return Boolean
     */
    public Boolean getAutoSizeHeaders()  {
        return getAttributeAsBoolean("autoSizeHeaders");
    }

    /**
     * {@link com.smartgwt.client.grid.GridRenderer#getBaseStyle base cell style} for this listGrid. If this property is unset,
     * base style may be derived from {@link com.smartgwt.client.widgets.grid.ListGrid#getNormalBaseStyle normalBaseStyle} or
     * {@link com.smartgwt.client.widgets.grid.ListGrid#getTallBaseStyle tallBaseStyle} as described in {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getBaseStyle ListGrid.getBaseStyle}.
     *
     * @param baseStyle baseStyle Default value is "cubeCell"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setBaseStyle(String baseStyle)  throws IllegalStateException {
        setAttribute("baseStyle", baseStyle, false);
    }

    /**
     * {@link com.smartgwt.client.grid.GridRenderer#getBaseStyle base cell style} for this listGrid. If this property is unset,
     * base style may be derived from {@link com.smartgwt.client.widgets.grid.ListGrid#getNormalBaseStyle normalBaseStyle} or
     * {@link com.smartgwt.client.widgets.grid.ListGrid#getTallBaseStyle tallBaseStyle} as described in {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getBaseStyle ListGrid.getBaseStyle}.
     *
     *
     * @return Return the base stylename for this cell.  Default implementation just returns this.baseStyle. See {@link
     * com.smartgwt.client.widgets.grid.ListGrid#getCellStyle getCellStyle()} for a general discussion of how to style cells.
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getBaseStyle()  {
        return getAttributeAsString("baseStyle");
    }

    /**
     * Minimum height for the body of this cubeGrid.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param bodyMinHeight bodyMinHeight Default value is null
     */
    public void setBodyMinHeight(Integer bodyMinHeight) {
        setAttribute("bodyMinHeight", bodyMinHeight, true);
    }

    /**
     * Minimum height for the body of this cubeGrid.
     *
     *
     * @return Integer
     */
    public Integer getBodyMinHeight()  {
        return getAttributeAsInt("bodyMinHeight");
    }

    /**
     * Minimum width for the body of this cubeGrid.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param bodyMinWidth bodyMinWidth Default value is null
     */
    public void setBodyMinWidth(Integer bodyMinWidth) {
        setAttribute("bodyMinWidth", bodyMinWidth, true);
    }

    /**
     * Minimum width for the body of this cubeGrid.
     *
     *
     * @return Integer
     */
    public Integer getBodyMinWidth()  {
        return getAttributeAsInt("bodyMinWidth");
    }

    /**
     * CSS class for the CubeGrid body
     *
     * @param bodyStyleName bodyStyleName Default value is "cubeGridBody"
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setBodyStyleName(String bodyStyleName) {
        setAttribute("bodyStyleName", bodyStyleName, true);
    }

    /**
     * CSS class for the CubeGrid body
     *
     *
     * @return String
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getBodyStyleName()  {
        return getAttributeAsString("bodyStyleName");
    }

    /**
     * If true, hierarchical facets will show expand/collapse controls to allow the user to expand and collapse the tree of
     * facetValues for that facet.
     *
     * @param canCollapseFacets canCollapseFacets Default value is false
     */
    public void setCanCollapseFacets(Boolean canCollapseFacets) {
        setAttribute("canCollapseFacets", canCollapseFacets, true);
    }

    /**
     * If true, hierarchical facets will show expand/collapse controls to allow the user to expand and collapse the tree of
     * facetValues for that facet.
     *
     *
     * @return Boolean
     */
    public Boolean getCanCollapseFacets()  {
        return getAttributeAsBoolean("canCollapseFacets");
    }

    /**
     * Whether cells can be edited in this grid.  Can be overridden on a per-facetValue basis.
     *
     * @param canEdit canEdit Default value is false
     */
    public void setCanEdit(Boolean canEdit) {
        setAttribute("canEdit", canEdit, true);
    }

    /**
     * Whether cells can be edited in this grid.  Can be overridden on a per-facetValue basis.
     *
     *
     * @return Boolean
     */
    public Boolean getCanEdit()  {
        return getAttributeAsBoolean("canEdit");
    }

    /**
     * If true, allow columns in the grid body to be minimized (reduced to the width of the minimize control) by clicking on a
     * minimize control in the innermost column headers.
     *
     * @param canMinimizeColumns canMinimizeColumns Default value is null
     */
    public void setCanMinimizeColumns(Boolean canMinimizeColumns) {
        setAttribute("canMinimizeColumns", canMinimizeColumns, true);
    }

    /**
     * If true, allow columns in the grid body to be minimized (reduced to the width of the minimize control) by clicking on a
     * minimize control in the innermost column headers.
     *
     *
     * @return Boolean
     */
    public Boolean getCanMinimizeColumns()  {
        return getAttributeAsBoolean("canMinimizeColumns");
    }

    /**
     * If true, when multiple facets are shown on a side, all facetValues in the second level of headers or higher will show
     * controls to "minimize" the values of the next facet. Minimizing means showing only one, or very few, of the next facet's
     * values. <P> Set {@link com.smartgwt.client.widgets.cube.FacetValue#getIsMinimizeValue isMinimizeValue} to indicate which
     * facetValues should be shown when a facet is minimized.
     *
     * @param canMinimizeFacets canMinimizeFacets Default value is false
     */
    public void setCanMinimizeFacets(Boolean canMinimizeFacets) {
        setAttribute("canMinimizeFacets", canMinimizeFacets, true);
    }

    /**
     * If true, when multiple facets are shown on a side, all facetValues in the second level of headers or higher will show
     * controls to "minimize" the values of the next facet. Minimizing means showing only one, or very few, of the next facet's
     * values. <P> Set {@link com.smartgwt.client.widgets.cube.FacetValue#getIsMinimizeValue isMinimizeValue} to indicate which
     * facetValues should be shown when a facet is minimized.
     *
     *
     * @return Boolean
     */
    public Boolean getCanMinimizeFacets()  {
        return getAttributeAsBoolean("canMinimizeFacets");
    }

    /**
     * Whether row and column facets can be rearranged by the user, by dragging and dropping the facet labels.
     *
     * @param canMoveFacets canMoveFacets Default value is false
     */
    public void setCanMoveFacets(Boolean canMoveFacets) {
        setAttribute("canMoveFacets", canMoveFacets, true);
    }

    /**
     * Whether row and column facets can be rearranged by the user, by dragging and dropping the facet labels.
     *
     *
     * @return Boolean
     */
    public Boolean getCanMoveFacets()  {
        return getAttributeAsBoolean("canMoveFacets");
    }

    /**
     * If true, body columns can be reordered via the innermost column headers.
     *
     * @param canReorderColumns canReorderColumns Default value is null
     */
    public void setCanReorderColumns(Boolean canReorderColumns) {
        setAttribute("canReorderColumns", canReorderColumns, true);
    }

    /**
     * If true, body columns can be reordered via the innermost column headers.
     *
     *
     * @return Boolean
     */
    public Boolean getCanReorderColumns()  {
        return getAttributeAsBoolean("canReorderColumns");
    }

    /**
     * If true, body columns can be resized via the innermost column headers.
     *
     * @param canResizeColumns canResizeColumns Default value is null
     */
    public void setCanResizeColumns(Boolean canResizeColumns) {
        setAttribute("canResizeColumns", canResizeColumns, true);
    }

    /**
     * If true, body columns can be resized via the innermost column headers.
     *
     *
     * @return Boolean
     */
    public Boolean getCanResizeColumns()  {
        return getAttributeAsBoolean("canResizeColumns");
    }

    /**
     * Determines whether row or column facetValue headers can be selected.
     *
     * @param canSelectHeaders canSelectHeaders Default value is true
     */
    public void setCanSelectHeaders(Boolean canSelectHeaders) {
        setAttribute("canSelectHeaders", canSelectHeaders, true);
    }

    /**
     * Determines whether row or column facetValue headers can be selected.
     *
     *
     * @return Boolean
     */
    public Boolean getCanSelectHeaders()  {
        return getAttributeAsBoolean("canSelectHeaders");
    }

    /**
     * Determines whether cell values in the body can be selected.
     *
     * @param canSelectValues canSelectValues Default value is true
     */
    public void setCanSelectValues(Boolean canSelectValues) {
        setAttribute("canSelectValues", canSelectValues, true);
    }

    /**
     * Determines whether cell values in the body can be selected.
     *
     *
     * @return Boolean
     */
    public Boolean getCanSelectValues()  {
        return getAttributeAsBoolean("canSelectValues");
    }

    /**
     * If true, sort controls will be shown on facet values. <P> When clicked, sort controls call {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#addSortByFacetValuesHandler CubeGrid.sortByFacetValues}.
     *
     * @param canSortData canSortData Default value is null
     */
    public void setCanSortData(Boolean canSortData) {
        setAttribute("canSortData", canSortData, true);
    }

    /**
     * If true, sort controls will be shown on facet values. <P> When clicked, sort controls call {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#addSortByFacetValuesHandler CubeGrid.sortByFacetValues}.
     *
     *
     * @return Boolean
     */
    public Boolean getCanSortData()  {
        return getAttributeAsBoolean("canSortData");
    }

    /**
     * If true, sort controls will be shown on FacetHeaders. <P> When clicked, sort controls call {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#addSortByFacetIdHandler CubeGrid.sortByFacetId}.
     *
     * @param canSortFacets canSortFacets Default value is null
     */
    public void setCanSortFacets(Boolean canSortFacets) {
        setAttribute("canSortFacets", canSortFacets, true);
    }

    /**
     * If true, sort controls will be shown on FacetHeaders. <P> When clicked, sort controls call {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#addSortByFacetIdHandler CubeGrid.sortByFacetId}.
     *
     *
     * @return Boolean
     */
    public Boolean getCanSortFacets()  {
        return getAttributeAsBoolean("canSortFacets");
    }

    /**
     * Default align for cell values (in body).
     *
     * @param cellAlign cellAlign Default value is "center"
     */
    public void setCellAlign(Alignment cellAlign) {
        setAttribute("cellAlign", cellAlign == null ? null : cellAlign.getValue(), true);
    }

    /**
     * Default align for cell values (in body).
     *
     *
     * @return Alignment
     */
    public Alignment getCellAlign()  {
        return EnumUtil.getEnum(Alignment.values(), getAttribute("cellAlign"));
    }

    /**
     * Name of the property in a cell record that holds it's unique ID.  Note cell record IDs are optional.
     *
     * @param cellIdProperty cellIdProperty Default value is "ID"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setCellIdProperty(String cellIdProperty)  throws IllegalStateException {
        setAttribute("cellIdProperty", cellIdProperty, false);
    }

    /**
     * Name of the property in a cell record that holds it's unique ID.  Note cell record IDs are optional.
     *
     *
     * @return String
     */
    public String getCellIdProperty()  {
        return getAttributeAsString("cellIdProperty");
    }

    /**
     * If {@link com.smartgwt.client.widgets.cube.CubeGrid#makeChart CubeGrid.makeChart} is called with a chart specification
     * that will show more than <code>chartConfirmThreshold</code> data elements, the user will be presented with a {@link
     * com.smartgwt.client.util.isc#confirm confirmation dialog}. <P> Set to 0 to disable this confirmation.
     *
     * @param chartConfirmThreshold chartConfirmThreshold Default value is 2000
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setChartConfirmThreshold(int chartConfirmThreshold)  throws IllegalStateException {
        setAttribute("chartConfirmThreshold", chartConfirmThreshold, false);
    }

    /**
     * If {@link com.smartgwt.client.widgets.cube.CubeGrid#makeChart CubeGrid.makeChart} is called with a chart specification
     * that will show more than <code>chartConfirmThreshold</code> data elements, the user will be presented with a {@link
     * com.smartgwt.client.util.isc#confirm confirmation dialog}. <P> Set to 0 to disable this confirmation.
     *
     *
     * @return int
     */
    public int getChartConfirmThreshold()  {
        return getAttributeAsInt("chartConfirmThreshold");
    }

    /**
     * Name of the Smart GWT Class to be used when creating charts.  Must support the Chart interface.
     *
     * @param chartConstructor chartConstructor Default value is "FacetChart"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setChartConstructor(String chartConstructor)  throws IllegalStateException {
        setAttribute("chartConstructor", chartConstructor, false);
    }

    /**
     * Name of the Smart GWT Class to be used when creating charts.  Must support the Chart interface.
     *
     *
     * @return String
     */
    public String getChartConstructor()  {
        return getAttributeAsString("chartConstructor");
    }

    /**
     * Default type of chart to plot.
     *
     * @param chartType chartType Default value is "Column"
     */
    public void setChartType(ChartType chartType) {
        setAttribute("chartType", chartType == null ? null : chartType.getValue(), true);
    }

    /**
     * Default type of chart to plot.
     *
     *
     * @return ChartType
     */
    public ChartType getChartType()  {
        return EnumUtil.getEnum(ChartType.values(), getAttribute("chartType"));
    }

    /**
     * {@link com.smartgwt.client.widgets.Button#getBaseStyle baseStyle} for the buttons in this grid's column headers. <P>
     * Exception: The innermost column header will always be styled using {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#getInnerHeaderBaseStyle innerHeaderBaseStyle}.
     *
     * @param colHeaderBaseStyle colHeaderBaseStyle Default value is colHeader
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setColHeaderBaseStyle(String colHeaderBaseStyle)  throws IllegalStateException {
        setAttribute("colHeaderBaseStyle", colHeaderBaseStyle, false);
    }

    /**
     * {@link com.smartgwt.client.widgets.Button#getBaseStyle baseStyle} for the buttons in this grid's column headers. <P>
     * Exception: The innermost column header will always be styled using {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#getInnerHeaderBaseStyle innerHeaderBaseStyle}.
     *
     *
     * @return String
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getColHeaderBaseStyle()  {
        return getAttributeAsString("colHeaderBaseStyle");
    }

    /**
     * The list of {@link com.smartgwt.client.widgets.cube.Facet#getId ids} for facets that will appear on top of the body.
     *
     * @param columnFacets columnFacets Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setColumnFacets(String... columnFacets)  throws IllegalStateException {
        setAttribute("columnFacets", columnFacets, false);
    }

    /**
     * The list of {@link com.smartgwt.client.widgets.cube.Facet#getId ids} for facets that will appear on top of the body.
     *
     *
     * @return String
     */
    public String[] getColumnFacets()  {
        return getAttributeAsStringArray("columnFacets");
    }

    /**
     * Default width of inner column headers.
     *
     * @param defaultFacetWidth defaultFacetWidth Default value is 100
     */
    public void setDefaultFacetWidth(int defaultFacetWidth) {
        setAttribute("defaultFacetWidth", defaultFacetWidth, true);
    }

    /**
     * Default width of inner column headers.
     *
     *
     * @return int
     */
    public int getDefaultFacetWidth()  {
        return getAttributeAsInt("defaultFacetWidth");
    }


    /**
     * CubeGrids only support editing by cell.
     *
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Boolean
     * @throws IllegalStateException if widget has not yet been rendered.
     */
    public Boolean getEditByCell() throws IllegalStateException {
        errorIfNotCreated("editByCell");
        return getAttributeAsBoolean("editByCell");
    }

    /**
     * Allows the developer to override the horizontal text alignment of hover tips shown for facetLabels.  If unspecified the
     * hover canvas content alignment will be set by <code>this.hoverAlign</code> if specified.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetLabelHoverAlign facetLabelHoverAlign Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverAlign
     */
    public void setFacetLabelHoverAlign(Alignment facetLabelHoverAlign) {
        setAttribute("facetLabelHoverAlign", facetLabelHoverAlign == null ? null : facetLabelHoverAlign.getValue(), true);
    }

    /**
     * Allows the developer to override the horizontal text alignment of hover tips shown for facetLabels.  If unspecified the
     * hover canvas content alignment will be set by <code>this.hoverAlign</code> if specified.
     *
     *
     * @return Alignment
     * @see com.smartgwt.client.widgets.Canvas#getHoverAlign
     */
    public Alignment getFacetLabelHoverAlign()  {
        return EnumUtil.getEnum(Alignment.values(), getAttribute("facetLabelHoverAlign"));
    }

    /**
     * If specified and <code>this.showHover</code> is true, this is the default height to apply to hover tips shown for
     * facetLabels. If unset, the hover canvas will be sized to  <code>this.hoverHeight</code> if specified instead.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetLabelHoverHeight facetLabelHoverHeight Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverHeight
     */
    public void setFacetLabelHoverHeight(Integer facetLabelHoverHeight) {
        setAttribute("facetLabelHoverHeight", facetLabelHoverHeight, true);
    }

    /**
     * If specified and <code>this.showHover</code> is true, this is the default height to apply to hover tips shown for
     * facetLabels. If unset, the hover canvas will be sized to  <code>this.hoverHeight</code> if specified instead.
     *
     *
     * @return Integer
     * @see com.smartgwt.client.widgets.Canvas#getHoverHeight
     */
    public Integer getFacetLabelHoverHeight()  {
        return getAttributeAsInt("facetLabelHoverHeight");
    }

    /**
     * Allows the developer to override the css class applied to  hover tips shown for facet labels.  If unspecified, and
     * <code>this.hoverStyle</code> is not null, that  css class will be applied to facet label hovers instead.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetLabelHoverStyle facetLabelHoverStyle Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverStyle
     */
    public void setFacetLabelHoverStyle(String facetLabelHoverStyle) {
        setAttribute("facetLabelHoverStyle", facetLabelHoverStyle, true);
    }

    /**
     * Allows the developer to override the css class applied to  hover tips shown for facet labels.  If unspecified, and
     * <code>this.hoverStyle</code> is not null, that  css class will be applied to facet label hovers instead.
     *
     *
     * @return String
     * @see com.smartgwt.client.widgets.Canvas#getHoverStyle
     */
    public String getFacetLabelHoverStyle()  {
        return getAttributeAsString("facetLabelHoverStyle");
    }

    /**
     * Allows the developer to override the vertical text alignment of hover tips shown for facetLabels.  If unspecified the
     * hover canvas content alignment will be set by <code>this.hoverVAlign</code> if specified.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetLabelHoverVAlign facetLabelHoverVAlign Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverVAlign
     */
    public void setFacetLabelHoverVAlign(VerticalAlignment facetLabelHoverVAlign) {
        setAttribute("facetLabelHoverVAlign", facetLabelHoverVAlign == null ? null : facetLabelHoverVAlign.getValue(), true);
    }

    /**
     * Allows the developer to override the vertical text alignment of hover tips shown for facetLabels.  If unspecified the
     * hover canvas content alignment will be set by <code>this.hoverVAlign</code> if specified.
     *
     *
     * @return VerticalAlignment
     * @see com.smartgwt.client.widgets.Canvas#getHoverVAlign
     */
    public VerticalAlignment getFacetLabelHoverVAlign()  {
        return EnumUtil.getEnum(VerticalAlignment.values(), getAttribute("facetLabelHoverVAlign"));
    }

    /**
     * If specified and <code>this.showHover</code> is true, this is the default width to apply to hover tips shown for
     * facetLabels. If unset, the hover canvas will be sized to  <code>this.hoverWidth</code> if specified instead.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetLabelHoverWidth facetLabelHoverWidth Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverWidth
     */
    public void setFacetLabelHoverWidth(Integer facetLabelHoverWidth) {
        setAttribute("facetLabelHoverWidth", facetLabelHoverWidth, true);
    }

    /**
     * If specified and <code>this.showHover</code> is true, this is the default width to apply to hover tips shown for
     * facetLabels. If unset, the hover canvas will be sized to  <code>this.hoverWidth</code> if specified instead.
     *
     *
     * @return Integer
     * @see com.smartgwt.client.widgets.Canvas#getHoverWidth
     */
    public Integer getFacetLabelHoverWidth()  {
        return getAttributeAsInt("facetLabelHoverWidth");
    }

    /**
     * Facet definitions for this CubeGrid.  Facets, also called "dimensions", are orthogonal aspects of the data model. <P>
     * For example, you can look at profit by the facets "plant and product" or by "product and plant" and it's the same
     * number, because the facets - plant and product - are the same. What would change the profit numbers would be to remove a
     * facet, called "summarizing", or add a new facet, called "drilling down".  For example, showing profit by plant and
     * product, you could "drill down" by adding the region facet, which would divide profit among each region.  Or you could
     * remove the "plant" facet, showing total profit for each "product", summed across all plants.
     *
     * @param facets facets Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.cube.CubeGrid#getFacet
     * @see com.smartgwt.client.widgets.cube.Facet
     * @see com.smartgwt.client.widgets.cube.CubeGrid#getFacetValue
     * @see com.smartgwt.client.widgets.cube.FacetValue
     */
    public void setFacets(Facet... facets)  throws IllegalStateException {
        setAttribute("facets", facets, false);
    }

    /**
     * Default alignment for facet labels.
     *
     * <br><br>If this method is called after the component has been drawn/initialized:
     * Set the align of a facet title (appears in facet label).
     *
     * @param facetTitleAlign facet to update. Default value is "center"
     */
    public void setFacetTitleAlign(Alignment facetTitleAlign) {
        setAttribute("facetTitleAlign", facetTitleAlign == null ? null : facetTitleAlign.getValue(), true);
    }

    /**
     * Default alignment for facet labels.
     *
     *
     * @return Alignment
     */
    public Alignment getFacetTitleAlign()  {
        return EnumUtil.getEnum(Alignment.values(), getAttribute("facetTitleAlign"));
    }

    /**
     * Default alignment for facet values (in headers).
     *
     * @param facetValueAlign facetValueAlign Default value is "center"
     */
    public void setFacetValueAlign(Alignment facetValueAlign) {
        setAttribute("facetValueAlign", facetValueAlign == null ? null : facetValueAlign.getValue(), true);
    }

    /**
     * Default alignment for facet values (in headers).
     *
     *
     * @return Alignment
     */
    public Alignment getFacetValueAlign()  {
        return EnumUtil.getEnum(Alignment.values(), getAttribute("facetValueAlign"));
    }

    /**
     * Allows the developer to override the horizontal text alignment of hover tips shown for facet values.  If unspecified the
     * hover canvas content alignment will be set by <code>this.hoverAlign</code> if specified.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetValueHoverAlign facetValueHoverAlign Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverAlign
     */
    public void setFacetValueHoverAlign(Alignment facetValueHoverAlign) {
        setAttribute("facetValueHoverAlign", facetValueHoverAlign == null ? null : facetValueHoverAlign.getValue(), true);
    }

    /**
     * Allows the developer to override the horizontal text alignment of hover tips shown for facet values.  If unspecified the
     * hover canvas content alignment will be set by <code>this.hoverAlign</code> if specified.
     *
     *
     * @return Alignment
     * @see com.smartgwt.client.widgets.Canvas#getHoverAlign
     */
    public Alignment getFacetValueHoverAlign()  {
        return EnumUtil.getEnum(Alignment.values(), getAttribute("facetValueHoverAlign"));
    }

    /**
     * If specified and <code>this.showHover</code> is true, this is the default height to apply to hover tips shown for
     * facetValues. If unset, the hover canvas will be sized to  <code>this.hoverHeight</code> if specified instead.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetValueHoverHeight facetValueHoverHeight Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverHeight
     */
    public void setFacetValueHoverHeight(Integer facetValueHoverHeight) {
        setAttribute("facetValueHoverHeight", facetValueHoverHeight, true);
    }

    /**
     * If specified and <code>this.showHover</code> is true, this is the default height to apply to hover tips shown for
     * facetValues. If unset, the hover canvas will be sized to  <code>this.hoverHeight</code> if specified instead.
     *
     *
     * @return Integer
     * @see com.smartgwt.client.widgets.Canvas#getHoverHeight
     */
    public Integer getFacetValueHoverHeight()  {
        return getAttributeAsInt("facetValueHoverHeight");
    }

    /**
     * Allows the developer to override the css class applied to  hover tips shown for facet values.  If unspecified, and
     * <code>this.hoverStyle</code> is not null, that  css class will be applied to facet value hovers instead.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetValueHoverStyle facetValueHoverStyle Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverStyle
     */
    public void setFacetValueHoverStyle(String facetValueHoverStyle) {
        setAttribute("facetValueHoverStyle", facetValueHoverStyle, true);
    }

    /**
     * Allows the developer to override the css class applied to  hover tips shown for facet values.  If unspecified, and
     * <code>this.hoverStyle</code> is not null, that  css class will be applied to facet value hovers instead.
     *
     *
     * @return String
     * @see com.smartgwt.client.widgets.Canvas#getHoverStyle
     */
    public String getFacetValueHoverStyle()  {
        return getAttributeAsString("facetValueHoverStyle");
    }

    /**
     * Allows the developer to override the vertical text alignment of hover tips shown for facet values.  If unspecified the
     * hover canvas content alignment will be set by <code>this.hoverVAlign</code> if specified.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetValueHoverVAlign facetValueHoverVAlign Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverVAlign
     */
    public void setFacetValueHoverVAlign(VerticalAlignment facetValueHoverVAlign) {
        setAttribute("facetValueHoverVAlign", facetValueHoverVAlign == null ? null : facetValueHoverVAlign.getValue(), true);
    }

    /**
     * Allows the developer to override the vertical text alignment of hover tips shown for facet values.  If unspecified the
     * hover canvas content alignment will be set by <code>this.hoverVAlign</code> if specified.
     *
     *
     * @return VerticalAlignment
     * @see com.smartgwt.client.widgets.Canvas#getHoverVAlign
     */
    public VerticalAlignment getFacetValueHoverVAlign()  {
        return EnumUtil.getEnum(VerticalAlignment.values(), getAttribute("facetValueHoverVAlign"));
    }

    /**
     * If specified and <code>this.showHover</code> is true, this is the default width to apply to hover tips shown for
     * facetValues. If unset, the hover canvas will be sized to  <code>this.hoverWidth</code> if specified instead.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param facetValueHoverWidth facetValueHoverWidth Default value is null
     * @see com.smartgwt.client.widgets.Canvas#setHoverWidth
     */
    public void setFacetValueHoverWidth(Integer facetValueHoverWidth) {
        setAttribute("facetValueHoverWidth", facetValueHoverWidth, true);
    }

    /**
     * If specified and <code>this.showHover</code> is true, this is the default width to apply to hover tips shown for
     * facetValues. If unset, the hover canvas will be sized to  <code>this.hoverWidth</code> if specified instead.
     *
     *
     * @return Integer
     * @see com.smartgwt.client.widgets.Canvas#getHoverWidth
     */
    public Integer getFacetValueHoverWidth()  {
        return getAttributeAsInt("facetValueHoverWidth");
    }

    /**
     * A {@link com.smartgwt.client.widgets.cube.FacetValueMap} describing the set of facet values that should be regarded as
     * "fixed" in this cubeGrid.  These are used as fixed criteria for load on demand, and also allow using a dataset with more
     * facets in it than are currently shown in the grid.
     *
     * @param fixedFacetValues fixedFacetValues Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.widgets.cube.CubeGrid#addFacet
     * @see com.smartgwt.client.widgets.cube.CubeGrid#removeFacet
     */
    public void setFixedFacetValues(FacetValueMap fixedFacetValues)  throws IllegalStateException {
        setAttribute("fixedFacetValues", fixedFacetValues.getJsObj(), false);
    }

    /**
     * A {@link com.smartgwt.client.widgets.cube.FacetValueMap} describing the set of facet values that should be regarded as
     * "fixed" in this cubeGrid.  These are used as fixed criteria for load on demand, and also allow using a dataset with more
     * facets in it than are currently shown in the grid.
     *
     *
     * @return FacetValueMap
     * @see com.smartgwt.client.widgets.cube.CubeGrid#addFacet
     * @see com.smartgwt.client.widgets.cube.CubeGrid#removeFacet
     */
    public FacetValueMap getFixedFacetValues()  {
        return new FacetValueMap(getAttributeAsJavaScriptObject("fixedFacetValues"));
    }

    /**
     * With {@link com.smartgwt.client.widgets.cube.CubeGrid#getHideEmptyFacetValues hideEmptyFacetValues}, controls on which
     * axis hiding of empty values is applied, "row" (only empty rows are hidden), "column" (only empty columns are hidden) or
     * both (the default).
     *
     * @param hideEmptyAxis hideEmptyAxis Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setHideEmptyAxis(Axis hideEmptyAxis)  throws IllegalStateException {
        setAttribute("hideEmptyAxis", hideEmptyAxis == null ? null : hideEmptyAxis.getValue(), false);
    }

    /**
     * With {@link com.smartgwt.client.widgets.cube.CubeGrid#getHideEmptyFacetValues hideEmptyFacetValues}, controls on which
     * axis hiding of empty values is applied, "row" (only empty rows are hidden), "column" (only empty columns are hidden) or
     * both (the default).
     *
     *
     * @return Axis
     */
    public Axis getHideEmptyAxis()  {
        return EnumUtil.getEnum(Axis.values(), getAttribute("hideEmptyAxis"));
    }

    /**
     * This causes the headers for any combination of facetValues for which there are no  cellRecords to be suppressed. (Note:
     * Valid only for CubeGrids that specify the complete dataset upfront -   don't use load on demand)
     *
     * @param hideEmptyFacetValues hideEmptyFacetValues Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setHideEmptyFacetValues(Boolean hideEmptyFacetValues)  throws IllegalStateException {
        setAttribute("hideEmptyFacetValues", hideEmptyFacetValues, false);
    }

    /**
     * This causes the headers for any combination of facetValues for which there are no  cellRecords to be suppressed. (Note:
     * Valid only for CubeGrids that specify the complete dataset upfront -   don't use load on demand)
     *
     *
     * @return Boolean
     */
    public Boolean getHideEmptyFacetValues()  {
        return getAttributeAsBoolean("hideEmptyFacetValues");
    }

    /**
     * Hilites to be applied to the data for this component.  See {@link com.smartgwt.client.docs.Hiliting}.
     *
     * @param hilites hilites Default value is null
     * @see com.smartgwt.client.docs.Hiliting Hiliting overview and related methods
     */
    public void setHilites(Hilite... hilites) {
        setAttribute("hilites", hilites, true);
    }

    /**
     * Hilites to be applied to the data for this component.  See {@link com.smartgwt.client.docs.Hiliting}.
     *
     *
     * @return Hilite
     * @see com.smartgwt.client.docs.Hiliting Hiliting overview and related methods
     */
    public Hilite[] getHilites()  {
        return Hilite.convertToHiliteArray(getAttributeAsJavaScriptObject("hilites"));
    }

    /**
     * {@link com.smartgwt.client.widgets.Button#getBaseStyle baseStyle} for the buttons in the innermost column header for 
     * this cubeGrid.
     *
     * @param innerHeaderBaseStyle innerHeaderBaseStyle Default value is innerHeader
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setInnerHeaderBaseStyle(String innerHeaderBaseStyle)  throws IllegalStateException {
        setAttribute("innerHeaderBaseStyle", innerHeaderBaseStyle, false);
    }

    /**
     * {@link com.smartgwt.client.widgets.Button#getBaseStyle baseStyle} for the buttons in the innermost column header for 
     * this cubeGrid.
     *
     *
     * @return String
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getInnerHeaderBaseStyle()  {
        return getAttributeAsString("innerHeaderBaseStyle");
    }

    /**
     * In a CubeGrid that displays values of different types (eg "Revenue" and "Income"), the different types of values on
     * display are enumerated as the facet values of the "metric facet".   <P> The metric facet is treated identically to any
     * other facet by the CubeGrid: it can be represented as row or column headers, can be innermost or have other facets under
     * it, can be moved around, etc.  However when a metric facet is used, {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#getMetricFacetId metricFacetId} must be set to allow the CubeGrid to generate
     * meaningful descriptions of values shown in cells for use in hovers and other situations; see {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#getValueTitle valueTitle} for a full explanation.
     *
     * @param metricFacetId metricFacetId Default value is "metric"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setMetricFacetId(String metricFacetId)  throws IllegalStateException {
        setAttribute("metricFacetId", metricFacetId, false);
    }

    /**
     * In a CubeGrid that displays values of different types (eg "Revenue" and "Income"), the different types of values on
     * display are enumerated as the facet values of the "metric facet".   <P> The metric facet is treated identically to any
     * other facet by the CubeGrid: it can be represented as row or column headers, can be innermost or have other facets under
     * it, can be moved around, etc.  However when a metric facet is used, {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#getMetricFacetId metricFacetId} must be set to allow the CubeGrid to generate
     * meaningful descriptions of values shown in cells for use in hovers and other situations; see {@link
     * com.smartgwt.client.widgets.cube.CubeGrid#getValueTitle valueTitle} for a full explanation.
     *
     *
     * @return String
     */
    public String getMetricFacetId()  {
        return getAttributeAsString("metricFacetId");
    }

    /**
     * Whether to pad titles so they aren't flush with header borders.
     *
     * @param padTitles padTitles Default value is true
     */
    public void setPadTitles(Boolean padTitles) {
        setAttribute("padTitles", padTitles, true);
    }

    /**
     * Whether to pad titles so they aren't flush with header borders.
     *
     *
     * @return Boolean
     */
    public Boolean getPadTitles()  {
        return getAttributeAsBoolean("padTitles");
    }

    /**
     * facetValueId of the default rollupValue for each facet.  Can be overridden per facet via facet.rollupValue.
     *
     * @param rollupValue rollupValue Default value is "sum"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setRollupValue(String rollupValue)  throws IllegalStateException {
        setAttribute("rollupValue", rollupValue, false);
    }

    /**
     * facetValueId of the default rollupValue for each facet.  Can be overridden per facet via facet.rollupValue.
     *
     *
     * @return Get the facetValue definition for the facetValue to show when this facet is "rolled up" under another facet, during a
     * breakout.<br><br> A facet is not required to have a rollup value, and if it does not have one, then rollups will simply
     * be blank rows.  The facetValueId of the rollup value can be declared as cubeGrid.rollupValue or facet.rollupValue.
     */
    public String getRollupValue()  {
        return getAttributeAsString("rollupValue");
    }

    /**
     * The list of {@link com.smartgwt.client.widgets.cube.Facet#getId ids} for facets that will appear to the left of the
     * body.
     *
     * @param rowFacets rowFacets Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setRowFacets(String... rowFacets)  throws IllegalStateException {
        setAttribute("rowFacets", rowFacets, false);
    }

    /**
     * The list of {@link com.smartgwt.client.widgets.cube.Facet#getId ids} for facets that will appear to the left of the
     * body.
     *
     *
     * @return String
     */
    public String[] getRowFacets()  {
        return getAttributeAsStringArray("rowFacets");
    }

    /**
     * {@link com.smartgwt.client.widgets.Button#getBaseStyle baseStyle} for the buttons in this grid's row headers.
     *
     * @param rowHeaderBaseStyle rowHeaderBaseStyle Default value is rowHeader
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setRowHeaderBaseStyle(String rowHeaderBaseStyle)  throws IllegalStateException {
        setAttribute("rowHeaderBaseStyle", rowHeaderBaseStyle, false);
    }

    /**
     * {@link com.smartgwt.client.widgets.Button#getBaseStyle baseStyle} for the buttons in this grid's row headers.
     *
     *
     * @return String
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getRowHeaderBaseStyle()  {
        return getAttributeAsString("rowHeaderBaseStyle");
    }

    /**
     * If enabled row headers for this cubeGrid will be rendered using a {@link com.smartgwt.client.grid.GridRenderer}
     * component. This improves performance for very large cubeGrids.
     * <p><b>Note : </b> This is an advanced setting</p>
     *
     * @param rowHeaderGridMode rowHeaderGridMode Default value is false
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setRowHeaderGridMode(Boolean rowHeaderGridMode)  throws IllegalStateException {
        setAttribute("rowHeaderGridMode", rowHeaderGridMode, false);
    }

    /**
     * If enabled row headers for this cubeGrid will be rendered using a {@link com.smartgwt.client.grid.GridRenderer}
     * component. This improves performance for very large cubeGrids.
     *
     *
     * @return Boolean
     */
    public Boolean getRowHeaderGridMode()  {
        return getAttributeAsBoolean("rowHeaderGridMode");
    }


    /**
     * CubeGrids only support editing by cell.
     *
     * <b>Note :</b> This method should be called only after the widget has been rendered.
     *
     * @return Boolean
     * @throws IllegalStateException if widget has not yet been rendered.
     */
    public Boolean getSaveByCell() throws IllegalStateException {
        errorIfNotCreated("saveByCell");
        return getAttributeAsBoolean("saveByCell");
    }

    /**
     * If true, clicking on the existing selection causes it to be entirely deselected.
     *
     * @param simpleDeselect simpleDeselect Default value is false
     */
    public void setSimpleDeselect(Boolean simpleDeselect) {
        setAttribute("simpleDeselect", simpleDeselect, true);
    }

    /**
     * If true, clicking on the existing selection causes it to be entirely deselected.
     *
     *
     * @return Boolean
     */
    public Boolean getSimpleDeselect()  {
        return getAttributeAsBoolean("simpleDeselect");
    }

    /**
     * Default directory for skin images (those defined by the class), relative to the Page-wide {@link
     * com.smartgwt.client.util.Page#getSkinDir skinDir}.
     *
     * @param skinImgDir skinImgDir Default value is "images/CubeGrid/"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public void setSkinImgDir(String skinImgDir)  throws IllegalStateException {
        setAttribute("skinImgDir", skinImgDir, false);
    }

    /**
     * Default directory for skin images (those defined by the class), relative to the Page-wide {@link
     * com.smartgwt.client.util.Page#getSkinDir skinDir}.
     *
     *
     * @return String
     * @see com.smartgwt.client.docs.Images Images overview and related methods
     */
    public String getSkinImgDir()  {
        return getAttributeAsString("skinImgDir");
    }

    /**
     * Direction of sorting if sortedFacet or sortedFacetValues is specified.
     *
     * @param sortDirection sortDirection Default value is Array.ASCENDING
     */
    public void setSortDirection(SortDirection sortDirection) {
        setAttribute("sortDirection", sortDirection == null ? null : sortDirection.getValue(), true);
    }

    /**
     * Direction of sorting if sortedFacet or sortedFacetValues is specified.
     *
     *
     * @return SortDirection
     */
    public SortDirection getSortDirection()  {
        return EnumUtil.getEnum(SortDirection.values(), getAttribute("sortDirection"));
    }

    /**
     * {@link com.smartgwt.client.widgets.cube.FacetValueMap} of facet values representing a set of facetValues by which the
     * cubeGrid data is sorted.
     *
     * @param sortedFacetValues sortedFacetValues Default value is null
     */
    public void setSortedFacetValues(FacetValueMap sortedFacetValues) {
        setAttribute("sortedFacetValues", sortedFacetValues.getJsObj(), true);
    }

    /**
     * {@link com.smartgwt.client.widgets.cube.FacetValueMap} of facet values representing a set of facetValues by which the
     * cubeGrid data is sorted.
     *
     *
     * @return FacetValueMap
     */
    public FacetValueMap getSortedFacetValues()  {
        return new FacetValueMap(getAttributeAsJavaScriptObject("sortedFacetValues"));
    }

    /**
     * CSS class for the CubeGrid as a whole
     *
     * @param styleName styleName Default value is "normal"
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public void setStyleName(String styleName) {
        setAttribute("styleName", styleName, true);
    }

    /**
     * CSS class for the CubeGrid as a whole
     *
     *
     * @return String
     * @see com.smartgwt.client.docs.Appearance Appearance overview and related methods
     */
    public String getStyleName()  {
        return getAttributeAsString("styleName");
    }

    /**
     * Name of the property in a cell record that holds the cell value.
     *
     * @param valueProperty valueProperty Default value is "_value"
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setValueProperty(String valueProperty)  throws IllegalStateException {
        setAttribute("valueProperty", valueProperty, false);
    }

    /**
     * Name of the property in a cell record that holds the cell value.
     *
     *
     * @return String
     */
    public String getValueProperty()  {
        return getAttributeAsString("valueProperty");
    }

    /**
     * A label for the data values shown in cells, such as "Sales in Thousands", typically used
     *  when the CubeGrid must generate a description for a cell value or set of cell values.
     *  <P>
     *  For example, in a CubeGrid showing "Revenue" by region and product, a cell with a
     *  CellRecord like:
     *  <pre> 
     *  {product:"chairs", region:"northwest", _value:"$5k"}
     *  </pre>
     *  Should be described as "Revenue for Chairs for Northwest Region", not "Chairs for
     *  Revenue for Northwest Region".
     *  <P>
     *  For CubeGrids that show multiple types of values at once (eg both "Revenue" and
     *  "Income") see {@link com.smartgwt.client.widgets.cube.CubeGrid#getMetricFacetId metricFacetId}.
     *
     * @param valueTitle valueTitle Default value is null
     * @throws IllegalStateException this property cannot be changed after the component has been created
     */
    public void setValueTitle(String valueTitle)  throws IllegalStateException {
        setAttribute("valueTitle", valueTitle, false);
    }

    /**
     * A label for the data values shown in cells, such as "Sales in Thousands", typically used
     *  when the CubeGrid must generate a description for a cell value or set of cell values.
     *  <P>
     *  For example, in a CubeGrid showing "Revenue" by region and product, a cell with a
     *  CellRecord like:
     *  <pre> 
     *  {product:"chairs", region:"northwest", _value:"$5k"}
     *  </pre>
     *  Should be described as "Revenue for Chairs for Northwest Region", not "Chairs for
     *  Revenue for Northwest Region".
     *  <P>
     *  For CubeGrids that show multiple types of values at once (eg both "Revenue" and
     *  "Income") see {@link com.smartgwt.client.widgets.cube.CubeGrid#getMetricFacetId metricFacetId}.
     *
     *
     * @return String
     */
    public String getValueTitle()  {
        return getAttributeAsString("valueTitle");
    }

    /**
     * Whether to allow text wrapping on facet titles.
     *
     * @param wrapFacetTitles wrapFacetTitles Default value is false
     */
    public void setWrapFacetTitles(Boolean wrapFacetTitles) {
        setAttribute("wrapFacetTitles", wrapFacetTitles, true);
    }

    /**
     * Whether to allow text wrapping on facet titles.
     *
     *
     * @return Boolean
     */
    public Boolean getWrapFacetTitles()  {
        return getAttributeAsBoolean("wrapFacetTitles");
    }

    /**
     * Whether to allow text wrapping on facet value titles.
     *
     * @param wrapFacetValueTitles wrapFacetValueTitles Default value is false
     */
    public void setWrapFacetValueTitles(Boolean wrapFacetValueTitles) {
        setAttribute("wrapFacetValueTitles", wrapFacetValueTitles, true);
    }

    /**
     * Whether to allow text wrapping on facet value titles.
     *
     *
     * @return Boolean
     */
    public Boolean getWrapFacetValueTitles()  {
        return getAttributeAsBoolean("wrapFacetValueTitles");
    }

    // ********************* Methods ***********************
            
    /**
     * Add a column facet to the view at index "index".  Handles the facet already being in the view (does a pivot).<br><br>
     * The facet being added should currently have a fixed facet value (unless it's already part of the view), which will be
     * removed from cubeGrid.fixedFacetValues. <br><i>methodType</i>  action
     * @param facetId facetId to add.  Definition must have been provided at init time.
     */
    public native void addColumnFacet(String facetId) /*-{
        var self = this.@com.smartgwt.client.widgets.BaseWidget::getOrCreateJsObj()();
        self.addColumnFacet(facetId);
    }-*/;

    /**
     * Add a column facet to the view at index "index".  Handles the facet already being in the view (does a pivot).<br><br>
     * The facet being added should currently have a fixed facet value (unless it's already part of the view), which will be
     * removed from cubeGrid.fixedFacetValues. <br><i>methodType</i>  action
     * @param facetId facetId to add.  Definition must have been provided at init time.
     * @param index index to add the facet at.  0 = outermost (default innermost)
     * @see com.smartgwt.client.widgets.cube.CubeGrid#removeFacet
    