package pl.tecna.gwt.connectors.client;

import java.util.ArrayList;

import pl.tecna.gwt.connectors.client.drop.AxisXYDragController;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;

public class Section extends HTML {
	
	public Point startPoint;
	public Point endPoint;
	public Connector connector;

	public SectionDecoration startPointDecoration;
	public SectionDecoration endPointDecoration;
	
	private int height;
	private int width;
	private final int additionalHeight = 2;
	private final int additionalWidth = 0;
	
//	protected boolean isVertical = false;
//	protected boolean isHorizontal = false;
	
	private AxisXYDragController sectionDragController;

	/**
	 * Section represents vertical or horizontal part of {@link Connector}. 
	 *
	 * @param  startPoint a {@link CornerPoint} or {@link EndPoint} where the Section starts
	 * @param  endPoint a {@link CornerPoint} or {@link EndPoint} where the Section ends
	 */
	public Section(Point startPoint, Point endPoint, Connector connector) {
		super();
		this.connector = connector;
		
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		
//		updateHorizontalVertical();
		if ((isHorizontal() == false) && (isVertical() == false)) {
			throw new IllegalArgumentException("Sections must be horizontal or vertical!");
		}
		
		// Count Section width and height
		this.height = Math.abs(endPoint.getTop() - startPoint.getTop());
		this.width = Math.abs(endPoint.getLeft() - startPoint.getLeft());
	}

//	private void updateHorizontalVertical() {
//		if (startPoint.getTop().compareTo(endPoint.getTop()) == 0) {
//			isHorizontal = true;
//			isVertical = false;
//		}
//		if (startPoint.getLeft().compareTo(endPoint.getLeft()) == 0) {
//			isVertical = true;
//			isHorizontal = false;
//		}
//		if ((isHorizontal == false) && (isVertical == false)) {
//			throw new IllegalArgumentException("Sections must be horizontal or vertical!");
//		}
//	}
	
