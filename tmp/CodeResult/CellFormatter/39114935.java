package com.vdm.starlight.client.educator.view.classroom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vdm.starlight.client.editor.HasImage;
import com.vdm.starlight.client.resources.EducatorResources;
import com.vdm.starlight.shared.dto.ClassroomDTO;

public class ClassroomEditDialogViewImpl extends Composite implements ClassroomDialogView, HasImage {
	@UiTemplate("ClassroomEditDialogView.ui.xml")
	interface ClassroomEditDialogViewUiBinder extends UiBinder<Widget, ClassroomEditDialogViewImpl> {
	}

	static EducatorResources resources = EducatorResources.INSTANCE;
	private static final int NUM_COLS = 2;
	private static ClassroomEditDialogViewUiBinder uiBinder = GWT.create(ClassroomEditDialogViewUiBinder.class);

	@UiField
	FlexTable flexTable;
	@UiField
	Button createButton;
	@UiField
	Button cancelButton;
	@UiField
	public DialogBox editClassroomDialog;

	FlexCellFormatter cellFormatter;
	TextBox nameEdit;
	TextArea descriptionEdit;
	TextBox ageGroupEdit;
	Button selectImageButton;
	SimplePanel imagePanel;

	private Presenter presenter;

	private int imageID;

	ClassroomDTO classroomBasicInfo;

	public ClassroomEditDialogViewImpl(int classroomID) {
		uiBinder.createAndBindUi(this);
		editClassroomDialog.getElement().getStyle().setZIndex(200);
		editClassroomDialog.show();
		imageID = -1;
		nameEdit = new TextBox();
		nameEdit.setWidth("219px");
		descriptionEdit = new TextArea();
		descriptionEdit.setWidth("219px");
		descriptionEdit.setHeight("110px");
		ageGroupEdit = new TextBox();
		ageGroupEdit.setWidth("219px");
		imagePanel = new SimplePanel();
		imagePanel.setStyleName(resources.starlightCSS().imagePanel());
		imagePanel.add(new Image(resources.deur()));
		imagePanel.setWidth("219px");
		createButton.setHeight("40px");
		cancelButton.setHeight("40px");
		createButton.setWidth("80px");
		cancelButton.setWidth("80px");
		selectImageButton = new Button("Browse for an image", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.showImagePicker();

			}
		});

		cellFormatter = flexTable.getFlexCellFormatter();
		// flexTable.setWidth("325px");
		flexTable.setHTML(0, 0, "Please complete the fields below");
		cellFormatter.setColSpan(0, 0, NUM_COLS);
		cellFormatter.setStyleName(0, 0, resources.starlightCSS().helpTip());
		flexTable.setCellSpacing(2);
		flexTable.setCellPadding(2);
		flexTable.setWidget(1, 0, new Label("Name:"));
		flexTable.setWidget(1, 1, nameEdit);
		flexTable.setWidget(2, 0, new Label("Description:"));
		flexTable.setWidget(2, 1, descriptionEdit);
		flexTable.setWidget(3, 0, new Label("Age Group:"));
		flexTable.setWidget(3, 1, ageGroupEdit);
		flexTable.setWidget(4, 0, new Label("Classroom Icon:"));
		flexTable.setWidget(4, 1, imagePanel);
		cellFormatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(5, 0, selectImageButton);
		cellFormatter.setColSpan(5, 0, NUM_COLS);
		cellFormatter.setHorizontalAlignment(5, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		nameEdit.setFocus(true);
		nameEdit.setTabIndex(1);
		descriptionEdit.setTabIndex(2);
		ageGroupEdit.setTabIndex(3);
		selectImageButton.setTabIndex(4);
		createButton.setTabIndex(5);
		cancelButton.setTabIndex(6);
		// presenter.getClassroomInfo(classroomID);
	}

	public void resetFields() {
		nameEdit.setText("");
		descriptionEdit.setText("");
		ageGroupEdit.setText("");
		imageID = -1;
		imagePanel.clear();
		imagePanel.add(new Image(resources.deur()));
	}

	@Override
	public Widget asWidget() {
		editClassroomDialog.show();
		return this;
	}

	@UiHandler("cancelButton")
	public void onCancelButtonClick(ClickEvent event) {
		editClassroomDialog.hide();
	}

	@UiHandler("createButton")
	public void onSaveButtonClick(ClickEvent event) {
		if (validateFields()) {
			if (classroomBasicInfo == null) {
				String name = nameEdit.getText();
				String description = descriptionEdit.getText();
				String ageGroup = ageGroupEdit.getText();
				String[] text = { name, description, ageGroup };
				presenter.createClassroom(text, imageID);
			} else {
				classroomBasicInfo.setName(nameEdit.getText());
				classroomBasicInfo.setDescription(descriptionEdit.getText());
				classroomBasicInfo.setAgeGroup(ageGroupEdit.getText());
				presenter.updateClassroomBasicInfo(classroomBasicInfo, imageID);

			}
		}
	}

	public boolean validateFields() {

		if (nameEdit.getText().length() < 1) {
			Window.alert("Error: Please enter a name for the classroom");
			return false;
		}
		if (descriptionEdit.getText().length() < 1) {
			Window.alert("Error: Please enter a description for the classroom");
			return false;
		}
		return true;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void classroomCreatedResult(String string, int classroomID) {
		Window.alert("Classroom \"" + string + "\" created.");
		resetFields();
	}

	@Override
	public void setImage(int id, String imageData, int width, int height) {
		this.imageID = id;
		Image img = new Image();
		img.setUrl(imageData);
		img.setWidth("140px");
		imagePanel.clear();
		imagePanel.add(img);

	}

	@Override
	public void setClassroomInfo(ClassroomDTO w) {
		resetFields();
		classroomBasicInfo = w;
		nameEdit.setText(w.getName());
		descriptionEdit.setText(w.getDescription());
		ageGroupEdit.setText(String.valueOf(w.getAgeGroup()));
		imagePanel.clear();
		Image img = new Image();
		if (!w.isDefaultImage()) {
			img.setUrl(w.getImage());
		} else {
			img.setUrl(resources.deur().getSafeUri());
		}

		img.setWidth("140px");
		imagePanel.add(img);
	}
}
