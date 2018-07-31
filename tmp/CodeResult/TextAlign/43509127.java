import processing.core.*; 
import processing.data.*; 
import processing.opengl.*; 

import java.awt.Rectangle; 
import javax.swing.JOptionPane; 
import javax.swing.SwingUtilities; 
import processing.pdf.*; 
import java.util.concurrent.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class TrioVis extends PApplet {

//Test migrating








int _STAGE_WIDTH = 1200;
int _STAGE_HEIGHT = 780;
PFont _FONT;

String _PROGRESS_MESSSAGE = "";

DataSet data;
PredictorThread predictor;


boolean saveFrame = false;
boolean fileChosen = false;
boolean isLoading = true;
boolean isWritingFile = false;
boolean isUpdating = false;// while variants are migrating

int updateCounter = 0;

boolean _MIGRATING = true;


//********* color ************ //
int colorBackground = 0xFFEDEDED;
int color_cyan_trans = color(0, 174, 237, 120);
int color_transparent = color(255, 255, 255, 0);
int color_lightgray_trans = color(80, 80, 80, 120);
int[] colorSampleArray = {0xFF66C2A5, 0xFFFC8D62, 0xFF8DA0CB};
int[] colorTransSampleArray = {0x7D66C2A5, 0x7DFC8D62, 0x7D8DA0CB};
int colorText = 0xFF666666;
int colorLightGray = 0xFFE6E6E6;
int colorDarkGray = 0xFF999999;
int colorGrayTrans = 0x7DCCCCCC;
int colorSelectionTrans = 0x66009CCE;//00698c;
int colorWhite = 0xFFFFFFFF;
int colorGray = 0xFFCCCCCC;
int colorPossible = 0xFFFFFFFF;
int colorImpossible = 0xFFCCCCCC;
int colorCyan = color(0, 174, 237);

public void setup(){
	size(_STAGE_WIDTH, _STAGE_HEIGHT);
	_FONT = createFont("Monospaced", 10);
	textFont(_FONT);
	smooth();

    // selectInput("Select a parent VCF file:", "parentOne");

    
	//MODEL class
	data = new DataSet();
    //setup controller before starting the thread
    setupController();
	// Runnable chooseFile  = new FileLoaderThread();
	// new Thread(chooseFile).start();

    // updator = new UpdateThread(1000, "variant_updator");
    if(isTestMode){
        fileChosen = true;
        startLoadingFiles();
    }
}

public void parentOne(File selection){
    if(selection == null){
        println("file was not selected");

    }else{
        println("selected file is:"+selection.getAbsolutePath());
        _files[0] = selection;
    }
}
public void parentTwo(File selection){
     if(selection == null){
        println("file was not selected");
    }else{
        println("selected file is:"+selection.getAbsolutePath());
        _files[1] = selection;
    }
}

public void child(File selection){
     if(selection == null){
        println("file was not selected");
    }else{
        println("selected file is:"+selection.getAbsolutePath());
        _files[2] = selection;
    }
}

public void startLoadingFiles(){
    Runnable chooseFile  = new FileLoaderThread();
    new Thread(chooseFile).start();
}

//called once the data is loaded
public void initialize(){
	println("--- initialize()");
    println("debug:total number of variants = "+data.allVariants.size());
    String sampleData = "";
	for(int i = 0; i<data.samples.length; i++){
        Sample s = data.samples[i];
        println("debug:"+s.sampleID+": total row count ="+ s.rows.size());
        sampleData += s.sampleID+":"+s.rows.size()+" variants\n";
    }
    sampleData+="total number of variants:"+data.allVariants.size();
    _PROGRESS_MESSSAGE = "Processing the data...";
    data.setupZygosity(); //check zygosity of each Row per sample
    //setup table
    _PROGRESS_MESSSAGE = "Setting up the table... \n"+sampleData;
    data.setupTable();

    //display
    _PROGRESS_MESSSAGE = "Setting up the display...";
    println("Trio.setupCellCoordinates()");
    setupCellCoordinates();

    //make distribution
    _PROGRESS_MESSSAGE = "Setting up the distribution...";
    data.calculateVarfreqDistributions();
    data.calculateCoverageDistributions();

    //predictor thread
    _PROGRESS_MESSSAGE ="Setting up the predictor histogram...";
    if(predictor == null){
        predictor = new PredictorThread();
        new Thread(predictor).start();
    }

    isLoading = false;
}

public void debug(){
    //print cell map
    println("----cell[2][0][0]");
    Cell cell = data.table[2][0][0];
    HashMap<Integer, HashMap> maps = cell.maps;
    Iterator ite = maps.entrySet().iterator();
    while(ite.hasNext()){
        Map.Entry entry = (Map.Entry) ite.next();
        int sampleIndex = (Integer)(entry.getKey());
        HashMap table = (HashMap<Integer, ArrayList<Row>>) entry.getValue();
        ArrayList<Integer> coverages = new ArrayList<Integer>();
        coverages.addAll(table.keySet());
        println("sample:"+_sampleLabels[sampleIndex]);
        println("coverage:"+coverages);
    }
}

public void draw(){
	if (saveFrame) {
        println("start of saving");
        beginRecord(PDF, "Plot-####.pdf");
    }

    background(colorBackground);
// file loading page
    if(!fileChosen){
        // println("file not chosen");
        //Instruction
        fill(colorText);
        textAlign(LEFT, TOP);
        textSize(14f);
        text("Trio analysis: Please select 3 VCF files:", plotX1, plotY1);

        //label
        textAlign(RIGHT, CENTER);
        fill(colorSampleArray[0]);
        text("Parent 1:", label_x_r, (float)path_rects[0].getCenterY());
        fill(colorSampleArray[1]);
        text("Parent 2:", label_x_r, (float)path_rects[1].getCenterY());
        fill(colorSampleArray[2]);
        text("Child:", label_x_r, (float)path_rects[2].getCenterY());

        //path
        //background
        rectMode(CORNER);
        fill(colorWhite);
        noStroke();
        rect(p1_path_rect.x, p1_path_rect.y, p1_path_rect.width, p1_path_rect.height);
        rect(p2_path_rect.x, p2_path_rect.y, p2_path_rect.width, p2_path_rect.height);
        rect(c_path_rect.x, c_path_rect.y, c_path_rect.width, c_path_rect.height);

        //path text
        for(int i = 0; i<_files.length; i++){
            File f = _files[i];
            if(f!= null){
                fill(colorText);
                textSize(12f);
                textAlign(LEFT, CENTER);
                text(f.getName(),path_rects[i].x+MARGIN, (float)path_rects[i].getCenterY());
            }
        }

        //draw button
        //rectangle
        noStroke();

        if(p1_btn.contains(mouseX, mouseY)){
            fill(colorCyan);
        }else{
            fill(colorText);
        }
        rect(p1_btn.x, p1_btn.y, p1_btn.width, p1_btn.height);
        
        if(p2_btn.contains(mouseX, mouseY)){
            fill(colorCyan);
        }else{
            fill(colorText);
        }
        rect(p2_btn.x, p2_btn.y, p2_btn.width, p2_btn.height);
        
        if(c_btn.contains(mouseX, mouseY)){
            fill(colorCyan);
        }else{
            fill(colorText);
        }
        rect(c_btn.x, c_btn.y, c_btn.width, c_btn.height);
        
        if(go_btn.contains(mouseX, mouseY)){
            fill(colorCyan);
        }else{
            fill(colorText);
        }
        rect(go_btn.x, go_btn.y, go_btn.width, go_btn.height);

        //button text
        fill(colorWhite);
        textAlign(CENTER, CENTER);
        textSize(12f);
        text("select", (float)(p1_btn.getCenterX()), (float)(p1_btn.getCenterY()));
        text("select", (float)(p2_btn.getCenterX()), (float)(p2_btn.getCenterY()));
        text("select", (float)(c_btn.getCenterX()), (float)(c_btn.getCenterY()));
        text("go", (float)(go_btn.getCenterX()), (float)(go_btn.getCenterY()));       
    }else if (isLoading && !isWritingFile) { // while loading files
        stroke(160);
        noFill();
        rect(_STAGE_WIDTH / 2 - 150, _STAGE_HEIGHT / 2, 300, 10);
        fill(160);
        float w = map(currentLoaded, 0, fileSize, 0, 300);
        rect(_STAGE_WIDTH / 2 - 150, _STAGE_HEIGHT / 2, w, 10);
        textSize(14);
        textAlign(CENTER);
        fill(160);
        text(_PROGRESS_MESSSAGE, _STAGE_WIDTH / 2, _STAGE_HEIGHT / 2 + 30);
    } else if(!isLoading && isWritingFile){ // while wirting file
        drawVarfreqDistibution();
        drawCoverageDistribution();
        drawUpAndDownBtns();
        drawControllerLabels();
        drawPredictor();
        drawTableLabel();
        drawTable();
        drawGlobalCount();

        //draw white box
        rectMode(CORNERS);
        fill(colorBackground, 200);
        noStroke();
        rect(0,0,_STAGE_WIDTH, _STAGE_HEIGHT);

        //text
        fill(colorText);
        textAlign(CENTER, CENTER);
        text("Writing VCF files....", (_STAGE_WIDTH/2), (_STAGE_HEIGHT/2));

    }else { //finished loading
        // println("draw()");
        drawVarfreqDistibution();
        drawCoverageDistribution();
        drawUpAndDownBtns();
        drawControllerLabels();
        drawTable();
        drawTableLabel();
        drawGlobalCount();
        drawPredictor();
        drawTextBtns();
        
        if(isUpdating){
            // predictor.updateAll();
            //draw updating msg
            updateCounter++;
            String updateMsg = "updating";
            if(updateCounter == 0){

            }else if(updateCounter == 1){
                updateMsg +=".";
            }else if(updateCounter == 2){
                updateMsg +="..";
            }else if(updateCounter == 3){
                updateMsg +="...";
            }else{
                updateCounter = 0;
            }
            textAlign(LEFT, TOP);
            fill(colorText);
            textSize(10f);
            text(updateMsg, updateMsgX, updateMsgY);
            // println("Draw while update");
        }else{
            if(optimisationThread != null){
                if(!optimisationThread.doneOnce){
                    //show progress
                    fill(255, 255, 255, 120);
                    noStroke();
                    rect(0, 0, _STAGE_WIDTH, _STAGE_HEIGHT);
                    //loading bar
                    stroke(160);
                    noFill();
                    rectMode(CORNER);
                    rect(_STAGE_WIDTH / 2 - 150, _STAGE_HEIGHT / 2, 300, 10);
                    fill(160);
                    float w = map(optimisationThread.currentIndex, 0, optimisationThread.totalIteration, 0, 300);
                    rect(_STAGE_WIDTH / 2 - 150, _STAGE_HEIGHT / 2, w, 10);
                    textSize(14);
                    textAlign(CENTER);
                    fill(160);
                    text("Please wait awhile to optimise...", _STAGE_WIDTH / 2, _STAGE_HEIGHT / 2 + 30);
                }else{
                    noLoop();
                }
            }else{
                noLoop();    
            }

        }

    }
    
    if (saveFrame) {
        // this.control.draw();
        endRecord();
        saveFrame = false;
        println("end of saving");
    }
}
class Cell{
	int[] indexes;
	HashSet<Variant> variants;
	HashMap<Integer, HashMap> maps;   //<SampleIndex, Hashmap>

	Rectangle rect; //display rectangle
    int maxCoverage = 200;
    int minCoverage = 1;
    int increment = 5;
    int counterSize = maxCoverage / increment;
    int counterMinIndex = 0;
    int counterMaxIndex = counterSize - 1;

    boolean isSelected = false;

    //constructor
	Cell(int i , int j, int k){
		variants = new HashSet<Variant>();
		indexes = new int[3];
		indexes[0] = i;
		indexes[1] = j;
		indexes[2] = k;
		maps = new HashMap<Integer, HashMap>(); //sampleIndex, HashMap
	}

	//add variant to the cell, and organized in hashmap
	public void addVariant(Variant v) {
        if (!variants.contains(v)) {
            variants.add(v);
            for (int i = 0; i < v.rows.size(); i++) {
                Row r = v.rows.get(i);
                int sampleIndex = r.sampleIndex;
                int coverage = r.coverage;
                Sample sample = data.samples[sampleIndex];
                int currentCoverage = sample.currentCoverage;
                if (coverage >= currentCoverage) {
                    //coverage, array
                    HashMap<Integer, HashSet<Row>> rowArray = maps.get(sampleIndex);
                    if (rowArray == null) {
                        rowArray = new HashMap<Integer, HashSet<Row>>();
                        maps.put(sampleIndex, rowArray);
                    }
                    HashSet<Row> rows = (HashSet<Row>) rowArray.get(coverage);
                    if (rows == null) {
                        rows = new HashSet<Row>();
                        rowArray.put(coverage, rows);
                    }
                    rows.add(r);
                }else{
                    //coverage is below currentCoverage
                }
            }
            //assign cell's index
            // println("debug: old"+Arrays.toString(v.cellIndex)+" new:"+Arrays.toString(indexes));
            v.cellIndex = indexes;
        }else{
            println("Error:Cell:addVariant(): cell alreay contains the variant");
        }
    }

    //remove all the rows assoicated with the variant
    public void removeVariant(Variant v) {
        if(variants.contains(v)){
            variants.remove(v);
            ArrayList<Row> rows = v.rows;
            for (int i = 0; i < rows.size(); i++) {
                Row r = rows.get(i);
                int coverage = r.coverage;
                int sampleIndex = r.sampleIndex;
                HashMap hm = maps.get(sampleIndex);
                if(hm == null){
                    Sample sample = data.samples[sampleIndex];
                    int currentCoverage = sample.currentCoverage;
                    if(coverage <= currentCoverage){
                        // println("Cell.removeVariant(): the row was not added when moved previously: coverage="+coverage+" cCoverage="+currentCoverage);
                    }else{
                        // println("Error:Cell.removeVariant():hm is null: sampleIndex ="+ sampleIndex+"  cellindex="+Arrays.toString(indexes)+  "coverage="+coverage+" cCoverage="+currentCoverage);      
                    }
                }else{
                    HashSet<Row> rowList = (HashSet<Row>) hm.get(coverage);
                    if(rowList == null){
                        // println("Error:Cell.removeVariant(): rowList is null.  sampleIndex ="+ sampleIndex+"  cellindex="+Arrays.toString(indexes)+"coverage="+coverage);
                    }else if (rowList.contains(r)) {
                        //remove
                        rowList.remove(r);
                    }else{
                        // println("Error:Cell.removeVariant(): rowList does not contain the row");
                    }             
                }
            }
        }else{
            println(v.variantID+" is not in the cell:this cell="+Arrays.toString(indexes)+"  v="+Arrays.toString(v.cellIndex));        
        }
    }

    //get histograms
    public int[][] getCountArrays() {
        //returning array
        int[][] result = new int[3][counterSize];
        //sample order
        int[] sampleOrder = _sampleOrder;
        //per sample
        for (int i = 0; i < _sampleOrder.length; i++) {
            int sampleIndex = sampleOrder[i];
            HashMap map = maps.get(sampleIndex); // <coverage, ArrayList>
            if (map == null) {
                //no data for this sample in this cell   
            } else {
                //iterate through
                for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry)it.next();
                    int coverage = (Integer) entry.getKey();
                    int index = coverageToCounterIndex(coverage);
                    HashSet<Row> rows = (HashSet<Row>)entry.getValue();
                    result[i][index] += rows.size();
                }
            }
        }
        return result;
    }

    public int coverageToCounterIndex(int coverage) {
        int index = round(constrain(map(coverage, minCoverage, maxCoverage, counterMinIndex, counterMaxIndex), counterMinIndex, counterMaxIndex));
        return index;
    }

}

