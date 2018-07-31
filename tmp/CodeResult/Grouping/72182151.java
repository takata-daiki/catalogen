package pixelmon.migration.groupings;

import java.util.ArrayList;

import pixelmon.migration.Pixelmon;

public abstract class Grouping {
	public ArrayList<Pixelmon> pixelmon;
	public Pixelmon leader;
	public boolean emptyGroup;
	public boolean oneType;
	public int xPos, yPos, zPos;

	/**
	 * The abstract grouping class is the overall class for handling groups of
	 * pokemon of any type. There will be a pokemon leader assigned to each
	 * grouping who recieves move information from migration and controls the
	 * grouping's movements through the minecraft world
	 * 
	 * @param creatureName
	 * @param x
	 *            x coord
	 * @param y
	 *            y coord
	 * @param z
	 *            z coord
	 */
	public Grouping(String creatureName, int x, int y, int z) {
		setPosition(x, y, z);
		pixelmon = new ArrayList<Pixelmon>();
		oneType = false;
		leader = new Pixelmon(creatureName, x, y, z, true);
		emptyGroup = false;
	}

	/**
	 * Sets the pokemon's position in the world
	 */
	public void setPosition(int x, int y, int z) {
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}

	/**
	 * Sets whether the group is made of all of one type of pokemon
	 * 
	 * @param b
	 *            true == one type
	 * @return the grouping
	 */
	public Grouping setOneType(boolean b) {
		oneType = b;
		return this;
	}

	/**
	 * Add's a pokemon to the group. Checks whether the pokemon is allowed to
	 * join that group before adding
	 * 
	 * @param name
	 *            Name of pokemon to be added
	 */
	public void addPixelmon(String name) {
		int x = 0, y = 0, z = 0;
		if (oneType) {
			if (!name.equals(leader.name)) {
				throw new RuntimeException(name + " was tried to be put in a one type group of " + leader.name);
			} else {
				Pixelmon p = new Pixelmon(name, x, y, z, false);
				pixelmon.add(p);
			}
		} else {
			Pixelmon p = new Pixelmon(name, x, y, z, false);
			pixelmon.add(p);
		}
	}

	/**
	 * Called when a pokemon in this particular group is killed. Will reassign a
	 * leader in the event of the leader dying
	 * 
	 * @param p
	 *            pokemon killed
	 */
	public void pixelmonKilled(Pixelmon p) {
		if (p == leader) {
			leader = null;
			if (pixelmon.isEmpty()) {
				emptyGroup = true;
			} else {
				Pixelmon newLeader = null;
				int level = 0;
				for (Pixelmon p1 : pixelmon) {
					int l = p1.level;
					if (l > level) {
						newLeader = p1;
						level = l;
					}
				}
				pixelmon.remove(newLeader);
				leader = newLeader;
			}
		} else {
			pixelmon.remove(p);
		}
	}

}
