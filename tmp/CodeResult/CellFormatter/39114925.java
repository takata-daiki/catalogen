package com.vdm.starlight.client.educator.view.media;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vdm.starlight.client.resources.EducatorResources;
import com.vdm.starlight.shared.dto.ResourceDTO;

public class MediaGridViewImpl extends Composite implements MediaGridView {
	static Logger logger = Logger.getLogger(" MediaGridViewImpl");
	static EducatorResources resources = EducatorResources.INSTANCE;

	@UiTemplate("MediaGridView.ui.xml")
	interface MediaGridViewUiBinder extends UiBinder<Widget, MediaGridViewImpl> {
	}

	/** The default list size. */
	private static final int DEFAULT_NUM_COLS = 6;

	private static MediaGridViewUiBinder uiBinder = GWT.create(MediaGridViewUiBinder.class);

	static {
		/** Injects css into uibinder view. */
		EducatorResources.INSTANCE.starlightCSS().ensureInjected();
	}
	@UiField
	FlexTable flexTable;

	FlexCellFormatter cellFormatter;

	private Presenter presenter;

	List<ResourceDTO> resourceList;

	Button fetchButton;

	String spinnerHTML;

	DialogBox dialogBox;

	Image previewImage;

	HorizontalPanel imagePanel;

	int selectedIndex;

	int selectedCol;

	int selectedRow;

