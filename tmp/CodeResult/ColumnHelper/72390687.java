package open.dolphin.impl.psearch;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import open.dolphin.client.*;
import open.dolphin.delegater.MasudaDelegater;
import open.dolphin.delegater.PVTDelegater;
import open.dolphin.delegater.PatientDelegater;
import open.dolphin.dto.PatientSearchSpec;
import open.dolphin.helper.KeyBlocker;
import open.dolphin.helper.SimpleWorker;
import open.dolphin.project.Project;
import open.dolphin.setting.MiscSettingPanel;
import open.dolphin.table.*;
import open.dolphin.util.AgeCalculator;
import open.dolphin.common.util.StringTool;
import open.dolphin.common.util.ZenkakuUtils;
import open.dolphin.infomodel.ChartEventModel;
import open.dolphin.infomodel.PatientModel;
import open.dolphin.infomodel.PatientVisitModel;
import open.dolphin.infomodel.SearchResultModel;
import open.dolphin.infomodel.SimpleDate;

/**
 * 患者検索PatientSearchPlugin
 *
 * @author Kazushi Minagawa
 * @author modified by masuda, Masuda Naika
 */
public class PatientSearchImpl extends AbstractMainComponent {

    private final String NAME = "患者検索";
    private static final String[] COLUMN_NAMES 
            = {"ID", "氏名", "カナ", "性別", "生年月日", "受診日", "日数", "状態"};
    private final String[] PROPERTY_NAMES 
            = {"patientId", "fullName", "kanaName", "genderDesc", "ageBirthday", "pvtDateTrimTime", "getElapsedDay", "isOpened"};
    private static final Class[] COLUMN_CLASSES = {
        String.class, String.class, String.class, String.class, String.class, 
        String.class, Integer.class, Object.class};
    private final int[] COLUMN_WIDTH = {50, 100, 120, 30, 100, 80, 20, 20};
    private final int START_NUM_ROWS = 1;
   
    // カラム仕様名
    private static final String COLUMN_SPEC_NAME = "patientSearchTable.withoutAddress.column.spec";
    // カラム仕様ヘルパー
    private ColumnSpecHelper columnHelper;
    
    private static final String KEY_AGE_DISPLAY = "patientSearchTable.withoutAddress.ageDisplay";
    
    // 選択されている患者情報
    private PatientModel selectedPatient;
    // 年齢表示
    private boolean ageDisplay;
    // 年齢生年月日メソッド
    private final String[] AGE_METHOD = new String[]{"ageBirthday", "birthday"};
    // 受診日メソッド
    private static final String[] PVTDATE_METHOD = new String[]{"pvtDateTrimTime", "pvtDateTrimDate"};
    
    private static final String FINISHED = "finished";
    
    // View
    private PatientSearchView view;
    private KeyBlocker keyBlocker;

    // カラム仕様リスト
    private int ageColumn;
    private int pvtDateColumn;
    private int stateColumn;
    private int genderColumn;
    
    private ListTableModel<PatientModel> tableModel;
    private ListTableSorter<PatientModel> sorter;
    private AbstractAction copyAction;
    
    private final String clientUUID;
    private final ChartEventListener cel;
    
    // 過去患者検索期間
    private static final int pastDay = 100;

    
    /** Creates new PatientSearch */
    public PatientSearchImpl() {
        setName(NAME);
        cel = ChartEventListener.getInstance();
        clientUUID = cel.getClientUUID();
    }

    @Override
    public void start() {
        setup();
        initComponents();
        connect();
        enter();
    }

