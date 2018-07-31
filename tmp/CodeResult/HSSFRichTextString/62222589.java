/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import test.dbc.DBConnection;
import test.vo.*;

/**
 *
 * @author sq
 */
public class jiandingListDAO {

    public boolean insert(jiandingList jiandinglist) {
        String sql = "INSERT INTO jiandingchengguo("
                + "chengguomingcheng,jiandingriqi,jiandingjielun,"
                + "wanchengdanwei,wanchengren,xiangguanxiangmu,"
                + "jiandingdanwei,jiandingjibie,jiandingxingshi,"
                + "jiandingzhengshuhao,zhuanhuariqi,shiyongdanwei,"
                + "keti_id "

                + " ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //??????
        Connection conn = DBConnection.getConnection();
        //??????????????????sql???
        String updateketizhuangtai="update keti_table "
                + "set ketizhuangtai=? where keti_id=?";
        //??????
        //??????????????????sql???
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;

        boolean insertSuccessFlag = false;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt1 = conn.prepareStatement(updateketizhuangtai);


            pstmt.setString(1, jiandinglist.getChengguomingcheng());
            pstmt.setString(2, jiandinglist.getJiandingriqi());
            pstmt.setString(3, jiandinglist.getJiandingjielun());
            pstmt.setString(4, jiandinglist.getWanchengdanwei());
            pstmt.setString(5, jiandinglist.getWanchengren());

            pstmt.setString(6, jiandinglist.getXiangguanxiangmu());
            pstmt.setString(7, jiandinglist.getJiandingdanwei());
            pstmt.setString(8, jiandinglist.getJiandingjibie());
            pstmt.setString(9, jiandinglist.getJiandingxingshi());
            pstmt.setString(10, jiandinglist.getJiandingzhengshuhao());

            pstmt.setString(11, jiandinglist.getZhuanhuariqi());
            pstmt.setString(12, jiandinglist.getShiyongdanwei());
            pstmt.setInt(13, jiandinglist.getKeti_id());
            


            pstmt1.setString(1,"wc");
            pstmt1.setInt(2,jiandinglist.getKeti_id());
             conn.setAutoCommit(false);

            int i = pstmt.executeUpdate();
            int j = pstmt1.executeUpdate();

            conn.commit();


            if (i == 1&&j==1) {
                // ??????????????????
                insertSuccessFlag = true;
            }
        } catch (SQLException e) {
            System.out.println("????????????");
            System.out.println(e);
             try {
                 //????
                conn.rollback();
            } catch (SQLException e1) {
                System.out.println("????");
            }
        } finally {
            DBConnection.close(conn, pstmt, null);
        }
        return insertSuccessFlag;
    }

    // ????
    public boolean update(jiandingList jiandinglist) {
        String sql = "UPDATE jiandingchengguo set "
                + "chengguomingcheng=?,jiandingriqi=?,jiandingjielun=?,wanchengdanwei=?,"
                + "wanchengren=?,xiangguanxiangmu=?,jiandingdanwei=?,"
                + "jiandingjibie=?,jiandingxingshi=?,jiandingzhengshuhao=?,"
                + "zhuanhuariqi=?,shiyongdanwei=? "
                + " WHERE keti_id=?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        boolean updateSuccessFlag = false;
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, jiandinglist.getChengguomingcheng());
            pstmt.setString(2, jiandinglist.getJiandingriqi());
            pstmt.setString(3, jiandinglist.getJiandingjielun());
            pstmt.setString(4, jiandinglist.getWanchengdanwei());
            pstmt.setString(5, jiandinglist.getWanchengren());

            pstmt.setString(6, jiandinglist.getXiangguanxiangmu());
            pstmt.setString(7, jiandinglist.getJiandingdanwei());
            pstmt.setString(8, jiandinglist.getJiandingjibie());
            pstmt.setString(9, jiandinglist.getJiandingxingshi());
            pstmt.setString(10, jiandinglist.getJiandingzhengshuhao());

            pstmt.setString(11, jiandinglist.getZhuanhuariqi());
            pstmt.setString(12, jiandinglist.getShiyongdanwei());
            pstmt.setInt(13, jiandinglist.getKeti_id());
                                  
            int i = pstmt.executeUpdate();
            if (i == 1) {
                // ??????????????????
                updateSuccessFlag = true;
            }

        } catch (SQLException e) {
            System.out.println("????????????");
            System.out.println(e);
        } finally {
            DBConnection.close(conn, pstmt, null);
        }
        return updateSuccessFlag;
    }
    // ????

    public boolean delete(int del_keti) {
        String sql = "DELETE FROM jiandingchengguo WHERE keti_id=?";
        PreparedStatement pstmt = null;
        Connection conn = DBConnection.getConnection();
        boolean deleteSuccessFlag = false;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, del_keti);
            int i = pstmt.executeUpdate();
            if (i == 1) {
                deleteSuccessFlag = true;
            }
        } catch (SQLException e) {
            System.out.println("????????????");
            System.out.println(e);
        } finally {
            DBConnection.close(conn, pstmt, null);
        }
        return deleteSuccessFlag;
    }

    // ?ID??,????????
    public jiandingList queryByKeti_id(int keti_id) {
        jiandingList jiandinglist = null;
        String sql = "SELECT * FROM jiandingchengguo q"
                + " where keti_id=?";
        // ?????*???????????
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, keti_id);
            rs = pstmt.executeQuery();
            //???????while,?while????
            if (rs.next()) {
                jiandinglist = new jiandingList();

                jiandinglist.setChengguo_id(rs.getInt(1));
                jiandinglist.setChengguomingcheng(rs.getString(2));
                jiandinglist.setJiandingriqi(rs.getString(3));
                jiandinglist.setJiandingjielun(rs.getString(4));
                jiandinglist.setWanchengdanwei(rs.getString(5));

                jiandinglist.setWanchengren(rs.getString(6));
                jiandinglist.setXiangguanxiangmu(rs.getString(7));
                jiandinglist.setJiandingdanwei(rs.getString(8));
                jiandinglist.setJiandingjibie(rs.getString(9));
                jiandinglist.setJiandingxingshi(rs.getString(10));

                jiandinglist.setJiandingzhengshuhao(rs.getString(11));
                jiandinglist.setZhuanhuariqi(rs.getString(12));
                jiandinglist.setShiyongdanwei(rs.getString(13));
                jiandinglist.setKeti_id(rs.getInt(14));
                
                

            }

        } catch (SQLException e) {
            System.out.println("??????????");
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return jiandinglist;
    }

    public List queryKetiByLogin_id(String denglu_id) {
        List keti = new ArrayList();
        jiandingList jiandinglist = null;
        String sql = "SELECT  "
                + "ketibianhao,mingcheng,zhuchiren,zhuchirendanwei,"
                + "ketilaiyuan,jingfei,qishiriqi,wanchengriqi,canjiaren1,"
                + "canjiaren2,canjiaren3,canjiaren4,"
                + "huojiang,ketizhuangtai,keti_id,"
                + "denglu_id "
                + " FROM jiandingchengguo where denglu_id=?  ";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, denglu_id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
       /*         jiandinglist = new jiandinglist();

                jiandinglist.setKetibianhao(rs.getString(1));
                jiandinglist.setMingcheng(rs.getString(2));
                jiandinglist.setZhuchiren(rs.getString(3));
                jiandinglist.setZhuchirendanwei(rs.getString(4));
                jiandinglist.setKetilaiyuan(rs.getString(5));

                jiandinglist.setJingfei(rs.getString(6));
                jiandinglist.setQishiriqi(rs.getString(7));
                jiandinglist.setWanchengriqi(rs.getString(8));
                jiandinglist.setCanjiaren1(rs.getString(9));
                jiandinglist.setCanjiaren2(rs.getString(10));

                jiandinglist.setCanjiaren3(rs.getString(11));
                jiandinglist.setCanjiaren4(rs.getString(12));
                jiandinglist.setHuojiang(rs.getString(13));
                jiandinglist.setKetizhuangtai(rs.getString(14));
                jiandinglist.setKeti_id(rs.getInt(15));

                jiandinglist.setDenglu_id(rs.getString(16));
*/
                keti.add(jiandinglist);
            }

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return keti;
    }

    public List queryKetiByShenhe(String shenhe) {
        List keti = new ArrayList();
        jiandingList jiandinglist = null;
        String sql = "SELECT  "
                + "ketibianhao,mingcheng,zhuchiren,zhuchirendanwei,"
                + "ketilaiyuan,jingfei,qishiriqi,wanchengriqi,canjiaren1,"
                + "canjiaren2,canjiaren3,canjiaren4,"
                + "huojiang,ketizhuangtai,keti_id,"
                + "denglu_id "
                + " FROM jiandingchengguo where shenhe=?  ";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shenhe);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                jiandinglist = new jiandingList();

         /*       jiandinglist.setKetibianhao(rs.getString(1));
                jiandinglist.setMingcheng(rs.getString(2));
                jiandinglist.setZhuchiren(rs.getString(3));
                jiandinglist.setZhuchirendanwei(rs.getString(4));
                jiandinglist.setKetilaiyuan(rs.getString(5));

                jiandinglist.setJingfei(rs.getString(6));
                jiandinglist.setQishiriqi(rs.getString(7));
                jiandinglist.setWanchengriqi(rs.getString(8));
                jiandinglist.setCanjiaren1(rs.getString(9));
                jiandinglist.setCanjiaren2(rs.getString(10));

                jiandinglist.setCanjiaren3(rs.getString(11));
                jiandinglist.setCanjiaren4(rs.getString(12));
                jiandinglist.setHuojiang(rs.getString(13));
                jiandinglist.setKetizhuangtai(rs.getString(14));
                jiandinglist.setKeti_id(rs.getInt(15));

                jiandinglist.setDenglu_id(rs.getString(16));
*/
                keti.add(jiandinglist);
            }

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return keti;
    }

    // ????????????????????
    public List queryKetiByLike(String keyword, int pageNO) {
        List allketi = new ArrayList();
        jiandingList jiandinglist = null;
        int offset = (pageNO - 1) * 7;
        // sql???????????????????????
        String sql = "SELECT  "
                + "ketibianhao,mingcheng,zhuchiren,zhuchirendanwei,"
                + "ketilaiyuan,jingfei,qishiriqi,wanchengriqi,canjiaren1,"
                + "canjiaren2,canjiaren3,canjiaren4,"
                + "huojiang,ketizhuangtai,keti_id " + //????keti_id??????
                " FROM jiandingchengguo "
                + " where ketibianhao like ? or "
                + "zhuchiren like ? or "
                + "ketilaiyuan like ? or "
                + "qishiriqi like ?  or "
                + //  "mingcheng like ? or" +
                //    "canjiaren1 like ? or "+
                //   "canjiaren2 like ? or "+
                //  "canjiaren3 like ? or "+
                //   "canjiaren4 like ?  or "+
                // "canjiarendeng like ? or "+
                "ketizhuangtai like ?  "
                + "limit " + offset + ",7";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");  //????????
            pstmt.setString(2, "%" + keyword + "%");  //?????
            pstmt.setString(3, "%" + keyword + "%");
            pstmt.setString(4, "%" + keyword + "%");
            pstmt.setString(5, "%" + keyword + "%");
//pstmt.setString(6, "%" + keyword + "%");
//pstmt.setString(7, "%" + keyword + "%");
//pstmt.setString(8, "%" + keyword + "%");
//pstmt.setString(9, "%" + keyword + "%");
//pstmt.setString(10, "%" + keyword + "%");
//pstmt.setString(11, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                jiandinglist = new jiandingList();

     /*           jiandinglist.setKetibianhao(rs.getString(1));
                jiandinglist.setMingcheng(rs.getString(2));
                jiandinglist.setZhuchiren(rs.getString(3));
                jiandinglist.setZhuchirendanwei(rs.getString(4));
                jiandinglist.setKetilaiyuan(rs.getString(5));

                jiandinglist.setJingfei(rs.getString(6));
                jiandinglist.setQishiriqi(rs.getString(7));
                jiandinglist.setWanchengriqi(rs.getString(8));
                jiandinglist.setCanjiaren1(rs.getString(9));
                jiandinglist.setCanjiaren2(rs.getString(10));

                jiandinglist.setCanjiaren3(rs.getString(11));
                jiandinglist.setCanjiaren4(rs.getString(12));
                //  jiandinglist.setCanjiarendeng(rs.getString(13));
                jiandinglist.setHuojiang(rs.getString(13));
                jiandinglist.setKeti_id(rs.getInt(15));

                jiandinglist.setKetizhuangtai(rs.getString(14));
*/
                allketi.add(jiandinglist);
            }

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }

       public List queryAllKetiByLike(String startYear,
            String endYear, String source, String danwei,
            String leibie, String paixu, int pageNO) {
        System.out.println(startYear);
        System.out.println(endYear);
        System.out.println(paixu);

        List allketi = new ArrayList();
        ketiList ketilist = null;
        int offset = (pageNO - 1) * 20;
        // sql???????????????????????
        String sql = "SELECT  "
                + "ketibianhao,mingcheng,zhuchiren,danweiName,"
                + "sourceName,jingfei,qishiriqi,wanchengriqi,canjiaren1,"
                + "canjiaren2,canjiaren3,canjiaren4,"
                + "huojiang,ketizhuangtai,CategoryName,keti_id " + //????keti_id??????
                " FROM keti_table k,danwei d,source_table s,category_table c"
                + " where k.leibie=c.categoryid and s.sourceid=k.ketilaiyuan "
                + "and k.zhuchirendanwei=d.danweiid"
                + " and SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=? "
                + " and ketilaiyuan like ? "
                + " and zhuchirendanwei like ? "
                + " and leibie like ? "
                + " order by  " + paixu
                + " limit " + offset + ",20";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            pstmt.setString(3, "%" + source + "%");
            pstmt.setString(4, "%" + danwei + "%");
            pstmt.setString(5, "%" + leibie + "%");
            // pstmt.setString(6,  paixu);
//pstmt.setString(6, "%" + keyword + "%");
//pstmt.setString(7, "%" + keyword + "%");
//pstmt.setString(8, "%" + keyword + "%");
//pstmt.setString(9, "%" + keyword + "%");
//pstmt.setString(10, "%" + keyword + "%");
//pstmt.setString(11, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ketilist = new ketiList();

                ketilist.setKetibianhao(rs.getString(1));
                ketilist.setMingcheng(rs.getString(2));
                ketilist.setZhuchiren(rs.getString(3));
                ketilist.setZhuchirendanwei(rs.getString(4));
                ketilist.setKetilaiyuan(rs.getString(5));

                ketilist.setJingfei(rs.getString(6));
                ketilist.setQishiriqi(rs.getString(7));
                ketilist.setWanchengriqi(rs.getString(8));
                ketilist.setCanjiaren1(rs.getString(9));
                ketilist.setCanjiaren2(rs.getString(10));

                ketilist.setCanjiaren3(rs.getString(11));
                ketilist.setCanjiaren4(rs.getString(12));
                //  ketilist.setCanjiarendeng(rs.getString(13));
                ketilist.setHuojiang(rs.getString(13));
                //????
                ketilist.setKeti_id(rs.getInt(16));
                ketilist.setLeibie(rs.getString(15));

                ketilist.setKetizhuangtai(rs.getString(14));

                allketi.add(ketilist);
            }

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }

    public int getAllRecordCountByLike(String startYear,
            String endYear, String source, String danwei,
            String leibie) {
        System.out.println(startYear);
        System.out.println(endYear);
        int totalRecord = 0;//????
        String sql = "SELECT count(*) FROM jiandingchengguo "
                + " where SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=? "
                + " and ketilaiyuan like ? "
                + " and zhuchirendanwei like ? "
                + " and leibie like ? ";

        // ???????
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            pstmt.setString(3, "%" + source + "%");
            pstmt.setString(4, "%" + danwei + "%");
            pstmt.setString(5, "%" + leibie + "%");
            rs = pstmt.executeQuery();
            rs.next();
            totalRecord = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return totalRecord;
    }

    public List countKeTiAllByHengxiangAndzongxiang(String startYear, String endYear) {
        List allketi = new ArrayList();
        CountKetiItem jiandinglist = null;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        // sql???????????????????????
        String sql = "select count(*),sum(jingfei) "
                + " from jiandingchengguo"
                + "  where substring(qishiriqi,1,4)>=" + startYear
                + " and  substring(qishiriqi,1,4)<=" + endYear
                + " and leibie=? ";
        String sql2 = "select count(*),sum(jingfei) as ?? from jiandingchengguo k"
                + " where substring(qishiriqi,1,4)>=? and  substring(qishiriqi,1,4)<=? ";


        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "01");

            rs = pstmt.executeQuery();
            rs.next();
            jiandinglist = new CountKetiItem();
            jiandinglist.setKetiCount(rs.getInt(1));
            jiandinglist.setJingFei(rs.getFloat(2));
            //jiandinglist.setYear(rs.getString(3));
            allketi.add(jiandinglist);
            pstmt.setString(1, "02");

            rs = pstmt.executeQuery();
            rs.next();
            jiandinglist = new CountKetiItem();
            jiandinglist.setKetiCount(rs.getInt(1));
            jiandinglist.setJingFei(rs.getFloat(2));
            //jiandinglist.setYear(rs.getString(3));
            allketi.add(jiandinglist);
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);

            rs = pstmt.executeQuery();
            rs.next();
            jiandinglist = new CountKetiItem();
            jiandinglist.setKetiCount(rs.getInt(1));
            jiandinglist.setJingFei(rs.getFloat(2));
            allketi.add(jiandinglist);

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }

    public List countKeTiByYearAndHengxiangAndzongxiang(String startYear, String endYear) {
        List allketi = new ArrayList();
        CountKetiItem jiandinglist = null;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        // sql???????????????????????
        String sql = "select count(*),sum(jingfei) ,"
                + "subString(qishiriqi,1,4) from jiandingchengguo"
                + "  where subString(qishiriqi,1,4)=?"
                + " group by leibie ";
        String sql2 = "select count(*),sum(jingfei) as ?? from jiandingchengguo k"
                + " where substring(qishiriqi,1,4)>=? and  substring(qishiriqi,1,4)<=? ";


        ResultSet rs = null;
        int year1 = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            for (year1 = Integer.parseInt(startYear);
                    year1 <= Integer.parseInt(endYear); year1++) {

                pstmt.setString(1, "" + year1);

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    jiandinglist = new CountKetiItem();
                    jiandinglist.setKetiCount(rs.getInt(1));
                    jiandinglist.setJingFei(rs.getFloat(2));
                    jiandinglist.setYear(rs.getString(3));
                    allketi.add(jiandinglist);
                }
            }
