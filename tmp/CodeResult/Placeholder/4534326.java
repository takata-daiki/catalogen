package com.softaria.gwt.common.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

public class PlaceHolder extends Image {

  private static String STYLE = "sa-placeHolder";

  public PlaceHolder() {
    super(GWT.getModuleBaseURL() + "common/images/x.gif");
    addStyleName(STYLE);
  }

}
