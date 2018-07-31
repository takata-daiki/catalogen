package open.dolphin.impl.pvt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import open.dolphin.client.*;
import open.dolphin.delegater.PVTDelegater;
import open.dolphin.infomodel.ChartEventModel;
import open.dolphin.infomodel.IInfoModel;
import open.dolphin.infomodel.ModelUtils;
import open.dolphin.infomodel.PatientModel;
import open.dolphin.infomodel.PatientVisitModel;
import open.dolphin.project.Project;
import open.dolphin.table.*;
import open.dolphin.util.AgeCalculator;
import open.dolphin.util.NamedThreadFactory;
import org.apache.commons.lang.time.DurationFormatUtils;

/**
 * 受付リスト。
 *
 * @author Kazushi Minagawa, Digital Globe, Inc.
 * @author modified by masuda, Masuda Naika
 */
public class WatingListImpl extends AbstractMainComponent {

    // Window Title
    private static final String NAME = "受付リスト";
    // 担当分のみを表示するかどうかの preference key
    private static final String ASSIGNED_ONLY = "assignedOnly";
    // 担当医未定の ORCA 医師ID
    private static final String UN_ASSIGNED_ID = "18080";
    
    // JTableレンダラ用の男性カラー
    private static final Color MALE_COLOR = new Color(230, 243, 243);
    // JTableレンダラ用の女性カラー
    private static final Color FEMALE_COLOR = new Color(254, 221, 242);
    // 受付キャンセルカラー
    private static final Color CANCEL_PVT_COLOR = new Color(128, 128, 128);
    // その他カラー by pns
    private static final Color SHOSHIN_COLOR = new Color(180,220,240); //青っぽい色
    private static final Color KARTE_EMPTY_COLOR = new Color(250,200,160); //茶色っぽい色
    private static final Color DIAGNOSIS_EMPTY_COLOR = new Color(243,255,15); //黄色
    
    // 来院テーブルのカラム名
    private static final String[] COLUMN_NAMES = {
        "受付", "患者ID", "来院時間", "氏   名", "性別", "保険", 
        "生年月日", "担当医", "診療科", "予約", "メモ", "状態"};
    // 来院テーブルのカラムメソッド
    private static final String[] PROPERTY_NAMES = {
        "getNumber", "getPatientId", "getPvtDateTrimDate", "getPatientName", "getPatientGenderDesc", "getFirstInsurance",
        "getPatientAgeBirthday", "getDoctorName", "getDeptName", "getAppointment", "getMemo", "getStateInteger"};
    // 来院テーブルのクラス名
    private static final Class[] COLUMN_CLASSES = {
        Integer.class, String.class, String.class, String.class, String.class, String.class, 
        String.class, String.class, String.class, String.class, String.class, Integer.class};
    // 来院テーブルのカラム幅
    private static final int[] COLUMN_WIDTH = {
        20, 80, 60, 100, 40, 130, 
        130, 50, 60, 40, 80, 30};
    // 年齢生年月日メソッド 
    private final String[] AGE_METHOD = {"getPatientAgeBirthday", "getPatientBirthday"};
    // カラム仕様名
    private static final String COLUMN_SPEC_NAME = "pvtTable.column.spec";

    // カラム仕様ヘルパー
    private ColumnSpecHelper columnHelper;

    // 受付時間カラム
    private int visitedTimeColumn;
    // 性別カラム
    private int sexColumn;
    // 年齢表示カラム
    private int ageColumn;
    // 来院情報テーブルのメモカラム
    private int memoColumn;
    // 来院情報テーブルのステータスカラム
    private int stateColumn;
    // 受付番号カラム
    //private int numberColumn;

    // PVT Table 
    private JTable pvtTable;
    // Table Model
    private ListTableModel<PatientVisitModel> pvtTableModel;
    // TableSorter
    private ListTableSorter<PatientVisitModel> sorter;
    
    // 性別レンダラフラグ 
    private boolean sexRenderer;
    // 年齢表示 
    private boolean ageDisplay;
    // 選択されている行を保存
    private int selectedRow;
    // View class
    private WatingListView view;
    
    // 状態ビット・アイコン配列
    private final BitAndIconPair[] bitAndIconPairs = {
        new BitAndIconPair(PatientVisitModel.BIT_OPEN, 
            OPEN_ICON),
        new BitAndIconPair(PatientVisitModel.BIT_SAVE_CLAIM, 
            ClientContext.getImageIconAlias("icon_sent_claim_small")),
        new BitAndIconPair(PatientVisitModel.BIT_MODIFY_CLAIM, 
            ClientContext.getImageIconAlias("icon_karte_modified_small")),
        new BitAndIconPair(PatientVisitModel.BIT_TREATMENT, 
            ClientContext.getImageIconAlias("icon_under_treatment_small")),
        new BitAndIconPair(PatientVisitModel.BIT_HURRY, 
            ClientContext.getImageIconAlias("icon_emergency_small")),
        new BitAndIconPair(PatientVisitModel.BIT_GO_OUT, 
            ClientContext.getImageIconAlias("icon_under_shopping_small")),
        new BitAndIconPair(PatientVisitModel.BIT_CANCEL, 
            ClientContext.getImageIconAlias("icon_cancel_small"))
    };
    
