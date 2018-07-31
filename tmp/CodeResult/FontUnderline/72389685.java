package open.dolphin.client;

import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.*;
import open.dolphin.helper.MenuSupport;
import open.dolphin.util.ModifiedFlowLayout;

/**
 * Menu Factory for Mac. 
 * 
 * @author Minagawa, Kazushi
 */
public class WindowsMenuFactory extends AbstractMenuFactory {
        
    private MenuSupport main;
    
    private MenuSupport chart;
    
    private JMenuBar menuBar;
    
    private JPanel toolPanel;
    
    private ActionMap actionMap;
    
    
    /** Creates a new instance of ApplicationMenu */
    public WindowsMenuFactory() {
    }
    
    @Override
    public void setMenuSupports(MenuSupport main, MenuSupport chart) {
        this.main = main;
        this.chart = chart;
    }
    
    @Override
    public JMenuBar getMenuBarProduct() {
        return menuBar;
    }
    
    @Override
    public JPanel getToolPanelProduct() {
        return toolPanel;
    }
    
    @Override
    public ActionMap getActionMap() {
        return actionMap;
    }
    
    private void storeActions(ActionMap map, ResourceBundle resource) {

        // New Karte
        String text = resource.getString("newKarte.Action.text");
        ImageIcon icon = ClientContext.getImageIconAlias("icon_new_karte");
        AbstractAction newKarte = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("newKarte");
            }
        };
        map.put("newKarte", newKarte);

        // New Document
        text = resource.getString("newDocument.Action.text");
        icon = ClientContext.getImageIconAlias("icon_new_document");
        AbstractAction newDocument = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("newDocument");
            }
        };
        map.put("newDocument", newDocument);

        // Open Karte
        text = resource.getString("openKarte.Action.text");
        AbstractAction openKarte = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("openKarte");
            }
        };
        map.put("openKarte", openKarte);

        // Close
        text = resource.getString("close.Action.text");
        AbstractAction close = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("close");
            }
        };
        map.put("close", close);

        // Save
        text = resource.getString("save.Action.text");
        icon = ClientContext.getImageIconAlias("icon_save");
        AbstractAction save = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("save");
            }
        };
        map.put("save", save);

        // Delete
        text = resource.getString("delete.Action.text");
        AbstractAction delete = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("delete");
            }
        };
        map.put("delete", delete);

        // Printer Setup
        text = resource.getString("printerSetup.Action.text");
        AbstractAction printerSetup = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("printerSetup");
            }
        };
        map.put("printerSetup", printerSetup);

        // Print
        text = resource.getString("print.Action.text");
        icon = ClientContext.getImageIconAlias("icon_printer");
        AbstractAction print = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("print");
            }
        };
        map.put("print", print);

        // Exit
        text = resource.getString("processExit.Action.text");
        AbstractAction processExit = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("processExit");
            }
        };
        map.put("processExit", processExit);

        // Modify
        text = resource.getString("modifyKarte.Action.text");
        icon = ClientContext.getImageIconAlias("icon_edit_karte_document");
        AbstractAction modifyKarte = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("modifyKarte");
            }
        };
        map.put("modifyKarte", modifyKarte);

        // Undo
        text = resource.getString("undo.Action.text");
        icon = ClientContext.getImageIconAlias("icon_undo");
        AbstractAction undo = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("undo");
            }
        };
        map.put("undo", undo);

        // Redo
        text = resource.getString("redo.Action.text");
        icon = ClientContext.getImageIconAlias("icon_redo");
        AbstractAction redo = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("redo");
            }
        };
        map.put("redo", redo);

        // Cut
        text = resource.getString("cut.Action.text");
        icon = ClientContext.getImageIconAlias("icon_cut");
        AbstractAction cut = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.cut();
            }
        };
        map.put("cut", cut);

        // Copy
        text = resource.getString("copy.Action.text");
        icon = ClientContext.getImageIconAlias("icon_copy");
        AbstractAction copy = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.copy();
            }
        };
        map.put("copy", copy);

        // Paste
        text = resource.getString("paste.Action.text");
        icon = ClientContext.getImageIconAlias("icon_paste");
        AbstractAction paste = new AbstractAction(text, icon) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.paste();
            }
        };
        map.put("paste", paste);

        // 処方日数一括変更
        text = resource.getString("changeNumOfDatesAll.Action.text");
        AbstractAction changeNumOfDatesAll = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("changeNumOfDatesAll");
            }
        };
        map.put("changeNumOfDatesAll", changeNumOfDatesAll);

        // Send Claim 元町皮ふ科
        text = resource.getString("sendClaim.Action.text");
        AbstractAction sendClaim = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("sendClaim");
            }
        };
        map.put("sendClaim", sendClaim);

        // 保険選択
        AbstractAction selectInsurance = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("selectInsurance", selectInsurance);

        // 昇順
        text = resource.getString("ascending.Action.text");
        AbstractAction ascending = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("ascending");
            }
        };
        map.put("ascending", ascending);

        // 降順
        text = resource.getString("descending.Action.text");
        AbstractAction descending = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("descending");
            }
        };
        map.put("descending", descending);

        // 修正履歴表示
        text = resource.getString("showModified.Action.text");
        AbstractAction showModified = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("showModified");
            }
        };
        map.put("showModified", showModified);

        // 環境設定
        text = resource.getString("setKarteEnviroment.Action.text");
        AbstractAction setKarteEnviroment = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("setKarteEnviroment");
            }
        };
        map.put("setKarteEnviroment", setKarteEnviroment);

        // 挿入　病名
        text = resource.getString("insertDisease.Action.text");
        AbstractAction insertDisease = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("insertDisease", insertDisease);

        // 挿入　テキスト
        text = resource.getString("insertText.Action.text");
        AbstractAction insertText = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("insertText", insertText);

        // 挿入　シェーマ
        text = resource.getString("insertSchema.Action.text");
        AbstractAction insertSchema = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("insertSchema", insertSchema);

        // 挿入　スタンプ
        text = resource.getString("insertStamp.Action.text");
        AbstractAction insertStamp = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("insertStamp", insertStamp);

        // Size
        text = resource.getString("size.text");
        AbstractAction size = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("size", size);

        // 大きく
        text = resource.getString("fontLarger.Action.text");
        AbstractAction fontLarger = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontLarger");
            }
        };
        map.put("fontLarger", fontLarger);

        // 小さく
        text = resource.getString("fontSmaller.Action.text");
        AbstractAction fontSmaller = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontSmaller");
            }
        };
        map.put("fontSmaller", fontSmaller);

        // 標準
        text = resource.getString("fontStandard.Action.text");
        AbstractAction fontStandard = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontStandard");
            }
        };
        map.put("fontStandard", fontStandard);

        // スタイル
        text = resource.getString("style.text");
        AbstractAction style = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("style", style);

        // ボールド
        text = resource.getString("fontBold.Action.text");
        AbstractAction fontBold = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontBold");
            }
        };
        map.put("fontBold", fontBold);

        // イタリック
        text = resource.getString("fontItalic.Action.text");
        AbstractAction fontItalic = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontItalic");
            }
        };
        map.put("fontItalic", fontItalic);

        // 下線
        text = resource.getString("fontUnderline.Action.text");
        AbstractAction fontUnderline = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontUnderline");
            }
        };
        map.put("fontUnderline", fontUnderline);

        // Justify
        text = resource.getString("justify.text");
        AbstractAction justify = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("justify", justify);

        // 左揃え
        text = resource.getString("leftJustify.Action.text");
        AbstractAction leftJustify = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("leftJustify");
            }
        };
        map.put("leftJustify", leftJustify);

        // 中央
        text = resource.getString("centerJustify.Action.text");
        AbstractAction centerJustify = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("centerJustify");
            }
        };
        map.put("centerJustify", centerJustify);

        // 右よせ
        text = resource.getString("rightJustify.Action.text");
        AbstractAction rightJustify = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("rightJustify");
            }
        };
        map.put("rightJustify", rightJustify);

        // カラー
        text = resource.getString("color.text");
        AbstractAction color = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        };
        map.put("color", color);

        // 赤
        text = resource.getString("fontRed.Action.text");
        AbstractAction fontRed = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontRed");
            }
        };
        map.put("fontRed", fontRed);

        // オレンジ
        text = resource.getString("fontOrange.Action.text");
        AbstractAction fontOrange = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontOrange");
            }
        };
        map.put("fontOrange", fontOrange);

        // 黄色
        text = resource.getString("fontYellow.Action.text");
        AbstractAction fontYellow = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontYellow");
            }
        };
        map.put("fontYellow", fontYellow);

        // 緑
        text = resource.getString("fontGreen.Action.text");
        AbstractAction fontGreen = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontGreen");
            }
        };
        map.put("fontGreen", fontGreen);

        // 青
        text = resource.getString("fontBlue.Action.text");
        AbstractAction fontBlue = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontBlue");
            }
        };
        map.put("fontBlue", fontBlue);

        // 紫
        text = resource.getString("fontPurple.Action.text");
        AbstractAction fontPurple = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontPurple");
            }
        };
        map.put("fontPurple", fontPurple);

        // グレー
        text = resource.getString("fontGray.Action.text");
        AbstractAction fontGray = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontGray");
            }
        };
        map.put("fontGray", fontGray);

        // ブラック
        text = resource.getString("fontBlack.Action.text");
        AbstractAction fontBlack = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("fontBlack");
            }
        };
        map.put("fontBlack", fontBlack);

        // リセット　未使用？
        AbstractAction resetStyle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("resetStyle");
            }
        };
        map.put("resetStyle", resetStyle);

        // StampBox
        text = resource.getString("showStampBox.Action.text");
        AbstractAction showStampBox = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("showStampBox");
            }
        };
        map.put("showStampBox", showStampBox);

        // シェーマBox
        text = resource.getString("showSchemaBox.Action.text");
        AbstractAction showSchemaBox = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("showSchemaBox");
            }
        };
        map.put("showSchemaBox", showSchemaBox);

        // パスワード変更
        text = resource.getString("changePassword.Action.text");
        AbstractAction changePassword = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("changePassword");
            }
        };
        map.put("changePassword", changePassword);

        // ユーザー追加
        text = resource.getString("addUser.Action.text");
        AbstractAction addUser = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("addUser");
            }
        };
        map.put("addUser", addUser);

        // Support
        text = resource.getString("browseDolphinSupport.Action.text");
        AbstractAction browseDolphinSupport = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("browseDolphinSupport");
            }
        };
        map.put("browseDolphinSupport", browseDolphinSupport);

        // Dolphin
        text = resource.getString("browseDolphinProject.Action.text");
        AbstractAction browseDolphinProject = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("browseDolphinProject");
            }
        };
        map.put("browseDolphinProject", browseDolphinProject);

        // MedXML
        text = resource.getString("browseMedXml.Action.text");
        AbstractAction browseMedXml = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("browseMedXml");
            }
        };
        map.put("browseMedXml", browseMedXml);

        // About
        text = resource.getString("showAbout.Action.text");
        AbstractAction showAbout = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("showAbout");
            }
        };
        map.put("showAbout", showAbout);
        
