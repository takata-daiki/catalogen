/********************************************************************************
** Form generated from reading ui file 'SettingsWindow.jui'
**
** Created: Sun Oct 17 17:14:41 2010
**      by: Qt User Interface Compiler version 4.5.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package org.openworship.gui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_SettingsWindow implements com.trolltech.qt.QUiForm<QDialog>
{
    public QVBoxLayout verticalLayout;
    public QTabWidget mainTab;
    public QWidget appearance;
    public QVBoxLayout verticalLayout_2;
    public QGroupBox fontGroup;
    public QGridLayout gridLayout;
    public QLabel fontFaceLabel;
    public QPushButton fontChooser;
    public QLineEdit fontExample;
    public QLabel colorLabel;
    public QLineEdit colorExample;
    public QPushButton colorChooser;
    public QGroupBox backgroundGroup;
    public QGridLayout gridLayout_4;
    public QLabel backgroundColorLabel;
    public QLineEdit backgroundColorExample;
    public QPushButton backgroundColorChooser;
    public QLabel backgroundImageLabel;
    public QLineEdit backgroundImagePath;
    public QPushButton backgroundImageChooser;
    public QHBoxLayout horizontalLayout;
    public QGroupBox graphicsGroup;
    public QVBoxLayout verticalLayout_6;
    public QCheckBox useOpenGLCheck;
    public QCheckBox useTransCheck;
    public QGroupBox transGroup;
    public QGridLayout gridLayout_2;
    public QDoubleSpinBox speedSpin;
    public QSpinBox qualitySpin;
    public QLabel speedLabel;
    public QLabel qualityLabel;
    public QSlider speedSlider;
    public QSlider qualitySlider;
    public QWidget database;
    public QVBoxLayout verticalLayout_3;
    public QGroupBox locationBox;
    public QGridLayout gridLayout_3;
    public QLineEdit locationLineEdit;
    public QPushButton locationOpenButton;
    public QLabel locationLabel;
    public QPushButton locationNewButton;
    public QGroupBox resetBox;
    public QVBoxLayout verticalLayout_4;
    public QLabel resetLabel;
    public QLabel resetWarningLabel;
    public QPushButton resetButton;
    public QWidget interfaceTab;
    public QVBoxLayout verticalLayout_5;
    public QGroupBox languageBox;
    public QGridLayout gridLayout_5;
    public QLabel currentLanguageLabel;
    public QLabel currentLanguage;
    public QLabel chooseLanguageLabel;
    public QComboBox languageChooser;
    public QSpacerItem verticalSpacer;
    public QPushButton applyButton;
    public QDialogButtonBox buttonBox;

    public Ui_SettingsWindow() { super(); }

    public void setupUi(QDialog SettingsWindow)
    {
        SettingsWindow.setObjectName("SettingsWindow");
        SettingsWindow.resize(new QSize(496, 470).expandedTo(SettingsWindow.minimumSizeHint()));
        SettingsWindow.setSizeGripEnabled(true);
        SettingsWindow.setModal(false);
        verticalLayout = new QVBoxLayout(SettingsWindow);
        verticalLayout.setObjectName("verticalLayout");
        mainTab = new QTabWidget(SettingsWindow);
        mainTab.setObjectName("mainTab");
        mainTab.setTabPosition(com.trolltech.qt.gui.QTabWidget.TabPosition.North);
        mainTab.setTabShape(com.trolltech.qt.gui.QTabWidget.TabShape.Rounded);
        mainTab.setElideMode(com.trolltech.qt.core.Qt.TextElideMode.ElideLeft);
        mainTab.setDocumentMode(false);
        mainTab.setTabsClosable(false);
        appearance = new QWidget();
        appearance.setObjectName("appearance");
        verticalLayout_2 = new QVBoxLayout(appearance);
        verticalLayout_2.setObjectName("verticalLayout_2");
        fontGroup = new QGroupBox(appearance);
        fontGroup.setObjectName("fontGroup");
        gridLayout = new QGridLayout(fontGroup);
        gridLayout.setObjectName("gridLayout");
        fontFaceLabel = new QLabel(fontGroup);
        fontFaceLabel.setObjectName("fontFaceLabel");

        gridLayout.addWidget(fontFaceLabel, 0, 0, 1, 1);

        fontChooser = new QPushButton(fontGroup);
        fontChooser.setObjectName("fontChooser");

        gridLayout.addWidget(fontChooser, 0, 2, 1, 1);

        fontExample = new QLineEdit(fontGroup);
        fontExample.setObjectName("fontExample");

        gridLayout.addWidget(fontExample, 0, 1, 1, 1);

        colorLabel = new QLabel(fontGroup);
        colorLabel.setObjectName("colorLabel");

        gridLayout.addWidget(colorLabel, 2, 0, 1, 1);

        colorExample = new QLineEdit(fontGroup);
        colorExample.setObjectName("colorExample");

        gridLayout.addWidget(colorExample, 2, 1, 1, 1);

        colorChooser = new QPushButton(fontGroup);
        colorChooser.setObjectName("colorChooser");

        gridLayout.addWidget(colorChooser, 2, 2, 1, 1);


        verticalLayout_2.addWidget(fontGroup);

        backgroundGroup = new QGroupBox(appearance);
        backgroundGroup.setObjectName("backgroundGroup");
        gridLayout_4 = new QGridLayout(backgroundGroup);
        gridLayout_4.setObjectName("gridLayout_4");
        backgroundColorLabel = new QLabel(backgroundGroup);
        backgroundColorLabel.setObjectName("backgroundColorLabel");

        gridLayout_4.addWidget(backgroundColorLabel, 0, 0, 1, 1);

        backgroundColorExample = new QLineEdit(backgroundGroup);
        backgroundColorExample.setObjectName("backgroundColorExample");

        gridLayout_4.addWidget(backgroundColorExample, 0, 1, 1, 1);

        backgroundColorChooser = new QPushButton(backgroundGroup);
        backgroundColorChooser.setObjectName("backgroundColorChooser");

        gridLayout_4.addWidget(backgroundColorChooser, 0, 2, 1, 1);

        backgroundImageLabel = new QLabel(backgroundGroup);
        backgroundImageLabel.setObjectName("backgroundImageLabel");

        gridLayout_4.addWidget(backgroundImageLabel, 1, 0, 1, 1);

        backgroundImagePath = new QLineEdit(backgroundGroup);
        backgroundImagePath.setObjectName("backgroundImagePath");

        gridLayout_4.addWidget(backgroundImagePath, 1, 1, 1, 1);

        backgroundImageChooser = new QPushButton(backgroundGroup);
        backgroundImageChooser.setObjectName("backgroundImageChooser");

        gridLayout_4.addWidget(backgroundImageChooser, 1, 2, 1, 1);


        verticalLayout_2.addWidget(backgroundGroup);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        graphicsGroup = new QGroupBox(appearance);
        graphicsGroup.setObjectName("graphicsGroup");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(graphicsGroup.sizePolicy().hasHeightForWidth());
        graphicsGroup.setSizePolicy(sizePolicy);
        verticalLayout_6 = new QVBoxLayout(graphicsGroup);
        verticalLayout_6.setObjectName("verticalLayout_6");
        useOpenGLCheck = new QCheckBox(graphicsGroup);
        useOpenGLCheck.setObjectName("useOpenGLCheck");

        verticalLayout_6.addWidget(useOpenGLCheck);

        useTransCheck = new QCheckBox(graphicsGroup);
        useTransCheck.setObjectName("useTransCheck");

        verticalLayout_6.addWidget(useTransCheck);


        horizontalLayout.addWidget(graphicsGroup);

        transGroup = new QGroupBox(appearance);
        transGroup.setObjectName("transGroup");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(transGroup.sizePolicy().hasHeightForWidth());
        transGroup.setSizePolicy(sizePolicy1);
        gridLayout_2 = new QGridLayout(transGroup);
        gridLayout_2.setObjectName("gridLayout_2");
        speedSpin = new QDoubleSpinBox(transGroup);
        speedSpin.setObjectName("speedSpin");

        gridLayout_2.addWidget(speedSpin, 0, 2, 1, 1);

        qualitySpin = new QSpinBox(transGroup);
        qualitySpin.setObjectName("qualitySpin");

        gridLayout_2.addWidget(qualitySpin, 1, 2, 1, 1);

        speedLabel = new QLabel(transGroup);
        speedLabel.setObjectName("speedLabel");

        gridLayout_2.addWidget(speedLabel, 0, 0, 1, 1);

        qualityLabel = new QLabel(transGroup);
        qualityLabel.setObjectName("qualityLabel");

        gridLayout_2.addWidget(qualityLabel, 1, 0, 1, 1);

        speedSlider = new QSlider(transGroup);
        speedSlider.setObjectName("speedSlider");
        speedSlider.setMinimum(5);
        speedSlider.setMaximum(100);
        speedSlider.setSingleStep(1);
        speedSlider.setPageStep(10);
        speedSlider.setValue(10);
        speedSlider.setTracking(true);
        speedSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        gridLayout_2.addWidget(speedSlider, 0, 1, 1, 1);

        qualitySlider = new QSlider(transGroup);
        qualitySlider.setObjectName("qualitySlider");
        qualitySlider.setMinimum(5);
        qualitySlider.setMaximum(120);
        qualitySlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        gridLayout_2.addWidget(qualitySlider, 1, 1, 1, 1);


        horizontalLayout.addWidget(transGroup);


        verticalLayout_2.addLayout(horizontalLayout);

        mainTab.addTab(appearance, com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Appearance", null));
        database = new QWidget();
        database.setObjectName("database");
        verticalLayout_3 = new QVBoxLayout(database);
        verticalLayout_3.setObjectName("verticalLayout_3");
        locationBox = new QGroupBox(database);
        locationBox.setObjectName("locationBox");
        gridLayout_3 = new QGridLayout(locationBox);
        gridLayout_3.setObjectName("gridLayout_3");
        locationLineEdit = new QLineEdit(locationBox);
        locationLineEdit.setObjectName("locationLineEdit");

        gridLayout_3.addWidget(locationLineEdit, 1, 1, 1, 1);

        locationOpenButton = new QPushButton(locationBox);
        locationOpenButton.setObjectName("locationOpenButton");

        gridLayout_3.addWidget(locationOpenButton, 1, 2, 1, 1);

        locationLabel = new QLabel(locationBox);
        locationLabel.setObjectName("locationLabel");

        gridLayout_3.addWidget(locationLabel, 1, 0, 1, 1);

        locationNewButton = new QPushButton(locationBox);
        locationNewButton.setObjectName("locationNewButton");

        gridLayout_3.addWidget(locationNewButton, 1, 3, 1, 1);


        verticalLayout_3.addWidget(locationBox);

        resetBox = new QGroupBox(database);
        resetBox.setObjectName("resetBox");
        verticalLayout_4 = new QVBoxLayout(resetBox);
        verticalLayout_4.setObjectName("verticalLayout_4");
        resetLabel = new QLabel(resetBox);
        resetLabel.setObjectName("resetLabel");
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);
        sizePolicy2.setHorizontalStretch((byte)0);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(resetLabel.sizePolicy().hasHeightForWidth());
        resetLabel.setSizePolicy(sizePolicy2);
        resetLabel.setTextFormat(com.trolltech.qt.core.Qt.TextFormat.AutoText);
        resetLabel.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft,com.trolltech.qt.core.Qt.AlignmentFlag.AlignTop));
        resetLabel.setWordWrap(true);

        verticalLayout_4.addWidget(resetLabel);

        resetWarningLabel = new QLabel(resetBox);
        resetWarningLabel.setObjectName("resetWarningLabel");
        resetWarningLabel.setTextFormat(com.trolltech.qt.core.Qt.TextFormat.RichText);
        resetWarningLabel.setWordWrap(true);

        verticalLayout_4.addWidget(resetWarningLabel);

        resetButton = new QPushButton(resetBox);
        resetButton.setObjectName("resetButton");

        verticalLayout_4.addWidget(resetButton);


        verticalLayout_3.addWidget(resetBox);

        mainTab.addTab(database, com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Database", null));
        interfaceTab = new QWidget();
        interfaceTab.setObjectName("interfaceTab");
        verticalLayout_5 = new QVBoxLayout(interfaceTab);
        verticalLayout_5.setObjectName("verticalLayout_5");
        languageBox = new QGroupBox(interfaceTab);
        languageBox.setObjectName("languageBox");
        gridLayout_5 = new QGridLayout(languageBox);
        gridLayout_5.setObjectName("gridLayout_5");
        currentLanguageLabel = new QLabel(languageBox);
        currentLanguageLabel.setObjectName("currentLanguageLabel");

        gridLayout_5.addWidget(currentLanguageLabel, 0, 0, 1, 1);

        currentLanguage = new QLabel(languageBox);
        currentLanguage.setObjectName("currentLanguage");

        gridLayout_5.addWidget(currentLanguage, 0, 1, 1, 1);

        chooseLanguageLabel = new QLabel(languageBox);
        chooseLanguageLabel.setObjectName("chooseLanguageLabel");

        gridLayout_5.addWidget(chooseLanguageLabel, 1, 0, 1, 1);

        languageChooser = new QComboBox(languageBox);
        languageChooser.setObjectName("languageChooser");

        gridLayout_5.addWidget(languageChooser, 1, 1, 1, 1);

        verticalSpacer = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout_5.addItem(verticalSpacer, 2, 0, 1, 1);

        applyButton = new QPushButton(languageBox);
        applyButton.setObjectName("applyButton");

        gridLayout_5.addWidget(applyButton, 1, 2, 1, 1);


        verticalLayout_5.addWidget(languageBox);

        mainTab.addTab(interfaceTab, com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Interface", null));

        verticalLayout.addWidget(mainTab);

        buttonBox = new QDialogButtonBox(SettingsWindow);
        buttonBox.setObjectName("buttonBox");
        buttonBox.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
        buttonBox.setStandardButtons(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.createQFlags(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Ok));

        verticalLayout.addWidget(buttonBox);

        QWidget.setTabOrder(fontChooser, colorChooser);
        QWidget.setTabOrder(colorChooser, useOpenGLCheck);
        QWidget.setTabOrder(useOpenGLCheck, fontExample);
        QWidget.setTabOrder(fontExample, colorExample);
        QWidget.setTabOrder(colorExample, buttonBox);
        QWidget.setTabOrder(buttonBox, locationOpenButton);
        QWidget.setTabOrder(locationOpenButton, resetButton);
        QWidget.setTabOrder(resetButton, locationLineEdit);
        retranslateUi(SettingsWindow);
        buttonBox.accepted.connect(SettingsWindow, "accept()");
        buttonBox.rejected.connect(SettingsWindow, "reject()");
        qualitySlider.valueChanged.connect(qualitySpin, "setValue(int)");
        qualitySpin.valueChanged.connect(qualitySlider, "setValue(int)");
        useTransCheck.toggled.connect(transGroup, "setVisible(boolean)");

        mainTab.setCurrentIndex(0);


        SettingsWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog SettingsWindow)
    {
        SettingsWindow.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Settings", null));
        fontGroup.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Fonts", null));
        fontFaceLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Font face", null));
        fontChooser.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Choose font", null));
        colorLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Text color", null));
        colorChooser.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Choose color", null));
        backgroundGroup.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Background", null));
        backgroundColorLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Background color", null));
        backgroundColorChooser.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Choose color", null));
        backgroundImageLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Background image", null));
        backgroundImageChooser.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Choose image", null));
        graphicsGroup.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Graphics", null));
        useOpenGLCheck.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Use OpenGL", null));
        useTransCheck.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Use slide transitions", null));
        transGroup.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Slide transitions", null));
        speedLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Speed (seconds)", null));
        qualityLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Quality (FPS)", null));
        mainTab.setTabText(mainTab.indexOf(appearance), com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Appearance", null));
        locationBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Database location", null));
        locationOpenButton.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "&Open", null));
        locationLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Path to database file:", null));
        locationNewButton.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "&New", null));
        resetBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Reset OpenWorship", null));
        resetLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "If everything just breaks down, and the database goes to pieces, or if you just wanted to fool around and test stuff, it may be nice to just reset everything.", null));
        resetWarningLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\" \"http://www.w3.org/TR/REC-html40/strict.dtd\">\n"+
"<html><head><meta name=\"qrichtext\" content=\"1\" /><style type=\"text/css\">\n"+
"p, li { white-space: pre-wrap; }\n"+
"</style></head><body style=\" font-family:'Sans Serif'; font-size:9pt; font-weight:400; font-style:normal;\">\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><span style=\" font-size:12pt; font-weight:600; color:#ff0000;\">WARNING: This will erase everything in the database, and replace it with the defaults!! This can not be undone!</span></p></body></html>", null));
        resetButton.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Reset application", null));
        mainTab.setTabText(mainTab.indexOf(database), com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Database", null));
        languageBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Language", null));
        currentLanguageLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Current UI language:", null));
        currentLanguage.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "English", null));
        chooseLanguageLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Choose UI language:", null));
        applyButton.setText(com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Apply", null));
        mainTab.setTabText(mainTab.indexOf(interfaceTab), com.trolltech.qt.core.QCoreApplication.translate("SettingsWindow", "Interface", null));
    } // retranslateUi

}