    // 修正送信アイコンの配列インデックス
    private static final int INDEX_MODIFY_SEND_ICON = 2;
    // save_claim, modify_claim
    private static final int[] SAVED_BITS = {1, 2};
    // others
    private static final int[] USER_BITS = {1, 2, 3, 4, 5, 6};
    
    // Status　情報　メインウィンドウの左下に表示される内容
    private String statusInfo;

    // State 設定用のcombobox
    private JComboBox stateCmb;
    private AbstractAction copyAction;

    // 受付数・待ち時間の更新間隔
    private static final int intervalSync = 60;

    // pvtUpdateTask
    private ScheduledExecutorService executor;
    private ScheduledFuture schedule;
    private Runnable timerTask;
    
    // pvtCount
    private int totalPvtCount;
    private int waitingPvtCount;
    private Date waitingPvtDate;
    
    // PatientVisitModelの全部
    private List<PatientVisitModel> pvtList;
    
    // pvt delegater
    private PVTDelegater pvtDelegater;
    
    private final String clientUUID;
    private final ChartEventListener cel;
    private final String orcaId;
    
    /**
     * Creates new WatingList
     */
    public WatingListImpl() {
        setName(NAME);
        cel = ChartEventListener.getInstance();
        clientUUID = cel.getClientUUID();
        orcaId = Project.getUserModel().getOrcaId();
    }
    
    /**
     * プログラムを開始する。
     */
    @Override
    public void start() {
        setup();
        initComponents();
        connect();
        startSyncMode();
    }
    
    private void setup() {
        
        // ColumnSpecHelperを準備する
        columnHelper = new ColumnSpecHelper(COLUMN_SPEC_NAME,
                COLUMN_NAMES, PROPERTY_NAMES, COLUMN_CLASSES, COLUMN_WIDTH);
        columnHelper.loadProperty();

        // Scan して age, memo, state カラムを設定する
        visitedTimeColumn = columnHelper.getColumnPosition("getPvtDateTrimDate");
        sexColumn = columnHelper.getColumnPosition("getPatientGenderDesc");
        ageColumn = columnHelper.getColumnPositionEndsWith("Birthday");
        memoColumn = columnHelper.getColumnPosition("getMemo");
        stateColumn = columnHelper.getColumnPosition("getStateInteger");
        //numberColumn = columnHelper.getColumnPosition("getNumber");
        
        // 修正送信アイコンを決める
        ImageIcon modifySendIcon = Project.getBoolean("change.icon.modify.send", true)
                ? ClientContext.getImageIconAlias("icon_karte_modified_small")
                : ClientContext.getImageIconAlias("icon_sent_claim_small");
        bitAndIconPairs[INDEX_MODIFY_SEND_ICON].setIcon(modifySendIcon);
        
        stateCmb = new JComboBox();
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        renderer.setPreferredSize(new Dimension(30, ClientContext.getHigherRowHeight()));
        stateCmb.setRenderer(renderer);

        sexRenderer = Project.getBoolean("sexRenderer", false);
        ageDisplay = Project.getBoolean("ageDisplay", true);

        NamedThreadFactory factory = new NamedThreadFactory(getClass().getSimpleName());
        executor = Executors.newSingleThreadScheduledExecutor(factory);
        
        pvtDelegater = PVTDelegater.getInstance();
    }
    
