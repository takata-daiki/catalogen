package uk.ac.lkl.migen.system.expresser.model.shape.block.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.lkl.common.util.value.Number;
import uk.ac.lkl.migen.system.expresser.model.ModelColor;
import uk.ac.lkl.migen.system.expresser.model.shape.block.*;
import uk.ac.lkl.migen.system.expresser.test.StandaloneExpresserTest;

public class TestGroup extends StandaloneExpresserTest {

    /**
     * The width and height of a newly-created group should both be 0.
     * 
     */
    @Test
    public void testWidthAndHeightWhenGroupEmpty() {
	GroupShape group = new GroupShape();
	assertEquals(group.getWidth(), 0);
	assertEquals(group.getHeight(), 0);
    }

    /**
     * The width and height of a group consisting of a single unit block should
     * both be 1.
     */
    @Test
    public void testWidthAndHeightWithSingleBlock() {
	BasicShape block = new BasicShape(ModelColor.YELLOW);
	GroupShape group = new GroupShape();
	group.addShape(block);
	assertEquals(group.getWidth(), 1);
	assertEquals(group.getHeight(), 1);
    }

    /**
     * For a group consisting of a single block that has width and height set to
     * values other than 1, if the block should have the same width and height.
     * 
     */
    @Test
    public void testWidthAndHeightWithResizedSingleBlock() {
	BasicShape block = new BasicShape(ModelColor.YELLOW);
	block.setWidth(new Number(4));
	block.setHeight(new Number(5));
	GroupShape group = new GroupShape();
	group.addShape(block);
	assertEquals(group.getWidth(), 4);
	assertEquals(group.getHeight(), 5);
    }

    /**
     * If a unit block is added to a group, its size should be 1x1 initially. If
     * this block's size is now changed (after it has been added), the group's
     * width and height should update appropriately.
     * 
     */
    @Test
    public void testWidthAndHeightWithResizedSingleBlockAfterAdding() {
	BasicShape block = new BasicShape(ModelColor.YELLOW);
	System.out.println("Block: " + block.hashCode());
	GroupShape group = new GroupShape();

	group.addShape(block);
	assertEquals(group.getWidth(), 1);
	assertEquals(group.getHeight(), 1);

	boolean setOk = block.setWidth(new Number(4));
	assertEquals(setOk, true);
	// block.setHeight(new IntegerValue(5));
	assertEquals(group.getWidth(), 4);
	// assertEquals(group.getHeight(), 5);
    }

    @Test
    public void testWidthAndHeightWithTwoBlocks() {
	BasicShape block1 = new BasicShape(ModelColor.YELLOW);
	block1.setXValue(new Number(1));
	block1.setYValue(new Number(2));

	BasicShape block2 = new BasicShape(ModelColor.RED);
	block2.setXValue(new Number(3));
	block2.setYValue(new Number(4));

	GroupShape group = new GroupShape();
	group.addShape(block1);
	group.addShape(block2);

	assertEquals(group.getWidth(), 3);
	assertEquals(group.getHeight(), 3);

	// however, width (of a basic block) is enforced so setting this should
	// work
	block2.setWidth(new Number(10));
	assertEquals(group.getWidth(), 12);
	assertEquals(group.getHeight(), 3);
    }

    /**
     * Test that the initial location of a group is as expected.
     * 
     */
    @Test
    public void testGroupLocation() {
	BasicShape block1 = new BasicShape(ModelColor.YELLOW);
	block1.setXValue(new Number(1));
	block1.setYValue(new Number(2));

	BasicShape block2 = new BasicShape(ModelColor.RED);
	block2.setXValue(new Number(3));
	block2.setYValue(new Number(4));

	GroupShape group = new GroupShape();
	group.addShape(block1);
	group.addShape(block2);

	assertEquals(group.getX(), 1);
	assertEquals(group.getY(), 2);
    }

    /**
     * Test that a group can be moved and it moves all its shapes appropriately.
     * 
     */
    @Test
    public void testGroupMovement() {
	BasicShape block1 = new BasicShape(ModelColor.YELLOW);
	block1.setXValue(new Number(1));
	block1.setYValue(new Number(2));

	BasicShape block2 = new BasicShape(ModelColor.RED);
	block2.setXValue(new Number(3));
	block2.setYValue(new Number(4));

	GroupShape group = new GroupShape();
	group.addShape(block1);
	group.addShape(block2);

	// moves x location of group by 3
	boolean xSetOk = group.setXValue(new Number(4));
	assertEquals(xSetOk, true);

	BlockShape groupBlock1 = group.getShape(0);
	BlockShape groupBlock2 = group.getShape(1);

	// so the internal shapes should have moved
	assertEquals(groupBlock1.getXValue().intValue(), 4);
	assertEquals(groupBlock2.getXValue().intValue(), 6);

	// move y location of group by 2
	boolean ySetOk = group.setYValue(new Number(3));
	assertEquals(ySetOk, true);

	// so the internal shapes should have moved
	assertEquals(groupBlock1.getYValue().intValue(), 3);
	assertEquals(groupBlock2.getYValue().intValue(), 5);
    }

    @Test
    public void testRepeatedGroupPatternMovement() {
	BasicShape block1 = new BasicShape(ModelColor.YELLOW);
	block1.setXValue(new Number(1));
	block1.setYValue(new Number(2));

	BasicShape block2 = new BasicShape(ModelColor.RED);
	block2.setXValue(new Number(3));
	block2.setYValue(new Number(4));

	GroupShape group = new GroupShape();
	group.addShape(block1);
	group.addShape(block2);

	PatternShape pattern = new PatternShape();
	pattern.setShape(group);
	pattern.setIterations(0);
	pattern.setIncrement(BlockShape.X, new Number(1));

	for (int i = 0; i < 100; i++) {
	    int x = (int) (Math.random() * 10);
	    int y = (int) (Math.random() * 10);
	    pattern.setXValue(new Number(x));
	    pattern.setYValue(new Number(y));
	}
    }

}
