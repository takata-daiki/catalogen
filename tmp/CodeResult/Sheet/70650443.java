/**************************************************************************
 * Sheet.java is part of Touch4j 4.0. Copyright 2012 Emitrom LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 **************************************************************************/
package com.emitrom.touch4j.client.ui;

import com.emitrom.touch4j.client.core.config.Attribute;
import com.emitrom.touch4j.client.core.config.Event;
import com.emitrom.touch4j.client.core.config.XType;
import com.emitrom.touch4j.client.core.handlers.CallbackRegistration;
import com.emitrom.touch4j.client.core.handlers.component.CenteredChangeHandler;
import com.emitrom.touch4j.client.laf.Position;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A general sheet class. This renderable container provides base support for
 * orientation-aware transitions for popup or side-anchored sliding Panels. In
 * most cases, you should use:
 * <ul>
 * <li>com.emitrom.touch4j.client.containers.ActionSheet
 * <li>com.emitrom.touch4j.client.containers.MessageBox
 * <li>com.emitrom.touch4j.client.containers.Picker
 * <li>com.emitrom.touch4j.client.containers.DatePicker
 * </ul>
 * 
 * @see <a href=http://docs.sencha.com/touch/2-0/#!/api/Ext.Sheet>Ext.Sheet</a>
 */
public class Sheet extends Panel {

    @Override
    protected native void init()/*-{
		var c = new $wnd.Ext.Sheet();
		this.@com.emitrom.touch4j.client.core.Component::configPrototype = c.initialConfig;
    }-*/;

    protected Sheet(JavaScriptObject config) {
        super(config);
    }

    @Override
    protected native JavaScriptObject create(JavaScriptObject config) /*-{
		return new $wnd.Ext.Sheet(config);
    }-*/;

    @Override
    public String getXType() {
        return XType.SHEET.getValue();
    }

    /**
     * Create a new Sheet.
     */
    public Sheet() {
    }

    /**
     * returns the value of centered.
     * 
     * @return
     */
    @Override
    public native boolean isCentered() /*-{
		var sheet = this.@com.emitrom.touch4j.client.core.Component::getOrCreateJsObj()();
		return sheet.getCentered();
    }-*/;

    /**
     * sets the value of centered.
     * 
     * @return
     */
    public native void setCentered(boolean value) /*-{
		var sheet = this.@com.emitrom.touch4j.client.core.Component::getOrCreateJsObj()();
		sheet.setCentered(value);
    }-*/;

    @Override
    public CallbackRegistration addCenteredChangeHandler(CenteredChangeHandler handler) {
        return this.addWidgetListener(Event.CENTERED_CHANGED.getValue(), handler.getJsoPeer());
    }

    /**
     * True to stretch this sheet horizontally.
     * 
     * @param value
     */
    public void setStrechX(boolean value) {
        setAttribute(Attribute.STRETCH_X.getValue(), value, true);
    }

    /**
     * True to stretch this sheet vertically.
     * 
     * @param value
     */
    public void setStrechY(boolean value) {
        setAttribute(Attribute.STRETCH_Y.getValue(), value, true);
    }

    /**
     * The viewport side used as the enter point when shown (top, bottom, left,
     * right) Applies to sliding animation effects only. Defaults to 'bottom'
     * 
     * @param value
     */
    public void setEnter(Position direction) {
        setAttribute(Attribute.ENTER.getValue(), direction.getValue(), true);
    }

    /**
     * The viewport side used as the exit point when hidden (top, bottom, left,
     * right) Applies to sliding animation effects only. Defaults to 'bottom'
     * 
     * @param value
     */
    public void setExit(Position direction) {
        setAttribute(Attribute.EXIT.getValue(), direction.getValue(), true);
    }

}