    /**
     * GUI コンポーネントを初期化しレアイアウトする。
     */
    private void initComponents() {

        // View クラスを生成しこのプラグインの UI とする
        view = new WatingListView();
        setUI(view);

        view.getPvtInfoLbl().setText("");
        pvtTable = view.getTable();
        
        // ColumnSpecHelperにテーブルを設定する
        columnHelper.setTable(pvtTable);
        
        //------------------------------------------
        // View のテーブルモデルを置き換える
        //------------------------------------------
        String[] columnNames = columnHelper.getTableModelColumnNames();
        String[] methods = columnHelper.getTableModelColumnMethods();
        Class[] cls = columnHelper.getTableModelColumnClasses();

        pvtTableModel = new ListTableModel<PatientVisitModel>(columnNames, 1, methods, cls) {

            @Override
            public boolean isCellEditable(int row, int col) {

                // nullなら編集不可
                if (getObject(row) == null) {
                    return false;
                }
                
                // メモは編集可
                if (col == memoColumn) {
                    return true;
                }
                // 状態カラムでないなら不可
                if (col != stateColumn) {
                    return false;
                }

                // statusをチェックする
                PatientVisitModel pvt = getObject(row);
                
                // sendClaim / modifyClaim
                if (pvt.getStateBit(PatientVisitModel.BIT_SAVE_CLAIM)
                        || pvt.getStateBit(PatientVisitModel.BIT_MODIFY_CLAIM)) {
                    stateCmb.removeAllItems();
                    for (int i : SAVED_BITS) {
                        stateCmb.addItem(bitAndIconPairs[i]);
                    }
                    return true;
                }
                
                // クローズ・処置中・急ぎ・外出
                if (pvt.getState() == 0
                        || pvt.getStateBit(PatientVisitModel.BIT_TREATMENT)
                        || pvt.getStateBit(PatientVisitModel.BIT_HURRY)
                        || pvt.getStateBit(PatientVisitModel.BIT_GO_OUT)) {
                    stateCmb.removeAllItems();
                    stateCmb.addItem(new BitAndIconPair(PatientVisitModel.BIT_OPEN, null));
                    for (int i : USER_BITS) {
                        stateCmb.addItem(bitAndIconPairs[i]);
                    }
                    return true;
                }

                return false;
            }

            @Override
            public Object getValueAt(int row, int col) {

                Object ret = null;

                if (col == ageColumn && ageDisplay) {

                    PatientVisitModel p = getObject(row);

                    if (p != null) {
                        int showMonth = Project.getInt("ageToNeedMonth", 6);
                        ret = AgeCalculator.getAgeAndBirthday(p.getPatientModel().getBirthday(), showMonth);
                    }
                } else {

                    ret = super.getValueAt(row, col);
                }

                return ret;
            }

            @Override
            public void setValueAt(Object value, int row, int col) {

                // ここはsorterから取得したらダメ
                //final PatientVisitModel pvt = (PatientVisitModel) sorter.getObject(row);
                final PatientVisitModel pvt = pvtTableModel.getObject(row);
                
                if (pvt == null || value == null) {
                    return;
                }

                // Memo
                if (col == memoColumn) {
                    String memo = ((String) value).trim();
                    if (memo != null && (!memo.equals(""))) {
                        pvt.setMemo(memo);
                        cel.publishPvtState(pvt);
                    }

                } else if (col == stateColumn) {

                    // State ComboBox の value
                    BitAndIconPair pair = (BitAndIconPair) value;
                    int theBit = pair.getBit().intValue();

                    if (theBit == PatientVisitModel.BIT_CANCEL) {

                        Object[] cstOptions = new Object[]{"はい", "いいえ"};

                        StringBuilder sb = new StringBuilder(pvt.getPatientName());
                        sb.append("様の受付を取り消しますか?");
                        String msg = sb.toString();

                        int select = JOptionPane.showOptionDialog(
                                SwingUtilities.getWindowAncestor(pvtTable),
                                msg,
                                ClientContext.getFrameTitle(getName()),
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                ClientContext.getImageIconAlias("icon_caution"),
                                cstOptions, "はい");

                        System.err.println("select=" + select);

                        if (select != 0) {
                            return;
                        }
                    }
                    
                    // UNFINISHED以外をクリア
                    int oldState = pvt.getState();
                    pvt.setState(oldState & (1 << PatientVisitModel.BIT_UNFINISHED));

                    // set the bit、BIT_OPENは除外
                    if (theBit != 0) {
                        pvt.setStateBit(theBit, true);
                    }
                    
                    // 変更あればpublish
                    if (oldState != pvt.getState()) {
                        cel.publishPvtState(pvt);
                    }
                }
            }
        };

        // sorter組み込み
        sorter = new ListTableSorter(pvtTableModel);
        pvtTable.setModel(sorter);
        sorter.setTableHeader(pvtTable.getTableHeader());

        // 選択モード
        pvtTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Memo 欄 clickCountToStart=1
        JTextField tf = new JTextField();
        tf.addFocusListener(AutoKanjiListener.getInstance());
        DefaultCellEditor de = new DefaultCellEditor2(tf);
        de.setClickCountToStart(1);
        pvtTable.getColumnModel().getColumn(memoColumn).setCellEditor(de);

        // 性別レンダラを生成する
        MaleFemaleRenderer maleFemaleRenderer = new MaleFemaleRenderer();
        maleFemaleRenderer.setTable(pvtTable);
        // Center Renderer
        CenterRenderer centerRenderer = new CenterRenderer();
        centerRenderer.setTable(pvtTable);
        // カルテ(PVT)状態レンダラ
        KarteStateRenderer stateRenderer = new KarteStateRenderer();
        stateRenderer.setTable(pvtTable);
        
        List<ColumnSpec> columnSpecs = columnHelper.getColumnSpecs();
        for (int i = 0; i < columnSpecs.size(); i++) {
            if (i == visitedTimeColumn || i == sexColumn) {
                pvtTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            } else if (i == stateColumn) {
                pvtTable.getColumnModel().getColumn(i).setCellRenderer(stateRenderer);
            } else {
                pvtTable.getColumnModel().getColumn(i).setCellRenderer(maleFemaleRenderer);
            }
        }

        // PVT状態設定エディタ
        pvtTable.getColumnModel().getColumn(stateColumn).setCellEditor(new DefaultCellEditor(stateCmb));
        
        // カラム幅更新
        columnHelper.updateColumnWidth();
    }

