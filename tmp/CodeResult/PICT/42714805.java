/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AsuransiDAO;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import model.AsuransiModel;
import model.Dtl_KerjasamaModel;
import model.Hdr_KerjasamaModel;
import model.PerusahaanModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author user
 */
public class KerjaSama extends javax.swing.JFrame {
private DefaultTableModel tbl;
dao.AsuransiDAO dao = new AsuransiDAO();
int idKerjaSama;
Dtl_KerjasamaModel dtl_kerjasama;
PerusahaanModel pers;
AsuransiModel as;
Hdr_KerjasamaModel hdr_kerjasama;
Dimension uk = new Dimension();
   
    public KerjaSama() {
         try{
           UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initComponents();
    //    setLocationRelativeTo(this);
        if(TglMulai.getDate() == null){
           Calendar cal = Calendar.getInstance();
           String tgl = "dd MMMM yyyy";
           SimpleDateFormat sdf = new SimpleDateFormat(tgl);
           TglMulai.setDate(cal.getTime());
           TglSelesai.setDate(cal.getTime());
           TglMulaiDtl.setDate(cal.getTime());
           TglSelesaiDtl.setDate(cal.getTime());
           TxtId.setEnabled(false);
           TxtIdDtl.setEnabled(false);
           TxtAsuransi.setVisible(false);
           TxtIdPers.setVisible(false);
          // KlsParameter.code.kosongText(Hdr);
           if(KlsParameter.kelas.getTambahDtl().equals(false)){
                if(KlsParameter.kelas.getTambahHdr().equals(false)){
                    // Ubah Detil
                    TxtId.setText(KlsParameter.kelas.getIdHdr());
                    TxtIdDtl.setText(KlsParameter.kelas.getIdDtl());
                    cariId();
                    cariIdDtl();
                    KlsParameter.code.enableText(Hdr, false);
                    TglMulai.setEnabled(false);
                    TglSelesai.setEnabled(false);
                    BtnSimpan.setEnabled(false);
                    BtnSimpanDtl.setEnabled(true);
                    BtnCariAsuransi.setEnabled(false);
                    BtnTambahHdr.setEnabled(false);
                    TxtSyarat.setEnabled(false);
                    this.setTitle("Kerja Sama Perusahaan ");
                    ChkAktif.setEnabled(false);
       
                }
                else{
                   //Ubah Hdr
                   TxtId.setText(KlsParameter.kelas.getIdHdr());
                   cariId();
                   KlsParameter.code.kosongText(Dtl);
                   KlsParameter.code.kosongCheckbox(Dtl);
                   KlsParameter.code.enableText(Dtl, false);
                   KlsParameter.code.enableCheckbox(Dtl, false);
                   Dtl.setVisible(false);
                   this.setResizable(true);
                   this.setPreferredSize(uk);
                   this.setSize(780, 250);
                   TglMulaiDtl.setEnabled(false);
                   TglSelesaiDtl.setEnabled(false);
                   BtnSimpanDtl.setEnabled(false);
                   BtnCariPers.setEnabled(false);
                   BtnTambahDtl.setEnabled(false);
                   TxtSyaratDtl.setEnabled(false);
                   this.setTitle("Kerja Sama Asuransi");
                   ChkAktif.setEnabled(true);
                }
           }else{
               // Tambah Detil
               if(KlsParameter.kelas.getTambahHdr().equals(false)){
                    TxtId.setText(KlsParameter.kelas.getIdHdr());
                    cariId();
                    KlsParameter.code.enableText(Hdr, false);
                    //KlsParameter.code.enableCheckbox(Dtl, false);
                    TglMulai.setEnabled(false);
                    TglSelesai.setEnabled(false);
                    BtnSimpan.setEnabled(false);
                    BtnCariAsuransi.setEnabled(false);
                    BtnTambahHdr.setEnabled(false);
                    TxtSyarat.setEnabled(false);
                    this.setTitle("Kerja Sama Perusahaan ");
                    ChkAktif.setEnabled(false);
               }else {
                   // Tambah Header
                   KlsParameter.code.kosongText(Hdr);
                   KlsParameter.code.kosongText(Dtl);
                   KlsParameter.code.kosongCheckbox(Hdr);
                   KlsParameter.code.kosongCheckbox(Dtl);
                   KlsParameter.code.visibleCheckBox(Dtl, false);
                   KlsParameter.code.visibleLabel(Dtl, false);
                   KlsParameter.code.visibleText(Dtl, false);
                   Dtl.setVisible(false);
                   BtnSimpanDtl.setEnabled(false);
                   this.setSize(800,270);
                   TglMulaiDtl.setEnabled(false);
                   TglSelesaiDtl.setEnabled(false);
                   BtnCariPers.setEnabled(false);
                   BtnTambahDtl.setEnabled(false);
                   TxtSyaratDtl.setEnabled(false);
                   this.setTitle("Kerja Sama Asuransi");
                   ChkAktif.setEnabled(true);
               }
           }
           }
        setLocationRelativeTo(this);
        lblUser.setText(KlsParameter.kelas.getUser());
        }
    
                              
    void cariId(){ 
    SessionFactory sess = HibernateUtil.getSessionFactory();
    Session session = sess.openSession();
    hdr_kerjasama = takeId(Integer.parseInt(TxtId.getText().trim()));
    as = (AsuransiModel)session.load(AsuransiModel.class, hdr_kerjasama.getAsuransiModel().getId_asuransi());
    setId();
    }
    void setId(){
    TxtAsuransi.setText(Integer.toString(as.getId_asuransi()));
    TxtnamaAsuransi.setText(as.getKd_asuransi());
    TxtNmAsuransi.setText(as.getNama());
    TxtKeterangan.setText(hdr_kerjasama.getKeterangan());
    TglMulai.setDate(hdr_kerjasama.getTglMulai());
    TglSelesai.setDate(hdr_kerjasama.getTglSelesai());
    ChkAktif.setSelected(hdr_kerjasama.getAktif().equals(1) ? true : false);
    TxtSyarat.setText(hdr_kerjasama.getSyarat().trim());
        
    }
    public  Hdr_KerjasamaModel takeId(int x){
     SessionFactory sess = HibernateUtil.getSessionFactory();
     Session session = sess.openSession();
     return (Hdr_KerjasamaModel) session.load(Hdr_KerjasamaModel.class, x);
     
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Hdr = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TxtId = new javax.swing.JTextField();
        TxtAsuransi = new javax.swing.JTextField();
        TxtKeterangan = new javax.swing.JTextField();
        TglMulai = new com.toedter.calendar.JDateChooser();
        TglSelesai = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        BtnCariAsuransi = new javax.swing.JButton();
        TxtnamaAsuransi = new javax.swing.JTextField();
        BtnSimpan = new javax.swing.JButton();
        BtnTambahHdr = new javax.swing.JButton();
        ChkAktif = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TxtSyarat = new javax.swing.JTextArea();
        TxtNmAsuransi = new javax.swing.JTextField();
        BtnPrevHdr = new javax.swing.JButton();
        BtnNextHdr = new javax.swing.JButton();
        Dtl = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        TxtIdDtl = new javax.swing.JTextField();
        TxtIdPers = new javax.swing.JTextField();
        TxtExcess = new javax.swing.JTextField();
        TxtVit = new javax.swing.JTextField();
        TxtSupplemen = new javax.swing.JTextField();
        TxtRI = new javax.swing.JTextField();
        TxtRJ = new javax.swing.JTextField();
        TxtMelahirkan = new javax.swing.JTextField();
        TxtRG = new javax.swing.JTextField();
        TxtMaternity = new javax.swing.JTextField();
        TxtMCU = new javax.swing.JTextField();
        TxtKet = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        chkExcess = new javax.swing.JCheckBox();
        chkVitamin = new javax.swing.JCheckBox();
        chkSupplemen = new javax.swing.JCheckBox();
        chkRI = new javax.swing.JCheckBox();
        chkRJ = new javax.swing.JCheckBox();
        chkMelahirkan = new javax.swing.JCheckBox();
        chkRG = new javax.swing.JCheckBox();
        chkMaternity = new javax.swing.JCheckBox();
        chkMCU = new javax.swing.JCheckBox();
        BtnCariPers = new javax.swing.JButton();
        TxtPerusahaan = new javax.swing.JTextField();
        BtnSimpanDtl = new javax.swing.JButton();
        TglMulaiDtl = new com.toedter.calendar.JDateChooser();
        BtnTambahDtl = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtSyaratDtl = new javax.swing.JTextArea();
        chkAktifDtl = new javax.swing.JCheckBox();
        TglSelesaiDtl = new com.toedter.calendar.JDateChooser();
        TxtNmPers = new javax.swing.JTextField();
        BtnNextDetil = new javax.swing.JButton();
        BtnPrevDetil = new javax.swing.JButton();
        lblUser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Hdr.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("Id Kerjasama");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel2.setText("Asuransi");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("Keterangan");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setText("Tgl Mulai");

        TxtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdActionPerformed(evt);
            }
        });

        TglMulai.setDateFormatString("dd MMMM yyyy");
        TglMulai.setPreferredSize(new java.awt.Dimension(101, 23));

        TglSelesai.setDateFormatString("dd MMMM yyyy");
        TglSelesai.setPreferredSize(new java.awt.Dimension(101, 23));

        jLabel5.setText("s/d");

        BtnCariAsuransi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Magnifier-icon.png"))); // NOI18N
        BtnCariAsuransi.setText("Cari");
        BtnCariAsuransi.setPreferredSize(new java.awt.Dimension(81, 32));
        BtnCariAsuransi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariAsuransiActionPerformed(evt);
            }
        });

        BtnSimpan.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/floppy-copy-icon.png"))); // NOI18N
        BtnSimpan.setText("Simpan");
        BtnSimpan.setPreferredSize(new java.awt.Dimension(95, 32));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });

        BtnTambahHdr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnTambahHdr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Actions-list-add-icon.png"))); // NOI18N
        BtnTambahHdr.setText("Tambah");
        BtnTambahHdr.setPreferredSize(new java.awt.Dimension(99, 32));
        BtnTambahHdr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahHdrActionPerformed(evt);
            }
        });

        ChkAktif.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        ChkAktif.setText("Aktif");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel20.setText("Syarat");

        TxtSyarat.setColumns(20);
        TxtSyarat.setRows(5);
        jScrollPane1.setViewportView(TxtSyarat);

        BtnPrevHdr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnPrevHdr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Actions-arrow-left-fast-rewind-icon.png"))); // NOI18N
        BtnPrevHdr.setMinimumSize(new java.awt.Dimension(95, 30));
        BtnPrevHdr.setPreferredSize(new java.awt.Dimension(95, 32));
        BtnPrevHdr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrevHdrActionPerformed(evt);
            }
        });

        BtnNextHdr.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnNextHdr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Actions-arrow-right-fast-forward-icon.png"))); // NOI18N
        BtnNextHdr.setPreferredSize(new java.awt.Dimension(99, 32));
        BtnNextHdr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNextHdrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HdrLayout = new javax.swing.GroupLayout(Hdr);
        Hdr.setLayout(HdrLayout);
        HdrLayout.setHorizontalGroup(
            HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HdrLayout.createSequentialGroup()
                .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel20))
                .addGap(25, 25, 25)
                .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HdrLayout.createSequentialGroup()
                        .addComponent(TglMulai, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TglSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HdrLayout.createSequentialGroup()
                        .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HdrLayout.createSequentialGroup()
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnTambahHdr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(ChkAktif)
                                .addGap(42, 42, 42)
                                .addComponent(BtnPrevHdr, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnNextHdr, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(HdrLayout.createSequentialGroup()
                                .addComponent(TxtnamaAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtNmAsuransi, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnCariAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TxtKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE))
                .addContainerGap())
        );
        HdrLayout.setVerticalGroup(
            HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HdrLayout.createSequentialGroup()
                .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(HdrLayout.createSequentialGroup()
                        .addComponent(BtnCariAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(HdrLayout.createSequentialGroup()
                        .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnTambahHdr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ChkAktif))
                            .addComponent(BtnPrevHdr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnNextHdr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(TxtnamaAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtNmAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtAsuransi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)))
                .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(TglSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(HdrLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel5))
                        .addComponent(TglMulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HdrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        Dtl.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel6.setText("Id Kerjasama Detil");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("Perusahaan");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel8.setText("Excess");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel9.setText("Vitamin");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel10.setText("Supplemen");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel11.setText("Rawat Inap");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel12.setText("Rawat Jalan");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel13.setText("Melahirkan");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel14.setText("Rawat Gigi");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel15.setText("Maternity");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel16.setText("MCU");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel17.setText("Keterangan");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel18.setText("Tgl Mulai");

        TxtIdDtl.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        TxtIdDtl.setPreferredSize(new java.awt.Dimension(0, 20));

        TxtExcess.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtVit.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtSupplemen.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtRI.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtRJ.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtMelahirkan.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtRG.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtMaternity.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtMCU.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        TxtKet.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jLabel19.setText("s/d");
        jLabel19.setPreferredSize(null);

        chkExcess.setText("Ya");
        chkExcess.setPreferredSize(new java.awt.Dimension(37, 17));

        chkVitamin.setText("Ya");
        chkVitamin.setPreferredSize(new java.awt.Dimension(37, 17));

        chkSupplemen.setText("Ya");
        chkSupplemen.setPreferredSize(new java.awt.Dimension(37, 17));

        chkRI.setText("Ya");
        chkRI.setPreferredSize(new java.awt.Dimension(37, 17));

        chkRJ.setText("Ya");
        chkRJ.setPreferredSize(new java.awt.Dimension(37, 17));

        chkMelahirkan.setText("Ya");
        chkMelahirkan.setPreferredSize(new java.awt.Dimension(37, 17));

        chkRG.setText("Ya");
        chkRG.setPreferredSize(new java.awt.Dimension(37, 17));

        chkMaternity.setText("Ya");
        chkMaternity.setPreferredSize(new java.awt.Dimension(37, 17));

        chkMCU.setText("Ya");
        chkMCU.setPreferredSize(new java.awt.Dimension(37, 17));

        BtnCariPers.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnCariPers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Magnifier-icon.png"))); // NOI18N
        BtnCariPers.setText("Cari");
        BtnCariPers.setPreferredSize(new java.awt.Dimension(81, 32));
        BtnCariPers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPersActionPerformed(evt);
            }
        });

        TxtPerusahaan.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        BtnSimpanDtl.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnSimpanDtl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/floppy-copy-icon.png"))); // NOI18N
        BtnSimpanDtl.setText("Simpan");
        BtnSimpanDtl.setMinimumSize(new java.awt.Dimension(95, 30));
        BtnSimpanDtl.setPreferredSize(new java.awt.Dimension(95, 32));
        BtnSimpanDtl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanDtlActionPerformed(evt);
            }
        });

        TglMulaiDtl.setDateFormatString("dd MMMM yyyy");
        TglMulaiDtl.setPreferredSize(new java.awt.Dimension(0, 23));

        BtnTambahDtl.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnTambahDtl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Actions-list-add-icon.png"))); // NOI18N
        BtnTambahDtl.setText("Tambah");
        BtnTambahDtl.setPreferredSize(new java.awt.Dimension(99, 32));
        BtnTambahDtl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahDtlActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel21.setText("Syarat");

        TxtSyaratDtl.setColumns(20);
        TxtSyaratDtl.setRows(5);
        jScrollPane2.setViewportView(TxtSyaratDtl);

        chkAktifDtl.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        chkAktifDtl.setText("Aktif");

        TglSelesaiDtl.setDateFormatString("dd MMMM yyyy");
        TglSelesaiDtl.setPreferredSize(new java.awt.Dimension(0, 23));

        BtnNextDetil.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnNextDetil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Actions-arrow-right-fast-forward-icon.png"))); // NOI18N
        BtnNextDetil.setPreferredSize(new java.awt.Dimension(99, 32));
        BtnNextDetil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNextDetilActionPerformed(evt);
            }
        });

        BtnPrevDetil.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        BtnPrevDetil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Actions-arrow-left-fast-rewind-icon.png"))); // NOI18N
        BtnPrevDetil.setMinimumSize(new java.awt.Dimension(95, 30));
        BtnPrevDetil.setPreferredSize(new java.awt.Dimension(95, 32));
        BtnPrevDetil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrevDetilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DtlLayout = new javax.swing.GroupLayout(Dtl);
        Dtl.setLayout(DtlLayout);
        DtlLayout.setHorizontalGroup(
            DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DtlLayout.createSequentialGroup()
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DtlLayout.createSequentialGroup()
                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel21)
                            .addComponent(jLabel8))
                        .addGap(60, 60, 60)
                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
                            .addGroup(DtlLayout.createSequentialGroup()
                                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(chkMCU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chkMaternity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chkRG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chkMelahirkan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chkRJ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chkRI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chkSupplemen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chkVitamin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtSupplemen, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                                    .addComponent(TxtRI, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                                    .addComponent(TxtRJ, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                                    .addComponent(TxtMelahirkan, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                                    .addComponent(TxtMaternity, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                                    .addComponent(TxtRG, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                                    .addComponent(TxtMCU, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                                    .addComponent(TxtVit, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)))
                            .addComponent(TxtKet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DtlLayout.createSequentialGroup()
                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel18)
                            .addComponent(jLabel6))
                        .addGap(34, 34, 34)
                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DtlLayout.createSequentialGroup()
                                .addComponent(chkExcess, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(TxtExcess, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE))
                            .addGroup(DtlLayout.createSequentialGroup()
                                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DtlLayout.createSequentialGroup()
                                        .addComponent(TglMulaiDtl, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TglSelesaiDtl, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DtlLayout.createSequentialGroup()
                                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TxtIdDtl, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                                            .addComponent(TxtPerusahaan))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(DtlLayout.createSequentialGroup()
                                                .addComponent(BtnSimpanDtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(BtnTambahDtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(chkAktifDtl)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                                                .addComponent(BtnPrevDetil, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(BtnNextDetil, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(126, 126, 126))
                                            .addComponent(TxtNmPers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TxtIdPers, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(17, 17, 17)))
                                .addComponent(BtnCariPers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(24, 24, 24))
        );
        DtlLayout.setVerticalGroup(
            DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DtlLayout.createSequentialGroup()
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DtlLayout.createSequentialGroup()
                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(TxtIdDtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnSimpanDtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnTambahDtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chkAktifDtl)
                                .addComponent(BtnPrevDetil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BtnNextDetil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtIdPers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(TxtPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TxtNmPers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DtlLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel18))
                            .addComponent(TglSelesaiDtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TglMulaiDtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DtlLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(BtnCariPers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(chkExcess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtExcess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TxtVit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkVitamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TxtSupplemen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkSupplemen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkRI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtRI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkRJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtRJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkMelahirkan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtMelahirkan, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtRG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkMaternity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtMaternity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkMCU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtMCU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DtlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("jLabel20");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Dtl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Hdr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(Hdr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Dtl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUser)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       SessionFactory sess = HibernateUtil.getSessionFactory();
       Session session = sess.openSession();
       
       String tgl1, tgl2;
       String tanggal = "yyyy-MM-dd";
       
       SimpleDateFormat sdf = new SimpleDateFormat(tanggal);
       tgl1 = sdf.format(TglMulai.getDate());
       tgl2 = sdf.format(TglSelesai.getDate());
       Hdr_KerjasamaModel hdr = new Hdr_KerjasamaModel();
       if (TxtId.getText().equals("")){    
       }else{
           hdr.setId_kerjasama(Integer.parseInt(TxtId.getText().trim()));
           
       }
       AsuransiModel as = (AsuransiModel) session.load(AsuransiModel.class, Integer.parseInt(TxtAsuransi.getText().trim()));
       hdr.setAsuransiModel(as);
       
       hdr.setKeterangan(TxtKeterangan.getText().trim());
       hdr.setTglMulai(TglMulai.getDate());
       hdr.setTglSelesai(TglSelesai.getDate());
       hdr.setUsrUpdate("tes");
       hdr.setTglUpdate(new Date(System.currentTimeMillis()));
       hdr.setAktif(ChkAktif.isSelected() ? 1 : 0 );
       hdr.setSyarat(TxtSyarat.getText().trim());
       Transaction t = session.beginTransaction();
       session.saveOrUpdate(hdr);
       t.commit();
       TxtId.setText(Integer.toString(hdr.getId_kerjasama()));
       session.close();
     
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanDtlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanDtlActionPerformed
      Calendar cal = Calendar.getInstance();
      String tgl1, tgl2;
      String tanggal = "yyyy-MM-dd";
      SimpleDateFormat sdf = new SimpleDateFormat(tanggal);
      SessionFactory sess = HibernateUtil.getSessionFactory();
      Session session = sess.openSession();
      Dtl_KerjasamaModel dtl = new Dtl_KerjasamaModel();
      if(TxtIdDtl.getText().equals("")){
          
      }
      else {
          dtl.setId_kerjaSamaDetil(Integer.parseInt(TxtIdDtl.getText().trim()));
      }
      PerusahaanModel pers = (PerusahaanModel) session.load(PerusahaanModel.class, Integer.parseInt(TxtIdPers.getText().trim()));
      dtl.setPerusahaanModel(pers);
      dtl.setTglMulai(TglMulaiDtl.getDate());
      dtl.setTglSelesai(TglSelesaiDtl.getDate());
      if(chkExcess.isSelected()){dtl.setExcess_b(1);}else{dtl.setExcess_b(0);}
      dtl.setExcess_s(TxtExcess.getText().trim());
      if(chkVitamin.isSelected()){dtl.setVitamin_b(1);}else{dtl.setVitamin_b(0);}
      dtl.setVitamin_s(TxtVit.getText().trim());
      if(chkSupplemen.isSelected()){dtl.setSupplemen_b(1);}else{dtl.setSupplemen_b(0);}
      dtl.setSupplemen_s(TxtSupplemen.getText().trim());
      if(chkRI.isSelected()){dtl.setRi_b(1);}else{dtl.setRi_b(0);}
      dtl.setRi_s(TxtRI.getText().trim());
      if(chkRJ.isSelected()){dtl.setRj_b(1);}else{dtl.setRj_b(0);}
      dtl.setRj_s(TxtRJ.getText().trim());
      if(chkMelahirkan.isSelected()){dtl.setMelahirkan_b(1);}else{dtl.setMelahirkan_b(0);}
      dtl.setMelahirkan_s(TxtMelahirkan.getText().trim());
      if(chkRG.isSelected()){dtl.setRg_b(1);}else{dtl.setRg_b(0);}
      dtl.setRg_s(TxtRG.getText().trim());
      if(chkMaternity.isSelected()){dtl.setMaternity_b(1);}else{dtl.setMaternity_b(0);}
      dtl.setMaternity_s(TxtMaternity.getText().trim());
      if(chkMCU.isSelected()){dtl.setMcu_b(1);}else{dtl.setMcu_b(0);}
      dtl.setMcu_s(TxtMCU.getText().trim());
      dtl.setKet(TxtKet.getText().trim());
      dtl.setUsrUpdate(lblUser.getText());
      dtl.setTglUpdate(new Date(System.currentTimeMillis()));
      dtl.setSyarat(TxtSyaratDtl.getText().trim());
      dtl.setAktif(chkAktifDtl.isSelected()? true : false);
      Hdr_KerjasamaModel hdr = (Hdr_KerjasamaModel) session.load(Hdr_KerjasamaModel.class, Integer.parseInt(TxtId.getText().trim()));
      dtl.setHdr_kerjasama(hdr);
      Transaction tx = session.beginTransaction();
      session.saveOrUpdate(dtl);
      tx.commit();
      TxtIdDtl.setText(Integer.toString(dtl.getId_kerjaSamaDetil()));
      session.close();
    /*  KlsParameter.code.kosongCheckbox(Dtl);
      KlsParameter.code.kosongText(Dtl);
      TglMulaiDtl.setDate(cal.getTime());
      TglSelesaiDtl.setDate(cal.getTime());*/
    }//GEN-LAST:event_BtnSimpanDtlActionPerformed

    private void BtnCariAsuransiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariAsuransiActionPerformed
        //cariAsuransi cariasuransi = new cariAsuransi(TxtAsuransi.getText().trim(), this);
        KlsParameter.kelas.setKerjasama(this);
        KlsParameter.kelas.setIdAsuransi(TxtnamaAsuransi.getText().trim());
        cariAsuransi cariasuransi = new cariAsuransi();
        cariasuransi.setVisible(true);
    }//GEN-LAST:event_BtnCariAsuransiActionPerformed
    void getidAsuransi(){
    TxtAsuransi.setText(KlsParameter.kelas.getIdAsuransi());
    TxtnamaAsuransi.setText(KlsParameter.kelas.getKdAsuransi());
    TxtNmAsuransi.setText(KlsParameter.kelas.getNmAsuransi());
}
    private void BtnCariPersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPersActionPerformed
        KlsParameter.kelas.setKerjasama(this);
        KlsParameter.kelas.setIdPerusahaan(TxtPerusahaan.getText().trim());
        cariPerusahaan caripers = new cariPerusahaan();
        caripers.setVisible(true);
       
    }//GEN-LAST:event_BtnCariPersActionPerformed

    void getidPerusahaan(){
    TxtIdPers.setText(KlsParameter.kelas.getIdPerusahaan());
    TxtPerusahaan.setText(KlsParameter.kelas.getKdPerusahaan());
    TxtNmPers.setText(KlsParameter.kelas.getNmPerusahaan());
}
    private void TxtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdActionPerformed
       cariId();
    }//GEN-LAST:event_TxtIdActionPerformed

    private void BtnTambahHdrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahHdrActionPerformed
        Calendar cal = Calendar.getInstance();
        KlsParameter.code.kosongText(Hdr);
        TglMulai.setDate(cal.getTime());
        TglSelesai.setDate(cal.getTime());
     
    }//GEN-LAST:event_BtnTambahHdrActionPerformed

    private void BtnTambahDtlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahDtlActionPerformed
      Calendar cal = Calendar.getInstance();
      KlsParameter.code.kosongText(Dtl);
      KlsParameter.code.kosongCheckbox(Dtl);
      TglMulaiDtl.setDate(cal.getTime());
      TglSelesaiDtl.setDate(cal.getTime());
    }//GEN-LAST:event_BtnTambahDtlActionPerformed

private void BtnNextDetilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNextDetilActionPerformed
SessionFactory sess = HibernateUtil.getSessionFactory();
Session session = sess.openSession();
Query q = session.createQuery(" from Dtl_KerjasamaModel dtl inner join dtl.perusahaanModel pers where dtl.hdr_kerjasama = '" + TxtId.getText().trim() + "' and dtl.id_kerjaSamaDetil > '" + TxtIdDtl.getText().trim() + "' ").setMaxResults(1);
Iterator it = q.list().iterator();
if(it.hasNext()){
    Object [] row = (Object []) it.next();
    dtl_kerjasama = (Dtl_KerjasamaModel) row[0];
    pers = (PerusahaanModel) row[1];
    TxtIdDtl.setText(Integer.toString(dtl_kerjasama.getId_kerjaSamaDetil()));
    setDetil();
    session.close();
}else{ JOptionPane.showMessageDialog(this, "End Of Record");}
}//GEN-LAST:event_BtnNextDetilActionPerformed

private void BtnPrevDetilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrevDetilActionPerformed
SessionFactory sess = HibernateUtil.getSessionFactory();
Session session = sess.openSession();
Query q = session.createQuery(" from Dtl_KerjasamaModel dtl inner join dtl.perusahaanModel pers where dtl.hdr_kerjasama = '" + TxtId.getText().trim() + "' and dtl.id_kerjaSamaDetil < '" + TxtIdDtl.getText().trim() + "' order by dtl.id_kerjaSamaDetil desc ").setMaxResults(1);
Iterator it = q.list().iterator();
if (it.hasNext()){
     Object [] row = (Object []) it.next();
     dtl_kerjasama = (Dtl_KerjasamaModel) row[0];
     pers = (PerusahaanModel) row[1];
     TxtIdDtl.setText(Integer.toString(dtl_kerjasama.getId_kerjaSamaDetil()));
     setDetil();
     session.close();
     
}else {JOptionPane.showMessageDialog(this, "Begin Of Record");}

}//GEN-LAST:event_BtnPrevDetilActionPerformed

private void BtnPrevHdrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrevHdrActionPerformed
SessionFactory sess  = HibernateUtil.getSessionFactory();
Session session = sess.openSession();
Query q = session.createQuery(" from Hdr_KerjasamaModel  hdr inner join hdr.asuransiModel where hdr.id_kerjasama < '" + TxtId.getText().trim() + "' order by id_kerjasama desc ").setMaxResults(1);
Iterator it = q.list().iterator();
if(it.hasNext()){
    Object [] row = (Object []) it.next();
    hdr_kerjasama = (Hdr_KerjasamaModel) row[0];
    as = (AsuransiModel) row[1];
    TxtId.setText(Integer.toString(hdr_kerjasama.getId_kerjasama()));
    setId();
    session.close();
} else { JOptionPane.showMessageDialog(this, "Begin Of Record");}
}//GEN-LAST:event_BtnPrevHdrActionPerformed

private void BtnNextHdrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNextHdrActionPerformed
SessionFactory sess = HibernateUtil.getSessionFactory();
Session session = sess.openSession();
Query q = session.createQuery(" from Hdr_KerjasamaModel hdr inner join hdr.asuransiModel  where hdr.id_kerjasama > '" + TxtId.getText().trim() + "'").setMaxResults(1);
Iterator it = q.list().iterator();
if(it.hasNext()){
    Object [] row = (Object []) it.next();
    hdr_kerjasama = (Hdr_KerjasamaModel) row[0];
    as = (AsuransiModel) row [1];
    TxtId.setText(Integer.toString(hdr_kerjasama.getId_kerjasama()));
    setId();
    session.close();
}else { JOptionPane.showMessageDialog(this, "End Of Record");}
}//GEN-LAST:event_BtnNextHdrActionPerformed
    void cariIdDtl(){
    if (TxtIdDtl.getText().equals("")){}else{
        SessionFactory sess = HibernateUtil.getSessionFactory();
        Session session = sess.openSession();
        dtl_kerjasama = (Dtl_KerjasamaModel) session.load(Dtl_KerjasamaModel.class, Integer.parseInt(TxtIdDtl.getText()));
        pers = (PerusahaanModel) session.load(PerusahaanModel.class, dtl_kerjasama.getPerusahaanModel().getId_perusahaan());
        setDetil();
        session.close();
        
    } 
}
    void setDetil(){
        TxtIdPers.setText(Integer.toString(pers.getId_perusahaan()));
        TxtPerusahaan.setText(pers.getKd_perusahaan());
        TxtNmPers.setText(pers.getNama());
        TglMulaiDtl.setDate(dtl_kerjasama.getTglMulai());
        TglSelesaiDtl.setDate(dtl_kerjasama.getTglSelesai());
        if(dtl_kerjasama.getExcess_b() == 1){chkExcess.setSelected(true);}else{chkExcess.setSelected(false);}
        TxtExcess.setText(dtl_kerjasama.getExcess_s());
        if(dtl_kerjasama.getVitamin_b() == 1){chkVitamin.setSelected(true);}else{chkVitamin.setSelected(false);}
        TxtVit.setText(dtl_kerjasama.getVitamin_s());
        if(dtl_kerjasama.getSupplemen_b() == 1){chkSupplemen.setSelected(true);}else{chkSupplemen.setSelected(false);}
        TxtSupplemen.setText(dtl_kerjasama.getSupplemen_s());
        if(dtl_kerjasama.getRi_b() == 1){chkRI.setSelected(true);}else{chkRI.setSelected(false);}
        TxtRI.setText(dtl_kerjasama.getRi_s());
        if(dtl_kerjasama.getRj_b() == 1){chkRJ.setSelected(true);}else{chkRJ.setSelected(false);}
        TxtRJ.setText(dtl_kerjasama.getRj_s());
        if(dtl_kerjasama.getMelahirkan_b() == 1){chkMelahirkan.setSelected(true);}else{chkMelahirkan.setSelected(false);}
        TxtMelahirkan.setText(dtl_kerjasama.getMelahirkan_s());
        if(dtl_kerjasama.getRg_b() == 1){chkRG.setSelected(true);}else{chkRG.setSelected(false);}
        TxtRG.setText(dtl_kerjasama.getRg_s());
        if(dtl_kerjasama.getMaternity_b() == 1){chkMaternity.setSelected(true);}else{chkMaternity.setSelected(false);}
        TxtMaternity.setText(dtl_kerjasama.getMaternity_s());
        if(dtl_kerjasama.getMcu_b() == 1){chkMCU.setSelected(true);}else{chkMCU.setSelected(false);}
        TxtMCU.setText(dtl_kerjasama.getMcu_s());
        TxtKet.setText(dtl_kerjasama.getKet());
        chkAktifDtl.setSelected(dtl_kerjasama.getAktif() );
        TxtSyaratDtl.setText(dtl_kerjasama.getSyarat().trim());
    }
    public void cariAsuransiHdr(String id, String nama){
    TxtAsuransi.setText(id);
    TxtnamaAsuransi.setText(nama);
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KerjaSama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KerjaSama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KerjaSama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KerjaSama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KerjaSama().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCariAsuransi;
    private javax.swing.JButton BtnCariPers;
    private javax.swing.JButton BtnNextDetil;
    private javax.swing.JButton BtnNextHdr;
    private javax.swing.JButton BtnPrevDetil;
    private javax.swing.JButton BtnPrevHdr;
    private javax.swing.JButton BtnSimpan;
    private javax.swing.JButton BtnSimpanDtl;
    private javax.swing.JButton BtnTambahDtl;
    private javax.swing.JButton BtnTambahHdr;
    private javax.swing.JCheckBox ChkAktif;
    private javax.swing.JPanel Dtl;
    private javax.swing.JPanel Hdr;
    private com.toedter.calendar.JDateChooser TglMulai;
    private com.toedter.calendar.JDateChooser TglMulaiDtl;
    private com.toedter.calendar.JDateChooser TglSelesai;
    private com.toedter.calendar.JDateChooser TglSelesaiDtl;
    private javax.swing.JTextField TxtAsuransi;
    private javax.swing.JTextField TxtExcess;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtIdDtl;
    private javax.swing.JTextField TxtIdPers;
    private javax.swing.JTextField TxtKet;
    private javax.swing.JTextField TxtKeterangan;
    private javax.swing.JTextField TxtMCU;
    private javax.swing.JTextField TxtMaternity;
    private javax.swing.JTextField TxtMelahirkan;
    private javax.swing.JTextField TxtNmAsuransi;
    private javax.swing.JTextField TxtNmPers;
    private javax.swing.JTextField TxtPerusahaan;
    private javax.swing.JTextField TxtRG;
    private javax.swing.JTextField TxtRI;
    private javax.swing.JTextField TxtRJ;
    private javax.swing.JTextField TxtSupplemen;
    private javax.swing.JTextArea TxtSyarat;
    private javax.swing.JTextArea TxtSyaratDtl;
    private javax.swing.JTextField TxtVit;
    private javax.swing.JTextField TxtnamaAsuransi;
    private javax.swing.JCheckBox chkAktifDtl;
    private javax.swing.JCheckBox chkExcess;
    private javax.swing.JCheckBox chkMCU;
    private javax.swing.JCheckBox chkMaternity;
    private javax.swing.JCheckBox chkMelahirkan;
    private javax.swing.JCheckBox chkRG;
    private javax.swing.JCheckBox chkRI;
    private javax.swing.JCheckBox chkRJ;
    private javax.swing.JCheckBox chkSupplemen;
    private javax.swing.JCheckBox chkVitamin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUser;
    // End of variables declaration//GEN-END:variables
}
