package com.vdm.starlight.client.editor;

import net.auroris.ColorPicker.client.ColorPicker;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vdm.starlight.client.DatabaseServiceAsync;
import com.vdm.starlight.client.editor.images.PropertyImages;
import com.vdm.starlight.client.editor.objects.Component;
import com.vdm.starlight.client.editor.objects.ImageObject;
import com.vdm.starlight.client.educator.presenter.MediaGridPresenter;
import com.vdm.starlight.client.educator.view.workbook.WorkbookEditorViewImpl;

public enum Property {

	Text(1, "Text", "Sets the text inside the component"), TextColour(2, "Text Colour",
			"Sets the text colour of the component"), TextSize(3, "Text Size", "Sets the text size of the component"), TextBold(
			7, "Bold", "Sets the text weight of the component"), Width(4, "Width",
			"The width of the component excluding borders"), Height(5, "Height",
			"The height of the component excluding borders"), Pos(6, "Position on Canvas",
			"The distance from the top left corner of the page to the border"), BackgroundColor(8, "BackgroundColour",
			"The colour of the background of the component"), Image(9, "Image", "Sets the image to be displayed"), InputType(
			10, "Input Type", "Sets the type of input that can be entered"), NumInputChar(11, "Number Char",
			"Sets the number of characters allowed in the box"), Input(12, "Input Text", "The input the user types in"), Layer(
			13, "Layer", "Allows the creator to move components infront or behind other components"), Lines(14,
			"Lines", "Number of visible lines of text"), Align(15, "Alignment",
			"The alignment of the text of the component"), List(16, "List", "The list of options to be dislayed"), Border(
			18, "Border", "The type of border to use"), BorderColour(19, "Border Colour", "The colour of the border."), BorderType(
			20, "Border Type", "The type of the border"), BackgroundImage(21, "Background Image",
			"Sets the image on the background of the component"), BorderWidth(22, "Border Width",
			"The width of the border"), Caption(23, "Text", "The line of text above the component.");

	public static PropertyImages images = GWT.create(PropertyImages.class);
	public static DatabaseServiceAsync dbService;
	public static ImagePickerViewImpl imagePickerView;
	public static Iterable<Widget> widgets;
	private int id;
	private String name;
	private String description;

