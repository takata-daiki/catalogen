/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.ui.trans.steps.xmlinputstream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.TransPreviewFactory;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.steps.xmlinputstream.XMLInputStreamMeta;
import org.pentaho.di.ui.core.dialog.EnterNumberDialog;
import org.pentaho.di.ui.core.dialog.EnterTextDialog;
import org.pentaho.di.ui.core.dialog.PreviewRowsDialog;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.dialog.TransPreviewProgressDialog;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

//TODO correct sizing of window
public class XMLInputStreamDialog extends BaseStepDialog implements StepDialogInterface
{
	private static Class<?> PKG = XMLInputStreamMeta.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

	// for tabs later on:
	//	private CTabFolder   wTabFolder;
	//	private FormData     fdTabFolder;
	//
	//	private CTabItem     wFileTab;
	//	private CTabItem     wContentTab;
	//	private CTabItem     wFieldsTab;

	private TextVar wFilename;

	private Button wbbFilename; // Browse for a file

	private Button wAddResult;

	private TextVar wRowsToSkip;

	private TextVar wLimit;

	private TextVar wDefaultStringLen;

	private TextVar wEncoding;

	private Button wEnableNamespaces;

	private Button wEnableTrim;

	private Button wIncludeFilename;

	private Text wFilenameField;

	private Button wIncludeRowNumber;

	private Text wRowNumberField;

	private Button wIncludeXmlDataTypeNumeric;

	private Text wXmlDataTypeNumericField;

	private Button wIncludeXmlDataTypeDescription;

	private Text wXmlDataTypeDescriptionField;

	private Button wIncludeXmlLocationLine;

	private Text wXmlLocationLineField;

	private Button wIncludeXmlLocationColumn;

	private Text wXmlLocationColumnField;

	private Button wIncludeXmlElementID;

	private Text wXmlElementIDField;

	private Button wIncludeXmlParentElementID;

	private Text wXmlParentElementIDField;

	private Button wIncludeXmlBlockID;

	private Text wXmlBlockIDField;

	private Text wXmlBlockTagField;

	private Button wIncludeXmlElementLevel;

	private Text wXmlElementLevelField;

	private Button wIncludeXmlPath;

	private Text wXmlPathField;

	private Button wIncludeXmlParentPath;

	private Text wXmlParentPathField;

	private Button wIncludeXmlDataName;

	private Text wXmlDataNameField;

	private Button wIncludeXmlDataValue;

	private Text wXmlDataValueField;

	private final XMLInputStreamMeta inputMeta;

	public XMLInputStreamDialog(Shell parent, Object in, TransMeta tr, String sname)
	{
		super(parent, (BaseStepMeta) in, tr, sname);
		this.inputMeta = (XMLInputStreamMeta) in;
	}