	public MediaGridViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		cellFormatter = flexTable.getFlexCellFormatter();
		flexTable.addStyleName(resources.starlightCSS().pictureGrid());
		flexTable.setWidth("432px");
		flexTable.setCellSpacing(2);
		flexTable.setCellPadding(2);
		previewImage = new Image(resources.defaultIcon());
		imagePanel = new HorizontalPanel();
		dialogBox = createDialogBox();
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		flexTable.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Cell c = flexTable.getCellForEvent(event);
				if (c != null) {
					int cellIndex = c.getCellIndex();
					int rowIndex = c.getRowIndex();
					if (rowIndex > 0 && rowIndex < (flexTable.getRowCount() - 1)) {
						selectedIndex = ((rowIndex - 1) * DEFAULT_NUM_COLS) + cellIndex;
						selectedCol = cellIndex;
						selectedRow = rowIndex;
						if (resourceList.size() > selectedIndex) {
							imagePanel.clear();
							imagePanel.add(new Image(resources.bigSpinner()));
							Label loading = new Label("  Loading image...");
							imagePanel.add(loading);
							imagePanel.setCellVerticalAlignment(loading, HasVerticalAlignment.ALIGN_MIDDLE);
							dialogBox.show();
							presenter.getImage(resourceList.get(selectedIndex).getId());
						}
					}
				}
			}
		});
		resourceList = new ArrayList<ResourceDTO>();
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		flexTable.setHTML(0, 0, "Click an image to enlarge it");
		cellFormatter.setColSpan(0, 0, DEFAULT_NUM_COLS);
		cellFormatter.setStyleName(0, 0, resources.starlightCSS().helpTip());

		int numRows = flexTable.getRowCount();
		ImageResource im = resources.spinner();
		spinnerHTML = "<img src=\"" + im.getSafeUri().asString() + "\" height=\"16px\"/>";
		fetchButton = new Button("Fetch More Images", new ClickHandler() {
			public void onClick(ClickEvent event) {
				fetchButton.setHTML("<table><tr><td style='border: 0;'>" + spinnerHTML
						+ "</td><td style='border: 0;'>Fetching Images...</td></tr></table>");
				fetchButton.setEnabled(false);
				Timer t = new Timer() {
					public void run() {
						presenter.fetchMore(resourceList.size());
					}
				};
				t.schedule(1000);
			}
		});
		flexTable.setWidget(numRows, 0, fetchButton);
		cellFormatter.setColSpan(numRows, 0, DEFAULT_NUM_COLS);
		cellFormatter.setHorizontalAlignment(numRows, 0, HasHorizontalAlignment.ALIGN_CENTER);
		fetchButton.setHTML("<table><tr><td style='border: 0;'>" + spinnerHTML
				+ "</td><td style='border: 0;'>Fetching Images...</td></tr></table>");
		fetchButton.setEnabled(false);
		Timer t = new Timer() {
			public void run() {
				presenter.fetchMore(resourceList.size());
			}
		};
		t.schedule(1000);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public void removeDeletedItem() {
		resourceList.remove(selectedIndex);
		flexTable.removeCell(selectedRow, selectedCol);
	}

	public void showImage(String data) {
		Image img = new Image();
		img.setUrl("data:image/" + "png" + ";base64," + data);
		imagePanel.clear();
		imagePanel.add(img);
		previewImage = img;
		dialogBox.show();
	}

	public void newDataToDisplay(List<ResourceDTO> pNewList) {
		int lastRow = flexTable.getRowCount() - 1;
		if (pNewList.size() > 0) {
			int numExistingImages = resourceList.size();
			int nextColumn = numExistingImages % DEFAULT_NUM_COLS;
			for (ResourceDTO res : pNewList) {
				HTML thumb = null;
				if (res.getImageData() != null && res.getImageData().length() > 0) {
					thumb = new HTML();
					thumb.setHTML("<img src=\"data:image/" + res.getType() + ";base64," + res.getImageData() + "\"/>");
					if (nextColumn == 0) { // we have to add another row
						flexTable.insertRow(lastRow);
						for (int i = 0; i < DEFAULT_NUM_COLS; i++) {
							flexTable.insertCell(lastRow, 0);
						}
						lastRow += 1;
					}
					flexTable.setWidget(lastRow - 1, nextColumn, thumb);
					cellFormatter.setHorizontalAlignment(lastRow - 1, nextColumn, HasHorizontalAlignment.ALIGN_CENTER);
					nextColumn = (nextColumn + 1) % DEFAULT_NUM_COLS; // increment nextColumn and ensure it wraps around
					this.resourceList.add(res);// Only add images that we could show successfully
				}
			}
		} else {
			Window.alert("No more images to fetch");
		}
		fetchButton.setText("Fetch More Images");
		fetchButton.setEnabled(true);
	}

	// @UiHandler("gridLink")
	// void onGridLinkClick(ClickEvent event) {
	// this.presenter.gridLinkClick();
	// }

	@UiHandler("uploadLink")
	void onUploadLinkClick(ClickEvent event) {
		this.presenter.uploadLinkClick();
	}

	/**
	 * Create the dialog box for this example.
	 * 
	 * @return the new dialog box
	 */
	private DialogBox createDialogBox() {
		// Create a dialog box and set the caption text
		final DialogBox dialogBox = new DialogBox();
		dialogBox.ensureDebugId("cwDialogBox");
		dialogBox.setText("Image preview");

		// Create a table to layout the content
		VerticalPanel dialogContents = new VerticalPanel();
		dialogContents.setSpacing(4);
		dialogBox.setWidget(dialogContents);

		// Add some text to the top of the dialog
		HTML details = new HTML("Click Close to go back to the wall of images.");
		dialogContents.add(details);
		dialogContents.setCellHorizontalAlignment(details, HasHorizontalAlignment.ALIGN_CENTER);

		// Add an image panel to the dialog

		dialogContents.add(imagePanel);
		dialogContents.setCellHorizontalAlignment(imagePanel, HasHorizontalAlignment.ALIGN_CENTER);

		// Add a close button at the bottom of the dialog
		Button closeButton = new Button("Close This Dialog", new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		// Add a delete button at the bottom of the dialog
		Button deleteButton = new Button("Delete this Image", new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (Window.confirm("Are you sure you want to delete this image?")) {
					presenter.deleteResource(resourceList.get(selectedIndex).getId());
					dialogBox.hide();
				}
			}
		});
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(closeButton);
		buttonPanel.add(deleteButton);
		dialogContents.add(buttonPanel);
		dialogContents.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_CENTER);

		// Return the dialog box
		return dialogBox;
	}
}
