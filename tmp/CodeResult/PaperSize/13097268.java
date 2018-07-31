/**
 * ReportGear(2011)
 */
package com.reportgear.report.print.pane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.print.PageFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.reportgear.core.util.ImageUtils;
import com.reportgear.core.util.UnitUtils;
import com.reportgear.report.model.ReportModel;
import com.reportgear.report.print.Margin;
import com.reportgear.report.print.PaperSize;
import com.reportgear.report.setting.PaperSetting;
import com.reportgear.report.setting.ReportSettings;

/**
 * ????
 * 
 * @version 1.0 2011-4-20
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since Report 1.0
 */
public class PagePane extends JPanel {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -3938637965121401764L;
	private JRadioButton portraitRadioButton;
	private JRadioButton landscapeRadioButton;
	private JRadioButton predefinedRadioButton;
	private JRadioButton customRadioButton;
	private JComboBox predefinedComboBox;
	private JSpinner paperWidthSpinner;
	private JSpinner paperHeightSpinner;
	private UnitFieldPane marginTopUnitFieldPane;
	private UnitFieldPane marginLeftUnitFieldPane;
	private UnitFieldPane marginBottomUnitFieldPane;
	private UnitFieldPane marginRightUnitFieldPane;
	private UnitFieldPane headerUnitFieldPane;
	private UnitFieldPane footerUnitFieldPane;

	private ShowPagePane showPagePane;

