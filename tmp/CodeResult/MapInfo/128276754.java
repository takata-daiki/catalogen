package com.andreschnabel.tinyworld.map;

public class MapInfo {
	public boolean finished;		
	public long ticksNeeded;
	
	public MapInfo(boolean finished, long ticksNeeded) {
		this.finished = finished;
		this.ticksNeeded = ticksNeeded;
	}
}