	/**
	 * Shows Section on a given panel. The Section is represented by 
	 * horizontal or vertical line. The panel argument must be of type
	 * of AbsolutePanel.
	 * <p>
	 * This method also add a focus panel to the Section. Focus panel
	 * is necessary to provide drag and drop functionality to the Section.
	 * It also makes possible selecting a Section.
	 * <p>
	 * If the Section is already on the Diagram this method do nothing.
	 *
	 * @param  panel  an absolute panel on witch the line will be drawn
	 * @return        the section drawn on specified panel
	 */
	public void showOnDiagram(AbsolutePanel panel){
		// Create DIV to draw a line
		// Using CSS
		/*
		 * .gwt-connectors-line {
  				font-size: 1px;
  				line-height:1px;
  				background-color: black
			}
		   .gwt-connectors-line-vertical {
  				width:1px
			}
		   .gwt-connectors-line-horizontal {
  				height:1px
			}
		 */
		boolean allowHorizontalDragging = false;
		boolean allowVerticalDragging = false;
		
		// Set line look and behavior
		if (isVertical()) {
			this.setHTML(verticalLine(this.height + additionalHeight));
			allowHorizontalDragging = true;
		} else if (isHorizontal()) {
			this.setHTML(horizontalLine(this.width + additionalWidth));
			allowVerticalDragging = true;
		}
		
		// Set Section's cursor
		if (isVertical()) {
			DOM.setStyleAttribute(this.getElement(), "cursor", "w-resize"); 
		} else if (isHorizontal()) {
			DOM.setStyleAttribute(this.getElement(), "cursor", "n-resize"); 
		}

		// Add drag and drop functionality
		this.sectionDragController = new AxisXYDragController(panel, true, allowHorizontalDragging, allowVerticalDragging) {
			@Override
			public void dragStart() {
				// select Section and Connector
				Section.this.connector.select();
				
				// If dragged section startPoint or dragged section endPoint 
				// is glued to connectionPoint then split section into three 
				// to draw new lines to connectionPoint
				if (Section.this.startPointIsGluedToConnectionPoint() || Section.this.endPointIsGluedToConnectionPoint()) {
					// Calculate new CornerPoints
					ArrayList<CornerPoint> newCornerPoints = new ArrayList<CornerPoint>();
					Point sp = Section.this.startPoint;
					Point ep = Section.this.endPoint;
					CornerPoint cp1 = new CornerPoint(sp.getLeft() + (ep.getLeft() - sp.getLeft()) / 2,
													  sp.getTop() + (ep.getTop() - sp.getTop()) / 2);
					CornerPoint cp2 = new CornerPoint(sp.getLeft() + (ep.getLeft() - sp.getLeft()) / 2,
							  						  sp.getTop() + (ep.getTop() - sp.getTop()) / 2);
					newCornerPoints.add(cp1);
					newCornerPoints.add(cp2);
					// Split Section
					Section.this.splitSection(newCornerPoints);					
				}
				
				super.dragStart();
			}

			@Override
			public void dragMove() {
				if (Section.this.startPoint.getLeft() < Section.this.endPoint.getLeft()) {
					Section.this.startPoint.setLeft(context.draggable.getAbsoluteLeft() - context.boundaryPanel.getAbsoluteLeft());
					Section.this.endPoint.setLeft(context.draggable.getAbsoluteLeft() - context.boundaryPanel.getAbsoluteLeft() + width);
				} else {
					Section.this.startPoint.setLeft(context.draggable.getAbsoluteLeft() - context.boundaryPanel.getAbsoluteLeft() + width);
					Section.this.endPoint.setLeft(context.draggable.getAbsoluteLeft() - context.boundaryPanel.getAbsoluteLeft());
				}

				if (Section.this.startPoint.getTop() < Section.this.endPoint.getTop()) {
					Section.this.startPoint.setTop(context.draggable.getAbsoluteTop() - context.boundaryPanel.getAbsoluteTop());
					Section.this.endPoint.setTop(context.draggable.getAbsoluteTop() - context.boundaryPanel.getAbsoluteTop() + height);
				} else {
					Section.this.startPoint.setTop(context.draggable.getAbsoluteTop() - context.boundaryPanel.getAbsoluteTop() + height);
					Section.this.endPoint.setTop(context.draggable.getAbsoluteTop() - context.boundaryPanel.getAbsoluteTop());
				}
				
				if (Section.this.connector.getNextSection(Section.this) != null) {
					Section.this.connector.getNextSection(Section.this).update(); 
				};
				if (Section.this.connector.getPrevSection(Section.this) != null)   {
					Section.this.connector.getPrevSection(Section.this).update();
				};
				
				super.dragMove();
			}

			@Override
			public void dragEnd() {
				// deselect Section
//TODO				Section.this.connector.deselect();
				
//				System.out.println("dragEnd released");
//				System.out.println("startPointNeighbor top: " 
//						+ Section.this.startPointNeighbor().startPoint.getTop() 
//						+ ", Section top: " 
//						+ Section.this.endPoint.getTop());
				
				// If after dragging two or more neighbor Sections are aligned to the line
				// (they form one single line), those neighbor Sections are merged to one.
				if ((Section.this.connector.getPrevSection(Section.this) != null) &&
				    (Section.this.connector.getPrevSection(Section.this).hasNoDimensions())) {
					System.out.println("merge with preceding Section");
					// Loop 2 times to remove two preceding Sections
					for (int i = 0; i < 2; i++) {
						Section.this.startPoint = Section.this.connector.getPrevSection(Section.this).startPoint;
						Section.this.startPointDecoration = Section.this.connector.getPrevSection(Section.this).startPointDecoration;
						Section.this.connector.getPrevSection(Section.this).removeFromDiagram();
						Section.this.connector.sections.remove(Section.this.connector.getPrevSection(Section.this));
					}
				}
				if ((Section.this.connector.getNextSection(Section.this) != null) && 
				    (Section.this.connector.getNextSection(Section.this).hasNoDimensions())) {
					System.out.println("merge with succeeding Section");
					// Loop 2 times to remove two succeeding Sections
					for (int i = 0; i < 2; i++) {
						Section.this.endPoint = Section.this.connector.getNextSection(Section.this).endPoint;
						Section.this.endPointDecoration = Section.this.connector.getNextSection(Section.this).endPointDecoration;
						Section.this.connector.getNextSection(Section.this).removeFromDiagram();
						Section.this.connector.sections.remove(Section.this.connector.getNextSection(Section.this));
					}
				}
				
				// DEBUG - Display all sections in connector
//				String str2 = "Section dragEnd() - All Sections in Connector: ";
//				for (int i = 0; i < Section.this.connector.sections.size(); i++) {
//					str2 = str2 + "(" + Section.this.connector.sections.get(i).startPoint.getLeft() + "," +
//										Section.this.connector.sections.get(i).startPoint.getTop() + "," +
//										Section.this.connector.sections.get(i).endPoint.getLeft() + "," +
//										Section.this.connector.sections.get(i).endPoint.getTop() + ")";
//				}
//				System.out.println(str2);
				// END_DEBUG
				
				super.dragEnd();
				Section.this.update();
				
				// update end points
				Section.this.connector.endEndPoint.update();
				Section.this.connector.startEndPoint.update();

			}
			
			
		}; 

		// Add line to given panel
		panel.add(this, Math.min(this.startPoint.getLeft(), this.endPoint.getLeft()), 
				        Math.min(this.startPoint.getTop(), this.endPoint.getTop()));
		this.sectionDragController.makeDraggable(this);
		
		// Calculate decoration's direction and add SectionDecorations to diagram
		if (startPointDecoration != null) {
			this.startPointDecoration.showOnDiagram(panel, 
					calculateStartPointDecorationDirection(), 
					startPoint.getLeft(), startPoint.getTop());
		}
		if (endPointDecoration != null) {
			this.endPointDecoration.showOnDiagram(panel, 
					calculateEndPointDecorationDirection(), 
					endPoint.getLeft(), endPoint.getTop());
		}
	}