// ControlP5 control;
Rectangle[] upBtns, downBtns;
Range[] varfreqRanges = new Range[3];
Slider[] coverageSliders = new Slider[3];

String[] controllerLabel = {"varfreq/zygosity", "coverage", "histogram"};
int[] controllerLabelX = new int[controllerLabel.length];

int _currentBtnSampleIndex = -1;

Range activeRange = null;
Slider activeSlider = null;

Cell _selectedCell = null;
ArrayList<Cell> selectedCells  = null;
HashMap<Integer, HashMap<Integer, Variant>> exportingResult = null; //<chrIndex, <position, Variant>>

String export_label = "export_VCF";
String[] migrating_label = {"with_migration", "without_migration"};
String optimisation_label ="optimise";
Rectangle[] text_btn_rects = new Rectangle[3];
boolean[] text_btn_mouseover ={false, false, false};


OptimisationThread optimisationThread = null;

boolean isOptimising = false;

//set up controller and their coordinates.
public void setupController(){
	int runningY = varfreqY;

    upBtns = new Rectangle[3];
    downBtns = new Rectangle[3];

    int itemCounter = 0;
    for (int i = 0; i < _sampleLabels.length; i++) {
        if (i == 0) {
            controllerLabelX[itemCounter] = varfreqX;
        }
        Range varfreq = new Range(i, _VARFREQ_MIN, _VARFREQ_MAX, _defaultLowCutPoint, _defaultHighCutPoint, varfreqX, runningY, varfreqWidth , varfreqHeight);
        varfreq.update();
        
        itemCounter++;
       	varfreqRanges[i] = varfreq;
        
        if (i == 0) {
            controllerLabelX[itemCounter] = coverageX;
        }
        Slider coverage = new Slider(i, coverageSliderMin, coverageSliderMax, coverageSliderMin, coverageX, runningY, coverageWidth, coverageHeight);
        coverage.update();
        itemCounter++;
        coverageSliders[i] = coverage;

        upBtns[i] = new Rectangle(coverageX + coverageWidth, runningY, MARGIN, coverageHeight / 2);
        downBtns[i] = new Rectangle(coverageX + coverageWidth, runningY + coverageHeight / 2, MARGIN, coverageHeight / 2);

        //reset running values
        itemCounter = 0;
        runningY += varfreqHeight + controllerGapY;
    }
    //preictor
    controllerLabelX[2] = predictorX;

    //text buttons
    int runningX = varfreqX;
    int btn_y = _STAGE_HEIGHT - MARGIN*2;
    //export VCF
    textSize(12);
    textAlign(LEFT, TOP);
    int textWidth = round(textWidth(export_label));
    text_btn_rects[0] = new Rectangle(runningX,btn_y, textWidth, 15);
    runningX += textWidth + MARGIN*2;

    textWidth = round(textWidth(migrating_label[1]));
    text_btn_rects[1] = new Rectangle(runningX, btn_y, textWidth, 15);
    runningX += textWidth + MARGIN*2;
    //optimise btn
    textWidth = round(textWidth(optimisation_label));
    text_btn_rects[2] = new Rectangle(runningX, btn_y, textWidth, 15);


}
//***** Mouse *********//
public void mousePressed(){
    //check hit area
    if(!fileChosen){
        //file not selected yet
        if(p1_btn.contains(mouseX, mouseY)){
            selectInput("Select a parent1 VCF file:", "parentOne");
        }else if(p2_btn.contains(mouseX, mouseY)){
            selectInput("Select a parent2 VCF file:", "parentTwo");
        }else if(c_btn.contains(mouseX, mouseY)){
            selectInput("Select a child/proband VCF file:", "child");
        }else if(go_btn.contains(mouseX, mouseY)){
            println("go button!");
            //check if all files
            boolean allFilesSelected = true;
            for(int i = 0; i<_files.length; i++){
                File f = _files[i];
                if(f == null){
                    allFilesSelected = false;
                    break;
                }
            }
            if(allFilesSelected){
                //load files
                fileChosen = true;
                startLoadingFiles();
            }else{
                println("not all files are selected");
                Runnable jThread = new FileChooseAlert();
                try{
                    SwingUtilities.invokeAndWait(jThread);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }else{ //everything else
        if(!isLoading){
            if(optimisationThread != null ){
                if(!optimisationThread.doneOnce){
                    //do nothing what so ever
                    println("ignore input");
                    return;
                }
            }
            println(" did it really ignore?");
            //y range
            if(mouseY > plotY1 && mouseY <plotY2){
                //cell area
                //check if it hits a cell
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j< 3; j++){
                        for(int k = 0; k < 3; k++){
                            Cell cell = data.table[i][j][k];
                            if(cell.rect.contains(mouseX, mouseY)){
                                println("celll click:"+i+" "+j+" "+k);
                                cell.isSelected =  !cell.isSelected;
                                return;
                            }
                        }
                    }
                }
            }else if(mouseY > varfreqY && mouseY < varfreqY+predictorHeight){
                if(mouseX > varfreqX - MARGIN && mouseX < varfreqX+varfreqWidth){
                    //varfreq 
                    for(int i = 0; i < varfreqRanges.length; i++){
                        Range r = varfreqRanges[i];
                        //specify which handle is selected
                        if(r.h_low.contains(mouseX, mouseY)){
                            activeRange = r;
                            activeRange.isLowSelected = true; 
                            return;
                        }else if(r.h_high.contains(mouseX, mouseY)){
                            activeRange = r;
                            activeRange.isHighSelected = true;
                            return;
                        }
                    }
                }else if( mouseX > coverageX -MARGIN && mouseX < coverageX+coverageWidth){
                    //coverage
                    for(int i = 0; i < coverageSliders.length; i++){
                        Slider r = coverageSliders[i];
                        if(r.handle_rect.contains(mouseX, mouseY)){
                            activeSlider = r;
                            _currentBtnSampleIndex = i;
                            return;
                        }
                    }
                }else if( mouseX >coverageX+coverageWidth && mouseX < coverageX+coverageWidth+MARGIN){
                    //up or down buttons
                    for (int i = 0; i < upBtns.length; i++) {
                        if (upBtns[i].contains(mouseX, mouseY)) {
                            if(!isUpdating){ //only when updating is done
                                // println("debug: upBtn click");
                                Slider slider = coverageSliders[i];
                                slider.value_current = min(slider.value_current+1, _COVERAGE_MAX);
                                slider.update();
                                data.updateCoverage(data.samples[slider.sampleIndex], slider.value_current);
                                predictor.updateAll();
                                loop();
                            }
                            return;
                        } else if (downBtns[i].contains(mouseX, mouseY)) {
                            if(!isUpdating){
                                Slider slider = coverageSliders[i];
                                slider.value_current = max(slider.value_current-1, _COVERAGE_MIN);
                                slider.update();
                                data.updateCoverage(data.samples[slider.sampleIndex], slider.value_current);
                                predictor.updateAll();
                                loop();
                            }
                            return;
                        }
                    }
                }
            }else if(mouseY > text_btn_rects[0].y && mouseY < text_btn_rects[0].y+text_btn_rects[0].height){
                if(text_btn_rects[0].contains(mouseX, mouseY)){
                    //eport vcf
                    println("export filtered VCF");
                    if(isLoading){
                        println("still loading....");
                    }else{
                        getReadyForExport();
                        exportVCF();
                    }
                }else if(text_btn_rects[1].contains(mouseX, mouseY)){
                    //change migrating mode
                    println("migrate:"+_MIGRATING);
                    //update current status
                    hardReset(); //reset current coverage
                    _MIGRATING = !_MIGRATING;
                }else if(text_btn_rects[2].contains(mouseX, mouseY)){
                    //optimise
                    println("Optimise!");
                    isOptimising = true;
                    hardReset();
                    if(optimisationThread == null){
                        optimisationThread  = new OptimisationThread(1,10);
                        new Thread(optimisationThread).start();

                    }else{
                        //done
                        optimisationThread.setOptimal();
                    }

                }

            }     
            
        }
    }
}
public void mouseMoved() {
    //hovering action
    if(optimisationThread != null){
        if(!optimisationThread.doneOnce){
            //do nothing what so ever
            return;
        }
    }

    if (!isLoading && !mousePressed) { 
        if (mouseY > plotY1 && mouseY < plotY2 && mouseX > plotX1 && mouseX < plotX2) {
            cursor(HAND);
            loop();
        }else if(mouseX > varfreqX && mouseX < coverageX+coverageWidth+MARGIN && mouseY >varfreqY && mouseY < (varfreqY+predictorHeight)){
            loop();
        }else if(predictorRect.contains(mouseX, mouseY)){
            //check if it is hitting histogram_rects
            if(histogram_rects!=null){
                for(int i = 0; i<histogram_rects.length; i++){
                    Rectangle rect = histogram_rects[i];
                    if(rect.contains(mouseX, mouseY)){
                        selected_bar = i;
                        loop();
                        return;
                    }
                }
                selected_bar = -1;
                loop();
            }else{
                selected_bar = -1;
            }
        }else if(mouseY > text_btn_rects[0].y && mouseY < text_btn_rects[0].y+text_btn_rects[0].height){
            //check which button
            if(mouseX > text_btn_rects[0].x && mouseX < text_btn_rects[0].x +text_btn_rects[0].width){
                //export
                text_btn_mouseover[0]= true;
                text_btn_mouseover[1] = false;
                text_btn_mouseover[2] = false;
                cursor(HAND);
            }else if(mouseX > text_btn_rects[1].x && mouseX < text_btn_rects[1].x +text_btn_rects[1].width){
                text_btn_mouseover[0]= false;
                text_btn_mouseover[1] = true;
                text_btn_mouseover[2] = false;
                cursor(HAND);
            }else if(mouseX > text_btn_rects[2].x && mouseX < text_btn_rects[2].x +text_btn_rects[2].width){
                text_btn_mouseover[0]= false;
                text_btn_mouseover[1] = false; 
                text_btn_mouseover[2] = true;
                cursor(HAND);
            }else{
                text_btn_mouseover[0]= false;
                text_btn_mouseover[1] = false; 
                text_btn_mouseover[2] = false;
                cursor(ARROW);
            }
            loop();
            return;

        }else{
            cursor(ARROW);
            selected_bar = -1;
            text_btn_mouseover[0]= false;
            text_btn_mouseover[1] = false; 
            text_btn_mouseover[2] = false;
            loop();
            return;
        }
    }
}

public void mouseDragged() {
    if(activeRange != null){
        varfreqInteraction();
    }else if( activeSlider != null){
        coverageInteraction();        
    }
}

public void mouseReleased() { 
    activeSlider = null;
    if(activeRange != null){
        activeRange.isLowSelected = false;
        activeRange.isHighSelected = false;
    }
    activeRange = null;
    _currentBtnSampleIndex = -1;

    loop();
}

//called when varfreq value is changed
public void varfreqInteraction() { ///// reimplement this
    int sampleIndex = activeRange.sampleIndex;
    Sample s = data.samples[sampleIndex];
    activeRange.updateValue(mouseX, mouseY);
    int filterMin = activeRange.value_current_low;
    int filterMax = activeRange.value_current_high;
    data.updateVarfreq(s, filterMin, filterMax);
    // predictor.updateAll();
    loop();
}
//called when coverage slider is changed on drag
public void coverageInteraction() {
    int sampleIndex = activeSlider.sampleIndex;
    Sample s = data.samples[sampleIndex];
    activeSlider.updateValue(mouseX);
    int filterMin = activeSlider.value_current;
    data.updateCoverage(s, filterMin);

    // predictor.updateAll();
    loop();
}


//******* Keyboard ******* //
public void keyPressed() {
    if (key == 'p') {
        println("print");
        this.saveFrame = true;
        loop();
    } else if (key == 'e') {
        println("export filtered VCF");
        if(isLoading){
            println("still loading....");
        }else{
            getReadyForExport();
            exportVCF();
        }
    } else if (key == '2') {
    } else if (keyCode == ENTER) {
        System.out.println("Enter hit!");
    } else if( key == 'm'){
        println("migrate:"+_MIGRATING);
        //update current status
        hardReset(); //reset current coverage
        _MIGRATING = !_MIGRATING;
        
    }
}

public void hardReset(){
    for(int i = 0; i < data.samples.length; i++){
        Sample s = data.samples[i];
        s.currentCoverage = 1;
        // data.updateCoverage(s, 1);
        Slider slider = coverageSliders[i];
        slider.value_current =1;
        slider.update();
        // predictor.updateAll();
        // loop();
    }
    isLoading = true;
    loop();
    initialize();
}


//select a cell or cells to export the variants
public void getReadyForExport(){
    //check if a cell / cells selected
    selectedCells = new ArrayList<Cell>();
    for(int i = 0; i< data.table.length; i++){
        for(int j = 0; j<data.table.length; j++){
            for(int k = 0; k<data.table.length; k++){
                Cell cell = data.table[i][j][k];
                if(cell.isSelected){
                    selectedCells.add(cell);
                    println("selected cell:"+Arrays.toString(cell.indexes));
                }
            }
        }
    }

    int totalPossible = globalInitialCount[0];
    int totalImpossible = globalInitialCount[1];
    int selectedPossible = 0;
    int selectedImpossible = 0;



    //if no cell selected
    if(selectedCells.isEmpty()){
        println("no cell selected yet");
        // int selectedValue = JOptionPane.showConfirmDialog(null, "Would you like to export all the variants?", "Export Filtered VCF", JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
        // if(selectedValue == JOptionPane.YES_OPTION){
        for(int i = 0; i< data.table.length; i++){
            for(int j = 0; j<data.table.length; j++){
                for(int k = 0; k<data.table.length; k++){
                    Cell cell = data.table[i][j][k];
                    if(i == 0 && j == 0 && k ==0){
                    }else{
                        selectedCells.add(cell);                        
                    }
                }
            }
        }
        println("selecetd cell count ="+selectedCells.size());
        exportingResult = new HashMap<Integer, HashMap<Integer, Variant>>(); 
        //iterate through selected cell
        for(int i = 0; i<selectedCells.size(); i++){
            Cell cell = selectedCells.get(i);
            boolean isPossible = _combination[cell.indexes[0]][cell.indexes[1]][cell.indexes[2]];
            //iterate through variants
            Iterator ite = cell.variants.iterator();
            while(ite.hasNext()){
                Variant v = (Variant)ite.next();  
                HashMap<Integer, Variant> chrArray = (HashMap<Integer, Variant>)exportingResult.get(v.chrIndex);
                if(chrArray == null){
                    chrArray = new HashMap<Integer, Variant>();
                    exportingResult.put(v.chrIndex, chrArray);
                }
                //record the variant position
                chrArray.put(v.position, v);
                if(isPossible){
                    selectedPossible ++;
                }else{
                    selectedImpossible++;
                }
            }
        }
        // }else if(selectedValue == JOptionPane.NO_OPTION){
        //     exportingResult = null;
        // }

    }else{
        println("selecetd cell count ="+selectedCells.size());
        exportingResult = new HashMap<Integer, HashMap<Integer, Variant>>(); 
        //iterate through selected cell
        for(int i = 0; i<selectedCells.size(); i++){
            Cell cell = selectedCells.get(i);
            boolean isPossible = _combination[cell.indexes[0]][cell.indexes[1]][cell.indexes[2]];
            //iterate through variants
            Iterator ite = cell.variants.iterator();
            while(ite.hasNext()){
                Variant v = (Variant)ite.next();            
                HashMap<Integer, Variant> chrArray = (HashMap<Integer, Variant>)exportingResult.get(v.chrIndex);
                if(chrArray == null){
                    chrArray = new HashMap<Integer, Variant>();
                    exportingResult.put(v.chrIndex, chrArray);
                }
                //record the variant position
                chrArray.put(v.position, v);
                if(isPossible){
                    selectedPossible ++;
                }else{
                    selectedImpossible++;
                }
            }
        }
    }

    int possiblePercent = round((float)selectedPossible/(float)totalPossible*100);
    int impossiblePercent = round((float)selectedImpossible/(float)totalImpossible *100);
    
    String msg = "Exporting variants from "+selectedCells.size()+" blocks:\n\t\t"+selectedPossible+" possible variants ("+possiblePercent+"%)\n\t\t"+selectedImpossible+" impossible variants ("+impossiblePercent+"%)";
    int exportOrNot = JOptionPane.showConfirmDialog(null, msg, "Export Filtered VCF", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
    if(exportOrNot == JOptionPane.OK_OPTION){
    }else if(exportOrNot == JOptionPane.CANCEL_OPTION){
        exportingResult = null;
    }
}

public void exportVCF(){
    //run on another thread
    if(exportingResult != null){
        isWritingFile = true;
        Runnable readAndWrite  = new ReadAndWriteThread();
        new Thread(readAndWrite).start();
        
    }
}
//method called from ReadAndWriteThread
public void readAndWrite(String path, String p){
    //create writer
    String[] fileName = splitTokens(path, ".");
    PrintWriter writer = createWriter(p+fileName[0]+"_filtered.vcf");
    Sample s = data.samples[loadingSampleIndex];
    int s_coverage = s.currentCoverage;
    File f = new File(dataPath(path));
    try{
        //Get the file from the data folder
        BufferedReader reader = createReader(f);
        //Loop to read the file one line at a time
        String line = null;
        while ((line = reader.readLine()) != null) {
            if(line.startsWith("##")){
                //attribute
                writer.println(line);
            }else if(line.startsWith("#")){
                //header
                writer.println(line);
            }else{
                String[] st = split(line, TAB);
                if (st[0] != null) {
                    String chr = st[0].trim();
                    int chrIndex = data.getChrIndex(chr);
                    if(chrIndex == -1){
                        print("Error:");
                    }
                    int position = Integer.parseInt(st[1]);
                    HashMap<Integer, Variant> map = (HashMap<Integer, Variant>)exportingResult.get(chrIndex);
                    if(map == null){
                        println("cannot find chromosome id:"+chr);
                    }else{
                        Variant v = (Variant)map.get(position);
                        if( v != null){
                            //find Row
                            Row r = v.getRow(loadingSampleIndex);
                            if(r != null){
                                // find coverage
                                if(r.coverage >= s_coverage){
                                    //write
                                    writer.println(line);
                                }else{
                                    //don't wirte
                                }
                            }else{
                                println("Error: Row is null. Controller.readAndWrite()");
                            }
                        }else{
                            //this variant was not selected
                        }
                    }
                }
            }
            writer.flush();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    writer.close();
    println("finnished writing: "+fileName[0]+"_filtered.vcf");
}
//real mode
public void readAndWrite(File file, String p){
    //create writer
    String[] fileName = splitTokens(file.getName(), ".");
    PrintWriter writer = createWriter(p+fileName[0]+"_filtered.vcf");//createWriter(sketchPath+"/data/"+fileName[0]+"_filtered.vcf");
    Sample s = data.samples[loadingSampleIndex];
    int s_coverage = s.currentCoverage;

    File f = file;
    try{
        //Get the file from the data folder
        BufferedReader reader = createReader(f);
        //Loop to read the file one line at a time
        String line = null;
        while ((line = reader.readLine()) != null) {
            if(line.startsWith("##")){
                //attribute
                writer.println(line);
            }else if(line.startsWith("#")){
                //header
                writer.println(line);
            }else{
                String[] st = split(line, TAB);
                if (st[0] != null) {
                    String chr = st[0].trim();
                    int chrIndex = data.getChrIndex(chr);
                    if(chrIndex == -1){
                        print("Error:");
                    }
                    int position = Integer.parseInt(st[1]);

                    HashMap<Integer, Variant> map = (HashMap<Integer, Variant>)exportingResult.get(chrIndex);
                    if(map == null){
                        println("cannot find chromosome id:"+chr);
                    }else{
                        Variant v = (Variant)map.get(position);
                        if( v != null){
                            //find Row
                            Row r = v.getRow(loadingSampleIndex);
                            if(r != null){
                                // find coverage
                                if(r.coverage >= s_coverage){
                                    //write
                                    writer.println(line);
                                }else{
                                    //don't wirte
                                }

                            }else{
                                println("Error: Row is null. Controller.readAndWrite()");
                            }
                        }
                    }
                }
            }
            writer.flush();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    writer.close();
    println("finnished writing: "+fileName[0]+"_filtered.vcf");
}





















String[] _sampleLabels = new String[3]; //sample ID
String[] _chrLabels = {"chr1", "chr2", "chr3", "chr4", "chr5", "chr6", "chr7", "chr8", "chr9", "chr10", "chr11", "chr12", "chr13", "chr14", "chr15", "chr16", "chr17", "chr18", "chr19", "chr20","chr21", "chr22", "chrX", "chrY"};
boolean[][][] _combination = {{{true, false, false}, {true, true, false}, {false, true, false}},
        {{true, true, false}, {true, true, true}, {false, true, true}},
        {{false, true, false}, {false, true, true}, {false, false, true}}};
String[][][] _cellLable = {{{"", "de novo", ""}, {"", "", ""}, {"", "", ""}},
        {{"", "", ""}, {"", "", "recessive"}, {"", "", ""}},
        {{"", "", ""}, {"", "", ""}, {"", "", ""}}};
int[] _sampleOrder = {0, 1, 2}; //initial default order
//varfreq range
int _VARFREQ_MIN = 0;
int _VARFREQ_MAX = 100;
//coverage range
int _COVERAGE_MIN = 1; 
int _COVERAGE_MAX = 200;
int _defaultLowCutPoint = 20;
int _defaultHighCutPoint = 90;

// int[] sampleMinCoverages = {1, 1, 1};

class DataSet{
	Sample[] samples; //all samples
	HashMap<Integer, HashMap> variantChrMap; //variants sorted by chromosomes
	HashSet<Variant> allVariants;
	HashSet<Variant> excludedVariants; //variants below the varfreq thresholds
	Cell[][][] table; //table of cells


	DataSet(){
		samples = new Sample[3];
		allVariants = new HashSet<Variant>();
		variantChrMap = new HashMap<Integer, HashMap>();
	}

	//get index of chr from the name
	public int getChrIndex(String chr){
        if(!chr.startsWith("chr")){
            chr = "chr"+chr;
        }
        for(int i = 0; i< _chrLabels.length; i++){
            if(chr.equals(_chrLabels[i])){
                return i;
            }
        }
        return -1;//no match
    }
    //returns Sample object, if not exist, creates a new Sample
    public Sample findSample(int sampleIndex) {
    	Sample s = samples[sampleIndex];
    	if(s == null){
    		s = new Sample(_sampleLabels[sampleIndex]);
    		samples[sampleIndex]= s;
    	}
    	return s;
    }
    //returns Variant object, if not exist, creates a new Variant
    public Variant findVariant(int chr, int position, String id){     
        //locate the chromosome
        HashMap chrMap = variantChrMap.get(chr);
        if(chrMap == null){
            chrMap = new HashMap<Integer, Variant>();
            variantChrMap.put(chr, chrMap);
        }
        //find the variant
        Variant v = (Variant) chrMap.get(position);
        if(v == null){
            v = new Variant(chr, position, id);
            chrMap.put(position, v);
            allVariants.add(v);
        }
        return v;
    }
    //setup the zygosity of each Row, called only once
    public void setupZygosity() {
        for (int i = 0; i < samples.length; i++) {
            Sample s = samples[i];
            s.setupZygosity();
        }
    }
    //setup table, called only once at the beginning
    public void setupTable() {
        //setup table
        table = new Cell[3][3][3];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                for (int k = 0; k < table.length; k++) {
                    table[i][j][k] = new Cell(i, j, k);
                }
            }
        }
        //excluded variant
        excludedVariants = new HashSet<Variant>();
        //loader
        currentLoaded = 0;
        fileSize = allVariants.size();
        Iterator ite = allVariants.iterator();
        while(ite.hasNext()){
            Variant v = (Variant)ite.next();

        // for (int i = 0; i < allVariants.size(); i++) {
        // Variant v = allVariants.get(i);
            int[] index = setupVariantIndex(v); //called only once
            if(index == null){
                //exclude the variant
                //one of row has out of range varfreq
                excludedVariants.add(v);
            }else{
                //add the variant to the cell
                table[index[0]][index[1]][index[2]].addVariant(v);
            }
            currentLoaded ++;
        }
        println("debug:DataSet.setupTable():excluded vairant based on varfreq threshold =" + excludedVariants.size());
    }


    //find the variant index, the very first time
    //returns null if the variant is excluded due to varfreq below low boundary
    //it assumes that if there was no call, it becomes Ref/Ref
    public int[] setupVariantIndex(Variant v) {
        int[] result = {0, 0, 0};
        ArrayList<Row> rows = v.rows;
        for (int i = 0; i < rows.size(); i++) {
            Row r = rows.get(i);
            int sampleIndex = r.sampleIndex;
            int sampleOrderIndex = _sampleOrder[sampleIndex];
            if (r.isRefAlt) {
                result[sampleOrderIndex] = 1;
            } else if (r.isAltAlt) {
                result[sampleOrderIndex] = 2;
            } else {
                //varfreq below 20
                return null;
            }
        } 
        //debug
        if (rows.size() > 3) {
            println("Error: setupVariantIndex " + rows.size() + " v-id=" + v.variantID);
        }
        return result;
    }

    //find the variant index //only _MIGRATING mode
    //returns null if the variant is excluded due to varfreq below low boundary
    public int[] getVariantIndex(Variant v) {
        int[] result = {0, 0, 0}; // 
        ArrayList<Row> rows = v.rows;
        for (int i = 0; i < rows.size(); i++) {
            Row r = rows.get(i);
            int sampleIndex = r.sampleIndex;
            int sampleOrderIndex = _sampleOrder[sampleIndex];
            Sample sample = data.samples[sampleIndex];
            int coverage = r.coverage;
            int currentCoverage = sample.currentCoverage;
            if (r.isRefAlt) {
                if(coverage >=currentCoverage){
                    result[sampleOrderIndex] = 1;
                }else{
                    //consider it Ref/Ref
                    result[sampleOrderIndex] = 0;
                }
            } else if (r.isAltAlt) {
                if(coverage >=currentCoverage){
                    result[sampleOrderIndex] = 2;
                }else{
                    result[sampleOrderIndex] = 0;    
                }
            } else {
                //below varfreq threashold
                return null;   
            }
        } 
        //debug
        if (rows.size() > 3) {
            println("Error DataSet.getVariantIndex() " + rows.size() + " v-id=" + v.variantID);
        }
        return result;
    }

    //calculate distribution for each sample
    public void calculateVarfreqDistributions() {
        //varfreq 
        for (int j = 0; j < samples.length; j++) {
            Sample s = samples[j];
            Distribution d = new Distribution(_VARFREQ_MIN, _VARFREQ_MAX, 5);  //5 value increment 
            ArrayList<Row> rows = s.rows;
            for (int i = 0; i < rows.size(); i++) {
                Row r = rows.get(i);
                int vf = r.varfreq;
                d.addCount(vf);
            }
            d.findMaxAndMinCount();
            s.varfreqDistribution = d;
            // println(s.sampleID+": varfreq distribution:"+d.toString());
        }
    }
    //calculate coverage distribution for each sample
    public void calculateCoverageDistributions() {
        //coverage
        for (int j = 0; j < samples.length; j++) {
            Sample s = samples[j];
            Distribution d = new Distribution(_COVERAGE_MIN, _COVERAGE_MAX, 10);
            ArrayList<Row> rows = s.rows;
            for (int i = 0; i < rows.size(); i++) {
                Row r = rows.get(i);
                int cov = r.coverage;
                d.addCount(cov);
            }
            d.findMaxAndMinCount();
            s.coverageDistribution = d;
        }
    }


    //update based on the varfreq ranges
    public void updateVarfreq(Sample s, int filterMin, int filterMax) {
        ArrayList<Variant> variantsToUpdate = s.updateVarfreqRange(filterMin, filterMax);
        //move around variant
        for(int i = 0; i<variantsToUpdate.size(); i++){
            Variant v = variantsToUpdate.get(i);
            int[] oldIndex = v.cellIndex;
            int[] newIndex = getVariantIndex(v);
            
            if(oldIndex == null){
                //previously excluded variants
                if(excludedVariants.contains(v)){
                    if(newIndex == null){
                        //at least one of rows is still below the minimum varfreq
                        v.cellIndex = null;
                    }else{
                        excludedVariants.remove(v);
                        table[newIndex[0]][newIndex[1]][newIndex[2]].addVariant(v);                    
                    }
                }else{
                    println("debug: error in updateVarfreq(): not in excluded:"+v.toString());
                }
            }else{
                if(excludedVariants.contains(v)){
                    if(newIndex == null){
                        //still at least one of rows is still below minimum varfreq
                        v.cellIndex = null;
                    }else{
                        excludedVariants.remove(v);
                        table[newIndex[0]][newIndex[1]][newIndex[2]].addVariant(v);    
                    }
                }else{
                    //remove variant from old cell
                    table[oldIndex[0]][oldIndex[1]][oldIndex[2]].removeVariant(v);
                    if(newIndex  == null){
                        excludedVariants.add(v);
                        v.cellIndex = null;
                    }else{
                        //add variant to new cell
                        table[newIndex[0]][newIndex[1]][newIndex[2]].addVariant(v);
                    }
                }   
            }   
        }    
        predictor.updateAll();
    }
    //update based on the coverage, called when overage changes
    public void updateCoverage(Sample s, int filterMin) {
        // println("DataSet.updateCoverage()");
        int prevCoverage = s.currentCoverage;
        s.currentCoverage = filterMin;
        //whether to remove or add these variants
        boolean increasing = (prevCoverage < filterMin)?true:false;
        
        //set the minmum filter value for this sample
        //ArrayList<Variant> vs = s.updateCoverage(filterMin, prevCoverage); //list of variants to update
        ConcurrentHashMap<String, Variant> vs = s.updateCoverage2(filterMin, prevCoverage);
        // println("debug: number of variants to update= "+vs.size());

        Thread updator = new UpdateThread(vs, increasing);
        updator.start();

        //update predictor
        predictor.updateAll();
    }

    public void updateCoverageNonThread(Sample s, int filterMin){
        int prevCoverage = s.currentCoverage;
        s.currentCoverage = filterMin;
        //whether to remove or add these variants
        boolean increasing = (prevCoverage < filterMin)?true:false;
        //set the minmum filter value for this sample
        ConcurrentHashMap<String, Variant> vs = s.updateCoverage2(filterMin, prevCoverage);
        Iterator ite = vs.entrySet().iterator();
        while(ite.hasNext()){
            Map.Entry entry = (Map.Entry)ite.next();
            //action
            // count++;
            Variant v = (Variant) entry.getValue();
            if(_MIGRATING){
                int[] newIndex = data.getVariantIndex(v);
                int[] oldIndex = v.cellIndex;
                if(oldIndex == null){
                    //variants outside of the varfreq range
                }else{
                    //remove variant from the old cell
                    Cell cell = data.table[oldIndex[0]][oldIndex[1]][oldIndex[2]];
                    cell.removeVariant(v);
                    //add variant to the new cell
                    cell = data.table[newIndex[0]][newIndex[1]][newIndex[2]];
                    cell.addVariant(v);
                }
            }else{
                int[] cellIndex  = v.cellIndex;
                if(cellIndex == null){
                    //variants outside of the Varfreq Reange
                }else{
                    Cell cell = data.table[cellIndex[0]][cellIndex[1]][cellIndex[2]];
                    if(increasing){
                        cell.removeVariant(v);
                    }else{
                        cell.addVariant(v);
                    }
                }
            }     
        }
    }
    //count how many possible and imposibble variatns
    public int[] getGlobalCounts() {
        int[] result = new int[2];  //0 blue, 1 red
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                for (int k = 0; k < this.table.length; k++) {
                    if (i == 0 && j == 0 && k == 0) {
                        //do not add to global count
                    } else {
                        Cell cell = table[i][j][k];
                        if (_combination[i][j][k]) {
                            //possible
                            result[0] += cell.variants.size();
                        } else {
                            //impossible
                            result[1] += cell.variants.size();
                        }                        
                    }
                }
            }
        }
        return result;
    }


}

int MARGIN = 10;
int Y_MARGIN = 50;
int LABEL_MARGIN = MARGIN / 5;
// int STAGE_WIDTH = 1200;
// int STAGE_HEIGHT = 800;
int plotX1 = MARGIN * 9;
int plotY1 = MARGIN * 6;
int cellWidth = MARGIN * 32;
int cellHeight = MARGIN * 5;
int blockHeight = cellHeight * 3;
int cellMaxCount = 400;
int cellMaxCoverage = 200;
int cellMinCoverage = 0;
int plotWidth = (cellWidth * 3) + (MARGIN * 2);
int plotHeight = (blockHeight * 3) + (MARGIN * 2);
int plotX2 = plotX1 + plotWidth;
int plotY2 = plotY1 + plotHeight;
//global plot
int globalBarW = 20;
int globalPlotW = (globalBarW * 2) + (MARGIN * 3);
int globalPlotX1 = plotX2 + (MARGIN * 2);
int globalPlotX2 = globalPlotX1 + globalPlotW;
int globalPlotY1 = plotY1;
int globalPlotY2 = plotY2;
int globalPlotH = plotHeight;
int globalPossibleX = globalPlotX1 + MARGIN;
int globalImpossibleX = this.globalPossibleX + globalBarW + MARGIN;
int tableX[] = {plotX1, plotX1 + MARGIN + cellWidth, plotX1 + (MARGIN * 2) + (cellWidth * 2)};
int tableY[] = {plotY1, plotY1 + (MARGIN) + blockHeight, plotY1 + (MARGIN * 2) + (blockHeight * 2)};

//controller coordinates
int varfreqX = plotX1;
int varfreqY = plotY2 + (MARGIN * 5);// +150;
int varfreqWidth = cellWidth;//MARGIN * 20;
int varfreqHeight = MARGIN * 4;
int varfreqHandleSize = 5;
//coverage slider
int coverageX = varfreqX + varfreqWidth + (MARGIN);
int coverageY = varfreqY;
int coverageWidth = cellWidth - MARGIN; //subtract the button width
int coverageHeight = varfreqHeight;
int coverageSliderMin = 1;
int coverageSliderMax = 200; 
int controllerGapY = MARGIN; //gap

int coverageMaxCount = -1; 

//predictor
int predictorX  = coverageX + coverageWidth + (MARGIN)*2;
int predictorY = coverageY;
int predictorWidth = cellWidth;
int predictorHeight = varfreqHeight*3+MARGIN*2;//this.blockHeight;
Rectangle predictorRect = new Rectangle(predictorX, predictorY, predictorWidth, predictorHeight);

//global count
int[] globalInitialCount;
int gInitialHeightPossible = 0;
int gInitialHeightImpossible = 0;
int globalBarInterval = 1000;
int globalMaxCount = 0;


int updateMsgX = predictorX;
int updateMsgY = predictorY+predictorHeight+MARGIN*2;


//File Choose page
int instructionX = plotX1;
int instructionY = plotY1;
int p1_y = plotY1 + MARGIN*4;
int p2_y = p1_y + MARGIN*3;
int c_y = p2_y + MARGIN*3;
int label_x_r = plotX1 + MARGIN*7;
int path_x = label_x_r +MARGIN;
int path_w = MARGIN*40;
int path_h = MARGIN*2;
Rectangle p1_path_rect = new Rectangle(path_x, p1_y, path_w, path_h);
Rectangle p2_path_rect = new Rectangle(path_x, p2_y, path_w, path_h);
Rectangle c_path_rect = new Rectangle(path_x, c_y, path_w, path_h);
Rectangle[] path_rects = {p1_path_rect, p2_path_rect, c_path_rect};
int btn_x = path_x+path_w+MARGIN;
int btn_w = MARGIN*5;
int btn_h = MARGIN*2;
Rectangle p1_btn = new Rectangle(btn_x, p1_y, btn_w, btn_h);
Rectangle p2_btn = new Rectangle(btn_x, p2_y, btn_w, btn_h);
Rectangle c_btn = new Rectangle(btn_x, c_y, btn_w, btn_h);

int btn_go_y = c_y+MARGIN*3;
Rectangle go_btn = new Rectangle(btn_x, btn_go_y, btn_w, btn_h);

int annotateit_x = plotX1;
int annotateit_msg_y = c_btn.y+c_btn.height+MARGIN*10;
int annotateit_path_y = annotateit_msg_y+MARGIN*3;
Rectangle a_path_rect = new Rectangle(path_x, annotateit_path_y, path_w, path_h);
Rectangle annotateit_btn = new Rectangle(btn_x, annotateit_path_y, btn_w, btn_h);

int btn_go2_y = annotateit_path_y+MARGIN*3;
Rectangle go2_btn = new Rectangle(btn_x, btn_go2_y, btn_w, btn_h);
Rectangle[] histogram_rects = null;
int selected_bar = -1;





String[] tableLabels = {"Ref/Ref", "Ref/Alt", "Alt/Alt"};



//set the cell display coordinates, assign Rectangle per Cell
public void setupCellCoordinates(){
	Cell[][][] table = data.table;
    Rectangle rect = table[0][0][0].rect;
    if (rect == null) {
        //first time set rectangle positions
        for (int i = 0; i < table.length; i++) {
            int runningX = tableX[i];
            for (int j = 0; j < table.length; j++) {
                int runningY = tableY[j];
                for (int k = 0; k < table.length; k++) {
                    Cell cell = table[i][j][k];
                    cell.rect = new Rectangle(runningX, runningY, this.cellWidth, this.cellHeight);
                    runningY += cellHeight;
                }
            }
        }
    }
}
//draw varfreq distribution slider   
public void drawVarfreqDistibution() {
    for (int i = 0; i < _sampleLabels.length; i++) {
        Sample s = data.samples[_sampleOrder[i]];
        String sampleID = s.sampleID;
        Distribution distribution = s.varfreqDistribution;

        //range slider position
        Range range = varfreqRanges[i];
        Rectangle r = range.display_rect;
        int[] counts = distribution.countArray;
        int maxCount = distribution.maxCount;
        int minCount = distribution.minCount;

        //draw background
        fill(255);
        noStroke();
        rectMode(CORNERS);
        rect(r.x, r.y, r.x+r.width, r.y+r.height);
        //draw distribution
        fill(this.colorSampleArray[i]);  // color based on sample
        noStroke();
        beginShape();
        vertex(r.x, r.y + r.height);
        for (int j = 0; j < counts.length; j++) {
            int count = counts[j];
            float py = map(count, minCount, maxCount, r.y + r.height, r.y);
            float px = map(j, 0, counts.length - 1, r.x, r.x + r.width);
            vertex(px, py);
        }
        vertex(r.x + r.width, r.y + r.height);
        endShape(CLOSE);
        
        int r_low = range.value_current_low;
        int r_high = range.value_current_high;
        float px1 = range.dx_low;//map(r_low, _VARFREQ_MIN, _VARFREQ_MAX, r.x,  r.x+r.width);
        float px2 = range.dx_high;//map(r_high, _VARFREQ_MIN, _VARFREQ_MAX, r.x,  r.x+r.width);
        //current value
        //line
        stroke(colorText);
        strokeWeight(1f);
        line(px1, r.y, px1, r.y+r.height);
        line(px2, r.y, px2, r.y+r.height);
        //circle
        stroke(colorText);
        strokeWeight(3f);
        point(px1, r.y+r.height);
        point(px2, r.y+r.height);
        //text
        fill(colorText);
        textFont(_FONT);
        textSize(12f);
        textAlign(LEFT, TOP);
        text(r_low, px1+1, r.y);
        text(r_high, px2+1, r.y);

        //mouseover highlight
        if(range.h_low.contains(mouseX, mouseY)){
            //highlight the selected region
            Rectangle h = range.h_low;
            rectMode(CORNER);
            fill(color_cyan_trans);
            noStroke();
            rect(h.x, h.y, h.width, h.height);
        }else if(range.h_high.contains(mouseX, mouseY)){
            //highlight the selected region
            Rectangle h = range.h_high;
            rectMode(CORNER);
            fill(color_cyan_trans);
            noStroke();
            rect(h.x, h.y, h.width, h.height);
        }
    }
}
//draw coverage distribution slider   
public void drawCoverageDistribution() {
    if(coverageMaxCount == -1){
        //calculate the average of max count for distribution
        int sum = 0;
        for (int i = 0; i < _sampleLabels.length; i++) {
            Sample s = data.samples[_sampleOrder[i]];
            Distribution distribution = s.coverageDistribution;
            int maxCount = distribution.maxCount;
            sum += maxCount;
        }
        coverageMaxCount = sum/_sampleLabels.length;
        println("average is ="+coverageMaxCount);
    }

    for (int i = 0; i < _sampleLabels.length; i++) {
        Sample s = data.samples[_sampleOrder[i]];
        Distribution distribution = s.coverageDistribution;
        Slider slider = coverageSliders[i];
        Rectangle r = slider.d_rect;

        int[] counts = distribution.countArray;
        // int maxCount = distribution.maxCount;
        int maxCount = coverageMaxCount;
        int minCount = distribution.minCount;
        int value = slider.value_current;
        int dx = slider.display_current_x;//map(value, _COVERAGE_MIN, _COVERAGE_MAX, r.x, r.x+r.width);
        
        // println("maxCount ="+distribution.maxCount +" sample="+s.sampleID);
        //background
        fill(255);
        noStroke();
        rectMode(CORNER);
        rect(r.x, r.y, r.width, r.height);

        //draw distribution
        fill(colorSampleArray[i]);
        noStroke();
        beginShape();
        vertex(r.x, r.y + r.height);
        for (int j = 0; j < counts.length; j++) {
            int count = counts[j];
            float py = constrain(map(count, minCount, maxCount, r.y + r.height, r.y),r.y , r.y+r.height);
            float px = map(j, 0, counts.length - 1, r.x, r.x + r.width);
            vertex(px, py);
        }
        vertex(r.x + r.width, r.y + r.height);
        endShape(CLOSE);
        
        
        // current value
        noFill();
        stroke(colorText);
        strokeWeight(1f);
        line(dx, r.y, dx, r.y+r.height);
        //dot
        strokeWeight(3f);
        point(dx, r.y+r.height);
        //text
        fill(colorText);
        textSize(12f);
        textAlign(LEFT, TOP);
        text(value, dx+1, r.y);

        //mouse over
        if(slider.handle_rect.contains(mouseX, mouseY)){
            //highlight the selected region
            Rectangle h = slider.handle_rect;
            rectMode(CORNER);
            fill(color_cyan_trans);
            noStroke();
            rect(h.x, h.y, h.width, h.height);
        }
    }
}
//draw up and down btns for coverage
public void drawUpAndDownBtns() {
    for (int i = 0; i < upBtns.length; i++) {
        Rectangle up = upBtns[i];
        Rectangle down = downBtns[i];
        rectMode(CORNER);
        noStroke();

        //up
        if (up.contains(mouseX, mouseY)) {
            fill(colorDarkGray);
            _currentBtnSampleIndex = i;
        } else {
            fill(colorLightGray);
        }
        rect(up.x, up.y, up.width, up.height);
        if (up.contains(mouseX, mouseY)) {
            fill(colorLightGray);
        } else {
            fill(colorDarkGray);
        }
        triangle(up.x + up.width / 2, up.y + up.height / 3, up.x + up.width - 2, up.y + 2 * up.height / 3, up.x + 2, up.y + 2 * up.height / 3);


        //down
        if (down.contains(mouseX, mouseY)) {
            fill(colorDarkGray);
            _currentBtnSampleIndex = i;
        } else {
            fill(colorLightGray);
        }
        rect(down.x, down.y, down.width, down.height);
        if (down.contains(mouseX, mouseY)) {
            fill(colorLightGray);
        } else {
            fill(colorDarkGray);
        }
        triangle(down.x + down.width / 2, down.y + 2 * down.height / 3, down.x + up.width - 2, down.y + up.height / 3, down.x + 2, down.y + down.height / 3);
    }
}
//draw controller
public void drawControllerLabels() {
    //sample id
    textFont(_FONT);
    textSize(14);
    for (int i = 0; i < _sampleOrder.length; i++) {
        String sampleId = _sampleLabels[_sampleOrder[i]];
        textAlign(RIGHT, TOP);
        fill(colorSampleArray[i]);
        text(sampleId, plotX1 - MARGIN, varfreqY + (i * (MARGIN + varfreqHeight)));
    }

    fill(colorText);
    //sample ID
    textAlign(RIGHT, BOTTOM);
    text("SampleID", plotX1 - MARGIN, varfreqY);

    //controller label
    for (int i = 0; i < controllerLabel.length; i++) {
        textAlign(LEFT, BOTTOM);
        text(controllerLabel[i], controllerLabelX[i], varfreqY);
    }
}
//draws prector
public void drawPredictor_archive() {
    //background
    fill(255);
    noStroke();
    rect(predictorX, predictorY, predictorWidth, predictorHeight);
    //draw grid
    noFill();
    stroke(colorBackground);
    strokeWeight(1f);
    //vertical
    for(int i = 1; i<predictor.binSize-1;i++){
        float px = map(i, 0, predictor.binSize-1, predictorX, predictorX+predictorWidth);
        line(px, predictorY, px, predictorY+predictorHeight);
    }
    //horizontal
    for(int i = predictor.countInterval; i<predictor.countMax; i+=predictor.countInterval){
        float py = map(i, predictor.countMin, predictor.countMax, predictorY+predictorHeight, predictorY);
        line(predictorX, py, predictorX+predictorWidth, py);
    }

    //data
    int[][][] counters = predictor.counters;
    //sample
    for(int i = 0; i < 3; i++ ){
        int[][] sample = counters[i];
        //possible
        int[] possible = sample[0];
        //impossible
        int[] impossible = sample[1];
        
        //possible
        if(_currentBtnSampleIndex == i){
            stroke(colorSampleArray[i]);
        }else{
            stroke(colorGrayTrans);
        }
        float px = 0;
        float py = 0;
        strokeWeight(1f);
        noFill();
        beginShape();
        for(int j = 0; j< possible.length; j++){
            px = map(j, 0, possible.length-1, predictorX, predictorX+predictorWidth);
            py = constrain(map(possible[j], predictor.countMin, predictor.countMax, predictorY+predictorHeight, predictorY), predictorY, predictorY+predictorHeight);
            vertex(px, py);
        }
        endShape();
        if(_currentBtnSampleIndex == i){
            fill(colorSampleArray[i]);
            textAlign(LEFT, BOTTOM);
            textSize(12);
            text("possible", px+1, py);
            stroke(colorSampleArray[i]);
        }else{
            stroke(colorGrayTrans);
        }
        
        //impossible
        strokeWeight(2f);
        noFill();
        beginShape();
        for(int j = 0; j< impossible.length; j++){
            px = map(j, 0, impossible.length-1, predictorX, predictorX+predictorWidth);
            py = constrain(map(impossible[j], predictor.countMin, predictor.countMax, predictorY+predictorHeight, predictorY), predictorY, predictorY+predictorHeight);
            vertex(px, py);
        }
        endShape();
        if(_currentBtnSampleIndex == i){
            fill(colorSampleArray[i]);
            textAlign(LEFT, BOTTOM);
            textSize(12);
            text("impossible", px+1, py);
            stroke(colorSampleArray[i]);
        }else{
            stroke(colorGrayTrans);
        }
        
        //draw current coverage
        int coverage = data.samples[_sampleOrder[i]].currentCoverage;//sampleMinCoverages[i];
        px = constrain(map(coverage, 1, impossible.length, predictorX, predictorX+predictorWidth), predictorX, predictorX+predictorWidth);
        stroke(colorSampleArray[i]);
        strokeWeight(1f);
        line(px, predictorY, px, predictorY+predictorHeight+ (i*5));
        fill(colorSampleArray[i]);
        ellipse(px, predictorY+predictorHeight+ (i*5), 5, 5);
        
    }
        
        
    //coverage label
    fill(colorText);
    textAlign(CENTER, TOP);
    textSize(10);
    for(int i = 0; i<predictor.binSize;i++){
        int index = i+1;
        if(index == 1 || index%10 == 0){
            float px = map(i, 0, predictor.binSize-1, predictorX, predictorX+predictorWidth);
            text((index),px, predictorY+predictorHeight);
        }
    }
}
//two bar graphs
public void drawPredictor_two_bars() {
    noFill();
    stroke(255);
    strokeWeight(1f);
    //vertical
    for(int i = 1; i<predictor.binSize-1;i++){
        float px = map(i, 0, predictor.binSize-1, predictorX, predictorX+predictorWidth);
        line(px, predictorY, px, predictorY+predictorHeight);
    }
    //horizontal
    for(int i = predictor.countInterval; i<predictor.countMax; i+=predictor.countInterval){
        float py = map(i, predictor.countMin, predictor.countMax, predictorY+predictorHeight, predictorY);
        line(predictorX, py, predictorX+predictorWidth, py);
    }

    float increment = ((float)predictorWidth/(float)(predictor.binSize))/2;

    //data
    int[][][] counters = predictor.counters;
    if(_currentBtnSampleIndex != -1){
        int[][] sample = counters[_currentBtnSampleIndex];
        //possible
        int[] possible = sample[0];
        //impossible
        int[] impossible = sample[1];
        rectMode(CORNERS);
        float px = 0;
        float py = 0;
        for(int j = 0; j< possible.length; j++){
            px = map(j, 0, possible.length-1, predictorX, predictorX+predictorWidth);
            py = constrain(map(possible[j], predictor.countMin, predictor.countMax, predictorY+predictorHeight, predictorY), predictorY, predictorY+predictorHeight);
            //possible
            stroke(colorText);
            fill(colorPossible);
            rect(px, predictorY+predictorHeight, px+increment, py);

            //impossible
            px += increment;
            py = constrain(map(impossible[j], predictor.countMin, predictor.countMax, predictorY+predictorHeight, predictorY), predictorY, predictorY+predictorHeight);
            fill(colorImpossible);
            rect(px, predictorY+predictorHeight, px+increment, py);
        }
        //draw current coverage
        int coverage = data.samples[_sampleOrder[_currentBtnSampleIndex]].currentCoverage;//sampleMinCoverages[i];
        px = constrain(map(coverage, 1, predictor.binSize, predictorX, predictorX+predictorWidth), predictorX, predictorX+predictorWidth);
        stroke(colorSampleArray[_currentBtnSampleIndex]);
        strokeWeight(1f);
        line(px, predictorY, px, predictorY+predictorHeight+ (_currentBtnSampleIndex*5));
        fill(colorSampleArray[_currentBtnSampleIndex]);
        ellipse(px, predictorY+predictorHeight+ (_currentBtnSampleIndex*5), 5, 5);
    }
        
    //coverage label
    fill(colorText);
    textAlign(CENTER, TOP);
    textSize(10);
    for(int i = 0; i<predictor.binSize;i++){
        int index = i+1;
        if(index == 1 || index%10 == 0){
            float px = map(i, 0, predictor.binSize-1, predictorX, predictorX+predictorWidth);
            text((index),px+increment, predictorY+predictorHeight);
        }
    }
}
//stacked
public void drawPredictor() {
    noFill();
    stroke(255);
    strokeWeight(1f);  

    float increment = ((float)predictorWidth/(float)(predictor.binSize));
    //vertical grid
    float runningX = predictorX;
    for(int i = 0; i<=predictor.binSize;i++){
        line(runningX, predictorY, runningX, predictorY+predictorHeight);
        runningX += increment;
    }
    //horizontal grid
    for(int i = 0; i<=predictor.countMax; i+=predictor.countInterval){
        float py = map(i, predictor.countMin, predictor.countMax, predictorY+predictorHeight, predictorY);
        line(predictorX, py, predictorX+predictorWidth, py);
    }


    //data
    int[][][] counters = predictor.counters;
    if(_currentBtnSampleIndex != -1){
        //draw color overlay
        fill(colorSampleArray[_currentBtnSampleIndex], 60);
        noStroke();
        rectMode(CORNER);
        rect(predictorRect.x, predictorRect.y, predictorRect.width, predictorRect.height);

        histogram_rects = new Rectangle[predictor.binSize];
        int[][] sample = counters[_currentBtnSampleIndex];
        //possible
        int[] possible = sample[0];
        //impossible
        int[] impossible = sample[1];
        rectMode(CORNER);
        float px = predictorX;
        for(int j = 0; j< possible.length; j++){
            float ph1 = constrain(map(possible[j], predictor.countMin, predictor.countMax, 0, predictorHeight), 0, predictorHeight);
            float py = predictorY+predictorHeight - ph1; //LEFT TOP corner
            //possible
            stroke(colorText);
            fill(colorPossible);
            rect(px, py, increment, ph1);

            //impossible
            float ph2 = constrain(map(impossible[j], predictor.countMin, predictor.countMax, 0, predictorHeight), 0, predictorHeight);
            py -= ph2; //LEFT TOP CORNER
            fill(colorImpossible);
            rect(px, py, increment, ph2);

            //record rectange
            histogram_rects[j] = new Rectangle(round(px), round(py), round(increment), round(ph1+ph2)); ////////////////////

            //if selected
            if(j == selected_bar){
                stroke(colorCyan);
                fill(color_cyan_trans);
                rect(round(px), round(py), round(increment),round(ph1+ph2));

                drawVariantCounts( predictor.variant_counters[_currentBtnSampleIndex][j]);
                // println("selected:");
                // println(Arrays.deepToString(predictor.variant_counters[_currentBtnSampleIndex][j]));

            }


            //increment
            px += increment;
        }
        //draw current coverage
        int coverage = data.samples[_sampleOrder[_currentBtnSampleIndex]].currentCoverage;//sampleMinCoverages[i];
        px = constrain(map(coverage, 1, predictor.binSize+1, predictorX, predictorX+predictorWidth), predictorX, predictorX+predictorWidth);
        stroke(colorSampleArray[_currentBtnSampleIndex]);
        strokeWeight(1f);
        line(px, predictorY, px, predictorY+predictorHeight+ MARGIN);//(_currentBtnSampleIndex*5));
        fill(colorSampleArray[_currentBtnSampleIndex]);
        ellipse(px, predictorY+predictorHeight+MARGIN , 5,5); // (_currentBtnSampleIndex*5), 5, 5);
    }else{
        // println("debug: current sample index is "+_currentBtnSampleIndex);
        histogram_rects = null;
    }

        
    //coverage label
    fill(colorText);
    textAlign(CENTER, TOP);
    textSize(10);
    for(int i = 1; i<=predictor.binSize+1;i++){
        if(i == 1 || i%10 == 0){
            float px = map(i, 1, predictor.binSize+1, predictorX, predictorX+predictorWidth);
            text((i),px+(increment/2), predictorY+predictorHeight);
        }
    }
}
public void drawTable() {
    rectMode(CORNERS);
    Cell[][][] table = data.table;

    Cell mouseOverCell = null;
    //drawing cells
    for (int i = 0; i < table.length; i++) {
        for (int j = 0; j < table.length; j++) {
            for (int k = 0; k < table.length; k++) {
                Cell cell = table[i][j][k];
                Rectangle rec = cell.rect;
                int[][] countArrays = cell.getCountArrays();                

                if (i == 0 && j == 0 && k == 0) {
                    fill(colorBackground);
                    stroke(colorImpossible);
                    strokeWeight(1f);
                    rect(rec.x, rec.y, rec.x + cellWidth, rec.y + cellHeight);
                } else {
                    //inner 
                    if (_combination[i][j][k]) {
                        fill(colorPossible);
                    } else {
                        fill(colorImpossible);
                    }
                    noStroke();
                    rect(rec.x, rec.y, rec.x + cellWidth, rec.y + cellHeight);
                }

                //draw distribution;
                for (int n = 0; n < countArrays.length; n++) {
                    int[] countArray = countArrays[n];//cd.getCountArray(n);
                    int maxX = cell.maxCoverage;
                    int minX = cell.minCoverage;
                    int minY = 1;
                    fill(colorTransSampleArray[n]);
                    noStroke();
                    drawDistributionGraph(countArray, rec.x, rec.y, cellWidth, cellHeight, minX, maxX, minY, cellMaxCount);
                }

                //draw distribution outline
                for (int n = 0; n < countArrays.length; n++) {
                    int[] countArray = countArrays[n];//cd.getCountArray(n);
                    int maxX = cell.maxCoverage;
                    int minX = cell.minCoverage;
                    int minY = 1;
                    noFill();
                    stroke(colorSampleArray[n]);
                    strokeWeight(1f);
                    drawDistributionGraph(countArray, rec.x, rec.y, cellWidth, cellHeight, minX, maxX, minY, cellMaxCount);
                }
                //// MOUSE OVER ///////////////////////////////////////////////////////
                // if the mouse hover over the cell or selected
                if (cell.rect.contains(mouseX, mouseY) || cell.isSelected) {
                    noStroke();
                    fill(colorSelectionTrans);
                    rect(rec.x, rec.y, rec.x + cellWidth, rec.y + cellHeight);

                    //cell label
                    noStroke();
                    fill(colorWhite);
                    textSize(12);
                    textAlign(LEFT, TOP);
                    text(_cellLable[i][j][k], rec.x + LABEL_MARGIN, rec.y + LABEL_MARGIN);

                    //cell info
                    int variantCount = cell.variants.size();
                    textAlign(RIGHT, TOP);
                    text("variant:" + variantCount, rec.x + cellWidth - LABEL_MARGIN, rec.y + LABEL_MARGIN);

                    if(cell.rect.contains(mouseX, mouseY)){
                        mouseOverCell = cell;
                    }
                }
            }
        }
    }
    if(mouseOverCell != null){
        drawCellMouseOver(mouseOverCell);
    }
}

//draw scale and grid
public void drawCellMouseOver(Cell cell){    
    Rectangle rec = cell.rect;
    //show scale
    stroke(80);
    strokeWeight(1);
    noFill();
    line(rec.x, rec.y, rec.x, rec.y+rec.height);
    line(rec.x, rec.y+rec.height, rec.x+rec.width , rec.y+rec.height);
    //y ticks
    line(rec.x, rec.y, rec.x+10, rec.y);
    //middle
    line(rec.x, rec.y+(rec.height/2), rec.x+5,rec.y+(rec.height/2));
    //x ticks
    line(rec.x+rec.width, rec.y+rec.height, rec.x+rec.width, rec.y+rec.height-10);
    //middle
    line(rec.x+(rec.width/2), rec.y+rec.height, rec.x+(rec.width/2), rec.y+rec.height-10);
    //50
    line(rec.x+(rec.width/4), rec.y+rec.height, rec.x+(rec.width/4), rec.y+rec.height-5);
    //150
    line(rec.x+(rec.width/4 *3), rec.y+rec.height, rec.x+(rec.width/4*3), rec.y+rec.height-5);






    // //draw scale label background
    // fill(255, 180);
    // noStroke();
    // rectMode(CORNERS);
    // rect(rec.x - 35, rec.y, rec.x, rec.y+12);
    // rect(rec.x -15, rec.y+rec.height - 12, rec.x, rec.y+rec.height);

    //text
    //y axis
    fill(80);
    textAlign(RIGHT, CENTER);
    text(cellMaxCount, rec.x, rec.y);
    textAlign(RIGHT, CENTER);
    text("0", rec.x, rec.y+rec.height);
    text("200",rec.x, rec.y+(rec.height/2));

    //x axis
    // fill(colorWhite);
    // textAlign(LEFT, BOTTOM);
    // text(_COVERAGE_MIN, rec.x, rec.y+rec.height);
    fill(80);
    textAlign(CENTER, TOP);
    text(_COVERAGE_MAX, rec.x+rec.width, rec.y+rec.height);
    text(100, rec.x+(rec.width/2), rec.y+rec.height);
    text(50, rec.x+(rec.width/4), rec.y+rec.height);
    text(150, rec.x+(rec.width/4 *3), rec.y+rec.height);
}

//function to draw distribution graph
public void drawDistributionGraph(int[] countArray, int posX, int posY, int boxW, int boxH, int minX, int maxX, int minY, int maxY) {
    beginShape();
    vertex(posX, posY + boxH);
    for (int i = 0; i < countArray.length; i++) {
        int count = countArray[i];
        float py = constrain(map(count, minY, maxY, posY + boxH, posY), posY, posY + boxH);
        float px = map(i, 0, countArray.length - 1, posX, posX + boxW);
        vertex(px, py);

    }
    vertex(posX + boxW, posY + boxH);
    endShape();
}

public void drawTableLabel() {
    //parent 1 - horizontal
    textAlign(LEFT, BOTTOM);
    textSize(30);
    float textY = plotY1 - (MARGIN * 2) + textDescent() - 2;

    for (int i = 0; i < tableLabels.length; i++) {
        fill(colorSampleArray[0]);
        text(tableLabels[i], tableX[i], textY);
    }

    //parent2 - vertical
    textAlign(LEFT, BOTTOM);
    textSize(20);
    int label2x = plotX1 - (MARGIN * 4);
    pushMatrix();
    translate(label2x, tableY[0] + blockHeight);
    rotate(-HALF_PI);
    fill(colorSampleArray[1]);
    text(tableLabels[0], 0, 0);
    translate(-(blockHeight + MARGIN), 0);
    text(tableLabels[1], 0, 0);
    translate(-(blockHeight + MARGIN), 0);
    text(tableLabels[2], 0, 0);
    popMatrix();

    //child
    textAlign(LEFT, BOTTOM);
    textSize(10);
    fill(colorSampleArray[2]);
    pushMatrix();
    translate(plotX1 - (MARGIN * 2), tableY[0] + cellHeight);
    rotate(-HALF_PI);
    text(tableLabels[0], 0, 0);
    translate(-(cellHeight), 0);
    text(tableLabels[1], 0, 0);
    translate(-(cellHeight), 0);
    text(tableLabels[2], 0, 0);

    translate(-MARGIN - cellHeight, 0);
    text(tableLabels[0], 0, 0);
    translate(-(cellHeight), 0);
    text(tableLabels[1], 0, 0);
    translate(-(cellHeight), 0);
    text(tableLabels[2], 0, 0);

    translate(-MARGIN - cellHeight, 0);
    text(tableLabels[0], 0, 0);
    translate(-(cellHeight), 0);
    text(tableLabels[1], 0, 0);
    translate(-(cellHeight), 0);
    text(tableLabels[2], 0, 0);
    popMatrix();


    //draw lines
    noFill();
    strokeWeight(1f);
    //parent1
    int y1 = plotY1 - (MARGIN * 2);
    int y2 = y1 + MARGIN;
    drawHorizontalAxisDeco(tableX[0], y1, tableX[0] + cellWidth, y2, colorSampleArray[0]);
    drawHorizontalAxisDeco(tableX[1], y1, tableX[1] + cellWidth, y2, colorSampleArray[0]);
    drawHorizontalAxisDeco(tableX[2], y1, tableX[2] + cellWidth, y2, colorSampleArray[0]);

    //parent2
    int x1 = plotX1 - (MARGIN * 4);
    int x2 = x1 + MARGIN;
    drawVerticalAxisDeco(x1, tableY[0], x2, tableY[0] + blockHeight, colorSampleArray[1]);
    drawVerticalAxisDeco(x1, tableY[1], x2, tableY[1] + blockHeight, colorSampleArray[1]);
    drawVerticalAxisDeco(x1, tableY[2], x2, tableY[2] + blockHeight, colorSampleArray[1]);

    //child
    int gap = 1;
    x1 += (MARGIN * 2);
    x2 += (MARGIN * 2);
    drawVerticalAxisDeco(x1, tableY[0] + gap, x2, tableY[0] + cellHeight - gap, colorSampleArray[2]);
    drawVerticalAxisDeco(x1, (tableY[0] + gap + cellHeight), x2, tableY[0] - gap + (cellHeight * 2), colorSampleArray[2]);
    drawVerticalAxisDeco(x1, tableY[0] + gap + cellHeight * 2, x2, tableY[0] - gap + (cellHeight * 3), colorSampleArray[2]);
    drawVerticalAxisDeco(x1, tableY[1] + gap, x2, tableY[1] + cellHeight - gap, colorSampleArray[2]);
    drawVerticalAxisDeco(x1, (tableY[1] + gap + cellHeight), x2, tableY[1] - gap + (cellHeight * 2), colorSampleArray[2]);
    drawVerticalAxisDeco(x1, tableY[1] + gap + cellHeight * 2, x2, tableY[1] - gap + (cellHeight * 3), colorSampleArray[2]);
    drawVerticalAxisDeco(x1, tableY[2] + gap, x2, tableY[2] + cellHeight - gap, colorSampleArray[2]);
    drawVerticalAxisDeco(x1, (tableY[2] + gap + cellHeight), x2, tableY[2] - gap + (cellHeight * 2), colorSampleArray[2]);
    drawVerticalAxisDeco(x1, tableY[2] + gap + cellHeight * 2, x2, tableY[2] - gap + (cellHeight * 3), colorSampleArray[2]);
}
public void drawHorizontalAxisDeco(int x1, int y1, int x2, int y2, int c) {
    noFill();
    stroke(c);
    line(x1, y1, x1, y2);
    line(x1, y1, x2, y1);
    line(x2, y1, x2, y2);
}
public void drawVerticalAxisDeco(int x1, int y1, int x2, int y2, int c) {
    noFill();
    stroke(c);
    line(x1, y1, x2, y1);
    line(x1, y1, x1, y2);
    line(x1, y2, x2, y2);
}
//draw grlobal count
public void drawGlobalCount() {
    //record initial height
    if (gInitialHeightPossible == 0) {
        globalInitialCount = data.getGlobalCounts(); //get count
        //find global max count
        int higherCount = max(globalInitialCount[0], globalInitialCount[1]);
        //check the interval size, and adjust the interval size
        if(higherCount > 100000){
            globalBarInterval = 10000;
        }

        globalMaxCount = ((higherCount/globalBarInterval)+2)* globalBarInterval;

        int possibleHeight = round(map(globalInitialCount[0], 0, globalMaxCount, 0, globalPlotH));
        int impossibleHeight = round(map(globalInitialCount[1], 0, globalMaxCount, 0, globalPlotH));
        gInitialHeightPossible = globalPlotY2 - possibleHeight;
        gInitialHeightImpossible = globalPlotY2 - impossibleHeight;
        //debug
        println("max count = "+globalMaxCount);
        println("global initial variants count:" + Arrays.toString(globalInitialCount));
    }
    //draw grid
    stroke(colorWhite);
    strokeWeight(1f);
    noFill();
    int intervalCount = globalMaxCount / globalBarInterval;
    for (int i = 1; i < intervalCount; i++) {
        int count = globalBarInterval * i;
        int gridY = round(map(count, 0, globalMaxCount, globalPlotY2, globalPlotY2 - globalPlotH));
        line(globalPlotX1, gridY, globalPlotX2, gridY);
    }
    //axis
    stroke(colorWhite);
    strokeWeight(1f);
    line(globalPlotX1, globalPlotY2, globalPlotX2, globalPlotY2);
    line(globalPlotX2, globalPlotY1, globalPlotX2, globalPlotY2);

    //draw initial height
    rectMode(CORNERS);
    stroke(colorGray);
    strokeWeight(1f);
    noFill();
    rect(globalPossibleX, gInitialHeightPossible, globalPossibleX + globalBarW, globalPlotY2);
    rect(globalImpossibleX, gInitialHeightImpossible, globalImpossibleX + globalBarW, globalPlotY2);
    //intial count text
    fill(colorGray);
    textSize(12);
    textAlign(LEFT, BOTTOM);
    text(globalInitialCount[0], globalPossibleX, gInitialHeightPossible);
    text(globalInitialCount[1], globalImpossibleX, gInitialHeightImpossible);

    //draw bars
    int[] currentCount = data.getGlobalCounts();
    int possibleBar = round(map(currentCount[0], 0, globalMaxCount, 0, globalPlotH));
    int impossibleBar = round(map(currentCount[1], 0, globalMaxCount, 0, globalPlotH));

    stroke(colorText);
    fill(colorPossible);
    rect(globalPossibleX, globalPlotY2 - possibleBar, globalPossibleX + globalBarW, globalPlotY2);
    fill(colorImpossible);
    rect(globalImpossibleX, globalPlotY2 - impossibleBar, globalImpossibleX + globalBarW, globalPlotY2);
    //current count text
    fill(colorText);
    textAlign(LEFT, BOTTOM);
    textSize(12);
    text(currentCount[0], globalPossibleX, globalPlotY2 - possibleBar);
    text(currentCount[1], globalImpossibleX, globalPlotY2 - impossibleBar);

        //textlabel
    fill(colorText);
    noStroke();
    textSize(15);
    textLeading(15);
    textAlign(RIGHT, BOTTOM);
    text("Global\nvariant\ncount", globalPlotX2, globalPlotY1);
}

public void drawVariantCounts(int[][][] counters){
    Cell[][][] table = data.table;
    //drawing cells
    for (int i = 0; i < table.length; i++) {
        for (int j = 0; j < table.length; j++) {
            for (int k = 0; k < table.length; k++) {
                int count = counters[i][j][k];
                if(count >0){
                    // if only more than one count
                    Cell cell = table[i][j][k];
                    Rectangle rec = cell.rect;
                    //rectangle
                    noStroke();
                    fill(colorSelectionTrans);
                    rectMode(CORNER);
                    rect(rec.x, rec.y, rec.width, rec.height);

                    fill(colorWhite);
                    textAlign(RIGHT, TOP);
                    textSize(12);
                    text("variant:"+count, rec.x+rec.width, rec.y);
                    // fill(color_cyan_trans);
                    // textAlign(LEFT, TOP);
                    // textSize(20);
                    // text(count, rec.x, rec.y);
                }


            }
        }
    }
}

public void drawTextBtns(){
    //export VCF
    textSize(12);
    textAlign(LEFT, TOP);

    fill((text_btn_mouseover[0]?colorCyan: colorText));
    text(export_label, text_btn_rects[0].x, text_btn_rects[1].y);

    //migrating mode
    fill((text_btn_mouseover[1]?colorCyan: colorText));
    text((_MIGRATING?migrating_label[0]:migrating_label[1]), text_btn_rects[1].x, text_btn_rects[1].y);

    //optimise buttion
    fill(text_btn_mouseover[2]?colorCyan: colorText);
    text(optimisation_label, text_btn_rects[2].x, text_btn_rects[2].y);


}



















class Distribution{
	int min, max, increment, arraySize;
    int[] countArray;
    int minCount, maxCount;
    int scale = 1;
    
    
    Distribution(int min,int  max,int increment) {
        this.min = min;
        this.max = max;
        this.increment = increment;
        this.arraySize = ((max - min)/increment) +1;
        this.countArray = new int[arraySize];
    }

    
    public void addCount(int vf) {
        //find the index, constrain to the array length
        int arrayIndex = round(constrain(map(vf, min, max, 0, arraySize-1), 0, arraySize-1));
        countArray[arrayIndex] ++;
    }
    
    public String toString(){
        return Arrays.toString(countArray)+":max="+maxCount+" min="+minCount;
    }

    public void findMaxAndMinCount() {
        int minC = Integer.MAX_VALUE;
        int maxC = Integer.MIN_VALUE;
        
        for(int i = 0; i < countArray.length; i++){
            int count = countArray[i];
            minC = (count < minC)?count:minC;
            maxC = (count > maxC)?count:maxC;
        }
        minCount = minC;
        maxCount = maxC;
    }



}
class FileChooseAlert implements Runnable {

        public void run() {
            JOptionPane.showMessageDialog(null, "Not all VCF files are selected. Please select VCF files.", "Before loading...", JOptionPane.PLAIN_MESSAGE);
        
            // if(selectedValue == JOptionPane.YES_OPTION){
            //     println("yes!");
            //         //add all cells
            //     for(int i = 0; i< data.table.length; i++){
            //         for(int j = 0; j<data.table.length; j++){
            //             for(int k = 0; k<data.table.length; k++){
            //                 Cell cell = data.table[i][j][k];
            //                 if(i == 0 && j == 0 && k ==0){
            //                 }else{
            //                     selectedCells.add(cell);                        
                                
            //                 }

            //             }
            //         }
            //     }
            // }
            // else if(selectedValue == JOptionPane.NO_OPTION){
            //     println("No");
            //     return;

            // }else if(selectedValue == JOptionPane.CANCEL_OPTION){
            //     println("Cancel!");
            //     return;
            // }

        }
    }
// File[] _files;
String[] _test_files;
File[] _files = new File[3];
int loadingSampleIndex;
long fileSize = 0;
long currentLoaded = 0;

int _FORMAT_AD_INDEX = -1; //index for AD element in VCF, since all three files may be structurally different.


boolean isTestMode = false;  //**************************************

class FileLoaderThread implements Runnable{
	public void run() {
        //file chooser
        //assueme three _test_files are selected
        if(isTestMode){
            _test_files = new String[3];
            _test_files[0] = "Michelin4_test.vcf";
            _test_files[1] = "Michelin5_test.vcf";
            _test_files[2] = "Michelin3_test.vcf";

            // _test_files[0] = "Michelin4.vcf";
            // _test_files[1] = "Michelin5.vcf";
            // _test_files[2] = "Michelin3.vcf";
            
            for(int i = 0; i<_test_files.length; i++){
                String file = _test_files[i];
                String[] fileName = splitTokens(file, ".");
                loadingSampleIndex = i;
                _sampleLabels[i] = fileName[0];
                loadDataVCF(file);
            }
            _PROGRESS_MESSSAGE = "All files are loaded, and now processing";
            println("--- done loading file"); 
            initialize();
        }else{
            for(int i = 0; i<_files.length; i++){
                File file = _files[i];
                String[] fileName = splitTokens(file.getName(), ".");
                loadingSampleIndex = i;
                _sampleLabels[i] = fileName[0];
                loadDataVCF(file);
            }
            _PROGRESS_MESSSAGE = "All files are loaded, and now processing";
            println("--- done loading file"); 
            initialize();
        }
    }
}
//test mode
public void loadDataVCF(String file) {
    //reset format ad index
    _FORMAT_AD_INDEX = -1;
    _PROGRESS_MESSSAGE = "Loading "+file+" ...";
    File f = new File(dataPath(file));
    fileSize = f.length();
    currentLoaded = 0; 
    // println(file+" file size ="+fileSize);
    try{
        //Get the file from the data folder
        BufferedReader reader = createReader(file);
        //Loop to read the file one line at a time
        String line = null;
        while ((line = reader.readLine()) != null) {
            currentLoaded += line.length();
            parseInfoVCF(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
//real mode
public void loadDataVCF(File file) {
    //reset format ad index
    _FORMAT_AD_INDEX = -1;
    _PROGRESS_MESSSAGE = "Loading "+file.getAbsolutePath()+" ...";
    File f = file;
    fileSize = f.length();
    currentLoaded = 0; 
    // println(file+" file size ="+fileSize);
    try{
        //Get the file from the data folder
        BufferedReader reader = createReader(file);
        //Loop to read the file one line at a time
        String line = null;
        while ((line = reader.readLine()) != null) {
            currentLoaded += line.length();
            parseInfoVCF(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void parseInfoVCF(String line) {
    if(line.startsWith("##")){
        //attribute
    }else if(line.startsWith("#")){
        //header
    }else{
        String[] s = split(line, TAB);
        if (s[0] != null) {
	        String chr = s[0].trim();
	        int chrIndex = data.getChrIndex(chr);
	        if(chrIndex == -1){
                println("Error:FileLoaderThread.parseInfoVCF():chr="+chr);
	            return;
	        }
	        int position = Integer.parseInt(s[1]);
	        String genotypeID = s[2];
	        String[] formats = splitTokens(s[9], ":");
	        if(_FORMAT_AD_INDEX == -1){
	            //find the AD index
	            String formatOrder = s[8];
	            String[] formatLabel = splitTokens(formatOrder, ":");
	            for(int i = 0; i< formatLabel.length; i++){
	                if(formatLabel[i].equals("AD")){
	                    this._FORMAT_AD_INDEX = i;
	                    break;
	                }
	            }   
        	}
        
	        //split format information
	        String[] ad = split(formats[_FORMAT_AD_INDEX], ',');
	        int refCount = Integer.parseInt(ad[0]);
	        int altCount = Integer.parseInt(ad[1]);
	        int totalCount = refCount + altCount;
	        int varfreq = round(((float)altCount / (float)totalCount)*100f);
	        
	        //find sample
	        Sample sample = data.findSample(loadingSampleIndex);
	        Variant variant = data.findVariant(chrIndex, position, genotypeID);
	        Row row = new Row(variant, totalCount, varfreq, loadingSampleIndex);
	        
	        sample.addRow(row);
	        variant.addRow(row);

        } else {
            System.out.println("string content is null");
        }
    }
}

class OptimisationThread implements Runnable{
	// float[][][] f_scores;
	int from;
	int to;
	int length;

	float highest_score = 0; 
	int[] highest_index = new int[3];

	boolean doneOnce = false;

	int totalIteration;
	int currentIndex;

	OptimisationThread(int from, int to){
		this.from = from;
		this.to = to;
		this.length = to - from +1;
		// this.f_scores = new float[length][length][length];

		currentIndex = 0;
		totalIteration = length*length*length;

	}

	public void run(){
		if(!doneOnce){
			//calculate f score
			int tp_fn = globalInitialCount[0];

			for(int i = from; i < length; i++){
				coverageSliders[0].value_current = i;
				coverageSliders[0].update();
				data.updateCoverageNonThread(data.samples[0], i);

				for(int j = from; j < length; j++){
					coverageSliders[1].value_current = i;
					coverageSliders[1].update();
					data.updateCoverageNonThread(data.samples[1], j);
					for(int k = from; k < length; k++){
						//new coverage i, j, k
						coverageSliders[2].value_current = i;
						coverageSliders[2].update();
						data.updateCoverageNonThread(data.samples[2], k);

						int[] gcount = data.getGlobalCounts();
						int tp = gcount[0];
						int tp_fp = tp+ gcount[1];

						float precision = (float)tp / (float)tp_fp;
						float recall = (float)tp /(float)tp_fn;
						float fscore = 2*((precision*recall)/(precision + recall));

						if(fscore > highest_score){
							highest_score = fscore;
							highest_index[0] = i;
							highest_index[1] = j;
							highest_index[2] = k;
						}

						currentIndex ++;
						loop();
						// println("***** deubug: gcount = "+Arrays.toString(gcount) +" i="+i+"  j="+j+"  k="+k +"  score ="+fscore);
					}
				}
			}
			println("optimisation finished: highest score ="+highest_score+"  index:"+Arrays.toString(highest_index));
			doneOnce = true;
			isOptimising = false;
			// predictor.updateAll();
			// hardReset();
            setOptimal();
		}else{
			
		}
	}
	//set optimal score
	public void setOptimal(){
		println("set optimal");
		for(int i = 0; i < 3; i++){
			coverageSliders[i].value_current = highest_index[i];
			coverageSliders[i].update();
			data.updateCoverageNonThread(data.samples[i], highest_index[i]);
		}
		predictor.updateAll();

	}
}
class PredictorThread implements Runnable{
    int cMin = 1;
    int cMax = 21;
    int countMin = 0;
    int countMax = 2000;  // hight max of predictor
    int binSize = cMax - cMin;
    int[][][] counters = new int[3][2][binSize]; // sample, possibleOrImpossible,coverage-1
    boolean running = true;
    int realCountMax = -1;
    int countInterval = 100;

    int[][][][][] variant_counters = new int[3][binSize][3][3][3]; //sampleIndex, coverage -1, cell indexes

    public void run() {
        // println("PredictorThread.run()");
        if(running){
            //do this..
            for(int i = 0; i < 3; i++){
                updateCounter(i);
            }
            running = false;
        }    
        //find counterMax
        if(realCountMax == -1){
            //not defined yet
            for(int i = 0; i < counters.length; i++){
                for(int j = 0; j < counters[0].length; j++){
                    for(int k = 0; k < counters[0][0].length; k++){
                        realCountMax = max(counters[i][j][k], realCountMax);
                    }
                }
            }
            //scalkng the display size
            //adjust interval size
            if(realCountMax > 10000){
                countInterval = 1000;
            }
            //adjust the size of max y on predictor
            countMax = ((realCountMax/countInterval)+1)*countInterval;
            println("debug: PredictorThread: real count max = "+realCountMax +"   countMax = "+countMax);
        }

        loop();
    }
    // i is the sample index
    public void updateCounter(int i){
        // println("predictorThread updateCounter()");
        // Sample s = data.samples[_sampleOrder[i]];
        counters[i] = predict(_sampleOrder[i]);
    }
    public void updateAll(){
        // println("PredictorThread.updateAll()");
        running = true;
        run();
    }
     //calculate predictor change
    public int[][] predict(int sampleIndex) {
        variant_counters[sampleIndex] = new int[binSize][3][3][3]; //number of variants
        //0=possible  1=impossible
        int[][] result = new int[2][binSize];
        Sample selected = data.samples[sampleIndex];
        //get array of ArrayList
        ArrayList<Row>[] allRows = selected.getRows(cMin, cMax); //****************shoud be hashset
        for(int i = 0; i< allRows.length; i++){
            //iterate per coverage level, by going through arraylist
            ArrayList<Row> rows = allRows[i];
            if(rows != null){
                for(int j = 0; j<rows.size(); j++){
                    Row r = rows.get(j);
                    Variant v = r.parentVariant;
                    int rowCoverage = r.coverage;
                     //if _MIGRATING
                     if(_MIGRATING){
                        //check its new cell index and see if the variant is possible
                        int[] indexes = v.cellIndex;
                        if(indexes != null ){
                            if(indexes[0]==0 && indexes[1]==0 && indexes[2]==0){
                                //regard it as no call, all ref/ref
                            }else{
                                boolean possible = _combination[indexes[0]][indexes[1]][indexes[2]];
                                if(possible){
                                    result[0][i]++;
                                }else{
                                    result[1][i]++;
                                }
                                //count   
                                variant_counters[sampleIndex][rowCoverage-1][indexes[0]][indexes[1]][indexes[2]]++;

                            }
                        }
                     }else{
                        //check the samples coverage
                        int coverage = selected.currentCoverage;
                        //if it is above the coverage, add count
                        if(rowCoverage >= coverage){
                            int[] indexes = v.cellIndex;
                            if(indexes != null ){
                                if(indexes[0]==0 && indexes[1]==0 && indexes[2]==0){
                                    //regard it as no call, all ref/ref
                                }else{
                                    boolean possible = _combination[indexes[0]][indexes[1]][indexes[2]];
                                    if(possible){
                                        result[0][i]++;
                                    }else{
                                        result[1][i]++;
                                    }
                                    //count   
                                    variant_counters[sampleIndex][rowCoverage-1][indexes[0]][indexes[1]][indexes[2]]++;

                                }
                            }   
                        }

                     }   
                    // }
                } 
            }
        }  
        return result;
    }

}
class Range{
	int sampleIndex;
	int handleSize = 2;
	int value_min, value_max, value_current_low, value_current_high;
	Rectangle display_rect;

	int dx_low, dx_high;
	Rectangle h_low, h_high;
	boolean isLowSelected = false;
	boolean isHighSelected = false;

	Range(int n, int min, int max, int def_l,int def_h, int dx, int dy, int dw, int dh){
		sampleIndex = n;
		value_min = min;
		value_max = max;
		value_current_low = def_l;
		value_current_high = def_h;
		display_rect = new Rectangle(dx, dy, dw, dh);
	}


	public void update(){
		dx_low= round(map(value_current_low, value_min, value_max, display_rect.x, display_rect.x+display_rect.width));
		dx_high= round(map(value_current_high, value_min, value_max, display_rect.x, display_rect.x+display_rect.width));

		h_low = new Rectangle(dx_low-handleSize, display_rect.y, handleSize*2,display_rect.height);
		h_high = new Rectangle(dx_high-handleSize, display_rect.y, handleSize*2,display_rect.height);

		// println("display rect="+display_rect);
		// println("handle rect="+handle_rect);

	}

	public void updateValue(int mx, int my){
		if(isLowSelected){
			//low varfreq change
			//update display value
			dx_low = constrain(mx, display_rect.x, dx_high-handleSize); //never go pass the high value
			h_low = new Rectangle(dx_low-handleSize, display_rect.y, handleSize*2,display_rect.height);
			value_current_low = round(map(dx_low, display_rect.x, display_rect.x+display_rect.width, value_min, value_max));
		}else if(isHighSelected){
			//high varfreq change
			dx_high = constrain(mx, dx_low+handleSize, display_rect.x+display_rect.width);
			h_high = new Rectangle(dx_high-handleSize, display_rect.y, handleSize*2,display_rect.height);
			value_current_high = round(map(dx_high, display_rect.x, display_rect.x+display_rect.width, value_min, value_max));
		}else{
			println("Error:neither handle selected");
		}
	}

}
class ReadAndWriteThread implements Runnable{
	public void run(){
        selectFolder("Select a folder to save VCF files:", "folderSelected");
	}

}
public void folderSelected(File selection){
    if(selection == null){
        println("folder not selected");
        isWritingFile = false;
    }else{
        String path = selection.getAbsolutePath()+"/";
        println("absolute path="+path);
        write(path);
    }
}


public void write(String path){
    println("Start writing");
    if(isTestMode){
        for(int i = 0; i<_test_files.length; i++){
            //update view
            // draw();
            String file = _test_files[i];
            loadingSampleIndex = i;
            readAndWrite(file, path);
        }
        println("End of writing");
        JOptionPane.showMessageDialog(null, "Files are saved.","VCF export",JOptionPane.PLAIN_MESSAGE);

        isWritingFile = false;
    }else{
        for(int i = 0; i<_files.length; i++){
            //update view
            // draw();
            File file = _files[i];
            loadingSampleIndex = i;
            readAndWrite(file, path);
        }
        println("End of writing");
        JOptionPane.showMessageDialog(null, "Files are saved.","VCF export",JOptionPane.PLAIN_MESSAGE);
        isWritingFile = false;
            
    }
}
class Row{
	Variant parentVariant;
	int coverage;
	int varfreq;
	int sampleIndex;
    boolean isRefRef, isRefAlt, isAltAlt;
	//constructor
	Row (Variant variant, int totalCount, int varf, int sIndex){
        parentVariant = variant;
        coverage = totalCount;
        varfreq = varf;
        sampleIndex = sIndex;
        // isValidCoverage = true; 
    }

    public String toString(){
    	return "c:"+coverage+"(v:"+varfreq+")";
    }
}
class Sample{
	String sampleID;
	HashMap<Integer, ArrayList<Row>> rowCoverageMap; //coverage and Arraylist
    HashMap<Integer, ArrayList<Row>> rowVarfreqMap; //Varfreq and ArrayList
    ArrayList<Row> rows; //all the rows 

    //Distribution
    Distribution varfreqDistribution, coverageDistribution;

    //coverage
    int currentCoverage = 1;
    //varfreq
    int currentVarfreqLow = 20;
    int currentVarfreqHigh = 90;
    int maxVarfreq = 100;

	//constructor
	Sample(String id){
		sampleID = id;
		rowCoverageMap = new HashMap<Integer, ArrayList<Row>>();
        rowVarfreqMap = new HashMap<Integer, ArrayList<Row>>();  
        rows = new ArrayList<Row>();
	}


	public void addRow(Row row) {
        int coverage = row.coverage;
        int varfreq = row.varfreq;
        //coverage 
        ArrayList<Row> cArray = rowCoverageMap.get(coverage);
        if(cArray == null){
            cArray = new ArrayList<Row>();
            rowCoverageMap.put(coverage, cArray);
        }
        cArray.add(row);
        //varfreq
        ArrayList<Row> vArray = rowVarfreqMap.get(varfreq);
        if(vArray == null){
            vArray = new ArrayList<Row>();
            rowVarfreqMap.put(varfreq, vArray);
        }
        vArray.add(row);
        //add to general arraylist
        rows.add(row);
    }

    //called only once at the beggining
    public void setupZygosity() {
        // println("debug:Sample.setupZygocity():"+sampleID);
        int discardedCounter = 0;
        for(int i = 0; i< rows.size(); i++){
            Row r = rows.get(i);
            int varfreq = r.varfreq;
            if(varfreq <= maxVarfreq && varfreq >= currentVarfreqHigh){
                //homozygous
                r.isRefRef = false;
                r.isRefAlt = false;
                r.isAltAlt = true;
            }else if( varfreq < currentVarfreqHigh && varfreq >= currentVarfreqLow){
                //hetrozygous
                r.isRefRef = false;
                r.isRefAlt = true;
                r.isAltAlt = false;
            }else{
                // println("Debug:discard:"+sampleID+":"+r);
                r.isRefRef = false;
                r.isRefAlt = false;
                r.isAltAlt = false;
                discardedCounter++;
            }
        }
        println("debug:"+sampleID+" has "+discardedCounter+" rows with varfreq below 20");
    }
    //for a set of Row object, update zygocity (slider interaction)
    public void updateZygosity(ArrayList<Row> rowsToUpdate) {
        if(rowsToUpdate != null){
            for(int i = 0; i< rowsToUpdate.size(); i++){
                Row r = rowsToUpdate.get(i);
                int varfreq = r.varfreq;
                if(varfreq <= maxVarfreq && varfreq >= currentVarfreqHigh){
                    //homozygous
                    r.isRefRef = false;
                    r.isRefAlt = false;
                    r.isAltAlt = true;
                }else if( varfreq <currentVarfreqHigh && varfreq >= currentVarfreqLow){
                    //hetrozygous
                    r.isRefRef = false;
                    r.isRefAlt = true;
                    r.isAltAlt = false;
                }else{
                    r.isRefRef = false;
                    r.isRefAlt = false;
                    r.isAltAlt = false;
                }
            }
        }
    }

    //get Rows from the spacified coverage range 
    public ArrayList<Row>[] getRows(int cMin, int cMax) {
        int bins = cMax - cMin;
        ArrayList<Row>[] result = (ArrayList<Row>[])new ArrayList[bins];
        
        for(int i = cMin; i<cMax; i++){
            ArrayList<Row> selectedRows = new ArrayList<Row>();
            ArrayList<Row> target = rowCoverageMap.get(i);
            if(target != null){
                selectedRows.addAll(target);
                result[i-1] = selectedRows;
            }
        }
        return result;
    }

    //update varfreq range and return arraylist of Row
    public ArrayList<Variant> updateVarfreqRange(int filterMin, int filterMax) {
        ArrayList<Variant> variantsToUpdate = new ArrayList<Variant>();
        int prevLow = currentVarfreqLow;
        int prevHigh = currentVarfreqHigh;
        currentVarfreqLow = filterMin;
        currentVarfreqHigh = filterMax;
        
        //low end
        if(prevLow > currentVarfreqLow){
            //decreased
            for(int i = currentVarfreqLow; i <prevLow; i++){
                ArrayList<Row> rowsToUpdate = rowVarfreqMap.get(i);
                updateZygosity(rowsToUpdate);
                variantsToUpdate.addAll(getVariants(rowsToUpdate));
            }
        }else if(prevLow < currentVarfreqLow){
            //increased
            for(int i = prevLow; i <currentVarfreqLow; i++){
                ArrayList<Row> rowsToUpdate = rowVarfreqMap.get(i);
                updateZygosity(rowsToUpdate);
                variantsToUpdate.addAll(getVariants(rowsToUpdate));
            }
        }
        
        //high end
        if(prevHigh > currentVarfreqHigh){
            //decreased
            for(int i = currentVarfreqHigh; i<prevHigh; i++){
                ArrayList<Row> rowsToUpdate = rowVarfreqMap.get(i);
                updateZygosity(rowsToUpdate);
                variantsToUpdate.addAll(getVariants(rowsToUpdate));
            }
        }else if( prevHigh < currentVarfreqHigh){
            for(int i = prevHigh; i<currentVarfreqHigh; i++){
                ArrayList<Row> rowsToUpdate = rowVarfreqMap.get(i);
                updateZygosity(rowsToUpdate);
                variantsToUpdate.addAll(getVariants(rowsToUpdate));
            }
        }
        return variantsToUpdate;
    }

    //get all the variants from the rows
    public ArrayList<Variant> getVariants(ArrayList<Row> rowsToUpdate) {
        if(rowsToUpdate != null){
            ArrayList<Variant> result = new ArrayList<Variant>();
            for(int i = 0; i< rowsToUpdate.size(); i++){
                Row r = rowsToUpdate.get(i);
                result.add(r.parentVariant);
            }
            return result;
        }
        return new ArrayList<Variant>();
    }

    //update minimum coverage
    //select and return vairants to update
    public ArrayList<Variant> updateCoverage(int filterMin, int prevCoverage) {
        //find all rows
        ArrayList<Row> rowsToUpdate = new ArrayList<Row>();
        if(filterMin > prevCoverage){
            //increasing coverage
            println("--increasing coverage: from "+prevCoverage+" to "+filterMin);
            for(int i = prevCoverage; i < filterMin; i++){
                ArrayList<Row> rows = rowCoverageMap.get(i);
                if(rows != null){ //some may have no Row at higher coverage
                    rowsToUpdate.addAll(rows);
                }
            }
        }else if(filterMin < prevCoverage){
            //decreasing coverage
            println("--decreasing coverage: from "+prevCoverage+" to "+filterMin);
            for(int i = filterMin; i< prevCoverage; i++){
                ArrayList<Row> rows = rowCoverageMap.get(i);
                if(rows != null){ //some may have no Row at higher coverage
                    rowsToUpdate.addAll(rows);
                }
            }
        }else{
            //System.out.println("no coverage change.... updateCoverage()");
        }
        
        //iterate through rows and find affected variants
        ArrayList<Variant> variantsToUpdate = new ArrayList<Variant>();
        for(int i = 0; i < rowsToUpdate.size(); i++){
            Row r = rowsToUpdate.get(i);
            Variant v = r.parentVariant;
            variantsToUpdate.add(v);
            // println("\t"+v.variantID+":"+v.rows);
        }
        
        return variantsToUpdate; 
    }

    public ConcurrentHashMap<String, Variant> updateCoverage2(int filterMin, int prevCoverage) {
        //find all rows
        ArrayList<Row> rowsToUpdate = new ArrayList<Row>();
        if(filterMin > prevCoverage){
            //increasing coverage
            // println("--increasing coverage: from "+prevCoverage+" to "+filterMin);
            for(int i = prevCoverage; i < filterMin; i++){
                ArrayList<Row> rows = rowCoverageMap.get(i);
                if(rows != null){ //some may have no Row at higher coverage
                    rowsToUpdate.addAll(rows);
                }
            }
        }else if(filterMin < prevCoverage){
            //decreasing coverage
            // println("--decreasing coverage: from "+prevCoverage+" to "+filterMin);
            for(int i = filterMin; i< prevCoverage; i++){
                ArrayList<Row> rows = rowCoverageMap.get(i);
                if(rows != null){ //some may have no Row at higher coverage
                    rowsToUpdate.addAll(rows);
                }
            }
        }else{
            //System.out.println("no coverage change.... updateCoverage()");
        }
        
        //iterate through rows and find affected variants
        ConcurrentHashMap<String, Variant> variantsToUpdate = new  ConcurrentHashMap<String, Variant>();
        for(int i = 0; i < rowsToUpdate.size(); i++){
            Row r = rowsToUpdate.get(i);
            Variant v = r.parentVariant;
            variantsToUpdate.put(v.chrIndex+"_"+v.position, v);
            // println("\t"+v.variantID+":"+v.rows);
        }
        
        return variantsToUpdate; 
    }


}
class Slider{
	int sampleIndex;
	int handleSize = 2;
	int value_min, value_max, value_current;
	Rectangle d_rect;
	int display_current_x;
	Rectangle handle_rect;


	//constructor
	Slider(int n, int min, int max, int def, int dx, int dy, int dw, int dh){
		sampleIndex = n;
		value_min = min;
		value_max = max;
		value_current = def;
		d_rect = new Rectangle(dx, dy, dw, dh);
	}

	public void update(){
		display_current_x = round(map(value_current, value_min, value_max, d_rect.x, d_rect.x+d_rect.width));
		handle_rect = new Rectangle(display_current_x-handleSize, d_rect.y, handleSize*2,d_rect.height);
		// println("display rect="+d_rect);
		// println("handle rect="+handle_rect);

	}

	//update the value based on the mouse position
	public void updateValue(int mx){
		display_current_x = constrain(mx, d_rect.x, d_rect.x+d_rect.width);
		handle_rect = new Rectangle(display_current_x-handleSize, d_rect.y, handleSize*2,d_rect.height);
		value_current = round(map(display_current_x, d_rect.x, d_rect.x+d_rect.width, value_min, value_max));
	}


}
class UpdateThread extends Thread{
	boolean running;
	int wait;
	String id;
	int count;
	boolean increasing;

	ArrayList<Variant> variants;

	ConcurrentHashMap<String, Variant> variantMap;


	// UpdateThread(ArrayList<Variant> vs){
	// 	running = false;
	// 	variants = vs;
	// }
	UpdateThread(ConcurrentHashMap<String, Variant> vs){
		running = false;
		variantMap = vs;
	}

	UpdateThread(ConcurrentHashMap<String, Variant> vs, boolean b){
		running = false;
		variantMap = vs;
		increasing = b;
	}

	public void start(){
		isUpdating = true;
		running = true;
		super.start();
	}

	public void run(){
		Iterator ite = variantMap.entrySet().iterator();

		while(running && ite.hasNext()){
			Map.Entry entry = (Map.Entry)ite.next();
			//action
			// count++;
			Variant v = (Variant) entry.getValue();
            if(_MIGRATING){
                int[] newIndex = data.getVariantIndex(v);
                int[] oldIndex = v.cellIndex;
                if(oldIndex == null){
                    //variants outside of the varfreq range
                }else{
                    // println("old index:"+Arrays.toString(v.cellIndex)+"  new index:"+Arrays.toString(newIndex)+ v.rows.get(0).coverage);
                    // if(increasing){

                    // }
                    //remove variant from the old cell
                    Cell cell = data.table[oldIndex[0]][oldIndex[1]][oldIndex[2]];
                    cell.removeVariant(v);
                    // println(Arrays.toString(oldIndex)+": total = "+cell.variants.size());
                    //add variant to the new cell
                    cell = data.table[newIndex[0]][newIndex[1]][newIndex[2]];
                    cell.addVariant(v);
                }
            }else{
                int[] cellIndex  = v.cellIndex;
                if(cellIndex == null){
                    //variants outside of the Varfreq Reange
                }else{
                    Cell cell = data.table[cellIndex[0]][cellIndex[1]][cellIndex[2]];
                    if(increasing){
                    	cell.removeVariant(v);
                    }else{
                    	cell.addVariant(v);
                    }
                }
            }     
		}
		isUpdating = false;
		
		// predictor.updateAll();//update preditor
		// println("UpdateThread is done!");

	}

	public void quit(){
		println("UpdateThread quitting");
		running = false;
		interrupt();
	}

	public void addVariant(ArrayList<Variant> vs){
		// for(int i = 0; i< vs.size(); i++){
		// 	if(vari)
		// }
		variants.addAll(vs);
	}


}
class Variant{
	int chrIndex;
	int position;
	ArrayList<Row> rows;
	String variantID;
    int[] cellIndex;


	//constructor
	Variant(int chr, int position, String id){
        this.position = position;
        chrIndex = chr;
        rows = new ArrayList<Row>();
        if(id.equals(".")){
            variantID = null;
        }else{
            variantID = id;
        }
    }

    public void addRow(Row r){    
        rows.add(r);
    }

    public Row getRow(int sampleIndex){
        for(int i = 0; i<rows.size(); i++){
            Row r = rows.get(i);
            if(r.sampleIndex == sampleIndex){
                return r;
            }
        }
        return null;
    }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "TrioVis" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