    @Override
    public void enter() {
        controlMenu();
//pns   入ってきたら，キーワードフィールドにフォーカス
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                view.getKeywordFld().requestFocusInWindow();
                view.getKeywordFld().selectAll();
            }
        });
        
    }

    @Override
    public void stop() {
        
        // ColumnSpecsを保存する
        if (columnHelper != null) {
            columnHelper.saveProperty();
        }
        // ChartStateListenerから除去する
        cel.removeListener(this);
    }

    public PatientModel getSelectedPatient() {
        return selectedPatient;
    }

    public void setSelectedPatient(PatientModel model) {
        selectedPatient = model;
        controlMenu();
    }

    public ListTableModel<PatientModel> getTableModel() {
        return (ListTableModel<PatientModel>) view.getTable().getModel();
    }

    /**
     * 年齢表示をオンオフする。
     */
    private void switchAgeDisplay() {
        
        if (view.getTable() == null) {
            return;
        }

        ageDisplay = !ageDisplay;
        Project.setBoolean(KEY_AGE_DISPLAY, ageDisplay);
        String method = ageDisplay ? AGE_METHOD[0] : AGE_METHOD[1];
        ListTableModel tModel = getTableModel();
        tModel.setProperty(method, ageColumn);

        List<ColumnSpec> columnSpecs = columnHelper.getColumnSpecs();
        for (int i = 0; i < columnSpecs.size(); i++) {
            ColumnSpec cs = columnSpecs.get(i);
            String test = cs.getMethod();
            if (test.toLowerCase().endsWith("birthday")) {
                cs.setMethod(method);
                break;
            }
        }
    }

    /**
     * メニューを制御する
     */
    private void controlMenu() {

        PatientModel pvt = getSelectedPatient();
        boolean enabled = (pvt != null);
        getContext().enabledAction(GUIConst.ACTION_OPEN_KARTE, enabled);
    }

    /**
     * 受付リストのコンテキストメニュークラス。
     */
    private class ContextListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            mabeShowPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mabeShowPopup(e);
        }

        public void mabeShowPopup(MouseEvent e) {

            if (e.isPopupTrigger()) {

                final JPopupMenu contextMenu = new JPopupMenu();

                int row = view.getTable().rowAtPoint(e.getPoint());
                PatientModel obj = sorter.getObject(row);
                int selected = view.getTable().getSelectedRow();

                if (row == selected && obj != null) {
                    JMenuItem mi1 = new JMenuItem(new AbstractAction("カルテを開く"){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            openKarte();
                        }
                    });
                    contextMenu.add(mi1);
                    contextMenu.addSeparator();
                    contextMenu.add(new JMenuItem(copyAction));
                    JMenuItem mi2 = new JMenuItem(new AbstractAction("受付登録"){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            addAsPvt();
                        }
                    });
                    contextMenu.add(mi2);
                    contextMenu.addSeparator();
                }

                JCheckBoxMenuItem item = new JCheckBoxMenuItem(new AbstractAction("年齢表示"){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchAgeDisplay();
                    }
                });
                contextMenu.add(item);
                item.setSelected(ageDisplay);
                
//pns^  検索結果をファイル保存
                if (view.getTable().getRowCount() > 0) {
                    JMenuItem mi3 = new JMenuItem(new AbstractAction("検索結果ファイル保存"){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            exportSearchResult();
                        }
                    });
                    contextMenu.add(mi3);
                }