	private int calculateEndPointDecorationDirection() {
		if (isHorizontal()) {
			if (this.endPoint.getLeft() < this.startPoint.getLeft()) {
				return SectionDecoration.HORIZONTAL_LEFT;
			} else {
				return SectionDecoration.HORIZONTAL_RIGHT;
			}
		} else if (isVertical()){
			if (this.endPoint.getTop() < this.startPoint.getTop()) {
				return SectionDecoration.VERTICAL_UP;
			} else {
				return SectionDecoration.VERTICAL_DOWN;
			}
		}
		return 0;
	}

	private int calculateStartPointDecorationDirection() {
		if (isHorizontal()) {
			if (this.startPoint.getLeft() < this.endPoint.getLeft()) {
				return SectionDecoration.HORIZONTAL_LEFT;
			} else {
				return SectionDecoration.HORIZONTAL_RIGHT;
			}
		} else if (isVertical()){
			if (this.startPoint.getTop() < this.endPoint.getTop()) {
				return SectionDecoration.VERTICAL_UP;
			} else {
				return SectionDecoration.VERTICAL_DOWN;
			}
		}
		return 0;
	}

	/**
	 * Returns true if Section has no dimensions.
	 * It means that Section's width and height equals zero.
	 * 
	 * @return true if Section has no dimensions
	 * 		   false if Section has dimensions
	 */
	protected boolean hasNoDimensions() {
		if ((this.startPoint.getLeft().intValue() == this.endPoint.getLeft().intValue())
		 && (this.startPoint.getTop().intValue() == this.endPoint.getTop().intValue())){
			return true;
		} else {
			return false;
		}
	}

	protected boolean startPointIsGluedToConnectionPoint() {
		if (Section.this.startPoint instanceof EndPoint) { 
			if (((EndPoint) Section.this.startPoint).isGluedToConnectionPoint())	{
				return true;
			}
		}
		return false;
	}

	protected boolean endPointIsGluedToConnectionPoint() {
		if (Section.this.endPoint instanceof EndPoint) {
			 if (((EndPoint) Section.this.endPoint).isGluedToConnectionPoint())  {
				 return true;
			 }
		}
		return false;
	}

