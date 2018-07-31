package com.vdm.starlight.client.educator.view.student;

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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vdm.starlight.client.editor.HasImage;
import com.vdm.starlight.client.resources.EducatorResources;
import com.vdm.starlight.shared.dto.StudentDTO;

public class StudentDialogViewImpl extends Composite implements StudentDialogView, HasImage {
	@UiTemplate("StudentDialogView.ui.xml")
	interface StudentDialogViewUiBinder extends UiBinder<Widget, StudentDialogViewImpl> {
	}

	static EducatorResources resources = EducatorResources.INSTANCE;
	private static final int NUM_COLS = 2;
	private static StudentDialogViewUiBinder uiBinder = GWT.create(StudentDialogViewUiBinder.class);

	@UiField
	FlexTable flexTable;
	@UiField
	Button createButton;

	FlexCellFormatter cellFormatter;

	TextBox usernameEdit;
	TextBox passwordEdit;
	TextBox nameEdit;
	TextBox surnameEdit;
	TextBox ageGroupEdit;
	Button selectImageButton;
	SimplePanel imagePanel;

	private Presenter presenter;

	private int imageID;

	StudentDTO studentInfo;

	public StudentDialogViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		imageID = -1;
		usernameEdit = new TextBox();
		usernameEdit.setWidth("219px");
		passwordEdit = new TextBox();
		passwordEdit.setWidth("219px");
		nameEdit = new TextBox();
		nameEdit.setWidth("219px");
		surnameEdit = new TextBox();
		surnameEdit.setWidth("219px");
		ageGroupEdit = new TextBox();
		ageGroupEdit.setWidth("219px");
		imagePanel = new SimplePanel();
		imagePanel.setStyleName(resources.starlightCSS().imagePanel());
		imagePanel.add(new Image(resources.defaultProfile()));
		imagePanel.setWidth("219px");
		createButton.setHeight("40px");
		selectImageButton = new Button("Browse for an image", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				presenter.showImagePicker();

			}
		});

		cellFormatter = flexTable.getFlexCellFormatter();
		flexTable.setWidth("325px");
		flexTable.setHTML(0, 0, "Please complete the fields below");
		cellFormatter.setColSpan(0, 0, NUM_COLS);
		cellFormatter.setStyleName(0, 0, resources.starlightCSS().helpTip());
		flexTable.setCellSpacing(2);
		flexTable.setCellPadding(2);
		flexTable.setWidget(1, 0, new Label("Username:"));
		flexTable.setWidget(1, 1, usernameEdit);
		flexTable.setWidget(2, 0, new Label("Password:"));
		flexTable.setWidget(2, 1, passwordEdit);
		flexTable.setWidget(3, 0, new Label("Name:"));
		flexTable.setWidget(3, 1, nameEdit);
		flexTable.setWidget(4, 0, new Label("Surname:"));
		flexTable.setWidget(4, 1, surnameEdit);
		flexTable.setWidget(5, 0, new Label("Age Group:"));
		flexTable.setWidget(5, 1, ageGroupEdit);
		flexTable.setWidget(6, 0, new Label("Workbook Icon:"));
		flexTable.setWidget(6, 1, imagePanel);
		cellFormatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidget(7, 0, selectImageButton);
		cellFormatter.setColSpan(7, 0, NUM_COLS);
		cellFormatter.setHorizontalAlignment(7, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		usernameEdit.setFocus(true);
		usernameEdit.setTabIndex(1);
		passwordEdit.setTabIndex(2);
		nameEdit.setTabIndex(3);
		surnameEdit.setTabIndex(4);
		ageGroupEdit.setTabIndex(5);
		selectImageButton.setTabIndex(6);
		createButton.setTabIndex(7);
	}

	public StudentDialogViewImpl(int studentID) {
		new StudentDialogViewImpl();
		presenter.getStudentInfo(studentID);
	}

	public void resetFields() {
		usernameEdit.setText("");
		passwordEdit.setText("");
		nameEdit.setText("");
		surnameEdit.setText("");
		ageGroupEdit.setText("");
		imageID = -1;
		imagePanel.clear();
		imagePanel.add(new Image(resources.defaultProfile()));
	}

	@UiHandler("createButton")
	public void onSaveButtonClick(ClickEvent event) {
		if (validateFields()) {
			if (studentInfo == null) {
				String[] fields = { usernameEdit.getText(), passwordEdit.getText(), nameEdit.getText(),
						surnameEdit.getText(), ageGroupEdit.getText() };
				presenter.createStudent(fields, imageID);
			} else {
				// workbookBasicInfo.setName(nameEdit.getText(), "");
				// workbookBasicInfo.setDescription(descriptionEdit.getText());
				// workbookBasicInfo.setAgeGroup(ageGroupEdit.getText());
				// presenter.updateStudentInfo(workbookBasicInfo, imageID);
			}
		}
	}

	public boolean validateFields() {

		if (usernameEdit.getText().length() < 1) {
			Window.alert("Error: Please enter a username(used to login) for the student");
			return false;
		}
		if (passwordEdit.getText().length() < 1) {
			Window.alert("Error: Please enter a password for the student");
			return false;
		}
		if (nameEdit.getText().length() < 1) {
			Window.alert("Error: Please enter a name for the student");
			return false;
		}
		if (surnameEdit.getText().length() < 1) {
			Window.alert("Error: Please enter a surname for the student");
			return false;
		}
		return true;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	@Override
	public void studentCreatedResult(String string, int workbookID) {
		Window.alert("Student \"" + string + "\" created.");
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

	@UiHandler("studentsLink")
	public void onStudentsLinkClick(ClickEvent event) {
		this.presenter.studentsLinkClick();
	}

	@Override
	public void setStudentInfo(StudentDTO s) {
		studentInfo = s;
		nameEdit.setText(s.getName());
		// descriptionEdit.setText(s.getDescription());
		ageGroupEdit.setText(String.valueOf(s.getAge()));
		if (s.getImage() != null) {
			imagePanel.clear();
			Image img = new Image();
			img.setUrl(s.getImage());
			img.setWidth("140px");
			imagePanel.add(img);
		}

	}
}
