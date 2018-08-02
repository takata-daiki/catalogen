/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import server.ejb.BeslagslisterFacade;
import server.ejb.DoererFacade;
import server.ejb.KategorierFacade;
import server.ejb.ProsjekterFacade;
import server.entity.Beslagslister;
import server.entity.Doerer;
import server.entity.Kategorier;
import server.entity.Prosjekter;

/**
 *
 * @author
 * David og Mikael
 */
@ManagedBean(name = "fileUploadController")
@SessionScoped
public class FileUploadController implements Serializable {

    private String destination = "C:\\PFUplaod\\";
    private UploadedFile uploadedFile = null;
    private Prosjekter newProject;
    private ArrayList<Doerer> alleDoerer = new ArrayList();
    public static final String[] feltnavn = {"Dørnr.", "Ant.", "Revisjon", "Bygning", "Etasje", "Romtype", "ID",
        "Dørtype", "Dørkonstruksjon", "Dørkonstruksjon", "Format", "Slagr.", "Glassåpning", "Fløy", "Overfl. Dør",
        "Karm", "Overfl. Karm", "Terskel", "Sparkepl", "Ant. Sp.Pl", "Pakker", "Låskasse", "Ant. Låsk.", "Sluttstykke",
        "Ant. Slutt.", "Dørtykkelse", "Sylinder", "Ant. Syl", "Tilleggslås", "Ant. Tilleggslås", "Syl. Tillegg.",
        "Ant. Syl.til", "Overflate Syl", "Vrider", "Ant. Vrider", "Skilt I", "Ant. Skilt", "Skilt II",
        "Ant. Skilt II", "Overflate Vrider", "Håndtak", "Ant. Håndtak", "Overflate Håndtak", "Skyvedørsoppheng",
        "Ant. Skyvedørsopphenh", "Skåte", "Ant. Skåte", "Overflate Skåte", "Dørlukker", "Ant. Dørlukker",
        "Utstyr Dørlukker I", "Ant. Utst. Dørlukker", "Plassering Dørlukker", "Utstyr Dørlukker II",
        "Ant. Utstyr Dørlukker II", "Overflate Dørlukker", "Holdemagnet", "Ant. Holdemagnet", "Karmoverføring",
        "Ant. Karmoverføring", "Magnetkontakt", "Ant. Magnetkontakt", "Nødutstyr", "Ant. Nødutstyr",
        "Kortleser Inn", "Ant. Kort.Inn", "Overflate Kortleser", "Kortleser Ut", "Ant. Kort.Ut",
        "Overflate Kortleser", "Div. Beslag I", "Ant. Div. Beslag I", "Div. Beslag II", "Ant. Div. Beslag II",
        "Div. Beslag III", "Ant. Div. Beslag III", "Div. Beslag IV", "Ant. Div. Beslag IV", "Utforing", "Dytting",
        "Fuging", "Listverk", "Merknad", "Merknad Beslag", "Montert Av", "Montert Dato"};