//masuda^
        // Broadcast
        text = "一斉通知";
        AbstractAction broadcastMsg = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("broadcastMsg");
            }
        };
        map.put("broadcastMsg", broadcastMsg);
        // GitHub
        text = resource.getString("browseGitHub.Action.text");
        AbstractAction browseGitHub = new AbstractAction(text) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("browseGitHub");
            }
        };
        map.put("browseGitHub", browseGitHub);
        
        text = resource.getString("findFirst.Action.text");
        AbstractAction findFirst = new AbstractAction(text) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("findFirst");
            }
        };
        map.put("findFirst", findFirst);

        text = resource.getString("findNext.Action.text");
        AbstractAction findNext = new AbstractAction(text) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("findNext");
            }
        };
        map.put("findNext", findNext);

        text = resource.getString("findPrevious.Action.text");
        AbstractAction findPrevious = new AbstractAction(text) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("findPrevious");
            }
        };
        map.put("findPrevious", findPrevious);

        text = resource.getString("selectAll.Action.text");
        AbstractAction selectAll = new AbstractAction(text) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                chart.sendToChain("selectAll");
            }
        };
        map.put("selectAll", selectAll);

        text = resource.getString("editDisconItem.Action.text");
        AbstractAction editDisconItem = new AbstractAction(text) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("editDisconItem");
            }
        };
        map.put("editDisconItem", editDisconItem);

        text = resource.getString("editUsingDrug.Action.text");
        AbstractAction editUsingDrug = new AbstractAction(text) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("editUsingDrug");
            }
        };
        map.put("editUsingDrug", editUsingDrug);
        
        text = resource.getString("checkTempKarte.Action.text");
        AbstractAction checkTempKarte = new AbstractAction(text) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("checkTempKarte");
            }
        };
        map.put("checkTempKarte", checkTempKarte);

        text = resource.getString("showRezeTen.Action.text");
        AbstractAction rezeTen = new AbstractAction(text) {

            @Override
            public void actionPerformed(ActionEvent ae) {
                main.sendToChain("showRezeTen");
            }
        };
        map.put("showRezeTen", rezeTen);
