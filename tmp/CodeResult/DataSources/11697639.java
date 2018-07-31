package ru.cos.sim.ras.duo;

import ru.cos.sim.ras.duo.digraph.Edge;
import ru.cos.sim.ras.duo.utils.Extendable;

public class DataSources {
	public DataSources(float timestamp, int vehicleId, Edge edge, int linkId, float position, float speed, Extendable vehicleData) {
		this.timestamp = timestamp;
		this.vehicleId = vehicleId;
		this.edge = edge;
		this.linkId = linkId;
		this.position = position;
		this.speed = speed;
		this.vehicleData = vehicleData;
	}
	
	private final float timestamp;
	public float getTimestamp() {
		return timestamp;
	}
	
	private final int vehicleId;
	public int getVehicleId() {
		return vehicleId;
	}
	
	private final Edge edge;
	public Edge getEdge() {
		return edge;
	}
	
	private int linkId;
	public int getLinkId() {
		return linkId;
	}
	
	private float position;
	public float getPosition() {
		return position;
	}
	
	private float speed;
	public float getSpeed() {
		return speed;
	}
	
	private final Extendable vehicleData;
	public Extendable getVehicleData() {
		return vehicleData;
	}
}
