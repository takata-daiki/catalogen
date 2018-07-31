package org.fenrir.jcollector.ui.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fenrir.jcollector.ui.ApplicationWindowManager;
import org.fenrir.jcollector.util.CommonUtils;
import org.fenrir.jcollector.dto.ICell;
import org.fenrir.jcollector.dto.PictureCell;

/**
 * TODO v1.0 Javadoc
 * @author Antonio Archilla Nava
 * @version v0.1.20111012
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel
{
	private static final int CELL_TOTAL_WIDTH = 160;
	private static final int CELL_TOTAL_HEIGHT = 190;
	private static final int CELL_IMAGE_WIDTH = 150;
	private static final int CELL_IMAGE_HEIGHT = 150;	
	private static final int CELL_TEXT_OFFSET_Y = 170;
	private static final int CELL_TEXT_LENGTH = 20;
	
	private Log log = LogFactory.getLog(Canvas.class);
	
	private List<ICell> cells = new ArrayList<ICell>();
	private int firstRow = 0;
	
	/* Variables pel rang de miniatures carregades */
	// Límit d'imatges carregades. Per defecte 0 (ilimitat)
	private int fetchLimit = 0;
	private int firstElement = 0;
	private int lastElement = 0;
	private boolean updateCells = false;
	
	private LoadContentWorker worker;

	public Canvas()
	{
		
	}
	
	public Canvas(int fetchLimit)
	{
		this.fetchLimit = fetchLimit;
	}
	
	public List<ICell> getCells()
	{
		return cells;
	}
	
	public void addCells(java.util.Collection<ICell> cells)
	{
		this.cells.addAll(cells);
		updateCells = true;
	}
	
	public void addCell(ICell cell)
	{		
		this.cells.add(cell);
		updateCells = true;
	}
	
	public void removeCell(ICell cell)
	{
		this.cells.remove(cell);
		updateCells = true;
	}
	
	public void clearCells()
	{
		this.cells.clear();
		firstElement = 0;
		lastElement = 0;
		updateCells = true;
	}
	
	public int getCellCount()
	{
		return cells.size();
	}
	
	public int getFetchLimit()
	{
		return fetchLimit;
	}
	
	public void setFetchLimit(int fetchLimit)
	{
		this.fetchLimit = fetchLimit;
	}
	
	public int getFirstRow()
	{
		return firstRow;
	}
	
	public void setFirstRow(int firstRow)
	{
		this.firstRow = firstRow;
	}
	
	public int getVisibleRows()
	{
		Dimension dimension = getSize();			
        int rows = (int)Math.floor(dimension.getHeight() / CELL_TOTAL_HEIGHT);
        
        return rows;
	}
	
	public int getVisibleColumns()
	{
		Dimension dimension = getSize();
        int columns = (int)Math.floor(dimension.getWidth() / CELL_TOTAL_WIDTH);
        
        return columns;
	}
	
	public int getTotalRows()
	{
		int totalRows = cells.size() / getVisibleColumns();
		if(cells.size()%getVisibleColumns()!=0){
			totalRows++;
		}
		
		return totalRows;
	}
	
	public ICell getSelectedCell(int x, int y)
	{
		int row = y / CELL_TOTAL_HEIGHT;
		int column = x / CELL_TOTAL_WIDTH;
		int visibleColumns = getVisibleColumns();
		int visibleRows = getVisibleRows();
		int index = (firstRow + row) * visibleColumns + column;
		
		if(index<cells.size()){
			ICell cell = cells.get(index);
			/* Si es fa click sobre l'espai en blanc que hi ha després de la última fila d'imatges
			 * es pot donar el cas que es calculi una cel.la que encara no ha estat dibuixada i per
			 * això encara no tingui posició assignada.
			 */
			if(row<visibleRows && cell.getPosition()!=null && cell.getImage()!=null){
				BufferedImage image = cell.getImage();
				int offsetx = (int)(CELL_IMAGE_WIDTH / 2 - image.getWidth() / 2);
        		int offsety = (int)(CELL_IMAGE_HEIGHT / 2 - image.getHeight() / 2);
				int originX = cell.getPosition().x + offsetx;
				int originY = cell.getPosition().y + offsety;				
				Rectangle area = new Rectangle(originX, originY, image.getWidth(), image.getHeight());
				if(area.contains(new Point(x, y))){
					return cell;
				}
			}
		}
		
		return null;
	}
	
	public boolean isCellVisible(ICell cell)
	{
		int visibleColumns = getVisibleColumns();
		int visibleRows = getVisibleRows();
		int rowIndex = cells.indexOf(cell) / visibleColumns;
		if(rowIndex>=firstRow && rowIndex<firstRow+visibleRows){
			return true;
		}
		
		return false;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
     
		Dimension dimension = getSize();
		int rows = getVisibleRows();
		int columns = getVisibleColumns();
		
		if(log.isDebugEnabled()){
        	log.debug("[Canvas::paintComponent] WIDTH: " + (int)dimension.getHeight() + "; HEIGHT: " + (int)dimension.getHeight());
        	log.debug("[Canvas::paintComponent] rows: " + rows + " / columns: " + columns);
        }
		
		// Es neteja la pantalla
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, (int)dimension.getWidth(), (int)dimension.getHeight());
        // Es reestableix el color negre per pintar el text
        g2.setPaint(Color.BLACK);
        
        File fLoadingImage = new File("data/icons/image_64.png");
        BufferedImage loadingImage = null;
        try{
        	loadingImage = ImageIO.read(fLoadingImage);
        	
        	int cont = firstRow * columns;
	        for(int i=0; i<rows && cont<cells.size(); i++){
	        	int yPosition = i * CELL_TOTAL_HEIGHT;
	        	for(int j=0; j<columns && cont<cells.size(); j++){
	        		ICell cell = cells.get(cont);
	        		int xPosition = j * CELL_TOTAL_WIDTH;	  		        				        		
	        				        		
	        		BufferedImage image;
	        		// Si encara no ha estat carregada la imatge, es posa una per defecte
	        		if(cell.getImage()==null){
	        			image = loadingImage;
	        		}
	        		else{
	        			image = cell.getImage();
	        		}
	        		// Es calcula l'offset per centrar la imatge
	        		int offsetx = (int)(CELL_IMAGE_WIDTH / 2 - image.getWidth() / 2);
	        		int offsety = (int)(CELL_IMAGE_HEIGHT / 2 - image.getHeight() / 2);
	        		g2.drawImage(image, xPosition + offsetx, yPosition + offsety, this);
	        		String cellText = cell.getText();
	        		if(cellText.length()>CELL_TEXT_LENGTH){
	        			cellText = cellText.substring(0, CELL_TEXT_LENGTH) + "...";
	        		}
	        		g2.drawString(cellText, xPosition, yPosition + CELL_TEXT_OFFSET_Y);
	        		
	        		// S'actualitzen les dades de la posició
	        		cell.setPostion(new Point(xPosition, yPosition));
	        		
	        		cont++;
	        	}
	        }    
        }
        catch(IOException e){	        	
        	log.error("[Canvas::paintComponent] Error al carregar miniatures: " + e.getMessage(), e);
        	JOptionPane.showMessageDialog(ApplicationWindowManager.getInstance().getMainWindow(), "Error al carregar miniatures", "Error", JOptionPane.ERROR_MESSAGE);
        }	        	                    
	}
	
	public void updateContents(int firstRow)
	{
		setFirstRow(firstRow);
		
		/* Es carreguen les miniatures si fa falta */		
		int visibleColumns = getVisibleColumns();
		int visibleRows = getVisibleRows();
		int startIndex = firstRow * visibleColumns;
		int endIndex = startIndex + (visibleColumns * visibleRows) - 1;
		if(endIndex>cells.size()){
			endIndex = cells.size() - 1;
		}		
		
		if(log.isDebugEnabled()){
			log.debug("[Canvas::updateContents] Actualitzant fila: " + firstRow);
			log.debug("[Canvas::updateContents] Index [rang actual]: " + startIndex + " - " + endIndex + " [" + firstElement + " - " + lastElement + "]");
		}
		
		if(startIndex>=firstElement && endIndex<=lastElement && !updateCells){				
			if(log.isDebugEnabled()){
				log.debug("[Canvas::updateContents] Index dins de rang carregat");
			}				
			repaint();
			
			return;
		}		
		
		updateCells = false;
			
		int newFirstElement = 0;
		int newLastElement = 0;
		int startLoadingIndex = 0;
		int endLoadingIndex = 0;
		int startUnloadingIndex = 0;
		int endUnLoadingIndex = 0;
		if(fetchLimit==0){
			newFirstElement = 0;
			newLastElement = cells.size() - 1;
			
			// Es carregaràn totes les cel.les
			startLoadingIndex = 0; 
			endLoadingIndex = newLastElement + 1;
			startUnloadingIndex = 0; 
			endUnLoadingIndex = 0;
		}
		else{
			int cellsCount = cells.size();		
			int amount = endIndex - startIndex + 1;
			int offset = (fetchLimit - amount) / 2;
	
			int startOffset = offset;
			if(startIndex-offset<0){
				startOffset = startIndex;
			}
			int endOffset = offset;
			if(offset+endIndex>=cellsCount){
				endOffset = cellsCount - (endIndex + 1);
			}
			
			// Es compensa l'offset en cas que no es pugui utilitzar tot al principi o al final
			if(startOffset<offset){
				endOffset += offset - startOffset;
			}
			if(endOffset<offset){
				startOffset += offset - endOffset;			
			}
			// Es calcula el nou rang d'elements carregats tenint en compte l'offset
			newFirstElement = (startIndex-startOffset)<0 ? 0 : Math.min(startIndex, startIndex-startOffset);
			newLastElement = (endIndex+endOffset)>=cellsCount ? cellsCount-1 : endIndex+endOffset;
							
			if(log.isDebugEnabled()){
				log.debug("[Canvas::updateContents] Offset [startOffset - endOffset]: " + offset + "["+ startOffset + " - " + endOffset + "]");
				log.debug("[Canvas::updateContents] Amount: " + amount);
				log.debug("[Canvas::updateContents] Nou rang: "+ newFirstElement + " - " + newLastElement);
			}			
				
			/* 1r cas: ··::.. */
			if(newFirstElement>firstElement && newFirstElement<lastElement){
				if(log.isDebugEnabled()){
					log.debug("[Canvas::updateContents] 1. Borrant del " + firstElement + " al " + newFirstElement);			
					log.debug("[Canvas::updateContents] 1. Carregant del " + lastElement + " al " + newLastElement);
				}		
				startUnloadingIndex = firstElement;
				endUnLoadingIndex = newFirstElement;
				startLoadingIndex = lastElement;
				endLoadingIndex = newLastElement;
			}				
			/* 2n cas: ·· .. || .. ··*/
			else if(newFirstElement>=lastElement || firstElement>=newLastElement){
				if(log.isDebugEnabled()){
					log.debug("[Canvas::updateContents] 2. Borrant del " + firstElement + " al " + lastElement);
					log.debug("[Canvas::updateContents] 2. Carregant del " + newFirstElement + " al " + newLastElement);
				}
				startUnloadingIndex = firstElement;
				endUnLoadingIndex = lastElement;
				startLoadingIndex = newFirstElement;
				endLoadingIndex = newLastElement;
			}
			/* 3r cas: ..::·· */
			else if(newFirstElement<firstElement && newFirstElement<lastElement){
				if(log.isDebugEnabled()){
					log.debug("[Canvas::updateContents] 3. Borrant del " + newLastElement + " al " + lastElement);
					log.debug("[Canvas::updateContents] 3. Carregant del " + newFirstElement + " al " + firstElement);
				}
				startUnloadingIndex = newLastElement;
				endUnLoadingIndex = lastElement;
				startLoadingIndex = newFirstElement;
				endLoadingIndex = firstElement;
			}				
		}
		
		/* Es fa un primer repaint per tal que, si la primera imatge tarda molt en actualitzar,
		 * es pinti la icona de càrrega mentrestant
		 */		
		repaint();
		
		// S'engega el procés de càrrega entre els índex calculats
		if(worker!=null && !worker.isCancelled()){
			worker.cancel(false);
		}
		worker = new LoadContentWorker(startLoadingIndex, endLoadingIndex+1, startUnloadingIndex, endUnLoadingIndex+1);
		worker.execute();
		
		// S'actualitzen els índexs del rang carregat
		firstElement = newFirstElement;
		lastElement = newLastElement;
	}
	
	private class LoadContentWorker extends SwingWorker<Void, ICell>
	{
		private int startLoadingIndex;
		private int loadingAmount;
		private int startUnloadingIndex;
		private int unloadingAmount;  
		
		public LoadContentWorker(int startLoadingIndex, int loadingAmount, 
				int startUnloadingIndex, int unloadingAmount)
		{
			this.startLoadingIndex = startLoadingIndex;
			this.loadingAmount = loadingAmount;
			this.startUnloadingIndex = startUnloadingIndex;
			this.unloadingAmount = unloadingAmount;
		}
		
		/**
		 * Mètode que s'encarregarà de realitzar la tasca d'actualitzar les miniatures
		 * S'executa en un Thread a part.
		 */
		@Override	
		protected Void doInBackground() 
		{			
			/* Esborrat de les imatges fora de rang */
			for(int i=startUnloadingIndex; i<unloadingAmount; i++){
				ICell cell = cells.get(i);
				if(!cell.isContainerCell()){
					cell.setImage(null);
				}
			}
			/* Càrrega de les imatges dins de rang */
			for(int i=startLoadingIndex; i<loadingAmount; i++){
				ICell cell = cells.get(i);
				if(!cell.isContainerCell()){
					BufferedImage image = CommonUtils.createAndLoadThumbnail(((PictureCell)cell).getElement());				
					cell.setImage(image);
					
					publish(cell);
				}
			}			
			
			return null;
		}
		
		@Override
		protected void process(List<ICell> chunks) 
		{
			boolean repaint = false;
			Iterator<ICell> iterator = chunks.iterator();
			while(iterator.hasNext() && !repaint){
				ICell cell = iterator.next();
				if(isCellVisible(cell)){
					repaint = true;
				}
			}	
			if(repaint){
				repaint();
			}
		}
	}
}
