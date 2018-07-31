package com.weanticipate.client.web_ui.gwt.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.weanticipate.client.web_ui.gwt.util.GwtUtil;

public final class HeaderBlock extends Widget {

	interface HeaderBlockUiBinder extends UiBinder<Element, HeaderBlock> {}

	private static HeaderBlockUiBinder uiBinder = GWT.create(HeaderBlockUiBinder.class);

	public HeaderBlock() {
		setElement(uiBinder.createAndBindUi(this));
		GwtUtil.addToRootContainer(this);
	}
}