//pns$
                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    private void setup() {
        
        // ColumnSpecHelperを準備する
        columnHelper = new ColumnSpecHelper(COLUMN_SPEC_NAME,
                COLUMN_NAMES, PROPERTY_NAMES, COLUMN_CLASSES, COLUMN_WIDTH);
        columnHelper.loadProperty();

        // Scan して age / pvtDate カラムを設定する
        ageColumn = columnHelper.getColumnPositionEndsWith("birthday");
        pvtDateColumn = columnHelper.getColumnPositionStartWith("pvtdate");
        stateColumn = columnHelper.getColumnPosition("isOpened");
        genderColumn = columnHelper.getColumnPosition("genderDesc");
        
        ageDisplay = Project.getBoolean(KEY_AGE_DISPLAY, true);
    }
    
    /**
     * GUI コンポーネントを初期化する。
     *
     */
    private void initComponents() {
        
        // View
        view = new PatientSearchView();
        setUI(view);

        // ColumnSpecHelperにテーブルを設定する
        columnHelper.setTable(view.getTable());

        //------------------------------------------
        // View のテーブルモデルを置き換える
        //------------------------------------------
        String[] columnNames = columnHelper.getTableModelColumnNames();
        String[] methods = columnHelper.getTableModelColumnMethods();
        Class[] cls = columnHelper.getTableModelColumnClasses();

        // テーブルモデルを設定
        tableModel = new ListTableModel<PatientModel>(columnNames, START_NUM_ROWS, methods, cls) {

            @Override
            public Object getValueAt(int row, int col) {

                Object ret = null;

                if (col == ageColumn && ageDisplay) {

                    PatientModel p = getObject(row);

                    if (p != null) {
                        int showMonth = Project.getInt("ageToNeedMonth", 6);
                        ret = AgeCalculator.getAgeAndBirthday(p.getBirthday(), showMonth);
                    }
                } else {

                    ret = super.getValueAt(row, col);
                }

                return ret;
            }
        };
        
//masuda^   table sorter 組み込み
        sorter = new ListTableSorter(tableModel);
        view.getTable().setModel(sorter);
        sorter.setTableHeader(view.getTable().getTableHeader());
//masuda$
        // カラム幅更新
        columnHelper.updateColumnWidth();

        // 連ドラ、梅ちゃん先生
        PatientListTableRenderer renderer = new PatientListTableRenderer();
        renderer.setTable(view.getTable());
        renderer.setDefaultRenderer();

        // HibernateSearchを使用するかなど Katou: 橋本医院では常時H検とする
        // final JComboBox methodCombo = view.getMethodCombo();
        // if (!useHibernateSearch()) {
        //     methodCombo.setSelectedItem(PatientSearchView.ALL_SEARCH);
        // }
        
        /*
        methodCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    boolean b = (methodCombo.getSelectedItem() == PatientSearchView.HIBERNATE_SEARCH);
                    Project.setBoolean(MiscSettingPanel.USE_HIBERNATE_SEARCH, b);
                }
            }
        });
        */

        // カルテ検索Radioをシフト右クリックでインデックス作成
        view.getKarteSearchBtn().addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                maybePopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                maybePopup(e);
            }
            private void maybePopup(MouseEvent e) {
                if ( e.isPopupTrigger() && e.isShiftDown()
                        /*&& view.getKarteSearchBtn().isSelected()
                        && methodCombo.getSelectedItem() == PatientSearchView.HIBERNATE_SEARCH*/) {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem mi;
                    mi = new JMenuItem("インデックス作成");
                    popup.add(mi);
                    mi.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            makeInitialIndex();
                        }
                    });
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        view.getPtSearchBtn().addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean b = !view.getPtSearchBtn().isSelected();
                // view.getMethodCombo().setEnabled(b);
            }
        });

        // 処方切れ検索
        view.getLoupeLbl().addMouseListener(new MouseAdapter(){

            @Override
            public void mousePressed(MouseEvent e) {
                maybePopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                maybePopup(e);
            }
            private void maybePopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem mi;
                    mi = new JMenuItem("処方切れ患者検索");
                    popup.add(mi);
                    mi.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            checkShohougire();
                        }
                    });
                    mi = new JMenuItem(String.format("過去%d日受診者検索", pastDay));
                    popup.add(mi);
                    mi.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            getPast100DayPatients();
                        }
                    });
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
        // 件数を0件にする
        updateStatusLabel();
    }

    /**
     * コンポーンントにリスナを登録し接続する。
     */
    private void connect() {

        // ColumnHelperでカラム変更関連イベントを設定する
        columnHelper.connect();
        // ChartEventListenerに登録する
        cel.addListener(this);

        EventAdapter adp = new EventAdapter(view.getKeywordFld(), view.getTable());

        // カレンダによる日付検索を設定する
        PopupListener pl = new PopupListener(view.getKeywordFld());

        // コンテキストメニューを設定する
        view.getTable().addMouseListener(new ContextListener());

        keyBlocker = new KeyBlocker(view.getKeywordFld());

        //-----------------------------------------------
        // Copy 機能を実装する
        //-----------------------------------------------
        KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        copyAction = new AbstractAction("コピー") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                copyRow();
            }
        };
        view.getTable().getInputMap().put(copy, "Copy");
        view.getTable().getActionMap().put("Copy", copyAction);
        
        // Enterでカルテオープン Katoh@Hashimoto-iin
        final String optionMapKey = "openKarte";
        final KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        view.getTable().getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, optionMapKey);
        view.getTable().getActionMap().put(optionMapKey, new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent e) {
                openKarte();
            }
        });
    }

    private class EventAdapter extends MouseAdapter implements ActionListener, ListSelectionListener {

        public EventAdapter(JTextField tf, JTable tbl) {

            boolean autoIme = Project.getBoolean("autoIme", true);
            if (autoIme) {
                tf.addFocusListener(AutoKanjiListener.getInstance());
            } else {
                tf.addFocusListener(AutoRomanListener.getInstance());
            }
            tf.addActionListener(EventAdapter.this);

            tbl.getSelectionModel().addListSelectionListener(EventAdapter.this);
            tbl.addMouseListener(EventAdapter.this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField tf = (JTextField) e.getSource();
            String test = tf.getText().trim();
            if (!test.equals("")) {
                find(test);
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
                JTable table = view.getTable();
                //ListTableModel<PatientModel> tableModel = getTableModel();
                int row = table.getSelectedRow();
//pns   row = -1 でここに入ってくることあり
                if (row >= 0) {
                    PatientModel patient = sorter.getObject(row);
                    setSelectedPatient(patient);
                } else {
                    setSelectedPatient(null);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
//masuda    nullかどうかの判断はopenKarte()でされるのでここでは不要か
                openKarte();
            }
        }
    }

    /**
     * 選択されている行をコピーする。
     */
    public void copyRow() {

        StringBuilder sb = new StringBuilder();
        int numRows = view.getTable().getSelectedRowCount();
        int[] rowsSelected = view.getTable().getSelectedRows();
        int numColumns =   view.getTable().getColumnCount();

        for (int i = 0; i < numRows; i++) {
            if (tableModel.getObject(rowsSelected[i]) != null) {
                StringBuilder s = new StringBuilder();
                for (int col = 0; col < numColumns; col++) {
                    Object o = view.getTable().getValueAt(rowsSelected[i], col);
                    if (o!=null) {
                        s.append(o.toString());
                    }
                    s.append(",");
                }
                if (s.length()>0) {
                    s.setLength(s.length()-1);
                }
                sb.append(s.toString()).append("\n");
            }
        }
        if (sb.length() > 0) {
            StringSelection stsel = new StringSelection(sb.toString());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stsel, stsel);
        }
    }

    /**
     * カルテを開く。
     * @param value 対象患者
     */
    private void openKarte() {

        // 来院情報を生成する
        PatientModel pm = getSelectedPatient();
        PatientVisitModel pvt = cel.createFakePvt(pm);
        // カルテコンテナを生成する
        getContext().openKarte(pvt);
    }

    // EVT から
    private void doStartProgress() {
        getContext().getProgressBar().setIndeterminate(true);
        getContext().getGlassPane().block();
        keyBlocker.block();
    }

    // EVT から
    private void doStopProgress() {
        getContext().getProgressBar().setIndeterminate(false);
        getContext().getProgressBar().setValue(0);
        getContext().getGlassPane().unblock();
        keyBlocker.unblock();
    }

    /**
     * リストで選択された患者を受付に登録する。
     */
    private void addAsPvt() {

        // 来院情報を生成する
        SimpleWorker worker = new SimpleWorker<Void, Void>() {

            @Override
            protected Void doInBackground() {
                try {
                    PatientModel pm = getSelectedPatient();
                    PatientVisitModel pvt = cel.createFakePvt(pm);
                    PVTDelegater pdl = PVTDelegater.getInstance();
                    pdl.addPvt(pvt);
                } catch (Exception ex) {
                }
                return null;
            }

            @Override
            protected void succeeded(Void result) {
            }

            @Override
            protected void failed(Throwable cause) {
            }

            @Override
            protected void startProgress() {
                doStartProgress();
            }

            @Override
            protected void stopProgress() {
                doStopProgress();
            }
        };

        worker.execute();
    }


    /**
     * 検索を実行する。
     * @param text キーワード
     */
    private void find(String text) {
        
        if (view.getPtSearchBtn().isSelected()) {

            PatientSearchSpec spec = new PatientSearchSpec();

            if (isDate(text)) {
                spec.setCode(PatientSearchSpec.DATE_SEARCH);
                spec.setDigit(text);

            } else if (StringTool.startsWithKatakana(text)) {
                spec.setCode(PatientSearchSpec.KANA_SEARCH);
                text = text.replace("　", " ");     // 全角スペースは半角に置換する
                spec.setName(text);

            } else if (StringTool.startsWithHiragana(text)) {
                text = StringTool.hiraganaToKatakana(text);
                spec.setCode(PatientSearchSpec.KANA_SEARCH);
                text = text.replace("　", " ");     // 全角スペースは半角に置換する
                spec.setName(text);

            } else if (isNameAddress(text)) {
                spec.setCode(PatientSearchSpec.NAME_SEARCH);
                text = text.replace("　", " ");     // 全角スペースは半角に置換する
                spec.setName(text);

            } else {

                if (Project.getBoolean("zero.paddings.id.search", false)) {
                    int len = text.length();
                    int paddings = Project.getInt("patient.id.length", 0) - len;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < paddings; i++) {
                        sb.append("0");
                    }
                    sb.append(text);
                    text = sb.toString();
                }
                
                //カルテ検索時に、全角数字を半角として扱えるようにする
                // https://github.com/KatouBuntarou/OpenDolphin-2.3mh/issues/14
                text = ZenkakuUtils.toHalfNumber(text);

                spec.setCode(PatientSearchSpec.DIGIT_SEARCH);
                spec.setDigit(text);
            }
            
            // PVT searchの場合はgetPvtTrimDate, その他はgetPvtTrimTime
            boolean trimDate = (spec.getCode() == PatientSearchSpec.DATE_SEARCH);
            setPvtDateMethodTrimDate(trimDate);

            SearchTask task = new SearchTask(spec);
            task.execute();

        } else {
            // 全文検索
            // 文字数が１文字だと hibernate search が止まってしまう
            if (useHibernateSearch() && text.length() <= 1) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(view),
                        "検索文字列は２文字以上入力して下さい",  "検索文字列入力エラー", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // pvtDateのmethod変更。いつもtrimDate = false
            setPvtDateMethodTrimDate(false);
            FullTextSearchTask task  = new FullTextSearchTask(text);
            task.execute();
        }

    }

    // カルテ検索のタスク
    private class SearchTask extends SimpleWorker<Collection<PatientModel>, Void> {

        private final PatientSearchSpec searchSpec;

        private SearchTask(PatientSearchSpec spec) {
            searchSpec = spec;
        }

        @Override
        protected Collection<PatientModel> doInBackground() throws Exception {

            PatientDelegater pdl = PatientDelegater.getInstance();
            Collection<PatientModel> result = pdl.getPatients(searchSpec);
            return result;
        }

        @Override
        protected void succeeded(Collection<PatientModel> result) {

            if (result != null){
                tableModel.setDataProvider((List<PatientModel>) result);
            } else {
                tableModel.clear();
            }
            updateStatusLabel();
        }

        @Override
        protected void failed(Throwable cause) {
        }

        @Override
        protected void startProgress() {
            doStartProgress();
        }

        @Override
        protected void stopProgress() {
            doStopProgress();
            
            // カルテをIDで検索した際、候補が1件であれば即座に開くように修正する
            // https://github.com/KatouBuntarou/OpenDolphin-2.3mh/issues/15
            if (tableModel.getDataProvider().size() == 1) {
                //setSelectedPatient(tableModel.getDataProvider().get(0));
                view.getTable().getSelectionModel().setSelectionInterval(0, 0);
                openKarte();
            }
        }
    }

    private boolean isDate(String text) {
        boolean maybe = false;
        if (text != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.parse(text);
                maybe = true;

            } catch (ParseException e) {
            }
        }

        return maybe;
    }

    private boolean isNameAddress(String text) {
        boolean maybe = false;
        if (text != null) {
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.getType(c) == Character.OTHER_LETTER) {
                    maybe = true;
                    break;
                }
            }
        }
        return maybe;
    }

    /**
     * テキストフィールドへ日付を入力するためのカレンダーポップアップメニュークラス。
     */
    private class PopupListener extends PopupCalendarListener {

        public PopupListener(JTextField tf) {
            super(tf);
        }
        
        @Override
        public void setValue(SimpleDate sd) {
            tf.setText(SimpleDate.simpleDateToMmldate(sd));
            String test = tf.getText().trim();
            if (!test.isEmpty()) {
                find(test);
            }
        }
    }
    
    