	public PagePane() {
		this.setLayout(new BorderLayout());

		JPanel container = new JPanel();
		this.add(container, BorderLayout.NORTH);
		container.setLayout(new BoxLayout(container, 1));

		// ??
		JPanel pane1 = new JPanel();
		container.add(pane1);
		pane1.setBorder(BorderFactory.createTitledBorder("??"));
		pane1.setLayout(new GridLayout(1, 2));
		Icon icon1 = ImageUtils.readIcon("/resources/images/dialog/pagesetup/portrait.png");
		this.portraitRadioButton = new JRadioButton("??");
		this.portraitRadioButton.setMnemonic('t');
		pane1.add(createIconRadioPane(icon1, this.portraitRadioButton));

		Icon icon2 = ImageUtils.readIcon("/resources/images/dialog/pagesetup/landScape.png");
		this.landscapeRadioButton = new JRadioButton("??");
		this.landscapeRadioButton.setMnemonic('L');
		pane1.add(createIconRadioPane(icon2, this.landscapeRadioButton));

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(this.portraitRadioButton);
		btnGroup.add(this.landscapeRadioButton);
		this.portraitRadioButton.setSelected(true);

		// ??
		JPanel pane2 = new JPanel();
		pane2.setLayout(new FlowLayout(1));
		this.showPagePane = new ShowPagePane();
		pane2.add(this.showPagePane);
		container.add(pane2);
		pane2.setBorder(BorderFactory.createTitledBorder("??"));

		// ????
		JPanel pane3 = new JPanel();
		container.add(pane3);
		pane3.setBorder(BorderFactory.createTitledBorder("????"));
		pane3.setLayout(new BoxLayout(pane3, 0));
		this.predefinedRadioButton = new JRadioButton("???:");
		this.predefinedRadioButton.setMnemonic('P');
		this.customRadioButton = new JRadioButton("???:");
		this.customRadioButton.setMnemonic('C');
		this.predefinedComboBox = new JComboBox();
		this.paperWidthSpinner = new JSpinner(new SpinnerNumberModel(0.0D, 0.0D, Long.MAX_VALUE, 1.0D));
		((JSpinner.DefaultEditor) this.paperWidthSpinner.getEditor()).getTextField().setColumns(7);
		this.paperHeightSpinner = new JSpinner(new SpinnerNumberModel(0.0D, 0.0D, Long.MAX_VALUE, 1.0D));
		((JSpinner.DefaultEditor) this.paperHeightSpinner.getEditor()).getTextField().setColumns(7);
		for (PaperSize o : PaperSize.PAPER_SIZE_ARRAY) {
			this.predefinedComboBox.addItem(o);
		}
		pane3.add(Box.createHorizontalStrut(8));
		JPanel radioPane = new JPanel();
		pane3.add(radioPane);
		radioPane.setLayout(new GridLayout(2, 1));
		radioPane.add(this.predefinedRadioButton);
		radioPane.add(this.customRadioButton);
		ButtonGroup btnGroup1 = new ButtonGroup();
		btnGroup1.add(this.predefinedRadioButton);
		btnGroup1.add(this.customRadioButton);

		JPanel sizePane = new JPanel();
		pane3.add(sizePane);
		sizePane.setLayout(new GridLayout(2, 1));
		JPanel sizeItemsPane = new JPanel();
		sizePane.add(sizeItemsPane);
		sizeItemsPane.setLayout(new FlowLayout(0, 4, 2));
		sizeItemsPane.add(this.predefinedComboBox);

		JPanel sizeCustomPane = new JPanel();
		sizeCustomPane.setLayout(new FlowLayout(0, 4, 2));
		sizeCustomPane.add(this.paperWidthSpinner);
		sizeCustomPane.add(new JLabel("x"));
		sizeCustomPane.add(this.paperHeightSpinner);
		JLabel lblUnit = new JLabel("??");
		Dimension dimUnit = new Dimension(lblUnit.getPreferredSize().width,
				this.paperHeightSpinner.getPreferredSize().height);
		lblUnit.setPreferredSize(dimUnit);
		sizeCustomPane.add(lblUnit);
		sizePane.add(sizeCustomPane);
		pane3.add(Box.createHorizontalStrut(12));

		// ??
		JPanel pane4 = new JPanel();
		container.add(pane4);
		pane4.setBorder(BorderFactory.createTitledBorder("??"));
		pane4.setLayout(new GridLayout(1, 2));
		JPanel paneMarginTB = new JPanel();
		pane4.add(paneMarginTB);
		paneMarginTB.setLayout(new FlowLayout(0, 4, 1));
		paneMarginTB.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 0));
		JPanel lblMarginTB = new JPanel();
		lblMarginTB.setLayout(new GridLayout(2, 1, 1, 2));
		lblMarginTB.add(new JLabel("?:"));
		lblMarginTB.add(new JLabel("?:"));
		paneMarginTB.add(lblMarginTB);

		JPanel inputMarginTB = new JPanel();
		inputMarginTB.setLayout(new GridLayout(2, 1, 1, 2));
		this.marginTopUnitFieldPane = new UnitFieldPane();
		this.marginBottomUnitFieldPane = new UnitFieldPane();
		inputMarginTB.add(this.marginTopUnitFieldPane);
		inputMarginTB.add(this.marginBottomUnitFieldPane);
		paneMarginTB.add(inputMarginTB);

		JPanel paneMarginLR = new JPanel();
		pane4.add(paneMarginLR);
		paneMarginLR.setLayout(new FlowLayout(0, 4, 1));
		paneMarginLR.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 0));
		JPanel lblMarinLR = new JPanel();
		paneMarginLR.add(lblMarinLR);
		lblMarinLR.setLayout(new GridLayout(2, 1, 1, 2));
		lblMarinLR.add(new JLabel("?:"));
		lblMarinLR.add(new JLabel("?:"));

		JPanel inputMarginLR = new JPanel();
		paneMarginLR.add(inputMarginLR);
		inputMarginLR.setLayout(new GridLayout(2, 1, 1, 2));
		this.marginLeftUnitFieldPane = new UnitFieldPane();
		this.marginRightUnitFieldPane = new UnitFieldPane();
		inputMarginLR.add(this.marginLeftUnitFieldPane);
		inputMarginLR.add(this.marginRightUnitFieldPane);
	}

	public void populate(ReportModel reportModel) {
		ReportSettings reportSetting = reportModel.getReportSettings();
		PaperSetting paperSetting = reportSetting.getPaperSetting();
		if (paperSetting.getOrientation() == PageFormat.LANDSCAPE)
			this.landscapeRadioButton.setSelected(true);
		else
			this.portraitRadioButton.setSelected(true);

		PaperSize paperSize = paperSetting.getPaperSize();

		PaperSize[] sizeArr = PaperSize.PAPER_SIZE_ARRAY;
		boolean isPreDefined = false;
		for (int i = 0, len = sizeArr.length; i < len; i++) {
			PaperSize ps = sizeArr[i];
			if (!ps.equals(paperSize)) {
				continue;
			}

			this.predefinedComboBox.setSelectedIndex(i);
			this.predefinedRadioButton.setSelected(true);

			isPreDefined = true;
			break;
		}

		if (!isPreDefined) {
			this.customRadioButton.setSelected(true);
			this.paperWidthSpinner.setValue(UnitUtils.inch2mm(paperSize.getWidth()));
			this.paperHeightSpinner.setValue(UnitUtils.inch2mm(paperSize.getHeight()));
		}

		Margin margin = paperSetting.getMargin();

		this.marginTopUnitFieldPane.setValue(UnitUtils.inch2mm(margin.getTop()));
		this.marginLeftUnitFieldPane.setValue(UnitUtils.inch2mm(margin.getLeft()));
		this.marginBottomUnitFieldPane.setValue(UnitUtils.inch2mm(margin.getBottom()));
		this.marginRightUnitFieldPane.setValue(UnitUtils.inch2mm(margin.getRight()));
	}

	public void update(ReportModel model) {
		ReportSettings reportSettings = model.getReportSettings();
		PaperSetting paperSetting = new PaperSetting();
		reportSettings.setPaperSetting(paperSetting);

		if (this.landscapeRadioButton.isSelected())
			paperSetting.setOrientation(PageFormat.LANDSCAPE);
		else
			paperSetting.setOrientation(PageFormat.PORTRAIT);

		if (this.predefinedRadioButton.isSelected()) {
			paperSetting.setPaperSize((PaperSize) this.predefinedComboBox.getSelectedItem());

		} else if (this.customRadioButton.isSelected()) {
			paperSetting.setPaperSize(PaperSize.getInstance("", UnitUtils.mm2inch(((Number) this.paperWidthSpinner
					.getValue()).doubleValue()), UnitUtils.mm2inch(((Number) this.paperHeightSpinner.getValue())
					.doubleValue())));
		}

		Margin margin = paperSetting.getMargin();
		margin.setTop(UnitUtils.mm2inch(this.marginTopUnitFieldPane.getValue()));
		margin.setLeft(UnitUtils.mm2inch(this.marginLeftUnitFieldPane.getValue()));
		margin.setBottom(UnitUtils.mm2inch(this.marginBottomUnitFieldPane.getValue()));
		margin.setRight(UnitUtils.mm2inch(this.marginRightUnitFieldPane.getValue()));
	}

	// ????????
	private JPanel createIconRadioPane(Icon icon, JRadioButton radio) {
		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout(1));
		pane.add(new JLabel(icon));
		pane.add(radio);

		return pane;
	}
}