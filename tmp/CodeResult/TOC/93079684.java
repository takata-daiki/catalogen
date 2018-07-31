


package convertor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import mainWindow.MainWindow;

/**
 *
 * @author Hubacek
 */
public class Convertor {

    String acctualPath = System.getProperty("user.dir");
    public String workspacePath = acctualPath + File.separator + "workspace";
    String outputPath = acctualPath + File.separator + "output";
    String kindleGenPath = acctualPath + File.separator + "kindlegen";

    
    File model = new File(workspacePath + File.separator + "model.html");
    File contentHTML = new File(workspacePath + File.separator + "content.html");
    File TOC = new File(workspacePath + File.separator + "toc.html");
    File cover = null;
    File OPF = new File(workspacePath + File.separator + "ekniha.opf");
    File NCX = new File(workspacePath + File.separator + "toc.ncx");
    File intro = new File(workspacePath + File.separator + "uvod.html");
    
    boolean clearDir;

    Vector<String> toc = new Vector<>();
    private final String content;
    private Metadata metadata;
    private String outputFormat="mobi"; 
    private File outputFile=null;
    
    
    public Convertor(String content, Metadata metadata) {
        this.content = content;
        this.metadata = metadata;
        deteleWorkspaceFiles();

    }



    public boolean convert() {
        boolean directoriesExist = checkPaths();
        
        boolean status = false;

        if (directoriesExist) {
            Vector<File> files = new Vector<File>();
            
            this.creatIntro();
            this.createContent(replaceChapterTag(content));
            this.createTOC();
            if (setCover()) files.add(cover);

            
            
            
            files.add(intro);
            files.add(TOC);
            files.add(contentHTML);

            XMLBuilder XMLbuild = new XMLBuilder(metadata, files);
            XMLbuild.getNCX(NCX,toc);
            files.add(NCX);
            XMLbuild.getOPF(OPF);

            status = convertToEbook(OPF);
            
            boolean transfer = transferToOutputFolder();
            
            if(transfer){
                deteleWorkspaceFiles();
            }else{
                
                JOptionPane.showMessageDialog(null, "Soubor se nepodařilo umístit do složky output, zůstal ve složce workspace");
            }
            
            
        }else{
            JOptionPane.showMessageDialog(null, "Nepodařilo se vytvořit pracovní složky");
        }

        return directoriesExist && status;
    }

    private boolean copyFile(File source, File dest) {
        boolean copy = true;
        try {
            Files.copy(source.toPath(), dest.toPath());
        } catch (IOException ex) {
            copy = false;
        }
        return copy;
    }

    private String replaceChapterTag(String content) {
        String patternString = "\\[headline\\]";
        String contentToReplace = content;

        int chapterID = 1;

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(contentToReplace);
        Vector<String> cuts = new Vector<>();
        while (matcher.find()) {
            //**
            toc.add("content.html#chapter" + chapterID );
            contentToReplace = pattern.matcher(contentToReplace).replaceFirst("<h2 id=\"chapter" + chapterID + "\">");
            chapterID++;
        }
        toc.add(0, TOC.getName());
        //String startHeadline = content.replaceAll("\\[headline\\]", "<h2>");
        String endHeadline = contentToReplace.replaceAll("\\[/headline\\]", "</h2>");
        
        String startHeadline2 = endHeadline.replaceAll("\\[headline2\\]", "<h3>");
        String endHeadline2 = startHeadline2.replaceAll("\\[/headline2\\]", "</h3>");
        
        String startBold = endHeadline2.replaceAll("\\[bold\\]", "<b>");
        String endBold = startBold.replaceAll("\\[/bold\\]", "</b>");
        String startItalic = endBold.replaceAll("\\[italic\\]", "<i>");
        String endItalic = startItalic.replaceAll("\\[/italic\\]", "</i>");
        
        String startUnderline = endItalic.replaceAll("\\[underline\\]", "<u>");
        String endUnderline = startUnderline.replaceAll("\\[/underline\\]", "</u>");
        
        String startAlignCenter = endUnderline.replaceAll("\\[align-center\\]", "<p style=\"text-align: center;text-indent:0px;\">");
        String endAlignCenter = startAlignCenter.replaceAll("\\[/align-center\\]", "</p>");
        
        String startAlignLeft = endAlignCenter.replaceAll("\\[align-left\\]", "<p style=\"text-align: left;text-indent:0px;\">");
        String endAlignLeft = startAlignLeft.replaceAll("\\[/align-left\\]", "</p>");
        
        String startAlignRight = endAlignLeft.replaceAll("\\[align-right\\]", "<p style=\"text-align: right;text-indent:0px;\">");
        String endAlignRight = startAlignRight.replaceAll("\\[/align-right\\]", "</p>");
        
        
        return endAlignRight;
    }
        
    public void setPaths(String workingPath, String kindleGenPath, String outputPath) {
        if (kindleGenPath != null) {
            this.kindleGenPath = kindleGenPath;
        }
        if (workingPath != null) {
            this.workspacePath = workingPath;
        }
        if (outputPath != null) {
            this.outputPath = outputPath;
        }

    }