//            pstmt = conn.prepareStatement(sql2);
//            pstmt.setString(1, startYear);
//            pstmt.setString(2, endYear);
//
//            rs = pstmt.executeQuery();
//            rs.next();
//            jiandinglist = new CountKetiItem();
//            jiandinglist.setKetiCount(rs.getInt(1));
//            jiandinglist.setJingFei(rs.getFloat(2));
//            allketi.add(jiandinglist);

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }

    public List countKeTiByDanweiORSourceChuizhi(String startYear, String endYear) {
        List allketi = new ArrayList();
        CountKetiItem jiandinglist = null;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        // sql???????????????????????
        String sql = "select count(*),sum(jingfei) as ?? from jiandingchengguo k"
                + " where substring(qishiriqi,1,4)=? ";
        String sql2 = "select count(*),sum(jingfei) as ?? from jiandingchengguo k"
                + " where substring(qishiriqi,1,4)>=? and  substring(qishiriqi,1,4)<=? ";


        ResultSet rs = null;
        int year1 = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            for (year1 = Integer.parseInt(startYear);
                    year1 <= Integer.parseInt(endYear); year1++) {

                pstmt.setString(1, "" + year1);

                rs = pstmt.executeQuery();
                rs.next();
                jiandinglist = new CountKetiItem();
                jiandinglist.setKetiCount(rs.getInt(1));
                jiandinglist.setJingFei(rs.getFloat(2));
                allketi.add(jiandinglist);
            }
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);

            rs = pstmt.executeQuery();
            rs.next();
            jiandinglist = new CountKetiItem();
            jiandinglist.setKetiCount(rs.getInt(1));
            jiandinglist.setJingFei(rs.getFloat(2));
            allketi.add(jiandinglist);

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }
    //???????

    public List countKeTiByDanweiShuiping(String startYear, String endYear) {
        List allketi = new ArrayList();
        CountKetiItem jiandinglist = null;
        // sql???????????????????????
        String sql = " select d.danweiName ,count(*) as ???? ,"
                + " sum(jingfei) as ??,SUBSTRING(qishiriqi,1,4)"
                + " as year from jiandingchengguo k,danwei d"
                + " where k.zhuchirendanwei=d.danweiid "
                + " and SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=?"
                + " group by d.danweiid";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jiandinglist = new CountKetiItem();
                jiandinglist.setSourceName(rs.getString(1));
                jiandinglist.setKetiCount(rs.getInt(2));
                jiandinglist.setJingFei(rs.getFloat(3));
                jiandinglist.setYear(rs.getString(4));
                allketi.add(jiandinglist);
            }
        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }
//??????????????

    public List countKeTiBySourceShuiping(String startYear, String endYear) {
        List allketi = new ArrayList();
        CountKetiItem jiandinglist = null;
        // sql???????????????????????
        String sql = " select s.sourceName ,count(*) as ???? ,"
                + " sum(jingfei) as ??,SUBSTRING(qishiriqi,1,4)"
                + " as year from jiandingchengguo k,source_table s"
                + " where k.ketilaiyuan=s.sourceid "
                + " and SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=?"
                + " group by s.sourceid";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jiandinglist = new CountKetiItem();
                jiandinglist.setSourceName(rs.getString(1));
                jiandinglist.setKetiCount(rs.getInt(2));
                jiandinglist.setJingFei(rs.getFloat(3));
                jiandinglist.setYear(rs.getString(4));
                allketi.add(jiandinglist);
            }
        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }

    public List countKeTiByDanwei(String startYear, String endYear) {

        List allketi = new ArrayList();
        CountKetiItem jiandinglist = null;
        // sql???????????????????????
        String sql = "select d.danweiName ,count(*) as ???? ,"
                + "sum(jingfei) as ??,SUBSTRING(qishiriqi,1,4)"
                + " as year from jiandingchengguo k,danwei d"
                + " where k.zhuchirendanwei=d.danweiid  group by d.danweiid,SUBSTRING(qishiriqi,1,4)"
                + "having year>=? and year<=?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jiandinglist = new CountKetiItem();
                jiandinglist.setSourceName(rs.getString(1));
                jiandinglist.setKetiCount(rs.getInt(2));
                jiandinglist.setJingFei(rs.getFloat(3));
                jiandinglist.setYear(rs.getString(4));
                allketi.add(jiandinglist);
            }
        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }

    public List countKeTiBySource(String startYear, String endYear) {

        List allketi = new ArrayList();
        CountKetiItem jiandinglist = null;
        // sql???????????????????????
        String sql = "select s.sourceName ,count(*) as ???? ,"
                + "sum(jingfei) as ??,SUBSTRING(qishiriqi,1,4) "
                + " as year from jiandingchengguo k,"
                + " source_table s where k.ketilaiyuan=s.sourceid"
                + " group by s.sourceid,SUBSTRING(qishiriqi,1,4)"
                + " having year>=? and year<=?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jiandinglist = new CountKetiItem();
                jiandinglist.setSourceName(rs.getString(1));
                jiandinglist.setKetiCount(rs.getInt(2));
                jiandinglist.setJingFei(rs.getFloat(3));
                jiandinglist.setYear(rs.getString(4));
                allketi.add(jiandinglist);
            }
        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }

    /**
     * ????
     * @return
     */
    public int getTotalPage(String cond) {
        int totalPage = 0;//???
        int pageSize = 20; //???????
        int totalRecord = 0;//????
        String sql = "SELECT count(*) FROM jiandingchengguo "
                + " where ketibianhao like ? or "
                + "zhuchiren like ? or "
                + "ketilaiyuan like ? or "
                + "qishiriqi like ?  or "
                +  "ketizhuangtai like ?  ";

        // ???????
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            System.out.println("%" + cond + "%");
            pstmt.setString(1, "%" + cond + "%");
            pstmt.setString(2, "%" + cond + "%");
            pstmt.setString(3, "%" + cond + "%");
            pstmt.setString(4, "%" + cond + "%");
            pstmt.setString(5, "%" + cond + "%");
            rs = pstmt.executeQuery();
            rs.next();
            totalRecord = rs.getInt(1);

            if (totalRecord % pageSize == 0) {
                totalPage = totalRecord / pageSize;
            } else {
                totalPage = totalRecord / pageSize + 1;
            }

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return totalPage;
    }

    public int getAllTotalPage(String startYear,
            String endYear, String source, String danwei,
            String leibie) {
        int totalPage = 0;//???
        int pageSize = 20; //???????
        int totalRecord = 0;//????
        String sql = "SELECT count(*) FROM jiandingchengguo "
                + " where SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=? "
                + " and ketilaiyuan like ? "
                + " and zhuchirendanwei like ? "
                + " and leibie like ? ";

        // ???????
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            pstmt.setString(3, "%" + source + "%");
            pstmt.setString(4, "%" + danwei + "%");
            pstmt.setString(5, "%" + leibie + "%");
            rs = pstmt.executeQuery();
            rs.next();
            totalRecord = rs.getInt(1);

            if (totalRecord % pageSize == 0) {
                totalPage = totalRecord / pageSize;
            } else {
                totalPage = totalRecord / pageSize + 1;
            }

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return totalPage;
    }

    //??????
    public int getDaiShenheShu(String shenhe) {
        int totalRecord = 0;//????
        String sql = "SELECT count(*) FROM jiandingchengguo where shenhe=? ";
        // ???????
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shenhe);
            rs = pstmt.executeQuery();
            rs.next();
            totalRecord = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return totalRecord;
    }

    public HSSFWorkbook getWorkBook(String startYear,
            String endYear, String source, String danwei,
            String leibie) {
        HSSFWorkbook wb =
                new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(" sheet1 ");
        HSSFRow row = null;
        HSSFCell cell = null;

        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("????"));
        cell = row.createCell(1);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("????"));
        cell = row.createCell(2);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("???"));
        cell = row.createCell(3);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("?????"));
        cell = row.createCell(4);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("????"));

        cell = row.createCell(5);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??(?)"));
        cell = row.createCell(6);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("????"));

        cell = row.createCell(7);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("???1"));
        cell = row.createCell(8);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("???2"));

        cell = row.createCell(9);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("????"));
        cell = row.createCell(10);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(11);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("????"));



        String sql = "SELECT  "
                + "ketibianhao,mingcheng,zhuchiren,danweiName,"
                + "sourceName,jingfei,qishiriqi,canjiaren1,"
                + "canjiaren2,"
                + "huojiang,CategoryName,ketizhuangtai " + //????keti_id??????
                " FROM jiandingchengguo k,danwei d,source_table s,category_table c"
                + " where k.leibie=c.categoryid and s.sourceid=k.ketilaiyuan "
                + "and k.zhuchirendanwei=d.danweiid"
                + " and SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=? "
                + " and ketilaiyuan like ? "
                + " and zhuchirendanwei like ? "
                + " and leibie like ? "
                + " order by zhuchirendanwei";


        // ???????
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            pstmt.setString(3, "%" + source + "%");
            pstmt.setString(4, "%" + danwei + "%");
            pstmt.setString(5, "%" + leibie + "%");
            rs = pstmt.executeQuery();
            int iRow = 1;
            //?????????????Excel????
            while (rs.next()) {
                row = sheet.createRow((short) iRow);

                for (int j = 1; j <= 12; j++) {
                    cell = row.createCell((j - 1));
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(new HSSFRichTextString(rs.getObject(j).toString()));
                }
                iRow++;
            }
        } catch (SQLException e) {
            System.out.println("excel??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return wb;

    }

    public HSSFWorkbook CountAllketiByHengxiangAndZongxiangWorkBook(String startYear,
            String endYear) {
        HSSFWorkbook wb =
                new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet(" sheet1 ");
        HSSFRow row = null;
        HSSFCell cell = null;

        row = sheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(1);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(2);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(3);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(1);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(2);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(3);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(4);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));

        cell = row.createCell(5);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        cell = row.createCell(6);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(new HSSFRichTextString("??"));
        jiandingListDAO kdao = new jiandingListDAO();
        List<CountKetiItem> countjiandinglistByChuizhi = kdao.countKeTiByDanweiORSourceChuizhi(startYear, endYear);


        List<CountKetiItem> countjiandinglist = kdao.countKeTiByYearAndHengxiangAndzongxiang(startYear, endYear);
        int iRow = 2;
        int keti=0;
        float jingfei=0;
        for (int i = 0, j = 0; i < countjiandinglist.size(); i++, j++) {
            CountKetiItem countKetiItem = countjiandinglist.get(i);
             keti = countKetiItem.getKetiCount();
             jingfei = countKetiItem.getJingFei();
            String year = countKetiItem.getYear();

            //?????????????Excel????
            row = sheet.createRow((short) iRow);


            cell = row.createCell((short) 0);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString(year));
            cell = row.createCell((short) 1);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString(keti + ""));
            cell = row.createCell((short) 2);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString(jingfei + ""));

            if (i < countjiandinglist.size() - 1) {
                i++;
                countKetiItem = countjiandinglist.get(i);
                keti = countKetiItem.getKetiCount();
                jingfei = countKetiItem.getJingFei();
                cell = row.createCell((short) 3);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(keti + ""));
                cell = row.createCell((short) 4);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(jingfei + ""));
            }
                CountKetiItem countKetiItemAll = countjiandinglistByChuizhi.get(j);
                keti = countKetiItemAll.getKetiCount();
                jingfei = countKetiItemAll.getJingFei();
                cell = row.createCell((short)5);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(keti + ""));
                cell = row.createCell((short) 6);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(jingfei + ""));

                iRow++;
            }

            row = sheet.createRow((short) iRow);
            cell = row.createCell((short) 0);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(new HSSFRichTextString("??"));
            List<CountKetiItem> countAlljiandinglistByChuizhi = kdao.countKeTiAllByHengxiangAndzongxiang(startYear, endYear);
            for (int k = 0, m = 1; k < countAlljiandinglistByChuizhi.size(); k++) {
                CountKetiItem countAllKetiItem = countAlljiandinglistByChuizhi.get(k);
                keti = countAllKetiItem.getKetiCount();
                jingfei = countAllKetiItem.getJingFei();

                cell = row.createCell((short) m);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(keti + ""));
                m++;
                cell = row.createCell((short) (m));
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(new HSSFRichTextString(jingfei + ""));
                m++;
            }



            return wb;

        }

    public static void main(String args[]) {
        jiandingListDAO kdao = new jiandingListDAO();
//        List<jiandinglist> all = kdao.queryAllKetiByLike("2006", "2012", "", "", "", 1);

//        for (jiandinglist klist : all) {
//            System.out.println(klist.getKetilaiyuan());

        //}
    }

     public List queryAllKetiByWC(String startYear,
            String endYear, String source, String danwei,
            String ketizhuangtai, String paixu, int pageNO)
    {   System.out.println(startYear);
        System.out.println(endYear);
        System.out.println(paixu);
        List allketi = new ArrayList();
        ketiList ketilist = null;
        int offset = (pageNO - 1) * 20;
        // sql???????????????????????
        String sql = "SELECT  "
                + "ketibianhao,mingcheng,zhuchiren,danweiName,"
                + "sourceName,jingfei,qishiriqi,wanchengriqi,"
                + "huojiang,ketizhuangtai,keti_id " + //????keti_id??????
                " FROM keti_table k,danwei d,source_table s"
                + " where  s.sourceid=k.ketilaiyuan "
                + "and k.zhuchirendanwei=d.danweiid"
                + " and SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=? "
                + " and ketilaiyuan like ? "
                + " and zhuchirendanwei like ? "
                + " and ketizhuangtai like ? "
                + " order by  " + paixu
                + " limit " + offset + ",20";


        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            pstmt.setString(3, "%" + source + "%");
            pstmt.setString(4, "%" + danwei + "%");
            pstmt.setString(5, "%" + ketizhuangtai + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ketilist = new ketiList();

                ketilist.setKetibianhao(rs.getString(1));
                ketilist.setMingcheng(rs.getString(2));
                ketilist.setZhuchiren(rs.getString(3));
                ketilist.setZhuchirendanwei(rs.getString(4));
                ketilist.setKetilaiyuan(rs.getString(5));

                ketilist.setJingfei(rs.getString(6));
                ketilist.setQishiriqi(rs.getString(7));
                ketilist.setWanchengriqi(rs.getString(8));

                ketilist.setHuojiang(rs.getString(9));
                ketilist.setKetizhuangtai(rs.getString(10));
                ketilist.setKeti_id(rs.getInt(11));

                allketi.add(ketilist);
            }

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }


    public int getAllRecordCountByWC(String startYear,
            String endYear, String source, String danwei,
            String ketizhuangtai) {
        System.out.println(startYear);
        System.out.println(endYear);
        int totalRecord = 0;//????
        String sql = "SELECT count(*) FROM keti_table "
                + " where SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=? "
                + " and ketilaiyuan like ? "
                + " and zhuchirendanwei like ? "
                + " and ketizhuangtai like ? ";

        // ???????
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            pstmt.setString(3, "%" + source + "%");
            pstmt.setString(4, "%" + danwei + "%");
            pstmt.setString(5, "%" + ketizhuangtai + "%");
            rs = pstmt.executeQuery();
            rs.next();
            totalRecord = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return totalRecord;
    }


    public int getAllTotalPageWC(String startYear,
            String endYear, String source, String danwei,
            String ketizhuangtai) {
        int totalPage = 0;//???
        int pageSize = 20; //???????
        int totalRecord = 0;//????
        String sql = "SELECT count(*) FROM keti_table "
                + " where SUBSTRING(qishiriqi,1,4)>=? "
                + " and SUBSTRING(qishiriqi,1,4)<=? "
                + " and ketilaiyuan like ? "
                + " and zhuchirendanwei like ? "
                + " and ketizhuangtai like ? ";

        // ???????
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            pstmt.setString(3, "%" + source + "%");
            pstmt.setString(4, "%" + danwei + "%");
            pstmt.setString(5, "%" + ketizhuangtai + "%");
            rs = pstmt.executeQuery();
            rs.next();
            totalRecord = rs.getInt(1);

            if (totalRecord % pageSize == 0) {
                totalPage = totalRecord / pageSize;
            } else {
                totalPage = totalRecord / pageSize + 1;
            }

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return totalPage;
    }

     public List countJiandingByDanweiAndSource(
            String startYear, String endYear, String zhuchirendanwei,
            String zhuchiren) {

        List allketi = new ArrayList();
        CountJianDingItem ketilist = null;
        // sql???????????????????????
        String sql = "SELECT b.sourceName, COUNT(a.ketilaiyuan) as ???? "
               
                + " FROM jiandingchengguo a "
                + " RIGHT JOIN source_table b ON"
                + " a.ketilaiyuan=b.sourceid and wanchengdanwei=? "
                + " and substring(jiandingriqi,1,4)=? "
                + " and wanchengren like ?"
                + " GROUP BY b.sourceid";

System.out.println(sql);
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
//          try {
//            pstmt = conn.prepareStatement(sql);
//            for (year1 = Integer.parseInt(startYear);
//                    year1 <= Integer.parseInt(endYear); year1++) {
//
//                pstmt.setString(1, "" + year1);
//
//                rs = pstmt.executeQuery();
//                rs.next();
//                ketilist = new CountKetiItem();
//                ketilist.setKetiCount(rs.getInt(1));
//                ketilist.setJingFei(rs.getFloat(2));
//                allketi.add(ketilist);
//            }
        try {
            pstmt = conn.prepareStatement(sql);
            // pstmt.setString(1, startYear);
            //pstmt.setString(2, endYear);
            for (int year1 = Integer.parseInt(startYear);
                    year1 <= Integer.parseInt(endYear); year1++) {

                pstmt.setString(1, zhuchirendanwei);
                pstmt.setString(2, "" + year1);
                pstmt.setString(3, "%"+zhuchiren+"%");
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    ketilist = new CountJianDingItem();
                    ketilist.setSourceName(rs.getString(1));
                    ketilist.setJianDingCount(rs.getInt(2));
                   // ketilist.setJingFei(rs.getFloat(3));
                    //ketilist.setYear(rs.getString(4));
                    allketi.add(ketilist);
                }
            }
        } catch (SQLException e) {
            System.out.println("aaa??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }
     
     public List countJiandingByDanweiBYYearShuiping(String startYear,
            String endYear, String zhuchirendanwei,String zhuchiren) {
        List allketi = new ArrayList();
        CountJianDingItem ketilist = null;
        // sql???????????????????????
        String sql = " select count(k.chengguo_id) as ????"
                
                + "  from  jiandingchengguo k "
                + " where SUBSTRING(jiandingriqi,1,4)=? "
                + "and k.wanchengdanwei=? "
                   + "and wanchengren like ?";


        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            // pstmt.setString(1, startYear);
            //pstmt.setString(2, endYear);
            for (int year1 = Integer.parseInt(startYear);
                    year1 <= Integer.parseInt(endYear); year1++) {

                pstmt.setString(1, "" + year1);
                pstmt.setString(2, zhuchirendanwei);
                pstmt.setString(3, "%"+zhuchiren+"%");

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    ketilist = new CountJianDingItem();
                    //ketilist.setSourceName(rs.getString(1));
                    ketilist.setJianDingCount(rs.getInt(1));
                    //ketilist.setJingFei(rs.getFloat(2));
                    //ketilist.setYear(rs.getString(4));
                    allketi.add(ketilist);
                }
            }

        } catch (SQLException e) {
            System.out.println("bbbb??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }
    public List countJiandingByDanweiAndYearChuizhi(String startYear,
            String endYear,String danwei,String zhuchiren) {
        List allketi = new ArrayList();
        CountJianDingItem ketilist = null;
        Connection conn = DBConnection.getConnection();
        PreparedStatement pstmt = null;

        // sql???????????????????????
//        String sql = "select count(*),sum(jingfei) as ?? from t_jingfei k"
//                + " where substring(boruriqi,1,4)=? "
//                + "and zhchirendanwei=?";

        String sql = " select count(chengguo_id) as ????,"
                + " SUBSTRING(jiandingriqi,1,4)"
                + " as year from source_table s left join jiandingchengguo k"
                + " on k.ketilaiyuan=s.sourceid "
                + " and SUBSTRING(jiandingriqi,1,4)>=? "
                + " and SUBSTRING(jiandingriqi,1,4)<=?"
                + " and wanchengdanwei=? and wanchengren like ?"
                + " group by s.sourceid";
        String sql2 = "select count(chengguo_id)"
                + " from jiandingchengguo k"
                + " where substring(jiandingriqi,1,4)>=? "
                + " and  substring(jiandingriqi,1,4)<=? "
                + " and wanchengdanwei=?"
                + " and wanchengren like ?";


        ResultSet rs = null;
        int year1 = 0;
        try {
            pstmt = conn.prepareStatement(sql);
//            for (year1 = Integer.parseInt(startYear);
//                    year1 <= Integer.parseInt(endYear); year1++) {

                pstmt.setString(1, startYear);
                pstmt.setString(2, endYear);
                pstmt.setString(3, danwei);
                pstmt.setString(4, "%"+zhuchiren+"%");

                rs = pstmt.executeQuery();  
            while(rs.next()){
                ketilist = new CountJianDingItem();
                ketilist.setJianDingCount(rs.getInt(1));
                allketi.add(ketilist);
            }
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, startYear);
            pstmt.setString(2, endYear);
            pstmt.setString(3, danwei);
             pstmt.setString(4, "%"+zhuchiren+"%");

            rs = pstmt.executeQuery();
            rs.next();
            ketilist = new CountJianDingItem();
            ketilist.setJianDingCount(rs.getInt(1));
            allketi.add(ketilist);

        } catch (SQLException e) {
            System.out.println("??????????");
            System.out.println(e.getMessage());
        } finally {
            DBConnection.close(conn, pstmt, rs);
        }
        return allketi;
    }



}