	/**
	 * Splits the Section using CornerPoints given as parameter. 
	 *
	 * @param  newCornerPoints an array of CornerPoints that determines a new shape of Section
	 * 			split up into few new Sections. 
	 */	
	protected void splitSection(ArrayList<CornerPoint> newCornerPoints) {
		
		
		
		// DEBUG
//		String str1 = "Section.splitSection: newCornerPoints: ";
//		str1 = str1 + "s:(" + this.connector.startEndPoint.getLeft() + "," + this.connector.startEndPoint.getTop() + ")";
//		for (int i = 0; i < newCornerPoints.size(); i++) {
//			str1 = str1 + "n:(" + newCornerPoints.get(i).getLeft() + "," + newCornerPoints.get(i).getTop() + ")";
//		}
//		for (int i = 0; i < this.connector.cornerPoints.size(); i++) {
//			str1 = str1 + "o:(" + this.connector.cornerPoints.get(i).getLeft() + "," + this.connector.cornerPoints.get(i).getTop() + ")";
//		}
//		str1 = str1 + "e:(" + this.connector.endEndPoint.getLeft() + "," + this.connector.endEndPoint.getTop() + ")";
//		System.out.println(str1);
		// END_DEBUG
		
		
		
		if (this.startPointIsGluedToConnectionPoint()) {
			// Add a new horizontal Section as the first Section in Connector and move decorations into this section
			Section s1 = new Section(this.startPoint, newCornerPoints.get(0), this.connector);
			s1.setStartPointDecoration(this.startPointDecoration);
			this.startPointDecoration = null;
			s1.showOnDiagram(this.connector.diagram.boundaryPanel);
			this.connector.sections.add(0, s1);
			
			// Add a new vertical section as the second in Connector
			Section s2 = new Section(newCornerPoints.get(0), newCornerPoints.get(1), this.connector);
			s2.showOnDiagram(this.connector.diagram.boundaryPanel);
			this.connector.sections.add(1, s2);
			
			// Reconnect dragged Section to the second CornerPoint
			this.startPoint = newCornerPoints.get(1);
			this.update();
		}
		
		if (this.endPointIsGluedToConnectionPoint()) {
			// Add a new vertical Section as the last but one Section in Connector
			Section s1 = new Section(newCornerPoints.get(0), newCornerPoints.get(1), this.connector);
			s1.showOnDiagram(this.connector.diagram.boundaryPanel);
			this.connector.sections.add(s1);
			
			// Add a new horizontal section as the last in Connector and move decorations into this section
			Section s2 = new Section(newCornerPoints.get(1), this.endPoint, this.connector);
			s2.setEndPointDecoration(this.endPointDecoration);
			this.endPointDecoration = null;
			s2.showOnDiagram(this.connector.diagram.boundaryPanel);
			this.connector.sections.add(s2);
			
			// Reconnect dragged Section to the first CornerPoint
			this.endPoint = newCornerPoints.get(0);
			this.update();
		}
		
		
		// DEBUG - Display all sections in connector
//		String str2 = "All Sections in Connector: ";
//		for (int i = 0; i < this.connector.sections.size(); i++) {
//			str2 = str2 + "(" + this.connector.sections.get(i).startPoint.getLeft() + "," +
//			                    this.connector.sections.get(i).startPoint.getTop() + "," +
//			                    this.connector.sections.get(i).endPoint.getLeft() + "," +
//			                    this.connector.sections.get(i).endPoint.getTop() + ")";
//		}
//		System.out.println(str2);
		// END_DEBUG
	}

	public void removeFromDiagram() {
		((AbsolutePanel)this.getParent()).remove(this);
	}

	private String verticalLine(int height) {
		return "<div class=\"gwt-connectors-line gwt-connectors-line-vertical\"" +
		" style=\"height:" + (height) + "px\">&nbsp;</div>";
	}

	private String horizontalLine(int width) {
		return	"<div class=\"gwt-connectors-line gwt-connectors-line-horizontal\"" + 
		        " style=\"width:" + (width) + "px\">&nbsp;</div>";
	}
	
	private String selectedVerticalLine(int height) {
		return "<div class=\"gwt-connectors-line-selected gwt-connectors-line-vertical\"" +
		" style=\"height:" + (height) + "px\">&nbsp;</div>";
	}
	
	private String selectedHorizontalLine(int width) {
		return	"<div class=\"gwt-connectors-line-selected gwt-connectors-line-horizontal\"" + 
		        " style=\"width:" + (width) + "px\">&nbsp;</div>";
	}
	

	/**
	 * Updates section displayed on a diagram. Recalculates new position and size 
	 * of the Section.
	 * Also sets the functionality of the horizontal or vertical dragging.
	 */
	protected void update() {
//		System.out.println("Section.update " +
//		           "(" + startPoint.getLeft() + "," + startPoint.getTop() + 
//		           "," + endPoint.getLeft() + "," + endPoint.getTop() + ") ");
		
		this.height = Math.abs(endPoint.getTop() - startPoint.getTop());
		this.width = Math.abs(endPoint.getLeft() - startPoint.getLeft());	
		
		if (isVertical()) {
			// if section is selected then draw it selected
			if (this.connector.isSelected()) {
				this.setHTML(selectedVerticalLine(this.height + additionalHeight));
			} else {
				this.setHTML(verticalLine(this.height + additionalHeight));
			}

			sectionDragController.setAllowHorizontalDragging(true);
			sectionDragController.setAllowVerticalDragging(false);

			((AbsolutePanel)this.getParent()).setWidgetPosition(this, 
					this.startPoint.getLeft(), 
			        Math.min(this.startPoint.getTop(), this.endPoint.getTop()));
			
		} else if (isHorizontal()) {
			// if section is selected then draw it selected
			if (this.connector.isSelected()) {
				this.setHTML(selectedHorizontalLine(this.width + additionalWidth));
			} else {
				this.setHTML(horizontalLine(this.width + additionalWidth));
			}
			
			sectionDragController.setAllowHorizontalDragging(false);
			sectionDragController.setAllowVerticalDragging(true);

			((AbsolutePanel)this.getParent()).setWidgetPosition(this, 
					Math.min(this.startPoint.getLeft(), this.endPoint.getLeft()), 
			        this.endPoint.getTop());
		}

		// Calculate decoration's direction and update decorations
		if (startPointDecoration != null) {
			this.startPointDecoration.update( 
					calculateStartPointDecorationDirection(), 
					startPoint.getLeft(), startPoint.getTop());
		}
		if (endPointDecoration != null) {
			this.endPointDecoration.update( 
					calculateEndPointDecorationDirection(), 
					endPoint.getLeft(), endPoint.getTop());
		}
	}
	