	private Property(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public static Property getProperty(int id) {
		for (Property p : Property.values()) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}

	public static Property getProperty(String name) {
		for (Property p : Property.values()) {
			if (p.getName().equals(name))
				return p;
		}
		return null;
	}

	public static Widget getPropertyWidget(String name, String value, final PickupDragController dragController) {
		widgets = dragController.getSelectedWidgets();
		HorizontalPanel panel = null;
		Property p = Property.getProperty(name);
		switch (p) {
		case BackgroundColor:
			panel = getColorWidget(BackgroundColor, value, images.colorfill());
			break;
		case Height:
			panel = getNumberWidget(Height, value, images.verticalArrow());
			break;
		case Image:
			panel = getImageWidget(Image, value, images.image());
			break;
		case Input:
			break;
		case InputType:
			String[] options = { "String", "Integer", "Float" };
			panel = getOptionsWidget(InputType, value, images.inputType(), options);
			break;
		case Layer:
			String[] captions = { "forward", "backward" };
			panel = getButtonsWidget(Layer, value, captions, images.layers());
			break;
		case NumInputChar:
			break;
		case Pos:
			// No widget to show
			break;
		case Text:
			panel = getTextWidget(Text, value, true);
			break;
		case TextBold:
			String[] option = { "bold", "normal" };
			panel = getOptionsWidget(TextBold, value, images.bold(), option);
			break;
		case TextColour:
			panel = getColorWidget(TextColour, value, images.TextColor());
			break;
		case TextSize:
			String[] sizes = { "10pt", "14pt", "18pt", "22pt", "26pt", "36pt", "44pt", "50pt", "60pt", "70pt", "90pt" };
			panel = getOptionsWidget(TextSize, value, images.SizeSmaller(), sizes);
			break;
		case Width:
			panel = getNumberWidget(Width, value, images.horizontalArrow());
			break;
		case Align:
			String[] patterns = { "left", "center", "right" };
			panel = getOptionsWidget(Align, value, images.alignCenter(), patterns);
			break;
		case Lines:
			break;
		case List:
			panel = getTextWidget(List, value, false);
			break;
		case BorderColour:
			panel = getColorWidget(BorderColour, value, images.borderColour());
			break;
		case BorderWidth:
			panel = getNumberWidget(BorderWidth, value, images.borderWidth());
			break;
		case Caption:
			panel = getTextWidget(Caption, value, true);
			break;
		default:
			break;
		}
		return panel;
	}

	/* ***************************************** NEW NEW ***************************************** */

	public static class TextDialog extends DialogBox {

		public TextDialog(String value, final Property p) {
			WorkbookEditorViewImpl.isModal = true;
			TextDialog.this.setModal(true);
			TextDialog.this.getElement().getStyle().setZIndex(200);
			setText("Enter text to display:");

			VerticalPanel panel2 = new VerticalPanel();
			panel2.setSpacing(3);

			final TextArea nameBox = new TextArea();
			nameBox.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if ((event.getNativeKeyCode() == 220) || (event.getNativeKeyCode() == 94)
							|| (event.getNativeKeyCode() == 52) || (event.getNativeKeyCode() == 54)
							|| (event.getNativeKeyCode() == 192)) {
						String text = nameBox.getText();
						int index = text.length() - 1;
						if (index > 0)
							nameBox.setText(text.substring(0, index));
					}
				}
			});
			nameBox.setSize("400px", "400px");
			nameBox.setText(value);
			panel2.add(nameBox);

			Button ok = new Button("OK");
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					String text = nameBox.getText();
					text = text.replaceAll("~`\\^\\|\\$", "");