    public boolean setCover() {
        boolean status = metadata.isImageSet();
        if (status) {
            File originalCover = new File(metadata.getImagePath());
            String fileName = originalCover.getName();

            String koncovka = fileName.substring(fileName.lastIndexOf(".")+1);

            cover = new File(this.workspacePath + File.separator + "cover." + koncovka);
            //metadata.setImagePath(cover.getAbsolutePath());
            status = copyFile(originalCover, cover);
        }

        return status;
    }

    private File createContent(String docWithHtml) {
        String styles = "<style>"
                + "h2 {page-break-before:always;}"
                + "hr {page-break-before:always;visibility: hidden;}"
                + "</style>";
        String head = "<html><head><meta http-equiv=\"content-type\" "
                + "content=\"text/html;charset=utf-8\">" + styles + "</head><body>";
        String ending = "</body></html>";

        BufferedWriter writer = null;
        try {
            if (!contentHTML.exists()) contentHTML.createNewFile();
            writer = new BufferedWriter(new FileWriter(contentHTML, true));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrintWriter pw = null;
        pw = new PrintWriter(writer);
        pw.append(head);
        pw.append(docWithHtml);
        pw.append(ending);
        pw.close();
        return contentHTML;
    }

    private boolean convertToEbook(File fileToConvert) {
        boolean status = false;
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec("cmd.exe /c start kindlegen " + fileToConvert  , null, new File(kindleGenPath));
            status = true;
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;

    }

    private void createTOC() {
        String head = "<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>TOC</title><body>";
        String tail = "</body></html>";
        String headline = "<h1 id=\"toc\">Obsah</h1>";


        BufferedWriter writer = null;
        try {
            if (!TOC.exists()) {
                TOC.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(TOC, true));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrintWriter pw = null;
        pw = new PrintWriter(writer);
        pw.append(head);
        pw.append(headline);
        int chapterID=1;
        for (String link : toc) {
            pw.append("<a href=\"content.html#chapter" + chapterID + "\">" + "Kapitola " + chapterID + "</a>");
            pw.append("<br/>");
            chapterID++;

        }
        pw.append(tail);
        pw.close();

    }

    private boolean checkPaths() {
        boolean status = false;

        File workingPathDir = new File(workspacePath);

        boolean workDir = true;
        boolean outDir = true;
        boolean kidlegenDir = true;

        if (!workingPathDir.exists()) {
            workDir = workingPathDir.mkdir();
        }
        File outFileDir = new File(outputPath);
        if (!outFileDir.exists()) {
            outDir = outFileDir.mkdir();
        }
        //File kindleDir = new File(kindleGenPath);
        File kindleDir = new File(kindleGenPath);
        if (!kindleDir.exists()) {
            JOptionPane.showMessageDialog(null, "Je potřeba nastavit cestu k programu kindlegen");
            kidlegenDir = false;
        }
        if (workDir && outDir && kidlegenDir) {
            status = true;
        }
        return status;

    }

    private void creatIntro() {
        String styles = "<style>"
                + "h1 {text-align: center; text-indent: 0;}"
                + "h3 {text-align: center; text-indent: 0;}"
                + "</style>";
        String header = "<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">"
                + "<meta name=\"cover\" content=\"cover.jpg\">"+styles;
        

        String bookTitle = metadata.getTitle();
        String titleTag = "<title>" + bookTitle + "</title>";

        String tail = "</body></html>";

        BufferedWriter writer = null;
        try {
            if (!TOC.exists()) {
                TOC.createNewFile();
            }
            writer = new BufferedWriter(new FileWriter(intro, true));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrintWriter pw = null;
        pw = new PrintWriter(writer);
        pw.append(header);
        pw.append(titleTag);
        pw.append("</head><body>"
                + "");
        pw.append("<h1>Nazev knihy: <br>"+metadata.getTitle()+"</h1>");
        pw.append("<h3>Autor: "+metadata.getCreator()+"</h3>");
        

        pw.append(tail);
        pw.close();

    }

    private boolean deteleWorkspaceFiles() {
        boolean deleteStatus = false;
        File workspace = new File(workspacePath);
        File[] filesToDetele = workspace.listFiles();
        if (filesToDetele != null) {
            for (File fileToDetele : filesToDetele) {
                if (!fileToDetele.delete()) {
                    deleteStatus = false;
                }
            }
        }
        
        return deleteStatus;
    }

    private boolean transferToOutputFolder() {

        String ebookExtension = (outputFormat.equals("mobi"))?"mobi":"epub";

        int counter = 1;
        String outputFileString = workspacePath+File.separator+"ekniha."+ebookExtension;
        String outputFileNewName= outputPath+File.separator+"ekniha"+counter+"."+ebookExtension;
        do {            
            counter++;
            outputFileNewName= outputPath+File.separator+"ekniha"+counter+"."+ebookExtension;
        } while(new File(outputFileNewName).exists());


        File finalFile = new File(outputFileString);
        outputFile = new File(outputFileNewName);
        


        JOptionPane.showMessageDialog(null,"Výsledná e-kniha je umístěna v:"+ outputFile.getAbsolutePath());

        boolean success = copyFile(finalFile, outputFile);
        
        return success;
    }



}