	public void select() {
//		System.out.println("Section.select ");	
		
		if (isVertical()) {
			this.setHTML(selectedVerticalLine(this.height + additionalHeight));
		} else if (isHorizontal()) {
			this.setHTML(selectedHorizontalLine(this.width + additionalWidth));
		}
		
		// Select Section Decorations
		if (startPointDecoration != null) {
			this.startPointDecoration.select();
		}
		if (endPointDecoration != null) {
			this.endPointDecoration.select();
		}
	}

	public void deselect() {
		if (isVertical()) {
			this.setHTML(verticalLine(this.height + additionalHeight));
		} else if (isHorizontal()) {
			this.setHTML(horizontalLine(this.width + additionalWidth));
		}

		// Deselect Section Decorations
		if (startPointDecoration != null) {
			this.startPointDecoration.deselect();
		}
		if (endPointDecoration != null) {
			this.endPointDecoration.deselect();
		}
	}
	
	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public boolean isVertical() {
		
		
//		System.out.println("Section.isVertical() " +
//        "(" + startPoint.getLeft() + "," + startPoint.getTop() + 
//        "," + endPoint.getLeft() + "," + endPoint.getTop() + ") ");
		
		
		if (!(this.hasNoDimensions())) {
//			System.out.println("Section.isVertical() -> Has Dimensions");
			if (this.startPoint.getLeft().intValue() == this.endPoint.getLeft().intValue()) {
				return true;
			}
		} else {	
//			System.out.println("Section.isVertical() -> Has No Dimensions");
			if ((this.connector.getNextSection(this) != null) 
				&& (this.connector.getNextSection(this).isHorizontal())){
				return true;
			}
			if ((this.connector.getPrevSection(this) != null) 
					&& (this.connector.getPrevSection(this).isHorizontal())){
				return true;
			}
		}
		return false;
	}

//	public void setVertical(boolean isVertical) {
//		this.isVertical = isVertical;
//	}

	public boolean isHorizontal() {

//		System.out.println("Section.isHorizontal() " +
//		        "(" + startPoint.getLeft() + "," + startPoint.getTop() + 
//		        "," + endPoint.getLeft() + "," + endPoint.getTop() + ") ");

		
		if (!(this.hasNoDimensions())) {
//			System.out.println("Section.isHorizontal() -> Has Dimensions");
			if (this.startPoint.getTop().intValue() == this.endPoint.getTop().intValue()) {
				return true;
			}
		} else {	
//			System.out.println("Section.isHorizontal() -> Has No Dimensions");
			if ((this.connector.getNextSection(this) != null) 
				&& (this.connector.getNextSection(this).isVertical())){
				return true;
			}
			if ((this.connector.getPrevSection(this) != null) 
					&& (this.connector.getPrevSection(this).isVertical())){
				return true;
			}
		}
		return false;
	}

//	public void setHorizontal(boolean isHorizontal) {
//		this.isHorizontal = isHorizontal;
//	}

	public SectionDecoration getStartPointDecoration() {
		return startPointDecoration;
	}

	public void setStartPointDecoration(SectionDecoration startPointDecoration) {
		this.startPointDecoration = startPointDecoration;
	}

	public SectionDecoration getEndPointDecoration() {
		return endPointDecoration;
	}

	public void setEndPointDecoration(SectionDecoration endPointDecoration) {
		this.endPointDecoration = endPointDecoration;
	}
	
}
