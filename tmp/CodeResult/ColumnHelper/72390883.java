package open.dolphin.impl.admission;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import open.dolphin.client.AbstractMainComponent;
import open.dolphin.client.ChartEventListener;
import open.dolphin.client.GUIConst;
import open.dolphin.dao.SqlMiscDao;
import open.dolphin.delegater.PatientDelegater;
import open.dolphin.infomodel.AdmissionModel;
import open.dolphin.infomodel.ChartEventModel;
import open.dolphin.infomodel.PatientModel;
import open.dolphin.infomodel.PatientVisitModel;
import open.dolphin.table.ColumnSpecHelper;
import open.dolphin.table.ListTableModel;
import open.dolphin.table.ListTableSorter;
import open.dolphin.table.StripeTableCellRenderer;

/**
 * 入院患者リスト
 * @author masuda, Masuda Naika
 */
public class AdmissionList extends AbstractMainComponent {
    
    // Window Title
    private static final String NAME = "入院リスト";

    // 来院テーブルのカラム名
    private static final String[] COLUMN_NAMES = {
        "部屋", "患者ID",  "氏   名", "性別",  "年齢", 
        "担当医", "診療科", "入院日", "状態"};
    // 来院テーブルのカラムメソッド
    private static final String[] PROPERTY_NAMES = {
        "getRoom", "getPatientId", "getFullName", "getGenderDesc", "getAge", 
        "getDoctorName", "getDeptName", "getAdmissionDate", "isOpened"};
    // 来院テーブルのクラス名
    private static final Class[] COLUMN_CLASSES = {
        String.class, String.class, String.class, String.class, String.class, 
        String.class, String.class, String.class, Object.class};
    // 来院テーブルのカラム幅
    private static final int[] COLUMN_WIDTH = {
        30, 40, 100, 20, 40, 80, 50, 80, 20};
    
    // Status　情報　メインウィンドウの左下に表示される内容
    private final String statusInfo;
    private static final String INFO_MSG = "入院カルテはここから作成";
    private static final String CLICK_BTN_MSG = "左のボタンをクリックして入院患者リストを取得してください。";
    
    // カラム仕様名
    private static final String COLUMN_SPEC_NAME = "admissionTable.column.spec";
    // カラム仕様ヘルパー
    private ColumnSpecHelper columnHelper;
    
    // View panel
    private AdmissionListView view;
    // Table
    private JTable table;
    // Table Model
    private ListTableModel<PatientModel> tableModel;
    // TableSorter
    private ListTableSorter<PatientModel> sorter;

    private int stateColumn;
    private int genderColumn;
    
    // 選択されている行を保存
    private int selectedRow;
    
    private Action openKarteAction;
    private Action copyAction;
    
    private final String clientUUID;
    private final ChartEventListener cel;
    
    
    public AdmissionList() {
        setName(NAME);
        cel = ChartEventListener.getInstance();
        clientUUID = cel.getClientUUID();
        statusInfo = INFO_MSG;
    }