	@Override
	public String open()
	{
		Shell parent = this.getParent();
		Display display = parent.getDisplay();

		this.shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
		this.props.setLook(this.shell);
		this.setShellImage(this.shell, this.inputMeta);

		ModifyListener lsMod = new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				XMLInputStreamDialog.this.inputMeta.setChanged();
			}
		};
		this.changed = this.inputMeta.hasChanged();

		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;

		this.shell.setLayout(formLayout);
		this.shell.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Shell.Text")); //$NON-NLS-1$

		int middle = this.props.getMiddlePct();
		int margin = Const.MARGIN;

		// Step name line
		//
		this.wlStepname = new Label(this.shell, SWT.RIGHT);
		this.wlStepname.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Stepname.Label")); //$NON-NLS-1$
		this.props.setLook(this.wlStepname);
		this.fdlStepname = new FormData();
		this.fdlStepname.left = new FormAttachment(0, 0);
		this.fdlStepname.right = new FormAttachment(middle, -margin);
		this.fdlStepname.top = new FormAttachment(0, margin);
		this.wlStepname.setLayoutData(this.fdlStepname);
		this.wStepname = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wStepname);
		this.wStepname.addModifyListener(lsMod);
		this.fdStepname = new FormData();
		this.fdStepname.left = new FormAttachment(middle, 0);
		this.fdStepname.top = new FormAttachment(0, margin);
		this.fdStepname.right = new FormAttachment(100, 0);
		this.wStepname.setLayoutData(this.fdStepname);
		Control lastControl = this.wStepname;

		// split into tabs for better overview, later on:
		//		wTabFolder = new CTabFolder(shell, SWT.BORDER);
		//		props.setLook(wTabFolder, Props.WIDGET_STYLE_TAB);
		//
		//
		//		addFilesTab();
		//		addContentTab();
		//		addAdditionalFieldsTab();
		//
		//		fdTabFolder = new FormData();
		//		fdTabFolder.left  = new FormAttachment(0, 0);
		//		fdTabFolder.top   = new FormAttachment(wStepname, margin);
		//		fdTabFolder.right = new FormAttachment(100, 0);
		//		fdTabFolder.bottom= new FormAttachment(100, -50);
		//		wTabFolder.setLayoutData(fdTabFolder);

		// Filename...
		//
		// The filename browse button
		//
		this.wbbFilename = new Button(this.shell, SWT.PUSH | SWT.CENTER);
		this.props.setLook(this.wbbFilename);
		this.wbbFilename.setText(BaseMessages.getString(PKG, "System.Button.Browse"));
		this.wbbFilename.setToolTipText(BaseMessages.getString(PKG, "System.Tooltip.BrowseForFileOrDirAndAdd"));
		FormData fdbFilename = new FormData();
		fdbFilename.top = new FormAttachment(lastControl, margin);
		fdbFilename.right = new FormAttachment(100, 0);
		this.wbbFilename.setLayoutData(fdbFilename);

		// The field itself...
		//
		Label wlFilename = new Label(this.shell, SWT.RIGHT);
		wlFilename.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Filename.Label")); //$NON-NLS-1$
		this.props.setLook(wlFilename);
		FormData fdlFilename = new FormData();
		fdlFilename.top = new FormAttachment(lastControl, margin);
		fdlFilename.left = new FormAttachment(0, 0);
		fdlFilename.right = new FormAttachment(middle, -margin);
		wlFilename.setLayoutData(fdlFilename);
		this.wFilename = new TextVar(this.transMeta, this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wFilename);
		this.wFilename.addModifyListener(lsMod);
		FormData fdFilename = new FormData();
		fdFilename.top = new FormAttachment(lastControl, margin);
		fdFilename.left = new FormAttachment(middle, 0);
		fdFilename.right = new FormAttachment(this.wbbFilename, -margin);
		this.wFilename.setLayoutData(fdFilename);
		lastControl = this.wFilename;

		// add filename to result?
		//
		Label wlAddResult = new Label(this.shell, SWT.RIGHT);
		wlAddResult.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.AddResult.Label"));
		this.props.setLook(wlAddResult);
		FormData fdlAddResult = new FormData();
		fdlAddResult.left = new FormAttachment(0, 0);
		fdlAddResult.top = new FormAttachment(lastControl, margin);
		fdlAddResult.right = new FormAttachment(middle, -margin);
		wlAddResult.setLayoutData(fdlAddResult);
		this.wAddResult = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wAddResult);
		this.wAddResult.setToolTipText(BaseMessages.getString(PKG, "XMLInputStreamDialog.AddResult.Tooltip"));
		FormData fdAddResult = new FormData();
		fdAddResult.left = new FormAttachment(middle, 0);
		fdAddResult.top = new FormAttachment(lastControl, margin);
		this.wAddResult.setLayoutData(fdAddResult);
		lastControl = this.wAddResult;

		// RowsToSkip line
		//
		Label wlRowsToSkip = new Label(this.shell, SWT.RIGHT);
		wlRowsToSkip.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.RowsToSkip.Label")); //$NON-NLS-1$
		this.props.setLook(wlRowsToSkip);
		FormData fdlRowsToSkip = new FormData();
		fdlRowsToSkip = new FormData();
		fdlRowsToSkip.left = new FormAttachment(0, 0);
		fdlRowsToSkip.top = new FormAttachment(lastControl, margin);
		fdlRowsToSkip.right = new FormAttachment(middle, -margin);
		wlRowsToSkip.setLayoutData(fdlRowsToSkip);
		this.wRowsToSkip = new TextVar(this.transMeta, this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wRowsToSkip);
		this.wRowsToSkip.addModifyListener(lsMod);
		FormData fdRowsToSkip = new FormData();
		fdRowsToSkip = new FormData();
		fdRowsToSkip.left = new FormAttachment(middle, 0);
		fdRowsToSkip.top = new FormAttachment(lastControl, margin);
		fdRowsToSkip.right = new FormAttachment(100, 0);
		this.wRowsToSkip.setLayoutData(fdRowsToSkip);
		lastControl = this.wRowsToSkip;

		// Limit line
		//
		Label wlLimit = new Label(this.shell, SWT.RIGHT);
		wlLimit.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Limit.Label")); //$NON-NLS-1$
		this.props.setLook(wlLimit);
		FormData fdlLimit = new FormData();
		fdlLimit = new FormData();
		fdlLimit.left = new FormAttachment(0, 0);
		fdlLimit.top = new FormAttachment(lastControl, margin);
		fdlLimit.right = new FormAttachment(middle, -margin);
		wlLimit.setLayoutData(fdlLimit);
		this.wLimit = new TextVar(this.transMeta, this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wLimit);
		this.wLimit.addModifyListener(lsMod);
		FormData fdLimit = new FormData();
		fdLimit = new FormData();
		fdLimit.left = new FormAttachment(middle, 0);
		fdLimit.top = new FormAttachment(lastControl, margin);
		fdLimit.right = new FormAttachment(100, 0);
		this.wLimit.setLayoutData(fdLimit);
		lastControl = this.wLimit;

		// DefaultStringLen line
		//
		Label wlDefaultStringLen = new Label(this.shell, SWT.RIGHT);
		wlDefaultStringLen.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.DefaultStringLen.Label")); //$NON-NLS-1$
		this.props.setLook(wlDefaultStringLen);
		FormData fdlDefaultStringLen = new FormData();
		fdlDefaultStringLen = new FormData();
		fdlDefaultStringLen.left = new FormAttachment(0, 0);
		fdlDefaultStringLen.top = new FormAttachment(lastControl, margin);
		fdlDefaultStringLen.right = new FormAttachment(middle, -margin);
		wlDefaultStringLen.setLayoutData(fdlDefaultStringLen);
		this.wDefaultStringLen = new TextVar(this.transMeta, this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wDefaultStringLen);
		this.wDefaultStringLen.addModifyListener(lsMod);
		FormData fdDefaultStringLen = new FormData();
		fdDefaultStringLen = new FormData();
		fdDefaultStringLen.left = new FormAttachment(middle, 0);
		fdDefaultStringLen.top = new FormAttachment(lastControl, margin);
		fdDefaultStringLen.right = new FormAttachment(100, 0);
		this.wDefaultStringLen.setLayoutData(fdDefaultStringLen);
		lastControl = this.wDefaultStringLen;

		// Encoding line
		//
		Label wlEncoding = new Label(this.shell, SWT.RIGHT);
		wlEncoding.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Encoding.Label")); //$NON-NLS-1$
		this.props.setLook(wlEncoding);
		FormData fdlEncoding = new FormData();
		fdlEncoding = new FormData();
		fdlEncoding.left = new FormAttachment(0, 0);
		fdlEncoding.top = new FormAttachment(lastControl, margin);
		fdlEncoding.right = new FormAttachment(middle, -margin);
		wlEncoding.setLayoutData(fdlEncoding);
		this.wEncoding = new TextVar(this.transMeta, this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wEncoding);
		this.wEncoding.addModifyListener(lsMod);
		FormData fdEncoding = new FormData();
		fdEncoding = new FormData();
		fdEncoding.left = new FormAttachment(middle, 0);
		fdEncoding.top = new FormAttachment(lastControl, margin);
		fdEncoding.right = new FormAttachment(100, 0);
		this.wEncoding.setLayoutData(fdEncoding);
		lastControl = this.wEncoding;

		// EnableNamespaces?
		//
		Label wlEnableNamespaces = new Label(this.shell, SWT.RIGHT);
		wlEnableNamespaces.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.EnableNamespaces.Label"));
		this.props.setLook(wlEnableNamespaces);
		FormData fdlEnableNamespaces = new FormData();
		fdlEnableNamespaces.left = new FormAttachment(0, 0);
		fdlEnableNamespaces.top = new FormAttachment(lastControl, margin);
		fdlEnableNamespaces.right = new FormAttachment(middle, -margin);
		wlEnableNamespaces.setLayoutData(fdlEnableNamespaces);
		this.wEnableNamespaces = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wEnableNamespaces);
		this.wEnableNamespaces.setToolTipText(BaseMessages.getString(PKG, "XMLInputStreamDialog.EnableNamespaces.Tooltip"));
		FormData fdEnableNamespaces = new FormData();
		fdEnableNamespaces.left = new FormAttachment(middle, 0);
		fdEnableNamespaces.top = new FormAttachment(lastControl, margin);
		this.wEnableNamespaces.setLayoutData(fdEnableNamespaces);
		lastControl = this.wEnableNamespaces;

		// EnableTrim?
		//
		Label wlEnableTrim = new Label(this.shell, SWT.RIGHT);
		wlEnableTrim.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.EnableTrim.Label"));
		this.props.setLook(wlEnableTrim);
		FormData fdlEnableTrim = new FormData();
		fdlEnableTrim.left = new FormAttachment(0, 0);
		fdlEnableTrim.top = new FormAttachment(lastControl, margin);
		fdlEnableTrim.right = new FormAttachment(middle, -margin);
		wlEnableTrim.setLayoutData(fdlEnableTrim);
		this.wEnableTrim = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wEnableTrim);
		this.wEnableTrim.setToolTipText(BaseMessages.getString(PKG, "XMLInputStreamDialog.EnableTrim.Tooltip"));
		FormData fdEnableTrim = new FormData();
		fdEnableTrim.left = new FormAttachment(middle, 0);
		fdEnableTrim.top = new FormAttachment(lastControl, margin);
		this.wEnableTrim.setLayoutData(fdEnableTrim);
		lastControl = this.wEnableTrim;

		// IncludeFilename?
		//
		Label wlIncludeFilename = new Label(this.shell, SWT.RIGHT);
		wlIncludeFilename.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeFilename.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeFilename);
		FormData fdlIncludeFilename = new FormData();
		fdlIncludeFilename.top = new FormAttachment(lastControl, margin);
		fdlIncludeFilename.left = new FormAttachment(0, 0);
		fdlIncludeFilename.right = new FormAttachment(middle, -margin);
		wlIncludeFilename.setLayoutData(fdlIncludeFilename);
		this.wIncludeFilename = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeFilename);
		FormData fdIncludeFilename = new FormData();
		fdIncludeFilename.top = new FormAttachment(lastControl, margin);
		fdIncludeFilename.left = new FormAttachment(middle, 0);
		this.wIncludeFilename.setLayoutData(fdIncludeFilename);

		// FilenameField line
		//
		Label wlFilenameField = new Label(this.shell, SWT.RIGHT);
		wlFilenameField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlFilenameField);
		FormData fdlFilenameField = new FormData();
		fdlFilenameField = new FormData();
		fdlFilenameField.top = new FormAttachment(lastControl, margin);
		fdlFilenameField.left = new FormAttachment(this.wIncludeFilename, margin);
		wlFilenameField.setLayoutData(fdlFilenameField);
		this.wFilenameField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wFilenameField);
		this.wFilenameField.addModifyListener(lsMod);
		FormData fdFilenameField = new FormData();
		fdFilenameField = new FormData();
		fdFilenameField.top = new FormAttachment(lastControl, margin);
		fdFilenameField.left = new FormAttachment(wlFilenameField, margin);
		fdFilenameField.right = new FormAttachment(100, 0);
		this.wFilenameField.setLayoutData(fdFilenameField);
		lastControl = this.wFilenameField;

		// IncludeRowNumber?
		//
		Label wlIncludeRowNumber = new Label(this.shell, SWT.RIGHT);
		wlIncludeRowNumber.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeRowNumber.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeRowNumber);
		FormData fdlIncludeRowNumber = new FormData();
		fdlIncludeRowNumber.top = new FormAttachment(lastControl, margin);
		fdlIncludeRowNumber.left = new FormAttachment(0, 0);
		fdlIncludeRowNumber.right = new FormAttachment(middle, -margin);
		wlIncludeRowNumber.setLayoutData(fdlIncludeRowNumber);
		this.wIncludeRowNumber = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeRowNumber);
		FormData fdIncludeRowNumber = new FormData();
		fdIncludeRowNumber.top = new FormAttachment(lastControl, margin);
		fdIncludeRowNumber.left = new FormAttachment(middle, 0);
		this.wIncludeRowNumber.setLayoutData(fdIncludeRowNumber);

		// RowNumberField line
		//
		Label wlRowNumberField = new Label(this.shell, SWT.RIGHT);
		wlRowNumberField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlRowNumberField);
		FormData fdlRowNumberField = new FormData();
		fdlRowNumberField = new FormData();
		fdlRowNumberField.top = new FormAttachment(lastControl, margin);
		fdlRowNumberField.left = new FormAttachment(this.wIncludeRowNumber, margin);
		wlRowNumberField.setLayoutData(fdlRowNumberField);
		this.wRowNumberField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wRowNumberField);
		this.wRowNumberField.addModifyListener(lsMod);
		FormData fdRowNumberField = new FormData();
		fdRowNumberField = new FormData();
		fdRowNumberField.top = new FormAttachment(lastControl, margin);
		fdRowNumberField.left = new FormAttachment(wlRowNumberField, margin);
		fdRowNumberField.right = new FormAttachment(100, 0);
		this.wRowNumberField.setLayoutData(fdRowNumberField);
		lastControl = this.wRowNumberField;

		// IncludeXmlDataTypeNumeric?
		//
		Label wlIncludeXmlDataTypeNumeric = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlDataTypeNumeric.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlDataTypeNumeric.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlDataTypeNumeric);
		FormData fdlIncludeXmlDataTypeNumeric = new FormData();
		fdlIncludeXmlDataTypeNumeric.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlDataTypeNumeric.left = new FormAttachment(0, 0);
		fdlIncludeXmlDataTypeNumeric.right = new FormAttachment(middle, -margin);
		wlIncludeXmlDataTypeNumeric.setLayoutData(fdlIncludeXmlDataTypeNumeric);
		this.wIncludeXmlDataTypeNumeric = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlDataTypeNumeric);
		FormData fdIncludeXmlDataTypeNumeric = new FormData();
		fdIncludeXmlDataTypeNumeric.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlDataTypeNumeric.left = new FormAttachment(middle, 0);
		this.wIncludeXmlDataTypeNumeric.setLayoutData(fdIncludeXmlDataTypeNumeric);

		// XmlDataTypeNumericField line
		//
		Label wlXmlDataTypeNumericField = new Label(this.shell, SWT.RIGHT);
		wlXmlDataTypeNumericField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlDataTypeNumericField);
		FormData fdlXmlDataTypeNumericField = new FormData();
		fdlXmlDataTypeNumericField = new FormData();
		fdlXmlDataTypeNumericField.top = new FormAttachment(lastControl, margin);
		fdlXmlDataTypeNumericField.left = new FormAttachment(this.wIncludeXmlDataTypeNumeric, margin);
		wlXmlDataTypeNumericField.setLayoutData(fdlXmlDataTypeNumericField);
		this.wXmlDataTypeNumericField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlDataTypeNumericField);
		this.wXmlDataTypeNumericField.addModifyListener(lsMod);
		FormData fdXmlDataTypeNumericField = new FormData();
		fdXmlDataTypeNumericField = new FormData();
		fdXmlDataTypeNumericField.top = new FormAttachment(lastControl, margin);
		fdXmlDataTypeNumericField.left = new FormAttachment(wlXmlDataTypeNumericField, margin);
		fdXmlDataTypeNumericField.right = new FormAttachment(100, 0);
		this.wXmlDataTypeNumericField.setLayoutData(fdXmlDataTypeNumericField);
		lastControl = this.wXmlDataTypeNumericField;

		// IncludeXmlDataTypeDescription?
		//
		Label wlIncludeXmlDataTypeDescription = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlDataTypeDescription.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlDataTypeDescription.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlDataTypeDescription);
		FormData fdlIncludeXmlDataTypeDescription = new FormData();
		fdlIncludeXmlDataTypeDescription.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlDataTypeDescription.left = new FormAttachment(0, 0);
		fdlIncludeXmlDataTypeDescription.right = new FormAttachment(middle, -margin);
		wlIncludeXmlDataTypeDescription.setLayoutData(fdlIncludeXmlDataTypeDescription);
		this.wIncludeXmlDataTypeDescription = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlDataTypeDescription);
		FormData fdIncludeXmlDataTypeDescription = new FormData();
		fdIncludeXmlDataTypeDescription.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlDataTypeDescription.left = new FormAttachment(middle, 0);
		this.wIncludeXmlDataTypeDescription.setLayoutData(fdIncludeXmlDataTypeDescription);

		// XmlDataTypeDescriptionField line
		//
		Label wlXmlDataTypeDescriptionField = new Label(this.shell, SWT.RIGHT);
		wlXmlDataTypeDescriptionField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlDataTypeDescriptionField);
		FormData fdlXmlDataTypeDescriptionField = new FormData();
		fdlXmlDataTypeDescriptionField = new FormData();
		fdlXmlDataTypeDescriptionField.top = new FormAttachment(lastControl, margin);
		fdlXmlDataTypeDescriptionField.left = new FormAttachment(this.wIncludeXmlDataTypeDescription, margin);
		wlXmlDataTypeDescriptionField.setLayoutData(fdlXmlDataTypeDescriptionField);
		this.wXmlDataTypeDescriptionField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlDataTypeDescriptionField);
		this.wXmlDataTypeDescriptionField.addModifyListener(lsMod);
		FormData fdXmlDataTypeDescriptionField = new FormData();
		fdXmlDataTypeDescriptionField = new FormData();
		fdXmlDataTypeDescriptionField.top = new FormAttachment(lastControl, margin);
		fdXmlDataTypeDescriptionField.left = new FormAttachment(wlXmlDataTypeDescriptionField, margin);
		fdXmlDataTypeDescriptionField.right = new FormAttachment(100, 0);
		this.wXmlDataTypeDescriptionField.setLayoutData(fdXmlDataTypeDescriptionField);
		lastControl = this.wXmlDataTypeDescriptionField;

		// IncludeXmlLocationLine?
		//
		Label wlIncludeXmlLocationLine = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlLocationLine.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlLocationLine.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlLocationLine);
		FormData fdlIncludeXmlLocationLine = new FormData();
		fdlIncludeXmlLocationLine.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlLocationLine.left = new FormAttachment(0, 0);
		fdlIncludeXmlLocationLine.right = new FormAttachment(middle, -margin);
		wlIncludeXmlLocationLine.setLayoutData(fdlIncludeXmlLocationLine);
		this.wIncludeXmlLocationLine = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlLocationLine);
		FormData fdIncludeXmlLocationLine = new FormData();
		fdIncludeXmlLocationLine.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlLocationLine.left = new FormAttachment(middle, 0);
		this.wIncludeXmlLocationLine.setLayoutData(fdIncludeXmlLocationLine);

		// XmlLocationLineField line
		//
		Label wlXmlLocationLineField = new Label(this.shell, SWT.RIGHT);
		wlXmlLocationLineField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlLocationLineField);
		FormData fdlXmlLocationLineField = new FormData();
		fdlXmlLocationLineField = new FormData();
		fdlXmlLocationLineField.top = new FormAttachment(lastControl, margin);
		fdlXmlLocationLineField.left = new FormAttachment(this.wIncludeXmlLocationLine, margin);
		wlXmlLocationLineField.setLayoutData(fdlXmlLocationLineField);
		this.wXmlLocationLineField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlLocationLineField);
		this.wXmlLocationLineField.addModifyListener(lsMod);
		FormData fdXmlLocationLineField = new FormData();
		fdXmlLocationLineField = new FormData();
		fdXmlLocationLineField.top = new FormAttachment(lastControl, margin);
		fdXmlLocationLineField.left = new FormAttachment(wlXmlLocationLineField, margin);
		fdXmlLocationLineField.right = new FormAttachment(100, 0);
		this.wXmlLocationLineField.setLayoutData(fdXmlLocationLineField);
		lastControl = this.wXmlLocationLineField;

		// IncludeXmlLocationColumn?
		//
		Label wlIncludeXmlLocationColumn = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlLocationColumn.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlLocationColumn.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlLocationColumn);
		FormData fdlIncludeXmlLocationColumn = new FormData();
		fdlIncludeXmlLocationColumn.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlLocationColumn.left = new FormAttachment(0, 0);
		fdlIncludeXmlLocationColumn.right = new FormAttachment(middle, -margin);
		wlIncludeXmlLocationColumn.setLayoutData(fdlIncludeXmlLocationColumn);
		this.wIncludeXmlLocationColumn = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlLocationColumn);
		FormData fdIncludeXmlLocationColumn = new FormData();
		fdIncludeXmlLocationColumn.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlLocationColumn.left = new FormAttachment(middle, 0);
		this.wIncludeXmlLocationColumn.setLayoutData(fdIncludeXmlLocationColumn);

		// XmlLocationColumnField line
		//
		Label wlXmlLocationColumnField = new Label(this.shell, SWT.RIGHT);
		wlXmlLocationColumnField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlLocationColumnField);
		FormData fdlXmlLocationColumnField = new FormData();
		fdlXmlLocationColumnField = new FormData();
		fdlXmlLocationColumnField.top = new FormAttachment(lastControl, margin);
		fdlXmlLocationColumnField.left = new FormAttachment(this.wIncludeXmlLocationColumn, margin);
		wlXmlLocationColumnField.setLayoutData(fdlXmlLocationColumnField);
		this.wXmlLocationColumnField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlLocationColumnField);
		this.wXmlLocationColumnField.addModifyListener(lsMod);
		FormData fdXmlLocationColumnField = new FormData();
		fdXmlLocationColumnField = new FormData();
		fdXmlLocationColumnField.top = new FormAttachment(lastControl, margin);
		fdXmlLocationColumnField.left = new FormAttachment(wlXmlLocationColumnField, margin);
		fdXmlLocationColumnField.right = new FormAttachment(100, 0);
		this.wXmlLocationColumnField.setLayoutData(fdXmlLocationColumnField);
		lastControl = this.wXmlLocationColumnField;

		// IncludeXmlElementID?
		//
		Label wlIncludeXmlElementID = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlElementID.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlElementID.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlElementID);
		FormData fdlIncludeXmlElementID = new FormData();
		fdlIncludeXmlElementID.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlElementID.left = new FormAttachment(0, 0);
		fdlIncludeXmlElementID.right = new FormAttachment(middle, -margin);
		wlIncludeXmlElementID.setLayoutData(fdlIncludeXmlElementID);
		this.wIncludeXmlElementID = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlElementID);
		FormData fdIncludeXmlElementID = new FormData();
		fdIncludeXmlElementID.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlElementID.left = new FormAttachment(middle, 0);
		this.wIncludeXmlElementID.setLayoutData(fdIncludeXmlElementID);

		// XmlElementIDField line
		//
		Label wlXmlElementIDField = new Label(this.shell, SWT.RIGHT);
		wlXmlElementIDField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlElementIDField);
		FormData fdlXmlElementIDField = new FormData();
		fdlXmlElementIDField = new FormData();
		fdlXmlElementIDField.top = new FormAttachment(lastControl, margin);
		fdlXmlElementIDField.left = new FormAttachment(this.wIncludeXmlElementID, margin);
		wlXmlElementIDField.setLayoutData(fdlXmlElementIDField);
		this.wXmlElementIDField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlElementIDField);
		this.wXmlElementIDField.addModifyListener(lsMod);
		FormData fdXmlElementIDField = new FormData();
		fdXmlElementIDField = new FormData();
		fdXmlElementIDField.top = new FormAttachment(lastControl, margin);
		fdXmlElementIDField.left = new FormAttachment(wlXmlElementIDField, margin);
		fdXmlElementIDField.right = new FormAttachment(100, 0);
		this.wXmlElementIDField.setLayoutData(fdXmlElementIDField);
		lastControl = this.wXmlElementIDField;

		// IncludeXmlParentElementID?
		//
		Label wlIncludeXmlParentElementID = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlParentElementID.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlParentElementID.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlParentElementID);
		FormData fdlIncludeXmlParentElementID = new FormData();
		fdlIncludeXmlParentElementID.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlParentElementID.left = new FormAttachment(0, 0);
		fdlIncludeXmlParentElementID.right = new FormAttachment(middle, -margin);
		wlIncludeXmlParentElementID.setLayoutData(fdlIncludeXmlParentElementID);
		this.wIncludeXmlParentElementID = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlParentElementID);
		FormData fdIncludeXmlParentElementID = new FormData();
		fdIncludeXmlParentElementID.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlParentElementID.left = new FormAttachment(middle, 0);
		this.wIncludeXmlParentElementID.setLayoutData(fdIncludeXmlParentElementID);

		// XmlParentElementIDField line
		//
		Label wlXmlParentElementIDField = new Label(this.shell, SWT.RIGHT);
		wlXmlParentElementIDField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlParentElementIDField);
		FormData fdlXmlParentElementIDField = new FormData();
		fdlXmlParentElementIDField = new FormData();
		fdlXmlParentElementIDField.top = new FormAttachment(lastControl, margin);
		fdlXmlParentElementIDField.left = new FormAttachment(this.wIncludeXmlParentElementID, margin);
		wlXmlParentElementIDField.setLayoutData(fdlXmlParentElementIDField);
		this.wXmlParentElementIDField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlParentElementIDField);
		this.wXmlParentElementIDField.addModifyListener(lsMod);
		FormData fdXmlParentElementIDField = new FormData();
		fdXmlParentElementIDField = new FormData();
		fdXmlParentElementIDField.top = new FormAttachment(lastControl, margin);
		fdXmlParentElementIDField.left = new FormAttachment(wlXmlParentElementIDField, margin);
		fdXmlParentElementIDField.right = new FormAttachment(100, 0);
		this.wXmlParentElementIDField.setLayoutData(fdXmlParentElementIDField);
		lastControl = this.wXmlParentElementIDField;

		//-------

		// IncludeXmlBlockID?
		//
		Label wlIncludeXmlBlockID = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlBlockID.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlBlockID.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlBlockID);
		FormData fdlIncludeXmlBlockID = new FormData();
		fdlIncludeXmlBlockID.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlBlockID.left = new FormAttachment(0, 0);
		fdlIncludeXmlBlockID.right = new FormAttachment(middle, -margin);
		wlIncludeXmlBlockID.setLayoutData(fdlIncludeXmlBlockID);
		this.wIncludeXmlBlockID = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlBlockID);
		FormData fdIncludeXmlBlockID = new FormData();
		fdIncludeXmlBlockID.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlBlockID.left = new FormAttachment(middle, 0);
		this.wIncludeXmlBlockID.setLayoutData(fdIncludeXmlBlockID);

		// XmlBlockIDField line
		//
		Label wlXmlBlockIDField = new Label(this.shell, SWT.RIGHT);
		wlXmlBlockIDField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlBlockIDField);
		FormData fdlXmlBlockIDField = new FormData();
		fdlXmlBlockIDField = new FormData();
		fdlXmlBlockIDField.top = new FormAttachment(lastControl, margin);
		fdlXmlBlockIDField.left = new FormAttachment(this.wIncludeXmlBlockID, margin);
		wlXmlBlockIDField.setLayoutData(fdlXmlBlockIDField);

		this.wXmlBlockIDField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlBlockIDField);
		this.wXmlBlockIDField.addModifyListener(lsMod);
		FormData fdXmlBlockIDField = new FormData();
		fdXmlBlockIDField = new FormData();
		fdXmlBlockIDField.top = new FormAttachment(lastControl, margin);
		fdXmlBlockIDField.left = new FormAttachment(wlXmlBlockIDField, margin);
		fdXmlBlockIDField.right = new FormAttachment(75, 0);
		this.wXmlBlockIDField.setLayoutData(fdXmlBlockIDField);
		lastControl = this.wXmlBlockIDField;

		// XmlBlockTagField line
		//
		Label wlXmlBlockTagField = new Label(this.shell, SWT.RIGHT);
		wlXmlBlockTagField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.xmlDataName.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlBlockTagField);
		FormData fdlXmlBlockTagField = new FormData();
		fdlXmlBlockTagField = new FormData();
		fdlXmlBlockTagField.top = new FormAttachment(this.wXmlParentElementIDField, margin);
		fdlXmlBlockTagField.left = new FormAttachment(lastControl, margin);
		wlXmlBlockTagField.setLayoutData(fdlXmlBlockTagField);

		this.wXmlBlockTagField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlBlockTagField);
		this.wXmlBlockTagField.addModifyListener(lsMod);
		FormData fdXmlBlockTagField = new FormData();
		fdXmlBlockTagField = new FormData();
		fdXmlBlockTagField.top = new FormAttachment(this.wXmlParentElementIDField, margin);
		fdXmlBlockTagField.left = new FormAttachment(wlXmlBlockTagField, margin);
		fdXmlBlockTagField.right = new FormAttachment(100, 0);
		this.wXmlBlockTagField.setLayoutData(fdXmlBlockTagField);

		//------

		// IncludeXmlElementLevel?
		//
		Label wlIncludeXmlElementLevel = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlElementLevel.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlElementLevel.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlElementLevel);
		FormData fdlIncludeXmlElementLevel = new FormData();
		fdlIncludeXmlElementLevel.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlElementLevel.left = new FormAttachment(0, 0);
		fdlIncludeXmlElementLevel.right = new FormAttachment(middle, -margin);
		wlIncludeXmlElementLevel.setLayoutData(fdlIncludeXmlElementLevel);
		this.wIncludeXmlElementLevel = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlElementLevel);
		FormData fdIncludeXmlElementLevel = new FormData();
		fdIncludeXmlElementLevel.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlElementLevel.left = new FormAttachment(middle, 0);
		this.wIncludeXmlElementLevel.setLayoutData(fdIncludeXmlElementLevel);

		// XmlElementLevelField line
		//
		Label wlXmlElementLevelField = new Label(this.shell, SWT.RIGHT);
		wlXmlElementLevelField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlElementLevelField);
		FormData fdlXmlElementLevelField = new FormData();
		fdlXmlElementLevelField = new FormData();
		fdlXmlElementLevelField.top = new FormAttachment(lastControl, margin);
		fdlXmlElementLevelField.left = new FormAttachment(this.wIncludeXmlElementLevel, margin);
		wlXmlElementLevelField.setLayoutData(fdlXmlElementLevelField);
		this.wXmlElementLevelField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlElementLevelField);
		this.wXmlElementLevelField.addModifyListener(lsMod);
		FormData fdXmlElementLevelField = new FormData();
		fdXmlElementLevelField = new FormData();
		fdXmlElementLevelField.top = new FormAttachment(lastControl, margin);
		fdXmlElementLevelField.left = new FormAttachment(wlXmlElementLevelField, margin);
		fdXmlElementLevelField.right = new FormAttachment(100, 0);
		this.wXmlElementLevelField.setLayoutData(fdXmlElementLevelField);
		lastControl = this.wXmlElementLevelField;

		// IncludeXmlPath?
		//
		Label wlIncludeXmlPath = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlPath.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlPath.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlPath);
		FormData fdlIncludeXmlPath = new FormData();
		fdlIncludeXmlPath.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlPath.left = new FormAttachment(0, 0);
		fdlIncludeXmlPath.right = new FormAttachment(middle, -margin);
		wlIncludeXmlPath.setLayoutData(fdlIncludeXmlPath);
		this.wIncludeXmlPath = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlPath);
		FormData fdIncludeXmlPath = new FormData();
		fdIncludeXmlPath.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlPath.left = new FormAttachment(middle, 0);
		this.wIncludeXmlPath.setLayoutData(fdIncludeXmlPath);

		// XmlPathField line
		//
		Label wlXmlPathField = new Label(this.shell, SWT.RIGHT);
		wlXmlPathField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlPathField);
		FormData fdlXmlPathField = new FormData();
		fdlXmlPathField = new FormData();
		fdlXmlPathField.top = new FormAttachment(lastControl, margin);
		fdlXmlPathField.left = new FormAttachment(this.wIncludeXmlPath, margin);
		wlXmlPathField.setLayoutData(fdlXmlPathField);
		this.wXmlPathField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlPathField);
		this.wXmlPathField.addModifyListener(lsMod);
		FormData fdXmlPathField = new FormData();
		fdXmlPathField = new FormData();
		fdXmlPathField.top = new FormAttachment(lastControl, margin);
		fdXmlPathField.left = new FormAttachment(wlXmlPathField, margin);
		fdXmlPathField.right = new FormAttachment(100, 0);
		this.wXmlPathField.setLayoutData(fdXmlPathField);
		lastControl = this.wXmlPathField;

		// IncludeXmlParentPath?
		//
		Label wlIncludeXmlParentPath = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlParentPath.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlParentPath.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlParentPath);
		FormData fdlIncludeXmlParentPath = new FormData();
		fdlIncludeXmlParentPath.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlParentPath.left = new FormAttachment(0, 0);
		fdlIncludeXmlParentPath.right = new FormAttachment(middle, -margin);
		wlIncludeXmlParentPath.setLayoutData(fdlIncludeXmlParentPath);
		this.wIncludeXmlParentPath = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlParentPath);
		FormData fdIncludeXmlParentPath = new FormData();
		fdIncludeXmlParentPath.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlParentPath.left = new FormAttachment(middle, 0);
		this.wIncludeXmlParentPath.setLayoutData(fdIncludeXmlParentPath);

		// XmlParentPathField line
		//
		Label wlXmlParentPathField = new Label(this.shell, SWT.RIGHT);
		wlXmlParentPathField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlParentPathField);
		FormData fdlXmlParentPathField = new FormData();
		fdlXmlParentPathField = new FormData();
		fdlXmlParentPathField.top = new FormAttachment(lastControl, margin);
		fdlXmlParentPathField.left = new FormAttachment(this.wIncludeXmlParentPath, margin);
		wlXmlParentPathField.setLayoutData(fdlXmlParentPathField);
		this.wXmlParentPathField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlParentPathField);
		this.wXmlParentPathField.addModifyListener(lsMod);
		FormData fdXmlParentPathField = new FormData();
		fdXmlParentPathField = new FormData();
		fdXmlParentPathField.top = new FormAttachment(lastControl, margin);
		fdXmlParentPathField.left = new FormAttachment(wlXmlParentPathField, margin);
		fdXmlParentPathField.right = new FormAttachment(100, 0);
		this.wXmlParentPathField.setLayoutData(fdXmlParentPathField);
		lastControl = this.wXmlParentPathField;

		// IncludeXmlDataName?
		//
		Label wlIncludeXmlDataName = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlDataName.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlDataName.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlDataName);
		FormData fdlIncludeXmlDataName = new FormData();
		fdlIncludeXmlDataName.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlDataName.left = new FormAttachment(0, 0);
		fdlIncludeXmlDataName.right = new FormAttachment(middle, -margin);
		wlIncludeXmlDataName.setLayoutData(fdlIncludeXmlDataName);
		this.wIncludeXmlDataName = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlDataName);
		FormData fdIncludeXmlDataName = new FormData();
		fdIncludeXmlDataName.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlDataName.left = new FormAttachment(middle, 0);
		this.wIncludeXmlDataName.setLayoutData(fdIncludeXmlDataName);

		// XmlDataNameField line
		//
		Label wlXmlDataNameField = new Label(this.shell, SWT.RIGHT);
		wlXmlDataNameField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlDataNameField);
		FormData fdlXmlDataNameField = new FormData();
		fdlXmlDataNameField = new FormData();
		fdlXmlDataNameField.top = new FormAttachment(lastControl, margin);
		fdlXmlDataNameField.left = new FormAttachment(this.wIncludeXmlDataName, margin);
		wlXmlDataNameField.setLayoutData(fdlXmlDataNameField);
		this.wXmlDataNameField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlDataNameField);
		this.wXmlDataNameField.addModifyListener(lsMod);
		FormData fdXmlDataNameField = new FormData();
		fdXmlDataNameField = new FormData();
		fdXmlDataNameField.top = new FormAttachment(lastControl, margin);
		fdXmlDataNameField.left = new FormAttachment(wlXmlDataNameField, margin);
		fdXmlDataNameField.right = new FormAttachment(100, 0);
		this.wXmlDataNameField.setLayoutData(fdXmlDataNameField);
		lastControl = this.wXmlDataNameField;

		// IncludeXmlDataValue?
		//
		Label wlIncludeXmlDataValue = new Label(this.shell, SWT.RIGHT);
		wlIncludeXmlDataValue.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.IncludeXmlDataValue.Label")); //$NON-NLS-1$
		this.props.setLook(wlIncludeXmlDataValue);
		FormData fdlIncludeXmlDataValue = new FormData();
		fdlIncludeXmlDataValue.top = new FormAttachment(lastControl, margin);
		fdlIncludeXmlDataValue.left = new FormAttachment(0, 0);
		fdlIncludeXmlDataValue.right = new FormAttachment(middle, -margin);
		wlIncludeXmlDataValue.setLayoutData(fdlIncludeXmlDataValue);
		this.wIncludeXmlDataValue = new Button(this.shell, SWT.CHECK);
		this.props.setLook(this.wIncludeXmlDataValue);
		FormData fdIncludeXmlDataValue = new FormData();
		fdIncludeXmlDataValue.top = new FormAttachment(lastControl, margin);
		fdIncludeXmlDataValue.left = new FormAttachment(middle, 0);
		this.wIncludeXmlDataValue.setLayoutData(fdIncludeXmlDataValue);

		// XmlDataValueField line
		//
		Label wlXmlDataValueField = new Label(this.shell, SWT.RIGHT);
		wlXmlDataValueField.setText(BaseMessages.getString(PKG, "XMLInputStreamDialog.Fieldname.Label")); //$NON-NLS-1$
		this.props.setLook(wlXmlDataValueField);
		FormData fdlXmlDataValueField = new FormData();
		fdlXmlDataValueField = new FormData();
		fdlXmlDataValueField.top = new FormAttachment(lastControl, margin);
		fdlXmlDataValueField.left = new FormAttachment(this.wIncludeXmlDataValue, margin);
		wlXmlDataValueField.setLayoutData(fdlXmlDataValueField);
		this.wXmlDataValueField = new Text(this.shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		this.props.setLook(this.wXmlDataValueField);
		this.wXmlDataValueField.addModifyListener(lsMod);
		FormData fdXmlDataValueField = new FormData();
		fdXmlDataValueField = new FormData();
		fdXmlDataValueField.top = new FormAttachment(lastControl, margin);
		fdXmlDataValueField.left = new FormAttachment(wlXmlDataValueField, margin);
		fdXmlDataValueField.right = new FormAttachment(100, 0);
		this.wXmlDataValueField.setLayoutData(fdXmlDataValueField);
		lastControl = this.wXmlDataValueField;

		// Some buttons first, so that the dialog scales nicely...
		//
		this.wOK = new Button(this.shell, SWT.PUSH);
		this.wOK.setText(BaseMessages.getString(PKG, "System.Button.OK")); //$NON-NLS-1$
		this.wPreview = new Button(this.shell, SWT.PUSH);
		this.wPreview.setText(BaseMessages.getString(PKG, "System.Button.Preview")); //$NON-NLS-1$
		this.wCancel = new Button(this.shell, SWT.PUSH);
		this.wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel")); //$NON-NLS-1$

		this.setButtonPositions(new Button[] { this.wOK, this.wPreview, this.wCancel }, margin, lastControl);

		// Add listeners
		this.lsCancel = new Listener()
		{
			@Override
			public void handleEvent(Event e)
			{
				XMLInputStreamDialog.this.cancel();
			}
		};
		this.lsOK = new Listener()
		{
			@Override
			public void handleEvent(Event e)
			{
				XMLInputStreamDialog.this.ok();
			}
		};
		this.lsPreview = new Listener()
		{
			@Override
			public void handleEvent(Event e)
			{
				XMLInputStreamDialog.this.preview();
			}
		};

		this.wCancel.addListener(SWT.Selection, this.lsCancel);
		this.wOK.addListener(SWT.Selection, this.lsOK);
		this.wPreview.addListener(SWT.Selection, this.lsPreview);

		this.lsDef = new SelectionAdapter()
		{
			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
				XMLInputStreamDialog.this.ok();
			}
		};

		this.wStepname.addSelectionListener(this.lsDef);
		this.wFilename.addSelectionListener(this.lsDef);
		// TODO others

		// Listen to the browse button next to the file name
		this.wbbFilename.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog dialog = new FileDialog(XMLInputStreamDialog.this.shell, SWT.OPEN);
				dialog.setFilterExtensions(new String[] { "*.xml;*.XML", "*" });
				if (XMLInputStreamDialog.this.wFilename.getText() != null) {
					String fname = XMLInputStreamDialog.this.transMeta.environmentSubstitute(XMLInputStreamDialog.this.wFilename.getText());
					dialog.setFileName(fname);
				}

				dialog.setFilterNames(new String[] { BaseMessages.getString(PKG, "System.FileType.XMLFiles"), BaseMessages.getString(PKG, "System.FileType.AllFiles") });

				if (dialog.open() != null) {
					String str = dialog.getFilterPath() + System.getProperty("file.separator") + dialog.getFileName();
					XMLInputStreamDialog.this.wFilename.setText(str);
				}
			}
		});

		// Detect X or ALT-F4 or something that kills this window...
		this.shell.addShellListener(new ShellAdapter()
		{
			@Override
			public void shellClosed(ShellEvent e)
			{
				XMLInputStreamDialog.this.cancel();
			}
		});

		// Set the shell size, based upon previous time...
		this.setSize();

		this.getData();
		this.inputMeta.setChanged(this.changed);

		this.shell.open();
		while (!this.shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return this.stepname;
	}

	//	private void addFilesTab()
	//	{
	//		//////////////////////////
	//		// START OF FILE TAB   ///
	//		//////////////////////////###
	//
	//		wFileTab=new CTabItem(wTabFolder, SWT.NONE);
	//		wFileTab.setText(BaseMessages.getString(PKG, "TextFileInputDialog.FileTab.TabTitle"));
	//
	//		wFileSComp = new ScrolledComposite(wTabFolder, SWT.V_SCROLL | SWT.H_SCROLL );
	//		wFileSComp.setLayout(new FillLayout());
	//
	//		wFileComp = new Composite(wFileSComp, SWT.NONE );
	//		props.setLook(wFileComp);
	//
	//		FormLayout fileLayout = new FormLayout();
	//		fileLayout.marginWidth  = 3;
	//		fileLayout.marginHeight = 3;
	//		wFileComp.setLayout(fileLayout);
	//
	//		//###
	//	}
	//
	//	private void addContentTab()
	//	{
	//		//////////////////////////
	//		// START OF CONTENT TAB///
	//		///
	//		wContentTab=new CTabItem(wTabFolder, SWT.NONE);
	//		wContentTab.setText(BaseMessages.getString(PKG, "TextFileInputDialog.ContentTab.TabTitle"));
	//
	//		FormLayout contentLayout = new FormLayout ();
	//		contentLayout.marginWidth  = 3;
	//		contentLayout.marginHeight = 3;
	//
	//		wContentSComp = new ScrolledComposite(wTabFolder, SWT.V_SCROLL | SWT.H_SCROLL );
	//		wContentSComp.setLayout(new FillLayout());
	//
	//		wContentComp = new Composite(wContentSComp, SWT.NONE );
	//		props.setLook(wContentComp);
	//		wContentComp.setLayout(contentLayout);
	//
	//		//###
	//	}
	//
	//	private void addAdditionalFieldsTab()
	//	{
	//		// ////////////////////////
	//		// START OF ADDITIONAL FIELDS TAB ///
	//		// ////////////////////////
	//		wAdditionalFieldsTab = new CTabItem(wTabFolder, SWT.NONE);
	//		wAdditionalFieldsTab.setText(BaseMessages.getString(PKG, "TextFileInputDialog.AdditionalFieldsTab.TabTitle"));
	//
	//		wAdditionalFieldsComp = new Composite(wTabFolder, SWT.NONE);
	//		props.setLook(wAdditionalFieldsComp);
	//
	//		FormLayout fieldsLayout = new FormLayout();
	//		fieldsLayout.marginWidth = 3;
	//		fieldsLayout.marginHeight = 3;
	//		wAdditionalFieldsComp.setLayout(fieldsLayout);
	//
	//		//###
	//	}

	/**
	 * Copy information from the meta-data input to the dialog fields.
	 */
	public void getData()
	{
		this.wStepname.setText(this.stepname);
		this.wFilename.setText(Const.NVL(this.inputMeta.getFilename(), ""));
		this.wAddResult.setSelection(this.inputMeta.isAddResultFile());
		this.wRowsToSkip.setText(Const.NVL(this.inputMeta.getNrRowsToSkip(), "0"));
		this.wLimit.setText(Const.NVL(this.inputMeta.getRowLimit(), "0"));
		this.wDefaultStringLen.setText(Const.NVL(this.inputMeta.getDefaultStringLen(), XMLInputStreamMeta.DEFAULT_STRING_LEN));
		this.wEncoding.setText(Const.NVL(this.inputMeta.getEncoding(), XMLInputStreamMeta.DEFAULT_ENCODING));
		this.wEnableNamespaces.setSelection(this.inputMeta.isEnableNamespaces());
		this.wEnableTrim.setSelection(this.inputMeta.isEnableTrim());

		this.wIncludeFilename.setSelection(this.inputMeta.isIncludeFilenameField());
		this.wFilenameField.setText(Const.NVL(this.inputMeta.getFilenameField(), ""));

		this.wIncludeRowNumber.setSelection(this.inputMeta.isIncludeRowNumberField());
		this.wRowNumberField.setText(Const.NVL(this.inputMeta.getRowNumberField(), ""));

		this.wIncludeXmlDataTypeNumeric.setSelection(this.inputMeta.isIncludeXmlDataTypeNumericField());
		this.wXmlDataTypeNumericField.setText(Const.NVL(this.inputMeta.getXmlDataTypeNumericField(), ""));

		this.wIncludeXmlDataTypeDescription.setSelection(this.inputMeta.isIncludeXmlDataTypeDescriptionField());
		this.wXmlDataTypeDescriptionField.setText(Const.NVL(this.inputMeta.getXmlDataTypeDescriptionField(), ""));

		this.wIncludeXmlLocationLine.setSelection(this.inputMeta.isIncludeXmlLocationLineField());
		this.wXmlLocationLineField.setText(Const.NVL(this.inputMeta.getXmlLocationLineField(), ""));

		this.wIncludeXmlLocationColumn.setSelection(this.inputMeta.isIncludeXmlLocationColumnField());
		this.wXmlLocationColumnField.setText(Const.NVL(this.inputMeta.getXmlLocationColumnField(), ""));

		this.wIncludeXmlElementID.setSelection(this.inputMeta.isIncludeXmlElementIDField());
		this.wXmlElementIDField.setText(Const.NVL(this.inputMeta.getXmlElementIDField(), ""));

		this.wIncludeXmlParentElementID.setSelection(this.inputMeta.isIncludeXmlParentElementIDField());
		this.wXmlParentElementIDField.setText(Const.NVL(this.inputMeta.getXmlParentElementIDField(), ""));

		this.wIncludeXmlBlockID.setSelection(this.inputMeta.isIncludeXmlBlockIDField());
		this.wXmlBlockIDField.setText(Const.NVL(this.inputMeta.getXmlBlockIDField(), ""));
		this.wXmlBlockTagField.setText(Const.NVL(this.inputMeta.getXmlBlockTag(), ""));

		this.wIncludeXmlElementLevel.setSelection(this.inputMeta.isIncludeXmlElementLevelField());
		this.wXmlElementLevelField.setText(Const.NVL(this.inputMeta.getXmlElementLevelField(), ""));

		this.wIncludeXmlPath.setSelection(this.inputMeta.isIncludeXmlPathField());
		this.wXmlPathField.setText(Const.NVL(this.inputMeta.getXmlPathField(), ""));

		this.wIncludeXmlParentPath.setSelection(this.inputMeta.isIncludeXmlParentPathField());
		this.wXmlParentPathField.setText(Const.NVL(this.inputMeta.getXmlParentPathField(), ""));

		this.wIncludeXmlDataName.setSelection(this.inputMeta.isIncludeXmlDataNameField());
		this.wXmlDataNameField.setText(Const.NVL(this.inputMeta.getXmlDataNameField(), ""));

		this.wIncludeXmlDataValue.setSelection(this.inputMeta.isIncludeXmlDataValueField());
		this.wXmlDataValueField.setText(Const.NVL(this.inputMeta.getXmlDataValueField(), ""));

		this.wStepname.selectAll();
	}

	private void cancel()
	{
		this.stepname = null;
		this.inputMeta.setChanged(this.backupChanged);
		this.dispose();
	}

	private void ok()
	{
		if (Const.isEmpty(this.wStepname.getText())) {
			return;
		}

		this.stepname = this.wStepname.getText(); // return value

		this.getInfo(this.inputMeta);

		this.dispose();
	}

	private void getInfo(XMLInputStreamMeta xmlInputMeta)
	{

		xmlInputMeta.setFilename(this.wFilename.getText());
		xmlInputMeta.setAddResultFile(this.wAddResult.getSelection());
		xmlInputMeta.setNrRowsToSkip(Const.NVL(this.wRowsToSkip.getText(), "0"));
		xmlInputMeta.setRowLimit(Const.NVL(this.wLimit.getText(), "0"));
		xmlInputMeta.setDefaultStringLen(Const.NVL(this.wDefaultStringLen.getText(), XMLInputStreamMeta.DEFAULT_STRING_LEN));
		xmlInputMeta.setEncoding(Const.NVL(this.wEncoding.getText(), XMLInputStreamMeta.DEFAULT_ENCODING));
		xmlInputMeta.setEnableNamespaces(this.wEnableNamespaces.getSelection());
		xmlInputMeta.setEnableTrim(this.wEnableTrim.getSelection());

		xmlInputMeta.setIncludeFilenameField(this.wIncludeFilename.getSelection());
		xmlInputMeta.setFilenameField(this.wFilenameField.getText());

		xmlInputMeta.setIncludeRowNumberField(this.wIncludeRowNumber.getSelection());
		xmlInputMeta.setRowNumberField(this.wRowNumberField.getText());

		xmlInputMeta.setIncludeXmlDataTypeNumericField(this.wIncludeXmlDataTypeNumeric.getSelection());
		xmlInputMeta.setXmlDataTypeNumericField(this.wXmlDataTypeNumericField.getText());

		xmlInputMeta.setIncludeXmlDataTypeDescriptionField(this.wIncludeXmlDataTypeDescription.getSelection());
		xmlInputMeta.setXmlDataTypeDescriptionField(this.wXmlDataTypeDescriptionField.getText());

		xmlInputMeta.setIncludeXmlLocationLineField(this.wIncludeXmlLocationLine.getSelection());
		xmlInputMeta.setXmlLocationLineField(this.wXmlLocationLineField.getText());

		xmlInputMeta.setIncludeXmlLocationColumnField(this.wIncludeXmlLocationColumn.getSelection());
		xmlInputMeta.setXmlLocationColumnField(this.wXmlLocationColumnField.getText());

		xmlInputMeta.setIncludeXmlElementIDField(this.wIncludeXmlElementID.getSelection());
		xmlInputMeta.setXmlElementIDField(this.wXmlElementIDField.getText());

		xmlInputMeta.setIncludeXmlParentElementIDField(this.wIncludeXmlParentElementID.getSelection());
		xmlInputMeta.setXmlParentElementIDField(this.wXmlParentElementIDField.getText());

		xmlInputMeta.setIncludeXmlBlockIDField(this.wIncludeXmlBlockID.getSelection());
		xmlInputMeta.setXmlBlockIDField(this.wXmlBlockIDField.getText());
		xmlInputMeta.setXmlBlockTag(this.wXmlBlockTagField.getText());

		xmlInputMeta.setIncludeXmlElementLevelField(this.wIncludeXmlElementLevel.getSelection());
		xmlInputMeta.setXmlElementLevelField(this.wXmlElementLevelField.getText());

		xmlInputMeta.setIncludeXmlPathField(this.wIncludeXmlPath.getSelection());
		xmlInputMeta.setXmlPathField(this.wXmlPathField.getText());

		xmlInputMeta.setIncludeXmlParentPathField(this.wIncludeXmlParentPath.getSelection());
		xmlInputMeta.setXmlParentPathField(this.wXmlParentPathField.getText());

		xmlInputMeta.setIncludeXmlDataNameField(this.wIncludeXmlDataName.getSelection());
		xmlInputMeta.setXmlDataNameField(this.wXmlDataNameField.getText());

		xmlInputMeta.setIncludeXmlDataValueField(this.wIncludeXmlDataValue.getSelection());
		xmlInputMeta.setXmlDataValueField(this.wXmlDataValueField.getText());

		xmlInputMeta.setChanged();
	}

	// Preview the data
	private void preview()
	{
		// execute a complete preview transformation in the background.
		// This is how we do it...
		//
		XMLInputStreamMeta oneMeta = new XMLInputStreamMeta();
		this.getInfo(oneMeta);

		TransMeta previewMeta = TransPreviewFactory.generatePreviewTransformation(this.transMeta, oneMeta, this.wStepname.getText());

		EnterNumberDialog numberDialog = new EnterNumberDialog(this.shell, this.props.getDefaultPreviewSize(), BaseMessages.getString(PKG, "System.Dialog..PreviewSize.DialogTitle"), BaseMessages.getString(PKG, "System.Dialog..PreviewSize.DialogMessage"));
		int previewSize = numberDialog.open();
		if (previewSize > 0) {
			TransPreviewProgressDialog progressDialog = new TransPreviewProgressDialog(this.shell, previewMeta, new String[] { this.wStepname.getText() }, new int[] { previewSize });
			progressDialog.open();

			Trans trans = progressDialog.getTrans();
			String loggingText = progressDialog.getLoggingText();

			if (!progressDialog.isCancelled()) {
				if (trans.getResult() != null && trans.getResult().getNrErrors() > 0) {
					EnterTextDialog etd = new EnterTextDialog(this.shell, BaseMessages.getString(PKG, "System.Dialog.PreviewError.Title"), BaseMessages.getString(PKG, "System.Dialog.PreviewError.Message"), loggingText, true);
					etd.setReadOnly();
					etd.open();
				}
			}

			PreviewRowsDialog prd = new PreviewRowsDialog(this.shell, this.transMeta, SWT.NONE, this.wStepname.getText(), progressDialog.getPreviewRowsMeta(this.wStepname.getText()), progressDialog.getPreviewRows(this.wStepname.getText()), loggingText);
			prd.open();
		}
	}

}