    public void upload(FileUploadEvent event) {
        FacesMessage msg;
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        } catch (IOException e) {
            msg = new FacesMessage("Failure! ", event.getFile().getFileName() + " failed to upload.");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void copyFile(String fileName, InputStream in) {
        FacesMessage msg = null;
        File filen = null;
        try {
            try (OutputStream out = new FileOutputStream(filen = new File(destination + fileName))) {
                int read;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                in.close();
                out.flush();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success! ", fileName + " was stored on system."));
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Failure! ", fileName + " wasn't stored on system."));
        } finally {
            createProjectFromFile(filen.getAbsolutePath());
        }
    }

    private void caseCellNumber(int cellNr, String cellInfo, Doerer door, Row row) {

        switch (cellNr) {
            case 1:
                //Dørnr
                door.setDorNr(cellInfo); //Denne tror jeg vi kan fjerne...
                return;
            case 2:
                //Antall dører
                //Dette tror jeg alltid skal være 1, derfor gjør jeg ingenting her
                //Her kan vi eventuelt legge inn lur kode senere...
                /*
                 int antDoerer = Integer.parseInt(cellInfo);
                 if (antDoerer != 1){
                 //Noe er rart, gjør noe!?
                 } else {
                 //Do nothing
                 }*/
                return;
            case 3:
                //Revisjon. Kun i bruk i Microdoor.
                //Do nothing
                return;
            case 4:
                //Bygning
                door.setBygning(cellInfo.replace(".0", ""));
                return;
            case 5:
                //Etasje
                door.setEtasje(cellInfo.replace(".0", ""));
                return;
            case 6:
                //Romtype
                door.setRomtype(cellInfo);
                return;
            case 7:
                //dørID
                door.setId(cellInfo);//Denne tror jeg vi kan fjerne...
                return;
            case 8:
                //Dørtype
                door.setDoertype(cellInfo);
                return;
            case 9:
                //Dørkonstruksjon
                door.setDoerKonstruksjon1(cellInfo);
                return;
            case 10:
                //Dørkonstruksjon
                door.setDoerKonstruksjon2(cellInfo);
                return;
            case 11:
                //Format
                door.setFormat(cellInfo);
                return;
            case 12:
                //Slagretning
                door.setSlagretning(cellInfo);
                return;
            case 13:
                //Glassåpning
                door.setGlassapning(cellInfo);
                return;
            case 14:
                //Fløy
                door.setFloy(cellInfo);
                return;
            case 15:
                //Overflate dør
                door.setOverflatedoer(cellInfo);
                return;
            case 16:
                //Karm
                door.setKarm(cellInfo);
                return;
            case 17:
                //Overflate karm
                door.setOverflatekarm(cellInfo);
                return;
            case 18:
                //Terskel
                door.setTerskel(cellInfo);
                return;
            case 21:
                //Beslag
                createBeslagFromFile(door, cellInfo, "", feltnavn[cellNr - 1]);
                return;
            case 26:
                //Dørtykkelse
                //Mangler i db
                return;
            case 29:
                //Beslag
                for (int i = 0; i <= (int) ((row.getCell(cellNr).getNumericCellValue())); i++) {
                    createBeslagFromFile(door, cellInfo, ((row.getCell(32) != null) ? (row.getCell(32).getStringCellValue()) : ""), feltnavn[cellNr - 1]);
                }
                return;
            case 34:
                //Beslag
                for (int i = 0; i <= (int) ((row.getCell(cellNr).getNumericCellValue())); i++) {
                    createBeslagFromFile(door, cellInfo, ((row.getCell(39) != null) ? (row.getCell(39).getStringCellValue()) : ""), feltnavn[cellNr - 1]);
                }
                return;
            case 49:
                //Beslag                                                                
                for (int i = 0; i <= (int) ((row.getCell(cellNr).getNumericCellValue())); i++) {
                    createBeslagFromFile(door, cellInfo, "", (feltnavn[cellNr - 1] + ((row.getCell(52) != null) ? (" Plassering: " + (row.getCell(52).getStringCellValue())) : "")));
                }
                return;
            case 51:
                //Beslag
                for (int i = 0; i <= (int) ((row.getCell(cellNr).getNumericCellValue())); i++) {
                    createBeslagFromFile(door, cellInfo, ((row.getCell(55) != null) ? (row.getCell(55).getStringCellValue()) : ""), feltnavn[cellNr - 1]);
                }
                return;
            case 79:
                //Utforing
                door.setUtforing(cellInfo);
                return;
            case 80:
                //Dytting
                door.setDytting(cellInfo);
                return;
            case 81:
                //Fuging
                door.setFuging(cellInfo);
                return;
            case 82:
                //Listverk
                door.setListverk(cellInfo);
                return;
            case 83:
                //merknad
                //Denne må vi definitivt få lagt inn i db!!!
                return;
            case 84:
                //merknad beslag
                //Denne må vi definitivt få lagt inn i db!!!
                return;
            case 85:
                //Montert av
                //Denne skal inn på beslagsiden via appen
                return;
            case 86:
                //Montert dato
                //Denne kan være datoen døra er komplettert, men dette kommer isåfall også fra mobilappen.
                return;
            default:
            //Do nothing

        }

        //Her kan vi samle opp en del tilfeller:
        if (cellNr == 19 || cellNr == 22 || cellNr == 24 || cellNr == 27 || cellNr == 31 || cellNr == 36
                || cellNr == 38 || cellNr == 44 || cellNr == 54 || cellNr == 57 || cellNr == 59 || cellNr == 61
                || cellNr == 63 || cellNr == 71 || cellNr == 73 || cellNr == 75 || cellNr == 77) {
            //Beslag
            createBeslagFromFile(door, cellInfo, "", feltnavn[cellNr - 1]);

            //}
        } else if (cellNr == 41 || cellNr == 46 || cellNr == 65 || cellNr == 68) {
            //Beslag
            for (int i = 0; i <= (int) ((row.getCell(cellNr).getNumericCellValue())); i++) {
                createBeslagFromFile(door, cellInfo, ((row.getCell(cellNr + 1) != null) ? (row.getCell(cellNr + 1).getStringCellValue()) : ""), feltnavn[cellNr - 1]);
            }
        }

    }
    @EJB
    DoererFacade dFacade;
    @EJB
    ProsjekterFacade pFacade;
    //@EJB ProgressBean progress;

    public void createProjectFromFile(String fileName) {
        //Opprett prosjektet:
        newProject = new Prosjekter(); //Er prosjektID autoincrement!?    


        FileInputStream myInput = null;

        try {

            myInput = new FileInputStream(fileName);
            Workbook wb = WorkbookFactory.create(myInput);

            // Gets the sheets from workbook
            Sheet sheet = wb.getSheetAt(0);
            //int first = sheet.getFirstRowNum() + 1;
            //int last = sheet.getLastRowNum() + 1;

            //Gets the cells from sheet
            Iterator rowIter = sheet.rowIterator();

            //Hopp over raden med kolonneoverskrifter:
            Row row = (Row) rowIter.next();


            while (rowIter.hasNext()) { //Opprett ny dør
                row = (Row) rowIter.next();
                Doerer door = new Doerer();
                door.setProsjektIDfk(newProject);
                ArrayList<Beslagslister> alleBeslag = new ArrayList();
                door.setBeslagslisterCollection(alleBeslag);


                //Opprett døra som ligger i den aktuelle raden
                Iterator cellIter = row.cellIterator();
                Cell cell = null;
                String cellInfo = null;
                while (cellIter.hasNext()) {
                    cell = (Cell) cellIter.next();
                    if (cell.getCellType() == 1) {
                        cellInfo = (cell.getStringCellValue());
                    } else {
                        Double doub = cell.getNumericCellValue();
                        cellInfo = (doub.toString());
                    }
                    caseCellNumber(cell.getColumnIndex() + 1, cellInfo, door, row);

                }
                alleDoerer.add(door);

                //first++;
            }

        } catch (UnsupportedEncodingException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        newProject.setDoererCollection(alleDoerer);
//        pFacade.create(newProject);
        try {
            pFacade.create(newProject);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Database: ", ResourceBundle.getBundle("/Bundle").getString("ProsjekterUpdated")));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Database: ", ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured") + " " + e));
        }

    }

    /**
     *
     * @return
     */
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    /**
     *
     * @param
     * uploadedFile
     */
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    @EJB
    KategorierFacade kFacade;
    @EJB
    BeslagslisterFacade bFacade;

    private void createBeslagFromFile(Doerer door, String beslagNavn, String overflate, String kategorinavn) {

        Beslagslister beslag = new Beslagslister();

        beslag.setBeslagnavn(beslagNavn);
        beslag.setOverflate(overflate);

        beslag.setDoridfk(door);

        Kategorier kategori = findKat(kategorinavn);

        beslag.setIDBeslagsliste(5);

        beslag.setKategoriidfk(kategori);
        door.getBeslagslisterCollection().add(beslag);

    }

    private Kategorier findKat(String kategorinavn) {
        List kategorier = kFacade.findAll(); //Kontroller for NullPointerException
        for (Object obj : kategorier) {
            Kategorier kat = (Kategorier) obj;
            if (kat.getNavn().contentEquals(kategorinavn)) {
                return kat;
            }
        }
        Kategorier kategori = new Kategorier();
        kategori.setNavn(kategorinavn);
        kFacade.create(kategori);
        return kategori;
    }
}
