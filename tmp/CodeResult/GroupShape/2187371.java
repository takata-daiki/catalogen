package uk.ac.lkl.migen.system.server.manipulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import uk.ac.lkl.common.util.reflect.GenericClass;
import uk.ac.lkl.common.util.restlet.EntityMapper;
import uk.ac.lkl.common.util.restlet.RestletException;
import uk.ac.lkl.common.util.restlet.server.EntityId;
import uk.ac.lkl.common.util.restlet.server.EntityManipulator;
import uk.ac.lkl.common.util.restlet.server.EntityTableManipulator;
import uk.ac.lkl.common.util.restlet.server.LinkingTableManipulator;
import uk.ac.lkl.common.util.restlet.server.TableManipulatorManager;
import uk.ac.lkl.migen.system.expresser.model.shape.block.BlockShape;
import uk.ac.lkl.migen.system.expresser.model.shape.block.GroupShape;

/* This class is obsolete as of Dic 2011 */
@Deprecated
@SuppressWarnings("unused")
public class GroupShapeTableManipulator extends
	EntityTableManipulator<GroupShape> {

    private LinkingTableManipulator<GroupShape, BlockShape> subshapeTable;

    private EntityManipulator<BlockShape> blockShapeManipulator;

    public GroupShapeTableManipulator(Connection connection)
	    throws SQLException {
	super(connection, GenericClass.getSimple(GroupShape.class),
		"GroupShape");
    }

    @Override
    protected void extractManipulators(TableManipulatorManager manager)
	    throws RestletException {
	subshapeTable = manager.getLinkingManipulator(GroupShape.class,
		BlockShape.class);
	blockShapeManipulator = manager.getManipulator(BlockShape.class);
    }

    @Override
    public String[] getColumnNames() {
	// just has an id field which is taken care of in base class
	return new String[] {};
    }

    @Override
    protected GroupShape extractObject(ResultSet resultSet, EntityMapper mapper)
	    throws SQLException, RestletException {
	EntityId<GroupShape> groupId = getId(resultSet);
	List<BlockShape> subshapes = subshapeTable.getChildEntityList(groupId,
		null, mapper);
	return new GroupShape(subshapes);
    }

    @Override
    protected void populateInsertStatement(PreparedStatement statement,
	    GroupShape groupShape, EntityMapper mapper) throws SQLException {
	// do nothing. Is part of post-processing.
    }

    @Override
    protected void processInsertedObject(GroupShape groupShape,
	    EntityId<GroupShape> groupShapeId, EntityMapper mapper)
	    throws SQLException, RestletException {
	List<BlockShape> subshapes = groupShape.getShapes();
	for (BlockShape subshape : subshapes)
	    subshapeTable.addLink(groupShapeId, subshape, mapper);
    }

    // is this needed?
    @Override
    protected void populateUpdateStatement(PreparedStatement statement,
	    GroupShape groupShape) throws SQLException {
	// TODO Auto-generated method stub
    }

}
