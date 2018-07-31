/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wsxk;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 *
 * @author tai
 */
public class poireadexcel1 {






 /**//*
  * ?Excel????????????????
  **/
    public static void main(String args[]){

  ArrayList list = new ArrayList();
  int input = 0; //????
  String name = null;
  String sex = null;
  String age = null;

  try {
   //??????????inputstream????HSSFWordbook??
        HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream("e:/book1.xls"));
         HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);//??????
   HSSFRow hssfrow = hssfsheet.getRow(0);//???
int i=0;
   //?????????????i???????? getNumberOfSheets????????
          //  for (int i = 0; i < hssfworkbook.getNumberOfSheets(); i++) {
           //  hssfsheet = hssfworkbook.getSheetAt(i);

             //????????,j???? getPhysicalNumberOfRows????
                for (int j = 1; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
                 hssfrow = hssfsheet.getRow(j);
                 //??????????????
                    if (hssfrow == null) {
                     System.out.println("??????????"+i+"?,?"+j+"?");
                     break;
                    }
                    /** *//**?EXCEL??? j ?????????????*/
                    if (hssfrow.getCell((short) 0) == null) {
                     name = "";
                    } else if (hssfrow.getCell((short) 0).getCellType() == 0) {
                     name = new Double(hssfrow.getCell((short) 0).getNumericCellValue()).toString();
                    }
                    //??EXCEL?????????????
                    else {
                     name = hssfrow.getCell((short) 0).getStringCellValue().trim();
                    }
                    /** *//**?EXCEL??? j ?????????????*/
                    //??
                    if(hssfrow.getCell((short) 1) == null){
                     sex = "";
                    } else if(hssfrow.getCell((short) 1).getCellType() == 0) {
                        sex = new Double(hssfrow.getCell((short) 1).getNumericCellValue()).toString();
                    }
                    //??EXCEL?????????????
                    else {
                        sex = hssfrow.getCell((short) 1).getStringCellValue().trim();
                    }
                    /** *//**?EXCEL??? j ?????????????*/
                    //??
                    if(hssfrow.getCell((short) 2) == null){
                     age = "";
                    } else if(hssfrow.getCell((short) 2).getCellType() == 0) {
                        age = new Double(hssfrow.getCell((short) 2).getNumericCellValue()).toString();
                    }
                    //??EXCEL?????????????
                    else {
                        age = hssfrow.getCell((short) 2).getStringCellValue().trim();
                    }

                    name = name.trim();
                    sex = sex.toUpperCase();

                    if (name.equals("")) {
                     //error.setName(name);
                    // error.setMessage("??????");

                  //   list.add(error);
                     continue;
                    } else {
                        System.out.print(" "+name);
                        System.out.print(" "+sex);
                        System.out.println("  "+age);
//                     fm.setName(name);
//                     fm.setSex(sex);
//                     fm.setAge(age);
//
//                     session.save(fm);
                    }
                    //?????1
                    input++;
                }
            //}

          //  session.saveObjs(list.toArray());
        } catch(Exception e) {
            System.out.println(e.getMessage());

        }
 }
}



