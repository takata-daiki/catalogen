package plans;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: nickl
 * Date: 02.07.13
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public class DocXReader {


    public static void main(String[] args) throws Exception {

        InputStream fs  = new FileInputStream("/home/nickl/virtualshared/230700.62-01(0)-1 Информатика и программирование.docx");

        XWPFDocument doc = new XWPFDocument(fs);

        for (int i = 0; i < doc.getParagraphs().size(); i++) {
            XWPFParagraph paragraph = doc.getParagraphs().get(i);


            String text = paragraph.getText();
            if (text.contains("Общий объём занятий в интерактивной форме "))
            {
                for (int j = 0; j < paragraph.getRuns().size(); j++) {
                    paragraph.removeRun(j);
                }
                paragraph.createRun().setText(text.replace(
                        "Общий объём занятий в интерактивной форме ",
                        "Общий объём занятий, проводимых в интерактивной форме, "));
            }

        }

            doc.write(new FileOutputStream("/home/nickl/virtualshared/230700.62-01(0)-1 Информатика и программирование2.docx"));

    }



}