//masuda$
    }
    
    @Override
    public void build(JMenuBar menuBar) {
        
        this.menuBar = menuBar;

        ResourceBundle resource = ClientContext.getBundle(this.getClass());
        actionMap = new ActionMap();
        storeActions(actionMap, resource);
        
        // ToolBar
        JToolBar fileBar = null;
        JToolBar editBar = null;
        if (chart != null) {
            fileBar = new JToolBar();
            fileBar.setName("fileBar");
            editBar = new JToolBar();
            editBar.setName("editBar");
            toolPanel = new JPanel(new ModifiedFlowLayout(FlowLayout.LEFT));
            toolPanel.add(fileBar);
            toolPanel.add(editBar);
        }
        
        // File
        JMenu file = new JMenu();
        file.setName("fileMenu");
        
        // 新規カルテ
        JMenuItem newKarte = new JMenuItem();
        newKarte.setName("newKarte");
        newKarte.setAction(actionMap.get("newKarte"));
        setAccelerator(newKarte, KeyEvent.VK_N);
        file.add(newKarte);
        if (fileBar != null) {
            JButton newKarteBtn = createButton();
            newKarteBtn.setAction(actionMap.get("newKarte"));
            newKarteBtn.setText(null);
            newKarteBtn.setToolTipText("カルテを新規に作成します。");
//masuda
            //newKarteBtn.setMargin(new Insets(5,5,5,5));
            fileBar.add(newKarteBtn);
        }
        
        // 新規文書
        JMenuItem newDocument = new JMenuItem();
        newDocument.setName("newDocument");
        newDocument.setAction(actionMap.get("newDocument"));
        file.add(newDocument);
        if (fileBar != null) {
            JButton newDocBtn = createButton();
            newDocBtn.setAction(actionMap.get("newDocument"));
            newDocBtn.setText(null);
            newDocBtn.setToolTipText("紹介状等の文書を新規に作成します。");
//masuda
            //newDocBtn.setMargin(new Insets(5,5,5,5));
            fileBar.add(newDocBtn);
        }
        
        // 開く
        JMenuItem openKarte = new JMenuItem();
        openKarte.setName("openKarte");
        openKarte.setAction(actionMap.get("openKarte"));
        setAccelerator(openKarte, KeyEvent.VK_O);
        file.add(openKarte);
        
        file.add(new JSeparator());
        
        // 閉じる
        JMenuItem close = new JMenuItem();
        close.setName("close");
        close.setAction(actionMap.get("close"));
        setAccelerator(close, KeyEvent.VK_W);
        file.add(close);
        
        // 保存
        JMenuItem save = new JMenuItem();
        save.setName("save");
        save.setAction(actionMap.get("save"));
        setAccelerator(save, KeyEvent.VK_S);
        file.add(save);
        if (fileBar != null) {
            JButton saveBtn = createButton();
            saveBtn.setAction(actionMap.get("save"));
            saveBtn.setText(null);
            saveBtn.setToolTipText("カルテや文書を保存します。");
//masuda
            //saveBtn.setMargin(new Insets(5,5,5,5));
            fileBar.add(saveBtn);
        }
        
        file.add(new JSeparator());
        
        // 削除
        JMenuItem delete = new JMenuItem();
        delete.setName("delete");
        delete.setAction(actionMap.get("delete"));
        file.add(delete);
        
        file.add(new JSeparator());
        
        // 印刷設定
        JMenuItem printerSetup = new JMenuItem();
        printerSetup.setName("printerSetup");
        printerSetup.setAction(actionMap.get("printerSetup"));
        file.add(printerSetup);
        
        // 印刷
        JMenuItem print = new JMenuItem();
        print.setName("print");
        print.setAction(actionMap.get("print"));
        setAccelerator(print, KeyEvent.VK_P);
        file.add(print);
        if (fileBar != null) {
            JButton printBtn = createButton();
            printBtn.setAction(actionMap.get("print"));
            printBtn.setText(null);
            printBtn.setToolTipText("印刷します。");
//masuda
            //printBtn.setMargin(new Insets(5,5,5,5));
            fileBar.add(printBtn);
        }
        
        file.add(new JSeparator());
        
        // 終了
        JMenuItem exit = new JMenuItem();
        exit.setName("processExit");
        exit.setAction(actionMap.get("processExit"));
        file.add(exit);
        setAccelerator(exit, KeyEvent.VK_Q);
        
        /******************************************************/
        
        // Edit
        JMenu edit = new JMenu();
        edit.setName("editMenu");
        
        // 修正
        JMenuItem modifyKarte = new JMenuItem();
        modifyKarte.setName("modifyKarte");
        modifyKarte.setAction(actionMap.get("modifyKarte"));
        setAccelerator(modifyKarte, KeyEvent.VK_M);
        edit.add(modifyKarte);
        if (editBar != null) {
            JButton modifyKarteBtn = createButton();
            modifyKarteBtn.setAction(actionMap.get("modifyKarte"));
            modifyKarteBtn.setText(null);
            modifyKarteBtn.setToolTipText("カルテや文書を修正します。");
//masuda
            //modifyKarteBtn.setMargin(new Insets(5,5,5,5));
            editBar.add(modifyKarteBtn);
        }
        
        edit.add(new JSeparator());
        
        // Undo
        JMenuItem undo = new JMenuItem();
        undo.setName("undo");
        undo.setAction(actionMap.get("undo"));
        setAccelerator(undo, KeyEvent.VK_Z);
        edit.add(undo);
        if (editBar != null) {
            JButton undoBtn = createButton();
            undoBtn.setAction(actionMap.get("undo"));
            undoBtn.setText(null);
            undoBtn.setToolTipText("操作をやり直します。");
//masuda
            //undoBtn.setMargin(new Insets(5,5,5,5));
            editBar.add(undoBtn);
        }
        
        // Redo
        JMenuItem redo = new JMenuItem();
        redo.setName("redo");
        redo.setAction(actionMap.get("redo"));
        setAccelerator(redo, KeyEvent.VK_Z, true);
        edit.add(redo);
        if (editBar != null) {
            JButton redoBtn = createButton();
            redoBtn.setAction(actionMap.get("redo"));
            redoBtn.setText(null);
            redoBtn.setToolTipText("操作を再実行します。");
//masuda
            //redoBtn.setMargin(new Insets(5,5,5,5));
            editBar.add(redoBtn);
        }
        
        edit.add(new JSeparator());
        
        // Cut
        JMenuItem cut = new JMenuItem();
        cut.setName("cut");
        cut.setAction(actionMap.get("cut"));
        setAccelerator(cut, KeyEvent.VK_X);
        edit.add(cut);
        if (editBar != null) {
            JButton cutBtn = createButton();
            cutBtn.setAction(actionMap.get("cut"));
            cutBtn.setText(null);
            cutBtn.setToolTipText("テキスト、スタンプ、画像をカットします。");
//masuda
            //cutBtn.setMargin(new Insets(5,5,5,5));
            editBar.add(cutBtn);
        }
        
        // Copy
        JMenuItem copy = new JMenuItem();
        copy.setName("copy");
        copy.setAction(actionMap.get("copy"));
        setAccelerator(copy, KeyEvent.VK_C);
        edit.add(copy);
        if (editBar != null) {
            JButton copyBtn = createButton();
            copyBtn.setAction(actionMap.get("copy"));
            copyBtn.setText(null);
            copyBtn.setToolTipText("テキスト、スタンプ、画像をコピーします。");
//masuda
            //copyBtn.setMargin(new Insets(5,5,5,5));
            editBar.add(copyBtn);
        }
        
        // Paste
        JMenuItem paste = new JMenuItem();
        paste.setName("paste");
        paste.setAction(actionMap.get("paste"));
        setAccelerator(paste, KeyEvent.VK_V);
        edit.add(paste);
        if (editBar != null) {
            JButton pasteBtn = createButton();
            pasteBtn.setAction(actionMap.get("paste"));
            pasteBtn.setText(null);
            pasteBtn.setToolTipText("テキスト、スタンプ、画像をペーストします。");
//masuda
            //pasteBtn.setMargin(new Insets(5,5,5,5));
            editBar.add(pasteBtn);
        }
        
//pns^
        // SelectAll
        JMenuItem selectAll = new JMenuItem();
        selectAll.setName("selectAll");
        selectAll.setAction(actionMap.get("selectAll"));
        setAccelerator(selectAll, KeyEvent.VK_A);
        edit.add(selectAll);

        edit.add(new JSeparator());

        // Find
        JMenuItem findFirst = new JMenuItem();
        findFirst.setName("findFirst");
        findFirst.setAction(actionMap.get("findFirst"));
        setAccelerator(findFirst, KeyEvent.VK_F);
        edit.add(findFirst);

        // Find Next
        JMenuItem findNext = new JMenuItem();
        findNext.setName("findNext");
        findNext.setAction(actionMap.get("findNext"));
        setAccelerator(findNext, KeyEvent.VK_G);
        edit.add(findNext);

        // Find Previous
        JMenuItem findPrevious = new JMenuItem();
        findPrevious.setName("findPrevious");
        findPrevious.setAction(actionMap.get("findPrevious"));
        setAccelerator(findPrevious, KeyEvent.VK_G, true);
        edit.add(findPrevious);
//pns$
        
        /******************************************************/
        
        // Karte
        JMenu karte = new JMenu();
        karte.setName("karteMenu");

        //-------------------
        // 処方日数変更
        //-------------------
        JMenuItem changeNumOfDatesAll = new JMenuItem();
        changeNumOfDatesAll.setName("changeNumOfDatesAll");
        changeNumOfDatesAll.setAction(actionMap.get("changeNumOfDatesAll"));
        setAccelerator(changeNumOfDatesAll, KeyEvent.VK_R);
        karte.add(changeNumOfDatesAll);

        //--------------------
        // 元町皮ふ科 CLAIM 送信
        //--------------------
        JMenuItem sendClaim = new JMenuItem();
        sendClaim.setName("sendClaim");
        sendClaim.setAction(actionMap.get("sendClaim"));
        setAccelerator(sendClaim, KeyEvent.VK_L);
        karte.add(sendClaim);
        
        // 昇順
        JRadioButtonMenuItem ascending = new JRadioButtonMenuItem();
        ascending.setName("ascending");
        ascending.setAction(actionMap.get("ascending"));
        actionMap.get("ascending").putValue("menuItem",ascending);
        karte.add(ascending);
        
        // 降順
        JRadioButtonMenuItem descending = new JRadioButtonMenuItem();
        descending.setName("descending");
        descending.setAction(actionMap.get("descending"));
        actionMap.get("descending").putValue("menuItem",descending);
        karte.add(descending);
        
        // RadiButtonGroup
        ButtonGroup bg = new ButtonGroup();
        bg.add(ascending);
        bg.add(descending);
        
        
        // 修正履歴表示 
        JCheckBoxMenuItem showModified = new JCheckBoxMenuItem();
        showModified.setName("showModified");
        showModified.setAction(actionMap.get("showModified"));
        actionMap.get("showModified").putValue("menuItem",showModified);
        karte.add(showModified);
        
        // 環境設定 
        JMenuItem setKarteEnviroment = new JMenuItem();
        setKarteEnviroment.setName("setKarteEnviroment");
        setKarteEnviroment.setAction(actionMap.get("setKarteEnviroment"));
        setAccelerator(setKarteEnviroment, KeyEvent.VK_E);
        karte.add(setKarteEnviroment);
        
//masuda^
        // Look&Feel
        JMenu lookAndFeel = createLafMenu();
        karte.add(lookAndFeel);
//masuda$
        
        /******************************************************/
        
        // Insert
        JMenu insert = new JMenu();
        insert.setName("insertMenu");
        if (chart != null) {
            insert.addMenuListener(chart);
        }
        
        JMenu insertDisease = new JMenu();
        insertDisease.setName("insertDisease");
        insertDisease.setAction(actionMap.get("insertDisease"));
        insert.add(insertDisease);
        
        JMenu insertText = new JMenu();
        insertText.setName("insertText");
        insertText.setAction(actionMap.get("insertText"));
        insert.add(insertText);
        
        JMenu insertSchema = new JMenu();
        insertSchema.setName("insertSchema");
        insertSchema.setAction(actionMap.get("insertSchema"));
        insert.add(insertSchema);
        
        JMenu insertStamp = new JMenu();
        insertStamp.setName("insertStamp");
        insertStamp.setAction(actionMap.get("insertStamp"));
        insert.add(insertStamp);
        
        /******************************************************/
        
        // Text
        JMenu text = new JMenu();
        text.setName("textMenu");
        if (chart != null) {
            text.addMenuListener(chart);
        }
        
        //// size ////
        JMenu size = new JMenu();
        size.setName("size");
        size.setAction(actionMap.get("size"));
        text.add(size);
        
        JMenuItem fontLarger = new JMenuItem();
        fontLarger.setName("fontLarger");
        fontLarger.setAction(actionMap.get("fontLarger"));
        //setAccelerator(fontLarger, KeyEvent.VK_PLUS, true);
        size.add(fontLarger);
        
        JMenuItem fontSmaller = new JMenuItem();
        fontSmaller.setName("fontSmaller");
        fontSmaller.setAction(actionMap.get("fontSmaller"));
        //setAccelerator(fontSmaller, KeyEvent.VK_MINUS);
        size.add(fontSmaller);
        
        JMenuItem fontStandard = new JMenuItem();
        fontStandard.setName("fontStandard");
        fontStandard.setAction(actionMap.get("fontStandard"));
        //setAccelerator(fontStandard, KeyEvent.VK_NUMBER_SIGN, true);
        size.add(fontStandard);  
        
        //// style ////
        JMenu style = new JMenu();
        style.setName("style");
        style.setAction(actionMap.get("style"));
        text.add(style);
        
        JMenuItem fontBold = new JMenuItem();
        fontBold.setName("fontBold");
        fontBold.setAction(actionMap.get("fontBold"));
        setAccelerator(fontBold, KeyEvent.VK_B);
        style.add(fontBold);
        
        JMenuItem fontItalic = new JMenuItem();
        fontItalic.setName("fontItalic");
        fontItalic.setAction(actionMap.get("fontItalic"));
        setAccelerator(fontItalic, KeyEvent.VK_I);
        style.add(fontItalic);
        
        JMenuItem fontUnderline = new JMenuItem();
        fontUnderline.setName("fontUnderline");
        fontUnderline.setAction(actionMap.get("fontUnderline"));
        setAccelerator(fontUnderline, KeyEvent.VK_U);
        style.add(fontUnderline);

        //// justify ////
        JMenu justify = new JMenu();
        justify.setName("justify");
        justify.setAction(actionMap.get("justify"));
        text.add(justify);
        
        JMenuItem leftJustify = new JMenuItem();
        leftJustify.setName("leftJustify");
        leftJustify.setAction(actionMap.get("leftJustify"));
        //setAccelerator(leftJustify, KeyEvent.VK_OPEN_BRACKET);
        justify.add(leftJustify);
        
        JMenuItem centerJustify = new JMenuItem();
        centerJustify.setName("centerJustify");
        centerJustify.setAction(actionMap.get("centerJustify"));
        //setAccelerator(centerJustify, KeyEvent.VK_CIRCUMFLEX);
        justify.add(centerJustify);
        
        JMenuItem rightJustify = new JMenuItem();
        rightJustify.setName("rightJustify");
        rightJustify.setAction(actionMap.get("rightJustify"));
        //setAccelerator(rightJustify, KeyEvent.VK_CLOSE_BRACKET);
        justify.add(rightJustify);
        
        //// Color ////
        JMenu color = new JMenu();
        color.setName("color");
        color.setAction(actionMap.get("color"));
        text.add(color);
        
        JMenuItem fontRed = new JMenuItem();
        fontRed.setName("fontRed");
        fontRed.setAction(actionMap.get("fontRed"));
        color.add(fontRed);
        
        JMenuItem fontOrange = new JMenuItem();
        fontOrange.setName("fontOrange");
        fontOrange.setAction(actionMap.get("fontOrange"));
        color.add(fontOrange);
        
        JMenuItem fontYellow = new JMenuItem();
        fontYellow.setName("fontYellow");
        fontYellow.setAction(actionMap.get("fontYellow"));
        color.add(fontYellow);
        
        JMenuItem fontGreen = new JMenuItem();
        fontGreen.setName("fontGreen");
        fontGreen.setAction(actionMap.get("fontGreen"));
        color.add(fontGreen);
        
        JMenuItem fontBlue = new JMenuItem();
        fontBlue.setName("fontBlue");
        fontBlue.setAction(actionMap.get("fontBlue"));
        color.add(fontBlue);
        
        JMenuItem fontPurple = new JMenuItem();
        fontPurple.setName("fontPurple");
        fontPurple.setAction(actionMap.get("fontPurple"));
        color.add(fontPurple);
        
        JMenuItem fontGray = new JMenuItem();
        fontGray.setName("fontGray");
        fontGray.setAction(actionMap.get("fontGray"));
        color.add(fontGray);
        
        JMenuItem fontBlack = new JMenuItem();
        fontBlack.setName("fontBlack");
        fontBlack.setAction(actionMap.get("fontBlack"));
        color.add(fontBlack);
        
        /******************************************************/
        
        // Tool
        JMenu tool = new JMenu();
        tool.setName("toolMenu");
        
        JMenuItem showStampBox = new JMenuItem();
        showStampBox.setName("showStampBox");
        showStampBox.setAction(actionMap.get("showStampBox"));
        tool.add(showStampBox);
        
        JMenuItem showSchemaBox = new JMenuItem();
        showSchemaBox.setName("showSchemaBox");
        showSchemaBox.setAction(actionMap.get("showSchemaBox"));
        tool.add(showSchemaBox);
        
        tool.add(new JSeparator());
        
//masuda^
        JMenuItem editDisconItem = new JMenuItem();
        editDisconItem.setName("editDisconItem");
        editDisconItem.setAction(actionMap.get("editDisconItem"));
        tool.add(editDisconItem);

        JMenuItem editUsingDrug = new JMenuItem();
        editUsingDrug.setName("editUsingDrug");
        editUsingDrug.setAction(actionMap.get("editUsingDrug"));
        tool.add(editUsingDrug);
        
        JMenuItem checkTempKarte = new JMenuItem();
        checkTempKarte.setName("checkTempKarte");
        checkTempKarte.setAction(actionMap.get("checkTempKarte"));
        tool.add(checkTempKarte);
        
        JMenuItem showRezeTen = new JMenuItem();
        showRezeTen.setName("showRezeTen");
        showRezeTen.setAction(actionMap.get("showRezeTen"));
        tool.add(showRezeTen);

        tool.add(new JSeparator());
//masuda$
        
        JMenuItem changePassword = new JMenuItem();
        changePassword.setName("changePassword");
        changePassword.setAction(actionMap.get("changePassword"));
        tool.add(changePassword);
        
        JMenuItem addUser = new JMenuItem();
        addUser.setName("addUser");
        addUser.setAction(actionMap.get("addUser"));
        tool.add(addUser);
        
        //tool.add(new JSeparator());
        
        LinkedHashMap<String, String> toolProviders = ClientContext.getToolProviders();
        
        if (toolProviders != null && toolProviders.size() > 0) {
            
            tool.add(new JSeparator());
//masuda^
            for (Map.Entry<String, String> entry : toolProviders.entrySet()) {
                String cmd = entry.getKey();
                final String className = entry.getValue();
                JMenuItem mItem = new JMenuItem();
                AbstractAction a = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        main.sendToChain("invokeToolPlugin", className);
                    }
                };
                mItem.setAction(a);
                mItem.setText(cmd);
                tool.add(mItem);
            }
        } 
