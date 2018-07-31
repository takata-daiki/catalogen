package uk.ac.lkl.migen.system.expresser.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.KeyStroke;

import uk.ac.lkl.migen.system.expresser.model.ExpresserModel;

import uk.ac.lkl.migen.system.expresser.model.shape.block.BlockShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.GroupShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.PatternShape;

// action specific to block shapes
public class UngroupAction extends SelectionDependentAction {

    public UngroupAction(ExpresserModel model) {
	super(model, "Ungroup", "ungroup");
	putValue(SHORT_DESCRIPTION, "Split a group");
	putValue(MNEMONIC_KEY, KeyEvent.VK_U);
	putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U,
		ActionEvent.CTRL_MASK));
    }

    @Override
    protected boolean canBeUsed(Collection<BlockShape> selectedObjects) {
	for (BlockShape blockShape : selectedObjects) {
	    if (blockShape instanceof PatternShape) {
		PatternShape pattern = (PatternShape) blockShape;
		if (pattern.getIterations().intValue() == 1) {
		    BlockShape nestedShape = pattern.getShape();
		    if (nestedShape instanceof GroupShape) {
			return true;
		    }
		}
	    }
	}
	return false;
    }
    
    // ungroups whichever ones are groups (that are patterns that repeat once)
    @Override
    public void actionPerformed(ActionEvent e) {
	this.pressed();
	// create copy since deleting as iterate
	Collection<BlockShape> selectedObjects = 
	    new ArrayList<BlockShape>(getModel().getSelectedObjects());
	for (BlockShape shape : selectedObjects) {
	    if (shape instanceof PatternShape) {
		PatternShape pattern = (PatternShape) shape;
		if (pattern.getIterations().intValue() == 1) {
		    BlockShape nestedShape = pattern.getShape();
		    if (nestedShape instanceof GroupShape) {
			GroupShape group = (GroupShape) nestedShape;
			getModel().removeObject(shape);
			for (BlockShape groupShape : group.getShapes()) {
			    // note: getting the original shape will not be in the correct place
			    // so make copy here
			    BlockShape copy = groupShape.createUnlinkedCopy();
			    copy.setXValue(groupShape.getXValue());
			    copy.setYValue(groupShape.getYValue());
			    getModel().addObject(copy);
			    copy.setSelected(true);
			}
		    }
		}
	    }
	}
    }

}