    /**
     * コンポーネントにイベントハンドラーを登録し相互に接続する。
     */
    private void connect() {

        // ColumnHelperでカラム変更関連イベントを設定する
        columnHelper.connect();
        
        // 来院リストテーブル 選択
        pvtTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedRow = pvtTable.getSelectedRow();
                    controlMenu();
                }
            }
        });

        // 来院リストテーブル ダブルクリック
        view.getTable().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openKarte();
                }
            }
        });

        // コンテキストメニューを登録する
        view.getTable().addMouseListener(new ContextListener());

        // 靴のアイコンをクリックした時来院情報を検索する
        view.getKutuBtn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 同期モードではPvtListを取得し直し
                getFullPvt();
            }
        });

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
        pvtTable.getInputMap().put(copy, "Copy");
        pvtTable.getActionMap().put("Copy", copyAction);
        
        // Enterでカルテオープン Katoh@Hashimoto-iin
        final String optionMapKey = "openKarte";
        final KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        pvtTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, optionMapKey);
        pvtTable.getActionMap().put(optionMapKey, new AbstractAction(){

            @Override
            public void actionPerformed(ActionEvent e) {
                openKarte();
            }
        });
        
    }

    // comet long polling機能を設定する
    private void startSyncMode() {
        setStatusInfo();
        getFullPvt();
        cel.addListener(this);
        timerTask = new UpdatePvtInfoTask();
        restartTimer();
        enter();
    }
    
    /**
     * タイマーをリスタートする。
     */
    private void restartTimer() {

        if (schedule != null && !schedule.isCancelled()) {
            if (!schedule.cancel(true)) {
                return;
            }
        }

        // 同期モードでは毎分０秒に待ち患者数を更新する
        GregorianCalendar now = new GregorianCalendar();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(now.getTime());
        gc.clear(GregorianCalendar.SECOND);
        gc.clear(GregorianCalendar.MILLISECOND);
        gc.add(GregorianCalendar.MINUTE, 1);
        long delay = gc.getTimeInMillis() - now.getTimeInMillis();
        long interval = intervalSync * 1000;

        schedule = executor.scheduleWithFixedDelay(timerTask, delay, interval, TimeUnit.MILLISECONDS);
    }
    
    /**
     * メインウインドウのタブで受付リストに切り替わった時 コールされる。
     */
    @Override
    public void enter() {
        controlMenu();
        getContext().getStatusLabel().setText(statusInfo);
    }

    /**
     * プログラムを終了する。
     */
    @Override
    public void stop() {
        
        // ColumnSpecsを保存する
        if (columnHelper != null) {
            columnHelper.saveProperty();
        }
        // ChartStateListenerから除去する
        cel.removeListener(this);
        
        if (executor != null) {
            executor.shutdownNow();
        }
    }


    /**
     * 性別レンダラかどうかを返す。
     *
     * @return 性別レンダラの時 true
     */
    public boolean isSexRenderer() {
        return sexRenderer;
    }

    /**
     * レンダラをトグルで切り替える。
     */
    private void switchRendererer() {
        sexRenderer = !sexRenderer;
        Project.setBoolean("sexRenderer", sexRenderer);
        if (pvtTable != null) {
            pvtTableModel.fireTableDataChanged();
        }
    }

    /**
     * 年齢表示をオンオフする。
     */
    public void switchAgeDisplay() {
        if (pvtTable != null) {
            ageDisplay = !ageDisplay;
            Project.setBoolean("ageDisplay", ageDisplay);
            String method = ageDisplay ? AGE_METHOD[0] : AGE_METHOD[1];
            pvtTableModel.setProperty(method, ageColumn);
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
    }

    /**
     * テーブル及び靴アイコンの enable/diable 制御を行う。
     *
     * @param busy pvt 検索中は true
     */
    private void setBusy(final boolean busy) {
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (busy) {
                    view.getKutuBtn().setEnabled(false);
                    if (getContext().getCurrentComponent() == getUI()) {
                        getContext().block();
                        getContext().getProgressBar().setIndeterminate(true);
                    }
                    selectedRow = pvtTable.getSelectedRow();
                } else {
                    view.getKutuBtn().setEnabled(true);
                    if (getContext().getCurrentComponent() == getUI()) {
                        getContext().unblock();
                        getContext().getProgressBar().setIndeterminate(false);
                        getContext().getProgressBar().setValue(0);
                    }
                    pvtTable.getSelectionModel().addSelectionInterval(selectedRow, selectedRow);
                }
            }
        });
    }

    /**
     * 選択されている来院情報を設定返す。
     *
     * @return 選択されている来院情報
     */
    public PatientVisitModel getSelectedPvt() {
        selectedRow = pvtTable.getSelectedRow();
        return sorter.getObject(selectedRow);
    }



    /**
     * カルテオープンメニューを制御する。
     */
    private void controlMenu() {
        PatientVisitModel pvt = getSelectedPvt();
        boolean enabled = canOpen(pvt);
        getContext().enabledAction(GUIConst.ACTION_OPEN_KARTE, enabled);
    }

    private void openKarte() {

        PatientVisitModel pvt = getSelectedPvt();
        if (pvt == null) {
            return;
        }
        getContext().openKarte(pvt);
    }

  /**
     * カルテを開くことが可能かどうかを返す。
     *
     * @return 開くことが可能な時 true
     */
    private boolean canOpen(PatientVisitModel pvt) {
        
        if (pvt == null) {
            return false;
        }
        // Cancelなら開けない
        if (pvt.getStateBit(PatientVisitModel.BIT_CANCEL)) {
            return false;
        }
        // 開いてたら開けない
        if (pvt.getStateBit(PatientVisitModel.BIT_OPEN)) {
            return false;
        }
        return true;
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
                String pop3 = "偶数奇数レンダラを使用する";
                String pop4 = "性別レンダラを使用する";
                String pop5 = "年齢表示";
                String pop6 = "担当分のみ表示";
                String pop7 = "修正送信を注意アイコンにする";

                int row = pvtTable.rowAtPoint(e.getPoint());
                PatientVisitModel obj = getSelectedPvt();
                
                if (row == selectedRow && obj != null && !obj.getStateBit(PatientVisitModel.BIT_CANCEL)) {
                    JMenuItem mi1 = new JMenuItem(new AbstractAction("カルテを開く"){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            openKarte();
                        }
                    });
                    contextMenu.add(mi1);
                    contextMenu.addSeparator();
                    contextMenu.add(new JMenuItem(copyAction));
                    // pvt削除は誰も開いていない場合のみ
                    if (obj.getPatientModel().getOwnerUUID() == null) {
                        JMenuItem mi2 = new JMenuItem(new AbstractAction("受付削除"){

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                removePvt();
                            }
                        });
                        contextMenu.add(mi2);
                    }
                    contextMenu.addSeparator();
                }
                
                // pvt cancelのundo
                if (row == selectedRow && obj != null && obj.getStateBit(PatientVisitModel.BIT_CANCEL)) {
                    JMenuItem mi3 = new JMenuItem(new AbstractAction("キャンセル取消"){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            undoCancelPvt();
                        }
                    });
                    contextMenu.add(mi3);
                    contextMenu.addSeparator();
                }
                JRadioButtonMenuItem oddEven = new JRadioButtonMenuItem(new AbstractAction(pop3){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                       switchRendererer();
                    }
                });
                JRadioButtonMenuItem sex =  new JRadioButtonMenuItem(new AbstractAction(pop4){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                       switchRendererer();
                    }
                });
                ButtonGroup bg = new ButtonGroup();
                bg.add(oddEven);
                bg.add(sex);
                contextMenu.add(oddEven);
                contextMenu.add(sex);
                if (sexRenderer) {
                    sex.setSelected(true);
                } else {
                    oddEven.setSelected(true);
                }
                
                JCheckBoxMenuItem item = new JCheckBoxMenuItem(new AbstractAction(pop5){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switchAgeDisplay();
                    }
                });
                contextMenu.add(item);
                item.setSelected(ageDisplay);

                // 担当分のみ表示: getOrcaId() != nullでメニュー
                if (orcaId != null) {
                    contextMenu.addSeparator();

                    // 担当分のみ表示
                    JCheckBoxMenuItem item2 = new JCheckBoxMenuItem(pop6);
                    contextMenu.add(item2);
                    item2.setSelected(isAssignedOnly());
                    item2.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            boolean now = isAssignedOnly();
                            Project.setBoolean(ASSIGNED_ONLY, !now);
                            filterPatients();
                        }
                    });
                }

                // 修正送信を注意アイコンにする ON/OF default = ON
                JCheckBoxMenuItem item3 = new JCheckBoxMenuItem(pop7);
                contextMenu.add(item3);
                item3.setSelected(Project.getBoolean("change.icon.modify.send", true));
                item3.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        boolean curIcon = Project.getBoolean("change.icon.modify.send", true);
                        boolean change = !curIcon;
                        Project.setBoolean("change.icon.modify.send", change);
                        changeModiSendIcon();
                    }
                });
                
                JMenu menu = columnHelper.createMenuItem();
                contextMenu.add(menu);

                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    /**
     * 修正送信アイコンを決める
     *
     * @param change
     */
    private void changeModiSendIcon() {

        // 修正送信アイコンを決める
        ImageIcon modifySendIcon = Project.getBoolean("change.icon.modify.send", true)
                ? ClientContext.getImageIconAlias("icon_karte_modified_small")
                : ClientContext.getImageIconAlias("icon_sent_claim_small");
        bitAndIconPairs[INDEX_MODIFY_SEND_ICON].setIcon(modifySendIcon);

        // 表示を更新する
        pvtTableModel.fireTableDataChanged();
    }

    /**
     * 選択されている行をコピーする。
     */
    public void copyRow() {

        StringBuilder sb = new StringBuilder();
        int numRows = pvtTable.getSelectedRowCount();
        int[] rowsSelected = pvtTable.getSelectedRows();
        int numColumns = pvtTable.getColumnCount();

        for (int i = 0; i < numRows; i++) {
            if (sorter.getObject(rowsSelected[i]) != null) {
                StringBuilder s = new StringBuilder();
                for (int col = 0; col < numColumns; col++) {
                    Object o = pvtTable.getValueAt(rowsSelected[i], col);
                    if (o != null) {
                        s.append(o.toString());
                    }
                    s.append(",");
                }
                if (s.length() > 0) {
                    s.setLength(s.length() - 1);
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
     * 選択した患者の受付キャンセルをundoする。masuda
     */
    private void undoCancelPvt() {

        final PatientVisitModel pvtModel = getSelectedPvt();

        // ダイアログを表示し確認する
        StringBuilder sb = new StringBuilder(pvtModel.getPatientName());
        sb.append("様の受付キャンセルを取り消しますか?");
        if (!showCancelDialog(sb.toString())) {
            return;
        }
        
        // updateStateする。
        pvtModel.setStateBit(PatientVisitModel.BIT_CANCEL, false);
        cel.publishPvtState(pvtModel);
    }
    
    /**
     * 選択した患者の受付を削除する。masuda
     */
    private void removePvt() {

        final PatientVisitModel pvtModel = getSelectedPvt();

        // ダイアログを表示し確認する
        StringBuilder sb = new StringBuilder(pvtModel.getPatientName());
        sb.append("様の受付を削除しますか?");
        if (!showCancelDialog(sb.toString())) {
            return;
        }

        // publish
        cel.publishPvtDelete(pvtModel);

    }
    
    private boolean showCancelDialog(String msg) {

        final String[] cstOptions = new String[]{"はい", "いいえ"};

        int select = JOptionPane.showOptionDialog(
                SwingUtilities.getWindowAncestor(pvtTable),
                msg,
                ClientContext.getFrameTitle(getName()),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                ClientContext.getImageIconAlias("icon_caution"),
                cstOptions, cstOptions[1]);
        return (select == 0);
    }
    
    /**
     * KarteStateRenderer カルテ（チャート）の状態をレンダリングするクラス。
     */
    private class KarteStateRenderer extends MaleFemaleRenderer {

        private final Border emptyBorder = BorderFactory.createEmptyBorder();

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean isFocused, int row, int col) {

            super.getTableCellRendererComponent(table, value, isSelected, isFocused, row, col);
            setBorder(emptyBorder);
            setHorizontalAlignment(CENTER);
            
            PatientVisitModel pvt = sorter.getObject(row);
            if (pvt == null) {
                return this;
            }
            
            // 選択状態の場合はStripeTableCellRendererの配色を上書きしない
            if (!isSelected) {
                // 病名の状態に応じて背景色を変更 pns
                if (!pvt.getStateBit(PatientVisitModel.BIT_CANCEL)) {
                    // 初診
                    if (pvt.isShoshin()) {
                        setBackground(SHOSHIN_COLOR);
                    }
                    // 病名ついてない
                    if (!pvt.hasByomei()) {
                        setBackground(DIAGNOSIS_EMPTY_COLOR);
                    }
                    // set background to unfinished color
                    if (pvt.getStateBit(PatientVisitModel.BIT_UNFINISHED)) {
                        setBackground(KARTE_EMPTY_COLOR);
                    }
                }
            }

            ImageIcon icon = null;

            // 状態アイコン　ラベル付きbreak文を使ってみる
            bitLoop:
            for (int i = 0; i < bitAndIconPairs.length; ++i) {
                if (!pvt.getStateBit(bitAndIconPairs[i].getBit())) {
                    continue;
                }
                switch (i) {
                    case PatientVisitModel.BIT_OPEN:
                        if (clientUUID.equals(pvt.getPatientModel().getOwnerUUID())) {
                            icon = bitAndIconPairs[i].getIcon();
                        } else {
                            icon = NETWORK_ICON;
                        }
                        break bitLoop;
                    case PatientVisitModel.BIT_SAVE_CLAIM:
                        // SAVE_CLAIMとMODIFY_CLAIMは同時に立っていることがある
                        icon = bitAndIconPairs[i].getIcon();
                        break;
                    default:
                        icon = bitAndIconPairs[i].getIcon();
                        break bitLoop;
                }
            }

            setIcon(icon);
            setText("");

            return this;
        }
    }

    /**
     * KarteStateRenderer カルテ（チャート）の状態をレンダリングするクラス。
     */
    private class MaleFemaleRenderer extends StripeTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean isFocused, int row, int col) {

            super.getTableCellRendererComponent(table, value, isSelected, isFocused, row, col);
            
            PatientVisitModel pvt = sorter.getObject(row);
            if (pvt == null) {
                return this;
            }
            
            if (pvt.getStateBit(PatientVisitModel.BIT_CANCEL)) {
                setForeground(CANCEL_PVT_COLOR);
            } else {
                // 選択状態の場合はStripeTableCellRendererの配色を上書きしない
                String gender = pvt.getPatientModel().getGender();
                if (gender != null && isSexRenderer() && !isSelected) {
                    switch (gender) {
                        case IInfoModel.MALE:
                            setBackground(MALE_COLOR);
                            break;
                        case IInfoModel.FEMALE:
                            setBackground(FEMALE_COLOR);
                            break;
                    }
                }
            }

            return this;
        }
    }

    private class CenterRenderer extends MaleFemaleRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean isFocused, int row, int col) {
            
            super.getTableCellRendererComponent(table, value, isSelected, isFocused, row, col);
            setHorizontalAlignment(CENTER);
            
            return this;
        }
    }

    /**
     * Iconを表示するJComboBox Renderer.
     */
    private class ComboBoxRenderer extends JLabel implements ListCellRenderer {

        public ComboBoxRenderer() {
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList list,
                Object value, int index, boolean isSelected, boolean cellHasFocus) {

            BitAndIconPair pair = (BitAndIconPair) value;

            setIcon(pair.getIcon());
            return this;
        }
    }

    private static class BitAndIconPair {

        private Integer bit;
        private ImageIcon icon;

        public BitAndIconPair(Integer bit, ImageIcon icon) {
            this.bit = bit;
            this.icon = icon;
        }

        public Integer getBit() {
            return bit;
        }

        public ImageIcon getIcon() {
            return icon;
        }
        
        public void setBit(Integer bit) {
            this.bit = bit;
        }
        public void setIcon(ImageIcon icon) {
            this.icon = icon;
        }
    }
    
    // 左下のstatus infoを設定する
    private void setStatusInfo() {

        StringBuilder sb = new StringBuilder();
        sb.append("更新間隔: ");
        sb.append(intervalSync);
        sb.append("秒 ");
        sb.append("同期");
        statusInfo = sb.toString();
    }

    // 更新時間・待ち人数などを設定する
    private void updatePvtInfo() {

        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        String waitingTime = "00:00";
        Date now = new Date();

        StringBuilder sb = new StringBuilder();
        sb.append(timeFormatter.format(now));
        sb.append(" | ");
        sb.append("来院数");
        sb.append(String.valueOf(totalPvtCount));
        sb.append(" 待ち");
        sb.append(String.valueOf(waitingPvtCount));
        sb.append(" 待時間 ");
        if (waitingPvtDate != null && now.after(waitingPvtDate)) {
            waitingTime = DurationFormatUtils.formatPeriod(waitingPvtDate.getTime(), now.getTime(), "HH:mm");
        }
        sb.append(waitingTime);
        final String info = sb.toString();
        
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                view.getPvtInfoLbl().setText(info);
            }
        });
        
    }