					if (text.length() > 0) { // if text was entered
						if (p.equals(Property.List)) { // Parse into list
							for (Widget s : widgets) {
								((Component) s).call(Property.List.getName(), text, false);
							}
						} else {
							for (Widget s : widgets) {
								((Component) s).call(p.getName(), text, false);
							}
						}
					}
					TextDialog.this.hide();
					WorkbookEditorViewImpl.isModal = false;
				}
			});
			Button cancel = new Button("Cancel");
			cancel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					TextDialog.this.hide();
					WorkbookEditorViewImpl.isModal = false;

				}
			});
			HorizontalPanel panel = new HorizontalPanel();
			panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			panel.add(ok);
			panel.add(cancel);
			panel.setWidth("100%");
			panel2.add(panel);
			setWidget(panel2);
		}
	}

	public static class ColourDialog extends DialogBox {
		private ColorPicker picker;

		public ColourDialog(String value, final Property p, final Label colour) {
			WorkbookEditorViewImpl.isModal = true;
			ColourDialog.this.getElement().getStyle().setZIndex(200);
			HorizontalPanel panel = new HorizontalPanel();
			VerticalPanel panel2 = new VerticalPanel();
			panel2.setSpacing(3);
			panel.setSpacing(3);
			setText("Choose a colour");
			final CheckBox transparent = new CheckBox("Make transparent", false);
			transparent.setValue(false);
			panel2.add(transparent);
			picker = new ColorPicker();
			Button ok = new Button("OK");
			ok.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (transparent.getValue()) {
						colour.getElement().getStyle().setBackgroundColor("white");
						((Component) widgets.iterator().next()).call(p.getName(), "transparent", false);
					} else {
						String color = "#" + picker.getHexColor();
						colour.getElement().getStyle().setBackgroundColor(color);
						((Component) widgets.iterator().next()).call(p.getName(), color, false);
					}
					ColourDialog.this.hide();
					WorkbookEditorViewImpl.isModal = false;
				}
			});
			Button cancel = new Button("Cancel");
			cancel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ColourDialog.this.hide();
					WorkbookEditorViewImpl.isModal = false;
				}
			});
			panel2.add(picker);
			// if (value.equals("transparent")) {
			// transparent.setValue(true);
			// } else if (value.contains("#")) {
			// try {
			// picker.setHex(value.substring(1));
			// } catch (Exception e) {
			// }
			// } else if (value.contains("rgb")) {
			// try {
			// String[] values = value.substring(4, value.length() - 1).split(",");
			// picker.setRGB(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
			// } catch (Exception e) {
			// }
			// }
			panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			panel.setWidth("100%");
			panel.add(ok);
			panel.add(cancel);
			panel2.add(panel);
			setWidget(panel2);
		}
	}

	public static HorizontalPanel getNumberWidget(final Property property, String value, ImageResource image) {
		// Container Panel
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(3);

		// Image
		com.google.gwt.user.client.ui.Image im = new com.google.gwt.user.client.ui.Image(image.getSafeUri());
		im.setPixelSize(25, 25);
		panel.add(im);

		// Input
		final TextBox w = new TextBox();
		w.setText(value); // Apply old retrieved value
		w.setWidth("4em");
		w.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				for (Widget widget : widgets) {
					String params = (((TextBox) w).getText()); // get the new value

					if (params.length() > 0) { // if the textbox is not empty
						int number = Integer.parseInt(params);

						if (number > 0) {
							if ((property.equals(Property.Width) && number <= 950)
									|| ((property.equals(Property.Height) && number <= 600)) || (number <= 100))
								((Component) widget).call(property.getName(), params, false);
						}

					}
				}
			}
		});
		panel.add(w);

		// Description
		w.addMouseOverHandler(new ToolTipListener(property.getDescription()));
		w.addMouseOutHandler(new ToolTipListener(property.getDescription()));

		// Validate
		NumberInputValidator validator = new NumberInputValidator(w, com.vdm.starlight.client.editor.InputType.Integer);
		w.addKeyUpHandler(validator);

		// Label
		panel.add(new Label("px"));

		return panel;
	}

	public static HorizontalPanel getColorWidget(final Property property, final String value, ImageResource image) {
		// Container
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(3);

		// Image
		com.google.gwt.user.client.ui.Image im = new com.google.gwt.user.client.ui.Image(image.getSafeUri());
		im.setPixelSize(25, 25);
		panel.add(im);

		// Input
		final Label colour = new Label();
		// colour.getElement().getStyle().setPaddingRight(3, Unit.PX);
		// colour.getElement().getStyle().setPaddingLeft(3, Unit.PX);
		colour.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		colour.getElement().getStyle().setBackgroundColor(value);
		colour.getElement().getStyle().setBorderWidth(1, Unit.PX);
		colour.getElement().getStyle().setPaddingTop(6, Unit.PX);
		colour.setWidth("4em");
		colour.setHeight("15px");

		Button choose = new Button("Choose");
		choose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new ColourDialog(value, property, colour).center();
			}
		});

		// Description
		choose.addMouseOverHandler(new ToolTipListener(property.getDescription()));
		choose.addMouseOutHandler(new ToolTipListener(property.getDescription()));

		// panel
		panel.add(colour);
		panel.add(choose);
		return panel;
	}

	public static HorizontalPanel getButtonsWidget(final Property property, String value, String[] buttonCaptions,
			ImageResource image) {
		// Container
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(3);

		// Image
		com.google.gwt.user.client.ui.Image im = new com.google.gwt.user.client.ui.Image(image.getSafeUri());
		im.setPixelSize(25, 25);
		panel.add(im);

		// Input
		Button forwardButton = new Button(buttonCaptions[0]);
		Button backButton = new Button(buttonCaptions[1]);
		forwardButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (Widget widget : widgets) {
					((Component) widget).call(property.getName(), "foward", false); // larger
				}
			}
		});
		backButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (Widget widget : widgets) {
					((Component) widget).call(property.getName(), "back", false); // smaller
				}
			}
		});

		// Description
		forwardButton.addMouseOverHandler(new ToolTipListener(
				"Brings the selected component forward (to the front of other Components"));
		backButton.addMouseOutHandler(new ToolTipListener(
				"Takes the selected component backwards (to the back of other Components"));

		// Panel
		panel.add(backButton);
		panel.add(forwardButton);
		return panel;
	}

	private static HorizontalPanel getOptionsWidget(final Property property, String value, ImageResource image,
			String[] patterns) {
		// Container
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(3);

		// Image
		com.google.gwt.user.client.ui.Image im = new com.google.gwt.user.client.ui.Image(image.getSafeUri());
		im.setPixelSize(25, 25);
		panel.add(im);

		// Input
		final ListBox w = new ListBox();
		w.setWidth("10em");
		for (int i = 0; i < patterns.length; i++) {
			((ListBox) w).addItem(patterns[i]);
			if (patterns[i].equals(value))
				((ListBox) w).setSelectedIndex(i);
		}

		w.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String params = "";
				for (Widget widget : widgets) {
					params = ((ListBox) w).getItemText(((ListBox) w).getSelectedIndex());
					((Component) widget).call(property.getName(), params, false);
				}
			}
		});

		// Description
		w.addMouseOverHandler(new ToolTipListener(property.getDescription()));
		w.addMouseOutHandler(new ToolTipListener(property.getDescription()));

		// panel
		panel.add(w);
		return panel;
	}

	public static HorizontalPanel getImageWidget(final Property property, String value, ImageResource image) {
		// Container
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(3);

		// Image
		com.google.gwt.user.client.ui.Image im = new com.google.gwt.user.client.ui.Image(image.getSafeUri());
		im.setPixelSize(25, 25);
		panel.add(im);

		// Input
		Button butt = new Button("Change Image");
		butt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boolean loadImages = false;
				if (imagePickerView == null) {
					imagePickerView = new ImagePickerViewImpl();
					loadImages = true;
				}
				for (Widget widget : widgets) {
					MediaGridPresenter pres = new MediaGridPresenter(dbService, imagePickerView, (ImageObject) widget);
					if (loadImages) {
						pres.go(null);
					}
					break;

				}
				imagePickerView.imagePickerDialog.center();
			}
		});
		butt.addMouseOverHandler(new ToolTipListener(property.getDescription()));
		butt.addMouseOutHandler(new ToolTipListener(property.getDescription()));

		panel.add(butt);
		return panel;
	}

	public static HorizontalPanel getTextWidget(final Property property, final String value, boolean showTextBox) {
		// Container
		HorizontalPanel panel = new HorizontalPanel();
		panel.setSpacing(3);

		// Image
		Label name = new Label(property.getName());
		name.getElement().getStyle().setWidth(25, Unit.PX);
		name.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		name.getElement().getStyle().setPaddingTop(5, Unit.PX);
		panel.add(name);

		// Description
		name.addMouseOverHandler(new ToolTipListener(property.getDescription()));
		name.addMouseOutHandler(new ToolTipListener(property.getDescription()));

		// Input
		if (showTextBox) {
			final TextBox w = new TextBox();
			w.setWidth("7em");
			w.setText(value);
			w.addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					for (Widget widget : widgets) {
						String params = (((TextBox) w).getText());
						((Component) widget).call(property.getName(), params, false);
					}
				}
			});
			w.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					for (Widget widget : widgets) {
						String params = (((TextBox) w).getText());
						((Component) widget).call(property.getName(), params, false);
					}
				}
			});
			panel.add(w);
		}

		// Button
		Button more = new Button("Change");
		more.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				new TextDialog(value, property).center();
			}
		});
		panel.add(more);
		return panel;
	}

}