//pns^
    /**
     * 検索結果をファイルに書き出す
     */
    private void exportSearchResult() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.exists() || isOverwriteConfirmed(file)) {
                
                try (FileWriter writer = new FileWriter(file)) {
                    JTable table = view.getTable();
                    // 書き出す内容
                    StringBuilder sb = new StringBuilder();
                    for (int row = 0; row < table.getRowCount(); row++) {
                        for (int column = 0; column < table.getColumnCount(); column++) {
                            sb.append(column == 0 ? "" : ',');
                            sb.append('"');
                            sb.append(table.getValueAt(row, column));
                            sb.append('"');
                        }
                        sb.append('\n');
                    }
                    writer.write(sb.toString());
                    
                    // close
                    writer.close();

                } catch (IOException ex) {
                    System.out.println("PatientSearchImpl.java: " + ex);
                }
            }
        }
    }

    /**
     * ファイル上書き確認ダイアログを表示する。
     * @param file 上書き対象ファイル
     * @return 上書きOKが指示されたらtrue
     */
    private boolean isOverwriteConfirmed(File file) {
        String title = "上書き確認";
        String message = "既存のファイル「" + file.toString() + "」\n" + "を上書きしようとしています。続けますか？";

        int confirm = JOptionPane.showConfirmDialog(
                view, message, title,
                JOptionPane.WARNING_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION);

        return confirm == JOptionPane.OK_OPTION;
    }