//pns^
    /**
     * 来院数，待人数，待時間表示, modified by masuda
     */
    private void countPvt() {

        waitingPvtCount = 0;
        totalPvtCount = 0;
        waitingPvtDate = null;

        List<PatientVisitModel> dataList = pvtTableModel.getDataProvider();

        for (int i = 0; i < dataList.size(); i++) {
            PatientVisitModel pvt = dataList.get(i);
            if (!pvt.getStateBit(PatientVisitModel.BIT_SAVE_CLAIM) && !pvt.getStateBit(PatientVisitModel.BIT_MODIFY_CLAIM)) {
                // 診察未終了レコードをカウント，最初に見つかった未終了レコードの時間から待ち時間を計算
                ++waitingPvtCount;
                if (waitingPvtDate == null) {
                    waitingPvtDate = ModelUtils.getDateTimeAsObject(pvt.getPvtDate());
                }
            }
            if (!pvt.getStateBit(PatientVisitModel.BIT_CANCEL)) {
                ++totalPvtCount;
            }
        }
    }
//pns$
    // 最終行を表示する
    private void showLastRow() {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                int lastRow = pvtTableModel.getObjectCount() - 1;
                pvtTable.scrollRectToVisible(pvtTable.getCellRect(lastRow, 0, true));
            }
        });
    }
    
    private class UpdatePvtInfoTask implements Runnable {

        @Override
        public void run() {
            // 同期時は時刻と患者数を更新するのみ
            updatePvtInfo();
        }
    }
    
    // pvtを全取得する
    private void getFullPvt() {

        SwingWorker worker = new SwingWorker<List<PatientVisitModel>, Void>() {

            @Override
            protected List<PatientVisitModel> doInBackground() throws Exception {
                setBusy(true);
                // サーバーからpvtListを取得する
                return pvtDelegater.getPvtList();
            }

            @Override
            protected void done() {
                try {
                    pvtList = get();

                } catch (InterruptedException | ExecutionException ex) {
                }
                // フィルタリング
                filterPatients();
                // 最終行までスクロール
                showLastRow();
                countPvt();
                updatePvtInfo();
                setBusy(false);
            }
        };
        worker.execute();
    }
    
    // 受付番号を振り、フィルタリングしてtableModelに設定する
    private void filterPatients() {

        List<PatientVisitModel> list;
        
        if (isAssignedOnly()) {
            list = new ArrayList<>();
            for (PatientVisitModel pvt : pvtList) {
                String doctorId = pvt.getDoctorId();
                if (doctorId == null || doctorId.equals(orcaId) || doctorId.equals(UN_ASSIGNED_ID)) {
                    list.add(pvt);
                }
            }
        } else {
            list = new ArrayList<>(pvtList);
        }
        
        for (int i = 0; i < list.size(); ++i) {
            PatientVisitModel pvt = list.get(i);
            pvt.setNumber(i + 1);
        }
        pvtTableModel.setDataProvider(list);
        //pvtTable.repaint();
    }
    
    private boolean isAssignedOnly() {
        return Project.getBoolean(ASSIGNED_ONLY, false);
    }

    // ChartEventListener
    // 待合リストを更新する
    @Override
    public void onEvent(ChartEventModel evt) {

        ChartEventModel.EVENT eventType = evt.getEventType();
        List<PatientVisitModel> tableDataList = pvtTableModel.getDataProvider();

        switch (eventType) {
            case PVT_ADD:
                PatientVisitModel model = evt.getPatientVisitModel();
                // pvtListに追加
                pvtList.add(model);
                // 担当でないならばテーブルに追加しない
                if (isAssignedOnly()) {
                    String doctorId = model.getDoctorId();
                    if (doctorId != null && !doctorId.equals(orcaId) && !doctorId.equals(UN_ASSIGNED_ID)) {
                        break;
                    }
                }
                int sRow = selectedRow;
                // 番号を振る
                int num = tableDataList.size() + 1;
                model.setNumber(num);
                // テーブルに追加
                pvtTableModel.addObject(model);
                
                // 選択中の行を保存
                // 保存した選択中の行を選択状態にする
                pvtTable.getSelectionModel().addSelectionInterval(sRow, sRow);
                // 追加した行は見えるようにスクロールする
                showLastRow();
                break;
                
            case PVT_STATE:
                // pvtListを更新
                for (PatientVisitModel pvt : pvtList) {
                    if (pvt.getId() == evt.getPvtPk()) {
                        // 更新する
                        pvt.setState(evt.getState());
                        pvt.setByomeiCount(evt.getByomeiCount());
                        pvt.setByomeiCountToday(evt.getByomeiCountToday());
                        pvt.setMemo(evt.getMemo());
                    }
                    if (pvt.getPatientModel().getId() == evt.getPtPk()) {
                        String ownerUUID = evt.getOwnerUUID();
                        pvt.setStateBit(PatientVisitModel.BIT_OPEN, ownerUUID != null);
                        pvt.getPatientModel().setOwnerUUID(evt.getOwnerUUID());
                    }
                }
                for (int row = 0; row < tableDataList.size(); ++row) {
                    PatientVisitModel pvt = tableDataList.get(row);
                    if (pvt.getId() == evt.getPvtPk() 
                            || pvt.getPatientModel().getId() == evt.getPtPk()) {
                        pvtTableModel.fireTableRowsUpdated(row, row);
                    }
                }
                break;
            case PVT_DELETE:
                // pvtListから削除
                PatientVisitModel toRemove = null;
                for (PatientVisitModel pvt : pvtList) {
                    if (evt.getPvtPk() == pvt.getId()) {
                        toRemove = pvt;
                        break;
                    }
                }
                if (toRemove != null) {
                    pvtList.remove(toRemove);
                }
                
                // 該当するpvtを削除し受付番号を振りなおす
                int counter = 0;
                toRemove = null;
                for (PatientVisitModel pm : tableDataList) {
                    if (pm.getId() == evt.getPvtPk()) {
                        toRemove = pm;
                    } else {
                        pm.setNumber(++counter);
                    }
                }
                if (toRemove != null) {
                    pvtTableModel.delete(toRemove);
                }
                break;
                
            case PVT_RENEW:
                // 日付が変わるとCMD_RENEWが送信される。pvtListをサーバーから取得する
                getFullPvt();
                break;
                
            case PVT_MERGE:
                // 同じ時刻のPVTで、PVTには追加されず、患者情報や保険情報の更新のみの場合
                // pvtListに変更
                PatientVisitModel toMerge = evt.getPatientVisitModel();
                for (int i = 0; i < pvtList.size(); ++i) {
                    PatientVisitModel pvt = pvtList.get(i);
                    if (pvt.getId() == evt.getPvtPk()) {
                        // 受付番号を継承
                        num = pvt.getNumber();
                        toMerge.setNumber(num);
                        pvtList.set(i, toMerge);
                    }
                }
                // tableModelに変更
                for (int row = 0; row < tableDataList.size(); ++row) {
                    PatientVisitModel pvt = tableDataList.get(row);
                    if (pvt.getId() == evt.getPvtPk()) {
                        // 選択中の行を保存
                        sRow = selectedRow;
                        pvtTableModel.setObject(row, toMerge);
                        // 保存した選択中の行を選択状態にする
                        pvtTable.getSelectionModel().addSelectionInterval(sRow, sRow);
                        break;
                    }
                }
                break;
                
            case PM_MERGE:
                // 患者モデルに変更があった場合
                // pvtListに変更
                PatientModel pm = evt.getPatientModel();
                long pk = pm.getId();
                for (PatientVisitModel pvt : pvtList) {
                    if (pvt.getPatientModel().getId() == pk) {
                        pvt.setPatientModel(pm);
                    }
                }
                break;
        }
        
        // PvtInfoを更新する
        countPvt();
        updatePvtInfo();
    }
}