//masuda$
        
        /******************************************************/
        
        // Help
        JMenu help = new JMenu();
        help.setName("helpMenu");
        
        JMenuItem browseDolphinSupport = new JMenuItem();
        browseDolphinSupport.setName("browseDolphinSupport");
        browseDolphinSupport.setAction(actionMap.get("browseDolphinSupport"));
        help.add(browseDolphinSupport);
        
        JMenuItem browseDolphinProject = new JMenuItem();
        browseDolphinProject.setName("browseDolphinProject");
        browseDolphinProject.setAction(actionMap.get("browseDolphinProject"));
        help.add(browseDolphinProject);
        
        //help.add(new JSeparator());
        
        JMenuItem browseMedXml = new JMenuItem();
        browseMedXml.setName("browseMedXml");
        browseMedXml.setAction(actionMap.get("browseMedXml"));
        help.add(browseMedXml);
        
        JMenuItem brouseGitHub = new JMenuItem();
        browseMedXml.setName("browseGitHub");
        brouseGitHub.setAction(actionMap.get("browseGitHub"));
        help.add(brouseGitHub);
        
        help.add(new JSeparator());
        
        JMenuItem showAbout = new JMenuItem();
        showAbout.setName("showAbout");
        showAbout.setAction(actionMap.get("showAbout"));
        help.add(showAbout);
        
        help.add(new JSeparator());
        
        JMenuItem broadcast = new JMenuItem();
        broadcast.setName("ブロードキャスト");
        broadcast.setAction(actionMap.get("broadcastMsg"));
        help.add(broadcast);
        
        /******************************************************/
        
        menuBar.add(file,   0);
        menuBar.add(edit,   1);
        menuBar.add(karte,  2);
        menuBar.add(insert, 3);
        menuBar.add(text,   4);
        menuBar.add(tool,   5);
        // 6 = Window
        menuBar.add(help,   7);
        
        /******************************************************/
        file.setText(resource.getString("fileMenu.text"));
        edit.setText(resource.getString("editMenu.text"));
        karte.setText(resource.getString("karteMenu.text"));
        insert.setText(resource.getString("insertMenu.text"));
        text.setText(resource.getString("textMenu.text"));
        tool.setText(resource.getString("toolMenu.text"));
        help.setText(resource.getString("helpMenu.text"));
        size.setText(resource.getString("size.text"));
        style.setText(resource.getString("style.text"));
        justify.setText(resource.getString("justify.text"));
        color.setText(resource.getString("color.text"));
    }
    
    private void setAccelerator(JMenuItem item, int key) {
        item.setAccelerator(KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    }
    
    private void setAccelerator(JMenuItem item, int key, boolean shiftMask) {
        item.setAccelerator(
                        KeyStroke.getKeyStroke(key, (java.awt.event.InputEvent.SHIFT_MASK | (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()))));
    }
    
    // quaqua9でボタンボーダーが増えた？
    private JButton createButton() {
        JButton btn = new JButton();
        btn.putClientProperty("Quaqua.Component.visualMargin", new Insets(0, 0, 0, 0));
        return btn;
    }
    
    // LAF menu
    private JMenu createLafMenu() {
        
        JMenu menu = new JMenu("ルック & フィール");
        String currentLaf = UIManager.getLookAndFeel().getClass().getName();
        final ButtonGroup bg = new ButtonGroup();
        
        AbstractAction lafAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonModel bm = bg.getSelection();
                String lafClassName = bm.getActionCommand();
                // Dolphin.changeLookAndFeelを呼ぶ
                main.sendToChain("changeLookAndFeel", lafClassName);
            }
        };
        
        // システム提供のLAF
        for (UIManager.LookAndFeelInfo lafInfo : UIManager.getInstalledLookAndFeels()) {
            String lafName = lafInfo.getName();
            String lafCls = lafInfo.getClassName();
            JRadioButtonMenuItem lafItem = createRadioMenuItem(lafName, lafCls, currentLaf, lafAction, bg);
            menu.add(lafItem);
        }
        
        // 外部LAF
        Map<String, JMenu> subMenuMap = new HashMap<>();
        for (String[] lafInfo : ILookAndFeelConst.EXT_LAF_INFO) {
            String group = lafInfo[0];
            String lafName = lafInfo[1];
            String lafCls = lafInfo[2];
            if (group == null) {
                JRadioButtonMenuItem lafItem = createRadioMenuItem(lafName, lafCls, currentLaf, lafAction, bg);
                menu.add(lafItem);
            } else {
                JMenu subMenu = subMenuMap.get(group);
                if (subMenu == null) {
                    subMenu = new JMenu(group);
                    subMenuMap.put(group, subMenu);
                    menu.add(subMenu);
                }
                JRadioButtonMenuItem lafItem = createRadioMenuItem(lafName, lafCls, currentLaf, lafAction, bg);
                subMenu.add(lafItem);
            }
        }
        subMenuMap.clear();
        
        return menu;
    }
    
    private JRadioButtonMenuItem createRadioMenuItem(String lafName, String lafCls, 
            String currentLaf, AbstractAction lafAction, ButtonGroup bg) {

        JRadioButtonMenuItem lafItem = new JRadioButtonMenuItem();
        lafItem.setHideActionText(true);
        lafItem.setAction(lafAction);
        lafItem.setActionCommand(lafCls);
        lafItem.setText(lafName);
        lafItem.setSelected(lafCls.equals(currentLaf));
        bg.add(lafItem);

        return lafItem;
    }
}
