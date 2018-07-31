package Indices.coordinate.client;

import gis.GISRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Indices.bufferpool.BufferPool;
import Indices.coordinate.ds.prQuadtree;
import controllers.LogController;

/**
 * Coordinate index for longitude+lat searches.
 * 
 * From the spec: The coordinate index will use a bucket PR quadtree for the physical organization. In a bucket PR
 * quadtree, each leaf stores up to K data objects (for some fixed value of K). Upon insertion, if the added value would
 * fall into a leaf that is already full, then the region corresponding to the leaf will be partitioned into quadrants
 * and the K+1 data objects will be inserted into those quadrants as appropriate. As is the case with the regular PR
 * quadtree, this may lead to a sequence of partitioning steps, extending the relevant branch of the quadtree by
 * multiple levels. In this project, K will probably equal 4, but I reserve the right to specify a different bucket size
 * with little notice, so this should be easy to modify.
 * 
 * The index entries held in the quadtree will store a geographic coordinate and a collection of the file offsets of the
 * matching GIS records in the database file.
 * 
 * Note: do not confuse the bucket size with any limit on the number of GIS records that may be associated with a single
 * geographic coordinate. A quadtree node can contain index objects for up to K different geographic coordinates. Each
 * such index object can contain references to an unlimited number of different GIS records.
 * 
 * The PR quadtree implementation should follow good design practices, and its interface should be somewhat similar to
 * that of the BST. You are expected to implement different types for the leaf and internal nodes, with appropriate data
 * membership for each, and an abstract base type from which they are both derived. Of course, these were all
 * requirements for the related minor project.
 * 
 * You must be able to display the PR quadtree in a readable manner. PR quadtree display code is given in the course
 * notes. The display must clearly indicate the structure of the tree, the relationships between its nodes, and the data
 * objects in the leaf nodes.
 * 
 * @author Anthony
 * 
 */
public class CoordinateIndex {
	private LogController logController;
	private prQuadtree<CoordAndOffset> quadTree;
	private BufferPool bufferPool;

	public CoordinateIndex(LogController logController, BufferPool bufferPool) {
		this.logController = logController;
		this.bufferPool = bufferPool;
	}

	public void setBoundaries(long westLong, long eastLong, long southLat, long northLat) {
		this.quadTree = new prQuadtree<CoordAndOffset>(westLong, eastLong, southLat, northLat);
	}

	public void setBoundaries(String args[]) {
		this.quadTree = new prQuadtree<CoordAndOffset>(GISRecord.getSeconds(args[1]), GISRecord.getSeconds(args[2]),
				GISRecord.getSeconds(args[3]), GISRecord.getSeconds(args[4]));
	}

	public boolean index(GISRecord record, long offset) {
		// TODO Auto-generated method stub
		CoordAndOffset recordContainer = new CoordAndOffset(record.getLongitudeInSeconds(),
				record.getLatitudeInSeconds(), offset);
		return this.quadTree.insert(recordContainer);
	}

	public boolean inBounds(GISRecord record) {
		// TODO Auto-generated method stub
		return true;
	}

	public List<Long> find(long lattitude, long longitude) {
		CoordAndOffset result = this.quadTree.find(new CoordAndOffset(longitude, lattitude, -1));
		if (result == null) {
			return new ArrayList<Long>(0);
		} else {
			return result.getFileOffsets();
		}
	}

	public Vector<CoordAndOffset> find(long xLo, long xHi, long yLo, long yHi) {
		Vector<CoordAndOffset> results = this.quadTree.find(xLo, xHi, yLo, yHi);
		return results;
	}

	public void what_is_at(String commandSwitch, String lattitudeString, String longitudeString) {
		long lattitude = GISRecord.getSeconds(lattitudeString);
		long longitude = GISRecord.getSeconds(longitudeString);
		CoordAndOffset result = this.quadTree.find(new CoordAndOffset(longitude, lattitude, -1));
		List<GISRecord> records = new ArrayList<GISRecord>(0);
		if (result == null || result.getFileOffsets().size() == 0) {
			this.logController.logWithNewline("Nothing was found at " + longitudeString + "\t" + lattitudeString);
		} else {
			records = this.bufferPool.getAll(result.getFileOffsets());
			if ("-c".equals(commandSwitch)) {
				this.logController.logWithNewline("The number of records for " + longitudeString + " and "
						+ lattitudeString + " was " + records.size());
			} else {
				if (result == null || records.size() == 0) {
					this.logController.logWithNewline("No records found");
				} else {
					this.logController.logWithNewline("The following features were found at " + longitudeString + "\t"
							+ lattitudeString + ":");
					for (GISRecord record : records) {
						if ("-l".equals(commandSwitch)) {
							this.logController.logWithNewline(record.nonEmptyFieldToString());
						} else {
							long offset = result.getFileOffsets().get(records.indexOf(record));
							this.logController.logWithNewline(offset + ":\t" + record.toStringFnameCnameStateAbbrev());
						}
					}
				}

			}
		}
	}

	public void what_is_in(String commandSwitch, String lattitudeString, String longitudeString, long halfHeight,
			long halfWidth) {
		long lattitude = GISRecord.getSeconds(lattitudeString);
		long longitude = GISRecord.getSeconds(longitudeString);
		List<CoordAndOffset> offsets = this.quadTree.find(longitude - halfWidth, longitude + halfWidth, lattitude
				- halfHeight, lattitude + halfHeight);
		if (offsets == null || offsets.size() == 0) {
			this.logController.logWithNewline("Nothing was found in (" + longitudeString + " +/- " + halfWidth + ", "
					+ lattitudeString + " +/- " + halfHeight + ")");
		} else {
			if ("-c".equals(commandSwitch)) {
				this.logController.logWithNewline(getOffsetCount(offsets) + " features were found in ("
						+ longitudeString + " +/- " + halfWidth + ", " + lattitudeString + " +/- " + halfHeight + ")");
			} else {
				this.logController.logWithNewline("The following " + getOffsetCount(offsets)
						+ " features were found in (" + longitudeString + " +/- " + halfWidth + ", " + lattitudeString
						+ " +/- " + halfHeight + ")");
				for (CoordAndOffset co : offsets) {
					for (long offset : co.getFileOffsets()) {
						if ("-l".equals(commandSwitch)) {
							logController.logWithNewline(this.bufferPool.get(offset).nonEmptyFieldToString());
						} else {
							logController.logWithNewline(offset + ":\t"
									+ this.bufferPool.get(offset).nameStateCoordinateToString());
						}
					}

				}
			}
		}

	}

	private int getOffsetCount(List<CoordAndOffset> offsets) {
		// TODO Auto-generated method stub
		int offsetCount = 0;
		for (CoordAndOffset offset : offsets) {
			offsetCount += offset.getFileOffsets().size();
		}
		return offsetCount;
	}

	public String toString() {
		return this.quadTree.toString();
	}

}