    @Override
    public void start() {
        setup();
        initComponents();
        connect();
        enter();
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
    /**
     * メインウインドウのタブで受付リストに切り替わった時 コールされる。
     */
    @Override
    public void enter() {
        controlMenu();
        getContext().getStatusLabel().setText(statusInfo);
    }
    
    private void setup() {
        
        // ColumnSpecHelperを準備する
        columnHelper = new ColumnSpecHelper(COLUMN_SPEC_NAME,
                COLUMN_NAMES, PROPERTY_NAMES, COLUMN_CLASSES, COLUMN_WIDTH);
        columnHelper.loadProperty();

        // Scan して state カラムを設定する
        stateColumn = columnHelper.getColumnPosition("isOpened");
        genderColumn = columnHelper.getColumnPosition("genderDesc");
    }
    
    /**
     * GUI コンポーネントを初期化しレアイアウトする。
     */
    private void initComponents() {
        
        // View クラスを生成しこのプラグインの UI とする
        view = new AdmissionListView();
        setUI(view);

        view.getInfoLbl().setText("");
        table = view.getTable();
        
        // ColumnSpecHelperにテーブルを設定する
        columnHelper.setTable(table);

        //------------------------------------------
        // View のテーブルモデルを置き換える
        //------------------------------------------
        String[] columnNames = columnHelper.getTableModelColumnNames();
        String[] methods = columnHelper.getTableModelColumnMethods();
        Class[] cls = columnHelper.getTableModelColumnClasses();
        
        tableModel = new ListTableModel<PatientModel>(columnNames, 1, methods, cls) {

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        // sorter組み込み
        sorter = new ListTableSorter(tableModel);
        table.setModel(sorter);
        sorter.setTableHeader(table.getTableHeader());
        
        // 選択モード
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // 連ドラ、梅ちゃん先生
        PatientListTableRenderer renderer = new PatientListTableRenderer();
        renderer.setTable(table);
        renderer.setDefaultRenderer();

        // カラム幅更新
        columnHelper.updateColumnWidth();
        
        view.getInfoLbl().setText(CLICK_BTN_MSG);
    }

    
    private void connect() {
        
        // ColumnHelperでカラム変更関連イベントを設定する
        columnHelper.connect();
        // ChartEventListenerに登録する
        cel.addListener(this);
        
        // 来院リストテーブル 選択
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedRow = table.getSelectedRow();
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
        view.getUpdateBtn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                renewList();
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
        table.getInputMap().put(copy, "Copy");
        table.getActionMap().put("Copy", copyAction);
        
        // カルテオープンアクションを設定
        openKarteAction = new AbstractAction("カルテを開く") {

            @Override
            public void actionPerformed(ActionEvent e) {
                openKarte();
            }
        };
        
        // Enterでカルテオープン Katoh@Hashimoto-iin
        final String optionMapKey = "openKarte";
        final KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, optionMapKey);
        table.getActionMap().put(optionMapKey, openKarteAction);
    }
    
    /**
     * 選択されている行をコピーする。
     */
    public void copyRow() {

        StringBuilder sb = new StringBuilder();
        int numRows = table.getSelectedRowCount();
        int[] rowsSelected = table.getSelectedRows();
        int numColumns = table.getColumnCount();

        for (int i = 0; i < numRows; i++) {
            if (sorter.getObject(rowsSelected[i]) != null) {
                StringBuilder s = new StringBuilder();
                for (int col = 0; col < numColumns; col++) {
                    Object o = table.getValueAt(rowsSelected[i], col);
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

    private void controlMenu() {
        PatientModel pm = getSelectedPatient();
        boolean enabled = pm != null ? !pm.isOpened() : false;
        getContext().enabledAction(GUIConst.ACTION_OPEN_KARTE, enabled);
    }
    
    private void openKarte() {
        
        PatientModel patient = getSelectedPatient();
        PatientVisitModel pvt = ChartEventListener.getInstance().createFakePvt(patient);
        
        // カルテコンテナを生成する
        getContext().openKarte(pvt);
    }
    
    private void renewList() {
        
        SwingWorker worker = new SwingWorker<List<PatientModel>, Void>() {

            @Override
            protected List<PatientModel> doInBackground() throws Exception {
                
                setBusy(true);
                
                // ORCAに入院患者を問い合わせる
                Date today = new Date();
                List<AdmissionModel> amList = SqlMiscDao.getInstance().getInHospitalPatients(today);
                
                // いったんHashMapに登録
                Map<String, AdmissionModel> amMap = new HashMap<>();
                for (AdmissionModel am : amList) {
                    amMap.put(am.getPatientId(), am);
                }
                
                // 対応するPatientModelを取得する
                Set<String> ids = amMap.keySet();
                List<PatientModel> pmList = PatientDelegater.getInstance().getPatientList(ids);
                
                // AdmissionModelをセットする
                for (PatientModel pm : pmList) {
                    AdmissionModel am = amMap.get(pm.getPatientId());
                    pm.setAdmissionModel(am);
                }
                // 部屋番号でソート
                Collections.sort(pmList, new RoomNumberComparator());
                
                return pmList;
            }

            @Override
            protected void done() {
                try {
                    List<PatientModel> list = get();
                    tableModel.setDataProvider(list);
                } catch (InterruptedException | ExecutionException ex) {
                }

                updateInfo();
                setBusy(false);
            }
        };
        worker.execute();
    }
    
    private static class RoomNumberComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            PatientModel p1 = (PatientModel) o1;
            PatientModel p2 = (PatientModel) o2;
            return p1.getRoom().compareTo(p2.getRoom());
        }
        
    }

    private void updateInfo() {
        
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int num = (tableModel.getDataProvider() != null)
                ? tableModel.getDataProvider().size()
                : 0;
        StringBuilder sb = new StringBuilder();
        sb.append(frmt.format(new Date()));
        sb.append(" 入院患者数：");
        sb.append(String.valueOf(num));
        sb.append(" 人");
        view.getInfoLbl().setText(sb.toString());
    }
    
    
    private PatientModel getSelectedPatient() {
        selectedRow = table.getSelectedRow();
        return sorter.getObject(selectedRow);
    }
    
    /**
     * テーブル及びアイコンの enable/diable 制御を行う。
     *
     * @param busy pvt 検索中は true
     */
    private void setBusy(final boolean busy) {
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (busy) {
                    view.getUpdateBtn().setEnabled(false);
                    if (getContext().getCurrentComponent() == getUI()) {
                        getContext().block();
                        getContext().getProgressBar().setIndeterminate(true);
                    }
                    selectedRow = table.getSelectedRow();
                } else {
                    view.getUpdateBtn().setEnabled(true);
                    if (getContext().getCurrentComponent() == getUI()) {
                        getContext().unblock();
                        getContext().getProgressBar().setIndeterminate(false);
                        getContext().getProgressBar().setValue(0);
                    }
                    table.getSelectionModel().addSelectionInterval(selectedRow, selectedRow);
                }
            }
        });
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

                int row = table.rowAtPoint(e.getPoint());
                PatientModel pm = getSelectedPatient();
                
                if (row == selectedRow && pm != null && !pm.isOpened()) {
                    contextMenu.add(new JMenuItem(openKarteAction));
                    contextMenu.addSeparator();
                }

                // 表示カラム設定
                JMenu menu = columnHelper.createMenuItem();
                contextMenu.add(menu);

                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
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
}