//pns$
    
//masuda^
    // ステータスラベルに検索件数を表示
    private void updateStatusLabel() {
        int count = tableModel.getObjectCount();
        String msg = String.valueOf(count) + "件";
        this.getContext().getStatusLabel().setText(msg);
    }

    // pvtDateのmethodを変更
    private void setPvtDateMethodTrimDate(boolean trimDate) {

        String method = tableModel.getProperty(pvtDateColumn);
        if (trimDate) {
            if (!PVTDATE_METHOD[1].equals(method)) {
                tableModel.setProperty(PVTDATE_METHOD[1], pvtDateColumn);
            }
        } else {
            if (!PVTDATE_METHOD[0].equals(method)) {
                tableModel.setProperty(PVTDATE_METHOD[0], pvtDateColumn);
            }
        }
    }

    // 処方切れチェック
    public void checkShohougire(){

        SwingWorker worker = new SwingWorker<List<PatientModel>, Void>() {

            @Override
            protected List<PatientModel> doInBackground() throws Exception {
                doStartProgress();
                getContext().getGlassPane().setText("処方切れ患者を検索中です。");
                CheckMedication cm = new CheckMedication();
                List<PatientModel> result = cm.getShohougirePatient();
                return result;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void done(){
                try {
                    List<PatientModel> result = get();
                    if (result != null) {
                        setPvtDateMethodTrimDate(false);
                        tableModel.setDataProvider(result);
                    } else {
                        tableModel.clear();
                    }
                } catch (InterruptedException | ExecutionException ex) {
                } finally{
                    doStopProgress();
                    updateStatusLabel();
                }
            }
        };
        worker.execute();
    }
    
    // 過去１００日の受診者検索
    public void getPast100DayPatients() {
        
        SwingWorker worker = new SwingWorker<List<PatientModel>, Void>() {

            @Override
            protected List<PatientModel> doInBackground() throws Exception {
                doStartProgress();
                getContext().getGlassPane().setText(String.format("過去%d日間の受診患者を検索中です。", pastDay));
                List<PatientModel> result = PatientDelegater.getInstance().getPast100DayPatients(pastDay);
                return result;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void done(){
                try {
                    List<PatientModel> result = get();
                    if (result != null) {
                        setPvtDateMethodTrimDate(false);
                        tableModel.setDataProvider(result);
                    } else {
                        tableModel.clear();
                    }
                } catch (InterruptedException | ExecutionException ex) {
                } finally{
                    doStopProgress();
                    updateStatusLabel();
                }
            }
        };
        worker.execute();
    }
    
    private class FullTextSearchTask extends SimpleWorker<List<PatientModel>, String[]> {

        private final String searchText;
        private ProgressMonitor progressMonitor;
        private final String message = "カルテ内検索";
        private final String progressNote = "<html>「%s」を検索中<br>（%d％完了，%d件発見）";
        private final String startingNote = "処理を開始します。";
        private final String initialNote = "<html><br>";


        public FullTextSearchTask(String searchText) {
            this.searchText = searchText;
        }

        @Override
        protected List<PatientModel> doInBackground() throws Exception {

            doStartProgress();

            progressMonitor = new ProgressMonitor(view, message, initialNote, 0, 100);

            // boolean hibernateSearch = view.getMethodCombo().getSelectedItem() == PatientSearchView.HIBERNATE_SEARCH;
            boolean hibernateSearch = true;

            // 患者検索
            if (!hibernateSearch) {
                progressMonitor.setMillisToDecideToPopup(0); // この処理は絶対時間がかかるので，すぐ出す
                progressMonitor.setMillisToPopup(0);
                return grepSearch();
            } else {
                return hibernateSearch();
            }
        }

        private List<PatientModel> hibernateSearch() throws Exception {
            // カルテ内検索をちょっとインチキする(Hibernate Search)
            publish(new String[]{startingNote, "50"});
            MasudaDelegater dl = MasudaDelegater.getInstance();
            // 患者を絞らない場合は karteId = 0 を設定する
            return dl.getKarteFullTextSearch(0, searchText);
        }

        private List<PatientModel> grepSearch() throws Exception {

            final int maxResult = 500;
            /* final boolean progressCourseOnly 
                    = view.getMethodCombo().getSelectedItem() == PatientSearchView.CONTENT_SEARCH;*/
            final boolean progressCourseOnly = false;

            // 検索開始
            MasudaDelegater dl = MasudaDelegater.getInstance();
            HashSet<PatientModel> pmSet = new HashSet<>();
            SearchResultModel srm = new SearchResultModel();

            long fromId = 0;
            int page = 0;
            long moduleCount = 0;
            // progress bar 表示
            publish(new String[]{startingNote, "0"});
            
            while (srm != null) {
                // キャンセルされた場合
                if (progressMonitor.isCanceled()) {
                    return new ArrayList<>(pmSet);
                }
                srm = dl.getSearchResult(searchText, fromId, maxResult, moduleCount, progressCourseOnly);

                if (srm != null) {
                    moduleCount = srm.getTotalCount();
                    fromId = srm.getDocPk();
                    List<PatientModel> newList = srm.getResultList();

                    for (PatientModel pm : newList) {
                        boolean found = false;
                        for (PatientModel old : pmSet) {
                            if (old.getId() == pm.getId()) {
                                List<Long> docIdList = old.getDocPkList();
                                if (docIdList != null) {
                                    HashSet<Long> pkSet = new HashSet<>();
                                    pkSet.addAll(docIdList);
                                    if (pm.getDocPkList() != null) {
                                        pkSet.addAll(pm.getDocPkList());
                                    }
                                    pm.setDocPkList(new ArrayList(pkSet));
                                }
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            pmSet.add(pm);
                        }
                    }
                    page++;
                    // progress bar 表示
                    int ratio = (moduleCount == 0)
                            ? 0 : (int) (100 * page * maxResult / moduleCount);
                    String msg = String.format(progressNote, searchText, ratio, pmSet.size());
                    publish(new String[]{msg, String.valueOf(ratio)});
                }

            }
            return new ArrayList<>(pmSet);
        }

        @Override
        protected void process(List<String[]> chunks) {
            for (String[] chunk : chunks) {
                progressMonitor.setNote(chunk[0]);
                progressMonitor.setProgress(Integer.valueOf(chunk[1]));
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void succeeded(List<PatientModel> result) {
            if (result != null) {
                tableModel.setDataProvider(result);
            } else {
                tableModel.clear();
            }
            updateStatusLabel();
            progressMonitor.close();
            doStopProgress();
        }

        @Override
        protected void failed(Throwable cause) {
            cause.printStackTrace(System.err);
            doStopProgress();
        }
    }
    
    private void makeInitialIndex() {

        IndexTaskWorker worker = new IndexTaskWorker();
        getContext().getGlassPane().setText("インデックス作成は時間がかかります。");
        worker.execute();
    }
    
    private class IndexTaskWorker extends SimpleWorker<Void, String[]> {

        private ProgressMonitor progressMonitor;
        private final String message = "インデックス作成";
        private final String progressNote = "<html>索引を作成中<br>%d件中、%d％完了";
        private final String startingNote = "処理を開始します。";
        private final String initialNote = "<html><br>";

        @Override
        protected Void doInBackground() throws Exception {

            doStartProgress();
            // progress bar 設定
            progressMonitor = new ProgressMonitor(view, message, initialNote, 0, 100);
            progressMonitor.setMillisToDecideToPopup(0); // この処理は絶対時間がかかるので，すぐ出す
            progressMonitor.setMillisToPopup(0);
            progressMonitor.setProgress(0);

            // 索引作成開始
            MasudaDelegater dl = MasudaDelegater.getInstance();

            // maxResult毎にインデックス作成する
            long totalModelCount = 0;
            final int maxResults = 200;
            long fromDocPk = 0;
            int page = 0;
            String ret = null;
            // progress bar 表示
            publish(new String[]{startingNote, "0"});
            
            while (!FINISHED.equals(ret)) {
                // キャンセルされた場合
                if (progressMonitor.isCanceled()) {
                    break;
                }
                ret = dl.makeDocumentModelIndex(fromDocPk, maxResults, totalModelCount);
                if (!FINISHED.equals(ret)) {
                    String[] str = ret.split(",");
                    totalModelCount = Long.valueOf(str[1]);
                    fromDocPk = Long.valueOf(str[0]);
                    page++;
                    // progress bar 表示
                    int ratio = (totalModelCount == 0)
                        ? 0 : (int) (100 * page * maxResults / totalModelCount);
                    String msg = String.format(progressNote, totalModelCount, ratio);
                    publish(new String[]{msg, String.valueOf(ratio)});
                }
            }
            return null;
        }

        @Override
        protected void process(List<String[]> chunks) {
            for (String[] chunk : chunks) {
                progressMonitor.setNote(chunk[0]);
                progressMonitor.setProgress(Integer.valueOf(chunk[1]));
            }
        }

        @Override
        protected void done() {
            getContext().getGlassPane().setText("");
            progressMonitor.close();
            doStopProgress();
        }

        @Override
        protected void failed(Throwable cause) {
            cause.printStackTrace(System.err);
            doStopProgress();
        }
    }
    
    private boolean useHibernateSearch() {
        boolean b = Project.getBoolean(MiscSettingPanel.USE_HIBERNATE_SEARCH, MiscSettingPanel.DEFAULT_HIBERNATE_SEARCH);
        return b;
    }
//masuda$

    // ChartEventListener
    @Override
    public void onEvent(ChartEventModel evt) {

        int sRow = -1;
        long ptPk = evt.getPtPk();
        List<PatientModel> list = tableModel.getDataProvider();
        ChartEventModel.EVENT eventType = evt.getEventType();
        
        switch (eventType) {
            case PVT_STATE:
                for (int row = 0; row < list.size(); ++row) {
                    PatientModel pm = list.get(row);
                    if (ptPk == pm.getId()) {
                        sRow = row;
                        pm.setOwnerUUID(evt.getOwnerUUID());
                        break;
                    }
                }
                break;
            case PM_MERGE:
                for (int row = 0; row < list.size(); ++row) {
                    PatientModel pm = list.get(row);
                    if (ptPk == pm.getId()) {
                        sRow = row;
                        //pm = msg.getPatientModel();
                        list.set(row, evt.getPatientModel());
                        break;
                    }
                }
                break;            
            case PVT_MERGE:
                for (int row = 0; row < list.size(); ++row) {
                    PatientModel pm = list.get(row);
                    if (ptPk == pm.getId()) {
                        sRow = row;
                        //pm = msg.getPatientVisitModel().getPatientModel();
                        list.set(row, evt.getPatientVisitModel().getPatientModel());
                        break;
                    }
                }
                break;
            default:
                break;
        }
        
        if (sRow != -1) {
            tableModel.fireTableRowsUpdated(sRow, sRow);
        }
    }
    
    private class PatientListTableRenderer extends StripeTableCellRenderer {
        
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean isFocused, int row, int col) {

            super.getTableCellRendererComponent(table, value, isSelected, isFocused, row, col);
            
            PatientModel pm = sorter.getObject(row);
            if (pm == null) {
                return this;
            }
            
            if (col == stateColumn) {
                setHorizontalAlignment(CENTER);
                setBorder(null);
                if (pm.isOpened()) {
                    if (clientUUID.equals(pm.getOwnerUUID())) {
                        setIcon(OPEN_ICON);
                    } else {
                        setIcon(NETWORK_ICON);
                    }
                } else {
                    setIcon(null);
                }
                setText("");
            } else {
                if (col == genderColumn) {
                    setHorizontalAlignment(CENTER);
                }
                setIcon(null);
                setText(value == null ? "" : value.toString());
            }

            return this;
        }
    }
}
