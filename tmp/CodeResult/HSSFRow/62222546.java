/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsxk;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import test.dao.TeacherDAOImpl;
import test.util.Util;
/**
 *
 * @author tai
 */
public class poireadexcel {






 /**//*
  * ?Excel????????????????
  **/
    public static void main(String args[]){

  ArrayList list = new ArrayList();
  int input = 0; //????
  String id=null;

  String name = null;
  String sex = null;

  String csrq = null;
  String xydh="";
  String zc="";
  String xuewei="";
  String xueli="";
  String sxzy;
  String cszy;


  try {
   //??????????inputstream????HSSFWordbook??
        HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream("f:/mingce.xls"));
         HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);//??????
   HSSFRow hssfrow = hssfsheet.getRow(0);//???
int i=0;
   //?????????????i???????? getNumberOfSheets????????
          //  for (int i = 0; i < hssfworkbook.getNumberOfSheets(); i++) {
           //  hssfsheet = hssfworkbook.getSheetAt(i);
         Map map=Util.danweiDH();
         TeacherDAOImpl teacherDAOImpl=new TeacherDAOImpl();
             //????????,j???? getPhysicalNumberOfRows????
                for (int j = 1; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
                 hssfrow = hssfsheet.getRow(j);
                 //??????????????
                    if (hssfrow == null) {
                     System.out.println("??????????"+i+"?,?"+j+"?");
                     break;
                    }
                    /** *//**?EXCEL??? j ?????????????*/
                   
                   
                     id=   new Integer((int)hssfrow.getCell( 0).getNumericCellValue()).toString();
                     if(id.length()==1){
                         id="0000"+id;
                     }else if(id.length()==2){
                         id="000"+id;
                     }
                    else if(id.length() == 3)
                    {
                         id="00"+id;
                     }
                     else if(id.length()==4)
                    {
                         id="0"+id;
                     }

                     xydh = hssfrow.getCell(1).getStringCellValue().trim();
                     xydh=(String)map.get(xydh);
                     name = hssfrow.getCell(3).getStringCellValue().trim();
                     sex = hssfrow.getCell(4).getStringCellValue().trim();
                     csrq = Util.formatDate(  hssfrow.getCell(5).getDateCellValue());
                     xueli= hssfrow.getCell(6).getStringCellValue().trim();
                     xuewei= hssfrow.getCell(7).getStringCellValue().trim();
                     zc= hssfrow.getCell(8).getStringCellValue().trim();
                     sxzy= hssfrow.getCell(9).getStringCellValue().trim();
                     cszy=hssfrow.getCell(10).getStringCellValue().trim();
                     teacherDAOImpl.insert(id,
                            xydh, name, sex,
                             csrq, xueli, xuewei, zc, sxzy, cszy);



                     



                        System.out.print("  "+id);
                        System.out.print(" "+name);
                        System.out.print(" "+sex);
                        System.out.println(" "+csrq);
                        System.out.println(" "+xydh);

                        
                    }
                    //?????1
                    
                
            //}

          //  session.saveObjs(list.toArray());
        } catch(Exception e) {
            System.out.println(e.getMessage());

        }
 }
}



