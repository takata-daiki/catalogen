package com.vizhen.poihelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import android.util.Log;

public class SimpleWord2Html
{
    private static final String TAG = "SimpleWord2Html";

    private String docFileName;
    
    private String outHtmlName;
    
    private String picCachePath;
    
    public SimpleWord2Html(String docName, String outName, String picCache)
    {
        this.docFileName = docName;
        this.outHtmlName = outName;
        this.picCachePath = picCache;
    }
    
    /**
     * 将word文档转换为html文件
     * @return true 成功，flase 失败
     */
    public boolean word2Html()
    {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        try
        {

            
            fileInputStream = new FileInputStream(docFileName);
            
            HWPFDocumentCore hwpfDocument = WordToHtmlUtils.loadDoc(fileInputStream);
            
            Log.d(TAG, "hwpfDocument:" + hwpfDocument);
            
            Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(newDocument);
            
            
            Log.d(TAG, "wordToHemlConverter:" + wordToHtmlConverter);
           
            wordToHtmlConverter.setPicturesManager(new PictureSaving());
            wordToHtmlConverter.processDocument(hwpfDocument);
            
            StringWriter stringWriter = new StringWriter();
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.METHOD, "html");
            transformer.transform(new DOMSource(wordToHtmlConverter.getDocument()), new StreamResult(stringWriter));
            
            fileOutputStream = new FileOutputStream(new File(outHtmlName));
            fileOutputStream.write(stringWriter.toString().getBytes());
            fileOutputStream.flush();
            
            fileOutputStream.close();
            fileInputStream.close();
            
            return true;
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerFactoryConfigurationError e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (TransformerException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    
    class PictureSaving implements PicturesManager
    {
        
        @Override
        public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,
            float heightInches)
        {
            try
            {
                OutputStream out = new FileOutputStream(new File(picCachePath, suggestedName));
                if (content != null && content.length > 0)
                {
                    out.write(content, 0, content.length);
                }
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return suggestedName;
        }
        
    }
}
